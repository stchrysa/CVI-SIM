package gui.wizards.createrequestwizard;

import java.util.List;

import model.Request;
import model.Simulator;

import com.nexes.wizard.Wizard;


public class CRWizard extends Wizard {
	
	Simulator simulator;
	
	private String prefix;
	private int numRequests;
	private int minNodes;
	private int maxNodes;
	private String linkConnectivity;
	private int minLinks;
	private int maxLinks;
	private double linkProbability;
	private String method;
	private int fixStart;
	private int uniformMin;
	private int uniformMax;
	private int normalMean;
	private int normalVariance;
	private int poissonMean;
	private int durationMin;
	private int durationMax;
	private String timeDistribution;
	private List<Request> requests;
	
	public CRWizard(Simulator simulator) {
		super();
		this.simulator = simulator;
		// TODO change icons
//		this.getModel().setNextFinishButtonIcon(icon);
	}
	
	public Simulator getSimulator() {
		return simulator;
	}
	public void setSimulator(Simulator simulator) {
		this.simulator = simulator;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public int getNumRequests() {
		return numRequests;
	}
	public void setNumRequests(int numRequests) {
		this.numRequests = numRequests;
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

	public String getLinkConnectivity() {
		return linkConnectivity;
	}

	public void setLinkConnectivity(String linkConnectivity) {
		this.linkConnectivity = linkConnectivity;
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

	public double getLinkProbability() {
		return linkProbability;
	}
	
	public void setLinkProbability(double linkProbability) {
		this.linkProbability = linkProbability;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}

	public List<Request> getRequests() {
		return requests;
	}
	
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public String getTimeDistribution() {
		return timeDistribution;
	}

	public void setTimeDistribution(String timeDistribution) {
		this.timeDistribution = timeDistribution;
	}

	public int getFixStart() {
		return fixStart;
	}

	public void setFixStart(int fixStart) {
		this.fixStart = fixStart;
	}

	public int getUniformMin() {
		return uniformMin;
	}

	public void setUniformMin(int uniformMin) {
		this.uniformMin = uniformMin;
	}

	public int getUniformMax() {
		return uniformMax;
	}

	public void setUniformMax(int uniformMax) {
		this.uniformMax = uniformMax;
	}

	public int getNormalMean() {
		return normalMean;
	}

	public void setNormalMean(int normalMean) {
		this.normalMean = normalMean;
	}

	public int getNormalVariance() {
		return normalVariance;
	}

	public void setNormalVariance(int normalVariance) {
		this.normalVariance = normalVariance;
	}

	public int getPoissonMean() {
		return poissonMean;
	}

	public void setPoissonMean(int poissonMean) {
		this.poissonMean = poissonMean;
	}

	public int getDurationMin() {
		return durationMin;
	}

	public void setDurationMin(int durationMin) {
		this.durationMin = durationMin;
	}

	public int getDurationMax() {
		return durationMax;
	}

	public void setDurationMax(int durationMax) {
		this.durationMax = durationMax;
	}

	
}
