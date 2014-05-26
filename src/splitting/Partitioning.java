package splitting;

import model.Substrate;
import model.Request;
import model.components.Link;
import model.components.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;


public class Partitioning {
	
	private List<Substrate> substrates=null;
	private Request req=null;
	private Substrate InPs = null;
	private String split=null;
	
	
	public Partitioning(Substrate InPs, List<Substrate> substrates, Request req, String split){
		this.substrates=substrates;
		this.req=req;
		this.InPs=InPs;
		this.split=split;
	}
	
	public double[] PerfromPartitioning(){
		
		//Returns denial, allocation in one substrate, allocation in multiple substrates, partitioning cost, partitioning time
		double[] ret_res = new double[5];
		double part_cost=0;
		double part_time=0;
		
		
		// Number of virtual nodes
		int reqNodeNum=req.getGraph().getVertexCount();
		
		//Number of substrate providers
		int inp_no=InPs.getGraph().getVertexCount();

		//Compute splitting cost
		cost costos=new cost(req, substrates);
		//[inp_no][reqNodeNum] cost of leasing a specific virtual node in each InP
		double[][] nodeCost=costos.NodeCost();
		//[inp_no][inp_no][reqNodeNum][reqNodeNum] cost of leasing intra/inter
		//links within/between InP(s)
		double[][][][] linkCost=costos.LinkCost(InPs);
		
		PartitioningAlgorithms split=new PartitioningAlgorithms(nodeCost, linkCost, req, inp_no);
		
		//Partitioning_Algorithms split=new Partitioning_Algorithms(node_Cost, link_Cost, req, inp_no);
		//splitting provided by the Exact algorithm, and computing time needed
		int[] nodeMapping=new int[reqNodeNum];

		//Execution time start and stop timing
		long strt=0;
		long stp=0;

		if (this.split.contains("Exact")){
			strt=System.currentTimeMillis();
			nodeMapping=split.Exact();
			stp=System.currentTimeMillis();
		}
		else{
			strt=System.currentTimeMillis();
			nodeMapping=split.ILS();
			stp=System.currentTimeMillis();
		}
		
		

		double[][] demand = getBandTable(req.getGraph());
		part_cost=costos.SplitCost(reqNodeNum, nodeCost, linkCost, nodeMapping, demand);
		part_time=(stp-strt);
		
		//Check if the mapping is possible if cost has a reasonable value and there are enough resources
		if (part_cost>20000000){
			return null;
		}
		/*if ( part_cost<2000000){
			int[][] match=costos.getMatch();
			//Find identical virtual nodes within the request
			ArrayList<ArrayList<Integer>> same=getSame(req);
			//Find possible for each virtual node in each InP
			ArrayList<ArrayList<ArrayList<Integer>>> MatcH=costos.getMatcH();

			nodeMapping=Check(nodeMapping,match,same,nodeCost,linkCost, req,reqNodeNum,inp_no,this.split);
		}
		else{
			break;
		}*/
		

		//if embedding is impossible reject the request 
		if (nodeMapping==null){
			//denial
			return null;
		}else{
			int counter=0;
			for (int j=0;j<inp_no;j++){
				for (int i=0;i<reqNodeNum;i++){
					if (nodeMapping[i]==j){
						counter++;
						break;
					}
				}
			}
			if (counter==1){
				//	Request has been allocated in only one substrate
				ret_res[1]=1;
			}
			else{
				//Request has been partitioned to multiple substrate providers
				ret_res[2]=1;
			}
		}
		
		ret_res[3]=part_cost;
		ret_res[4]=part_time;


		// 	Construct the subVNs
		List<Request> sub_request = new ArrayList<Request>();
		SubVNs svns=new SubVNs(reqNodeNum, nodeMapping, substrates, req);
		sub_request=svns.SUBVNS();

		//Assign the subVNs in the initial request
		req.setSubReq(sub_request);

		return ret_res;
	}
	
	public static double[][] getBandTable(Graph<Node,Link> t) {
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


}
