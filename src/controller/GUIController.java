package controller;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections15.Factory;

import model.Algorithm;
import model.NetworkGraph;
import model.Request;
import model.Simulation;
import model.Simulator;
import model.Substrate;
import model.SubstrateLinkFactory;
import model.SubstrateNodeFactory;
import model.components.Link;
import model.components.Node;
import model.components.SubstrateRouter;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import gui.SimulatorConstants;
import gui.components.tables.AlgorithmsTableModel;
import gui.components.tables.RequestsTableModel;
import gui.components.tables.SubstratesTableModel;
import gui.simulation.SimulationFrame;
import gui.simulation.SimulationWork;
import gui.wizards.createrequestwizard.CRFinishPanelDescriptor;
import gui.wizards.createrequestwizard.CRRandomPanelDescriptor;
import gui.wizards.createrequestwizard.CRSelectionPanelDescriptor;
import gui.wizards.createrequestwizard.CRWizard;
import gui.wizards.createsimulationwizard.CSAlgorithmPanelDescriptor;
import gui.wizards.createsimulationwizard.CSRequestsPanelDescriptor;
import gui.wizards.createsimulationwizard.CSSubstratePanelDescriptor;
import gui.wizards.createsimulationwizard.CSWizard;
import gui.wizards.createsubstratewizard.CSFinishPanelDescriptor;
import gui.wizards.createsubstratewizard.CSImportPanelDescriptor;
import gui.wizards.createsubstratewizard.CSRandomPanelDescriptor;
import gui.wizards.createsubstratewizard.CSSelectionPanelDescriptor;
import gui.wizards.createsubstratewizard.CSuWizard;

import com.nexes.wizard.WizardPanelDescriptor;

public class GUIController {

	/** Launch CreateRequestWizard 
	 * @param simulator 
	 * @param requestsTable **/
	public static void launchCreateRequestWizard(Simulator simulator, JTable requestsTable) {
		
		CRWizard wizard = new CRWizard(simulator);
        wizard.getDialog().setTitle("Create requests wizard");
        
        WizardPanelDescriptor descriptor1 = new CRSelectionPanelDescriptor();
        wizard.registerWizardPanel(CRSelectionPanelDescriptor.IDENTIFIER, descriptor1);

        WizardPanelDescriptor descriptor2 = new CRRandomPanelDescriptor();
        wizard.registerWizardPanel(CRRandomPanelDescriptor.IDENTIFIER, descriptor2);

        WizardPanelDescriptor descriptor3 = new CRFinishPanelDescriptor();
        wizard.registerWizardPanel(CRFinishPanelDescriptor.IDENTIFIER, descriptor3);
        
        wizard.setCurrentPanel(CRSelectionPanelDescriptor.IDENTIFIER);

        int ret = wizard.showModalDialog();
        if (ret==0) {
        	// Add requests to the simulator
        	simulator.addRequests(wizard.getRequests());
        	// Refresh requests table
	        for (Request request : wizard.getRequests()) {
				((DefaultTableModel) requestsTable.getModel()).addRow(new Object[]{request.getId(),
						request.getStartDate(),request.getEndDate(),request.getState()});
			}
        }
        
        System.out.println("Dialog return code is (0=Finish,1=Cancel,2=Error): " + ret);
	}

	/** Launch CreateSubstrateWizard 
	 * @param simulator  
	 * @param substratesTable **/
	public static void launchCreateSubstrateWizard(Simulator simulator,
			JTable substratesTable) {
		CSuWizard wizard = new CSuWizard(simulator);
        wizard.getDialog().setTitle("Create substrates wizard");
        
        WizardPanelDescriptor descriptor1 = new CSSelectionPanelDescriptor();
        wizard.registerWizardPanel(CSSelectionPanelDescriptor.IDENTIFIER, descriptor1);

        WizardPanelDescriptor descriptor2 = new CSRandomPanelDescriptor();
        wizard.registerWizardPanel(CSRandomPanelDescriptor.IDENTIFIER, descriptor2);
        
        WizardPanelDescriptor descriptor3 = new CSImportPanelDescriptor();
        wizard.registerWizardPanel(CSImportPanelDescriptor.IDENTIFIER, descriptor3);

        WizardPanelDescriptor descriptor4 = new CSFinishPanelDescriptor();
        wizard.registerWizardPanel(CSFinishPanelDescriptor.IDENTIFIER, descriptor4);
        
        wizard.setCurrentPanel(CSSelectionPanelDescriptor.IDENTIFIER);
        
        int ret = wizard.showModalDialog();
        if (ret==0) {
        	// Add substrates to the simulator
        	simulator.addSubstrates(wizard.getSubstrates());
        	// Refresh substrates table
	        for (Substrate substrate : wizard.getSubstrates()) {
				((DefaultTableModel) substratesTable.getModel()).addRow(new Object[]{substrate.getId(),
						substrate.getState()});
			}
        }
        
        System.out.println("Dialog return code is (0=Finish,1=Cancel,2=Error): " + ret);
	}
	
