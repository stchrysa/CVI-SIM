package gui.wizards.createsubstratewizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import gui.SimulatorConstants;

import javax.swing.JFileChooser;

import com.nexes.wizard.WizardPanelDescriptor;

public class CSImportPanelDescriptor extends WizardPanelDescriptor 
		implements ActionListener {
	
	public static final String IDENTIFIER = "CS_IMPORT_PANEL";
    
	CSImportPanel importPanel;
	
    public CSImportPanelDescriptor() {
    	importPanel = new CSImportPanel();
    	importPanel.addListeners(this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(importPanel);
    }
    
    public Object getNextPanelDescriptor() {
        return CSFinishPanelDescriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        return CSSelectionPanelDescriptor.IDENTIFIER;
    }
    
    public void aboutToDisplayPanel() {
    	setNextButtonEnabled();
    }

	private void setNextButtonEnabled() {
        if (importPanel.canFinish()) {
           getWizard().setNextFinishButtonEnabled(true);
           // Setting values
           ((CSuWizard) getWizard()).setMethod(SimulatorConstants.IMPORT_SUBSTRATE);
           ((CSuWizard) getWizard()).setFile(this.getFile());
        }
        else
           getWizard().setNextFinishButtonEnabled(false);           
    }
	
	public File getFile() {
		return importPanel.getFile();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource()==importPanel.getOpenButton()) {
			int returnValue = importPanel.getFileChooser().showOpenDialog(importPanel);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				// Save file
				importPanel.setFile(importPanel.getFileChooser().getSelectedFile());
				importPanel.setOpenedFileText(importPanel.getFileChooser().getSelectedFile().getPath());
			}
			setNextButtonEnabled();
		}
	}

}
