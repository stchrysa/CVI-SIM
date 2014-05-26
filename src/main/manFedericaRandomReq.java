/*package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cern.jet.random.Exponential;
import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;

import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import gui.SimulatorConstants;

import model.Algorithm;
import model.NetworkGraph;
import model.Request;
import model.RequestLinkFactory;
import model.RequestNodeFactory;
import model.Simulation;
import model.Substrate;
import model.SubstrateLinkFactory;
import model.SubstrateNodeFactory;
import output.Results;
import model.components.Link;
import model.components.Node;
import model.components.Server;
import model.components.SubstrateLink;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import model.components.Node.Location;

import java.util.HashSet;
import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.algorithms.generators.EvolvingGraphGenerator;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.Graph;


import java.io.*;
import jxl.*;
import java.util.*;
import jxl.Workbook;
import jxl.write.Number;

import jxl.write.*;

public class manFedericaRandomReq {

	public static void main(String[] args) {
	    int experiments=10;   

		for (int i =0; i<experiments; i++){
			 Substrate substrate = new Substrate("FEDERICA");
			
			 substrate= createSubstrateGraph(substrate);
			 Algorithm algorithm=null;
			 List<Request> request_tab = new ArrayList<Request>();
			 Simulation    simulation=null;
			 
			algorithm = new Algorithm("GSP");
			request_tab =randomRequestGeneration();
			simulation = new Simulation(substrate, request_tab, algorithm);
			launchLaunchSimulation(simulation);
				
     	    algorithm = new Algorithm("GSP");
		    request_tab =randomRequestGeneration();
		    simulation = new Simulation(substrate, request_tab, algorithm);
		    launchLaunchSimulation(simulation);
		    
		    algorithm = new Algorithm("GMCF");
		    request_tab =randomRequestGeneration();
		    simulation = new Simulation(substrate, request_tab, algorithm);
		    launchLaunchSimulation(simulation);
		    
		   
		    
	 //   Algorithm algorithm = new Algorithm("GSP");
	  //  Algorithm algorithm = new Algorithm("GMCF");
	  //  Algorithm algorithm = new Algorithm("rVine");
  
	    // Creating simulation
	    List<Request> request_tab = new ArrayList<Request>();
	    request_tab =randomRequestGeneration();
	    // Creating simulation
	    Simulation simulation = new Simulation(substrate, request_tab, algorithm);
	    launchLaunchSimulation(simulation);
		}
	}

	private static void launchLaunchSimulation(Simulation simulation) {
		
		  try
		    {
			  Calendar calendar = Calendar.getInstance();
			  java.util.Date now = calendar.getTime();
			  java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			  String name =  currentTimestamp.toString();
			  String[] out = name.split(" ");
			  String[] out1 = out[1].split(":");
			  System.out.println(out[0]);
		      String filename = "input"+out[0]+"_"+out1[0]+"-"+out1[1]+"_"+ simulation.getAlgorithm().getId()+".xls";
		      WorkbookSettings ws = new WorkbookSettings();
		      ws.setLocale(new Locale("en", "EN"));
		      WritableWorkbook workbook =  Workbook.createWorkbook(new File(filename), ws);
		      WritableSheet s = workbook.createSheet("Sheet1", 0);

					     Format the Font 
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
				      l=new Label (4,0,"NServer_Util_Cpu",cf);
				      s.addCell(l);
				      l=new Label (5,0,"NServer_Util_Memory",cf);
				      s.addCell(l);
				      l=new Label (6,0,"NServer_Util_DiskSpace",cf);
				      s.addCell(l);
				      l=new Label (7,0,"NRouter_Util_Cpu",cf);
				      s.addCell(l);
				      l=new Label (8,0,"NRouter_Util_Memory",cf);
				      s.addCell(l);
				      l=new Label (9,0,"NSwitch_Util_Cpu",cf);
				      s.addCell(l);
				      l=new Label (10,0,"NSwitch_Util_Memory",cf);
				      s.addCell(l);
				      l=new Label (11,0,"L_Util",cf);
				      s.addCell(l);
				    
		
		int denials = 0;
		double cost=0;
	    double revenue=0;
	    double avgHops =0;
	    int current_request=0;
	    
	    int simulationTime = (int)simulation.getEndDate();
		List<Request> requests = simulation.getRequests();
		Substrate substrate = simulation.getSubstrate();
		Algorithm algorithm = simulation.getAlgorithm();
		List<Request> endingRequests;
	    List<Request> startingRequests;
	    algorithm.addSubstrate(substrate);
	    
	    //Find the initial Node and Link capacity of the substrate to use it in
	    //the node utilization and link utilization result.
	    //Find also the number of Substrate Servers, Switch, Routers
	    int servers=0;
	    int routers=0;
	    int switches=0;
	    double[] n_initial_Cpu=new double[substrate.getGraph().getVertexCount()];
	    double[] n_initial_Memory=new double[substrate.getGraph().getVertexCount()];
	    double[] n_initial_DiskSpace=new double[substrate.getGraph().getVertexCount()];
	    for (Node x: substrate.getGraph().getVertices()){
	    	if (x instanceof Server){
	    		n_initial_Cpu[x.getId()]=x.getCpu();
	    		n_initial_Memory[x.getId()]=x.getMemory();
	    		n_initial_DiskSpace[x.getId()]=((Server) x).getDiskSpace();
	    		servers++;
	    	}
	    	else if (x instanceof SubstrateRouter){
	    		n_initial_Cpu[x.getId()]=x.getCpu();
	    		n_initial_Memory[x.getId()]=x.getMemory();
	    		routers++;
	    	}
	    	else if (x instanceof SubstrateSwitch){
	    		n_initial_Cpu[x.getId()]=x.getCpu();
	    		n_initial_Memory[x.getId()]=x.getMemory();
	    		switches++;
	    	}
	    }
	    
	    double[] l_initial=new double[substrate.getGraph().getEdgeCount()];
	    
	    for (Link current: substrate.getGraph().getEdges()){
	    	l_initial[current.getId()]=current.getBandwidth();
	    }
/////////////////////////////////////////////////////////////////////
	    
	    Results results=new Results(substrate);
	    int counter2=0;
	    //substrate.print();
	        for (int i=0; i<simulationTime; i++) {
	        
	            // Release ended simulations in the moment "i"
	            endingRequests = simulation.getEndingRequests(i);
	            simulation.releaseRequests(endingRequests);
	            // Allocate arriving simulations in the moment "i"
	            startingRequests = simulation.getStartingRequests(i);
	            algorithm.addRequests(startingRequests);
      			
	            double[][] retres=algorithm.runAlgorithm();
      			
      			for (int ind=0; ind<startingRequests.size();ind++){
      			denials += (int)retres[ind][0];
      			cost+=retres[ind][1];
      			avgHops +=retres[ind][2];
      			
      			}
      			
      			for (Request req:startingRequests){
          			revenue=revenue+results.Generate_Revenue(req);
          			current_request++;
      			}

          	//Take results every 1000 time units
	            if ((i%100)==0){
	            	counter2++;
	            	double[] nServer1_util=results.Node_utilization_Server_Cpu(n_initial_Cpu);
	            	double[] nServer2_util=results.Node_utilization_Server_Memory(n_initial_Memory);
	            	double[] nServer3_util=results.Node_utilization_Server_DiskSpace(n_initial_DiskSpace);
	            	double[] nRouter1_util=results.Node_utilization_Router_Cpu(n_initial_Cpu);
	            	double[] nRouter2_util=results.Node_utilization_Router_Memory(n_initial_Memory);
	            	double[] nSwitch1_util=results.Node_utilization_Switch_Cpu(n_initial_Cpu);
	            	double[] nSwitch2_util=results.Node_utilization_Switch_Memory(n_initial_Memory);
	            	double[] l_util=results.Link_utilization(l_initial);
	            	double nServer1_average=0;
	            	double nServer2_average=0;
	            	double nServer3_average=0;
	            	double nRouter1_average=0;
	            	double nRouter2_average=0;
	            	double nSwitch1_average=0;
	            	double nSwitch2_average=0;
	            	double l_average=0;
	            	for (int j=0;j<substrate.getGraph().getVertexCount();j++){
	            		nServer1_average += nServer1_util[j];
	            		nServer2_average += nServer2_util[j];
	            		nServer3_average += nServer3_util[j];
	            		nRouter1_average += nRouter1_util[j];
	            		nRouter2_average += nRouter2_util[j];
	            		nSwitch1_average += nSwitch1_util[j];
	            		nSwitch2_average += nSwitch2_util[j];
	            	}
	            	for (int j=0;j<substrate.getGraph().getEdgeCount();j++)
	            		l_average=l_average+l_util[j];
	            	
	            	Number n = new Number(0,counter2,i);
	                s.addCell(n);
	                Number n1 = new Number(1,counter2,results.Acceptance_Ratio(denials, current_request));
	                s.addCell(n1);
	                Number n2 = new Number(2,counter2,revenue/i);
	                s.addCell(n2);
	                Number n3 = new Number(3,counter2,cost/(current_request-denials));
	                s.addCell(n3);
	                Number n4 = new Number(4,counter2,(nServer1_average/servers));
	                s.addCell(n4);
	                Number n5 = new Number(5,counter2,(nServer2_average/servers));
	                s.addCell(n5);
	                Number n6 = new Number(6,counter2,(nServer3_average/servers));
	                s.addCell(n6);
	                Number n7 = new Number(7,counter2,(nRouter1_average/routers));
	                s.addCell(n7);
	                Number n8 = new Number(8,counter2,(nRouter2_average/routers));
	                s.addCell(n8);
	                Number n9 = new Number(9,counter2,(nSwitch1_average/switches));
	                s.addCell(n9);
	                Number n10 = new Number(10,counter2,(nSwitch2_average/switches));
	                s.addCell(n10);
	                Number n11 = new Number(11,counter2,(l_average/substrate.getGraph().getEdgeCount()));
	            	s.addCell(n11);
	            	Number n12 = new Number(12,counter2,avgHops/(current_request-denials));
	            	
	                s.addCell(n12);
	            }
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
		
	}
	
	private static List<Request> randomRequestGeneration() {
		final String prefix ="req";
		 final int numRequests = 30;
		 final int minNodes = 2;
		 final int maxNodes=6;
		 final int minLinks=1;
		 final int maxLinks=1;
		 final String timeDistribution = SimulatorConstants.POISSON_DISTRIBUTION;
		 final String linkConnectivity = SimulatorConstants.PERCENTAGE_CONNECTIVITY;
		 final double linkProbability=0.3;
		 final int fixStart=0;
		 final int uniformMin=0;
		 final int uniformMax=0;
		 final int normalMean=0;
		 final int normalVariance=0;
		 double poissonMean = 0.04;
		 final int lifetimeMin=1;
		 final int lifetimeMax=10;
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
						// TODO remove disconnected graphs
						WeakComponentClusterer<Node, Link> wcc = new WeakComponentClusterer<Node, Link>();
						Set<Set<Node>> nodeSets = wcc.transform(g);
						Collection<SparseMultigraph<Node, Link>> gs = FilterUtils.createAllInducedSubgraphs(nodeSets, g); 
						if (gs.size()>1) {
							Iterator itr = gs.iterator();
							g = (NetworkGraph)itr.next();
							while (itr.hasNext()) {
								SparseMultigraph<Node, Link> nextGraph = (SparseMultigraph<Node, Link>) itr.next();
						
								if (nextGraph.getVertexCount()>g.getVertexCount())
									g = (NetworkGraph)nextGraph;
							}
						}
						
						if (g.getVertexCount()>0){
							// Change id of nodes to consecutive int (0,1,2,3...)
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
					
				if (g.getVertexCount()>0){
					
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
				
					// Domain
					// Not defined
					
					requests.add(request);
				}
				}

        return requests;
	}
 public static Substrate createSubstrateGraph(Substrate substrate){
	// Hardcoded FEDERICA Substrate
	    *//** Portugal (FCCN)**//*
	    // sw1.lis.pt
	    SubstrateSwitch sw1LisPt = new SubstrateSwitch(0);
	    sw1LisPt.setName("sw1.lis.pt");
	    sw1LisPt.setLocation(Location.Portugal);
	    sw1LisPt.setCpu(100);
	    sw1LisPt.setMemory(100);
	    sw1LisPt.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.lis.pt
	    Server vnode1LisPt = new Server(1);
	    vnode1LisPt.setName("vnode1.lis.pt");
	    vnode1LisPt.setLocation(Location.Portugal);
	    vnode1LisPt.setCpu(100);
	    vnode1LisPt.setMemory(100);
	    vnode1LisPt.setDiskSpace(100);
	    vnode1LisPt.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    *//** Spain - Madrid (RedIRIS) **//*
	    // sw1.mad.es
	    SubstrateSwitch sw1MadEs = new SubstrateSwitch(2);
	    sw1MadEs.setName("sw1.mad.es");
	    sw1MadEs.setLocation(Location.Spain);
	    sw1MadEs.setCpu(100);
	    sw1MadEs.setMemory(100);
	    sw1MadEs.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.mad.es
	    Server vnode1MadEs = new Server(3);
	    vnode1MadEs.setName("vnode1.mad.es");
	    vnode1MadEs.setLocation(Location.Spain);
	    vnode1MadEs.setCpu(100);
	    vnode1MadEs.setMemory(100);
	    vnode1MadEs.setDiskSpace(100);
	    vnode1MadEs.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    *//** Spain - Barcelona (i2CAT) **//*
	    // sw1.bar.es
	    SubstrateSwitch sw1BarEs = new SubstrateSwitch(4);
	    sw1BarEs.setName("sw1.bar.es");
	    sw1BarEs.setLocation(Location.Spain);
	    sw1BarEs.setCpu(100);
	    sw1BarEs.setMemory(100);
	    sw1BarEs.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.bar.es
	    Server vnode1BarEs = new Server(5);
	    vnode1BarEs.setName("vnode1.bar.es");
	    vnode1BarEs.setLocation(Location.Spain);
	    vnode1BarEs.setCpu(100);
	    vnode1BarEs.setMemory(100);
	    vnode1BarEs.setDiskSpace(100);
	    vnode1BarEs.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode2.bar.es
	    Server vnode2BarEs = new Server(6);
	    vnode2BarEs.setName("vnode2.bar.es");
	    vnode2BarEs.setLocation(Location.Spain);
	    vnode2BarEs.setCpu(100);
	    vnode2BarEs.setMemory(100);
	    vnode2BarEs.setDiskSpace(100);
	    vnode2BarEs.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    *//** Ireland (HEANet) **//*
	    // sw1.dub.ie
	    SubstrateSwitch sw1DubIe = new SubstrateSwitch(7);
	    sw1DubIe.setName("sw1.dub.ie");
	    sw1DubIe.setLocation(Location.Ireland);
	    sw1DubIe.setCpu(100);
	    sw1DubIe.setMemory(100);
	    sw1DubIe.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.dub.ie
	    Server vnode1DubIe = new Server(8);
	    vnode1DubIe.setName("vnode1.dub.ie");
	    vnode1DubIe.setLocation(Location.Ireland);
	    vnode1DubIe.setCpu(100);
	    vnode1DubIe.setMemory(100);
	    vnode1DubIe.setDiskSpace(100);
	    vnode1DubIe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    *//** Greece (GRNET) **//*
	    // sw1.eie.gr
	    SubstrateSwitch sw1EieGr = new SubstrateSwitch(9);
	    sw1EieGr.setName("sw1.eie.gr");
	    sw1EieGr.setLocation(Location.Greece);
	    sw1EieGr.setCpu(100);
	    sw1EieGr.setMemory(100);
	    sw1EieGr.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // sw1.ntu.gr
	    SubstrateSwitch sw1NtuGr = new SubstrateSwitch(10);
	    sw1NtuGr.setName("sw1.ntu.gr");
	    sw1NtuGr.setLocation(Location.Greece);
	    sw1NtuGr.setCpu(100);
	    sw1NtuGr.setMemory(100);
	    sw1NtuGr.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.ntu.ie
	    Server vnode1NtuGr = new Server(11);
	    vnode1NtuGr.setName("vnode1.ntu.ie");
	    vnode1NtuGr.setLocation(Location.Greece);
	    vnode1NtuGr.setCpu(100);
	    vnode1NtuGr.setMemory(100);
	    vnode1NtuGr.setDiskSpace(100);
	    vnode1NtuGr.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    *//** Italy (GARR) **//*
	    // r1.mil.it
	    SubstrateRouter r1MilIt = new SubstrateRouter(12);
	    r1MilIt.setName("r1.mil.it");
	    r1MilIt.setLocation(Location.Italy);
	    r1MilIt.setCpu(100);
	    r1MilIt.setMemory(100);
	    r1MilIt.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // vnode1.mil.it
	    Server vnode1MilIt = new Server(13);
	    vnode1MilIt.setName("vnode1.mil.it");
	    vnode1MilIt.setLocation(Location.Italy);
	    vnode1MilIt.setCpu(100);
	    vnode1MilIt.setMemory(100);
	    vnode1MilIt.setDiskSpace(100);
	    vnode1MilIt.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode2.mil.it
	    Server vnode2MilIt = new Server(14);
	    vnode2MilIt.setName("vnode2.mil.it");
	    vnode2MilIt.setLocation(Location.Italy);
	    vnode2MilIt.setCpu(100);
	    vnode2MilIt.setMemory(100);
	    vnode2MilIt.setDiskSpace(100);
	    vnode2MilIt.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    *//** Switzerland (SWITCH) **//*
	    // sw1.gen.ch
	    SubstrateSwitch sw1GenCh = new SubstrateSwitch(15);
	    sw1GenCh.setName("sw1.gen.ch");
	    sw1GenCh.setLocation(Location.Switzerland);
	    sw1GenCh.setCpu(100);
	    sw1GenCh.setMemory(100);
	    sw1GenCh.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.gen.ch
	    Server vnode1GenCh = new Server(16);
	    vnode1GenCh.setName("vnode1.gen.ch");
	    vnode1GenCh.setLocation(Location.Switzerland);
	    vnode1GenCh.setCpu(100);
	    vnode1GenCh.setMemory(100);
	    vnode1GenCh.setDiskSpace(100);
	    vnode1GenCh.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    *//** Hungary (HUNGARNET) **//*
	    // sw1.bud.hu
	    SubstrateSwitch sw1BudHu = new SubstrateSwitch(17);
	    sw1BudHu.setName("sw1.bud.hu");
	    sw1BudHu.setLocation(Location.Hungary);
	    sw1BudHu.setCpu(100);
	    sw1BudHu.setMemory(100);
	    sw1BudHu.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.bud.hu
	    Server vnode1BudHu = new Server(18);
	    vnode1BudHu.setName("vnode1.bud.hu");
	    vnode1BudHu.setLocation(Location.Hungary);
	    vnode1BudHu.setCpu(100);
	    vnode1BudHu.setMemory(100);
	    vnode1BudHu.setDiskSpace(100);
	    vnode1BudHu.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    *//** Germany (DFN) **//*
	    // r1.erl.de
	    SubstrateRouter r1ErlDe = new SubstrateRouter(19);
	    r1ErlDe.setName("r1.erl.de");
	    r1ErlDe.setLocation(Location.Germany);
	    r1ErlDe.setCpu(100);
	    r1ErlDe.setMemory(100);
	    r1ErlDe.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    // vnode1.erl.de
	    Server vnode1ErlDe = new Server(20);
	    vnode1ErlDe.setName("vnode1.erl.de");
	    vnode1ErlDe.setLocation(Location.Germany);
	    vnode1ErlDe.setCpu(100);
	    vnode1ErlDe.setMemory(100);
	    vnode1ErlDe.setDiskSpace(100);
	    vnode1ErlDe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode2.erl.de
	    Server vnode2ErlDe = new Server(21);
	    vnode2ErlDe.setName("vnode2.erl.de");
	    vnode2ErlDe.setLocation(Location.Germany);
	    vnode2ErlDe.setCpu(100);
	    vnode2ErlDe.setMemory(100);
	    vnode2ErlDe.setDiskSpace(100);
	    vnode2ErlDe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    *//** Czech Republic (CESNET) **//*
	    // r1.pra.cz
	    SubstrateRouter r1PraCz = new SubstrateRouter(22);
	    r1PraCz.setName("r1.pra.cz");
	    r1PraCz.setLocation(Location.Czech_Republic);
	    r1PraCz.setCpu(100);
	    r1PraCz.setMemory(100);
	    r1PraCz.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);	    
	    // vnode1.pra.cz
	    Server vnode1PraCz = new Server(23);
	    vnode1PraCz.setName("vnode1.pra.cz");
	    vnode1PraCz.setLocation(Location.Czech_Republic);
	    vnode1PraCz.setCpu(100);
	    vnode1PraCz.setMemory(100);
	    vnode1PraCz.setDiskSpace(100);
	    vnode1PraCz.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    // vnode2.pra.cz
	    Server vnode2PraCz = new Server(24);
	    vnode2PraCz.setName("vnode2.pra.cz");
	    vnode2PraCz.setLocation(Location.Czech_Republic);
	    vnode2PraCz.setCpu(100);
	    vnode2PraCz.setMemory(100);
	    vnode2PraCz.setDiskSpace(100);
	    vnode2PraCz.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    *//** Poland (PSNC) **//*
	    // r1.poz.pl
	    SubstrateRouter r1PozPl = new SubstrateRouter(25);
	    r1PozPl.setName("r1.poz.pl");
	    r1PozPl.setLocation(Location.Poland);
	    r1PozPl.setCpu(100);
	    r1PozPl.setMemory(100);
	    r1PozPl.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);	
	    // vnode1.poz.pl
	    Server vnode1PozPl = new Server(26);
	    vnode1PozPl.setName("vnode1.poz.pl");
	    vnode1PozPl.setLocation(Location.Poland);
	    vnode1PozPl.setCpu(100);
	    vnode1PozPl.setMemory(100);
	    vnode1PozPl.setDiskSpace(100);
	    vnode1PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    // vnode2.poz.pl
	    Server vnode2PozPl = new Server(27);
	    vnode2PozPl.setName("vnode2.poz.pl");
	    vnode2PozPl.setLocation(Location.Poland);
	    vnode2PozPl.setCpu(100);
	    vnode2PozPl.setMemory(100);
	    vnode2PozPl.setDiskSpace(100);
	    vnode2PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // vnode3.poz.pl
	    Server vnode3PozPl = new Server(28);
	    vnode3PozPl.setName("vnode3.poz.pl");
	    vnode3PozPl.setLocation(Location.Poland);
	    vnode3PozPl.setCpu(100);
	    vnode3PozPl.setMemory(100);
	    vnode3PozPl.setDiskSpace(100);
	    vnode3PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	
	    // vnode4.poz.pl
	    Server vnode4PozPl = new Server(29);
	    vnode4PozPl.setName("vnode4.poz.pl");
	    vnode4PozPl.setLocation(Location.Poland);
	    vnode4PozPl.setCpu(100);
	    vnode4PozPl.setMemory(100);
	    vnode4PozPl.setDiskSpace(100);
	    vnode4PozPl.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	
	    *//** Sweden (KTH) **//*
	    // sw1.sth.se
	    SubstrateSwitch sw1SthSe = new SubstrateSwitch(30);
	    sw1SthSe.setName("sw1.sth.se");
	    sw1SthSe.setLocation(Location.Sweden);
	    sw1SthSe.setCpu(100);
	    sw1SthSe.setMemory(100);
	    sw1SthSe.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
	    // vnode1.sth.se
	    Server vnode1SthSe = new Server(31);
	    vnode1SthSe.setName("vnode1.sth.se");
	    vnode1SthSe.setLocation(Location.Sweden);
	    vnode1SthSe.setCpu(100);
	    vnode1SthSe.setMemory(100);
	    vnode1SthSe.setDiskSpace(100);
	    vnode1SthSe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    // vnode2.sth.se
	    Server vnode2SthSe = new Server(32);
	    vnode2SthSe.setName("vnode2.sth.se");
	    vnode2SthSe.setLocation(Location.Sweden);
	    vnode2SthSe.setCpu(100);
	    vnode2SthSe.setMemory(100);
	    vnode2SthSe.setDiskSpace(100);
	    vnode2SthSe.setVlans(SimulatorConstants.MAX_SERVER_VLANS);	    
	    
	   
	    // Adding links & interfaces
	    // sw1.lis.pt - vnode1.lis.pt
	    SubstrateLink link = new SubstrateLink(0,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1LisPt,vnode1LisPt), EdgeType.UNDIRECTED);
	    
	    // sw1.lis.pt - sw1.mad.es
	    link = new SubstrateLink(1,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1LisPt,sw1MadEs), EdgeType.UNDIRECTED);
	    
	    // vnode1.mad.es - sw1.mad.es (1)
	    link = new SubstrateLink(2,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,vnode1MadEs), EdgeType.UNDIRECTED);
	    
	    // vnode1.mad.es - sw1.mad.es (2)
	    link = new SubstrateLink(3,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,vnode1MadEs), EdgeType.UNDIRECTED);
	    
	    // vnode1.mad.es - sw1.mad.es (3)
	    link = new SubstrateLink(4,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,vnode1MadEs), EdgeType.UNDIRECTED);
	    
	    // sw1.mad.es - sw1.bar.es (1)
	    link = new SubstrateLink(5,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,sw1BarEs), EdgeType.UNDIRECTED);
	    
	    // sw1.mad.es - sw1.bar.es (2)
	    link = new SubstrateLink(6,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,sw1BarEs), EdgeType.UNDIRECTED);
	    
	    // vnode1.bar.es - sw1.bar.es
	    link = new SubstrateLink(7,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1BarEs,sw1BarEs), EdgeType.UNDIRECTED);
	    
	    // vnode2.bar.es - sw1.bar.es
	    link = new SubstrateLink(8,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode2BarEs,sw1BarEs), EdgeType.UNDIRECTED);	    
	    
	    // sw1.mad.es - sw1.ntu.gr
	    link = new SubstrateLink(9,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,sw1NtuGr), EdgeType.UNDIRECTED);
	    
	    // sw1.mad.es - sw1.dub.ie
	    link = new SubstrateLink(10,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,sw1DubIe), EdgeType.UNDIRECTED);	    

	    // sw1.mad.es - r1.mil.it
	    link = new SubstrateLink(11,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1MadEs,r1MilIt), EdgeType.UNDIRECTED);
	    
	    // sw1.ntu.gr - vnode1.ntu.gr
	    link = new SubstrateLink(12,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1NtuGr,sw1NtuGr), EdgeType.UNDIRECTED);
	    
	    // sw1.ntu.gr - r1.pra.cz
	    link = new SubstrateLink(13,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PraCz,sw1NtuGr), EdgeType.UNDIRECTED);	    
	    
	    // sw1.eie.gr - sw1.bud.hu
	    link = new SubstrateLink(14,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,sw1EieGr), EdgeType.UNDIRECTED);	    
	    
	    // sw1.bud.hu - vnode1.bud.hu (1)
	    link = new SubstrateLink(15,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,vnode1BudHu), EdgeType.UNDIRECTED);	    
	    
	    // sw1.bud.hu - vnode1.bud.hu (2)
	    link = new SubstrateLink(16,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,vnode1BudHu), EdgeType.UNDIRECTED);	
	    
	    // sw1.bud.hu - vnode1.bud.hu (3)
	    link = new SubstrateLink(17,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,vnode1BudHu), EdgeType.UNDIRECTED);	
	    
	    // sw1.bud.hu - sw1.gen.ch
	    link = new SubstrateLink(18,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1BudHu,sw1GenCh), EdgeType.UNDIRECTED);	    
	
	    // sw1.gen.ch - vnode1.gen.ch
	    link = new SubstrateLink(19,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1GenCh,sw1GenCh), EdgeType.UNDIRECTED);
	    
	    // sw1.gen.ch - r1.erl.de
	    link = new SubstrateLink(20,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,sw1GenCh), EdgeType.UNDIRECTED);	    
	    
	    // sw1.gen.ch - sw1.dub.ie
	    link = new SubstrateLink(21,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1DubIe,sw1GenCh), EdgeType.UNDIRECTED);	    
	    
	    // sw1.dub.ie - vnode1.dub.ie
	    link = new SubstrateLink(22,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1DubIe,vnode1DubIe), EdgeType.UNDIRECTED);    
	    
	    // sw1.dub.ie - sw1.sth.se
	    link = new SubstrateLink(23,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(sw1DubIe,sw1SthSe), EdgeType.UNDIRECTED);	    
	    
	    // sw1.sth.se - vnode1.sth.se
	    link = new SubstrateLink(24,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1SthSe,sw1SthSe), EdgeType.UNDIRECTED);	    
	    
	    // sw1.sth.se - vnode2.sth.se
	    link = new SubstrateLink(25,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode2SthSe,sw1SthSe), EdgeType.UNDIRECTED);	    
	    
	    // sw1.sth.se - r1.poz.pl
	    link = new SubstrateLink(26,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PozPl,sw1SthSe), EdgeType.UNDIRECTED);    
	    
	    // r1.mil.it - vnode1.mil.it
	    link = new SubstrateLink(27,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,vnode1MilIt), EdgeType.UNDIRECTED);    
	    
	    // r1.mil.it - vnode2.mil.it
	    link = new SubstrateLink(28,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,vnode2MilIt), EdgeType.UNDIRECTED); 	    
	    
	    // r1.mil.it - r1.poz.pl
	    link = new SubstrateLink(29,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,r1PozPl), EdgeType.UNDIRECTED); 
	    
	    // r1.mil.it - r1.pra.cz
	    link = new SubstrateLink(30,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1MilIt,r1PraCz), EdgeType.UNDIRECTED);
	    
	    // r1.pra.cz - vnode1.pra.cz
	    link = new SubstrateLink(31,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1PraCz,r1PraCz), EdgeType.UNDIRECTED);	    
	    
	    // r1.pra.cz - vnode2.pra.cz
	    link = new SubstrateLink(32,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode2PraCz,r1PraCz), EdgeType.UNDIRECTED);    
	    
	    // r1.pra.cz - r1.poz.pl
	    link = new SubstrateLink(33,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1PozPl,r1PraCz), EdgeType.UNDIRECTED);    
	    
	    // r1.pra.cz - r1.erl.de
	    link = new SubstrateLink(34,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,r1PraCz), EdgeType.UNDIRECTED);	    
	    
	    // r1.erl.de - vnode1.erl.de
	    link = new SubstrateLink(35,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,vnode1ErlDe), EdgeType.UNDIRECTED); 
	    
	    // r1.erl.de - vnode2.erl.de
	    link = new SubstrateLink(36,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,vnode2ErlDe), EdgeType.UNDIRECTED);   
	    
	    // r1.erl.de - r1.poz.pl
	    link = new SubstrateLink(37,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,r1PozPl), EdgeType.UNDIRECTED);	    
	    
	    // r1.poz.pl - vnode1.poz.pl
	    link = new SubstrateLink(38,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode1PozPl,r1PozPl), EdgeType.UNDIRECTED);  
	    
	    // r1.poz.pl - vnode2.poz.pl
	    link = new SubstrateLink(39,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode2PozPl,r1PozPl), EdgeType.UNDIRECTED);
	    
	    // r1.poz.pl - vnode3.poz.pl
	    link = new SubstrateLink(40,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode3PozPl,r1PozPl), EdgeType.UNDIRECTED);
	    
	    // r1.poz.pl - vnode4.poz.pl
	    link = new SubstrateLink(41,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(vnode4PozPl,r1PozPl), EdgeType.UNDIRECTED);
	    
	    // r1.erl.de - r1.mil.it
	    link = new SubstrateLink(42,100);
	    substrate.getGraph().addEdge(link,new Pair<Node>(r1ErlDe,r1MilIt), EdgeType.UNDIRECTED);
	
		return substrate;
 }
	
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      */