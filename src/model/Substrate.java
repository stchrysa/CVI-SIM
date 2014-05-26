package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import tools.LinkComparator;
import tools.NodeComparator;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

import model.components.*;

/**
 * Substrate Class. Subclass of Network.
 */
public class Substrate extends Network {
	
    /** Creates a new instance of Substrate */
    public Substrate(String id) {
    	super(id);
    	nodeFactory = new SubstrateNodeFactory();
    	linkFactory = new SubstrateLinkFactory();
    }
    
    public Object getCopy() {
    	Substrate s = new Substrate(this.getId());
    	s.state = this.state;
    	s.nodeFactory = (SubstrateNodeFactory) ((SubstrateNodeFactory) this.nodeFactory).getCopy();
    	s.linkFactory = (SubstrateLinkFactory) ((SubstrateLinkFactory) this.linkFactory).getCopy();
    	s.graph = ((NetworkGraph) this.graph).getCopy();
    	s.graphLayout = this.graphLayout;
    	return s;
    }
    
    @SuppressWarnings("unchecked")
	public void print(){
		ArrayList<Node> nodes =(ArrayList<Node>)getNodes(this.getGraph());
		ArrayList<Link> links =(ArrayList<Link>) getLinks(this.getGraph());
		Collections.sort(nodes,new NodeComparator());
		Collections.sort(links,new LinkComparator());
		
		System.out.println("****************************Substrate Nodes**************************");
		
		
		for (Node current : nodes){
			System.out.print("["  +  current.getId() + ": ");
		if ((current) instanceof Server  )  
			System.out.println(((Server)current).getCpu()+" "+ ((Server)current).getMemory()+" "+((Server)current).getDiskSpace()+"]");	
	  }
		System.out.println("****************************Substrate Links**************************");
		for (Link current : links)
			System.out.println("Link: " + current.getId()+ ": " +current.getBandwidth());
			
		
	}
    public ArrayList<Link> getLinks(Graph<Node,Link> t) {
		ArrayList<Link> reqLink =new ArrayList<Link>();
		Collection<Link> edges =  t.getEdges();

		for (Link current : edges)
			reqLink.add(current);
		
		return reqLink;
	}
	
	public ArrayList<Node> getNodes(Graph<Node,Link> t) {
		ArrayList<Node> reqNodes =new ArrayList<Node>();
		Collection<Link> edges =  t.getEdges();

		for (Link current : edges){
			Pair<Node> currentNodes =t.getEndpoints(current);
			if (reqNodes.contains(currentNodes.getFirst())==false)
				reqNodes.add(currentNodes.getFirst());
			if (reqNodes.contains(currentNodes.getSecond())==false)
				reqNodes.add(currentNodes.getSecond());
		}


		return reqNodes;
	}
}
