package model;

import gui.SimulatorConstants;



import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_cpxcp;
import org.gnu.glpk.glp_iocp;
import org.gnu.glpk.glp_prob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import output.Results;

import splitting.PartitioningAlgorithms;
import splitting.cost;
import tools.NodeComparator;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import model.components.Link;
import model.components.Node;
import model.components.Path;
import model.components.RequestRouter;
import model.components.RequestSwitch;
import model.components.Server;
import model.components.SubstrateLink;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import model.components.VirtualMachine;

import splitting.Partitioning;
import splitting.SubVNs;
/**
 * DUMMY Algorithm Class.
 */

public class Algorithm {
	 

	private String id;
	private String state;
	private Substrate InPs;
	private static List<Substrate> substrates ;
	private List<Request> reqs;
    private  static int  MAX_TYPES = 3;

	public Algorithm(String id) {
		this.id = id;
		this.state = "available";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	@SuppressWarnings("static-access")
	public void addSubstrate(List<Substrate> substrates) {
		this.substrates = substrates;
	}
	
	public void addRequests(List<Request> reqs) {
		this.reqs = reqs;
	}
	
	public void addInPs(Substrate InPs){
		this.InPs = InPs;
	}
	

	public double[][] runAlgorithm (){
		double[][] retres = null;
		
		if (this.id.contentEquals("NCM-ILS")) 
				retres=NCM();
		else if (this.id.contentEquals("GSP-ILS"))
			    retres=GSP();
		else if (this.id.contentEquals("GMCF-ILS"))
			retres=GMCF();
		else if (this.id.contentEquals("GSP-Exact"))
		    retres=GSP();
		else if (this.id.contentEquals("NCM-Exact"))
		    retres=NCM();
		else if (this.id.contentEquals("GMCF-Exact"))
			retres=GMCF();
				
		return retres;
	}
	
public static void waiting (int n){
        
        long t0, t1;

        t0 =  System.currentTimeMillis();

        do{
            t1 = System.currentTimeMillis();
        }
        
        while (t1 - t0 < n);
}

	

/////////////////////////////////GSP/////////////////////////////////////////////////////////////////////////
@SuppressWarnings({ "unchecked", "unused" })
public double[][] GSP(){
	boolean denied=false;
	int req_counter=0;
	double[][] retres=new double[reqs.size()][13];
	int inp_no=substrates.size();

	// int current_request=0;
	double part_cost=0;
	double part_time=0;


//////////////////REQUESTS//////////////////////////

for (Request req: reqs){ //for every request
	
	//Contain the node_mapping of every sub_request
	LinkedHashMap<String, LinkedHashMap<Node, Node>> phi_total = new LinkedHashMap<String, LinkedHashMap<Node, Node>>();
	//Contain the link_mapping of every sub_request
	LinkedHashMap<String, LinkedHashMap<Link,List<Path>>> flow_total = new LinkedHashMap<String, LinkedHashMap<Link,List<Path>>>();
			
	int denial=0;

		double[] ret_inter_res= null;
		if (inp_no>1){
			
			Partitioning partition = new Partitioning(InPs, substrates,req,id);
			ret_inter_res = partition.PerfromPartitioning();
			
			if (ret_inter_res==null){
				retres[req_counter][0]=1;
				break;
			}
			else{
				//Use one substrate only
				retres[req_counter][7]=ret_inter_res[1];
				//Use more than one substrates
				retres[req_counter][8]=ret_inter_res[2];
				//Partitioning Cost
				retres[req_counter][5]=ret_inter_res[3];
				//Partitioning time
				retres[req_counter][6]=ret_inter_res[4];
			}
			
		}
		else{
			//If there is only one substrate there will be only one subRequest the request it self
			List<Request> sub_request = new ArrayList<Request>();
			//Partitioning
		    int[] nodeMapping=new int[req.getGraph().getVertexCount()];
			SubVNs svns=new SubVNs(req.getGraph().getVertexCount(), nodeMapping, substrates, req);
			sub_request=svns.SUBVNS();
			req.setSubReq(sub_request);
		}


		//Intra VNE Mapping
		for (Substrate substrate: substrates){
			
			for (Request subVN: req.getSubReq()){
				//	if the sub request is to embedded on the specific substrate
				if ((subVN.getInP()==substrate.getId())&&(subVN.getGraph().getVertexCount()!=0)){

					ArrayList<Node> tmpReqN = new ArrayList<Node>();
					tmpReqN = 	(ArrayList<Node>) getNodes(subVN.getGraph());
					Collections.sort(tmpReqN,new NodeComparator());

					//Re-set the nodes id 
					int newnodeid=0;
					for (Node node:tmpReqN){
						node.setId(newnodeid);
						newnodeid++;
					}

					//	Re-set the links id			
					int newlinkid=0;
					for (Link link:subVN.getGraph().getEdges()){
						link.setId(newlinkid);
						newlinkid++;
					}


					denied=false;

					LinkedHashMap<Node, Node> phi = new LinkedHashMap<Node, Node>(); // request-real
					//	array holding virtual to substrate link mapping.
					LinkedHashMap<Link, List<Path>> flowReserved = new LinkedHashMap<Link,List<Path>>();
					
					//duplicate substrate graph   
					Substrate sub2 = new Substrate("sub2");
					sub2=(Substrate)substrate.getCopy();

					//	get bandwidth on substrate links.
					double[][] capTable = getCapTable(sub2.getGraph()) ;
					//get bandwidth of request links
					double[][] demands = getBandTable(subVN.getGraph());
					//	number of substrate nodes
					int initSubNodeNum=substrate.getGraph().getVertexCount();

					phi =  GreedyNodeMapping.GND(subVN, sub2, capTable);


					/////////////////////////////////////////////////////////////////
					//Step 2 implement Dijkstra/////////////////////////////////////
					///////////////////////////////////////////////////////////////
					if ((phi!=null)&&(subVN.getGraph().getEdgeCount()>0)){
						flowReserved =  Dijkstra.GLM(subVN, substrate, capTable, phi);
						
						if (flowReserved==null){
							denial++;
						}
						else{
							phi_total.put(substrate.getId(),phi);
							flow_total.put(substrate.getId(), flowReserved);
						}
					}
					else{
						denial++;
					}
				}//end of if
			}//end of sub_requests
		}//end of substrates
		double cost=0;
		double revenue=0;
		double avgHops=0;
		//	if all sub_requests are successfully embedded 
		if (denial==0){
			
			for (Substrate substrate: substrates){

				for (Request subVN: req.getSubReq()){

					if (subVN.getInP()==substrate.getId()){

						subVN.getRMap().accepted();

						ResourceMapping resMap=subVN.getRMap();
						Results results=new Results(substrate);

						//	update the node capacities in each substrate
						@SuppressWarnings("rawtypes")
						Set entries = phi_total.entrySet();
						@SuppressWarnings("rawtypes")
						Iterator iterator = entries.iterator();
						while (iterator.hasNext()) {
							@SuppressWarnings("rawtypes")
							Map.Entry entry = (Map.Entry)iterator.next();
							if (((String) entry.getKey())==subVN.getInP()){
								LinkedHashMap<Node, Node> pi= ((LinkedHashMap<Node, Node>) entry.getValue());

								resMap.setNodeMapping(pi);
								subVN.getRMap().reserveNodes(substrate);

							}
						}	

						//update the link capacities in each substrate
						if (subVN.getGraph().getVertexCount()>1){
							@SuppressWarnings("rawtypes")
							Set entries2 = flow_total.entrySet();
							@SuppressWarnings("rawtypes")
							Iterator iterator2 = entries2.iterator();
							while (iterator2.hasNext()) {
								@SuppressWarnings("rawtypes")
								Map.Entry entry2 = (Map.Entry)iterator2.next();
								if (((String) entry2.getKey())==subVN.getInP()){
									LinkedHashMap<Link, List<Path>> flow= ((LinkedHashMap<Link, List<Path>>) entry2.getValue());
									subVN.getRMap().setLinkMapping(flow);
									subVN.getRMap().reserveLinks(substrate);
									cost=results.Cost_Embedding(flow, subVN);
									revenue=results.Generate_Revenue(subVN);
									avgHops=results.avgHops(flow);
								}

							}
						}

						subVN.setRMap(resMap);
						
						retres[req_counter][1]+=cost;
						retres[req_counter][2]+=avgHops;
						retres[req_counter][9]+=revenue;

					}
				}
			}
			
			//If there are more than one substrates include the extra cost and hops due to the interlinks used
			if (InPs !=null){
				retres[req_counter][1]+=InterCost_Embedding(req,InPs);
				retres[req_counter][2]+=InterHop_Embedding(req, InPs);
			}

			double inter_cost=0;

			retres[req_counter][2]=retres[req_counter][2]/req.getGraph().getEdgeCount();
			
			
			Results inter_results = new Results(InPs);
		}
		else{
			denial=1;
			retres[req_counter][0]=denial;
			req.getRMap().denied();
			for (Request subVN : req.getSubReq()){
				subVN.getRMap().denied();
			}
		}

		req_counter++;
	}//end of requests


	return retres;
}


/////////////////////////////////GSP/////////////////////////////////////////////////////////////////////////
@SuppressWarnings({ "unchecked", "unused" })
public double[][] GMCF(){
boolean denied=false;
int req_counter=0;
double[][] retres=new double[reqs.size()][13];
int inp_no=substrates.size();

// int current_request=0;
double part_cost=0;
double part_time=0;


//////////////////REQUESTS//////////////////////////

for (Request req: reqs){ //for every request

	//Contain the node_mapping of every sub_request
	LinkedHashMap<String, LinkedHashMap<Node, Node>> phi_total = new LinkedHashMap<String, LinkedHashMap<Node, Node>>();
	//Contain the link_mapping of every sub_request
	LinkedHashMap<String, LinkedHashMap<Link,List<Path>>> flow_total = new LinkedHashMap<String, LinkedHashMap<Link,List<Path>>>();

	int denial=0;

	double[] ret_inter_res= null;
	if (inp_no>1){

		Partitioning partition = new Partitioning(InPs, substrates,req,id);
		ret_inter_res = partition.PerfromPartitioning();

		if (ret_inter_res==null){
			retres[req_counter][0]=1;
			break;
		}
		else{
			//Use one substrate only
			retres[req_counter][7]=ret_inter_res[1];
			//Use more than one substrates
			retres[req_counter][8]=ret_inter_res[2];
			//Partitioning Cost
			retres[req_counter][5]=ret_inter_res[3];
			//Partitioning time
			retres[req_counter][6]=ret_inter_res[4];
		}

	}
	else{
		//If there is only one substrate there will be only one subRequest the request it self
		List<Request> sub_request = new ArrayList<Request>();
		//Partitioning
		int[] nodeMapping=new int[req.getGraph().getVertexCount()];
		SubVNs svns=new SubVNs(req.getGraph().getVertexCount(), nodeMapping, substrates, req);
		sub_request=svns.SUBVNS();
		req.setSubReq(sub_request);
	}


	//Intra VNE Mapping
	for (Substrate substrate: substrates){

		for (Request subVN: req.getSubReq()){
			//	if the sub request is to embedded on the specific substrate
			if ((subVN.getInP()==substrate.getId())&&(subVN.getGraph().getVertexCount()!=0)){

				ArrayList<Node> tmpReqN = new ArrayList<Node>();
				tmpReqN = 	(ArrayList<Node>) getNodes(subVN.getGraph());
				Collections.sort(tmpReqN,new NodeComparator());

				//Re-set the nodes id 
				int newnodeid=0;
				for (Node node:tmpReqN){
					node.setId(newnodeid);
					newnodeid++;
				}

				//	Re-set the links id			
				int newlinkid=0;
				for (Link link:subVN.getGraph().getEdges()){
					link.setId(newlinkid);
					newlinkid++;
				}


				denied=false;

				LinkedHashMap<Node, Node> phi = new LinkedHashMap<Node, Node>(); // request-real
				//	array holding virtual to substrate link mapping.
				LinkedHashMap<Link, List<Path>> splittable_flows = new LinkedHashMap<Link, List<Path>>();

				//duplicate substrate graph   
				Substrate sub2 = new Substrate("sub2");
				sub2=(Substrate)substrate.getCopy();

				//	get bandwidth on substrate links.
				double[][] capTable = getCapTable(sub2.getGraph()) ;
				//get bandwidth of request links
				double[][] demands = getBandTable(subVN.getGraph());
				//	number of substrate nodes
				int initSubNodeNum=substrate.getGraph().getVertexCount();

				phi =  GreedyNodeMapping.GND(subVN, sub2, capTable);

	
				/////////////////////////////////////////////////////////////////
				//Step 2 implement MCF//////////////////////////////////////////
				///////////////////////////////////////////////////////////////
				

				if (phi==null){
				      denial++;
				      System.out.println("denial B   " +denial);
				}
				else {					
					ArrayList<Node> tmpSubNodesList = 	(ArrayList<Node>) getNodes(substrate.getGraph());
					Collections.sort(tmpSubNodesList,new NodeComparator());
					//Number of virtual links
					int reqLinkCount = subVN.getGraph().getEdgeCount();
					//Number of substrate nodes
					int subNodeCount = substrate.getGraph().getVertexCount();
				
					try{	
				
						glp_prob prob1 = GLPK.glp_create_prob();
						SWIGTYPE_p_int ind;
					    SWIGTYPE_p_double val;
				
						String name = null;
						int[][][] f_mcf = new int[reqLinkCount][][];
				
						//create f continuous variable, with bounds lb and ub
						//////////////////////////////////////////////////////////////////
						for(int k=0; k<reqLinkCount; k++) {
							f_mcf[k] = new int[subNodeCount][];
							for (int u=0; u<subNodeCount; u++){
								f_mcf[k][u] = new int[subNodeCount];
								for (int v=0; v<subNodeCount; v++){
									f_mcf[k][u][v] = GLPK.glp_add_cols(prob1, 1);
									name = "f_mcf[" + k + "," + u + "," + v + "]";
									GLPK.glp_set_col_name(prob1, f_mcf[k][u][v], name);
									GLPK.glp_set_col_kind(prob1, f_mcf[k][u][v], GLPKConstants.GLP_CV);
									GLPK.glp_set_col_bnds(prob1, f_mcf[k][u][v], GLPKConstants.GLP_DB, 0, Double.MAX_VALUE);
								}
							}
						}
				
						/*for (int k=0;k<reqLinksNum;k++){
						f_mcf[k]=new IloNumVar[initSubNodeNum][];
						for (int i=0;i<initSubNodeNum;i++){
							f_mcf[k][i]=cplex1.numVarArray(initSubNodeNum, 0, Double.MAX_VALUE);
						}
						}*/
				
						//Objective function
						for (int i=0; i<subNodeCount; i++) { 
							for (int j=0; j<subNodeCount; j++) {
								for (int k =0; k<reqLinkCount; k++) {				
									GLPK.glp_set_obj_coef(prob1, f_mcf[k][i][j], 1.0);
								}
							}
						}
				
						/*IloLinearNumExpr flows_mcf = cplex1.linearNumExpr();

						for (int i=0;i<initSubNodeNum; i++){ 
							for (int j=0;j<initSubNodeNum; j++){
								for (int k =0; k<reqLinksNum; k++) {
									//flows_mcf.addTerm(1.0, f_mcf[k][i][j]);
									flows_mcf.addTerm(1.0, f_mcf[k][i][j]);
								}
							}
						}				
						 */				
				

						///////////////////////////////////////////////////////////////////////////////
						//find the origins of the virtual link and corresponding substrate links//////
						/////////////////////////////////////////////////////////////////////////////
						int[] o=new int[reqLinkCount];
						int[] d=new int[reqLinkCount];
						for (Link link: subVN.getGraph().getEdges()){
							Pair<Node> pair = subVN.getGraph().getEndpoints(link);
							o[link.getId()]=pair.getFirst().getId();
							d[link.getId()]=pair.getSecond().getId();
						}
						
						int[] o2=new int[reqLinkCount];
						int[] d2=new int[reqLinkCount];
						for (Link link: subVN.getGraph().getEdges()){
							Pair<Node> pair = subVN.getGraph().getEndpoints(link);
							int first=pair.getFirst().getId();
							int second=pair.getSecond().getId();
							for (Node key: phi.keySet()){
								if (key.getId()==first){
									o2[link.getId()]=phi.get(key).getId();
								}
								if (key.getId()==second){
									d2[link.getId()]=phi.get(key).getId();
								}
							}
						}
						
	
				
						//flow reservation1
						for(int k=0;k<reqLinkCount; k++) {
							for(int i=0;i<subNodeCount; i++) {
								if(o2[k]!=i && d2[k]!=i) {
									int constraint = GLPK.glp_add_rows(prob1, 1);
									GLPK.glp_set_row_name(prob1, constraint, "r_(f1)_"+constraint);
									GLPK.glp_set_row_bnds(prob1, constraint, GLPKConstants.GLP_FX, 0, 0);
									ind = GLPK.new_intArray(subNodeCount*2+1);
									val = GLPK.new_doubleArray(subNodeCount*2+1);
									int len = 1;
									for(int j=0; j<subNodeCount; j++) {
										if (i!=j){
											GLPK.intArray_setitem(ind, len, f_mcf[k][i][j]);
											GLPK.intArray_setitem(ind, len+1, f_mcf[k][j][i]);
											GLPK.doubleArray_setitem(val, len, 1);
											GLPK.doubleArray_setitem(val, len+1, -1);
											len += 2;
										}
									}
									GLPK.glp_set_mat_row(prob1, constraint, len-1, ind, val);
								}
							}
						}
						
					
				
						/*	//flow reservation1
						for (int k=0;k<reqLinksNum;k++){
							for (int i=0;i<initSubNodeNum;i++){
								IloLinearNumExpr capReq = cplex1.linearNumExpr();
								//if ((o2[k]-reqNodeNum!=i)&&(d2[k]-reqNodeNum!=i)){
								if ((o2[k]!=i)&&(d2[k]!=i)){
									for (int j=0;j<initSubNodeNum;j++){
										capReq.addTerm(1,f_mcf[k][i][j]);
										capReq.addTerm(-1,f_mcf[k][j][i]);
									}
								}
								cplex1.addEq(capReq,0);
							}
						}
						 */

						//	flow reservation2
						double[][] tmpReqLinks = getCapTable(subVN.getGraph());
				
						for(int k=0;k<reqLinkCount; k++) {
							int constraint = GLPK.glp_add_rows(prob1, 1);
							GLPK.glp_set_row_name(prob1, constraint, "r_(f2)_"+constraint);
							GLPK.glp_set_row_bnds(prob1, constraint, GLPKConstants.GLP_FX, tmpReqLinks[o[k]][d[k]], tmpReqLinks[o[k]][d[k]]);
							ind = GLPK.new_intArray(subNodeCount*2+1);
							val = GLPK.new_doubleArray(subNodeCount*2+1);
							
							int idx = 1;
							for(int i=0;i<subNodeCount;i++) {
								if(o2[k] == i) {continue;}
								GLPK.intArray_setitem(ind, idx, f_mcf[k][o2[k]][i]);
								GLPK.intArray_setitem(ind, idx+1, f_mcf[k][i][o2[k]]);
								GLPK.doubleArray_setitem(val, idx, 1);
								GLPK.doubleArray_setitem(val, idx+1, -1);
								idx += 2;
							}
							GLPK.glp_set_mat_row(prob1, constraint, idx-1, ind, val);
						}
				

						
						/*			//flow reservation2
						for(int k=0;k<reqLinksNum;k++){
							IloLinearNumExpr capReq = cplex1.linearNumExpr();
							for (int i=0;i<initSubNodeNum;i++){
								capReq.addTerm(1,f_mcf[k][o2[k]][i]);
								//capReq.addTerm(1,f_mcf[k][o2[k]-reqNodeNum][i]);
								capReq.addTerm(-1,f_mcf[k][i][o2[k]]);
								//capReq.addTerm(-1,f_mcf[k][i][o2[k]-reqNodeNum]);
							}
							double reCap = 0;
							reCap=tmpReqLinks[o[k]][d[k]];
							cplex1.addEq(capReq,reCap);
						}*/
				
						//flow reservation3
						for (int k=0;k<reqLinkCount; k++){
							int constraint = GLPK.glp_add_rows(prob1, 1);
							GLPK.glp_set_row_name(prob1, constraint, "r_(f3)_"+constraint);
							GLPK.glp_set_row_bnds(prob1, constraint, GLPKConstants.GLP_FX, -tmpReqLinks[o[k]][d[k]], -tmpReqLinks[o[k]][d[k]]);
							ind = GLPK.new_intArray(subNodeCount*2+1);
							val = GLPK.new_doubleArray(subNodeCount*2+1);
							int idx = 0;
							for (int i=0;i<subNodeCount;i++){
								if(d2[k] == i) {continue;}
								GLPK.intArray_setitem(ind, idx, f_mcf[k][d2[k]][i]);
								GLPK.intArray_setitem(ind, idx+1, f_mcf[k][i][d2[k]]);
								GLPK.doubleArray_setitem(val, idx, 1);
								GLPK.doubleArray_setitem(val, idx+1, -1);
								idx += 2;
							}
							GLPK.glp_set_mat_row(prob1, constraint, idx-1, ind, val);
						}

						
						/*			//flow reservation3
						for(int k=0;k<reqLinksNum;k++){
							IloLinearNumExpr capReq = cplex1.linearNumExpr();
							for (int i=0;i<initSubNodeNum;i++){
								capReq.addTerm(1,f_mcf[k][d2[k]][i]);
								//capReq.addTerm(1,f_mcf[k][d2[k]-reqNodeNum][i]);
								capReq.addTerm(-1,f_mcf[k][i][d2[k]]);
								//capReq.addTerm(-1,f_mcf[k][i][d2[k]-reqNodeNum]);
							}
							double reCap = 0;
							reCap=-tmpReqLinks[o[k]][d[k]];
							cplex1.addEq(capReq,reCap);
						}*/	
	
						//	Link constraint
						double[][] subCapTable = getCapTable(substrate.getGraph());
						for (int i=0;i<subNodeCount; i++){
							for (int j=0;j<subNodeCount; j++){
								if (i == j) {continue;}
								double capacity = subCapTable[i][j];
								int constraint = GLPK.glp_add_rows(prob1, 1);
								GLPK.glp_set_row_name(prob1, constraint, "r_(lc)_"+constraint);
								GLPK.glp_set_row_bnds(prob1, constraint, GLPKConstants.GLP_UP, 0, capacity);
								ind = GLPK.new_intArray(2*reqLinkCount+1);
								val = GLPK.new_doubleArray(2*reqLinkCount+1);
								int idx = 0;
								
								for (int k=0; k<reqLinkCount; k++) {
									GLPK.intArray_setitem(ind, idx, f_mcf[k][i][j]);
									GLPK.intArray_setitem(ind, idx+1, f_mcf[k][j][i]);
									GLPK.doubleArray_setitem(val, idx, 1);
									GLPK.doubleArray_setitem(val, idx+1, 1);
									idx += 2;
								}
						
								GLPK.glp_set_mat_row(prob1, constraint, idx-1, ind, val);
							}		
						}
						
						/*				
						//Link constraint
						for (int i=0;i<initSubNodeNum; i++){
							for (int j=0;j<initSubNodeNum; j++){
								IloNumExpr expr1 =cplex1.prod(f_mcf[0][0][0],0);
								IloNumExpr flowSum =cplex1.prod(f_mcf[0][0][0],0);
								for (int k=0;k<reqLinksNum; k++){
									flowSum = cplex1.sum(cplex1.prod(f_mcf[k][i][j],1), 
									cplex1.prod(f_mcf[k][j][i],1));
									expr1 =cplex1.sum(expr1,flowSum);
								}
								double capacity =augCapTable[reqNodeNum+i][reqNodeNum+j];
								cplex1.addLe(expr1,capacity);
							}
						}*/

						GLPK.glp_write_lp(prob1, (new glp_cpxcp()), "gmcf.intern.problem.txt");
						GLPK.glp_set_obj_name(prob1, "GMCF Intern Problem obj");
						GLPK.glp_set_obj_dir(prob1, GLPKConstants.GLP_MIN);
				
						glp_iocp iocp1 = new glp_iocp();
						GLPK.glp_init_iocp(iocp1);
						iocp1.setPresolve(GLPKConstants.GLP_ON);
						int ret1 = GLPK.glp_intopt(prob1, iocp1);
				
						if ( ret1 == 0 ) {				
							
							//Update the residual bandwidth
					
							double[][][] fi=new double[subVN.getGraph().getEdgeCount()][substrate.getGraph().getVertexCount()][substrate.getGraph().getVertexCount()];
					
							for(int k=0;k<reqLinkCount;k++) {
								for(int i=0;i<subNodeCount;i++) {
									for(int j=0;j<subNodeCount;j++) {
										if(f_mcf[k][i][j] != 0) {
											fi[k][i][j] = GLPK.glp_mip_col_val(prob1, f_mcf[k][i][j]);
										} 
										else {
											fi[k][i][j] = 0;
										}
									}
								}
							}

							/*for (int k=0;k<reqLinksNum;k++){
								for (int i=0;i<initSubNodeNum;i++){
									for (int j=0;j<initSubNodeNum;j++){
										fi[k][i][j]=cplex1.getValue(f_mcf[k][i][j]);
									}
								}
							}
							 */
					
							splittable_flows = LinkToPath(fi, subVN, substrate, subNodeCount);
										
								
						}///cplex1
						else{
							denial++;
							System.out.println("denial C "  +denial);
							denied=true;
						}
				
						GLPK.glp_delete_prob(prob1);
					
						} catch (Exception e) {
							System.err.println("Concert exception caught: " + e);
						}	
					}
				

				if (splittable_flows==null){
					denial++;
				}
				else{
					phi_total.put(substrate.getId(),phi);
					flow_total.put(substrate.getId(), splittable_flows);
				}
			}
		}//end of sub_requests
	}//end of substrates

	
	double cost=0;
	double revenue=0;
	double avgHops=0;
	//	if all sub_requests are successfully embedded 
	if (denial==0){

		for (Substrate substrate: substrates){

			for (Request subVN: req.getSubReq()){

				if (subVN.getInP()==substrate.getId()){

					subVN.getRMap().accepted();

					ResourceMapping resMap=subVN.getRMap();
					Results results=new Results(substrate);

					//	update the node capacities in each substrate
					@SuppressWarnings("rawtypes")
					Set entries = phi_total.entrySet();
					@SuppressWarnings("rawtypes")
					Iterator iterator = entries.iterator();
					while (iterator.hasNext()) {
						@SuppressWarnings("rawtypes")
						Map.Entry entry = (Map.Entry)iterator.next();
						if (((String) entry.getKey())==subVN.getInP()){
							LinkedHashMap<Node, Node> pi= ((LinkedHashMap<Node, Node>) entry.getValue());

							resMap.setNodeMapping(pi);
							subVN.getRMap().reserveNodes(substrate);

						}
					}	

					//	update the link capacities in each substrate
					if (subVN.getGraph().getVertexCount()>1){
						@SuppressWarnings("rawtypes")
						Set entries2 = flow_total.entrySet();
						@SuppressWarnings("rawtypes")
						Iterator iterator2 = entries2.iterator();
						while (iterator2.hasNext()) {
							@SuppressWarnings("rawtypes")
							Map.Entry entry2 = (Map.Entry)iterator2.next();
							if (((String) entry2.getKey())==subVN.getInP()){
								LinkedHashMap<Link, List<Path>> flow= ((LinkedHashMap<Link, List<Path>>) entry2.getValue());
								subVN.getRMap().setLinkMapping(flow);
								subVN.getRMap().reserveLinks(substrate);
								cost=results.Cost_Embedding(flow, subVN);
								revenue=results.Generate_Revenue(subVN);
								avgHops=results.avgHops(flow);
							}
							
						}
					}

					subVN.setRMap(resMap);

					retres[req_counter][1]+=cost;
					retres[req_counter][2]+=avgHops;
					retres[req_counter][9]+=revenue;

				}
			}
		}

		//	If there are more than one substrates include the extra cost and hops due to the interlinks used
		if (InPs !=null){
			retres[req_counter][1]+=InterCost_Embedding(req,InPs);
			retres[req_counter][2]+=InterHop_Embedding(req, InPs);
		}

		double inter_cost=0;

		retres[req_counter][2]=retres[req_counter][2]/req.getGraph().getEdgeCount();


		Results inter_results = new Results(InPs);
	}
	else{
		denial=1;
		retres[req_counter][0]=denial;
		req.getRMap().denied();
		for (Request subVN : req.getSubReq()){
			subVN.getRMap().denied();
		}
	}

	req_counter++;
	}//end of requests


	return retres;
}


//-----------------------------------------NCM*************************

/**
* @return
*/
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
private double[][] NCM() {
	double[][] retres=new double[reqs.size()][13];
	boolean denied =false;
	int denials1=0;
	int denials2=0;
	int algo_denials=0;
	int req_counter=0;
	double part_cost=0;
	double part_time=0;
		
	int inp_no=substrates.size();
///////////////////////////////////////////////////////////////////////////

	for (Request request: reqs){ //for every request
		
		
		
		
		//Contain the node_mapping of every sub_request
		LinkedHashMap<String, LinkedHashMap<Node, Node>> phi_total = new LinkedHashMap<String, LinkedHashMap<Node, Node>>();
		//Contain the link_mapping of every sub_request
		LinkedHashMap<String, LinkedHashMap<Link, List<Path>>> flow_total = new LinkedHashMap<String, LinkedHashMap<Link, List<Path>>>();
		int denial=0;

			double[] ret_inter_res= null;
			if (inp_no>1){
				
				Partitioning partition = new Partitioning(InPs, substrates,request,id);
				ret_inter_res = partition.PerfromPartitioning();
				
				if (ret_inter_res==null){
					retres[req_counter][0]=1;
					break;
				}
				else{
					//Use one substrate only
					retres[req_counter][7]=ret_inter_res[1];
					//Use more than one substrates
					retres[req_counter][8]=ret_inter_res[2];
					//Partitioning Cost
					retres[req_counter][5]=ret_inter_res[3];
					//Partitioning time
					retres[req_counter][6]=ret_inter_res[4];
				}
				
			}
			else{
				//If there is only one substrate there will be only one subRequest the request it self
				List<Request> sub_request = new ArrayList<Request>();
				int[] nodeMapping=new int[request.getGraph().getVertexCount()];
				SubVNs svns=new SubVNs(request.getGraph().getVertexCount(), nodeMapping, substrates, request);
				sub_request=svns.SUBVNS();
				request.setSubReq(sub_request);
			}
		
		
		
		
		
	for (Substrate substrate: substrates){
			
		for (Request subVN: request.getSubReq()){
						
			//In case the subVN is only one node or the case that there are no links
			if ((subVN.getInP()==substrate.getId())&&((subVN.getGraph().getVertexCount()==1)||(subVN.getGraph().getEdgeCount()==0))){

				ArrayList<Node> tmpReqN = new ArrayList<Node>();
				tmpReqN = 	(ArrayList<Node>) getNodes(subVN.getGraph());
				Collections.sort(tmpReqN,new NodeComparator());
				
				//Re-set the nodes id 
				int newnodeid=0;
				for (Node node:tmpReqN){
					node.setId(newnodeid);
					newnodeid++;
				}
				boolean ctrl=false;
				LinkedHashMap<Node, Node> NodeMapping =new LinkedHashMap<Node,Node>();
				
				Substrate substrateCopy = (Substrate) substrate.getCopy();
		
				//	get bandwidth on substrate links.
				double[][] capTable = getCapTable(substrateCopy.getGraph()) ;
		
				NodeMapping =  GreedyNodeMapping.GND(subVN, substrateCopy, capTable);
				
				if (NodeMapping!=null){
					phi_total.put(substrate.getId(),NodeMapping);
				}else{
					subVN.getRMap().denied();
					denial++;
				}
				
		
			}
			
			
			
	//if the sub request is to embedded on the specific substrate
	if ((subVN.getInP()==substrate.getId())&&(subVN.getGraph().getEdgeCount()>0)){
		
		
		ArrayList<Node> tmpReqN = new ArrayList<Node>();
		tmpReqN = 	(ArrayList<Node>) getNodes(subVN.getGraph());
		Collections.sort(tmpReqN,new NodeComparator());
		
		//Re-set the nodes id 
		int newnodeid=0;
		for (Node node:tmpReqN){
			node.setId(newnodeid);
			newnodeid++;
		}
		//Re-set the links id			
		int newlinkid=0;
		for (Link link:subVN.getGraph().getEdges()){
			link.setId(newlinkid);
			newlinkid++;
		}
		
		
		
		denied=false;
		
		LinkedHashMap<Link, List<Path>> splittable_flows = new LinkedHashMap<Link, List<Path>>();
		LinkedHashMap<Node, Node> NodeMapping =new LinkedHashMap<Node,Node>();
				
		/// create augmented graph 
		// add substrate
		
		Substrate augmentedSubstrate = new Substrate("augmentedSubstrate");
		Substrate substrateCopy = (Substrate) substrate.getCopy();
		
		//change ids in substrate copy
		for (Node n: substrateCopy.getGraph().getVertices() ){
			int current_id = n.getId();
			n.setId(current_id+subVN.getGraph().getVertexCount());
		}

		////////////////////////////////////////////////////////////////////
		//////////// add substrate to augmented
		for (Link current : substrateCopy.getGraph().getEdges()){
			Pair<Node> x =  substrateCopy.getGraph().getEndpoints(current);
			augmentedSubstrate.getGraph().addEdge(current, x.getFirst(), x.getSecond(),  EdgeType.UNDIRECTED);
			augmentedSubstrate.getGraph().addVertex(x.getFirst());
			augmentedSubstrate.getGraph().addVertex(x.getSecond());
		}

		//////////// add req to augmented///////////////////////////////

		Collection<Node> tmpReqNodes = getNodes(subVN.getGraph());
		int subLinkNum = augmentedSubstrate.getGraph().getEdgeCount();

		for (Node x : tmpReqNodes){
			augmentedSubstrate.getGraph().addVertex(x);
			for (Node subNode : substrateCopy.getGraph().getVertices()){
				int LinkID = subLinkNum++;

				SubstrateLink l = new SubstrateLink(LinkID,(int) 2.147483647E5);
				augmentedSubstrate.getGraph().addEdge(l, x, subNode, EdgeType.UNDIRECTED);
			}
		}

		double[][] augCapTable = getCapTable(augmentedSubstrate.getGraph()) ;
		
		

		// run glpk on relaxed problem
		
		try {
			
			glp_prob prob = GLPK.glp_create_prob();
			GLPK.glp_set_prob_name(prob, "NCM-MIP");
			//GLPK._glp_lpx_set_real_parm(prob, GLPK.LPX_K_TMLIM, 600);
			//GLPK._glp_lpx_set_real_parm(prob, GLPK.LPX_K_OBJUL, Double.MAX_VALUE);
			//GLPK._glp_lpx_set_real_parm(prob, GLPK.LPX_K_OBJLL, -1*Double.MAX_VALUE);
			SWIGTYPE_p_int ind;
		    SWIGTYPE_p_double val;


			// substate copy has changed ids
			ArrayList<Node> tmpSubNodesList = new ArrayList<Node>();
			tmpSubNodesList = 	(ArrayList<Node>) getNodes(substrateCopy.getGraph());
			Collections.sort(tmpSubNodesList,new NodeComparator());

			ArrayList<Node> tmpReqNodesList = new ArrayList<Node>();
			tmpReqNodesList = 	(ArrayList<Node>) getNodes(subVN.getGraph());
			Collections.sort(tmpReqNodesList,new NodeComparator());
			

			///////////////////////////////////////////////////
			//Find how many routers and servers we have///////
			/////////////////////////////////////////////////
			LinkedHashMap<String, Integer> tmp_req = getType(tmpReqNodesList);
			int v_types =  tmp_req.size();
			/////////////////
			LinkedHashMap<String, Integer> tmp_sub = getType(tmpSubNodesList);
			int s_types =  tmp_sub.size();
			int[] ctypes=new int[s_types];
			ctypes[0]=1;
			
			ArrayList<ArrayList<Integer>> Vv  =createArray(tmpReqNodesList);
			ArrayList<ArrayList<Integer>> Vs = createArray(tmpSubNodesList);
		    
			System.out.println("Vv: "+Vv);
			System.out.println("Vs: "+Vs);
			//////////////////////////////////////
			//////////////////////////
			//Build arrays for the different functional characteristics////
			//////////////////////////////////////////////////////////////
			int[][] Vcost= getCapacities(tmpReqNodesList);
			int[][] Scost = getCapacities(tmpSubNodesList);
			
		
		
			int subNodeCount = tmpSubNodesList.size();
			int reqNodeCount = tmpReqNodes.size();
			int reqLinkCount = subVN.getGraph().getEdgeCount();
			int augNodeCount = subNodeCount+reqNodeCount;
			
			
			String name = null;
			int[][][] f = new int[reqLinkCount][][];
			int[][][] x = new int[reqLinkCount][][];
			
			
			
			//Create x,f continuous variables, all with bounds lb and ub	
			for(int k=0; k<reqLinkCount; k++) {
				f[k] = new int[augNodeCount][];
				x[k] = new int[augNodeCount][];
				for (int u=0; u<augNodeCount; u++){
					f[k][u] = new int[augNodeCount];
					x[k][u] = new int[augNodeCount];
					for (int v=0; v<augNodeCount; v++){
						if(u==v) {continue;}
						f[k][u][v] = GLPK.glp_add_cols(prob, 1);
	                    name = "f[" + k + "," + u + "," + v + "]";
	                    GLPK.glp_set_col_name(prob, f[k][u][v], name);
	                    GLPK.glp_set_col_kind(prob, f[k][u][v], GLPKConstants.GLP_CV);
	                    GLPK.glp_set_col_bnds(prob, f[k][u][v], GLPKConstants.GLP_DB, 0, Double.MAX_VALUE);
	                    
	                    x[k][u][v] = GLPK.glp_add_cols(prob, 1);
	                    name = "x[" + k + "," + u + "," + v + "]";
	                    GLPK.glp_set_col_name(prob, x[k][u][v], name);
					    GLPK.glp_set_col_kind(prob, x[k][u][v], GLPKConstants.GLP_CV);
						GLPK.glp_set_col_bnds(prob, x[k][u][v], GLPKConstants.GLP_DB, 0, 1);
						// GLPK.glp_set_col_kind(prob, x[k][u][v], GLPKConstants.GLP_BV);
					}
				}
			}

		
			
			// Objective coefficient: minimize flows (1a)	
			 for (int u=reqNodeCount; u<augNodeCount; u++) { 
				 for (int v=reqNodeCount; v<augNodeCount; v++) {
					 for (int k =0; k<reqLinkCount; k++) {
						GLPK.glp_set_obj_coef(prob, f[k][u][v], 1.0/(augCapTable[u][v]+0.00000000001));
					 }
				 }
			 }
			 
/*				IloLinearNumExpr flows = cplex.linearNumExpr();
				for (int i=reqNodeNum;i<augGraph; i++){ 
					for (int j=reqNodeNum;j<augGraph; j++){
						for (int k =0; k<reqLinksNum; k++) {
						flows.addTerm(1.0/(augCapTable[i][j]+0.000001), f[k][i][j]);
						//flows.addTerm(1.0, f[k][i][j]);
						}
					}
				}*/
			 


			// Objective coefficient: minimize computational capacities (1b);	
			for (int k=0;k<reqLinkCount;k++) {
				for (int i=0;i<v_types;i++) {
					for (int w : Vs.get(i)) {
						for (int m : Vv.get(i)){
							double totalCap=0;
							for (int j=0;j<SimulatorConstants.NODE_MAX_CAPACITY_TYPES;j++){
								totalCap = (totalCap+Vcost[j][m]/(Scost[j][w]+Double.MIN_VALUE));
							}
							GLPK.glp_set_obj_coef(prob, x[k][m][w+reqNodeCount], totalCap);
						}				
					}
				}	
			}



/*			IloLinearNumExpr capacities = cplex.linearNumExpr();
			for (int k=0;k<reqLinksNum;k++){
				for (int i=0;i<v_types;i++){
					for (int w : Vs.get(i)){
						for (int m : Vv.get(i)){
							double total_cap=0;
							for (int j=0;j<3;j++){
								total_cap= ((total_cap+Vcost[j][m]/(Scost[j][w]+0.000001)));								
							}
							capacities.addTerm(total_cap,x[k][m][w+reqNodeNum]);
						}
					}
				}
			}
*/
			
			//Objective coefficient: minimize hops (1c);	
			 for (int k=0;k<reqLinkCount;k++) {
				 for (int u=reqNodeCount; u<augNodeCount; u++){
					 for (int v=reqNodeCount; v<augNodeCount; v++){
						 double value = GLPK.glp_get_obj_coef(prob, x[k][u][v]);
						 GLPK.glp_set_obj_coef(prob, x[k][u][v], 1+value);
					 }
				 }	
			 }
			 

/*			IloNumExpr hops =cplex.prod(x[0][0][0],0);
			for (int k=0;k<reqLinksNum;k++){
				IloNumExpr expr1 =cplex.prod(x[0][0][0],0);
				for (int i=reqNodeNum;i<augGraph;i++){
					for (int j=reqNodeNum;j<augGraph;j++){
						expr1=cplex.sum(expr1,x[k][i][j]);
					}
				}
				hops = cplex.sum(hops, expr1);
			}*/
			


			
			 //Link Constraint: constraint (6) 
			 for (int k=0;k<reqLinkCount;k++){
				 for (int i=reqNodeCount;i<augNodeCount;i++){
					 for (int j=reqNodeCount;j<augNodeCount;j++){
						 if (i!=j){
						 double capacity = augCapTable[i][j];
						 int constraint = GLPK.glp_add_rows(prob, 1);
						 GLPK.glp_set_row_name(prob, constraint, "r_(6)_"+constraint);
						 GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_UP, 0, 0);
						 ind = GLPK.new_intArray(4);
						 val = GLPK.new_doubleArray(4);
						 GLPK.intArray_setitem(ind, 1, x[k][i][j]);
						 GLPK.intArray_setitem(ind, 2, f[k][i][j]);
						 GLPK.intArray_setitem(ind, 3, f[k][j][i]);
						 GLPK.doubleArray_setitem(val, 1, -capacity);
						 GLPK.doubleArray_setitem(val, 2, 1);
						 GLPK.doubleArray_setitem(val, 3, 1);
						 GLPK.glp_set_mat_row(prob, constraint, 3, ind, val);
						 }
					 }
				 }
			 }
			 
/*			for (int k=0;k<reqLinksNum; k++){
				for (int i=reqNodeNum;i<augGraph; i++){
					for (int j=reqNodeNum;j<augGraph; j++){
						IloNumExpr flowSum =cplex.prod(x[0][0][0],0);
						IloLinearNumExpr availBW = cplex.linearNumExpr();
						flowSum = cplex.sum(cplex.prod(f[k][i][j],1), 
									cplex.prod(f[k][j][i],1));
						
						double capacity = augCapTable[i][j];
						availBW.addTerm(capacity,x[k][i][j]);
						cplex.addLe(flowSum,availBW);	
					}
				}		
			}
			*/
			 
			//Link Constraint 2: Constraint (7) 
				for (int u=0; u<augNodeCount; u++){
					for (int v=u+1; v<augNodeCount; v++){
						double capacity = augCapTable[u][v];
						int constraint = GLPK.glp_add_rows(prob, 1);
						GLPK.glp_set_row_name(prob, constraint, "r_(7)_"+constraint);
						GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_UP, 0, capacity);
						ind = GLPK.new_intArray(2*reqLinkCount+1);
						val = GLPK.new_doubleArray(2*reqLinkCount+1);
						
						for (int k=0; k<reqLinkCount; k++) {
							int idx = (k+1)*2;
						    GLPK.intArray_setitem(ind, idx-1, f[k][u][v]);
						    GLPK.intArray_setitem(ind, idx, f[k][v][u]);
						    GLPK.doubleArray_setitem(val, idx-1, 1);
						    GLPK.doubleArray_setitem(val, idx, 1);
						}
						
						GLPK.glp_set_mat_row(prob, constraint, 2*reqLinkCount, ind, val);
					}		
				}

/*			for (int i=0;i<augGraph; i++){
				for (int j=0;j<augGraph; j++){
					IloNumExpr expr1 =cplex.prod(x[0][0][0],0);
					IloNumExpr flowSum =cplex.prod(x[0][0][0],0);
					
					for (int k=0;k<reqLinksNum; k++){
						flowSum = cplex.sum(cplex.prod(f[k][i][j],1), 
								cplex.prod(f[k][j][i],1));
						expr1 =cplex.sum(expr1,flowSum);
					}
					double capacity = augCapTable[i][j];
				
					cplex.addLe(expr1, capacity);
				}
			}*/

		
			//node constraint for virtual machines and servers: constraint (5);
			for (int k=0;k<reqLinkCount;k++){
				for (int i=0;i<s_types;i++){
					for (int w : Vs.get(i)){
						for (int m : Vv.get(i)){
							int index=1;
							if (i==0) {index = 3;}
							for (int j=0;j<index;j++){			
								double cpuSub = Scost[j][w];
								int constraint = GLPK.glp_add_rows(prob, 1);
								GLPK.glp_set_row_name(prob, constraint, "r_(5)_"+constraint);
								GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_UP, 0, cpuSub);
								ind = GLPK.new_intArray(2);
								GLPK.intArray_setitem(ind, 1, x[k][m][w+reqNodeCount]);
							    val = GLPK.new_doubleArray(2);
							    GLPK.doubleArray_setitem(val, 1, Vcost[j][m]);
							    GLPK.glp_set_mat_row(prob, constraint, 1, ind, val);
							}
						}
						
					}
				}
			}
			
			
/*			for (int k=0;k<reqLinksNum;k++){
				for (int i=0;i<s_types;i++){
					for (int w : Vs.get(i)){
						for (int m : Vv.get(i)){
							for (int j=0;j<3;j++){
								double cpuSub=Scost[j][w];
								cplex.addLe(cplex.prod(Vcost[j][m], x[k][m][w+reqNodeNum]),cpuSub);
							}
						}
					}
				}
			}*/
			
			

			//constraint (8)
			for (int k=0;k<reqLinkCount;k++){
				for (int i=0;i<s_types;i++){
					for (int w : Vs.get(i)){
						if(Vv.get(i).size() > 0) {
							int constraint = GLPK.glp_add_rows(prob, 1);
							GLPK.glp_set_row_name(prob, constraint, "r_(8)_"+constraint);
							GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_UP, 0, 1);
							ind = GLPK.new_intArray(Vv.get(i).size()+1);
							val = GLPK.new_doubleArray(Vv.get(i).size()+1);
							int idx = 1;
							for (int m : Vv.get(i)){
								GLPK.intArray_setitem(ind, idx, x[k][m][w+reqNodeCount]);
								GLPK.doubleArray_setitem(val, idx, 1);
							    idx++;
							}
							GLPK.glp_set_mat_row(prob, constraint, Vv.get(i).size(), ind, val);
						}
					}
				}
			}

		/*	//Constraint 8
			for (int k=0;k<reqLinksNum;k++){
				for (int i=0;i<s_types;i++){
					for (int w : Vs.get(i)){
						IloLinearNumExpr capReq = cplex.linearNumExpr();
						for (int m : Vv.get(i)){
							capReq.addTerm(1,x[k][m][w+reqNodeNum]);
						}
						cplex.addLe(capReq,1);
					}
				}
			}*/

			
			//constraint 9
			for (int k=0;k<reqLinkCount;k++){
				for (int m=0; m<reqNodeCount; m++){
					int constraint = GLPK.glp_add_rows(prob, 1);
					GLPK.glp_set_row_name(prob, constraint, "r_(9)_"+constraint);
					GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_UP, 0, 1);
					ind = GLPK.new_intArray(subNodeCount+1);
					val = GLPK.new_doubleArray(subNodeCount+1);
					int idx = 1;
					for (int w=reqNodeCount; w<augNodeCount; w++){
						GLPK.intArray_setitem(ind, idx, x[k][m][w]);
					    GLPK.doubleArray_setitem(val, idx, 1);
					    idx++;
					}
					GLPK.glp_set_mat_row(prob, constraint, subNodeCount, ind, val);
				}
			}
			
/*			//Constraint 9
			for (int k=0;k<reqLinksNum;k++){
				for (int m=0;m<reqNodeNum;m++){
					IloLinearNumExpr capReq = cplex.linearNumExpr();
						for (int w=reqNodeNum;w<augGraph;w++){
							capReq.addTerm(1,x[k][m][w]);
					}
						cplex.addLe(capReq, 1);
				}
			}*/
			
			
			//constraint 10
			for (int k=0;k<reqLinkCount;k++){
				for (int i=0;i<s_types;i++){
					for (int m : Vv.get(i)){
						if(Vs.get(i).size() > 0) {
							int constraint = GLPK.glp_add_rows(prob, 1);
							GLPK.glp_set_row_name(prob, constraint, "r_(10)_"+constraint);
							GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, 1, 1);
							ind = GLPK.new_intArray(Vs.get(i).size()+1);
							val = GLPK.new_doubleArray(Vs.get(i).size()+1);
							int idx = 1;
							for (int w : Vs.get(i)){
								GLPK.intArray_setitem(ind, idx, x[k][m][w+reqNodeCount]);
							    GLPK.doubleArray_setitem(val, idx, 1);
							    idx++;
							}
							GLPK.glp_set_mat_row(prob, constraint, Vs.get(i).size(), ind, val);
						}
					}
				}
			}
			
/*			//Constraint 10
			for (int k=0;k<reqLinksNum;k++){
				for (int i=0;i<s_types;i++){
					for (int m : Vv.get(i)){
						IloLinearNumExpr capReq = cplex.linearNumExpr();
						for (int w : Vs.get(i)){
							capReq.addTerm(1,x[k][m][w+reqNodeNum]);
						}
						cplex.addEq(capReq,1);
					}
				}
			}*/

			// Constraint 11
			for (int k=0;k<reqLinkCount;k++){
				for (int i=0;i<augNodeCount; i++){
					for (int j=i+1;j<augNodeCount; j++) {
						int constraint = GLPK.glp_add_rows(prob, 1);
						GLPK.glp_set_row_name(prob, constraint, "r_(11)_"+constraint);
						GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, 0, 0);
						ind = GLPK.new_intArray(3);
						val = GLPK.new_doubleArray(3);
						GLPK.intArray_setitem(ind, 1, x[k][i][j]);
						GLPK.intArray_setitem(ind, 2, x[k][j][i]);
						GLPK.doubleArray_setitem(val, 1, 1);
						GLPK.doubleArray_setitem(val, 2, -1);
						GLPK.glp_set_mat_row(prob, constraint, 2, ind, val);
					}
				}
			}
			
/*			//Constraint 11
			for (int k=0;k<reqLinksNum;k++){
				for (int i=0;i<augGraph; i++){
					for (int j=0;j<augGraph; j++){
							IloLinearNumExpr capReq1 = cplex.linearNumExpr();
							IloLinearNumExpr capReq2 = cplex.linearNumExpr();
							capReq1.addTerm(1,x[k][i][j]);
							capReq2.addTerm(1,x[k][j][i]);
							cplex.addEq(capReq1,capReq2);
					}
				}
			}*/
			
