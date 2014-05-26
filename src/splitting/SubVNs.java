package splitting;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import model.Request;
import model.Substrate;
import model.components.Link;
import model.components.Node;
import edu.uci.ics.jung.graph.util.Pair;

public class SubVNs {
	protected int reqNodeNum;
	protected int[] nodeMapping;
	protected List<Substrate> substrates;
	protected Request req;
	
	public SubVNs(int reqNodeNum, int[] nodeMapping2, List<Substrate> substrates, Request req){
		this.reqNodeNum=reqNodeNum;
		this.nodeMapping=nodeMapping2;
		this.substrates=substrates;
		this.req=req;
	}
		
	public List<Request> SUBVNS(){
		 
		List<Request> sub_request = new ArrayList<Request>();
		
		//Assign the appropriate InP at each virtual node
		 String[] NodeMapping = new String[reqNodeNum];
			for (int i=0;i<reqNodeNum;i++){
				//nodeMapping.put("sub+j", value)
				int j=nodeMapping[i];
				NodeMapping[i]="sub"+j;
			}
			//Construct the subVNs and add the appropriate virtual node at each subVN
			int counter=0;
			for(Substrate sub:substrates){
				Request subVN = new Request(req.getId()+"_"+counter);
				for (Node node:req.getGraph().getVertices()){
					if (NodeMapping[node.getId()].contains(sub.getId())){
						subVN.getGraph().addVertex(node);
						subVN.setStartDate(req.getStartDate());
						subVN.setEndDate(req.getEndDate());
						subVN.setInP(sub.getId());
					}
				}
				counter++;
				sub_request.add(subVN);
			}
			
			//Add the appropriate links at each SubVN
			for (Request subVN:sub_request){
				for (Link link:req.getGraph().getEdges()){
					Pair<Node> x =  req.getGraph().getEndpoints(link);
					int i=x.getFirst().getId();
					int j=x.getSecond().getId();
					int checklink=0;
					for (Node node: subVN.getGraph().getVertices()){
						if (node.getId()==i)  checklink++;
						if (node.getId()==j)  checklink++;
					}
					if (checklink==2) subVN.getGraph().addEdge(link, x.getFirst(), x.getSecond());
						
				}
			}
			
			 return sub_request;
		 }
}
