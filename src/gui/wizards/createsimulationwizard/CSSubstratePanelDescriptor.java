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

import model.Substrate;

import com.nexes.wizard.WizardPanelDescriptor;

public class CSSubstratePanelDescriptor extends WizardPanelDescriptor implements ActionListener, 
MouseListener, ListSelectionListener {
	
	public static final String IDENTIFIER = "CS_SUBSTRATE_PANEL";
	
	CSSubstratePanel substratePanel;
	List<Substrate> substrates;
	List<Substrate> selectedSubstrates;
    
    public CSSubstratePanelDescriptor(List<Substrate> substrates) {
    	this.substrates = substrates;
    	substratePanel = new CSSubstratePanel(substrates);
    	substratePanel.addListeners(this, this, this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(substratePanel);
    }
    
    // Decide next wizard panel
    public Object getNextPanelDescriptor() {
		return CSAlgorithmPanelDescriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        return CSRequestsPanelDescriptor.IDENTIFIER;
    }
    
    public void aboutToDisplayPanel() {
        setNextButtonEnabled();
    }    
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        substratePanel.performButtonAction(button);
        substratePanel.updateButtons();
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
				substratePanel.doubleClickPerformed(theList,index);
				setNextButtonEnabled();
			}
		}
	}
	
	/** Attend list selection **/
	@Override
	public void valueChanged(ListSelectionEvent evt) {
		if (evt.getValueIsAdjusting())
	          return;
		substratePanel.updateButtons();
	}

    private void setNextButtonEnabled() {
    	if (substratePanel.canFinish()) {
    		// Setting substrate on the wizard
    		selectedSubstrates = substratePanel.getSelectedSubstrates();
    		((CSWizard) getWizard()).setSelectedSubstrates(selectedSubstrates);
    		System.out.println("Selected substrates: "+selectedSubstrates.size());
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

