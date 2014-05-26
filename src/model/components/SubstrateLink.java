package model.components;

import java.util.ArrayList;
import java.util.List;

/**
 * SubstrateLink Class. Subclass of Link.
 */
public class SubstrateLink extends Link {
	
	private List<RequestLink> virtualLinks;
	private int availablebw;
	
	public SubstrateLink(int id, int bandwidth) {
		super(id, bandwidth);
		name = "substrateLink"+id;
		virtualLinks = new ArrayList<RequestLink>();
	}

	public List<RequestLink> getVirtualLinks() {
		return virtualLinks;
	}
	
	public void setVirtualLinks(List<RequestLink> virtualLinks) {
		this.virtualLinks = virtualLinks;
	}

	//The initial capacity of a substrate link
	public int getAvailableBandwidth(){
		return availablebw;
	}
	
	public void setAvailableBandwidth(int availablebw){
		this.availablebw = availablebw;
	}
		
	public Object getCopy() {
		SubstrateLink l = new SubstrateLink(this.getId(),this.getBandwidth());
		l.name = this.name;
		l.delay = this.delay;
		l.availablebw=this.availablebw;
		l.bandwidth = this.bandwidth;
		return l;
	}
	
}
