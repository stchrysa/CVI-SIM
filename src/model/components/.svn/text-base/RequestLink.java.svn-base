package model.components;

import java.util.ArrayList;
import java.util.List;

/**
 * RequestLink Class. Subclass of Link.
 */
public class RequestLink extends Link {

	private List<SubstrateLink> physicalLinks;
	
	public RequestLink(int id, int bandwidth) {
		super(id, bandwidth);
		name = "requestLink"+id;
		physicalLinks = new ArrayList<SubstrateLink>();
	}

	public List<SubstrateLink> getPhysicalLinks() {
		return physicalLinks;
	}

	public void setPhysicalLinks(List<SubstrateLink> physicalLinks) {
		this.physicalLinks = physicalLinks;
	}

	public Object getCopy() {
		RequestLink l = new RequestLink(this.getId(),this.getBandwidth());
		l.name = this.name;
		l.delay = this.delay;
//		l.endpoint1 = (Interface) this.endpoint1.getCopy();
//		l.endpoint2 = (Interface) this.endpoint2.getCopy();
//		for (SubstrateLink sl : this.physicalLinks) {
//			l.physicalLinks.add((SubstrateLink) sl.getCopy());
//		}
		return l;
	}
}