			//constraint 12
			 for (int k1=0;k1<reqLinkCount;k1++){
				 for (int k2=0;k2<reqLinkCount;k2++){
					 if(k1 == k2){
					 continue;
					 }
					  for (int i=0;i<reqNodeCount; i++){
						 for (int j=reqNodeCount;j<augNodeCount; j++){	
						 int constraint = GLPK.glp_add_rows(prob, 1);
						 GLPK.glp_set_row_name(prob, constraint, "r_(13)_"+constraint);
						 GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, 0, 0);
						 ind = GLPK.new_intArray(3);
						 val = GLPK.new_doubleArray(3);
						 GLPK.intArray_setitem(ind, 1, x[k1][i][j]);
						 GLPK.intArray_setitem(ind, 2, x[k2][i][j]);
						 GLPK.doubleArray_setitem(val, 1, 1);
						 GLPK.doubleArray_setitem(val, 2, -1);
						 GLPK.glp_set_mat_row(prob, constraint, 2, ind, val);					
						 }
					 }
				 }
			 }
			 
			 
/*				for (int k1=0;k1<reqLinksNum;k1++){
					for (int k2=0;k2<reqLinksNum;k2++){
						for (int i=0;i<reqNodeNum; i++){
							for (int j=reqNodeNum;j<augGraph; j++){
									IloLinearNumExpr capReq1 = cplex.linearNumExpr();
									IloLinearNumExpr capReq2 = cplex.linearNumExpr();
										capReq1.addTerm(1,x[k1][i][j]);
										capReq2.addTerm(1,x[k2][i][j]);
										cplex.addEq(capReq1,capReq2);
							}
						}
					}
				}*/
			
			 



			//	constraint 13
				for (int k=0;k<reqLinkCount;k++){
					for (int i=0;i<augNodeCount;i++){
						for (int j=i+1;j<augNodeCount;j++){
							int constraint = GLPK.glp_add_rows(prob, 1);
							GLPK.glp_set_row_name(prob, constraint, "r_(13)_"+constraint);
							GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_LO, 0, 0);
							ind = GLPK.new_intArray(4);
							val = GLPK.new_doubleArray(4);
							GLPK.intArray_setitem(ind, 1, x[k][i][j]);
							GLPK.intArray_setitem(ind, 2, f[k][i][j]);
							GLPK.intArray_setitem(ind, 3, f[k][j][i]);
							GLPK.doubleArray_setitem(val, 1, -1);
							GLPK.doubleArray_setitem(val, 2, 1);
							GLPK.doubleArray_setitem(val, 3, 1);
							GLPK.glp_set_mat_row(prob, constraint, 3, ind, val);
						}
					}
				}
				
	/*			//Constraint 13
				
				for (int k=0;k<reqLinksNum;k++){
					for (int i=0;i<augGraph;i++){
						for (int j=0;j<augGraph;j++){
							IloNumExpr flowSum =cplex.prod(x[0][0][0],0);
							IloLinearNumExpr capReq = cplex.linearNumExpr();
							capReq.addTerm(1,x[k][i][j]);
							
								flowSum = cplex.sum(cplex.prod(f[k][i][j],1), 
										cplex.prod(f[k][j][i],1));
										
							
							cplex.addGe(flowSum, capReq);
						}
					}
				}*/
			


			//Matching constraint: Constraint 14
			for (Node snode: substrate.getGraph().getVertices()){
				for (Node vnode: subVN.getGraph().getVertices()){
					if ((snode.getType()!=vnode.getType())||(snode.getOS()!=vnode.getOS())||(snode.getVEType()!=vnode.getVEType())){
						for (int k=0;k<reqLinkCount;k++){
							int constraint = GLPK.glp_add_rows(prob,1);
							GLPK.glp_set_row_name(prob, constraint, "r_(14)_"+constraint);
							GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, 0, 0);
							ind = GLPK.new_intArray(2);
							GLPK.intArray_setitem(ind, 1, x[k][vnode.getId()][snode.getId()+reqNodeCount]);
						    val = GLPK.new_doubleArray(2);
						    GLPK.doubleArray_setitem(val, 1, 1);
						    GLPK.glp_set_mat_row(prob, constraint, 1, ind, val);
						}
					}
				}
			}
			
		
		/*	for (Node snode:substrate.getGraph().getVertices()){
				for (Node vnode:subVN.getGraph().getVertices()){
					if ((snode.getType()!=vnode.getType())||(snode.getOS()!=vnode.getOS())||(snode.getVEType()!=vnode.getVEType())){
						for (int k=0;k<reqLinksNum;k++)
							cplex.addEq(x[k][vnode.getId()][snode.getId()+reqNodeNum],0);
					}
				}
			}
			*/
			double[][] demands = getBandTable(subVN.getGraph());


			/////////////////////////////////////////////
			//find the origins of the virtual link//////
			///////////////////////////////////////////
			int[] o=new int[reqLinkCount];
			int[] d=new int[reqLinkCount];
			for (Link link: subVN.getGraph().getEdges()){
				Pair<Node> pair = subVN.getGraph().getEndpoints(link);
				o[link.getId()]=pair.getFirst().getId();
				d[link.getId()]=pair.getSecond().getId();
			}
			
			
			//constraint 4.1	
			for (int k=0;k<reqLinkCount; k++){
				for (int i=0;i<augNodeCount; i++){ 
					if (i!=(o[k]) && i!=(d[k])){
						int constraint = GLPK.glp_add_rows(prob, 1);
						GLPK.glp_set_row_name(prob, constraint, "r_(4.1)_"+constraint);
						GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, 0, 0);
						ind = GLPK.new_intArray(augNodeCount*2+1);
						val = GLPK.new_doubleArray(augNodeCount*2+1);
						int idx = 1;
						for (int j=i+1; j<augNodeCount; j++){
							GLPK.intArray_setitem(ind, idx, f[k][i][j]);
							GLPK.intArray_setitem(ind, idx+1, f[k][j][i]);
							GLPK.doubleArray_setitem(val, idx, 1);
							GLPK.doubleArray_setitem(val, idx+1, -1);
							idx += 2;
						}
						GLPK.glp_set_mat_row(prob, constraint, idx-1, ind, val);
					}
				}
			}

			
		/*	for (int k=0;k<reqLinksNum; k++){
				for (int i=0;i<augGraph; i++){ 
					IloLinearNumExpr capReq = cplex.linearNumExpr();
					if (i!=o[k]&&i!=d[k]){
						for (int j=0;j<augGraph; j++){
							capReq.addTerm(1,f[k][i][j]);
							capReq.addTerm(-1,f[k][j][i]);
						}
					cplex.addEq(capReq,0);
					}
				}
			}*/

			
			//constraint 4.2;
			double[][] tmpReqLinks = getCapTable(subVN.getGraph());
			
			for (int k=0;k<reqLinkCount; k++){
				int constraint = GLPK.glp_add_rows(prob, 1);
				GLPK.glp_set_row_name(prob, constraint, "r_(4.2)_"+constraint);
				GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, tmpReqLinks[o[k]][d[k]], tmpReqLinks[o[k]][d[k]]);
				ind = GLPK.new_intArray(augNodeCount*2+1);
				val = GLPK.new_doubleArray(augNodeCount*2+1);
				int idx = 1;
				for (int w=0;w<augNodeCount;w++){
					if((o[k]) == w) {continue;}
					GLPK.intArray_setitem(ind, idx, f[k][o[k]][w]);
					GLPK.intArray_setitem(ind, idx+1, f[k][w][o[k]]);
					GLPK.doubleArray_setitem(val, idx, 1);
					GLPK.doubleArray_setitem(val, idx+1, -1);
					idx += 2;
				}
				GLPK.glp_set_mat_row(prob, constraint, idx-1, ind, val);
			}
			
