package gui.wizards.createrequestwizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.SimulatorConstants;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nexes.wizard.WizardPanelDescriptor;

public class CRRandomPanelDescriptor extends WizardPanelDescriptor implements DocumentListener, ActionListener {
	
	public static final String IDENTIFIER = "CR_RANDOM_PANEL";
    
	CRRandomPanel randomPanel; 
	
    public CRRandomPanelDescriptor() {
    	randomPanel = new CRRandomPanel();
    	randomPanel.addListeners(this, this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(randomPanel);
    }
    
    public Object getNextPanelDescriptor() {
        return CRFinishPanelDescriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        return CRSelectionPanelDescriptor.IDENTIFIER;
    }
    
    public void aboutToDisplayPanel() {
    	setNextButtonEnabled();
    	randomPanel.setSimulator(((CRWizard) getWizard()).getSimulator());
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
		randomPanel.refreshTimeDistribution();
		randomPanel.refreshLinkConnectivity();
		setNextButtonEnabled();
	}

	private void setNextButtonEnabled() {
        if (randomPanel.canFinish()) {
           getWizard().setNextFinishButtonEnabled(true);
           // Setting values
           ((CRWizard) getWizard()).setMethod(SimulatorConstants.RANDOM_REQUEST);
           ((CRWizard) getWizard()).setPrefix(this.getPrefix());
           ((CRWizard) getWizard()).setNumRequests(this.getNumRequests());
           ((CRWizard) getWizard()).setMinNodes(this.getMinNodes());
           ((CRWizard) getWizard()).setMaxNodes(this.getMaxNodes());
//           ((CRWizard) getWizard()).setMinLinks(this.getMinLinks());
//           ((CRWizard) getWizard()).setMaxLinks(this.getMaxLinks());
           ((CRWizard) getWizard()).setLinkConnectivity(this.getLinkConnectivity());
           if (this.getLinkConnectivity().equals(SimulatorConstants.LINK_PER_NODE_CONNECTIVITY)) {
        	   ((CRWizard) getWizard()).setMinLinks(this.getMinLinks());
        	   ((CRWizard) getWizard()).setMaxLinks(this.getMaxLinks());
           } else {
        	   ((CRWizard) getWizard()).setLinkProbability(this.getLinkProbability());
           }
           ((CRWizard) getWizard()).setTimeDistribution(this.getTimeDistribution());
           if (this.getTimeDistribution().equals(SimulatorConstants.FIXED_DISTRIBUTION))
        	   ((CRWizard) getWizard()).setFixStart(this.getFixStart());
           else if (this.getTimeDistribution().equals(SimulatorConstants.UNIFORM_DISTRIBUTION)) {
	           ((CRWizard) getWizard()).setUniformMin(this.getUniformMin());
	           ((CRWizard) getWizard()).setUniformMax(this.getUniformMax());
           }
           else if (this.getTimeDistribution().equals(SimulatorConstants.NORMAL_DISTRIBUTION)) {
        	   ((CRWizard) getWizard()).setNormalMean(this.getNormalMean());
        	   ((CRWizard) getWizard()).setNormalVariance(this.getNormalVariance());
           }
           else
        	   ((CRWizard) getWizard()).setPoissonMean(this.getPoissonMean());
           ((CRWizard) getWizard()).setDurationMin(this.getDurationMin());
           ((CRWizard) getWizard()).setDurationMax(this.getDurationMax());
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
	
	public String getTimeDistribution() {
		return randomPanel.getTimeDistribution();
	}
	
	public int getFixStart() {
		return randomPanel.getFixStart();
	}
	
	public int getUniformMin() {
		return randomPanel.getUniformMin();
	}
	
	public int getUniformMax() {
		return randomPanel.getUniformMax();
	}
	
	public int getNormalMean() {
		return randomPanel.getNormalMean();
	}
	
	public int getNormalVariance() {
		return randomPanel.getNormalVariance();
	}
	
	public int getPoissonMean() {
		return randomPanel.getPoissonMean();
	}
	
	public int getDurationMin() {
		return randomPanel.getDurationMin();
	}
	
	public int getDurationMax() {
		return randomPanel.getDurationMax();
	}

}
