package model;

import gui.SimulatorConstants;
import model.components.Node;
import model.components.Server;
import model.components.SubstrateRouter;
import model.components.SubstrateSwitch;
import org.apache.commons.collections15.Factory;

/**
 * This class is a factory of SubstrateNode. It generates the elements
 * with random parameters. Ranges for randomness can be found on 
 * SimulatorConstants class
 */
public class SubstrateNodeFactory implements Factory<Node>{

	private int nodeCount;
	
	public SubstrateNodeFactory() {
		super();
		nodeCount = 0;
	}
	
	/** Generate a random node **/
	public Node create() {
		Node node = null;
		int cpu;
		int memory;
		String os=null;
		String veType=null;

		int nodeType = (int) (Math.random()*10);
		if ( (nodeType>=0) && (nodeType<2) ){
				node = new SubstrateRouter(nodeCount);
				node.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
				((SubstrateRouter)node).setLogicalInstances(SimulatorConstants.MAX_LOGICAL_INSTANCES);
				((SubstrateRouter)node).setAvailableLogicalInstances(SimulatorConstants.MAX_LOGICAL_INSTANCES);
		}
		else{
				node = new Server(nodeCount);
				// Random diskSpace generation
				int diskSpace = SimulatorConstants.MIN_DISK 
							+ (int)(Math.random()*((SimulatorConstants.MAX_DISK 
							- SimulatorConstants.MIN_DISK) + 1));
				((Server) node).setDiskSpace(diskSpace);
				((Server) node).setAvailableDiskSpace(diskSpace);
				node.setVlans(SimulatorConstants.MAX_SERVER_VLANS);

				// Random cpu generation
				cpu = SimulatorConstants.MIN_CPU 
							+ (int)(Math.random()*((SimulatorConstants.MAX_CPU 
							- SimulatorConstants.MIN_CPU) + 1));
				node.setCpu(cpu);
				node.setAvailableCpu(cpu);
				
				// Random ram generation
				memory = SimulatorConstants.MIN_MEMORY 
							+ (int)(Math.random()*((SimulatorConstants.MAX_MEMORY 
							- SimulatorConstants.MIN_MEMORY) + 1));
				node.setMemory(memory);
				node.setAvailableMemory(memory);
			}

		
		
	int operation=(int) (Math.random()*4);
		
		switch (operation){
			case 0:
				os="Linux";
				break;
			case 1:
				os="Windows";
				break;
			case 2:
				os="Solaris";
				break;
			case 3:
				os="Android";
				break;
		}
		
		//node.setOS(os);
		node.setOS("Linux");
		
		
		int vtype=(int) (Math.random()*4);
		
		switch (vtype) {
			case 0:
				veType="VMWare";
				break;
			case 1:
				veType="XEN";
				break;
			case 2:
				veType="KVM";
				break;
			case 3:
				veType="uml";
				break;
		}
		
		//node.setVEType(veType);
		node.setVEType("VMWare");


		nodeCount++;
		return node;
	}
	
	/** Generate a Node specified by its type **/
	public Node create(String nodeType) {
		Node node = null;
		int cpu;
		int memory;
		
		if (nodeType.equalsIgnoreCase("router")) {
			node = new SubstrateRouter(nodeCount);
			node.setVlans(SimulatorConstants.MAX_ROUTER_VLANS);
			((SubstrateRouter)node).setLogicalInstances(SimulatorConstants.MAX_LOGICAL_INSTANCES);
		}
		else if (nodeType.equalsIgnoreCase("switch")) {
			node = new SubstrateSwitch(nodeCount); 
			node.setVlans(SimulatorConstants.MAX_SWITCH_VLANS);
		}
		else if (nodeType.equalsIgnoreCase("server")) {
			node = new Server(nodeCount);
			// Random diskSpace generation
			int diskSpace = SimulatorConstants.MIN_DISK 
						+ (int)(Math.random()*((SimulatorConstants.MAX_DISK 
						- SimulatorConstants.MIN_DISK) + 1));
			((Server) node).setDiskSpace(diskSpace);
			node.setVlans(SimulatorConstants.MAX_SERVER_VLANS);
		}
		
		// Random cpu generation
		cpu = SimulatorConstants.MIN_CPU 
					+ (int)(Math.random()*((SimulatorConstants.MAX_CPU 
					- SimulatorConstants.MIN_CPU) + 1));
		node.setCpu(cpu);
		// Random ram generation
		memory = SimulatorConstants.MIN_MEMORY 
					+ (int)(Math.random()*((SimulatorConstants.MAX_MEMORY 
					- SimulatorConstants.MIN_MEMORY) + 1));
		node.setMemory(memory);

		nodeCount++;
		return node;
	}
	
	public Object getCopy() {
		SubstrateNodeFactory f = new SubstrateNodeFactory();
		f.nodeCount = this.nodeCount;
		return f;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}

}
