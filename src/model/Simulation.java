package model;

import gui.SimulatorConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the instantiation of a simulation. It is defined by a set
 * of requests, a substrate and an algorithm
 */
public class Simulation {
	
	private String id;
	private Substrate substrate;
	private List<Request> requests;
	private Algorithm algorithm;
	
	/** Creates a new instance of Substrate */
    public Simulation(Substrate substrate, List<Request> requests,
    		Algorithm algorithm) {
    	this.substrate = substrate;
    	this.requests = requests;
    	this.algorithm = algorithm;
    }

	public Substrate getSubstrate() {
		return substrate;
	}

	public void setSubstrate(Substrate substrate) {
		this.substrate = substrate;
	}

	public void changeSubstrate(Substrate newSubstrate) {
		this.substrate.setState(SimulatorConstants.STATUS_AVAILABLE);
		this.substrate = newSubstrate;
		newSubstrate.setState(SimulatorConstants.STATUS_READY);
		
	}
	
	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public void addRequests(List<Request> selectedRequests) {
		for (Request req : selectedRequests) {
			this.requests.add(req);
			req.setState(SimulatorConstants.STATUS_READY);
		}
	}
	
	public void removeRequests(List<Request> selectedRequests) {
		for (Request req : selectedRequests) {
			this.requests.remove(req);
			req.setState(SimulatorConstants.STATUS_AVAILABLE);
		}
	}
	
	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	public void changeAlgorithm(Algorithm newAlgorithm) {
		this.algorithm.setState(SimulatorConstants.STATUS_AVAILABLE);
		this.algorithm = newAlgorithm;
		newAlgorithm.setState(SimulatorConstants.STATUS_READY);
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getEndDate() {
		int end = 0;
		for (Request req : requests)
			if (req.getEndDate()>end)
				end = req.getEndDate();
		return end;
	}

	public List<Request> getStartingRequests(int time) {
		List<Request> startingRequests = new ArrayList<Request>();
		for (Request req : requests)
			if (req.getStartDate()==time)
				startingRequests.add(req);
		return startingRequests;
	}
	
	public List<Request> getEndingRequests(int time) {
		List<Request> endingRequests = new ArrayList<Request>();
		for (Request req : requests)
			if (req.getEndDate()==time)
				endingRequests.add(req);
		return endingRequests;
	}

	/** Release resources of the requests from the substrate **/
	public void releaseRequests(List<Request> endingRequests) {
		for (Request req : endingRequests){
			if (req.getRMap().isDenied()==false){
			req.getRMap().releaseNodes(this.substrate);
			req.getRMap().releaseLinks(this.substrate);
			}
			System.out.println("//////////Released///////// "+req.getId()+ " at time "+req.getEndDate());
		}
	}
}
