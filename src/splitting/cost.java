package splitting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

import model.Request;
import model.Substrate;
import model.components.Link;
import model.components.SubstrateLink;
import model.components.Node;
import model.components.RequestRouter;
import model.components.Server;
import model.components.SubstrateRouter;
import model.components.VirtualMachine;

public class cost {
	
	protected Request req=null;
	protected List<Substrate> substrates=null;
	protected double[][] demands=null;
	protected int inp_no=0;
	protected int reqNodeNum=0;
	
	
	public cost(Request req,List<Substrate> substrates){
		this.req=req;
		this.substrates=substrates;
		this.inp_no=substrates.size();
		this.reqNodeNum=req.getGraph().getVertexCount();
		this.demands = getBandTable(req.getGraph());		
	}
	
	
	public double[][] NodeCost(){
		double[][] nodeCost=new double[inp_no][reqNodeNum];
		int[][] match=new int[inp_no][reqNodeNum];
		
		//for each virtual node register the type, operating system, and virt. environment
		String[][] requestnodes=new String[reqNodeNum][3];
		for (Node node:req.getGraph().getVertices()){
			requestnodes[node.getId()][0]=node.getType();
			requestnodes[node.getId()][1]=node.getOS();
			requestnodes[node.getId()][2]=node.getVEType();
		}
		
		int subnum=0;
		//0 for server (cpu+mem+disk), 1 for router(log instances)
		double[][] averagenode=new double[inp_no][2];

		for (Substrate sub:substrates){
			String[][] substratenodes =new String[sub.getGraph().getVertexCount()][3];
			
			double average_server=0;
			double average_router=0;

			for (Node node:sub.getGraph().getVertices()){
				//for each substrate node register the type, operating system, and virt. environment
				substratenodes[node.getId()][0]=node.getType();
				substratenodes[node.getId()][1]=node.getOS();
				substratenodes[node.getId()][2]=node.getVEType();
				if (node instanceof Server){
					average_server+=node.getCpu()+node.getMemory()+((Server) node).getDiskSpace();
				}
				if (node instanceof SubstrateRouter){
					average_router += ((SubstrateRouter) node).getLogicalInstances();
				}
			}
			averagenode[subnum][0]=average_server;
			averagenode[subnum][1]=average_router;
						
			//find the candidate substrate nodes for each virtual node in this substrate
			//that match the functional attributes
			for (Node node:req.getGraph().getVertices()){
				for (Node snode:sub.getGraph().getVertices()){
					if (requestnodes[node.getId()][0]==substratenodes[snode.getId()][0]){
						if (requestnodes[node.getId()][1]==substratenodes[snode.getId()][1]){
							if (requestnodes[node.getId()][2]==substratenodes[snode.getId()][2]){
								match[subnum][node.getId()]+=1;
							}
						}
					}
				}
			}
			subnum++;	
		}
		
		//0 for available resources in servers, 1 for routers
		double[][] availablenode=new double[inp_no][2];
		
		double[][] initial=Initial(substrates);
		
		for (int i=0;i<inp_no;i++){
			availablenode[i][0]=100*(1-(initial[i][0]-averagenode[i][0])/initial[i][0]);
			availablenode[i][1]=100*(1-(initial[i][1]-averagenode[i][1])/initial[i][1]);
		}

		//the more the availability and the more the number of matches, the less the cost
		for (int i=0;i<inp_no;i++){
			for (Node node:req.getGraph().getVertices()){
				if (match[i][node.getId()]!=0){
					if (node instanceof VirtualMachine)
						nodeCost[i][node.getId()]=1/(availablenode[i][0]*match[i][node.getId()]);
					else if (node instanceof RequestRouter)
						nodeCost[i][node.getId()]=1/(availablenode[i][1]*match[i][node.getId()]);
				}
				else
					nodeCost[i][node.getId()]=Integer.MAX_VALUE;
			}		
		}
		
	
		return nodeCost;
	}

	
	/*public LinkedHashMap<String, Double> Node_Cost(){
		
		//For each virtual node find the cost in each InP  vm0: [0.25, 0.1, 0.3, 0.4] 
		LinkedHashMap<String, Double> nodeCost = new LinkedHashMap<String, Double>();
		
		// (Average Utilization / Scarcity)
		for (Node node : req.getGraph().getVertices()){
			ArrayList<Double> node_cost=new ArrayList<Double>();
			for (Substrate sub: substrates){
				double scarcity=0;
				double utilization=0;
				for (Node vertex: sub.getGraph().getVertices()){
					if ((node.getType()==vertex.getType())&&(node.getOS()==vertex.getOS())&&(node.getVEType()==vertex.getVEType())){
						scarcity++;
						if (vertex instanceof Server){
							double initial_availability= vertex.getAvailableCpu()+vertex.getAvailableMemory()+((Server)vertex).getAvailableDiskSpace();
							double current_availability= vertex.getCpu()+vertex.getMemory()+((Server)vertex).getDiskSpace();
							utilization += 1 - ( (double) current_availability/(double) initial_availability);
						}
						else{
							double initial_availability= ((SubstrateRouter)vertex).getAvailableLogicalInstances();
							double current_availability= ((SubstrateRouter)vertex).getLogicalInstances();
							utilization += 1 - ( (double) current_availability/(double) initial_availability);
						}
					}
				}
			
								
				
			
							
				double total_cost = Integer.MAX_VALUE;
				if (scarcity!=0){
					double average_utilization = utilization/scarcity;
					total_cost = average_utilization/scarcity;
				}
				
								
				String key = node.getName()+"_"+sub.getId();
				nodeCost.put(key, total_cost);			
			}
			
		}		
		return nodeCost;
	}*/
	
