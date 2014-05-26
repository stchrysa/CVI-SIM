package gui.wizards.createsimulationwizard;

import java.util.List;

import model.Algorithm;
import model.Request;
import model.Substrate;

import com.nexes.wizard.Wizard;

public class CSWizard extends Wizard {
	
	private List<Request> selectedRequests;
	private List<Substrate> selectedSubstrates;
	private Algorithm selectedAlgorithm;
	
	public CSWizard() {
		super();
//		selectedRequests = new ArrayList<Request>();
		//TODO change icons
//		this.getModel().setNextFinishButtonIcon(icon);
	}

	public List<Request> getSelectedRequests() {
		return selectedRequests;
	}

	public void setSelectedRequests(List<Request> selectedRequests) {
		this.selectedRequests = selectedRequests;
	}

	public List<Substrate> getSelectedSubstrates() {
		return selectedSubstrates;
	}

	public void setSelectedSubstrates(List<Substrate> selectedSubstrates) {
		this.selectedSubstrates = selectedSubstrates;
	}

	public Algorithm getSelectedAlgorithm() {
		return selectedAlgorithm;
	}

	public void setSelectedAlgorithm(Algorithm selectedAlgorithm) {
		this.selectedAlgorithm = selectedAlgorithm;
	}
	
}
