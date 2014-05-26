package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

import edu.uci.ics.jung.graph.Graph;

import tools.NodeComparator;

import model.components.Link;
import model.components.Node;
import model.components.RequestRouter;
import model.components.RequestSwitch;
import model.components.Server;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import model.components.VirtualMachine;

public class GreedyNodeMapping {
		
	 @SuppressWarnings("unchecked")
	public static LinkedHashMap<Node, Node>  GND (Request req, Substrate sub2, double[][] capTable ) {
		// node mapping hash-map to be filled
	    LinkedHashMap<Node, Node> nodeMapping = new LinkedHashMap<Node, Node>(); // request-real
		 
		 
		//get list of the substrate nodes (sorted)
		ArrayList<Node> tmpSubN = new ArrayList<Node>();
		tmpSubN = 	(ArrayList<Node>) getNodes(sub2.getGraph());
		Collections.sort(tmpSubN,new NodeComparator());
	
		//get list of the requested nodes (sorted)
		ArrayList<Node> tmpReqN = new ArrayList<Node>();
		tmpReqN = 	(ArrayList<Node>) getNodes(req.getGraph());
		Collections.sort(tmpReqN,new NodeComparator());
		     	
    	double[] average =  createCosts(tmpSubN,tmpReqN, capTable);
    	
        //////////////////////////////////////////////////////////////////////////////////
		//Build arrays for the different functional characteristics (e.g. cpu, memory , disk for each servers ///
		////////////////////////////////////////////////////////////////////////////////
		int[][] Vcost= getCapacities(tmpReqN);
		int[][] Scost = getCapacities(tmpSubN);
		String[][] VNFunc = getNFunctional(tmpReqN);
		String[][] SNFunc = getNFunctional(tmpSubN);

        			
		 for (Node i : tmpReqN){
			    double max=0;
			 	int counter2=0;
			 	int counter3=0;
			 	Node selected=null;
			 	//System.out.println();
				if ((i instanceof VirtualMachine) ){
					int functional_types  = 3;
					int non_functional_types=2;
					for (Node x2: sub2.getGraph().getVertices()){
						
						//System.out.println("Checking out VM: "+i.getId()+ " for Server: "+x2.getId());
						if (x2 instanceof Server){
							counter2=0;
							counter3=0;
							//check that capacity constraints are satisfied for every functional type in the node.
							for (int j=0;j<functional_types;j++){
								if (Scost[j][x2.getId()]>=Vcost[j][i.getId()]){
									counter2++;
								}
							}
							for (int j=0;j<non_functional_types;j++){
								if (SNFunc[j][x2.getId()]==VNFunc[j][i.getId()]) counter3++;
							}
							
							// if capacity constraints are satisfied check if it has the biggest average
							if ((counter2==functional_types)&&(counter3==non_functional_types)){
								//System.out.println("Server: "+x2.getId()+" has average capacity of: "+average[x2.getId()]);
								if (average[x2.getId()]>max){
									max=average[x2.getId()];
									selected=x2;
								}	
							}
						}
					}
				}
				else if ((i instanceof RequestRouter) ){
					int non_functional_types = 2;
					for (Node x2: (sub2.getGraph()).getVertices()){
						counter3=0;
						if (x2 instanceof SubstrateRouter){
							for (int j=0;j<non_functional_types;j++){
								if (SNFunc[j][x2.getId()]==VNFunc[j][i.getId()]) counter3++;
							}
							//check that capacity constraints are satisfied for the node.
							if ((((SubstrateRouter) x2).getLogicalInstances()>0)&&(counter3==non_functional_types)){
									if (average[x2.getId()]> max){
										max=average[x2.getId()];
										selected=x2;
									}
							}
						}
					}
				}
				//update mapping and remove from pool of available substrate resources
				if (selected!=null){
					nodeMapping.put(i,selected);
					sub2.getGraph().removeVertex(selected);
				}
				else {
					// break out of node mapping phase
					break;
				}
			 
		 }
		 
		 if (nodeMapping.size()==req.getGraph().getVertexCount()) 
			 return nodeMapping;
		 else 		 
			 return null;
	 }
	 