	/*public LinkedHashMap<String, Double> Link_Cost(Substrate InPs){
		LinkedHashMap<String, Double> linkCost= new LinkedHashMap<String, Double>();
		
		//Intra Link Cost
		for (Link link: req.getGraph().getEdges()){
			for (Substrate sub: substrates){
				double scarcity=0;
				double utilization=0;
				for (Link edge: sub.getGraph().getEdges()){
					if (link.getLinkType()==edge.getLinkType()){
						scarcity++;
						double initial_availability = ((SubstrateLink) edge).getAvailableBandwidth();
						double current_availability = ((SubstrateLink) edge).getBandwidth();
						utilization += 1 - ( (double) current_availability/(double) initial_availability);
					}
				}
				String key = link.getName()+"_"+sub.getId()+"_"+sub.getId();
				double total_cost = Integer.MAX_VALUE;
				if (scarcity!=0){
					double average_utilization = utilization/scarcity;
					total_cost = 1000*average_utilization/scarcity;
				}
				
			
				
				linkCost.put(key, total_cost);			
				
			}
		}
		
		
		//Inter Link Cost
		for (Link link: req.getGraph().getEdges()){
			for (Substrate sub1: substrates){
				double scarcity1=0;
				double utilization1=0;
				for (Link edge1: sub1.getGraph().getEdges()){
					if (link.getLinkType()==edge1.getLinkType()){
						scarcity1++;
						double initial_availability = ((SubstrateLink) edge1).getAvailableBandwidth();
						double current_availability = ((SubstrateLink) edge1).getBandwidth();
						utilization1 += 1 - ( (double) current_availability/(double) initial_availability);
					}
				}
				double average_utilization1 = Integer.MAX_VALUE;
				if (scarcity1!=0){
					average_utilization1=utilization1/scarcity1;
				}
				
								
				for (Substrate sub2 : substrates){
					if (sub1.getId()!= sub2.getId()){
						double scarcity2=0;
						double utilization2=0;
						for (Link edge2: sub2.getGraph().getEdges()){
							if (link.getLinkType()==edge2.getLinkType()){
								scarcity2++;
								double initial_availability = ((SubstrateLink) edge2).getAvailableBandwidth();
								double current_availability = ((SubstrateLink) edge2).getBandwidth();
								utilization2 += 1 - ( (double) current_availability/(double) initial_availability);
							}
						}
						double average_utilization2 = Integer.MAX_VALUE;
						if (scarcity2!=0){
							average_utilization2=utilization2/scarcity2;
						}
						
						double inter_utilization = (average_utilization1+average_utilization2)/2;
						double inter_scarcity = Math.max(scarcity1, scarcity2);
						
						double total_cost=Integer.MAX_VALUE;
						if (inter_scarcity!=0){
							total_cost=inter_utilization/inter_scarcity;
						}
						
						//Include the penalty of using an inter link
						double total_cost1 = Integer.MAX_VALUE;
						double total_cost2 = Integer.MAX_VALUE;
						if (scarcity1!=0) total_cost1= 1000*average_utilization1/scarcity1;
						if (scarcity2!=0) total_cost2=1000*average_utilization2/scarcity2;
						
						
						int transit_links= Inter_Hops(InPs, sub1, sub2);
						
						total_cost = total_cost + 1.2*Math.max(total_cost1, total_cost2)*transit_links; 
						
						String key= link.getName()+"_"+sub1.getId()+"_"+sub2.getId();
						
						linkCost.put(key, total_cost);
						
					}
				}
			}
		}
		
		
		return linkCost;
	}*/
	
	
	public double[][][][] LinkCost(Substrate InPs){
		
		double[][][][] linkCost=new double[inp_no][inp_no][reqNodeNum][reqNodeNum];
		
		//the names to be changed 
		double costvlan = 0;
		double costsonet=0;
		double cost80211=0;
		
		//contains the type of the virtual link
		String[] requestlinks=new String[req.getGraph().getEdgeCount()];
		for (Link link: req.getGraph().getEdges()){
			requestlinks[link.getId()]=link.getLinkType();
		}
		
		//normalize linkCost according to nodeCost
		int[][] match=getMatch();
		int max=0;
		for (int i=0;i<inp_no;i++){
			for (int j=0;j<reqNodeNum;j++){
				if (match[i][j]>max) max=match[i][j];
			}
		}

		int subnum=0;
		double[] averagelink=new double[inp_no];
		for (Substrate sub:substrates){		
			
			//contains the type of the substrate link
			String[] substratelinks=new String[sub.getGraph().getEdgeCount()];
			for (Link link:sub.getGraph().getEdges()){
				substratelinks[link.getId()]=link.getLinkType();
				averagelink[subnum]+=link.getBandwidth();
			}
			
			// Set the link cost comparable to node cost
			costvlan = (double)1/((int)(1+Math.random()*max));
			costsonet=(double)1/((int) (1+Math.random()*max));
			cost80211=(double)1/((int) (1+Math.random()*max));

			//set the linkcost
			for (Link link:req.getGraph().getEdges()){
				Pair<Node> x =  req.getGraph().getEndpoints(link);
				int i=x.getFirst().getId();
				int j=x.getSecond().getId();
				if (link.getLinkType()=="VLAN"){
					linkCost[subnum][subnum][i][j]+=costvlan;
					linkCost[subnum][subnum][j][i]+=costvlan;
				}
				if (link.getLinkType()=="SONET"){
					linkCost[subnum][subnum][i][j]+=costsonet;
					linkCost[subnum][subnum][j][i]+=costsonet;
				}
				if (link.getLinkType()=="EIGHT0211"){
					linkCost[subnum][subnum][i][j]+=cost80211;
					linkCost[subnum][subnum][j][i]+=cost80211;
				}
			}
			
			subnum++;	
		}
		
		double[] availablelink=new double[inp_no];
		double[][] initial = Initial(substrates);
		
		for (int i=0;i<inp_no;i++){
			availablelink[i]=100*(1-(initial[i][2]-averagelink[i])/initial[i][2]);
		}
		
		//The cost of leasing an inter-link is 20% higher than the cost of leasing an intra link
		double penalty=1.2*maximum(costvlan, costsonet, cost80211);
		
		int[][] hopsinp = InterHops(InPs);
				
		
		//set the link cost
		for (int i=0;i<inp_no;i++){
			for (int j=0;j<inp_no;j++){
				for (int k=0;k<reqNodeNum;k++){
					for (int l=0;l<reqNodeNum;l++){
						if (demands[k][l]!=0){
							if (i!=j)
								if (hopsinp[i][j]==2){
									linkCost[i][j][k][l]= Math.max(linkCost[i][i][k][l], linkCost[j][j][k][l])+2*penalty;
								}
								else
									linkCost[i][j][k][l]= Math.max(linkCost[i][i][k][l], linkCost[j][j][k][l])+penalty;
						}
						else linkCost[i][j][k][l]= Integer.MAX_VALUE;
					}
				}
			}
		}
		
		for (int i=0;i<inp_no;i++){
			for (int j=0;j<inp_no;j++){
				for (int k=0;k<reqNodeNum;k++){
					for (int l=0;l<reqNodeNum;l++){
						if (demands[k][l]!=0){
							if (i==j)
								linkCost[i][j][k][l]= linkCost[i][j][k][l]/(availablelink[i]);
							else
								linkCost[i][j][k][l]=linkCost[i][j][k][l]/(((availablelink[i]+availablelink[j])/2));
						
						}
					}
				}
			}
		}
		
		
		return linkCost;
	}
	

	
	//Returns the candidate nodes in every InP for each virtual node
	public ArrayList<ArrayList<ArrayList<Integer>>> getMatcH(){
		
		ArrayList<ArrayList<ArrayList<Integer>>> MatcH = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		for (int i=0;i<inp_no;i++){
			MatcH.add(new ArrayList<ArrayList<Integer>>());
		}
		
		for (int i=0;i<inp_no;i++){
			for (int j=0;j<reqNodeNum;j++){
				MatcH.get(i).add(new ArrayList<Integer>());
			}
		}
		
		
		String[][] requestnodes=new String[reqNodeNum][3];
		for (Node node:req.getGraph().getVertices()){
			requestnodes[node.getId()][0]=node.getType();
			requestnodes[node.getId()][1]=node.getOS();
			requestnodes[node.getId()][2]=node.getVEType();
		}
		
		int subnum=0;
		for (Substrate sub:substrates){
			String[][] substratenodes =new String[sub.getGraph().getVertexCount()][3];
			
			for (Node node:sub.getGraph().getVertices()){
				substratenodes[node.getId()][0]=node.getType();
				substratenodes[node.getId()][1]=node.getOS();
				substratenodes[node.getId()][2]=node.getVEType();
			}

			
			for (Node node:req.getGraph().getVertices()){
				for (Node snode:sub.getGraph().getVertices()){
					if (requestnodes[node.getId()][0]==substratenodes[snode.getId()][0]){
						if (requestnodes[node.getId()][1]==substratenodes[snode.getId()][1]){
							if (requestnodes[node.getId()][2]==substratenodes[snode.getId()][2]){
								MatcH.get(subnum).get(node.getId()).add(snode.getId());
							}
						}
					}
				}
			}
			subnum++;	
		}
		
		
		
		return MatcH;
	}
	
