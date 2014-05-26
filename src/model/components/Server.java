package model.components;

import gui.Icons;
import gui.SimulatorConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Server Class. Subclass of Node.
 */
public class Server extends Node {
	
	/** Virtualization Software **/
	public enum VSoftware {VMWare, XEN, KVM, VirtualBox};
	
	public static final int MAX_VMS = 256;
	
	private int maxLogicalIfaces;
	
	private VSoftware vSoftware;
	private int diskSpace;
	private int availablediskSpace;
	private List<VirtualMachine> virtualMachines;

	public Server(int id) {
		super(id);
		name = "server"+id;
		virtualMachines = new ArrayList<VirtualMachine>();
		setIcon(Icons.SERVER);
		setSmallIcon(Icons.SERVER_SMALL);
		setPickedIcon(Icons.SERVER_PICKED);
		setYellowIcon(Icons.SERVER_YELLOW);
		setRedIcon(Icons.SERVER_RED);
		setMaxLogicalIfaces(SimulatorConstants.MAX_LOGICAl_IFACES_SERVER);
		/** Default VSoftware **/
		vSoftware = VSoftware.VMWare;
	}
	
	public VSoftware getvSoftware() {
		return vSoftware;
	}

	public void setvSoftware(VSoftware vSoftware) {
		this.vSoftware = vSoftware;
	}

	public int getDiskSpace() {
		return diskSpace;
	}

	public void setDiskSpace(int diskSpace) {
		this.diskSpace = diskSpace;
	}

	public List<VirtualMachine> getVirtualMachines() {
		return virtualMachines;
	}

	public void setVirtualMachines(List<VirtualMachine> virtualMachines) {
		this.virtualMachines = virtualMachines;
	}
	
	public void addVirtualMachine(VirtualMachine vm) {
		this.virtualMachines.add(vm);
	}
	
	
	public int getAvailableDiskSpace(){
		return availablediskSpace;
	}
	
	public void setAvailableDiskSpace(int availablediskSpace){
		this.availablediskSpace=availablediskSpace;
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
		Server s = new Server(this.getId());
		s.name = this.name;
		s.cpu = this.cpu;
		s.memory = this.memory;
		s.vlans = this.vlans;
		s.os = this.os;
		s.veType=this.veType;
		s.netStack = this.netStack;
		s.location = this.location;
		s.diskSpace = this.diskSpace;
		s.availablecpu = this.availablecpu;
		s.availablememory = this.availablememory;
		s.availablediskSpace = this.availablediskSpace;
		s.vSoftware = this.vSoftware;
		s.maxLogicalIfaces = this.maxLogicalIfaces;
		for (VirtualMachine vm : this.virtualMachines)
			s.virtualMachines.add((VirtualMachine) vm.getCopy());
		return s;
	}
	
}