	/** Launch CreateSimulationWizard 
	 * @param simulator  
	 * @param simulatorContentPane
	 * @param requestsTable
	 * @param substratesTable
	 * @param algorithmsTable **/
	public static int launchCreateSimulationWizard(Simulator simulator, 
			JPanel simulatorContentPane, 
			JTable requestsTable, JTable substratesTable, 
			JTable algorithmsTable) {
		
		int ret = 0;
		
		// Error control: check that not exists a simulation, exist requests, substrates and algorithms. If not, popupmessage.
		if (simulator.getSimulation()!=null) {
			// there is already a simulation running
			JOptionPane.showMessageDialog(simulatorContentPane, 
					"There is already a simulation running",
                    "Simulation already running", 1);
			return -1;
		}
		if (simulator.getRequests().size()==0) {
			// no available requests to create a simulation
			JOptionPane.showMessageDialog(simulatorContentPane, 
					"There are no available requests to create a simulation",
                    "Requests needed", 1);
			return -1;
		}
		if (simulator.getSubstrates().size()==0) {
			// no available substrates to create a simulation
			JOptionPane.showMessageDialog(simulatorContentPane, 
					"There are no available substrates to create a simulation",
                    "Substrate needed", 1);
			return -1;
		}
		if (simulator.getAlgorithms().size()==0) {
			// no available algorithms to create a simulation
			JOptionPane.showMessageDialog(simulatorContentPane, 
					"There are no available algorithms to create a simulation",
                    "Algorithm needed", 1);
			return -1;
		}
		
		CSWizard wizard = new CSWizard();
        wizard.getDialog().setTitle("Create simulation wizard");
        

        WizardPanelDescriptor descriptor1 = new CSRequestsPanelDescriptor(simulator.getRequests());
        wizard.registerWizardPanel(CSRequestsPanelDescriptor.IDENTIFIER, descriptor1);

        WizardPanelDescriptor descriptor2 = new CSSubstratePanelDescriptor(simulator.getSubstrates());
        wizard.registerWizardPanel(CSSubstratePanelDescriptor.IDENTIFIER, descriptor2);
        
        WizardPanelDescriptor descriptor3 = new CSAlgorithmPanelDescriptor(simulator.getAlgorithms());
        wizard.registerWizardPanel(CSAlgorithmPanelDescriptor.IDENTIFIER, descriptor3);
        
        wizard.setCurrentPanel(CSRequestsPanelDescriptor.IDENTIFIER);
        
        ret = wizard.showModalDialog();
        
        if (ret==0) {
        	
       	
        	List<Substrate> substrates = new ArrayList<Substrate>();
        	substrates = wizard.getSelectedSubstrates();
        	
        	//Create an abstract graph where each node represent an InP
        	int inp_no=substrates.size();        	
			Substrate InPs=new Substrate("Networked Substrate Environment");
			
			
			//create a partially mesh topology for the InPs
			if (inp_no!=1){
				boolean ctrl=false;
				while (ctrl==false){
					InPs=createInPGraph(inp_no);
					if (InPs.getGraph().getVertexCount()==inp_no){
						ctrl=true;
					}
				}
			}
			
        	
        	// Add created simulation to the simulator
        	Simulation simulation = new Simulation(InPs, substrates,
        			wizard.getSelectedRequests(),
        			wizard.getSelectedAlgorithm());
        	simulator.setSimulation(simulation);
        	/** Change status of the requests, substrate and 
        	 * algorithm used for the new simulation **/
        	for (Request request : wizard.getSelectedRequests())
        		request.setState(SimulatorConstants.STATUS_READY);
        	for (Substrate sub: wizard.getSelectedSubstrates())
        		sub.setState(SimulatorConstants.STATUS_READY);
        	wizard.getSelectedAlgorithm().setState(SimulatorConstants.STATUS_READY);
        	/** Refresh views in order to show new status of the elements **/
        	((RequestsTableModel) requestsTable.getModel()).refreshTableData();
        	((SubstratesTableModel) substratesTable.getModel()).refreshTableData();
        	((AlgorithmsTableModel) algorithmsTable.getModel()).refreshTableData();
        }
        
        System.out.println("Dialog return code is (0=Finish,1=Cancel,2=Error): " + ret);
        
        return ret;
	}

