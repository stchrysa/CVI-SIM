package gui.components.tables;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.Algorithm;

/**
 * This class controls data model of the algorithms jtable
 */
public class AlgorithmsTableModel extends DefaultTableModel {

	private List<Algorithm> algorithms;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlgorithmsTableModel(String[] col, int i, List<Algorithm> algorithms) {
		super(col,i);
		this.algorithms = algorithms;
	}

	public boolean isCellEditable(int x, int y) {
		return false;
	}

	/** refresh the table model **/
	public void refreshTableData() {
		this.setRowCount(0);
		for (Algorithm algorithm : algorithms) {
			this.addRow(new Object[]{algorithm.getId(),algorithm.getState()});
		}
	}
	
}
