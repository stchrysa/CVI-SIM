package gui.wizards.createsimulationwizard;

import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Algorithm;

import com.nexes.wizard.WizardPanelDescriptor;

public class CSAlgorithmPanelDescriptor extends WizardPanelDescriptor implements ListSelectionListener {
	
	public static final String IDENTIFIER = "CS_ALGORITHM_PANEL";
	
	CSAlgorithmPanel algorithmPanel;
	List<Algorithm> algorithms;
	Algorithm selectedAlgorithm;
    
    public CSAlgorithmPanelDescriptor(List<Algorithm> algorithms) {
    	this.algorithms = algorithms;
    	algorithmPanel = new CSAlgorithmPanel(algorithms);
    	algorithmPanel.addListeners(this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(algorithmPanel);
    }
    
    // Decide next wizard panel
    public Object getNextPanelDescriptor() {
		return FINISH;
    }
    
    public Object getBackPanelDescriptor() {
        return CSSubstratePanelDescriptor.IDENTIFIER;
    }
    
    public void aboutToDisplayPanel() {
        setNextButtonEnabled();
    }    
	
	/** Attend list selection **/
	@Override
	public void valueChanged(ListSelectionEvent evt) {
//		if (evt.getValueIsAdjusting())
//	          return;
		setNextButtonEnabled();
	}

    private void setNextButtonEnabled() {
    	if (algorithmPanel.canFinish()) {
    		// Setting substrate on the wizard
    		selectedAlgorithm = algorithmPanel.getSelectedAlgorithm();
    		((CSWizard) getWizard()).setSelectedAlgorithm(selectedAlgorithm);
    		getWizard().setNextFinishButtonEnabled(true);
    	}
    	else
    		getWizard().setNextFinishButtonEnabled(false);         
    }

}

