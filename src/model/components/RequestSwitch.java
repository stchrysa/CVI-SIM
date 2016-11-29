package model.components;

/**
 * RequestSwitch Class. Subclass of Node.
 */
public class RequestSwitch extends Node {
	
	private SubstrateSwitch physicalSwitch;
	
	public RequestSwitch(int id) {
		super(id);
		name = "requestSwitch"+id;
		physicalSwitch = null;
	}

	public SubstrateSwitch getPhysicalSwitch() {
		return physicalSwitch;
	}

	public void setPhysicalSwitch(SubstrateSwitch physicalSwitch) {
		this.physicalSwitch = physicalSwitch;
	}

	public Object getCopy() {
		RequestSwitch s = new RequestSwitch(this.getId());
		s.name = this.name;
		s.cpu = this.cpu;
		s.memory = this.memory;
		s.vlans = this.vlans;
		s.os = this.os;
		s.netStack = this.netStack;
		s.location = this.location;
//		for (Interface i : this.interfaces)
//			s.interfaces.add((Interface) i.getCopy());
//		if (this.physicalSwitch!=null)
//			s.physicalSwitch = (SubstrateSwitch) this.physicalSwitch.getCopy();
		return s;
	}
}