/*			//flow reserv2
  			for (int k=0;k<reqLinksNum;k++){
				IloLinearNumExpr capReq = cplex.linearNumExpr();
				for (int w=0;w<augGraph;w++){
					capReq.addTerm(1,f[k][o[k]][w]);
					capReq.addTerm(-1,f[k][w][o[k]]);
				}
				double reCap = 0;
				reCap=tmpReqLinks[o[k]][d[k]];
				cplex.addEq(capReq,reCap); 
			}*/
			
		//	constraint 4.3
			for (int k=0;k<reqLinkCount; k++){
				int constraint = GLPK.glp_add_rows(prob, 1);
				GLPK.glp_set_row_name(prob, constraint, "r_(4.3)_"+constraint);
				GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, -tmpReqLinks[o[k]][d[k]], -tmpReqLinks[o[k]][d[k]]);
				ind = GLPK.new_intArray(augNodeCount*2+1);
				val = GLPK.new_doubleArray(augNodeCount*2+1);
				int idx = 1;
				for (int w=0;w<augNodeCount;w++){
					if((d[k]) == w) {continue;}
					GLPK.intArray_setitem(ind, idx, f[k][d[k]][w]);
					GLPK.intArray_setitem(ind, idx+1, f[k][w][d[k]]);
					GLPK.doubleArray_setitem(val, idx, 1);
					GLPK.doubleArray_setitem(val, idx+1, -1);
					idx += 2;
				}
				GLPK.glp_set_mat_row(prob, constraint, idx-1, ind, val);
			}

