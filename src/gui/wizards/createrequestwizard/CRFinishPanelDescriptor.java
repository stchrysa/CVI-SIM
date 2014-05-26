package gui.wizards.createrequestwizard;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import gui.SimulatorConstants;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections15.Factory;

import model.NetworkGraph;
import model.Request;
import model.RequestLinkFactory;
import model.RequestNodeFactory;
import model.components.Link;
import model.components.Node;

import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;

import com.nexes.wizard.WizardPanelDescriptor;

public class CRFinishPanelDescriptor extends WizardPanelDescriptor {
	
	public static final String IDENTIFIER = "CR_FINISH_PANEL";
	
	// Random Request parameters
	private String prefix;
	private int numRequests;
	private int minNodes;
	private int maxNodes;
	private String linkConnectivity;
	private int minLinks;
	private int maxLinks;
	private double linkProbability;
	private String timeDistribution;
	private int fixStart;
	private int uniformMin;
	private int uniformMax;
	private int normalMean;
	private int normalVariance;
	private int poissonMean;
	private int lifetimeMin;
	private int lifetimeMax;
	List<Request> requests;
	
	// Random generation parameters
	RequestNodeFactory nodeFactory;
	RequestLinkFactory linkFactory;
	
	CRFinishPanel finishPanel;
    
    public CRFinishPanelDescriptor() { 	
    	finishPanel = new CRFinishPanel();
    	setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(finishPanel);
    }
    
    public Object getNextPanelDescriptor() {
        return FINISH;
    }
    
    public Object getBackPanelDescriptor() {
        return CRRandomPanelDescriptor.IDENTIFIER;
    }
    
    public void aboutToDisplayPanel() {

    	requests = new ArrayList<Request>();
    	// getting parameters from wizard
    	if (((CRWizard) this.getWizard()).getMethod()
    			.equals(SimulatorConstants.RANDOM_REQUEST)) {
	    	prefix = ((CRWizard) getWizard()).getPrefix();
	    	numRequests = ((CRWizard) getWizard()).getNumRequests();
	    	minNodes = ((CRWizard) getWizard()).getMinNodes();
	    	maxNodes = ((CRWizard) getWizard()).getMaxNodes();
	    	linkConnectivity = ((CRWizard) getWizard()).getLinkConnectivity();
	    	if (this.linkConnectivity.equals(SimulatorConstants.LINK_PER_NODE_CONNECTIVITY)) {
		    	minLinks = ((CRWizard) getWizard()).getMinLinks();
		    	maxLinks = ((CRWizard) getWizard()).getMaxLinks();
	    	} else {
	    		linkProbability = ((CRWizard) getWizard()).getLinkProbability();
	    	}
	    	timeDistribution = ((CRWizard) getWizard()).getTimeDistribution();
	    	if (this.timeDistribution.equals(SimulatorConstants.FIXED_DISTRIBUTION))
	    		fixStart = ((CRWizard) getWizard()).getFixStart();
	    	else if (this.timeDistribution.equals(SimulatorConstants.UNIFORM_DISTRIBUTION)) {
	    		uniformMin = ((CRWizard) getWizard()).getUniformMin();
	    		uniformMax = ((CRWizard) getWizard()).getUniformMax();
	    	}
	    	else if (this.timeDistribution.equals(SimulatorConstants.NORMAL_DISTRIBUTION)) {
	    		normalMean = ((CRWizard) getWizard()).getNormalMean();
	    		normalVariance = ((CRWizard) getWizard()).getNormalVariance();
	    	}
	    	else
	    	poissonMean = ((CRWizard) getWizard()).getPoissonMean();
	    	lifetimeMin = ((CRWizard) getWizard()).getDurationMin();
	    	lifetimeMax = ((CRWizard) getWizard()).getDurationMax();
	    	
    	}
    	
        getWizard().setNextFinishButtonEnabled(false);
        getWizard().setBackButtonEnabled(false);
        getWizard().setCancelButtonEnabled(true);
        
    }
    