	//contains the number of matches in every InP for each virtual nodes
	public int[][] getMatch(){
		int[][] match=new int[inp_no][reqNodeNum];
		
		String[][] requestnodes=new String[reqNodeNum][3];
		for (Node node:req.getGraph().getVertices()){
			requestnodes[node.getId()][0]=node.getType();
			requestnodes[node.getId()][1]=node.getOS();
			requestnodes[node.getId()][2]=node.getVEType();
		}
		
		int subnum=0;
		for (Substrate sub:substrates){
			String[][] substratenodes =new String[sub.getGraph().getVertexCount()][3];
			
			for (Node node:sub.getGraph().getVertices()){
				substratenodes[node.getId()][0]=node.getType();
				substratenodes[node.getId()][1]=node.getOS();
				substratenodes[node.getId()][2]=node.getVEType();
			}

			
			for (Node node:req.getGraph().getVertices()){
				for (Node snode:sub.getGraph().getVertices()){
					if (requestnodes[node.getId()][0]==substratenodes[snode.getId()][0]){
						if (requestnodes[node.getId()][1]==substratenodes[snode.getId()][1]){
							if (requestnodes[node.getId()][2]==substratenodes[snode.getId()][2]){
								match[subnum][node.getId()]+=1;
							}
						}
					}
				}
			}
			subnum++;	
		}
		return match;
	}
	
