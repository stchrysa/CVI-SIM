package gui.wizards.createsimulationwizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Request;

import com.nexes.wizard.WizardPanelDescriptor;

public class CSRequestsPanelDescriptor extends WizardPanelDescriptor implements ActionListener, 
			MouseListener, ListSelectionListener {
	
	public static final String IDENTIFIER = "CS_REQUESTS_PANEL";
	
	CSRequestsPanel requestsPanel;
	List<Request> requests;
	List<Request> selectedRequests;
    
    public CSRequestsPanelDescriptor(List<Request> requests) {
    	this.requests = requests;
    	requestsPanel = new CSRequestsPanel(requests);
    	requestsPanel.addListeners(this, this, this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(requestsPanel);
    }
    
    // Decide next wizard panel
    public Object getNextPanelDescriptor() {
		return CSSubstratePanelDescriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        return null;
    }
    
    public void aboutToDisplayPanel() {
        setNextButtonEnabled();
    }    

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        requestsPanel.performButtonAction(button);
        requestsPanel.updateButtons();
    	setNextButtonEnabled();
    }
    
    /** Attend doble click on a list **/
	@SuppressWarnings("rawtypes")
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		JList theList = (JList) mouseEvent.getSource();
		if (mouseEvent.getClickCount() == 2) {
			int index = theList.locationToIndex(mouseEvent.getPoint());
			if (index >= 0) {
				requestsPanel.doubleClickPerformed(theList,index);
				setNextButtonEnabled();
			}
		}
	}
	
	/** Attend list selection **/
	@Override
	public void valueChanged(ListSelectionEvent evt) {
		if (evt.getValueIsAdjusting())
	          return;
		requestsPanel.updateButtons();
	}

    private void setNextButtonEnabled() {
    	if (requestsPanel.canFinish()) {
    		// Setting requests on the wizard
    		selectedRequests = requestsPanel.getSelectedRequests();
    		((CSWizard) getWizard()).setSelectedRequests(selectedRequests);
    		System.out.println("Selected requests: "+selectedRequests.size());
    		getWizard().setNextFinishButtonEnabled(true);
    	}
    	else
    		getWizard().setNextFinishButtonEnabled(false);         
    }
		
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