    public void aboutToHidePanel() {
        // nothing to do
    }
    
    /** Create requests **/
    public void displayingPanel() {

    	
    	if (((CRWizard) this.getWizard()).getMethod()
    			.equals(SimulatorConstants.RANDOM_REQUEST)) {
    		
    		// launch random request generation
    		randomRequestGeneration();
    	}
    	
    	// set generated requests
    	((CRWizard) getWizard()).setRequests(requests);
    }

	private void randomRequestGeneration() {
	
		Thread t = new Thread() {
			
	        @SuppressWarnings({ "rawtypes", "unchecked" })
			public void run() {

	        	
	        	int startDate = 0;
	        	int lifetime = 0;
	        	
	        	Poisson poisson = null;
	        	if (timeDistribution.equals(SimulatorConstants.POISSON_DISTRIBUTION)) {
		        	RandomEngine engine = new DRand();
		        	poisson = new Poisson(poissonMean, engine);
	        	}
	        	
				int progress = 0;
				int notCreated = 0;
				finishPanel.setProgressMinimum(0);
				finishPanel.setProgressMaximum(numRequests);
				finishPanel.setProgressValue(0);
				finishPanel.addProgressText(Color.BLACK, "Creating requests...\n");
				for (int i=0; i<numRequests; i++) {
					Request request = new Request(prefix+i);			
					
					// Random num of nodes inside range (minNodes-maxNodes)
					int numNodes = minNodes + (int)(Math.random()*((maxNodes - minNodes) + 1));
					// Random num of links inside range (minLinks-maxLinks)
					int numLinks = minLinks + (int)(Math.random()*((maxLinks - minLinks) + 1));
					
					
					boolean ctrl = false;
					while (ctrl==false){
					
					/** Selecting the random generation algorithm
					 * depending on the link connectivity
					 */
					
					SparseMultigraph<Node, Link> g = null;
					nodeFactory = new RequestNodeFactory();
					linkFactory = new RequestLinkFactory();
					
					if (linkConnectivity.equals(SimulatorConstants.LINK_PER_NODE_CONNECTIVITY)) {
						//Random Graph Generation
						Factory<Graph<Node, Link>> graphFactory = new Factory<Graph<Node, Link>>() {
							public UndirectedGraph<Node, Link> create() {
								return new NetworkGraph();
							}
						};
						//Barabasi-Albert generation
						BarabasiAlbertGenerator<Node, Link> randomGraph = new BarabasiAlbertGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, 1, numLinks, new HashSet<Node>());
						randomGraph.evolveGraph(numNodes-1);
						g = (SparseMultigraph<Node, Link>) randomGraph.create();
					}
					else if (linkConnectivity.equals(SimulatorConstants.PERCENTAGE_CONNECTIVITY)) {
						//Random Graph Generation
						Factory<UndirectedGraph<Node, Link>> graphFactory = new Factory<UndirectedGraph<Node, Link>>() {
							public UndirectedGraph<Node, Link> create() {
								return new NetworkGraph();
							}
						};
						//ErdosRenyiGenerator generation
						ErdosRenyiGenerator<Node, Link> randomGraph = new ErdosRenyiGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, numNodes, linkProbability);
						g = (SparseMultigraph<Node, Link>) randomGraph.create();
						//Remove unconnected nodes
						((NetworkGraph) g).removeUnconnectedNodes();
						//Remove disconnected graphs
						WeakComponentClusterer<Node, Link> wcc = new WeakComponentClusterer<Node, Link>();
						Set<Set<Node>> nodeSets = wcc.transform(g);
						Collection<SparseMultigraph<Node, Link>> gs = FilterUtils.createAllInducedSubgraphs(nodeSets, g); 
						if (gs.size()>1) {
							// Lets get the biggest disconnected graph
							Iterator itr = gs.iterator();
							g = (NetworkGraph)itr.next();
							while (itr.hasNext()) {
								SparseMultigraph<Node, Link> nextGraph = (SparseMultigraph<Node, Link>) itr.next();
								if (nextGraph.getVertexCount()>g.getVertexCount())
									g = (NetworkGraph)nextGraph;
							}
						}
						/*// do not add if g is empty
						if (g.getVertexCount()!=numNodes) {
							notCreated++;
							progress++;
							finishPanel.setProgressValue(progress);
							finishPanel.addProgressText(Color.RED, request.getId()+" not created. " +
									"It has been generated without links\n");
							continue;
						}
						else {
							// Change id of nodes to consecutive int (0,1,2,3...)
							Iterator itr = g.getVertices().iterator();
							int id = 0;
							while (itr.hasNext()) {
								((Node) itr.next()).setId(id);
								id++;
							}
							// refresh nodeFactory's nodeCount
							nodeFactory.setNodeCount(id);
							// Change id of edges to consecutive int (0,1,2,3...)
							itr = g.getEdges().iterator();
							id = 0;
							while (itr.hasNext()) {
								((Link) itr.next()).setId(id);
								id++;
							}
							// refresh linkFactory's linkCount
							linkFactory.setLinkCount(id);
						}*/
					}
					
					/*// do not add request if topology is wrong
					if (!((NetworkGraph)g).hasCorrectTopology()) {
						notCreated++;
						progress++;
						finishPanel.setProgressValue(progress);
						finishPanel.addProgressText(Color.RED, request.getId()+" not created. " +
								"It has been generated with incorrect topology\n");
						continue;
					}*/
					
					if (g.getVertexCount()==numNodes){
						ctrl=true;
						request.setGraph(g);
						request.setNodeFactory(nodeFactory);
						request.setLinkFactory(linkFactory);
						
						// Random start/end dates
	 
						// Duration of the request
						lifetime = lifetimeMin + (int)(Math.random()*((lifetimeMax - lifetimeMin) + 1));
						
						// All requests start at month fixStart
						if (timeDistribution.equals(SimulatorConstants.FIXED_DISTRIBUTION)) {
							request.setStartDate(fixStart);
							request.setEndDate(fixStart+lifetime);
						}
						
						// Random: Uniform distribution
						if (timeDistribution.equals(SimulatorConstants.UNIFORM_DISTRIBUTION)) {
							startDate = uniformMin + (int)(Math.random()*((uniformMax - uniformMin) + 1));
							request.setStartDate(startDate);
							request.setEndDate(startDate+lifetime);
						}
						
						// Random: Normal distribution
						if (timeDistribution.equals(SimulatorConstants.NORMAL_DISTRIBUTION)) {
							Random random = new Random();
							startDate = (int) (normalMean + random.nextGaussian() * normalVariance);
							if (startDate<0)
								startDate*=-1;
							request.setStartDate(startDate);
							request.setEndDate(startDate+lifetime);
						}
	
						// Random: Poisson distribution
						if (timeDistribution.equals(SimulatorConstants.POISSON_DISTRIBUTION)) {
							startDate = poisson.nextInt();
							request.setStartDate(startDate);
							request.setEndDate(startDate+lifetime);
						}
					}
					
				}
				
					// Domain
					// Not defined
					
					requests.add(request);
					progress++;
					finishPanel.setProgressValue(progress);
					finishPanel.addProgressText(Color.BLACK, "Request "+request.getId()+
						" successfully created\n");
				}
				finishPanel.addProgressText(Color.BLACK, "Summary:\n");
				finishPanel.addProgressText(Color.BLACK, numRequests-notCreated+" requests successfully created\n");
				finishPanel.addProgressText(Color.BLACK, notCreated+" requests not created\n");
				finishPanel.getProgressDescription().setEditable(false);
				
				getWizard().setNextFinishButtonEnabled(true);
				getWizard().setCancelButtonEnabled(false);
	        }
        };

        t.start();
		
	}
  
}
