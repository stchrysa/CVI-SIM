package gui.components.tables;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.Substrate;


/**
 * This class controls data model of the substrates jtable
 */
public class SubstratesTableModel extends DefaultTableModel {

	private List<Substrate> substrates;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SubstratesTableModel(String[] col, int i, List<Substrate> substrates) {
		super(col,i);
		this.substrates = substrates;
	}

	public boolean isCellEditable(int x, int y) {
		return false;
	}

	/** refresh the table model **/
	public void refreshTableData() {
		this.setRowCount(0);
		for (Substrate substrate : substrates) {
			this.addRow(new Object[]{substrate.getId(),substrate.getState()});
		}
	}
	
}