/*			//flow reserv3
			for (int k=0;k<reqLinksNum;k++){
				IloLinearNumExpr capReq = cplex.linearNumExpr();
				for (int w=0;w<augGraph;w++){
					capReq.addTerm(1,f[k][d[k]][w]);
					capReq.addTerm(-1,f[k][w][d[k]]);
				}
				double reCap = 0;
				reCap=-tmpReqLinks[o[k]][d[k]];
				cplex.addEq(capReq,reCap); 
			}*/
			
			GLPK.glp_write_lp(prob, (new glp_cpxcp()), "ncm.problem.txt");
			GLPK.glp_set_obj_dir(prob, GLPKConstants.GLP_MIN);
			GLPK.glp_set_obj_name(prob, "ncm obj");
			
			glp_iocp iocp = new glp_iocp();
			GLPK.glp_init_iocp(iocp);
			iocp.setPresolve(GLPKConstants.GLP_ON);
			int ret = GLPK.glp_intopt(prob, iocp);
			
			String nameProb = GLPK.glp_get_obj_name(prob);
			double valProb =  GLPK.glp_get_obj_val(prob);
			
			if (ret != 0) {
				GLPK.glp_delete_prob(prob);
				denial++;
				denied=true;
				System.out.println("Node Mapping Denial");
			}else {
				nameProb = GLPK.glp_get_obj_name(prob);
				valProb =  GLPK.glp_get_obj_val(prob);
		
				Double[][][] xVar = new Double[reqLinkCount][augNodeCount][augNodeCount];
				Double[][][] fVar = new Double[reqLinkCount][augNodeCount][augNodeCount];
					for (int k=0; k<reqLinkCount; k++){
						for (int u=0; u<augNodeCount; u++){
							for (int v=0; v<augNodeCount; v++){
								if(x[k][u][v] != 0) {
									xVar[k][u][v] = GLPK.glp_mip_col_val(prob, x[k][u][v]); 
								}
								else { 
									xVar[k][u][v] = null;
								}		
								if(f[k][u][v] != 0) {
									fVar[k][u][v] = GLPK.glp_mip_col_val(prob, f[k][u][v]);
								}
								else { 
									fVar[k][u][v] = null;
								}
							}
						}
					}		
				
				NodeMapping = dNodeMapping(tmpSubNodesList, substrate, tmpReqNodes,reqLinkCount, xVar, fVar);

				GLPK.glp_delete_prob(prob);
	
				if (NodeMapping==null){
				      denial++;
				      algo_denials++;
				      System.out.println("denial B   " +denial);
				}
				else {
					
				tmpSubNodesList = 	(ArrayList<Node>) getNodes(substrateCopy.getGraph());
				Collections.sort(tmpSubNodesList,new NodeComparator());
				
				glp_prob prob1 = GLPK.glp_create_prob();
				//GLPK.glp_set_prob_name(prob1, "NCM Intern");
				//GLPK._glp_lpx_set_real_parm(prob1, GLPK.LPX_K_TMLIM, 600);
				//GLPK._glp_lpx_set_real_parm(prob1, GLPK.LPX_K_OBJUL, Double.MAX_VALUE);
				//GLPK._glp_lpx_set_real_parm(prob1, GLPK.LPX_K_OBJLL, -1*Double.MAX_VALUE);
				
				int[][][] f_mcf = new int[reqLinkCount][][];
				
				//create f continuous variable, with bounds lb and ub
				//////////////////////////////////////////////////////////////////
				for(int k=0; k<reqLinkCount; k++) {
					f_mcf[k] = new int[subNodeCount][];
					for (int u=0; u<subNodeCount; u++){
						f_mcf[k][u] = new int[subNodeCount];
						for (int v=0; v<subNodeCount; v++){
							f_mcf[k][u][v] = GLPK.glp_add_cols(prob1, 1);
							name = "f_mcf[" + k + "," + u + "," + v + "]";
							GLPK.glp_set_col_name(prob1, f_mcf[k][u][v], name);
							GLPK.glp_set_col_kind(prob1, f_mcf[k][u][v], GLPKConstants.GLP_CV);
							GLPK.glp_set_col_bnds(prob1, f_mcf[k][u][v], GLPKConstants.GLP_DB, 0, Double.MAX_VALUE);
						}
					}
				}
				
				/*for (int k=0;k<reqLinksNum;k++){
					f_mcf[k]=new IloNumVar[initSubNodeNum][];
					for (int i=0;i<initSubNodeNum;i++){
						f_mcf[k][i]=cplex1.numVarArray(initSubNodeNum, 0, Double.MAX_VALUE);
					}
				}*/
				
				//Objective function
				for (int i=0; i<subNodeCount; i++) { 
					for (int j=0; j<subNodeCount; j++) {
						for (int k =0; k<reqLinkCount; k++) {				
							GLPK.glp_set_obj_coef(prob1, f_mcf[k][i][j], 1.0/(augCapTable[i+reqNodeCount][j+reqNodeCount]+Double.MIN_VALUE));
						}
					}
				}
				
				/*IloLinearNumExpr flows_mcf = cplex1.linearNumExpr();

				for (int i=0;i<initSubNodeNum; i++){ 
					for (int j=0;j<initSubNodeNum; j++){
						for (int k =0; k<reqLinksNum; k++) {
							//flows_mcf.addTerm(1.0, f_mcf[k][i][j]);
							flows_mcf.addTerm(1.0/(augCapTable[i+reqNodeNum][j+reqNodeNum]+Double.MIN_VALUE), f_mcf[k][i][j]);
						}
					}
				}
				
*/				
				
				

				int[] o2=new int[reqLinkCount];
				int[] d2=new int[reqLinkCount];



				for (int i=0; i<o.length;i++){
					if (NodeMapping.containsKey(tmpReqNodesList.get(o[i]))){
						o2[i]=NodeMapping.get(tmpReqNodesList.get(o[i])).getId()-reqNodeCount;
					}
				}
				for (int i=0; i<d.length;i++){
					if (NodeMapping.containsKey(tmpReqNodesList.get(d[i]))){
						d2[i]=NodeMapping.get(tmpReqNodesList.get(d[i])).getId()-reqNodeCount;
					}
				}
				
				//flow reservation1
				for(int k=0;k<reqLinkCount; k++) {
					for(int i=0;i<subNodeCount; i++) {
						if(o2[k]!=i && d2[k]!=i) {
							int constraint = GLPK.glp_add_rows(prob1, 1);
							GLPK.glp_set_row_name(prob1, constraint, "r_(f1)_"+constraint);
							GLPK.glp_set_row_bnds(prob1, constraint, GLPKConstants.GLP_FX, 0, 0);
							ind = GLPK.new_intArray(subNodeCount*2+1);
							val = GLPK.new_doubleArray(subNodeCount*2+1);
							int len = 1;
							for(int j=0; j<subNodeCount; j++) {
								if (i!=j){
									GLPK.intArray_setitem(ind, len, f_mcf[k][i][j]);
									GLPK.intArray_setitem(ind, len+1, f_mcf[k][j][i]);
									GLPK.doubleArray_setitem(val, len, 1);
									GLPK.doubleArray_setitem(val, len+1, -1);
									len += 2;
								}
							}
							GLPK.glp_set_mat_row(prob1, constraint, len-1, ind, val);
						}
					}
				}
		
				
/*				//flow reservation1
				for (int k=0;k<reqLinksNum;k++){
					for (int i=0;i<initSubNodeNum;i++){
						IloLinearNumExpr capReq = cplex1.linearNumExpr();
						//if ((o2[k]-reqNodeNum!=i)&&(d2[k]-reqNodeNum!=i)){
						if ((o2[k]!=i)&&(d2[k]!=i)){
							for (int j=0;j<initSubNodeNum;j++){
								capReq.addTerm(1,f_mcf[k][i][j]);
								capReq.addTerm(-1,f_mcf[k][j][i]);
							}
						}
						cplex1.addEq(capReq,0);
					}
				}
				*/

				//flow reservation2
				for(int k=0;k<reqLinkCount; k++) {
					int constraint = GLPK.glp_add_rows(prob1, 1);
					GLPK.glp_set_row_name(prob1, constraint, "r_(f2)_"+constraint);
					GLPK.glp_set_row_bnds(prob1, constraint, GLPKConstants.GLP_FX, tmpReqLinks[o[k]][d[k]], tmpReqLinks[o[k]][d[k]]);
					ind = GLPK.new_intArray(subNodeCount*2+1);
					val = GLPK.new_doubleArray(subNodeCount*2+1);
					
					int idx = 1;
					for(int i=0;i<subNodeCount;i++) {
						if(o2[k] == i) {
							continue;
						}
						GLPK.intArray_setitem(ind, idx, f_mcf[k][o2[k]][i]);
						GLPK.intArray_setitem(ind, idx+1, f_mcf[k][i][o2[k]]);
						GLPK.doubleArray_setitem(val, idx, 1);
						GLPK.doubleArray_setitem(val, idx+1, -1);
						idx += 2;
					}
					GLPK.glp_set_mat_row(prob1, constraint, idx-1, ind, val);
				}
				
/*			//flow reservation2
			for(int k=0;k<reqLinksNum;k++){
				IloLinearNumExpr capReq = cplex1.linearNumExpr();
				for (int i=0;i<initSubNodeNum;i++){
					capReq.addTerm(1,f_mcf[k][o2[k]][i]);
					//capReq.addTerm(1,f_mcf[k][o2[k]-reqNodeNum][i]);
					capReq.addTerm(-1,f_mcf[k][i][o2[k]]);
					//capReq.addTerm(-1,f_mcf[k][i][o2[k]-reqNodeNum]);
				}
				double reCap = 0;
				reCap=tmpReqLinks[o[k]][d[k]];
				cplex1.addEq(capReq,reCap);
			}*/
				
				//flow reservation3
				for (int k=0;k<reqLinkCount; k++){
					int constraint = GLPK.glp_add_rows(prob1, 1);
					GLPK.glp_set_row_name(prob1, constraint, "r_(f3)_"+constraint);
					GLPK.glp_set_row_bnds(prob1, constraint, GLPKConstants.GLP_FX, -tmpReqLinks[o[k]][d[k]], -tmpReqLinks[o[k]][d[k]]);
					ind = GLPK.new_intArray(subNodeCount*2+1);
					val = GLPK.new_doubleArray(subNodeCount*2+1);
					int idx = 0;
					for (int i=0;i<subNodeCount;i++){
						if(d2[k] == i) {
							continue;
						}
						GLPK.intArray_setitem(ind, idx, f_mcf[k][d2[k]][i]);
						GLPK.intArray_setitem(ind, idx+1, f_mcf[k][i][d2[k]]);
						GLPK.doubleArray_setitem(val, idx, 1);
						GLPK.doubleArray_setitem(val, idx+1, -1);
						idx += 2;
					}
					GLPK.glp_set_mat_row(prob1, constraint, idx-1, ind, val);
				}

/*			//flow reservation3
			for(int k=0;k<reqLinksNum;k++){
				IloLinearNumExpr capReq = cplex1.linearNumExpr();
				for (int i=0;i<initSubNodeNum;i++){
					capReq.addTerm(1,f_mcf[k][d2[k]][i]);
					//capReq.addTerm(1,f_mcf[k][d2[k]-reqNodeNum][i]);
					capReq.addTerm(-1,f_mcf[k][i][d2[k]]);
					//capReq.addTerm(-1,f_mcf[k][i][d2[k]-reqNodeNum]);
				}
				double reCap = 0;
				reCap=-tmpReqLinks[o[k]][d[k]];
				cplex1.addEq(capReq,reCap);
			}*/
	
				//Link constraint
				for (int i=0;i<subNodeCount; i++){
					for (int j=0;j<subNodeCount; j++){
						if (i == j) {
							continue;
						}
						double capacity = augCapTable[reqNodeCount+i][reqNodeCount+j];
						int constraint = GLPK.glp_add_rows(prob1, 1);
						GLPK.glp_set_row_name(prob1, constraint, "r_(lc)_"+constraint);
						GLPK.glp_set_row_bnds(prob1, constraint, GLPKConstants.GLP_UP, 0, capacity);
						ind = GLPK.new_intArray(2*reqLinkCount+1);
						val = GLPK.new_doubleArray(2*reqLinkCount+1);
						int idx = 0;
						
						for (int k=0; k<reqLinkCount; k++) {
							GLPK.intArray_setitem(ind, idx, f_mcf[k][i][j]);
						    GLPK.intArray_setitem(ind, idx+1, f_mcf[k][j][i]);
						    GLPK.doubleArray_setitem(val, idx, 1);
						    GLPK.doubleArray_setitem(val, idx+1, 1);
						    idx += 2;
						}
						
						GLPK.glp_set_mat_row(prob1, constraint, idx-1, ind, val);
					}		
				}
/*				
				//Link constraint
				for (int i=0;i<initSubNodeNum; i++){
					for (int j=0;j<initSubNodeNum; j++){
						IloNumExpr expr1 =cplex1.prod(f_mcf[0][0][0],0);
						IloNumExpr flowSum =cplex1.prod(f_mcf[0][0][0],0);
						for (int k=0;k<reqLinksNum; k++){
							flowSum = cplex1.sum(cplex1.prod(f_mcf[k][i][j],1), 
									cplex1.prod(f_mcf[k][j][i],1));
							expr1 =cplex1.sum(expr1,flowSum);
						}
						double capacity =augCapTable[reqNodeNum+i][reqNodeNum+j];
						cplex1.addLe(expr1,capacity);
					}
				}*/

				GLPK.glp_write_lp(prob1, (new glp_cpxcp()), "ncm.intern.problem.txt");
				GLPK.glp_set_obj_name(prob1, "NCM Intern Problem obj");
				GLPK.glp_set_obj_dir(prob1, GLPKConstants.GLP_MIN);
				
				glp_iocp iocp1 = new glp_iocp();
				GLPK.glp_init_iocp(iocp1);
				iocp1.setPresolve(GLPKConstants.GLP_ON);
				int ret1 = GLPK.glp_intopt(prob1, iocp1);
				
				if ( ret1 == 0 ) {				
					//Update the residual bandwidth
					
					double[][][] fi=new double[subVN.getGraph().getEdgeCount()][substrate.getGraph().getVertexCount()][substrate.getGraph().getVertexCount()];
					
					for(int k=0;k<reqLinkCount;k++) {
						for(int i=0;i<subNodeCount;i++) {
							for(int j=0;j<subNodeCount;j++) {
								if(f_mcf[k][i][j] != 0) {
									fi[k][i][j] = GLPK.glp_mip_col_val(prob1, f_mcf[k][i][j]);
								} else {
									fi[k][i][j] = 0;
								}
							}
						}
					}

/*					for (int k=0;k<reqLinksNum;k++){
						for (int i=0;i<initSubNodeNum;i++){
							for (int j=0;j<initSubNodeNum;j++){
								fi[k][i][j]=cplex1.getValue(f_mcf[k][i][j]);
							}
						}
					}
					*/
					
					splittable_flows = LinkToPath(fi, subVN, substrate, subNodeCount);
										
								
				}///cplex1
				else{
					denial++;
					denials2++;
					System.out.println("denial C "  +denial);
					denied=true;
				}
				
				GLPK.glp_delete_prob(prob1);
				}//end of the NodeMapping != null
			}//end of the cplex.solve=true

			} catch (Exception e) {
			System.err.println("Concert exception caught: " + e);
			}			


			ResourceMapping resMap=subVN.getRMap();

			Collection c = NodeMapping.entrySet();
			Iterator itr = c.iterator();
			
			while(itr.hasNext()){
				 Map.Entry entry = (Map.Entry)itr.next();
				 ((Node)entry.getValue()).setId(((Node)entry.getValue()).getId()-subVN.getGraph().getVertexCount());
			}
			
			if (!denied){
				phi_total.put(substrate.getId(),NodeMapping);
				flow_total.put(substrate.getId(), splittable_flows);

			}else{
				subVN.getRMap().denied();
				denial++;
			}
			

			}// end of substrates
		}// end of sub requests
	}// end of check

	double cost=0;
	double revenue=0;
 	double avgHops=0;
 	
 	
 	
	//	if all sub_requests are successfully embedded 
	if (denial==0){
		
		for (Substrate substrate: substrates){

			for (Request subVN: request.getSubReq()){

				if (subVN.getInP()==substrate.getId()){

					subVN.getRMap().accepted();

					ResourceMapping resMap=subVN.getRMap();
					Results results=new Results(substrate);

					//	update the node capacities in each substrate
					Set entries = phi_total.entrySet();
					@SuppressWarnings("rawtypes")
					Iterator iterator = entries.iterator();
					while (iterator.hasNext()) {
						@SuppressWarnings("rawtypes")
						Map.Entry entry = (Map.Entry)iterator.next();
						if (((String) entry.getKey())==subVN.getInP()){
							LinkedHashMap<Node, Node> pi= ((LinkedHashMap<Node, Node>) entry.getValue());

							resMap.setNodeMapping(pi);
							subVN.getRMap().reserveNodes(substrate);

						}
					}	

					//update the link capacities in each substrate
					if (subVN.getGraph().getVertexCount()>1){
						@SuppressWarnings("rawtypes")
						Set entries2 = flow_total.entrySet();
						@SuppressWarnings("rawtypes")
						Iterator iterator2 = entries2.iterator();
						while (iterator2.hasNext()) {
							@SuppressWarnings("rawtypes")
							Map.Entry entry2 = (Map.Entry)iterator2.next();
							if (((String) entry2.getKey())==subVN.getInP()){
								LinkedHashMap<Link, List<Path>> flow= (LinkedHashMap<Link, List<Path>>) entry2.getValue();
								subVN.getRMap().setLinkMapping(flow);
								subVN.getRMap().reserveLinks(substrate);
								cost=results.Cost_Embedding(flow, subVN);
								revenue=results.Generate_Revenue(subVN);
								avgHops=results.avgHops(flow);
							}
						}
					}

					subVN.setRMap(resMap);

					retres[req_counter][1]+=cost;
					retres[req_counter][2]+=avgHops;
					retres[req_counter][9]+=revenue;


				}
			}

		}
		
		//If there are more than one substrates include the extra cost and hops due to the interlinks used
		if (InPs !=null){
			retres[req_counter][1]+=InterCost_Embedding(request,InPs);
			retres[req_counter][2]+=InterHop_Embedding(request, InPs);
		}

		double inter_cost=0;


		retres[req_counter][2]=retres[req_counter][2]/request.getGraph().getEdgeCount();
	}
	else{
		denial=1;
		retres[req_counter][0]=denial;
		request.getRMap().denied();
		for (Request subVN : request.getSubReq()){
			subVN.getRMap().denied();
		}
	}
	

	}//end of requests
	return retres; 
}		
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Collection<Node> getNodes(Graph<Node,Link> t) {
		ArrayList<Node> reqNodes =new ArrayList<Node>();
		@SuppressWarnings("unused")
		Collection<Link> edges =  t.getEdges();
		
		for (Node x: t.getVertices())
			reqNodes.add(x);


		return reqNodes;
	}
	
	public double[][] getCapTable(Graph<Node,Link> t) {
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
			tab[node2][node1]=cap;
			//table.put(currentNodes, cap);
		}
		return tab;
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

		
	public LinkedHashMap<String, Integer> getType (@SuppressWarnings("rawtypes") ArrayList list) {
		LinkedHashMap<String, Integer>  tmp = new LinkedHashMap<String, Integer> ();


	    for (int i=0;i<list.size();i++){   
		     if ((list.get(i) instanceof Server) ) {
		    	 	if (tmp.containsKey("Server")==false)
		    	 		tmp.put("Server", 1);
		    	 	else 	    	 
		    	 		tmp.put("Server", tmp.get("Server")+1);
		    	 }
		     else  if ((list.get(i) instanceof SubstrateRouter) ) {
		    	 	if (tmp.containsKey("SubstrateRouter")==false)
		    	 		tmp.put("SubstrateRouter", 1);
		    	 	else 	    	 
		    	 		tmp.put("SubstrateRouter", tmp.get("SubstrateRouter")+1);
		    	 }
		     else if ((list.get(i) instanceof SubstrateSwitch) ) {
		    	 	if (tmp.containsKey("SubstrateSwitch")==false)
		    	 		tmp.put("SubstrateSwitch", 1);
		    	 	else 	    	 
		    	 		tmp.put("SubstrateSwitch", tmp.get("SubstrateSwitch")+1);
		    	 }
		     else if ((list.get(i) instanceof VirtualMachine) ){
		    	 	if (tmp.containsKey("VirtualMachine")==false)
		    	 		tmp.put("VirtualMachine", 1);
		    	 	else 	    	 
		    	 		tmp.put("VirtualMachine", tmp.get("VirtualMachine")+1);
		    	 }
		    	 
		     else if ((list.get(i) instanceof RequestRouter) ){
		    	 	if (tmp.containsKey("RequestRouter")==false)
		    	 		tmp.put("RequestRouter", 1);
		    	 	else 	    	 
		    	 		tmp.put("RequestRouter", tmp.get("RequestRouter")+1);
		    	 }
		     else if ((list.get(i) instanceof RequestSwitch) ) {
		    	 	if (tmp.containsKey("RequestSwitch")==false)
		    	 		tmp.put("RequestSwitch", 1);
		    	 	else 	    	 
		    	 		tmp.put("RequestSwitch", tmp.get("RequestSwitch")+1);
		    	 }


		   }
		    
	    return tmp;

		}

		public ArrayList<ArrayList<Integer>> createArray (@SuppressWarnings("rawtypes") ArrayList list) {
			 ArrayList<ArrayList<Integer>>  tmp = new  ArrayList<ArrayList<Integer>>();
			 
			 	 
			 for (int i=0;i <MAX_TYPES;i++)
				 tmp.add(new ArrayList<Integer>());
			 


			  for (int i=0;i<list.size();i++){   
				     if ((list.get(i) instanceof Server) ) {
				    	 tmp.get(0).add(i);
				    	 }
				     else  if ((list.get(i) instanceof SubstrateRouter) ) {
				    	 	tmp.get(1).add(i);
				    	 }
				     else if ((list.get(i) instanceof SubstrateSwitch) ) {
				    	 tmp.get(2).add(i);
				    	 }
				     else if ((list.get(i) instanceof VirtualMachine) ){
				    	 	tmp.get(0).add(i);
				    	 	}
				    	 
				     else if ((list.get(i) instanceof RequestRouter) ){
				    	 	tmp.get(1).add(i);
				    	 	}
				     else if ((list.get(i) instanceof RequestSwitch) ) {
				    	 	tmp.get(2).add(i);
				    	 	}


				   }
					
				return tmp;

			}

		public int[][] getCapacities (@SuppressWarnings("rawtypes") ArrayList list){
			int[][] tmp =  new int[3][list.size()];

			for (int i=0;i<list.size();i++){  
			if ((list.get(i) instanceof VirtualMachine) ){
				tmp[0][i]= ((VirtualMachine)list.get(i)).getCpu();
				tmp[1][i]=((VirtualMachine)list.get(i)).getMemory();
				tmp[2][i]=((VirtualMachine)list.get(i)).getDiskSpace();
			}
			if ((list.get(i) instanceof RequestRouter) ){
				tmp[0][i]=((RequestRouter)list.get(i)).getCpu();
				tmp[1][i]=((RequestRouter)list.get(i)).getMemory();
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
				tmp[0][i]=((SubstrateRouter)list.get(i)).getCpu();
				tmp[1][i]=((SubstrateRouter)list.get(i)).getMemory();
				}
			if ((list.get(i) instanceof SubstrateSwitch) ){
				tmp[0][i]=((SubstrateSwitch)list.get(i)).getCpu();
				tmp[1][i]=((SubstrateSwitch)list.get(i)).getMemory();
				}

			}

			return tmp;
		}
		
		
	public 	LinkedHashMap<Node, Node> deterministicNodeMapping(ArrayList<Node> substrate, Substrate realSub, Collection<Node> request, int reqLinksNum, double[][][] xVar, double[][][] fVar){
			LinkedHashMap<Node, Node> phi = new LinkedHashMap<Node, Node>(); // request-real
			//augmented substrate
			double[] fai=new double[realSub.getGraph().getVertexCount()];
			double[] pai=new double[realSub.getGraph().getVertexCount()];
			boolean ctrl=true;
			for (Node node:request){
				double max=0;
				int index_max=0;
				Node selected=null;
				for (Node subNode:substrate){
					if (node.getType().equalsIgnoreCase(subNode.getType())){
						for (int k=0;k<reqLinksNum;k++){
								pai[subNode.getId()-request.size()]=pai[subNode.getId()-request.size()]+(fVar[k][node.getId()][subNode.getId()]+fVar[k][subNode.getId()][node.getId()])*xVar[k][node.getId()][subNode.getId()];
						}
						if (pai[subNode.getId()-request.size()]>max&&fai[subNode.getId()-request.size()]==0){
							max=pai[subNode.getId()-request.size()];
							index_max=subNode.getId()-request.size();
							selected=subNode;
						}
					}
				}
				if (max!=0){
					fai[index_max]=1;
					phi.put(node, selected);
				}
				else{
					System.out.println("node can not be mapped");
					ctrl=false;
				}
			}
			if (ctrl==true)
				return phi;
			else
				return null;
		}
	
	public 	LinkedHashMap<Node, Node> dNodeMapping(ArrayList<Node> substrate, Substrate realSub, Collection<Node> request, int reqLinksNum, Double[][][] xVar, Double[][][] fVar){
		LinkedHashMap<Node, Node> phi = new LinkedHashMap<Node, Node>(); // request-real
		//augmented substrate
		double[] fai=new double[realSub.getGraph().getVertexCount()];
		double[] pai=new double[realSub.getGraph().getVertexCount()];
		boolean ctrl=true;
		for (Node node:request){
			double max=0;
			int index_max=0;
			Node selected=null;
			for (Node subNode:substrate){
				if (node.getType().equalsIgnoreCase(subNode.getType())){
					for (int k=0;k<reqLinksNum;k++){
							pai[subNode.getId()-request.size()]=pai[subNode.getId()-request.size()]+(fVar[k][node.getId()][subNode.getId()]+fVar[k][subNode.getId()][node.getId()])*xVar[k][node.getId()][subNode.getId()];
					}
					if (pai[subNode.getId()-request.size()]>max&&fai[subNode.getId()-request.size()]==0){
						max=pai[subNode.getId()-request.size()];
						index_max=subNode.getId()-request.size();
						selected=subNode;
					}
				}
			}
			if (max!=0){
				fai[index_max]=1;
				phi.put(node, selected);
			}
			else{
				System.out.println("node can not be mapped");
				ctrl=false;
			}
		}
		if (ctrl==true)
			return phi;
		else
			return null;
	}
	
	public static int[] Check(int[] nodeMapping, int[][] match,ArrayList<ArrayList<Integer>> same, double[][] nodeCost, double[][][][] linkCost, Request req, int reqNodeNum, int inp_no, String algo){
		boolean ctrl=false;
		while (ctrl==false){
			ctrl=true;
			for (int i=0;i<reqNodeNum;i++){
				for (int k=0;k<inp_no;k++){
					int counter=0;
					for (int j=0;j<same.get(i).size();j++){
						if (nodeMapping[same.get(i).get(j)]==k) counter++;
						//if the number of identical virtual nodes embedded in the same InP > number of matches,
						//make the cost of the extra nodes infinity
						if (counter>match[k][i]){
							nodeCost[k][same.get(i).get(j)]=Integer.MAX_VALUE;
							ctrl=false;
						}
					}
				}
			}
			//if there is a wrong splitting, re-do the splitting with the new costs
			if (ctrl==false){
				PartitioningAlgorithms split2=new PartitioningAlgorithms(nodeCost, linkCost, req, inp_no);
				if (algo=="RRP")
					nodeMapping=split2.ILS();
				else
					nodeMapping=split2.Exact();
			}
			cost costos=new cost(req, substrates);
			double[][] demand = getBandTable(req.getGraph());
			//compute the new cost
			double cost_exact=costos.SplitCost(reqNodeNum, nodeCost, linkCost, nodeMapping, demand);
			//if the new cost is infinity embedding is not possible return null 
			if (cost_exact>20000){
				ctrl=true;
				nodeMapping=null;
			}
		}

		return nodeMapping;
}
	
	//Returns identical virtual nodes
	@SuppressWarnings("rawtypes")
	public ArrayList getSame(Request req){
		ArrayList<ArrayList<Integer>> temp=new ArrayList<ArrayList<Integer>>();
		
		for (int i=0;i<req.getGraph().getVertexCount();i++){
			temp.add(new ArrayList<Integer>());
		}
		
		for (Node n1:req.getGraph().getVertices()){
			for (Node n2:req.getGraph().getVertices()){
					if ((n1.getType()==n2.getType())&&(n1.getOS()==n2.getOS())&&(n1.getVEType()==n2.getVEType()))
						temp.get(n1.getId()).add(n2.getId());
			}
		}
		
		return temp;
		
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
	 
	 public double InterCost_Embedding(Request req, Substrate InPs){
		 	double cost=0;		 
		 	int inp_no = InPs.getGraph().getVertexCount();
		 
		 	int[][] hopsinp = InterHops(InPs);
		 
			//compute the flows that traverse InPs
			int[][] interlink=new int[inp_no][inp_no];
			int[][] interhops=new int[inp_no][inp_no];
			for (Link link:req.getGraph().getEdges()){
				Pair<Node> x=req.getGraph().getEndpoints(link);
				int inp_first=0;
				int inp_second=0;
				int counter=0;
				for (Request subVN: req.getSubReq()){
					if ((subVN!=null)&&(subVN.getGraph().getVertices().contains(x.getFirst())))
						inp_first=counter;
					if ((subVN!=null)&&(subVN.getGraph().getVertices().contains(x.getSecond())))
						inp_second=counter;
					counter++;
				}
				if(inp_first!=inp_second){
					interlink[inp_first][inp_second]+=link.getBandwidth();
					interhops[inp_first][inp_second]++;
				}				
			}
		 
		 for (int i=0;i<interlink.length;i++){
			 for (int j=0;j<interlink.length;j++){
				 cost=cost+(interlink[i][j]*hopsinp[i][j]);
			 }
		 }

		 return cost;
	 }
	 
	 public double InterHop_Embedding(Request req, Substrate InPs){
		 	double hops=0;		 
		 	int inp_no = InPs.getGraph().getVertexCount();
		 
		 	int[][] hopsinp = InterHops(InPs);
		 
			//compute the flows that traverse InPs
			int[][] interlink=new int[inp_no][inp_no];
			int[][] interhops=new int[inp_no][inp_no];
			for (Link link:req.getGraph().getEdges()){
				Pair<Node> x=req.getGraph().getEndpoints(link);
				int inp_first=0;
				int inp_second=0;
				int counter=0;
				for (Request subVN: req.getSubReq()){
					if ((subVN!=null)&&(subVN.getGraph().getVertices().contains(x.getFirst())))
						inp_first=counter;
					if ((subVN!=null)&&(subVN.getGraph().getVertices().contains(x.getSecond())))
						inp_second=counter;
					counter++;
				}
				if(inp_first!=inp_second){
					interlink[inp_first][inp_second]+=link.getBandwidth();
					interhops[inp_first][inp_second]++;
				}				
			}
		 
			//Include into the average hop number the hops of the inter-links
			for (int i=0;i<interlink.length;i++){
				for (int j=0;j<interlink.length;j++){
					hops+=interhops[i][j]*hopsinp[i][j];
				}
			}

		 return hops;
	 }
	 
	 public LinkedHashMap<Link, List<Path>> LinkToPath(double[][][] fi, Request subVN, Substrate substrate,int initSubNodeNum){
		 
		 LinkedHashMap<Link, List<Path>> splittable_flows = new LinkedHashMap<Link, List<Path>>();
			for (Link link: subVN.getGraph().getEdges()){
				List<Path> paths = new ArrayList<Path>();
				ArrayList<Integer> number_of_paths = new ArrayList<Integer>();
				for (int i=0;i<initSubNodeNum;i++){
					for (int j=0;j<initSubNodeNum;j++){
						if (fi[link.getId()][i][j]!=0){
							int value = (int) fi[link.getId()][i][j];
							if (number_of_paths.size()==0){
								number_of_paths.add(value);
							}
							else{
								boolean exist = false;
								for (int k=0;k<number_of_paths.size();k++){
									if (number_of_paths.get(k)==value){
										exist = true;
									}
								}
								if (exist==false) number_of_paths.add(value);
							}
						}
					}
				}
														
				for (int k=0;k<number_of_paths.size();k++){
					Path path = new Path(link.getId());
					path.setName(link.getName());
					List<Link> substrateLinks = new ArrayList<Link>();
					for (int i=0;i<initSubNodeNum;i++){
						for (int j=0;j<initSubNodeNum;j++){
							if (fi[link.getId()][i][j]!=0){
								if (fi[link.getId()][i][j] == number_of_paths.get(k)){
									for (Link edge: substrate.getGraph().getEdges()){
										Pair<Node> pair = substrate.getGraph().getEndpoints(edge);
										if (((pair.getFirst().getId()==i)&&(pair.getSecond().getId()==j))||
												((pair.getFirst().getId()==j)&&(pair.getSecond().getId()==i))){
											substrateLinks.add(edge);
											path.setBandwidth(number_of_paths.get(k));
										}
									}
								}
							}
						}
					}
					path.setSubstrateLinks(substrateLinks);
					paths.add(path);
				}			
				splittable_flows.put(link, paths);				
			}
			
			return splittable_flows;
	 }
	 
		
	}
	

