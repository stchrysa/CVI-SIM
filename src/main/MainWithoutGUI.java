package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cern.jet.random.Exponential;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;

import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

import gui.SimulatorConstants;

import model.Algorithm;
import model.NetworkGraph;
import model.Request;
import model.RequestLinkFactory;
import model.RequestNodeFactory;
import model.Simulation;
import model.Substrate;
import output.Results;
import model.components.Link;
import model.components.Node;
import model.components.RequestLink;
import model.components.Server;
import model.components.SubstrateLink;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import model.components.VirtualMachine;

import java.util.HashSet;
import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.Graph;


import java.io.*;
import jxl.*;
import java.util.*;
import jxl.Workbook;
import jxl.write.Number;

import jxl.write.*;

public class MainWithoutGUI {

	public static void main(String[] args) {
	    
	    Substrate substrate = new Substrate("InPs");
	    
	    Node node0 = new Node(0);
	    Node node1 = new Node(1);
	    Link l1 = new Link(0, 2000);
	    substrate.getGraph().addEdge(l1, node0, node1, EdgeType.UNDIRECTED);
	    
	    
	    Algorithm algorithm = new Algorithm("NCM-Exact");
	    
	    List<Substrate> substrates = new ArrayList<Substrate>();
	    
	    Substrate sub0=new Substrate("sub0");
	    Substrate sub1=new Substrate("sub1");
  
	 // Hardcoded Substrates
	    // Server0
	    Server server0 = new Server(0);
	    server0.setCpu(8);
	    server0.setMemory(32);
	    server0.setDiskSpace(240);
	    server0.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server1
	    Server server1 = new Server(3);
	    server1.setCpu(8);
	    server1.setMemory(16);
	    server1.setDiskSpace(652);
	    server1.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server2
	    Server server2 = new Server(5);
	    server2.setCpu(8);
	    server2.setMemory(16);
	    server2.setDiskSpace(670);
	    server2.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	  
	    
	    // Router1
	    SubstrateRouter sr1 = new SubstrateRouter(1);
	    sr1.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    ((SubstrateRouter) sr1).setLogicalInstances(15);
	    // Router2
	    SubstrateRouter sr2 = new SubstrateRouter(2);
	    sr2.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    ((SubstrateRouter) sr2).setLogicalInstances(15);
	    // Router3
	    SubstrateRouter sr3 = new SubstrateRouter(4);
	    sr3.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    ((SubstrateRouter) sr3).setLogicalInstances(15);

	    // Links
	   
	    SubstrateLink sl1 = new SubstrateLink(0,2000);
	    SubstrateLink sl2 = new SubstrateLink(1,1000);
	    SubstrateLink sl3 = new SubstrateLink(2,2000);
	    SubstrateLink sl4 = new SubstrateLink(3,1000);
	    SubstrateLink sl5 = new SubstrateLink(4,1000);
	    SubstrateLink sl6 = new SubstrateLink(5,2000);

	    
	    // Adding links
	    sub0.getGraph().addEdge(sl1,server0, sr1, EdgeType.UNDIRECTED);
	    sub0.getGraph().addEdge(sl2,sr1, sr2, EdgeType.UNDIRECTED);
	    sub0.getGraph().addEdge(sl3,sr2, server1, EdgeType.UNDIRECTED);
	    sub0.getGraph().addEdge(sl4,sr1, sr3, EdgeType.UNDIRECTED);
	    sub0.getGraph().addEdge(sl5,sr2, sr3, EdgeType.UNDIRECTED);
	    sub0.getGraph().addEdge(sl6,sr3, server2, EdgeType.UNDIRECTED);
	    
	    // Hardcoded Substrate
	    // Server0
	    Server server10 = new Server(0);
	    server10.setCpu(8);
	    server10.setMemory(32);
	    server10.setDiskSpace(240);
	    server10.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server1
	    Server server11 = new Server(3);
	    server11.setCpu(8);
	    server11.setMemory(16);
	    server11.setDiskSpace(652);
	    server11.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server2
	    Server server12 = new Server(5);
	    server12.setCpu(8);
	    server12.setMemory(16);
	    server12.setDiskSpace(670);
	    server12.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	  
	    
	    // Router1
	    SubstrateRouter sr11 = new SubstrateRouter(1);
	    sr1.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    ((SubstrateRouter) sr1).setLogicalInstances(15);
	    // Router2
	    SubstrateRouter sr12 = new SubstrateRouter(2);
	    sr2.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    ((SubstrateRouter) sr2).setLogicalInstances(15);
	    // Router3
	    SubstrateRouter sr13 = new SubstrateRouter(4);
	    sr3.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
	    ((SubstrateRouter) sr3).setLogicalInstances(15);

	    // Links
	   
	    SubstrateLink sl11 = new SubstrateLink(0,2000);
	    SubstrateLink sl12 = new SubstrateLink(1,1000);
	    SubstrateLink sl13 = new SubstrateLink(2,2000);
	    SubstrateLink sl14 = new SubstrateLink(3,1000);
	    SubstrateLink sl15 = new SubstrateLink(4,1000);
	    SubstrateLink sl16 = new SubstrateLink(5,2000);

	    
	    // Adding links
	    sub1.getGraph().addEdge(sl11,server10, sr11, EdgeType.UNDIRECTED);
	    sub1.getGraph().addEdge(sl12,sr11, sr12, EdgeType.UNDIRECTED);
	    sub1.getGraph().addEdge(sl13,sr12, server11, EdgeType.UNDIRECTED);
	    sub1.getGraph().addEdge(sl14,sr11, sr13, EdgeType.UNDIRECTED);
	    sub1.getGraph().addEdge(sl15,sr12, sr13, EdgeType.UNDIRECTED);
	    sub1.getGraph().addEdge(sl16,sr13, server12, EdgeType.UNDIRECTED);
	    
	    
	    //Request
	    Request req= new Request("REq");
	    
	    // Server0
	    VirtualMachine vm0 = new VirtualMachine(0);
	    vm0.setCpu(4);
	    vm0.setMemory(8);
	    vm0.setDiskSpace(50);
	    vm0.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    // Server1
	    VirtualMachine vm1 = new VirtualMachine(1);
	    vm1.setCpu(4);
	    vm1.setMemory(8);
	    vm1.setDiskSpace(50);
	    vm1.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
	    
	    RequestLink vl1 = new RequestLink(0,500);
	    
	    req.getGraph().addEdge(vl1,vm0, vm1, EdgeType.UNDIRECTED);
	    req.setStartDate(0);
	    req.setEndDate(50);
	    
	    // Creating simulation
	    List<Request> request_tab = new ArrayList<Request>();
	    request_tab.add(req);
	    //request_tab =randomRequestGeneration();
	    // Creating simulation
	    Simulation simulation = new Simulation(substrate, substrates, request_tab, algorithm);
	    launchLaunchSimulation(simulation);
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
		      String filename = "input"+out[0]+"_"+out1[0]+"-"+out1[1]+".xls";
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
				      l=new Label (4,0,"NServer_Util_Cpu",cf);
				      s.addCell(l);
				      l=new Label (5,0,"NServer_Util_Memory",cf);
				      s.addCell(l);
				      l=new Label (6,0,"NServer_Util_DiskSpace",cf);
				      s.addCell(l);
				      l=new Label (7,0,"NRouter_Util",cf);
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
		Substrate substrate = simulation.getInPs();
		List<Substrate> substrates = simulation.getSubstrates();
		Algorithm algorithm = simulation.getAlgorithm();
		List<Request> endingRequests;
	    List<Request> startingRequests;
	    algorithm.addSubstrate(substrates);
	    algorithm.addInPs(substrate);
	    
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
	            	@SuppressWarnings("static-access")
					double[] nServer1_util=results.Node_utilization_Server_Cpu(substrates);
	            	double[] nServer2_util=results.Node_utilization_Server_Memory(substrates);
	            	double[] nServer3_util=results.Node_utilization_Server_DiskSpace(substrates);
	            	double[] l_util=results.Link_utilization(substrates);
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
	            	}
	            	for (int j=0;j<substrate.getGraph().getEdgeCount();j++)
	            		l_average=l_average+l_util[j];
	            	
