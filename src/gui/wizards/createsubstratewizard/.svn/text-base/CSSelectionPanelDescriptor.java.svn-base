package gui.wizards.createsubstratewizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nexes.wizard.WizardPanelDescriptor;

public class CSSelectionPanelDescriptor extends WizardPanelDescriptor implements ActionListener {
	
	public static final String IDENTIFIER = "CS_SELECTION_PANEL";
	
	CSSelectionPanel selectionPanel; 
    
    public CSSelectionPanelDescriptor() {
    	selectionPanel = new CSSelectionPanel();
    	selectionPanel.addListeners(this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(selectionPanel);
    }
    
    // Decide next wizard panel
    public Object getNextPanelDescriptor() {
    	if (selectionPanel.isRandomButtonSelected())
    		return CSRandomPanelDescriptor.IDENTIFIER;
    	else if (selectionPanel.isImportButtonSelected())
    		return CSImportPanelDescriptor.IDENTIFIER;
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
    	if (!selectionPanel.isDesignButtonSelected())
    		getWizard().setNextFinishButtonEnabled(true);
    	else
    		getWizard().setNextFinishButtonEnabled(false);         
    }
}