	 public static double[] createCosts (ArrayList<Node> tmpSubN ,ArrayList<Node> tmpReqN, double[][] capTable){
		// number of nodes
			int initSubNodeNum = tmpSubN.size();
			
			double[] average=new double[initSubNodeNum];
			
		//Find the average of every substrate node regarding available resources
			for (int i=0;i<initSubNodeNum;i++){
				if (tmpSubN.get(i) instanceof Server){
					average[i]=((Server)tmpSubN.get(i)).getCpu()+((Server)tmpSubN.get(i)).getMemory()+((Server)tmpSubN.get(i)).getDiskSpace();
				}
				if (tmpSubN.get(i) instanceof SubstrateRouter){
					average[i]=((SubstrateRouter)tmpSubN.get(i)).getLogicalInstances();
				}
			}
			
			//apart form node capacity take into consideration link capacity of links leaving the node
			//Multiply the capacities with sum of adjacent substrate links of every substrate node
			double[] adj_bandwidth=new double[initSubNodeNum];
			for (int i=0;i<initSubNodeNum;i++){
				for (int j=0;j<initSubNodeNum;j++){
					if (capTable[i][j]!=0){
						adj_bandwidth[i]=adj_bandwidth[i]+capTable[i][j];
					}
				}
				average[i]=average[i]*adj_bandwidth[i];
			
			}
			return average;
	 }
	 	 
		
 
		public static int[][] getCapacities (@SuppressWarnings("rawtypes") ArrayList list){
			int[][] tmp =  new int[3][list.size()];

			for (int i=0;i<list.size();i++){  
			if ((list.get(i) instanceof VirtualMachine) ){
				tmp[0][i]= ((VirtualMachine)list.get(i)).getCpu();
				tmp[1][i]=((VirtualMachine)list.get(i)).getMemory();
				tmp[2][i]=((VirtualMachine)list.get(i)).getDiskSpace();
			}
			if ((list.get(i) instanceof RequestRouter) ){
				tmp[0][i]=1;
				}
			if ((list.get(i) instanceof RequestSwitch) ){
				tmp[0][i]=((RequestSwitch)list.get(i)).getCpu();
				tmp[1][i]=((RequestSwitch)list.get(i)).getMemory();
				}
			if ((list.get(i) instanceof Server) ){
				tmp[0][i]= ((Server)list.get(i)).getCpu();
				tmp[1][i]=((Server)list.get(i)).getMemory();
				tmp[2][i]=((Server)list.get(i)).getDiskSpace();
			}
			if ((list.get(i) instanceof SubstrateRouter) ){
				tmp[0][i]=((SubstrateRouter)list.get(i)).getLogicalInstances();
				}
			if ((list.get(i) instanceof SubstrateSwitch) ){
				tmp[0][i]=((SubstrateSwitch)list.get(i)).getCpu();
				tmp[1][i]=((SubstrateSwitch)list.get(i)).getMemory();
				}

			}

			return tmp;
		}
		
		public static String[][] getNFunctional (@SuppressWarnings("rawtypes") ArrayList list){
			String[][] tmp =  new String[2][list.size()];

			for (int i=0;i<list.size();i++){  
				tmp[0][i]= ((Node)list.get(i)).getOS();
				tmp[1][i]=((Node)list.get(i)).getVEType();
			}

			return tmp;
		}
		
		public static Collection<Node> getNodes(Graph<Node,Link> t) {
			ArrayList<Node> reqNodes =new ArrayList<Node>();
			@SuppressWarnings("unused")
			Collection<Link> edges =  t.getEdges();
			
			for (Node x: t.getVertices())
				reqNodes.add(x);


			return reqNodes;
		}
}
