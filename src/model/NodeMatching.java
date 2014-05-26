package model;

import java.util.ArrayList;

import model.components.Node;

public class NodeMatching {
	private Request request;
	private Substrate substrate;
	
	public NodeMatching (Request req, Substrate sub){
		this.request=req;
		this.substrate=sub;
	}
	
	public ArrayList<ArrayList<Integer>> findMatching(){
		ArrayList<ArrayList<Integer>> tmp=new ArrayList<ArrayList<Integer>>();
		
		for (int i=0;i <request.getGraph().getVertexCount();i++)
			 tmp.add(new ArrayList<Integer>());
		
		for (Node r:request.getGraph().getVertices()){
			for (Node x:substrate.getGraph().getVertices()){
				if ((r.getOS()==x.getOS())&&(r.getNetStack()==x.getNetStack())&&(r.getLocation()==x.getLocation())&&(r.getLinkType()==x.getLinkType())&&(r.getVEType()==x.getVEType())&&(r.getConnectivity()==x.getConnectivity())){
					tmp.get(r.getId()).add(x.getId());
				}
			}
		}
		
		return tmp;
	}

}
