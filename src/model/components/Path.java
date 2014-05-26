package model.components;

import java.util.List;

/**
 * Link Class. Superclass of SubstrateLink and RequestLink
 */
public class Path {

	protected int id;
	/** name for visualization **/
	protected String name;
	/** bandwdidth traversing the path **/
	protected int bandwidth;
	/** interface endpoints **/
	protected Interface endpoint1;
	protected Interface endpoint2;
	/** delay is not used for now **/
	protected float delay;
	/** Link Type **/
	protected String linkType;
	
	protected List<Link> substrate_links;
		
	public Path(int id) {
		this.id = id;
		/** Default delay value **/
		this.delay = 0;
	}
	
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Link> getSubstrateLinks() {
		return substrate_links;
	}
	
	public void setSubstrateLinks(List<Link> substrate_links) {
		this.substrate_links = substrate_links;
	}
	
	public Interface getEndpoint1() {
		return endpoint1;
	}

	public void setEndpoint1(Interface endpoint1) {
		this.endpoint1 = endpoint1;
	}

	public Interface getEndpoint2() {
		return endpoint2;
	}

	public void setEndpoint2(Interface endpoint2) {
		this.endpoint2 = endpoint2;
	}

	public float getDelay() {
		return delay;
	}

	public void setDelay(float delay) {
		this.delay = delay;
	}

	public String toString() {
		return Integer.toString(id);
	}
	
	public int getBandwidth(){
		return bandwidth;
	}
	
	public void setBandwidth(int bandwidth){
		this.bandwidth=bandwidth;
	}
	

	/** dummy getCopy. Subclass getCopy will be called **/
	public Object getCopy() {
		return null;
	}

}