	private double[][] getBandTable(Graph<Node,Link> t) {
		int num = t.getVertexCount();
		//Map<Pair<Node>, Double> table = new LinkedHashMap<Pair<Node>, Double>();
		Collection<Link> edges =  t.getEdges();
		double[][] tab =  new double[num][num];
		
		for (Link current : edges){
			Pair<Node> currentNodes =t.getEndpoints(current);
			int node1 = currentNodes.getFirst().getId();
			int node2= currentNodes.getSecond().getId();
			double cap = current.getBandwidth();
			tab[node1][node2]=cap;
			tab[node2][node1]=-cap;
			//table.put(currentNodes, cap);
		}
		
		return tab;
	}
	
	private double maximum(double x, double y, double z){
		
		if ((x>=y)&&(x>=z)) return x;
		else if ((y>=x)&&(y>=z)) return y;
		else if ((z>=x)&&(z>=y)) return z;
		else return 0;
		
	}
	
	//Compute the cost of splitting
	 public double SplitCost(int reqNodeNum, double[][] nodeCost, double[][][][] linkCost, int[] nodeMapping, double[][] demands){
			double Exact_cost=0;
			for (int q=0;q<reqNodeNum;q++){
				Exact_cost+=nodeCost[nodeMapping[q]][q];
			}
			
			for (int q=0;q<reqNodeNum;q++){
				for (int w=q;w<reqNodeNum;w++){
					if (demands[q][w]!=0){
						Exact_cost+=linkCost[nodeMapping[q]][nodeMapping[w]][q][w];
					}
				}
			}
			
			return Exact_cost;
	 }
	 
	 
	 public double[][] Initial (List<Substrate> substrates){
	 
	 int substrate_num=0;
	 //0 for server, 1 for router, 2 for link
	 double [][] ret=new double[substrates.size()][3];
	 for (Substrate substrate:substrates){
		 int initial_server=0;
		 int initial_router=0;
		 for (Node x: substrate.getGraph().getVertices()){
			if (x instanceof Server)
		 		initial_server=initial_server+x.getAvailableCpu()+x.getAvailableMemory()+((Server) x).getAvailableDiskSpace();
		 	else if (x instanceof SubstrateRouter)
		 		//Each router has 15 logical instances
		 		initial_router=initial_router+15;
		}
		 
		int initial_link=0;
		    
		for (Link current: substrate.getGraph().getEdges()){
		   	initial_link+= ((SubstrateLink) current).getAvailableBandwidth();
		}
		
		ret[substrate_num][0]=initial_server;
		ret[substrate_num][1]=initial_router;
		ret[substrate_num][2]=initial_link;
		substrate_num++;
		   
	 }
	 
	 return ret;
 
 }
	 

