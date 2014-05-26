package splitting;

import java.util.Collection;

import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_cpxcp;
import org.gnu.glpk.glp_iocp;
import org.gnu.glpk.glp_prob;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

import model.Request;
import model.components.Link;
import model.components.Node;

/**
 * DUMMY Algorithm Class.
 */

public class PartitioningAlgorithms {

	private double[][] nodeCost;
	private double[][][][] linkCost;
	private Request req=null;
	private int inp_no=0;

	public PartitioningAlgorithms(double[][] nodeCost, double[][][][] linkCost, Request req, int inp_no) {
		this.nodeCost=nodeCost;
		this.linkCost=linkCost;
		this.req=req;
		this.inp_no=inp_no;
	}

	
// Exact method from Houidi
	public int[] Exact() {
		
			int[] nodeMapping=new int[req.getGraph().getVertexCount()];
						
			//Number of req nodes
			int reqNodeNum=req.getGraph().getVertexCount();

			//Demands of virtual links to find the connectivity between nodes
			double[][] demands = getBandTable(req.getGraph());

			
			try {
			

				glp_prob prob = GLPK.glp_create_prob();
				GLPK.glp_set_prob_name(prob, "Partitioning-MIP");
				SWIGTYPE_p_int ind;
			    SWIGTYPE_p_double val;
			    
			    
				String name = null;
				int[][][][] y = new int[inp_no][][][];
				int[][] x = new int[inp_no][];
				
				//Create x continuous variables
				for (int i=0;i<inp_no;i++){
					x[i]= new int[reqNodeNum];
					for (int j=0;j<reqNodeNum;j++){
						x[i][j]= GLPK.glp_add_cols(prob,1);
						name = "x[" + i + "," + j +"]";
						GLPK.glp_set_col_name(prob, x[i][j], name);
					    GLPK.glp_set_col_kind(prob, x[i][j], GLPKConstants.GLP_BV);
					}
				}
				
				for(int k=0; k<inp_no; k++) {
					y[k] = new int[inp_no][][];
					for (int u=0; u<inp_no; u++){
						y[k][u] = new int[reqNodeNum][];
						for (int v=0; v<reqNodeNum; v++){
							y[k][u][v] =new int[reqNodeNum];
							for (int l=0;l<reqNodeNum;l++){
								y[k][u][v][l] = GLPK.glp_add_cols(prob, 1);
				                    name = "y[" + k + "," + u + "," + v + ","+l+"]";
				                    GLPK.glp_set_col_name(prob, y[k][u][v][l], name);
									GLPK.glp_set_col_kind(prob, y[k][u][v][l], GLPKConstants.GLP_BV);
							}
						}
					}
				}
				

				
/*				
				IloNumVar[][] x = new IloNumVar[inp_no][];
				for (int i=0;i<inp_no;i++){
					x[i]=cplex.numVarArray(reqNodeNum, 0, 1, IloNumVarType.Int);
				}*/
				
				
/*				IloNumVar[][][][] y=new IloNumVar[inp_no][][][];
				for (int i=0;i<inp_no;i++){
					y[i]=new IloNumVar[inp_no][][];
					for (int j=0;j<inp_no;j++){
						y[i][j]=new IloNumVar[reqNodeNum][];
						for (int k=0;k<reqNodeNum;k++){
							y[i][j][k]=cplex.numVarArray(reqNodeNum, 0, 1, IloNumVarType.Int);
						}
					}
				}*/
				
				// Objective first term	
				 for (int i=0; i<reqNodeNum; i++) { 
					 for (int j=0; j<inp_no; j++) {
						 GLPK.glp_set_obj_coef(prob, x[j][i], nodeCost[j][i]);
					 }
				 }
				
				
				/*//Built the first summation
				IloLinearNumExpr nodes = cplex.linearNumExpr();
				for (int i=0;i<reqNodeNum;i++){
					for (int j=0;j<inp_no;j++){
						nodes.addTerm(nodeCost[j][i],x[j][i]);
					}
				}*/
				 
				//Objective second term
				 for (int i=0; i<reqNodeNum; i++) { 
					 for (int ipr=i+1; ipr<reqNodeNum; ipr++) {
						 for (int j =0; j<inp_no; j++) {
							 for (int jpr=0;jpr<inp_no;jpr++){
								 GLPK.glp_set_obj_coef(prob, y[j][jpr][i][ipr], linkCost[j][jpr][i][ipr]);	 
							 }							
						 }
					 }
				 }				
				
/*				//Build the second summation
				IloLinearNumExpr links = cplex.linearNumExpr();

				for (int i=0;i<reqNodeNum;i++){
					for (int ipr=i+1;ipr<reqNodeNum;ipr++){
						for (int j=0;j<inp_no;j++){
							for (int jpr=0;jpr<inp_no;jpr++){
								links.addTerm(linkCost[j][jpr][i][ipr], y[j][jpr][i][ipr]);
							}
						}		
					}
				}*/
				
				 //Constraint 1
				 for (int i=0;i<reqNodeNum;i++){
					 for (int ipr=0;ipr<reqNodeNum;ipr++){
						 for (int j=0;j<inp_no;j++){
								 int constraint = GLPK.glp_add_rows(prob, 1);
								 GLPK.glp_set_row_name(prob, constraint, "r_(1)_"+constraint);
								 GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, 0, 0);
								 ind = GLPK.new_intArray(inp_no+2);
								 val = GLPK.new_doubleArray(inp_no+2);
								 int idx=1;
								 for (int jpr=0;jpr<inp_no;jpr++){
									 GLPK.intArray_setitem(ind, idx, y[j][jpr][i][ipr]);
									 GLPK.doubleArray_setitem(val, idx, 1);
									 idx++;
								 }
								 GLPK.intArray_setitem(ind, idx, x[j][i]);
								 GLPK.doubleArray_setitem(val, idx, -1);
								 GLPK.glp_set_mat_row(prob, constraint, inp_no+1, ind, val);
						 }
					 }
				 }
				 

				/*//Constraint 1
				for (int i=0;i<reqNodeNum;i++){
					for (int ipr=0;ipr<reqNodeNum;ipr++){
						for (int j=0;j<inp_no;j++){
							IloLinearNumExpr sum1 = cplex.linearNumExpr();
							for (int jpr=0;jpr<inp_no;jpr++){
								sum1.addTerm(y[j][jpr][i][ipr],1);
							}
							cplex.addEq(sum1, x[j][i]);
						}
					}
				}*/
				
				//	constraint 2
				for (int i=0;i<reqNodeNum;i++){
					for (int ipr=0;ipr<reqNodeNum;ipr++){
						for (int j=0;j<inp_no;j++){
							for (int jpr=0;jpr<inp_no;jpr++){
								if ((i==ipr)&&(j==jpr)){
									int constraint = GLPK.glp_add_rows(prob, 1);
									GLPK.glp_set_row_name(prob, constraint, "r_(2)_"+constraint);
									GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_UP, 1, 1);
									ind = GLPK.new_intArray(3);
									val = GLPK.new_doubleArray(3);
									GLPK.intArray_setitem(ind, 1, x[j][i]);
									GLPK.intArray_setitem(ind, 2, y[j][jpr][i][ipr]);
									GLPK.doubleArray_setitem(val, 1, 2);
									GLPK.doubleArray_setitem(val, 2, -1);
									GLPK.glp_set_mat_row(prob, constraint, 2, ind, val);
								}
								else{
									int constraint = GLPK.glp_add_rows(prob, 1);
									GLPK.glp_set_row_name(prob, constraint, "r_(2)_"+constraint);
									GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_UP, 1, 1);
									ind = GLPK.new_intArray(4);
									val = GLPK.new_doubleArray(4);
									GLPK.intArray_setitem(ind, 1, x[j][i]);
									GLPK.intArray_setitem(ind, 2, x[jpr][ipr]);
									GLPK.intArray_setitem(ind, 3, y[j][jpr][i][ipr]);
									GLPK.doubleArray_setitem(val, 1, 1);
									GLPK.doubleArray_setitem(val, 2, 1);
									GLPK.doubleArray_setitem(val, 3, -1);
									GLPK.glp_set_mat_row(prob, constraint, 3, ind, val);	
								}
								
							}
						}
					}
				}
				
				/*//Constraint 2
				for (int i=0;i<reqNodeNum;i++){
					for (int ipr=0;ipr<reqNodeNum;ipr++){
						for (int j=0;j<inp_no;j++){
							for (int jpr=0;jpr<inp_no;jpr++){
								IloNumExpr sum2= null;
								sum2 = cplex.sum(x[j][i],x[jpr][ipr]);
								sum2 = cplex.sum(sum2,cplex.prod(y[j][jpr][i][ipr], -1));
								cplex.addLe(sum2, 1);
							}
						}
					}
				}*/
				
				
				//Constraint 3
				for (int i=0;i<reqNodeNum;i++){
					int constraint = GLPK.glp_add_rows(prob, 1);
					GLPK.glp_set_row_name(prob, constraint, "r_(3)_"+constraint);
					GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, 1, 1);
					ind = GLPK.new_intArray(inp_no+1);
					val = GLPK.new_doubleArray(inp_no+1);
					int idx = 1;
					for (int j=0;j<inp_no;j++){
						GLPK.intArray_setitem(ind, idx, x[j][i]);
					    GLPK.doubleArray_setitem(val, idx, 1);
					    idx++;
					}
					GLPK.glp_set_mat_row(prob, constraint, inp_no, ind, val);
				}

				/*//Constraint 3
				for (int i=0;i<reqNodeNum;i++){
					IloLinearNumExpr sum3 = cplex.linearNumExpr();
					for (int j=0;j<inp_no;j++){
						sum3.addTerm(x[j][i], 1);
					}
					cplex.addEq(sum3, 1);
				}*/
				
				
				// Constraint 4
				for (int i=0;i<reqNodeNum;i++){
					for (int ipr=0;ipr<reqNodeNum; ipr++){
						for (int j=0;j<inp_no; j++) {
							for (int jpr=0;jpr<inp_no;jpr++){
								if ((i==ipr)&&(j==jpr)) {continue;}
								int constraint = GLPK.glp_add_rows(prob, 1);
								GLPK.glp_set_row_name(prob, constraint, "r_(4)_"+constraint);
								GLPK.glp_set_row_bnds(prob, constraint, GLPKConstants.GLP_FX, 0, 0);
								ind = GLPK.new_intArray(3);
								val = GLPK.new_doubleArray(3);
								GLPK.intArray_setitem(ind, 1, y[j][jpr][i][ipr]);
								GLPK.intArray_setitem(ind, 2, y[jpr][j][ipr][i]);
								GLPK.doubleArray_setitem(val, 1, 1);
								GLPK.doubleArray_setitem(val, 2, -1);
								GLPK.glp_set_mat_row(prob, constraint, 2, ind, val);								
							}
						}
					}
				}
				
				/*//Constraint 4
				for (int i=0;i<reqNodeNum;i++){
					for (int ipr=0;ipr<reqNodeNum;ipr++){
						for (int j=0;j<inp_no;j++){
							for (int jpr=0;jpr<inp_no;jpr++){
								cplex.addEq(y[j][jpr][i][ipr], y[jpr][j][ipr][i]);
							}
						}
					}
				}*/

				GLPK.glp_write_lp(prob, (new glp_cpxcp()), "partitioning.problem.txt");
				GLPK.glp_set_obj_dir(prob, GLPKConstants.GLP_MIN);
				GLPK.glp_set_obj_name(prob, "partitioning obj");
				
				glp_iocp iocp = new glp_iocp();
				GLPK.glp_init_iocp(iocp);
				iocp.setPresolve(GLPKConstants.GLP_ON);
				int ret = GLPK.glp_intopt(prob, iocp);
				
				String nameProb = GLPK.glp_get_obj_name(prob);
				double valProb =  GLPK.glp_get_obj_val(prob);
				
			
				if ( ret != 0 ) {
					GLPK.glp_delete_prob(prob);
					System.out.println("denial");
				}
				else {
					
					nameProb = GLPK.glp_get_obj_name(prob);
					valProb =  GLPK.glp_get_obj_val(prob);
			
					Double[][] xVar = new Double[inp_no][reqNodeNum];
					Double[][][][] yVar = new Double[inp_no][inp_no][reqNodeNum][reqNodeNum];
					
					for (int j=0; j<inp_no; j++){
						for (int i=0; i<reqNodeNum; i++){
							if(x[j][i] != 0) {
								xVar[j][i] = GLPK.glp_mip_col_val(prob, x[j][i]); 
							}
							else { 
								xVar[j][i] = null;
							}		
						}
					}
					
					for (int j=0; j<inp_no; j++){
						for (int jpr=0; jpr<inp_no; jpr++){
							for (int i=0; i<reqNodeNum; i++){
								for (int ipr=0;ipr<reqNodeNum;ipr++){
									if(y[j][jpr][i][ipr] != 0) {
										yVar[j][jpr][i][ipr] = GLPK.glp_mip_col_val(prob, y[j][jpr][i][ipr]); 
									}
									else { 
										yVar[j][jpr][i][ipr] = null;
									}		
								}
							}
						}
					}		
					
					/*double[][] xVar =new double [inp_no][reqNodeNum];
					for (int k=0;k<inp_no;k++){
						xVar[k] = cplex.getValues(x[k]);	
					}
					
					

					double[][][][] yVar =new double [inp_no][inp_no][reqNodeNum][reqNodeNum];
				    for (int k=0;k<inp_no;k++){
				     for (int l=0;l<inp_no;l++){
				      for (int z=0;z<reqNodeNum;z++){
				       for (int m=0;m<reqNodeNum;m++){
				       try{
				        yVar[k][l][z][m] = cplex.getValue(y[k][l][z][m]);
				       }catch(IloException e){
				        //System.out.println("error");
				       }
				       }
				      }
				     }
				    }
				    for (int k=0;k<inp_no;k++){
				     for (int l=0;l<inp_no;l++){
				      for (int z=0;z<reqNodeNum;z++){
				       for (int m=0;m<reqNodeNum;m++){
				       }
				      }
				     }
				    }
				    
*/
					for (int i=0;i<reqNodeNum;i++){
						for (int j=0;j<inp_no;j++){
							if (xVar[j][i]!=0){
								nodeMapping[i]=j;
							}
						}
					}
					
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
					
					for (int i=0;i<reqNodeNum;i++){
						System.out.printf("\t%d",nodeMapping[i]);
					}

					System.out.println("Exact_cost="+Exact_cost);

					GLPK.glp_delete_prob(prob);
					
					
				}

				
			} catch (Exception e) {
				System.err.println("Concert exception caught: " + e);
			}	

		return nodeMapping;
	}

	
	//recursive random permutations
	public int[] ILS() {
		
		int[] bestnodeMapping=new int[req.getGraph().getVertexCount()];
			//Number of req nodes
			int reqNodeNum=req.getGraph().getVertexCount();
			//Demands of virtual links to find the connectivity between nodes
			double[][] demands = getBandTable(req.getGraph());


						
			int trials=10*inp_no*reqNodeNum;
			
			//Initial Random Solution
			int[] nodeMapping=new int[reqNodeNum];

			double best_cost=Integer.MAX_VALUE;
			int maxExperiment=5;
			
		for (int qq=0;qq<maxExperiment;qq++){
			
			for (int i=0;i<reqNodeNum;i++){
				int initial=(int) (Math.random()*inp_no);
				nodeMapping[i]=initial;
			}
			


			for (int i=0;i<trials;i++){
					//compute current cost
					double cost=0;
					for (int q=0;q<reqNodeNum;q++){
						cost+=nodeCost[nodeMapping[q]][q];
					}
					
					for (int q=0;q<reqNodeNum;q++){
						for (int w=q;w<reqNodeNum;w++){
							if (demands[q][w]!=0){
								cost+=linkCost[nodeMapping[q]][nodeMapping[w]][q][w];
							}
						}
					}
					
					//compute cost after random mutation
					int randomNode=(int) (Math.random()*reqNodeNum);
					int randomINP=(int)(Math.random()*inp_no);
					
					double costPrime=0;
					for (int q=0;q<reqNodeNum;q++){
						if (q==randomNode){
							costPrime+=nodeCost[randomINP][q];
						}
						else{
							costPrime+=nodeCost[nodeMapping[q]][q];	
						}
					}
					
					for (int q=0;q<reqNodeNum;q++){
						for (int w=q;w<reqNodeNum;w++){
							if (demands[q][w]!=0){
								if (q==randomNode)
									costPrime+=linkCost[randomINP][nodeMapping[w]][q][w];
								if (w==randomNode)
									costPrime+=linkCost[nodeMapping[q]][randomINP][q][w];
								if (q!=randomNode && w!=randomNode)
									costPrime+=linkCost[nodeMapping[q]][nodeMapping[w]][q][w];
							}
						}
					}
					
					//if the cost is reduced accept the mutation 
					//otherwise accept it with a probability p
					if (costPrime<cost){
						nodeMapping[randomNode]=randomINP;
					}

				}
			

			
			double cost=0;
			for (int q=0;q<reqNodeNum;q++){
				cost+=nodeCost[nodeMapping[q]][q];
			}
			
			for (int q=0;q<reqNodeNum;q++){
				for (int w=q;w<reqNodeNum;w++){
					if (demands[q][w]!=0){
						cost+=linkCost[nodeMapping[q]][nodeMapping[w]][q][w];
					}
				}
			}
			
			if (cost<best_cost){
				best_cost=cost;
				bestnodeMapping=nodeMapping.clone();
			}

		}

	
	
		return bestnodeMapping;
	}
	
	
	
	
		
	///////////////////////////////////////////////////////////////////////////////////////////////////

	
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
	
	 public double Cost(int reqNodeNum, double[][] nodeCost, double[][][][] linkCost, int[] nodeMapping, double[][] demands){
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
		
		
}
	

