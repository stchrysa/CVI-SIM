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
import gui.SimulatorConstants;
import model.Algorithm;
import model.ER;
import model.ERreq;
import model.NetworkGraph;
import model.Request;
import model.RequestLinkFactory;
import model.RequestNodeFactory;
import model.ResourceMapping;
import model.Simulation;
import model.Substrate;
import model.SubstrateLinkFactory;
import model.SubstrateNodeFactory;
import output.Results;
import model.components.Link;
import model.components.Node;
import model.components.Server;
import model.components.SubstrateRouter;
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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainWithoutGUIfull {
	
	private static Random mrandom = new Random(SimulatorConstants.RANDOM_SEED);
	private static Random grandom = new Random(SimulatorConstants.RANDOM_SEED);

	public static void main(String[] args) {
		
		//Number of experiments to execute
	    int experiments=1;
	   

		for (int i =0; i<experiments; i++){
			
			//Create the substrate network
			Substrate substrate=new Substrate("sub");
			substrate= createSubstrateGraph(substrate);
//			
//			for (Link link: substrate.getGraph().getEdges()){
//				System.out.println(link.getId()+" capacity "+link.getBandwidth());
//			}
			
			//Create a bulk of requests
			List<Request> request_tab = new ArrayList<Request>();
			request_tab =randomRequestGeneration();
			
//			for(int j=0; j<request_tab.size(); j++){
//				for (Link link: request_tab.get(j).getGraph().getEdges()){			
//					System.out.println(link.getId()+" capacity "+link.getBandwidth());
//				}
//			}
						
			//Run the NCM algorithm
			//Algorithm algorithm = new Algorithm("NCM");
			//Algorithm algorithm = new Algorithm("E");
			//Algorithm algorithm = new Algorithm("twoStepSVNE");
			Algorithm algorithm = new Algorithm("oneStepSVNEdisjPaths2");
			Simulation simulation = new Simulation(substrate, request_tab, algorithm);
			launchLaunchSimulation(simulation, i);

			//Clean the requests from mappings
			for (Request x: request_tab){
				x.setRMap(new ResourceMapping(x));
			}
			
			//Run the G-MCF algorithm
			algorithm = new Algorithm("twoStepSVNE");
			simulation = new Simulation(substrate, request_tab, algorithm);
			launchLaunchSimulation(simulation, i);
	

			final String username = "dummymailforrepot@gmail.com";
	        final String password = "simulation";

	        Properties props = new Properties();
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
			          new javax.mail.Authenticator() {
			            protected PasswordAuthentication getPasswordAuthentication() {
			                return new PasswordAuthentication(username, password);
			            }
			          });
			
			 try {

		            Message message = new MimeMessage(session);
		            message.setFrom(new InternetAddress("dummymailforrepot@gmail.com"));
		            message.setRecipients(Message.RecipientType.TO,
		                InternetAddress.parse("stchrysa@hotmail.com"));
		            message.setSubject("[SVNE Report] DELL"+ algorithm.getId());
		            message.setText("Done :-)");

		            //Transport.send(message);
		            //System.out.println("e-mail sent");
		        } catch (MessagingException e) {
		            throw new RuntimeException(e);
		        }
			
		}
	}

	@SuppressWarnings("unused")
	private static void launchLaunchSimulation(Simulation simulation, int inD) {
		
		  try
		    {
			  //Extract the result in a xls file
			  Calendar calendar = Calendar.getInstance();
			  java.util.Date now = calendar.getTime();
			  java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			  String name =  currentTimestamp.toString();
			  String[] out = name.split(" ");
			  String[] out1 = out[1].split(":");
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
				      l= new Label (1,0,"Acceptance",cf);
				      s.addCell(l);
				      l=new Label (2,0,"Revenue",cf);
				      s.addCell(l);
				      l=new Label (3,0,"Cost",cf);
				      s.addCell(l);
				      l=new Label (4,0,"Node Cost",cf);
				      s.addCell(l);
				      l=new Label (5,0,"Link Cost",cf);
				      s.addCell(l);
				      l=new Label (6,0,"Working Cost",cf);
				      s.addCell(l);
				      l=new Label (7,0,"Hops", cf);
				      s.addCell(l);
				      l=new Label (8,0,"NServer_Util_Cpu",cf);
				      s.addCell(l);
				      l=new Label (9,0,"NServer_Util_Memory",cf);
				      s.addCell(l);
				      l=new Label (10,0,"NServer_Util_DiskSpace",cf);
				      s.addCell(l);
				      l=new Label (11,0,"NRouter_Util",cf);
				      s.addCell(l);
				      l=new Label (12,0,"L_Util",cf);
				      s.addCell(l);
				      l=new Label (13,0,"Backups",cf);
				      s.addCell(l);
				      l=new Label (14,0,"StressNS",cf);
				      s.addCell(l);
				      l=new Label (15,0,"StressR",cf);
				      s.addCell(l);
				      l=new Label (16,0,"StressL",cf);
				      s.addCell(l);
				      l=new Label (17,0,"Soft",cf);
				      s.addCell(l);
				      l=new Label (18,0,"Hard",cf);
				      s.addCell(l);
				      l=new Label (19,0,"2",cf);
				      s.addCell(l);
				      l=new Label (20,0,"3",cf);
				      s.addCell(l);
				      l=new Label (21,0,"4",cf);
				      s.addCell(l);
				      l=new Label (22,0,"5",cf);
				      s.addCell(l);
				      l=new Label (23,0,"6",cf);
				      s.addCell(l);
				      l=new Label (24,0,"7",cf);
				      s.addCell(l);
				      l=new Label (25,0,"8",cf);
				      s.addCell(l);
				      l=new Label (26,0,"9",cf);
				      s.addCell(l);
				      l=new Label (27,0,"10",cf);
				      s.addCell(l);
				      l=new Label (28,1,"Cost",cf);
				      s.addCell(l);
				      l=new Label (28,2,"Node Cost",cf);
				      s.addCell(l);
				      l=new Label (28,3,"Link Cost",cf);
				      s.addCell(l);
				      l=new Label (28,4,"Working Cost",cf);
				      s.addCell(l);
				      l=new Label (28,5,"Time",cf);
				      s.addCell(l);
				      l=new Label (29,0,"2Nodes",cf);
				      s.addCell(l);
				      l=new Label (30,0,"3Nodes",cf);
				      s.addCell(l);
				      l=new Label (31,0,"4Nodes",cf);
				      s.addCell(l);
				      l=new Label (32,0,"5Nodes",cf);
				      s.addCell(l);
				      l=new Label (33,0,"6Nodes",cf);
				      s.addCell(l);
				      l=new Label (34,0,"7Nodes",cf);
				      s.addCell(l);
				      l=new Label (35,0,"8Nodes",cf);
				      s.addCell(l);
				      l=new Label (36,0,"9Nodes",cf);
				      s.addCell(l);
				      l=new Label (37,0,"10Nodes",cf);
				      s.addCell(l);
				      l=new Label (38, 0, "Av Costs");
				      s.addCell(l);
				      l=new Label (39, 0, "Av Node Costs");
				      s.addCell(l);
				      l=new Label (40, 0, "Av Link Costs");
				      s.addCell(l);
				      l=new Label (41, 0, "Av Working Cost");
				      s.addCell(l);
				      l=new Label (42, 0, "Av Time");
				      s.addCell(l);
				      l=new Label (43, 0, "Total Av Time");
				      s.addCell(l);
				      
		int denials = 0;
		double cost=0;
		double node_cost=0;
		double link_cost=0;
		double working_cost=0;
		double backups = 0;
	    double revenue=0;
	    double avgHops =0;
	    double algo_den=0;
	    double[] sizes=new double[9];
	    double[] costs=new double[9];
	    double[] node_costs=new double[9];
	    double[] link_costs=new double[9];
	    double[] working_costs=new double[9];
	    double[] time=new double[9];
	    int totallinks=0;
	    int current_request=0;
	    int soft=0;
	    int hard=0;
	    int avTime=0;
	    
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
	    }
	    
	    double[] l_initial=new double[substrate.getGraph().getEdgeCount()];
	    
	    for (Link current: substrate.getGraph().getEdges()){
	    	l_initial[current.getId()]=current.getBandwidth();
	    }
/////////////////////////////////////////////////////////////////////
	    
	    Results results=new Results(substrate);
	    int counter2=0;
	    //substrate.print();
	        for (int i=0; i<simulationTime+1; i++) {
	        
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
      				costs[startingRequests.get(ind).getGraph().getVertexCount()-2]+=retres[ind][1];
      				node_cost+=retres[ind][10];
      				node_costs[startingRequests.get(ind).getGraph().getVertexCount()-2]+=retres[ind][10];
      				link_cost+=retres[ind][11];
      				link_costs[startingRequests.get(ind).getGraph().getVertexCount()-2]+=retres[ind][11];
      				working_cost+=retres[ind][12];
      				working_costs[startingRequests.get(ind).getGraph().getVertexCount()-2]+=retres[ind][12];
      				backups += retres[ind][13];
      				avgHops +=retres[ind][2];
      				algo_den +=retres[ind][3];
      				soft+=retres[ind][7];
      				hard+=retres[ind][8];
      				avTime+=retres[ind][9];
      				time[startingRequests.get(ind).getGraph().getVertexCount()-2]+=retres[ind][9];
       			}
      			
      			for (Request req:startingRequests){
      				if (!req.getRMap().isDenied()){
      					revenue=revenue+results.Generate_Revenue(req);
      					sizes[req.getGraph().getVertexCount()-2]++;
      				}
          				current_request++;
          				
          				totallinks+=req.getGraph().getEdgeCount();
          				
          				System.out.println("cost: "+cost);
          				System.out.println("working cost: "+working_cost);
          				System.out.println("node cost: "+node_cost);
          				System.out.println("link cost: "+link_cost);
          				System.out.println("hops: "+avgHops);
          				System.out.println("denials: "+denials);
          				System.out.println("current request: "+current_request);
      			}
    
          	//Take results every 100 time units
	            if ((i%100)==0){
	            	counter2++;
	            	double[] nServer1_util=results.Node_utilization_Server_Cpu(n_initial_Cpu);
	            	double[] nServer2_util=results.Node_utilization_Server_Memory(n_initial_Memory);
	            	double[] nServer3_util=results.Node_utilization_Server_DiskSpace(n_initial_DiskSpace);
	            	double[] nRouter1_util=results.Node_utilization_Router();
	            	double[] l_util=results.Link_utilization(l_initial);
	            	double nServer1_average=0;
	            	double nServer2_average=0;
	            	double nServer3_average=0;
	            	double nRouter1_average=0;
	            	double l_average=0;
	            	for (int j=0;j<substrate.getGraph().getVertexCount();j++){
	            		nServer1_average += nServer1_util[j];
	            		nServer2_average += nServer2_util[j];
	            		nServer3_average += nServer3_util[j];
	            		nRouter1_average += nRouter1_util[j];
	            	}
	            	for (int j=0;j<substrate.getGraph().getEdgeCount();j++){
	            		if (l_util[j]<1000000000)
	            		l_average=l_average+l_util[j];
	            	}
	            	
	            	Number n = new Number(0,counter2,i);
	                s.addCell(n);
	                Number n1 = new Number(1,counter2,results.Acceptance_Ratio(denials, current_request));
	                s.addCell(n1);
	                Number n2 = new Number(2,counter2,revenue/i);
	                s.addCell(n2);
	                Number n3 = new Number(3,counter2,cost/(current_request-denials));
	                s.addCell(n3);
	                Number n4 = new Number(4,counter2,node_cost/(current_request-denials));
	                s.addCell(n4);
	                Number n5 = new Number(5,counter2,link_cost/(current_request-denials));
	                s.addCell(n5);
	                Number n6 = new Number(6,counter2,working_cost/(current_request-denials));
	                s.addCell(n6);
	            	Number n7 = new Number(7,counter2,avgHops/(current_request-denials));
	                s.addCell(n7);
	                Number n8 = new Number(8,counter2,(nServer1_average/servers));
	                s.addCell(n8);
	                Number n9 = new Number(9,counter2,(nServer2_average/servers));
	                s.addCell(n9);
	                Number n10 = new Number(10,counter2,(nServer3_average/servers));
	                s.addCell(n10);
	                Number n11 = new Number(11,counter2,(nRouter1_average/routers));
	                s.addCell(n11);
	                Number n12 = new Number(12,counter2,(l_average/substrate.getGraph().getEdgeCount()));
	            	s.addCell(n12);
	            	Number n13 = new Number(13,counter2,backups/(current_request-denials));
	            	s.addCell(n13);

	               Number n17 = new Number(17, counter2, soft);
	               s.addCell(n17);
	               Number n18 = new Number(18, counter2, hard);
	               s.addCell(n18);
	              
	               
	            }
	        }
	        
	        for (int i=0;i<9;i++){
	        	Number n19 = new Number (19+i, 1, sizes[i] );
	        	s.addCell(n19);
	        	Number n291 = new Number (29+i, 1, costs[i]);
	        	s.addCell(n291);
	        	Number n292 = new Number (29+i, 2, node_costs[i]);
	        	s.addCell(n292);
	        	Number n293 = new Number (29+i, 3, link_costs[i]);
	        	s.addCell(n293);
	        	Number n294 = new Number (29+i, 4, working_costs[i]);
	        	s.addCell(n294);
	        	Number n295 = new Number (29+i, 5, time[i]);
	        	s.addCell(n295);
	        }
	        
	        
	        for (int i=0;i<9;i++){
	        	Number n38 = new Number (38,1+i, (double) costs[i]/sizes[i]);
	        	s.addCell(n38);
	        }
	        
	        for (int i=0;i<9;i++){
	        	Number n39 = new Number (39,1+i, (double) node_costs[i]/sizes[i]);
	        	s.addCell(n39);
	        }
	        
	        for (int i=0;i<9;i++){
	        	Number n40 = new Number (40,1+i, (double) link_costs[i]/sizes[i]);
	        	s.addCell(n40);
	        }
	        
	        for (int i=0;i<9;i++){
	        	Number n41 = new Number (41,1+i, (double) working_costs[i]/sizes[i]);
	        	s.addCell(n41);
	        }
	        
	        for (int i=0;i<9;i++){
	        	Number n42 = new Number (42,1+i, (double) time[i]/sizes[i]);
	        	s.addCell(n42);
	        }
	        
	        Number n43 = new Number (43,1, (double) avTime/(requests.size()-denials));
        	s.addCell(n43);
	        
			System.out.println();
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
	
	


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<Request> randomRequestGeneration() {
		final String prefix ="req";
		//Number of Requests
		final int numRequests = 1;
		//Range of nodes within a request
		final int minNodes =3;
		final int maxNodes= 3;
		final int minLinks=1;
		final int maxLinks=25;
		final String timeDistribution = SimulatorConstants.POISSON_DISTRIBUTION;
		final String linkConnectivity = SimulatorConstants.PERCENTAGE_CONNECTIVITY;
		final double linkProbability= 0.3;
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
		          	RandomEngine engine = new DRand(SimulatorConstants.RANDOM_SEED);
		            exp_arr = new Exponential(0.04,engine);
		        	exp= new Exponential(0.001,engine);
	        	}

        	
				for (int i=0; i<numRequests; i++) {
						Request request = new Request(prefix+i);			
						
						// Random num of nodes inside range (minNodes-maxNodes)
						int numNodes = minNodes + (int)(mrandom.nextDouble()*((maxNodes - minNodes) + 1));
						// Random num of links inside range (minLinks-maxLinks)
						int numLinks = minLinks + (int)(mrandom.nextDouble()*((maxLinks - minLinks) + 1));
						//lifetime of the request
						lifetime = exp.nextInt();
						
						System.out.println("requested nodes = "+numNodes);
						
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
							ERreq<Node, Link> randomGraph = new ERreq<Node, Link>(graphFactory, nodeFactory, linkFactory, numNodes, linkProbability);
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
								startDate = uniformMin + (int)(mrandom.nextDouble()*((uniformMax - uniformMin) + 1));
								request.setStartDate(startDate);
								request.setEndDate(startDate+lifetime);
							}
							
							// Random: Normal distribution
							if (timeDistribution.equals(SimulatorConstants.NORMAL_DISTRIBUTION)) {
								//Random random = new Random();
								startDate = (int) (normalMean + grandom.nextGaussian() * normalVariance);
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
							System.out.println("request has "+request.getGraph().getVertexCount());
						}
					}
				}
				
		//System.exit(0);
        return requests;
     
	}
	
	
 @SuppressWarnings({ "rawtypes", "unchecked" })
public static Substrate createSubstrateGraph(Substrate substrate){
		int numNodes = 10;
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
		//ErdosRenyiGenerator<Node, Link> randomGraph = new ErdosRenyiGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, numNodes, linkProbability );
		ER<Node, Link> randomGraph = new ER<Node, Link>(graphFactory, nodeFactory, linkFactory, numNodes, linkProbability);
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
		
		if  ((g.getVertexCount()==numNodes)){
			
			substrate.setGraph(g);
			substrate.setNodeFactory(nodeFactory);
			substrate.setLinkFactory(linkFactory);
			System.out.println("substrate ok");
			return substrate;
		}
		else{
			createSubstrateGraph(substrate);
		}
	
		return substrate;
 }
	
}