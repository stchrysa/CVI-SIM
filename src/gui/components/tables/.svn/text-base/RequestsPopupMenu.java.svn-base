package gui.components.tables;

import gui.Icons;
import gui.SimulatorConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import controller.GUIController;

import model.Request;
import model.Simulator;

public class RequestsPopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Request> selectedRequests;
	Simulator simulator;
	JPanel simulatorContentPane;
	JTable requestsTable;
	JTabbedPane graphViewerTabbedPane;
	
	public RequestsPopupMenu(final Simulator simulator, 
			final JPanel simulatorContentPane,
			final JTabbedPane graphViewerTabbedPane,
			final List<Request> selectedRequests, final JTable requestsTable) {
		super();
		this.selectedRequests = selectedRequests;
		this.simulator = simulator;
		this.simulatorContentPane = simulatorContentPane;
		this.requestsTable = requestsTable;
		this.graphViewerTabbedPane = graphViewerTabbedPane;
		JMenuItem addToSimulationItem = new JMenuItem("Add selected requests to simulation", Icons.ADD);
		this.add(addToSimulationItem);
		JMenuItem removeFromSimulationItem = new JMenuItem("Remove selected requests from simulation");
		this.add(removeFromSimulationItem);
		JMenuItem removeItem = new JMenuItem("Remove selected requests", Icons.DELETE);
		this.add(removeItem);
		
		if (!requestsStatus(SimulatorConstants.STATUS_AVAILABLE)) {
			addToSimulationItem.setEnabled(false);
			removeItem.setEnabled(false);
		} 
		if (!requestsStatus(SimulatorConstants.STATUS_READY)) 
			removeFromSimulationItem.setEnabled(false);
		
		// Add Listeners
		addToSimulationItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Error control: check that is created a simulation,
				//if not, show a popupmenu with the error notification
				if (simulator.getSimulation()==null) {
					JOptionPane.showMessageDialog(simulatorContentPane,
							"There is no simulation running",
		                    "No simulation running", 1);
					return;
				}
				GUIController.addRequestsToSimulation(selectedRequests, 
						simulator, requestsTable);
			}
		});
		removeFromSimulationItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIController.removeRequestsFromSimulation(selectedRequests, 
						simulator, requestsTable);
			}
		});
		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIController.removeRequests(selectedRequests, 
						simulator, 
						graphViewerTabbedPane, 
						requestsTable, null);
			}
		});

	}

	private boolean requestsStatus(String status) {
		for (Request request : selectedRequests) {
			if (!request.getState().equals(status))
				return false;
		}
		return true;
	}
	
}