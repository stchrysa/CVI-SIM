package model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.util.Pair;

import model.components.Link;
import model.components.Node;
import model.components.Path;
import model.components.RequestRouter;
import model.components.RequestSwitch;
import model.components.Server;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import model.components.VirtualMachine;

public class ResourceMapping {
	@SuppressWarnings("unused")
	private Request request;
	private LinkedHashMap<Node, Node> nodeMap=null; //request-real 
	private LinkedHashMap<Link, List<Path>> flows;
	@SuppressWarnings("unused")
	private int aug =0;
	private boolean denied=false;
	
	public ResourceMapping(Request req){
		this.request=req;
	}

	public void setNodeMapping(LinkedHashMap<Node, Node> map){
		this.nodeMap=map;
	}
	
	public void setLinkMapping(LinkedHashMap<Link, List<Path>> f){
		this.flows=f;
	}
	
	public void denied() { this.denied=true; }
	public void accepted() { this.denied=false; }
	
	public boolean isDenied() { return this.denied;}
	public void setAugmented() { this.aug=1;}
	
	@SuppressWarnings("rawtypes")
	public void reserveNodes(Substrate sub){
		/*Collection v = sub.getGraph().getVertices();
		Iterator itr_sub = v.iterator();
		while(itr_sub.hasNext()){*/
		for (Node subNode: sub.getGraph().getVertices()){
			//Node subNode =  (Node) itr_sub.next();
			int aug_ID=  subNode.getId();
			//if (this.aug==1)  aug_ID += this.request.getGraph().getVertexCount();

			Collection c = this.nodeMap.entrySet();
			Iterator itr = c.iterator();

			while(itr.hasNext()){
				 Map.Entry entry = (Map.Entry)itr.next();
				 if(aug_ID == ((Node)entry.getValue()).getId()) {
				 	if (((Node)entry.getKey()) instanceof VirtualMachine)  {
				 		((Server)subNode).setCpu( ((Server)entry.getValue()).getCpu()-((VirtualMachine)entry.getKey()).getCpu());
				 		 ((Server)subNode).setMemory( ((Server)entry.getValue()).getMemory()-((VirtualMachine)entry.getKey()).getMemory());
				    	 ((Server)subNode).setDiskSpace( ((Server)entry.getValue()).getDiskSpace()-((VirtualMachine)entry.getKey()).getDiskSpace());
				    	 ((Server)subNode).setStress( ((Server)entry.getValue()).getStress()+1);
			   	 	}
				    else if ((((Node)entry.getKey()) instanceof RequestRouter)){
				    	subNode.setStress(subNode.getStress()+1);
				    	((SubstrateRouter)subNode).setLogicalInstances(((SubstrateRouter)subNode).getLogicalInstances()-1);
				    	 	}
				    else if ((((Node)entry.getKey()) instanceof RequestSwitch)) {
				    	subNode.setStress(subNode.getStress()+1);
		    	 	}
				 }
			}
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public void releaseNodes(Substrate sub){
		Collection v = sub.getGraph().getVertices();
		Iterator itr_sub = v.iterator();
		while(itr_sub.hasNext()){
			Node subNode =  (Node) itr_sub.next();
			int aug_ID=  subNode.getId();
			if(this.nodeMap!=null) {
			
			Collection c = this.nodeMap.entrySet();
			Iterator itr = c.iterator();

			while(itr.hasNext()){
				 Map.Entry entry = (Map.Entry)itr.next();
				 if(aug_ID == ((Node)entry.getValue()).getId()) {
				 	if (((Node)entry.getKey()) instanceof VirtualMachine  )  {
				    	 ((Server)subNode).setCpu( ((Server)subNode).getCpu()+((VirtualMachine)entry.getKey()).getCpu());
				    	 ((Server)subNode).setMemory( ((Server)subNode).getMemory()+((VirtualMachine)entry.getKey()).getMemory());
				    	 ((Server)subNode).setDiskSpace( ((Server)subNode).getDiskSpace()+((VirtualMachine)entry.getKey()).getDiskSpace());
				    	 subNode.setStress(subNode.getStress()-1);
			   	 	}
				    else if ((((Node)entry.getKey()) instanceof RequestRouter) ){
				    	subNode.setStress(subNode.getStress()-1);
				    	((SubstrateRouter)subNode).setLogicalInstances(((SubstrateRouter)subNode).getLogicalInstances()+1);
				    	 	}
				    else if ((((Node)entry.getKey()) instanceof RequestSwitch) ) {
				    	subNode.setStress(subNode.getStress()-1);
				   	}
				 }
			}
			
			
		
			}
		}

	}
	
	public void reserveLinks(Substrate sub){
		
		if (this.flows!=null){
			for (Link key: flows.keySet()){
				for (Path path: (List<Path>) flows.get(key)){
					for (Link link : path.getSubstrateLinks()){
						for (Link edge: sub.getGraph().getEdges()){
							if (link.getName()==edge.getName()){
								edge.setBandwidth(edge.getBandwidth()-path.getBandwidth());
								edge.setStress(edge.getStress()+1);
							}
						}		
					}									
				}				
			}
		}		
	}
	
	
	
	public void releaseLinks(Substrate sub){
		
		if (this.nodeMap!=null){
			if (this.flows!=null){
				for (Link key: flows.keySet()){
					for (Path path: (List<Path>) flows.get(key)){
						for (Link link : path.getSubstrateLinks()){
							for (Link edge: sub.getGraph().getEdges()){
								if (link.getName()==edge.getName()){
									edge.setBandwidth(edge.getBandwidth()+path.getBandwidth());
									edge.setStress(edge.getStress()-1);
								}
							}		
						}									
					}				
				}
			}		
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public double computeCost(Substrate sub){
		
		double cost=0;
		for (Link key: flows.keySet()){
			for (Path path: (List<Path>) flows.get(key)){
				for (Link link: path.getSubstrateLinks()){
					for (Link edge: sub.getGraph().getEdges()){
						if (link.getName()==edge.getName()){
							cost += path.getBandwidth();
						}
					}
				}
			}
		}
		
		Collection v = sub.getGraph().getVertices();
		Iterator itr_sub = v.iterator();
		while(itr_sub.hasNext()){
			Node subNode =  (Node) itr_sub.next();
			int aug_ID=  subNode.getId();

			Collection c = this.nodeMap.entrySet();
			Iterator itr = c.iterator();

			while(itr.hasNext()){
				 Map.Entry entry = (Map.Entry)itr.next();
				 if(aug_ID == ((Node)entry.getValue()).getId()) {
				 	if (((Node)entry.getKey()) instanceof VirtualMachine)  {
				 		cost+=((VirtualMachine) entry.getKey()).getCpu()+((VirtualMachine)entry.getKey()).getMemory()+((VirtualMachine)entry.getKey()).getDiskSpace();
			   	 	}
				    else if ((((Node)entry.getKey()) instanceof RequestRouter)){
				    	cost+= ((SubstrateRouter) subNode).getStress();
		    	 	}
				    else if ((((Node)entry.getKey()) instanceof RequestSwitch)) {
				    	cost+= ((SubstrateSwitch) subNode).getStress();
				    }
				 }
			}		
		}		
		return cost;
	}
	
	
	@SuppressWarnings("rawtypes")
	void printNodeMapping(){
		Collection c = this.nodeMap.entrySet();
		Iterator itr = c.iterator();
		while(itr.hasNext()){
			 Map.Entry entry = (Map.Entry)itr.next();
			 
			 System.out.println("Virtual " +((Node)entry.getKey()).getId()+  " cpu  " +((Node)entry.getKey()).getCpu() + 
					 " Real " + ((Node) entry.getValue()).getId() + " cpu "  + ((Node) entry.getValue()).getCpu());
		}
	}

	void printLinkMapping(Substrate sub){
		
		for (Link key: this.flows.keySet()){
			System.out.println("For Virtual Link "+ key.getName());
			for (Path path: (List<Path>) this.flows.get(key)){
				for (Link link: path.getSubstrateLinks()){
					Pair<Node> pair = sub.getGraph().getEndpoints(link);
					System.out.println(link.getName()+":"+pair.getFirst().getName()+"->"+pair.getSecond().getName()+" bw: "+path.getBandwidth());
				}
			}
		}	

	}
}


