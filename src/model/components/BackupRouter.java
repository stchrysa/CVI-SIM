package model.components;

import java.util.ArrayList;
import java.util.List;

public class BackupRouter extends RequestRouter implements BackupNode {
	
	private SubstrateRouter physicalRouter;
	private List<Node> criticalRouters;

	public BackupRouter(int id) {
		super(id);
		name = "backupRouter"+id;
		this.critical=false;
		criticalRouters = new ArrayList<Node>();
	}

	public SubstrateRouter getPhysicalRouter() {
		return physicalRouter;
	}

	public void setPhysicalRouter(SubstrateRouter physicalRouter) {
		this.physicalRouter = physicalRouter;
	}

	@Override
	public Object getCopy() {
		BackupRouter r = new BackupRouter(this.getId());
		r.name = this.name;
		r.cpu = this.cpu;
		r.memory = this.memory;
		r.vlans = this.vlans;
		r.os = this.os;
		r.netStack = this.netStack;
		r.location = this.location;
		r.criticalRouters=this.criticalRouters;
		return r;
	}

	@Override
	public List<Node> getCriticalNodes() {
		return this.criticalRouters;
	}

	@Override
	public void setCriticalNodes(List<Node> criticalRouters) {
		this.criticalRouters=criticalRouters;
	}

	@Override
	public void addCriticalNode(Node criticalRouter) {
		this.criticalRouters.add(criticalRouter);
	}
}
