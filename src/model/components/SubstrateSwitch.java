package model.components;

import gui.SimulatorConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SubstrateSwitch Class. Subclass of Node.
 */
public class SubstrateSwitch extends Node {

	private List<RequestSwitch> virtualSwitches;
	
	private int maxLogicalIfaces;
	
	public SubstrateSwitch(int id) {
		super(id);
		name = "substrateSwitch"+id;
		setMaxLogicalIfaces(SimulatorConstants.MAX_LOGICAl_IFACES_SWITCH);
		virtualSwitches = new ArrayList<RequestSwitch>();
	}

	public List<RequestSwitch> getVirtualSwitches() {
		return virtualSwitches;
	}

	public void setVirtualSwitches(List<RequestSwitch> virtualSwitches) {
		this.virtualSwitches = virtualSwitches;
	}
	
	public void addVirtualSwitch(RequestSwitch rs) {
		this.virtualSwitches.add(rs);
	}

	public int getAvailableCpu() {
		int returnValue = this.getCpu();
		for (RequestSwitch s : virtualSwitches)
			returnValue-=s.getCpu();
		return returnValue;
	}
	
	public int getAvailableMemory() {
		int returnValue = this.getMemory();
		for (RequestSwitch s : virtualSwitches)
			returnValue-=s.getMemory();
		return returnValue;
	}
	
	public int getAvailableVlans(Collection<Link> links) {
		int returnValue = this.vlans;
		for (RequestSwitch s : virtualSwitches)
			returnValue-=s.getVlans();
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
		SubstrateSwitch s = new SubstrateSwitch(this.getId());
		s.name = this.name;
		s.cpu = this.cpu;
		s.memory = this.memory;
		s.vlans = this.vlans;
		s.os = this.os;
		s.netStack = this.netStack;
		s.location = this.location;
		s.maxLogicalIfaces = this.maxLogicalIfaces;
//		for (Interface i : this.interfaces)
//			s.interfaces.add((Interface) i.getCopy());
		for (RequestSwitch rs : this.virtualSwitches)
			s.virtualSwitches.add((RequestSwitch) rs.getCopy());
		return s;
	}
}
