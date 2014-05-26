package gui.components.tables;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.Request;

/**
 * This class controls data model of the requests jtable
 */
public class RequestsTableModel extends DefaultTableModel {

	private List<Request> requests;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RequestsTableModel(String[] col, int i, List<Request> requests) {
		super(col,i);
		this.requests = requests;
	}

	public boolean isCellEditable(int x, int y) {
		return false;
	}

	/** refresh the table model **/
	public void refreshTableData() {
		this.setRowCount(0);
		for (Request request : requests) {
			this.addRow(new Object[]{request.getId(),
					request.getStartDate(),
					request.getEndDate(),request.getState()});
		}
	}
	
}
