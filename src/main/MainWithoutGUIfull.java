package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.Number;

import model.Algorithm;
import model.NetworkGraph;
import model.Request;
import model.RequestLinkFactory;
import model.RequestNodeFactory;
import model.Simulation;
import model.Substrate;
import model.SubstrateLinkFactory;
import model.SubstrateNodeFactory;
import model.components.Link;
import model.components.Node;
import model.components.VirtualMachine;

import output.Results;

import org.apache.commons.collections15.Factory;

import cern.jet.random.Exponential;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.Pair;
import gui.SimulatorConstants;


public class MainWithoutGUIfull{

	public static void main(String[] args) {
		
	
		// Number of experiments to execute
		int experiments=1;
		
		for (int i=0;i<experiments;i++){			
			//Number of Infrastructure Providers
			int inp_no=5;
			
			//Create an abstract graph where each node represent an InP
			Substrate InPs=new Substrate("InPs");
						
			//create a partially mesh topology for the InPs
			if (inp_no!=1){
				boolean ctrl=false;
				while (ctrl==false){
					InPs=createInPGraph(inp_no);
					if (InPs.getGraph().getVertexCount()==inp_no){
						ctrl=true;
					}
				}
			}		
					
			//Create each InP
			List<Substrate> substrates = new ArrayList<Substrate>();
			substrates= createSubstrateGraph(inp_no);
			
			//Create the Requests
			List<Request> request_tab = new ArrayList<Request>();
			request_tab =randomRequestGeneration();	
			
			//Run the Algorithm, First term defines the Intra-VNE second term the Inter-VNE
			Algorithm algorithm = new Algorithm("GSP-ILS");
			Simulation simulation = new Simulation(InPs, substrates, request_tab, algorithm);
			launchLaunchSimulation(simulation, i);
		
		}
	}

	
	private static double[] launchLaunchSimulation(Simulation simulation,int inD){
		//results, 0 cost, 1 time, 2, denial
		double[] results=new double[3];
		  try {
			  Calendar calendar = Calendar.getInstance();
			  java.util.Date now = calendar.getTime();
			  java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			  String name =  currentTimestamp.toString();
			  String[] out = name.split(" ");
			  String[] out1 = out[1].split(":");
			 // System.out.println(out[0]);
		      String filename = "input"+out[0]+"_"+out1[0]+"-"+out1[1]+"_"+ simulation.getAlgorithm().getId()+"_"+inD+".xls";
		      WorkbookSettings ws = new WorkbookSettings();
		      ws.setLocale(new Locale("en", "EN"));
		      WritableWorkbook workbook =  Workbook.createWorkbook(new File(filename), ws);
		      WritableSheet s = workbook.createSheet("Sheet1", 0);

			   /* Format the Font */
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
	    
	    int simulationTime = (int)simulation.getEndDate();
		List<Substrate> substrates = simulation.getSubstrates();
		Substrate InPs = simulation.getInPs();
		Algorithm algorithm = simulation.getAlgorithm();
		List<Request> endingRequests;
	    List<Request> startingRequests;
	    algorithm.addSubstrate(substrates);
	    algorithm.addInPs(InPs);
	    

	    
/////////////////////////////////////////////////////////////////////
	    
	  
	    //Results results=new Results(substrate);
	    int counter2=0;
	        for (int i=0; i<simulationTime; i++) {	        
	            // Release ended simulations in the moment "i"
	            endingRequests = simulation.getEndingRequests(i);
	            simulation.releaseRequests(endingRequests);
	            // Allocate arriving requests in the moment "i"
	            startingRequests = simulation.getStartingRequests(i);
	            algorithm.addRequests(startingRequests);
      			double[][] retres=algorithm.runAlgorithm();      			
      	
      			for (Request req: startingRequests){
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
      			
              	//Take results every 100 time units
	            if (((i%100)==0)&&(i!=0)){
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
	                	ctr_sub++;
	                }
	                
	                //System.exit(0);                
	               	               
	            }
      			
	        }
	        
	        Number n5 = new Number(5,1, (double) part_cost/current_request);
	        s.addCell(n5);
	        Number n6 = new Number (6,1, (double) part_time/current_request);
	        s.addCell(n6);
	        Number n7 = new Number(7,1, single);
	        s.addCell(n7);
	        Number n8 = new Number(8,1, multi);
	        s.addCell(n8);
  			
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
  			

  			return results;

	}
	
	
	
	private static List<Request> randomRequestGeneration() {
		final String prefix ="req";
		//Number of Requests
		final int numRequests = 1;
		//Range of nodes within a request
		final int minNodes =2;
		final int maxNodes=10;
		final int minLinks=1;
		final int maxLinks=25;
		final String timeDistribution = SimulatorConstants.POISSON_DISTRIBUTION;
		final String linkConnectivity = SimulatorConstants.PERCENTAGE_CONNECTIVITY;
		final double linkProbability=0.5;
		final int fixStart=0;
		final int uniformMin=0;
		final int uniformMax=0;
		final int normalMean=0;
		final int normalVariance=0;
		RequestNodeFactory nodeFactory;
		RequestLinkFactory linkFactory;
		

		final List<Request> requests = new ArrayList<Request>();

	        	int startDate = 0;
	        	int lifetime = 0;
	        	int sum=0;

	        	Exponential exp_arr = null;
	        	Exponential exp = null;
	        	if (timeDistribution.equals(SimulatorConstants.POISSON_DISTRIBUTION)) {
		          	RandomEngine engine = new DRand();
		            exp_arr = new Exponential(0.04,engine);
		        	exp= new Exponential(0.001,engine);
	        	}

        	
				for (int i=0; i<numRequests; i++) {
						Request request = new Request(prefix+i);			
						
						// Random num of nodes inside range (minNodes-maxNodes)
						int numNodes = minNodes + (int)(Math.random()*((maxNodes - minNodes) + 1));
						// Random num of links inside range (minLinks-maxLinks)
						int numLinks = minLinks + (int)(Math.random()*((maxLinks - minLinks) + 1));
						//lifetime of the request
						lifetime = exp.nextInt();
						
						boolean flag = false;
						while (flag==false){
						
						SparseMultigraph<Node, Link> g = null;
						
						nodeFactory = new RequestNodeFactory();
						linkFactory = new RequestLinkFactory();
						
						if (linkConnectivity.equals(SimulatorConstants.LINK_PER_NODE_CONNECTIVITY)) {
							//Random Graph Generation
							Factory<Graph<Node, Link>> graphFactory = new Factory<Graph<Node, Link>>() {
								public UndirectedGraph<Node, Link> create() {
									return new NetworkGraph();
								}
							};
							//Barabasi-Albert generation
							BarabasiAlbertGenerator<Node, Link> randomGraph = new BarabasiAlbertGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, 1, numLinks, new HashSet<Node>());
							randomGraph.evolveGraph(numNodes-1);
							g = (SparseMultigraph<Node, Link>) randomGraph.create();
						}
						else if (linkConnectivity.equals(SimulatorConstants.PERCENTAGE_CONNECTIVITY)) {
							
							//Random Graph Generation
							Factory<UndirectedGraph<Node, Link>> graphFactory = new Factory<UndirectedGraph<Node, Link>>() {
								public UndirectedGraph<Node, Link> create() {
									return new NetworkGraph();
								}
							};
							//ErdosRenyiGenerator generation
							ErdosRenyiGenerator<Node, Link> randomGraph = new ErdosRenyiGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, numNodes, linkProbability);
							g = (SparseMultigraph<Node, Link>) randomGraph.create();
							//Remove unconnected nodes
							((NetworkGraph) g).removeUnconnectedNodes();
						
							WeakComponentClusterer<Node, Link> wcc = new WeakComponentClusterer<Node, Link>();
							Set<Set<Node>> nodeSets = wcc.transform(g);
							Collection<SparseMultigraph<Node, Link>> gs = FilterUtils.createAllInducedSubgraphs(nodeSets, g); 
							if (gs.size()>1) {
								@SuppressWarnings("rawtypes")
								Iterator itr = gs.iterator();
								g = (NetworkGraph)itr.next();
								while (itr.hasNext()) {
									@SuppressWarnings("unchecked")
									SparseMultigraph<Node, Link> nextGraph = (SparseMultigraph<Node, Link>) itr.next();
							
									if (nextGraph.getVertexCount()>g.getVertexCount())
										g = (NetworkGraph)nextGraph;
								}
							}
							
							if (g.getVertexCount()>0){
								// Change id of nodes to consecutive int (0,1,2,3...)
								@SuppressWarnings("rawtypes")
								Iterator itr = g.getVertices().iterator();
								int id = 0;
								while (itr.hasNext()) {
									((Node) itr.next()).setId(id);
									id++;
								}
								// refresh nodeFactory's nodeCount
								nodeFactory.setNodeCount(id);
								// Change id of edges to consecutive int (0,1,2,3...)
								itr = g.getEdges().iterator();
								id = 0;
								while (itr.hasNext()) {
									((Link) itr.next()).setId(id);
									id++;
								}
								// refresh linkFactory's linkCount
								linkFactory.setLinkCount(id);
							}
	
						}
					
						if (g.getVertexCount()==numNodes){
					
							flag = true;
							request.setGraph(g);
							request.setNodeFactory(nodeFactory);
							request.setLinkFactory(linkFactory);
					
							// Random start/end dates
		 
							// Duration of the request
							lifetime = exp.nextInt();
							// All requests start at month fixStart
							if (timeDistribution.equals(SimulatorConstants.FIXED_DISTRIBUTION)) {
								request.setStartDate(fixStart);
								request.setEndDate(fixStart+lifetime);
							}
							
							// Random: Uniform distribution
							if (timeDistribution.equals(SimulatorConstants.UNIFORM_DISTRIBUTION)) {
								startDate = uniformMin + (int)(Math.random()*((uniformMax - uniformMin) + 1));
								request.setStartDate(startDate);
								request.setEndDate(startDate+lifetime);
							}
							
							// Random: Normal distribution
							if (timeDistribution.equals(SimulatorConstants.NORMAL_DISTRIBUTION)) {
								Random random = new Random();
								startDate = (int) (normalMean + random.nextGaussian() * normalVariance);
								if (startDate<0)
									startDate*=-1;
								request.setStartDate(startDate);
								request.setEndDate(startDate+lifetime);
							}
		
							// Random: Poisson distribution
							if (timeDistribution.equals(SimulatorConstants.POISSON_DISTRIBUTION)) {
								startDate = exp_arr.nextInt();
								sum=sum+startDate;
								if (lifetime==0)
									lifetime=lifetime+1;
								request.setStartDate(sum);
								request.setEndDate(sum+lifetime);
							}
						
							
							requests.add(request);
						}
					}
				}
				
		
        return requests;
	}
	
	
		
 public static List<Substrate> createSubstrateGraph(int inp_no){
	 	 	
	 	final String prefix ="sub";
		final List<Substrate> substrates = new ArrayList<Substrate>();
		
		for (int i=0;i<inp_no;i++){
			Substrate substrate = new Substrate(prefix+i);
			//Number of nodes
			int numNodes = 50;
			//Percentage of connectivity between nodes
			double linkProbability = 0.5;	
			SparseMultigraph<Node, Link> g = null;	
			SubstrateNodeFactory nodeFactory = new SubstrateNodeFactory();
			SubstrateLinkFactory linkFactory = new SubstrateLinkFactory();
			
			//Random Graph Generation
			Factory<UndirectedGraph<Node, Link>> graphFactory = new Factory<UndirectedGraph<Node, Link>>() {
				public UndirectedGraph<Node, Link> create() {
					return new NetworkGraph();
				}
			};
				
				//ErdosRenyiGenerator generation
			ErdosRenyiGenerator<Node, Link> randomGraph = new ErdosRenyiGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, numNodes, linkProbability );
			g = (SparseMultigraph<Node, Link>) randomGraph.create();
				//Remove unconnected nodes
			((NetworkGraph) g).removeUnconnectedNodes();
				// TODO remove disconnected graphs
			WeakComponentClusterer<Node, Link> wcc = new WeakComponentClusterer<Node, Link>();
			Set<Set<Node>> nodeSets = wcc.transform(g);
			Collection<SparseMultigraph<Node, Link>> gs = FilterUtils.createAllInducedSubgraphs(nodeSets, g); 
			if (gs.size()>1) {
					@SuppressWarnings("rawtypes")
					Iterator itr = gs.iterator();
					g = (NetworkGraph)itr.next();
					while (itr.hasNext()) {
						@SuppressWarnings("unchecked")
						SparseMultigraph<Node, Link> nextGraph = (SparseMultigraph<Node, Link>) itr.next();
				
						if (nextGraph.getVertexCount()>g.getVertexCount())
							g = (NetworkGraph)nextGraph;
					}
			}
				
			if (g.getVertexCount()>0){
					// Change id of nodes to consecutive int (0,1,2,3...)
					@SuppressWarnings("rawtypes")
					Iterator itr = g.getVertices().iterator();
					int id = 0;
					while (itr.hasNext()) {
						((Node) itr.next()).setId(id);
						id++;
					}
					// refresh nodeFactory's nodeCount
					nodeFactory.setNodeCount(id);
					// Change id of edges to consecutive int (0,1,2,3...)
					itr = g.getEdges().iterator();
					id = 0;
					while (itr.hasNext()) {
						((Link) itr.next()).setId(id);
						id++;
					}
					// refresh linkFactory's linkCount
					linkFactory.setLinkCount(id);
			}
			
			substrate.setGraph(g);
			substrate.setNodeFactory(nodeFactory);
			substrate.setLinkFactory(linkFactory);
						
			substrates.add(substrate);
		
		}
		
		return substrates;
 }
 

 public static Substrate createInPGraph(int inp_no){
	 	
	 	final String prefix ="inps";
		final Substrate substrate = new Substrate(prefix);
		//Number of nodes in the Infrastructure Provider
		int numNodes = inp_no;
		//Probability of a connection
		double linkProbability = 0.5;	
		SparseMultigraph<Node, Link> g = null;	
		SubstrateNodeFactory nodeFactory = new SubstrateNodeFactory();
		SubstrateLinkFactory linkFactory = new SubstrateLinkFactory();
			
		//Random Graph Generation
		Factory<UndirectedGraph<Node, Link>> graphFactory = new Factory<UndirectedGraph<Node, Link>>() {
			public UndirectedGraph<Node, Link> create() {
				return new NetworkGraph();
			}
		};
				
		//ErdosRenyiGenerator generation
		ErdosRenyiGenerator<Node, Link> randomGraph = new ErdosRenyiGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, numNodes, linkProbability );
		g = (SparseMultigraph<Node, Link>) randomGraph.create();
		//Remove unconnected nodes
		((NetworkGraph) g).removeUnconnectedNodes();
		// TODO remove disconnected graphs
		WeakComponentClusterer<Node, Link> wcc = new WeakComponentClusterer<Node, Link>();
		Set<Set<Node>> nodeSets = wcc.transform(g);
		Collection<SparseMultigraph<Node, Link>> gs = FilterUtils.createAllInducedSubgraphs(nodeSets, g); 
		if (gs.size()>1) {
				@SuppressWarnings("rawtypes")
				Iterator itr = gs.iterator();
				g = (NetworkGraph)itr.next();
				while (itr.hasNext()) {
				@SuppressWarnings("unchecked")
				SparseMultigraph<Node, Link> nextGraph = (SparseMultigraph<Node, Link>) itr.next();
			
				if (nextGraph.getVertexCount()>g.getVertexCount())
						g = (NetworkGraph)nextGraph;
				}
		}
				
		if (g.getVertexCount()>0){
				// Change id of nodes to consecutive int (0,1,2,3...)
				@SuppressWarnings("rawtypes")
				Iterator itr = g.getVertices().iterator();
				int id = 0;
				while (itr.hasNext()) {
					((Node) itr.next()).setId(id);
					id++;
				}
				// refresh nodeFactory's nodeCount
				nodeFactory.setNodeCount(id);
				// Change id of edges to consecutive int (0,1,2,3...)
				itr = g.getEdges().iterator();
				id = 0;
				while (itr.hasNext()) {
					((Link) itr.next()).setId(id);
					id++;
				}
				// refresh linkFactory's linkCount
				linkFactory.setLinkCount(id);
		}
		
		if  ((g.getVertexCount()==inp_no)){
			substrate.setGraph(g);
			substrate.setNodeFactory(nodeFactory);
			substrate.setLinkFactory(linkFactory);
		}
		else{			
			createInPGraph(inp_no);
		}
		
		final String pre_fix ="sub";
				int i=0;
		for (Node node: substrate.getGraph().getVertices()){
			node.setName(pre_fix+i);
			i++;
		}
		
		return substrate;
}


 
 
 public static List<Request> SUBVNS(int reqNodeNum, int[] nodeMapping, List<Substrate> substrates, Request req){
 
List<Request> sub_request = new ArrayList<Request>();

 String[] NodeMapping = new String[reqNodeNum];
	for (int i=0;i<reqNodeNum;i++){
		int j=nodeMapping[i];
		NodeMapping[i]="sub"+j;
	}

	int counter=0;
	for(Substrate sub:substrates){
		Request subVN = new Request(req.getId()+"_"+counter);
		for (Node node:req.getGraph().getVertices()){
			if (NodeMapping[node.getId()].contains(sub.getId())){
				subVN.getGraph().addVertex(node);
				subVN.setStartDate(req.getStartDate());
				subVN.setEndDate(req.getEndDate());
				subVN.setInP(sub.getId());
			}
		}
		counter++;
		sub_request.add(subVN);
	}
	
	
	for (Request subVN:sub_request){
		for (Link link:req.getGraph().getEdges()){
			Pair<Node> x =  req.getGraph().getEndpoints(link);
			int i=x.getFirst().getId();
			int j=x.getSecond().getId();
			int checklink=0;
			for (Node node: subVN.getGraph().getVertices()){
				if (node.getId()==i)  checklink++;
				if (node.getId()==j)  checklink++;
			}
			if (checklink==2) subVN.getGraph().addEdge(link, x.getFirst(), x.getSecond());
				
		}
	}
	
	 return sub_request;
 }

 
}
