package gui.wizards.createsubstratewizard;

import java.io.File;
import java.util.List;

import model.Simulator;
import model.Substrate;

import com.nexes.wizard.Wizard;

public class CSuWizard extends Wizard {
	
	private Simulator simulator;
	private String method;
	private List<Substrate> substrates;
	
	// Random generation parameters
	private String prefix;
	private int numSubstrates;
	private int minNodes;
	private int maxNodes;
	private int minLinks;
	private int maxLinks;
	private String linkConnectivity;
	private double linkProbability;
	
	// Import generation parameters
	private File file;
	
	public CSuWizard(Simulator simulator) {
		super();
		this.simulator = simulator;
		//TODO change icons
//		this.getModel().setNextFinishButtonIcon(icon);
	}
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public int getNumSubstrates() {
		return numSubstrates;
	}
	public void setNumRequests(int numRequests) {
		this.numSubstrates = numRequests;
	}
	public int getMinNodes() {
		return minNodes;
	}
	public void setMinNodes(int minNodes) {
		this.minNodes = minNodes;
	}
	public int getMaxNodes() {
		return maxNodes;
	}
	public void setMaxNodes(int maxNodes) {
		this.maxNodes = maxNodes;
	}

	public int getMinLinks() {
		return minLinks;
	}

	public void setMinLinks(int minLinks) {
		this.minLinks = minLinks;
	}

	public int getMaxLinks() {
		return maxLinks;
	}

	public void setMaxLinks(int maxLinks) {
		this.maxLinks = maxLinks;
	}

	public Simulator getSimulator() {
		return simulator;
	}

	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}

	public List<Substrate> getSubstrates() {
		return substrates;
	}
	
	public void setSubstrates(List<Substrate> substrates) {
		this.substrates = substrates;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public String getLinkConnectivity() {
		return linkConnectivity;
	}

	public void setLinkConnectivity(String linkConnectivity) {
		this.linkConnectivity = linkConnectivity;
	}
	
	public double getLinkProbability() {
		return linkProbability;
	}
	
	public void setLinkProbability(double linkProbability) {
		this.linkProbability = linkProbability;
	}
	
}
