package model.components;


/**
 * VirtualMachine Class. Subclass of Node.
 */
public class VirtualMachine extends Node {
	
	private int diskSpace;
	private Server server;
	
	public VirtualMachine(int id) {
		super(id);
		name = "vm"+id;
		this.server = null;
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

	public Object getCopy() {
		VirtualMachine vm = new VirtualMachine(this.getId());
		vm.name = this.name;
		vm.cpu = this.cpu;
		vm.memory = this.memory;
		vm.vlans = this.vlans;
		vm.os = this.os;
		vm.veType=this.veType;
		vm.netStack = this.netStack;
		vm.location = this.location;
		vm.diskSpace = this.diskSpace;
//		for (Interface i : this.interfaces)
//			vm.interfaces.add((Interface) i.getCopy());
//		if (this.server!=null)
//			vm.server = (Server) this.server.getCopy();
		return vm;
	}
	
}
