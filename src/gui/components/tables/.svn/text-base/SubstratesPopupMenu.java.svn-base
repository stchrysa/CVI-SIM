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

import model.Simulator;
import model.Substrate;

public class SubstratesPopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Substrate> selectedSubstrates;
	Simulator simulator;
	JPanel simulatorContentPane;
	JTable substratesTable;
	JTabbedPane graphViewerTabbedPane;
	
	public SubstratesPopupMenu(final Simulator simulator, 
			final JPanel simulatorContentPane,
			final JTabbedPane graphViewerTabbedPane,
			final List<Substrate> selectedSubstrates, final JTable substratesTable) {
		super();
		this.selectedSubstrates = selectedSubstrates;
		this.simulator = simulator;
		this.simulatorContentPane = simulatorContentPane;
		this.substratesTable = substratesTable;
		this.graphViewerTabbedPane = graphViewerTabbedPane;
		
		JMenuItem toSimulationItem = new JMenuItem("Set as simulation substrate", Icons.ADD);
		this.add(toSimulationItem);
		if (selectedSubstrates.size()>1) {
			toSimulationItem.setEnabled(false);
		}
		JMenuItem removeItem = new JMenuItem("Remove selected substrates", Icons.DELETE);
		this.add(removeItem);
		if (!substratesStatus(SimulatorConstants.STATUS_AVAILABLE)) {
			removeItem.setEnabled(false);
		} 
		
		// Add Listeners
		toSimulationItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Error control: check that is created a simulation,
				// if not, show a popupmenu with the error notification
				if (simulator.getSimulation()==null) {
					JOptionPane.showMessageDialog(simulatorContentPane,
							"There is no simulation running",
		                    "No simulation running", 1);
					return;
				}
				GUIController.changeSimulationSubstrate(selectedSubstrates, 
						simulator, substratesTable);
			}
		});
		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIController.removeSubstrates(selectedSubstrates, 
						simulator, 
						graphViewerTabbedPane, 
						substratesTable, null);
			}
		});

	}

	private boolean substratesStatus(String status) {
		for (Substrate substrate : selectedSubstrates) {
			if (!substrate.getState().equals(status))
				return false;
		}
		return true;
	}
	
}