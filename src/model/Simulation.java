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
	private Substrate InPs;
	private List<Substrate> substrates;
	private List<Request> requests;
	private Algorithm algorithm;
	
	/** Creates a new instance of Substrate */
    public Simulation(Substrate InPs, List<Substrate> substrates, List<Request> requests, Algorithm algorithm) {
    	this.substrates = substrates;
    	this.requests = requests;
    	this.algorithm = algorithm;
    	this.InPs = InPs;
    }
    
    public Substrate getInPs(){
    	return InPs;
    }
    
    public void setInPs(Substrate InPs){
    	this.InPs=InPs;
    }

	public List<Substrate> getSubstrates(){
		return substrates;
	}

	public void setSubstrates(List<Substrate> substrates){
		this.substrates=substrates;
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
	
	public void changeSubstrate(List<Substrate> newSubstrates) {
		for (Substrate sub: substrates){
			sub.setState(SimulatorConstants.STATUS_AVAILABLE);
		}
		this.substrates = newSubstrates;
		for (Substrate sub: substrates){
			sub.setState(SimulatorConstants.STATUS_READY);
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
		for (Request req : requests){
			if (req.getEndDate()==time){
				endingRequests.add(req);
			}
		}
		return endingRequests;
	}


	/** Release resources of the requests from the substrate **/
	public void releaseRequests(List<Request> endingRequests) {
		// TODO
		for (Request req : endingRequests){
			if (req.getSubReq()!=null){
				for (Request subVN: req.getSubReq()){
					for (Substrate substrate: substrates){
						if (subVN.getInP()==substrate.getId()){
							if (subVN.getRMap().isDenied()==false){
								subVN.getRMap().releaseNodes(substrate);
								subVN.getRMap().releaseLinks(substrate);
								System.out.println("//////////Released///////// "+subVN.getId()+ " at time "+req.getEndDate());
							}
						}
					}
					//this.substrate.print();
				}
			}
		}
	}
}
