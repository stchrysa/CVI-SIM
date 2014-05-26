package gui.simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import output.Results;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import model.Algorithm;
import model.Request;
import model.Simulation;
import model.Substrate;
import model.components.Link;
import model.components.Node;
import model.components.Server;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import model.components.VirtualMachine;

@SuppressWarnings("rawtypes")
public class SimulationWork extends SwingWorker {

	private Simulation simulation;
	private List<Request> requests;
	private List<Substrate> substrates;
	private Algorithm algorithm;
	private Substrate substrate;
	SimulationFrame simulationFrame;
	JPanel simulatorContentPane;
	
	public SimulationWork(Simulation simulation, JPanel simulatorContentPane) {
		this.simulation = simulation;
		this.simulatorContentPane = simulatorContentPane;
		this.requests = simulation.getRequests();
		this.substrate = simulation.getInPs();
		this.substrates = simulation.getSubstrates();
		this.algorithm = simulation.getAlgorithm();
		// Setting substrate to the algorithm
		this.algorithm.addSubstrate(substrates);
		this.algorithm.addInPs(substrate);
	}

	@Override
	protected List<Substrate> doInBackground() throws Exception {
		
		List<Substrate> resultSubstrates = new ArrayList<Substrate>();
		
		Substrate InPs = (Substrate) substrate.getCopy();
		InPs.setId(substrate.getId()+"(0)");
		resultSubstrates.add((Substrate) substrate.getCopy());
		
		for (Substrate sub: substrates){
			Substrate initSubstrate = (Substrate) sub.getCopy();
			initSubstrate.setId(sub.getId()+"(0)");
			resultSubstrates.add((Substrate) sub.getCopy());
		}

		try
	    {
		  Calendar calendar = Calendar.getInstance();
		  java.util.Date now = calendar.getTime();
		  java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		  String name =  currentTimestamp.toString();
		  String[] out = name.split(" ");
		  String[] out1 = out[1].split(":");
		 // System.out.println(out[0]);
		  String filename = "input"+out[0]+"_"+out1[0]+"-"+out1[1]+"_"+ simulation.getAlgorithm().getId()+".xls";
	      WorkbookSettings ws = new WorkbookSettings();
	      ws.setLocale(new Locale("en", "EN"));
	      WritableWorkbook workbook =  Workbook.createWorkbook(new File(filename), ws);
	      WritableSheet s = workbook.createSheet("Sheet1", 0);

	      // Format the Font 
		    WritableFont wf = new WritableFont(WritableFont.ARIAL, 
		      10, WritableFont.BOLD);
		    WritableCellFormat cf = new WritableCellFormat(wf);
		    cf.setWrap(true);
		      Label l = new Label(0,0,"Time",cf);
		      s.addCell(l);
		      l= new Label(1,0,"Acceptance",cf);
		      s.addCell(l);
		      l=new Label(2,0,"Revenue",cf);
		      s.addCell(l);
		      l=new Label(3,0,"Cost",cf);
		      s.addCell(l);
		      l=new Label(4,0,"Avg Hop",cf);
		      s.addCell(l);
		      l=new Label(5,0,"SPBC",cf);
		      s.addCell(l);
		      l=new Label(6,0,"Partitioning Cost", cf);
		      s.addCell(l);
		      l=new Label(7,0,"Partitioning Exec. Time", cf);
		      s.addCell(l);
		      l=new Label(8,0,"Inter Allocation", cf);
		      s.addCell(l);
		      l=new Label(9,0,"Intra Allocation", cf);
		      s.addCell(l);
		      int counter_sub=0;
		      for (Substrate sub: simulation.getSubstrates()){
		    	  l=new Label(10+simulation.getSubstrates().size()*counter_sub,0,"Cpu_Util_"+sub.getId(),cf);
		    	  s.addCell(l);
		    	  l=new Label(10+simulation.getSubstrates().size()*counter_sub+1,0,"Mem_Util_"+sub.getId(),cf);
		    	  s.addCell(l);
		    	  l=new Label(10+simulation.getSubstrates().size()*counter_sub+2,0,"Disk_Util_"+sub.getId(),cf);
		    	  s.addCell(l);
		    	  l=new Label(10+simulation.getSubstrates().size()*counter_sub+3,0,"Router_Util_"+sub.getId(),cf);
		    	  s.addCell(l);
		    	  l=new Label(10+simulation.getSubstrates().size()*counter_sub+4,0,"Link_Util_"+sub.getId(),cf);
		    	  s.addCell(l);
		    	  counter_sub++;
		      }
			    
	
			int denials = 0;
			double cost=0;
			int multi=0;
			int single=0;
		    double revenue=0;
		    double avgHops =0;
		    double spbc=0;
			double part_cost=0;
			double part_time=0;
		    int current_request=0;
						
		setProgress(0);

		// Give total time of the simulation
		int simulationTime = simulation.getEndDate();
		// Sizing progress bar
		simulationFrame.getProgressBar().setMinimum(0);
		simulationFrame.getProgressBar().setValue(0);
		simulationFrame.getProgressBar().setMaximum(simulationTime);
		
		List<Request> endingRequests;
		List<Request> startingRequests;		

	    int counter2=0;		
	    
	    //Initialization of the charts 
	    final XYSeries accept = new XYSeries("Acceptance Ratio");
	    final XYSeries emb_cost = new XYSeries("Embedding Cost");
	    final XYSeries emb_rev = new XYSeries("Embedding Revenue");
	    final XYSeries hops = new XYSeries("Average Hop Number");
	    final LinkedHashMap<String, XYSeries> util = new LinkedHashMap<String, XYSeries>();
	    final XYSeriesCollection dataset_acc = new XYSeriesCollection();
	    final XYSeriesCollection dataset_cost = new XYSeriesCollection();
	    final XYSeriesCollection dataset_rev = new XYSeriesCollection();
	    final XYSeriesCollection dataset_hop = new XYSeriesCollection();
	    final LinkedHashMap<String, XYSeriesCollection> dataset_util = new LinkedHashMap<String, XYSeriesCollection>();
	    
	    //Populate the utilization hash map
	    for (Substrate sub: substrates){
	    	String cpu = sub.getId().concat("cpu");
	    	final XYSeries utilcpu= new XYSeries("CPU Utilization");
	    	util.put(cpu, utilcpu);
	    	String mem = sub.getId().concat("mem");
	    	final XYSeries utilmem= new XYSeries("Memory Utilization");
	    	util.put(mem, utilmem);
	    	String disk = sub.getId().concat("disk");
	    	final XYSeries utildisk= new XYSeries("Disk Utilization");
	    	util.put(disk, utildisk);
	    	String rout = sub.getId().concat("rout");
	    	final XYSeries utilrout= new XYSeries("Router Utilization");
	    	util.put(rout, utilrout);
	    	String link = sub.getId().concat("link");
	    	final XYSeries utillink= new XYSeries("Link Utilization");
	    	util.put(link, utillink);
	    }

		/** for each unit of time we first release ended requests
		 * and then we send to the algorithm starting requests
		 * to perform the allocation of their resources **/
		for (int i=0; i<=simulationTime; i++) {
		
			//substrate.print();
			// Release ended simulations
			endingRequests = simulation.getEndingRequests(i);
			for (Request req : endingRequests)
				simulationFrame.addProgressText(Color.BLACK, "Releasing request "+req.getId()+"...\n");
			simulation.releaseRequests(endingRequests);
			// Allocate arriving simulations
			startingRequests = simulation.getStartingRequests(i);
			for (Request req : startingRequests)
				simulationFrame.addProgressText(Color.BLACK, "Allocating request "+req.getId()+"...\n");
			algorithm.addRequests(startingRequests);
			
			
			double[][] retres=algorithm.runAlgorithm();
  			
			for (int ind=0; ind<startingRequests.size();ind++){
  				denials += (int)retres[ind][0];
  				cost+=retres[ind][1];
  				avgHops +=retres[ind][2];
  				spbc += retres[ind][3];
  				part_cost+=retres[ind][5];
  				part_time+=retres[ind][6];
  				single+=retres[ind][7];
  				multi+=retres[ind][8];
  				revenue+=retres[ind][9];
   			}
  			
  			for (Request req:startingRequests){
  				current_request++;
  				if (!req.getRMap().isDenied()){
  					for (Node x: req.getGraph().getVertices()){
  						if (x instanceof VirtualMachine)
  							revenue+=+x.getCpu()+x.getMemory()+((VirtualMachine)x).getDiskSpace();
  					}
  				
  					for (Link current: req.getGraph().getEdges())
  						revenue+=current.getBandwidth();
  					
  				}
  			}

  		//Take results every 1000 time units
  		//Take results every 100 time units
            //if (((i%100)==0)&&(i!=0)){
  			if (((i%10)==0)){
            	double[] cpu_util=Results.Node_utilization_Server_Cpu(substrates);
            	double[] mem_util=Results.Node_utilization_Server_Memory(substrates);
            	double[] disk_util=Results.Node_utilization_Server_DiskSpace(substrates);
            	double[] router_util=Results.Node_utilization_Router(substrates);
            	double[] link_util=Results.Link_utilization(substrates);
            	
            	counter2++;
            	Number n = new Number(0,counter2,i);
                s.addCell(n);
                double acceptance = (double)(current_request-denials)/current_request; 
                Number n1 = new Number(1,counter2,acceptance);
                s.addCell(n1);
                Number n2 = new Number(2,counter2,revenue/i);
                s.addCell(n2);
                Number n3 = new Number(3,counter2,cost/(current_request-denials));
                s.addCell(n3);
               	Number n4 = new Number(4,counter2,avgHops/(current_request-denials));
                s.addCell(n4);
                Number n5 = new Number(5,counter2, spbc/(current_request-denials));
                s.addCell(n5);
                int ctr_sub=0;
                for (@SuppressWarnings("unused") Substrate sub: substrates){
                	Number n10 = new Number (10+substrates.size()*ctr_sub, counter2, cpu_util[ctr_sub]);
                	s.addCell(n10);
                	Number n11 = new Number (10+substrates.size()*ctr_sub+1, counter2, mem_util[ctr_sub]);
                	s.addCell(n11);
                	Number n12 = new Number (10+substrates.size()*ctr_sub+2, counter2, disk_util[ctr_sub]);
                	s.addCell(n12);
                	Number n13 = new Number (10+substrates.size()*ctr_sub+3, counter2, router_util[ctr_sub]);
                	s.addCell(n13);
                	Number n14 = new Number (10+substrates.size()*ctr_sub+4, counter2, link_util[ctr_sub]);
                	s.addCell(n14);
                	
                	//update the capacity of the nodes representing the substrates
                	for (Node node: substrate.getGraph().getVertices()){
                		if (node.getId()==ctr_sub){
                			node.setCpu((int) (node.getCpu()-node.getCpu()*cpu_util[ctr_sub]));
                			node.setMemory((int) (node.getMemory()-node.getMemory()*mem_util[ctr_sub]));
                			((Server)node).setDiskSpace((int) (((Server)node).getDiskSpace()-((Server)node).getDiskSpace()*mem_util[ctr_sub]));
                		}
                	}
                	
                	// update the utilization for the chart
                	XYSeries cpu = new XYSeries("Cpu Utilization");
                	XYSeries mem = new XYSeries("Memory Utilization");
                	XYSeries disk = new XYSeries("Disk Utilization");
                	XYSeries rout = new XYSeries("Router Utilization");
                	XYSeries link = new XYSeries("Link Utilization");
                	for (String key: util.keySet()){
                		if (key.contains(sub.getId().concat("cpu"))){
                			cpu = util.get(key);
                			cpu.add(i,cpu_util[ctr_sub]);
                		}
                		if (key.contains(sub.getId().concat("mem"))){
                			mem = util.get(key);
                			mem.add(i,mem_util[ctr_sub]);
                		}
                		if (key.contains(sub.getId().concat("disk"))){
                			disk = util.get(key);
                			disk.add(i,disk_util[ctr_sub]);
                		}
                		if (key.contains(sub.getId().concat("rout"))){
                			rout = util.get(key);
                			rout.add(i,router_util[ctr_sub]);
                		}
                		if (key.contains(sub.getId().concat("link"))){
                			link = util.get(key);
                			link.add(i,link_util[ctr_sub]);
                		}
                	}
                		
                	util.put(sub.getId().concat("cpu"), cpu);
                	util.put(sub.getId().concat("mem"), mem);
                	util.put(sub.getId().concat("disk"), disk);
                	util.put(sub.getId().concat("rout"), rout);
                	util.put(sub.getId().concat("link"), link);
                	
                	ctr_sub++;
                
                }
                //System.exit(0);
                
                accept.add(i,acceptance);
                emb_cost.add(i,cost/(current_request-denials));
                emb_rev.add(i,revenue/i);
                hops.add(i,avgHops/(current_request-denials));
                
                
            }
            
	        Number n5 = new Number(5,1, (double) part_cost/current_request);
	        s.addCell(n5);
	        Number n6 = new Number (6,1, (double) part_time/current_request);
	        s.addCell(n6);
	        Number n7 = new Number(7,1, single);
	        s.addCell(n7);
	        Number n8 = new Number(8,1, multi);
	        s.addCell(n8);
	        
	        
	        
      
			// Progress++
			setProgress(100*i/simulationTime);
			System.out.println("Progress: "+getProgress());
			simulationFrame.addProgressText(Color.BLACK, "Simulation time "+i+" done\n");
			simulationFrame.getProgressBar().setValue(i+1);
			System.out.println("in tge init b ////////////////////////////////////////////////////////");

			
			for (Substrate sub: substrates){
				Substrate iterationSubstrate = (Substrate) sub.getCopy();
				iterationSubstrate.setId(sub.getId()+"("+(i+1)+")");
				resultSubstrates.add(iterationSubstrate);
			}
			
			Substrate iterationInP = (Substrate) substrate.getCopy();
			iterationInP.setId(substrate.getId()+"("+(i+1)+")");
			resultSubstrates.add(iterationInP);			
		}
		
    
		//Create the charts
        dataset_acc.addSeries(accept);
        dataset_cost.addSeries(emb_cost);
        dataset_rev.addSeries(emb_rev);
        dataset_hop.addSeries(hops);    
        Charts demo = new Charts("Simulation Results", dataset_acc, dataset_cost, dataset_rev, dataset_hop);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        
        for (Substrate sub: substrates){
        	XYSeriesCollection cpu = new XYSeriesCollection();
        	cpu.addSeries(util.get(sub.getId().concat("cpu")));
        	XYSeriesCollection mem = new XYSeriesCollection();
        	mem.addSeries(util.get(sub.getId().concat("mem")));
        	XYSeriesCollection disk = new XYSeriesCollection();
        	disk.addSeries(util.get(sub.getId().concat("disk")));
        	XYSeriesCollection rout = new XYSeriesCollection();
        	rout.addSeries(util.get(sub.getId().concat("rout")));
        	XYSeriesCollection link = new XYSeriesCollection();
        	link.addSeries(util.get(sub.getId().concat("link")));
        	String title="Utilization ".concat(sub.getId());
        	Charts_Utilization demo2 = new Charts_Utilization(title, cpu, mem, disk, rout, link);
        	
        	demo2.pack();
            RefineryUtilities.centerFrameOnScreen(demo2);
            demo2.setVisible(true);
        }
        		
		System.out.println();
		System.out.println("simulation time: "+simulationTime);
		System.out.println("denials: "+denials);
		System.out.println("current request: "+requests.size());

        
		workbook.write();
	    workbook.close(); 
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    catch (WriteException e)
	    {
	      e.printStackTrace();
	    }
		
        return resultSubstrates;
	}
	
	 /*
     * Executed in event dispatching thread
     */
    @SuppressWarnings("unchecked")
	@Override
    public void done() {
    	Toolkit.getDefaultToolkit().beep();
    	if (!isCancelled()) {
    		simulationFrame.addProgressText(Color.BLACK, "Simulation finished\n");
    		simulationFrame.getCancelButton().setEnabled(false);
    		// Show panel with the state of the substrate at each step
			List<Substrate> resultSubstrates = new ArrayList<Substrate>();
			/*try {
				resultSubstrates = (List<Substrate>) this.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			if (resultSubstrates.size()==0) {
				//TODO Error!
				return;
			}
			
			for (Substrate sub: substrates){
				ArrayList<Substrate> resultSubstrate = new ArrayList<Substrate>();
				for (Substrate ressub: resultSubstrates){
					if (ressub.getId().contains(sub.getId())){
						resultSubstrate.add(ressub);
					}
				}
				//Setting visible the ResultsFrame
				ResultsFrame rf = new ResultsFrame(simulatorContentPane,
					"Substrate status during the simulation",
					resultSubstrate);
					rf.pack();
					rf.setVisible(true);				
			}
			
			ArrayList<Substrate> resultSubstrate = new ArrayList<Substrate>();
			for (Substrate ressub: resultSubstrates){
				if (ressub.getId().contains(substrate.getId())){
					resultSubstrate.add(ressub);
				}
			}
			//Setting visible the ResultsFrame
			ResultsFrame rf = new ResultsFrame(simulatorContentPane,
				"Substrate status during the simulation",
				resultSubstrate);
				rf.pack();
				rf.setVisible(true);	
		
		}
    	else simulationFrame.addProgressText(Color.RED, "Simulation canceled\n");
    	simulationFrame.getProgressDescription().setEditable(false);
//    	simulationDialog.setVisible(false);
    }

	public void setDialog(SimulationFrame simulationDialog) {
		this.simulationFrame = simulationDialog;
	}

	public Substrate getSubstrate() {
		return substrate;
	}

}
