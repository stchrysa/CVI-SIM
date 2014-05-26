package gui.wizards.createsimulationwizard;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import model.Request;

/** Show the ID of the element for JList that uses this Render **/
class RequestListCellRenderer extends DefaultListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, 
            Object value,
            int index, boolean isSelected,
            boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		Request r = (Request)value;
		setText(r.getId());
		return this;
	}
}
