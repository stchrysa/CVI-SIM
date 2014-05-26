package gui.wizards.createsubstratewizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.SimulatorConstants;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nexes.wizard.WizardPanelDescriptor;

public class CSRandomPanelDescriptor extends WizardPanelDescriptor 
		implements DocumentListener, ActionListener {
	
	public static final String IDENTIFIER = "CS_RANDOM_PANEL";
    
	CSRandomPanel randomPanel; 
	
    public CSRandomPanelDescriptor() {
    	randomPanel = new CSRandomPanel();
    	randomPanel.addListeners(this,this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(randomPanel);
    }
    
    public Object getNextPanelDescriptor() {
        return CSFinishPanelDescriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        return CSSelectionPanelDescriptor.IDENTIFIER;
    }
    
    public void aboutToDisplayPanel() {
    	setNextButtonEnabled();
    	randomPanel.setSimulator(((CSuWizard) getWizard()).getSimulator());
    }    

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		setNextButtonEnabled();
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		setNextButtonEnabled();
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		setNextButtonEnabled();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		randomPanel.refreshLinkConnectivity();
		setNextButtonEnabled();
	}
	
	private void setNextButtonEnabled() {
        if (randomPanel.canFinish()) {
           getWizard().setNextFinishButtonEnabled(true);
           // Setting values
           ((CSuWizard) getWizard()).setMethod(SimulatorConstants.RANDOM_SUBSTRATE);
           ((CSuWizard) getWizard()).setPrefix(this.getPrefix());
           ((CSuWizard) getWizard()).setNumRequests(this.getNumRequests());
           ((CSuWizard) getWizard()).setMinNodes(this.getMinNodes());
           ((CSuWizard) getWizard()).setMaxNodes(this.getMaxNodes());
           ((CSuWizard) getWizard()).setLinkConnectivity(this.getLinkConnectivity());
           if (this.getLinkConnectivity().equals(SimulatorConstants.LINK_PER_NODE_CONNECTIVITY)) {
        	   ((CSuWizard) getWizard()).setMinLinks(this.getMinLinks());
               ((CSuWizard) getWizard()).setMaxLinks(this.getMaxLinks());
           } else {
        	   ((CSuWizard) getWizard()).setLinkProbability(this.getLinkProbability());
           }
           
        }
        else
           getWizard().setNextFinishButtonEnabled(false);           
    }
	
	public String getPrefix() {
		return randomPanel.getPrefix();
	}
	
	public int getNumRequests() {
		return randomPanel.getNumRequests();
	}

	public int getMinNodes() {
		return randomPanel.getMinNodes();
	}

	public int getMaxNodes() {
		return randomPanel.getMaxNodes();
	}
	
	public int getMinLinks() {
		return randomPanel.getMinLinks();
	}

	public int getMaxLinks() {
		return randomPanel.getMaxLinks();
	}
	
	public double getLinkProbability() {
		return randomPanel.getLinkProbability();
	}
	
	public String getLinkConnectivity() {
		return randomPanel.getLinkConnectivity();
	}

}
