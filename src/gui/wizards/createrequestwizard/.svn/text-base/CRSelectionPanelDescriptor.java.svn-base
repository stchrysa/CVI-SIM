package gui.wizards.createrequestwizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nexes.wizard.WizardPanelDescriptor;

public class CRSelectionPanelDescriptor extends WizardPanelDescriptor implements ActionListener {
	
	public static final String IDENTIFIER = "CR_SELECTION_PANEL";
	
	CRSelectionPanel selectionPanel; 
    
    public CRSelectionPanelDescriptor() {
    	selectionPanel = new CRSelectionPanel();
    	selectionPanel.addListeners(this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(selectionPanel);
    }
    
    // Decide next wizard panel
    public Object getNextPanelDescriptor() {
    	if (selectionPanel.isRandomButtonSelected())
    		return CRRandomPanelDescriptor.IDENTIFIER;
    	else return null;
    }
    
    public Object getBackPanelDescriptor() {
        return null;
    }
    
    public void aboutToDisplayPanel() {
        setNextButtonEnabled();
    }    

    public void actionPerformed(ActionEvent e) {
        setNextButtonEnabled();
    }
            
    
    private void setNextButtonEnabled() {
    	if (selectionPanel.isRandomButtonSelected())
    		getWizard().setNextFinishButtonEnabled(true);
    	else
    		getWizard().setNextFinishButtonEnabled(false);         
    }
}
