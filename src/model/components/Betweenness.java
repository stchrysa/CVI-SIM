package model.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import edu.uci.ics.jung.graph.Graph;

import tools.NodeComparator;

import model.Dijkstra;
import model.Substrate;;

public class Betweenness {
	
	public void computeBetCet(Substrate sub){
		

		//Compute betweeness centrality
		//For every combination of nodes compute Dijkstra and then find
		//which nodes are transit nodes of the paths
		double[] through_paths=new double[sub.getGraph().getVertexCount()];
		
		ArrayList<Node> nodes=new ArrayList<Node>();
		nodes = (ArrayList<Node>) getNodes(sub.getGraph());
		Collections.sort(nodes,new NodeComparator());
		
			
		for (int i=0;i<nodes.size();i++){
			int pred[] = Dijkstra.DijkstraBet(sub, nodes.get(i).getId(), 0);
			for (int j=i+1;j<nodes.size();j++){
				ArrayList<Integer> path=Dijkstra.returnPath(sub, pred, nodes.get(i).getId(), nodes.get(j).getId());
				for (int k=0;k<path.size();k++){
					through_paths[path.get(k)]++;
				}
			}
		}

		for (Node node:sub.getGraph().getVertices()){
			double bet_cet=through_paths[node.getId()]/(((sub.getGraph().getVertexCount()*(sub.getGraph().getVertexCount()-1)))/2);
			node.setBetweenness(bet_cet);
		}
		
		
	}
	
	public Collection<Node> getNodes(Graph<Node,Link> t) {
		ArrayList<Node> reqNodes =new ArrayList<Node>();
		
		for (Node x: t.getVertices())
			reqNodes.add(x);


		return reqNodes;
	}

}
