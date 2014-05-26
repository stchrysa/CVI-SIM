package gui.components.tables;

import gui.SimulatorConstants;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This class controls the rendering of the tables in the main window
 */
public class SimulatorTableRenderer extends DefaultTableCellRenderer {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(  
			JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int col)
	{
		Component comp = super.getTableCellRendererComponent(table,  value, isSelected, hasFocus, row, col);
		
		/** Setting background color in function of the state of the resource
		 * (if row not selected)
		 * Ready = green
		 * Available = white
		 * Assigned = green 
		 * Rejected = red **/
		String s =  table.getModel().getValueAt(row, table.getModel().getColumnCount()-1).toString();
		if (!isSelected) {
			if (s.equalsIgnoreCase(SimulatorConstants.STATUS_AVAILABLE))
				comp.setBackground(Color.white);
			else if (s.equalsIgnoreCase(SimulatorConstants.STATUS_READY))
				comp.setBackground(Color.cyan);
			else if (s.equalsIgnoreCase(SimulatorConstants.STATUS_ASSIGNED))
				comp.setBackground(Color.green);
			else if (s.equalsIgnoreCase(SimulatorConstants.STATUS_REJECTED))
				comp.setBackground(Color.red);
			else
				comp.setBackground(Color.white);
			comp.setForeground(Color.black);
		}
		
		return comp;
	}

	
}