 public int Inter_Hops(Substrate InPs, Substrate sub1, Substrate sub2){
	 int inter_hops=2;
	 
	 
	 for (Link link:InPs.getGraph().getEdges()){
		 Pair<Node> pair = InPs.getGraph().getEndpoints(link);
		 if (((pair.getFirst().getName().contains(sub1.getId()))&&(pair.getSecond().getName().contains(sub2.getId()))) ||
		 	((pair.getFirst().getName().contains(sub2.getId()))&&(pair.getSecond().getName().contains(sub1.getId())))) {
			 inter_hops =1;
		 }
	 }
	 
	 return inter_hops;
 }
	 
	 //Finds path length between InPs
	 public int[][] InterHops(Substrate InPs){
		 
		 int[][] hops = new int[InPs.getGraph().getVertexCount()][InPs.getGraph().getVertexCount()];
		 
		 for (Link link: InPs.getGraph().getEdges()){
			 Pair<Node> pair = InPs.getGraph().getEndpoints(link);
			 hops[pair.getFirst().getId()][pair.getSecond().getId()]=1;
			 hops[pair.getSecond().getId()][pair.getFirst().getId()]=1;
		 }
		 
		 for (int i=0;i<InPs.getGraph().getVertexCount();i++){
			 for (int j=0;j<InPs.getGraph().getVertexCount();j++){
				 if ((i!=j)&&(hops[i][j]==0)){
					 hops[i][j]=2;
					 hops[j][i]=2;
				 }
			 }
		 }
		 
		 return hops;
		 
	 }


}