	/** Launch simulation
	 * There will be launched the simulation with requests, substrate 
	 * and algorithm in the current simulation.
	 * @param simulator  
	 * @param requestsTable **/
	public static int launchLaunchSimulation(Simulator simulator, 
			final JPanel simulatorContentPane,
			JTable requestsTable) {
		
		Simulation simulation = simulator.getSimulation();
		
		// Error control:
		if (simulator.getSimulation().getRequests().size()==0) {
			// no ready requests to launch a simulation
			JOptionPane.showMessageDialog(simulatorContentPane, 
					"There are no requests ready to launch a simulation",
                    "Requests needed", 1);
			return -1;
		}
		
		int ret = 0;
		
		// Preparing simulation work (thread)
		final SimulationWork simulationWork = new SimulationWork(simulation, simulatorContentPane);

		// Setting visible the simulation progress & launching simulation
		SimulationFrame sf = new SimulationFrame(simulatorContentPane, 
				"Running simulation...", "Simulation progress", simulationWork);
		sf.pack();
		sf.setVisible(true);
						
		
	
		/** Refresh views **/
		((RequestsTableModel) requestsTable.getModel()).refreshTableData();
		
		return ret;
	}
	
	
	/** Add selected requests to the current simulation **/
	public static void addRequestsToSimulation(List<Request> selectedRequests,
			Simulator simulator, JTable requestsTable) {
		if (simulator.getSimulation()==null) return;
		simulator.getSimulation().addRequests(selectedRequests);
		/** Refresh views **/
		((RequestsTableModel) requestsTable.getModel()).refreshTableData();
		
	}

	/** Remove selected requests from the current simulation **/
	public static void removeRequestsFromSimulation(List<Request> selectedRequests, 
			Simulator simulator, JTable requestsTable) {
		if (simulator.getSimulation()==null) return;
		simulator.getSimulation().removeRequests(selectedRequests);
		/** Refresh views **/
		((RequestsTableModel) requestsTable.getModel()).refreshTableData();
	}

	/** Remove selected requests from the simulator
	 * if simulatorContentPane == null, action is called by right clicking
	 * and there is no error control needed. Else it is called by pressing
	 * delete and error control is needed **/
	public static void removeRequests(List<Request> selectedRequests,
			Simulator simulator, 
			JTabbedPane graphViewerTabbedPane,
			JTable requestsTable,
			JPanel simulatorContentPane) {
		
		if (simulatorContentPane!=null) {
			// check if selected requests can be removed (available)
			for (Request request : selectedRequests) {
				if (!request.getState().equals(SimulatorConstants.STATUS_AVAILABLE)) {
					JOptionPane.showMessageDialog(simulatorContentPane, 
							"Requests in a simulation can not be removed",
		                    "Simulation requests", 1);
					return;
				}
			}
		}
		
		simulator.removeRequests(selectedRequests);
		
		/** Refresh views **/
		// remove graph view (if opened)
		for (Request req : selectedRequests) {
			int index = graphViewerTabbedPane.indexOfTab(req.getId());
			if (index!=-1) {
				graphViewerTabbedPane.remove(index);
			}
		}
		// refresh requests table
		((RequestsTableModel) requestsTable.getModel()).refreshTableData();
		
		
	}

/*	*//** Change the substrate of the current simulation **/
	public static void changeSimulationSubstrate(
			List<Substrate> selectedSubstrates, Simulator simulator,
			JTable substratesTable) {
		if (simulator.getSimulation()==null) return;
		simulator.getSimulation().changeSubstrate(selectedSubstrates);
			/** Refresh views **/
		((SubstratesTableModel) substratesTable.getModel()).refreshTableData();
	}

	/** Remove selected substrates from the simulator
	 * if simulatorContentPane == null, action is called by right clicking
	 * and there is no error control needed. Else it is called by pressing
	 * delete and error control is needed **/
	public static void removeSubstrates(List<Substrate> selectedSubstrates,
			Simulator simulator, JTabbedPane graphViewerTabbedPane,
			JTable substratesTable,
			JPanel simulatorContentPane) {
		
		if (simulatorContentPane!=null) {
			// check if selected substrates can be removed (available)
			for (Substrate substrate : selectedSubstrates) {
				if (!substrate.getState().equals(SimulatorConstants.STATUS_AVAILABLE)) {
					JOptionPane.showMessageDialog(simulatorContentPane, 
							"Substrate in a simulation can not be removed",
		                    "Simulation substrate", 1);
					return;
				}
			}
		}
		
		simulator.removeSubstrates(selectedSubstrates);
		
		/** Refresh views **/
		// remove graph view (if opened)
		for (Substrate subs : selectedSubstrates) {
			int index = graphViewerTabbedPane.indexOfTab(subs.getId());
			if (index!=-1) {
				graphViewerTabbedPane.remove(index);
			}
		}
		// refresh substrates table
		((SubstratesTableModel) substratesTable.getModel()).refreshTableData();
	}

