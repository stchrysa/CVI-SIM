package model.components;

import java.util.List;

public interface BackupNode {
	
	public List<Node> getCriticalNodes() ;
	
	public void setCriticalNodes(List<Node> criticalNodes);
	
	public void addCriticalNode(Node criticalNode);

}
