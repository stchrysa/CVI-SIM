package model;

import java.util.HashMap;
import java.util.Map;

import model.components.Link;
import model.components.Node;
import model.components.BackupRouter;
import model.components.BackupSwitch;
import model.components.BackupVM;
import model.components.RequestNode;
import model.components.BackupNode;
import model.components.VirtualMachine;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.Graph;

public class BackupNodeFactory implements Factory<Node> {
	
	private int nodeCount;
	private Map<String, Node> backupNodes;
	
	public BackupNodeFactory() {
		super();
		nodeCount = 0;
		backupNodes = new HashMap<String, Node>();
	}

	public Node create() {
		return null;
	}
	
	/** Generate a Node specified by its type **/
	public Node createOneRedundunt(Node critical) {
		Node node = null;
		
		if(backupNodes.get(critical.getType())==null){
			if (critical.getType().equalsIgnoreCase("Server")){
				node = new BackupVM(nodeCount);
				backupNodes.put(node.getType(), node);
				((VirtualMachine) node).setDiskSpace(((VirtualMachine) critical).getDiskSpace());
			}
			else if (critical.getType().equalsIgnoreCase("Switch")){
				node = new BackupSwitch(nodeCount);
				backupNodes.put(node.getType(), node);
			}
			else if (critical.getType().equalsIgnoreCase("Router")){
				node = new BackupRouter(nodeCount);
				backupNodes.put(node.getType(), node);
			} 
			node.setCpu(critical.getCpu());
			node.setMemory(critical.getMemory());
			nodeCount++;
			
			((BackupNode) node).addCriticalNode(critical);
			((RequestNode) critical).setBackupNode(node);

			return node;
		}	
		else{
			node=backupNodes.get(critical.getType());
			if (critical.getType().equalsIgnoreCase("Server"))
				((VirtualMachine) node).setDiskSpace(((VirtualMachine) critical).getDiskSpace());
			node.setCpu(Math.max(critical.getCpu(),node.getCpu()));
			node.setMemory(Math.max(critical.getMemory(), node.getMemory()));
			//node.setVlans(Math.max(critical.getVlans(), node.getVlans()));
			
			((BackupNode) node).addCriticalNode(critical);
			((RequestNode) critical).setBackupNode(node);

			return null;
		}
		
	}

	public Node createKRedundunt(Node critical) {
		Node node = null;
		
		if (critical.getType().equalsIgnoreCase("Server")){
			node = new BackupVM(nodeCount);
			((VirtualMachine) node).setDiskSpace(((VirtualMachine) critical).getDiskSpace());
		}
		else if (critical.getType().equalsIgnoreCase("Switch")){
			node = new BackupSwitch(nodeCount);
		}
		else if (critical.getType().equalsIgnoreCase("Router")){
			node = new BackupRouter(nodeCount);
		}
		node.setCpu(critical.getCpu());
		node.setMemory(critical.getMemory());
		nodeCount++;
			
		((BackupNode) node).addCriticalNode(critical);
		((RequestNode) critical).setBackupNode(node);

		return node;
	}
	
	public Object getCopy() {
		BackupNodeFactory f = new BackupNodeFactory();
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
