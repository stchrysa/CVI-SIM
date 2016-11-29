package output;

import gui.SimulatorConstants;


import java.text.DecimalFormat;
import java.util.ArrayList;
import model.Request;
import model.Substrate;
import model.components.BackupLink;
import model.components.BackupNode;
import model.components.Link;
import model.components.Node;
import model.components.Server;
import model.components.SubstrateRouter;
import model.components.VirtualMachine;

public class Results {
	private Substrate substrate;
	
	public Results (Substrate sub){
		this.substrate=sub;
	}
	
	public double Acceptance_Ratio(double denial, double current_request){
		return (current_request-denial)/current_request;
	}

	
	public double Generate_Revenue(Request req){
		double sum=0;
		for (Node x: req.getGraph().getVertices()){
				if(!(x instanceof BackupNode)){
					if (x instanceof VirtualMachine)
						sum=sum+x.getCpu()+x.getMemory()+((VirtualMachine)x).getDiskSpace();
					else {
						if (req.getProv()=="soft") sum=sum+15;
						else sum=sum+30;
					}
				}
		}
		
		for (Link current: req.getGraph().getEdges())
			if(!(current instanceof BackupLink))
				sum=sum+current.getBandwidth();
		
		return sum;
	}
	
	public double avgHops(double[][][] flow){
		double avgHops =0;
		@SuppressWarnings("unused")
		ArrayList<Integer> x= new ArrayList<Integer>();
		
		for (int k=0; k< flow.length;k++){
			int hop_count=0;
			for (int i=0; i<flow[k].length;i++) {
				for (int j=0; j<flow[k][i].length;j++){
					if (flow[k][i][j]>0){						
						hop_count++;
					}
				}
			}
			avgHops +=hop_count;
		}
		
		return avgHops/flow.length;
	}
	
	 public double Node_Cost(Request req){
		 double cost=0;
		   for (Node x: req.getGraph().getVertices()){
		     if (x instanceof VirtualMachine)
		      cost=cost+x.getCpu()+x.getMemory()+((VirtualMachine)x).getDiskSpace();
		     else{
		    	 if (req.getProv()=="soft") cost=cost+15;
				 else cost=cost+30;
		     }
		   }		  
		  return cost;
	 }
	 
	
	
	 public double Link_Cost(double[][][] flow){
		 DecimalFormat df = new DecimalFormat();
		 df.setMaximumFractionDigits(4);
		 
		 double cost=0;
		  for (int k=0;k<flow.length;k++){
			  for (int i=0;i<this.substrate.getGraph().getVertexCount();i++){
				  for (int j=0;j<this.substrate.getGraph().getVertexCount();j++){
					  if(flow[k][i][j]!=0)
					  		System.out.println(k+","+i+","+j+": "+df.format(flow[k][i][j])+"\t");
					  cost=cost+flow[k][i][j];
				  }
				 // System.out.println("");
			  }
		  }  
		  System.out.println("linkCost: "+cost);
		  return cost;
	 }
	 
	 public double Cost_Embedding(double[][][] flow, Request req){
		 double cost=0;
		 
		 cost+=Link_Cost(flow);
		  
		  if (cost!=0){
		   cost+=Node_Cost(req);
		  }
		  
		  return cost;
	 }
	 
	 //The total backup resources cost to to working resource costs
	 public double Working_Cost(double[][][] flow,int reqLinksNum, Request req){
		 double working_cost=0;
		  for (int k=0;k<reqLinksNum;k++){
		   for (int i=0;i<this.substrate.getGraph().getVertexCount();i++){
		    for (int j=0;j<this.substrate.getGraph().getVertexCount();j++){
		     working_cost=working_cost+flow[k][i][j];
		    }
		   }
		  }
		  System.out.println("woring link cost: "+working_cost);
		  double node_working_cost = 0;
		  if (working_cost!=0){
		   for (Node x: req.getGraph().getVertices()){
			 if(!(x instanceof BackupNode)){
			     if (x instanceof VirtualMachine){
			    	working_cost=working_cost+x.getCpu()+x.getMemory()+((VirtualMachine)x).getDiskSpace();
			     	node_working_cost=node_working_cost+x.getCpu()+x.getMemory()+((VirtualMachine)x).getDiskSpace();
			     }
			     else{
			    	 if (req.getProv()=="soft"){
			    		 working_cost=working_cost+15;
			    		 node_working_cost=node_working_cost+15;
			    	 }
					 else{
						 working_cost=working_cost+30;
						 node_working_cost=node_working_cost+30;
					 }
			     }
			   }
		   }
		  }
		  System.out.println("woring node cost: "+node_working_cost);
		  return working_cost;
	 }
	
	public double[] Node_utilization_Server_Cpu(double[] initial){
		double[] n_util=new double[initial.length];
			
		for (Node x: this.substrate.getGraph().getVertices()){
			if (x instanceof Server){
					n_util[x.getId()]=1-(x.getCpu()/initial[x.getId()]);
			}
		}
		return n_util;
	}
	
	public double[] Node_utilization_Server_Memory(double[] initial){
		double[] n_util=new double[initial.length];
				
		for (Node x: this.substrate.getGraph().getVertices()){
			if (x instanceof Server){
					n_util[x.getId()]=1-(x.getMemory()/initial[x.getId()]);
			}
		}
		return n_util;
	}
	
	public double[] Node_utilization_Server_DiskSpace(double[] initial){
		double[] n_util=new double[initial.length];
				
		for (Node x: this.substrate.getGraph().getVertices()){
			if (x instanceof Server){
					n_util[x.getId()]=1-(((Server)x).getDiskSpace()/initial[x.getId()]);
			}
		}
		return n_util;
	}
	
	public double[] Node_utilization_Router(){
		double[] n_util=new double[this.substrate.getGraph().getVertexCount()];
				
		for (Node x: this.substrate.getGraph().getVertices()){
			if (x instanceof SubstrateRouter){
					n_util[x.getId()]=1-( (double)((SubstrateRouter)x).getLogicalInstances()/(double)SimulatorConstants.MAX_LOGICAL_INSTANCES);
			}
		}
		return n_util;
	}
	
	
	public double[] Link_utilization(double[] initial){
		double[] l_util=new double[initial.length];
		
		for (Link current: this.substrate.getGraph().getEdges()){
			l_util[current.getId()]= 1-((double) current.getBandwidth()/initial[current.getId()]);
		}
		return l_util;
	}
	
	
	
}
