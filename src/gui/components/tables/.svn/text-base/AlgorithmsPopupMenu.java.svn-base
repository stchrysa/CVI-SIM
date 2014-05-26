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

import model.Algorithm;
import model.Simulator;

public class AlgorithmsPopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Algorithm> selectedAlgorithms;
	Simulator simulator;
	JPanel simulatorContentPane;
	JTable algorithmsTable;
	JTabbedPane graphViewerTabbedPane;
	
	public AlgorithmsPopupMenu(final Simulator simulator, 
			final JPanel simulatorContentPane,
			final JTabbedPane graphViewerTabbedPane,
			final List<Algorithm> selectedAlgorithms, final JTable algorithmsTable) {
		super();
		this.selectedAlgorithms = selectedAlgorithms;
		this.simulator = simulator;
		this.simulatorContentPane = simulatorContentPane;
		this.algorithmsTable = algorithmsTable;
		this.graphViewerTabbedPane = graphViewerTabbedPane;
		
		JMenuItem toSimulationItem = new JMenuItem("Set as simulation algorithm", Icons.ADD);
		this.add(toSimulationItem);
		if (selectedAlgorithms.size()>1) {
			toSimulationItem.setEnabled(false);
		}
		JMenuItem removeItem = new JMenuItem("Remove selected algorithms", Icons.DELETE);
		this.add(removeItem);
		if (!algorithmsStatus(SimulatorConstants.STATUS_AVAILABLE)) {
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
				GUIController.changeSimulationAlgorithm(selectedAlgorithms, 
						simulator, algorithmsTable);
			}
		});
		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIController.removeAlgorithms(selectedAlgorithms, 
						simulator, 
						graphViewerTabbedPane, 
						algorithmsTable, null);
			}
		});

	}

	private boolean algorithmsStatus(String status) {
		for (Algorithm alg : selectedAlgorithms) {
			if (!alg.getState().equals(status))
				return false;
		}
		return true;
	}
	
}