	            	Number n = new Number(0,counter2,i);
	                s.addCell(n);
	                Number n1 = new Number(1,counter2,results.Acceptance_Ratio(denials, current_request));
	                s.addCell(n1);
	                Number n2 = new Number(2,counter2,revenue/i);
	                s.addCell(n2);
	                Number n3 = new Number(3,counter2,cost/current_request);
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
	            	Number n12 = new Number(12,counter2,avgHops/current_request);
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
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private static List<Request> randomRequestGeneration() {
		final String prefix ="req";
		 final int numRequests = 1;
		 final int minNodes = 2;
		 final int maxNodes=10;
		 final int minLinks=1;
		 final int maxLinks=1;
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
  

	/*private static List<Request> randomRequestGeneration() {
		String prefix ="req";
		 int numRequests = 300;
		 int minNodes = 2;
		 int maxNodes=10;
		 int minLinks=1;
		 int maxLinks=1;
		 String timeDistribution = SimulatorConstants.POISSON_DISTRIBUTION;
		 int fixStart=0;
		 int uniformMin=0;
		 int uniformMax=0;
		 int normalMean=0;
		 int normalVariance=0;
		 double poissonMean = 0.04;
		 int lifetimeMin=1;
		 int lifetimeMax=10;
		List<Request> requests = new ArrayList<Request>();
		Factory<Graph<Node, Link>> graphFactory;
		RequestNodeFactory nodeFactory;
		RequestLinkFactory linkFactory;

        	int startDate = 0;
        	int lifetime = 0;
        	int sum=0;
        	
        	Poisson poisson = null;
        	Exponential exp_arr = null;
        	Exponential exp = null;
        	if (timeDistribution.equals(SimulatorConstants.POISSON_DISTRIBUTION)) {
	          	RandomEngine engine = new DRand();
	            exp_arr = new Exponential(25,engine);
	        	poisson = new Poisson(25, engine);
	        	exp= new Exponential(0.001,engine);
        	}

			for (int i=0; i<numRequests; i++) {
				Request request = new Request(prefix+i);			
				
				//Random Graph Generation
				graphFactory = new Factory<Graph<Node, Link>>() {
					public Graph<Node,Link> create() {
						return new SparseMultigraph<Node, Link>();
					}
				};
				
				// Random num of nodes inside range (minNodes-maxNodes)
				int numNodes = minNodes + (int)(Math.random()*((maxNodes - minNodes) + 1));
				// Random num of links inside range (minLinks-maxLinks)
				int numLinks = minLinks + (int)(Math.random()*((maxLinks - minLinks) + 1));
				
				//Barabasi-Albert generation
				nodeFactory = new RequestNodeFactory();
				linkFactory = new RequestLinkFactory();
				BarabasiAlbertGenerator<Node, Link> randomGraph = new BarabasiAlbertGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, 1, numLinks, new HashSet<Node>());
				randomGraph.evolveGraph(numNodes-1);
				SparseMultigraph<Node, Link> g = (SparseMultigraph<Node, Link>) randomGraph.create();

				//EppsteinPowerLaw Generator
				// This generator can create unconnected graphs
//				EppsteinPowerLawGenerator<Node, Link> randomGraph = new EppsteinPowerLawGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, numNodes, numLinks, 1);
//				SparseMultigraph<Node, Link> g = (SparseMultigraph<Node, Link>) randomGraph.create();
//				System.out.println("Generated random graph: "+g.toString());
				
				request.setGraph(g);
				request.setNodeFactory(nodeFactory);
				request.setLinkFactory(linkFactory);
				
				// Random start/end dates

				// Duration of the request
				
				//	lifetime = lifetimeMin + (int)(Math.random()*((lifetimeMax - lifetimeMin) + 1));
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
					startDate=poisson.nextInt();
					sum=sum+startDate;
					if (sum==sum+lifetime)
						lifetime=lifetime+1;
					request.setStartDate(sum);
					request.setEndDate(sum+lifetime);
				}
			
				// Domain
				// Not defined
				
				requests.add(request);
	
			}
			return requests;
}
*/	
	

	
}