	/** Change the algorithm of the current simulation **/
	public static void changeSimulationAlgorithm(
			List<Algorithm> selectedAlgorithms, Simulator simulator,
			JTable algorithmsTable) {
		if (simulator.getSimulation()==null) return;
		simulator.getSimulation().changeAlgorithm(selectedAlgorithms.get(0));
		/** Refresh views **/
		((AlgorithmsTableModel) algorithmsTable.getModel()).refreshTableData();
	}
	
	/** Remove selected algorithms from the simulator
	 * if simulatorContentPane == null, action is called by right clicking
	 * and there is no error control needed. Else it is called by pressing
	 * delete and error control is needed **/
	public static void removeAlgorithms(List<Algorithm> selectedAlgorithms,
			Simulator simulator, JTabbedPane graphViewerTabbedPane,
			JTable algorithmsTable,
			JPanel simulatorContentPane) {
		
		if (simulatorContentPane!=null) {
			// check if selected algorithms can be removed (available)
			for (Algorithm algorithm : selectedAlgorithms) {
				if (!algorithm.getState().equals(SimulatorConstants.STATUS_AVAILABLE)) {
					JOptionPane.showMessageDialog(simulatorContentPane, 
							"Algorithm in a simulation can not be removed",
		                    "Simulation algorithm", 1);
					return;
				}
			}
		}
		
		simulator.removeAlgorithms(selectedAlgorithms);
		
		/** Refresh views **/
		// remove graph view (if opened)
		for (Algorithm alg : selectedAlgorithms) {
			int index = graphViewerTabbedPane.indexOfTab(alg.getId());
			if (index!=-1) {
				graphViewerTabbedPane.remove(index);
			}
		}
		// refresh algorithms table
		((AlgorithmsTableModel) algorithmsTable.getModel()).refreshTableData();
	}
	
public static Substrate createInPGraph(int inp_no){
	 	
	 	final String prefix ="Network Substrate Infrastructures";
		final Substrate substrate = new Substrate(prefix);
		//Number of nodes in the Infrastructure Provider
		int numNodes = inp_no;
		//Probability of a connection
		double linkProbability = 0.5;	
		SparseMultigraph<Node, Link> g = null;	
		SubstrateNodeFactory nodeFactory = new SubstrateNodeFactory();
		SubstrateLinkFactory linkFactory = new SubstrateLinkFactory();
			
		//Random Graph Generation
		Factory<UndirectedGraph<Node, Link>> graphFactory = new Factory<UndirectedGraph<Node, Link>>() {
			public UndirectedGraph<Node, Link> create() {
				return new NetworkGraph();
			}
		};
				
		//ErdosRenyiGenerator generation
		ErdosRenyiGenerator<Node, Link> randomGraph = new ErdosRenyiGenerator<Node, Link>(graphFactory, nodeFactory, linkFactory, numNodes, linkProbability );
		g = (SparseMultigraph<Node, Link>) randomGraph.create();
		//Remove unconnected nodes
		((NetworkGraph) g).removeUnconnectedNodes();
		// TODO remove disconnected graphs
		WeakComponentClusterer<Node, Link> wcc = new WeakComponentClusterer<Node, Link>();
		Set<Set<Node>> nodeSets = wcc.transform(g);
		Collection<SparseMultigraph<Node, Link>> gs = FilterUtils.createAllInducedSubgraphs(nodeSets, g); 
		if (gs.size()>1) {
				@SuppressWarnings("rawtypes")
				Iterator itr = gs.iterator();
				g = (NetworkGraph)itr.next();
				while (itr.hasNext()) {
				@SuppressWarnings("unchecked")
				SparseMultigraph<Node, Link> nextGraph = (SparseMultigraph<Node, Link>) itr.next();
			
				if (nextGraph.getVertexCount()>g.getVertexCount())
						g = (NetworkGraph)nextGraph;
				}
		}
				
		if (g.getVertexCount()>0){
				// Change id of nodes to consecutive int (0,1,2,3...)
				@SuppressWarnings("rawtypes")
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
		}
		
		if (g.getVertexCount()!=inp_no){
			createInPGraph(inp_no);
		}
		
		boolean ctrl= true;
		
		for (Node node: g.getVertices()){
			if (node instanceof SubstrateRouter){
				ctrl=false;
				break;
			}
		}
		
		if (ctrl==true){
			substrate.setGraph(g);
			substrate.setNodeFactory(nodeFactory);
			substrate.setLinkFactory(linkFactory);
		}
		else{			
			createInPGraph(inp_no);
		}
			

		
		return substrate;
}

}