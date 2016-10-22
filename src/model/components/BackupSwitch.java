package model.components;

import java.util.ArrayList;
import java.util.List;

public class BackupSwitch extends RequestSwitch implements BackupNode {

	private SubstrateSwitch physicalSwitch;
	private List<Node> criticalSwitches;

	public BackupSwitch(int id) {
		super(id);
		name = "backupSwitch"+id;
		this.critical=false;
		criticalSwitches = new ArrayList<Node>();
	}

	public SubstrateSwitch getPhysicalSwitch() {
		return physicalSwitch;
	}

	public void setPhysicalSwitch(SubstrateSwitch physicalSwitch) {
		this.physicalSwitch = physicalSwitch;
	}



	@Override
	public Object getCopy() {
		BackupSwitch s = new BackupSwitch(this.getId());
		s.name = this.name;
		s.cpu = this.cpu;
		s.memory = this.memory;
		s.vlans = this.vlans;
		s.os = this.os;
		s.netStack = this.netStack;
		s.location = this.location;
		s.criticalSwitches=this.criticalSwitches;
		return s;
	}

	@Override
	public List<Node> getCriticalNodes() {
		return this.criticalSwitches;
	}

	@Override
	public void setCriticalNodes(List<Node> criticalSwitches) {
		this.criticalSwitches=criticalSwitches;
	}

	@Override
	public void addCriticalNode(Node criticalSwitches) {
		this.criticalSwitches.add(criticalSwitches);
	}

}
