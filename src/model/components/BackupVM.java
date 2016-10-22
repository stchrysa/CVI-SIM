package model.components;

import java.util.ArrayList;
import java.util.List;

public class BackupVM extends VirtualMachine implements BackupNode {
	
	private int diskSpace;
	private Server server;
	private List<Node> criticalVMs;
	
	public BackupVM(int id) {
		super(id);
		name = "backupVM"+id;
		this.server=null;
		this.critical=false;
		criticalVMs = new ArrayList<Node>();
	}

	public int getDiskSpace() {
		return diskSpace;
	}

	public void setDiskSpace(int diskSpace) {
		this.diskSpace = diskSpace;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	@Override
	public List<Node> getCriticalNodes() {
		return criticalVMs;
	}

	@Override
	public void setCriticalNodes(List<Node> criticalVMs) {
		this.criticalVMs = criticalVMs;
	}

	@Override
	public void addCriticalNode(Node criticalVMs) {
		this.criticalVMs.add(criticalVMs);
	}
	
	@Override
	public Object getCopy() {
		BackupVM vm = new BackupVM(this.getId());
		vm.name = this.name;
		vm.cpu = this.cpu;
		vm.memory = this.memory;
		vm.vlans = this.vlans;
		vm.os = this.os;
		vm.netStack = this.netStack;
		vm.location = this.location;
		vm.diskSpace = this.diskSpace;
		vm.criticalVMs = this.criticalVMs;
		return vm;
	}

	
	

}
