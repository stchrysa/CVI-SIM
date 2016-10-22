package model.components;

public interface RequestNode {

	public Node getBackupNode() ;
	
	public void setBackupNode(Node backup);
	
	public boolean isCritical();

}
