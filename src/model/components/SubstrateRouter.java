package model.components;

import gui.SimulatorConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SubstrateRouter Class. Subclass of Node.
 */
public class SubstrateRouter extends Node {

	public static final int MAX_LOGICAL_ROUTERS = 15;
	
	private int maxLogicalIfaces;
	
	private int logicalInstances;
	
	private int availablelogicalInstances;
	
	private List<RequestRouter> virtualRouters;
	
	public SubstrateRouter(int id) {
		super(id);
		name = "substrateRouter"+id;
		setMaxLogicalIfaces(SimulatorConstants.MAX_LOGICAl_IFACES_ROUTER);
		virtualRouters = new ArrayList<RequestRouter>();
	}
	
	public int getLogicalInstances() {
		return logicalInstances;
	}

	public void setLogicalInstances(int logicalInstances) {
		this.logicalInstances = logicalInstances;
	}
	
	public List<RequestRouter> getVirtualRouters() {
		return virtualRouters;
	}

	public void setVirtualRouters(List<RequestRouter> virtualRouters) {
		this.virtualRouters = virtualRouters;
	}
	
	public void addVirtualRouter(RequestRouter rr) {
		this.virtualRouters.add(rr);
	}
	
	public int getAvailableLogicalInstances() {
		return availablelogicalInstances;
	}
	
	public void setAvailableLogicalInstances(int availablelogicalInstances){
		this.availablelogicalInstances=availablelogicalInstances;
	}

	public int getAvailableCpu() {
		int returnValue = this.getCpu();
		for (RequestRouter router : virtualRouters)
			returnValue-=router.getCpu();
		return returnValue;
	}
	
	public int getAvailableMemory() {
		int returnValue = this.getMemory();
		for (RequestRouter router : virtualRouters)
			returnValue-=router.getMemory();
		return returnValue;
	}
	
	public int getAvailableVlans(Collection<Link> links) {
		int returnValue = this.vlans;
		for (Link l : links)
			returnValue -= ((SubstrateLink) l).getVirtualLinks().size();
		return returnValue;
	}
	
	public int getMaxLogicalIfaces() {
		return maxLogicalIfaces;
	}

	public void setMaxLogicalIfaces(int maxLogicalIfaces) {
		this.maxLogicalIfaces = maxLogicalIfaces;
	}

	public Object getCopy() {
		SubstrateRouter r = new SubstrateRouter(this.getId());
		r.icon = this.icon;
		r.name = this.name;
		r.cpu = this.cpu;
		r.memory = this.memory;
		r.vlans = this.vlans;
		r.os = this.os;
		r.veType=this.veType;
		r.netStack = this.netStack;
		r.location = this.location;
		r.logicalInstances=this.logicalInstances;
		r.availablelogicalInstances=this.availablelogicalInstances;
		r.maxLogicalIfaces = this.maxLogicalIfaces;
//		for (Interface i : this.interfaces)
//			r.interfaces.add((Interface) i.getCopy());
		for (RequestRouter rr : this.virtualRouters)
			r.virtualRouters.add((RequestRouter) rr.getCopy());
		return r;
	}
	
}
