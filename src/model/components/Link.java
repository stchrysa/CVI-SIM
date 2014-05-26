package model.components;

/**
 * Link Class. Superclass of SubstrateLink and RequestLink
 */
public class Link {

	protected int id;
	protected int bandwidth;
	protected int stress;
	/** name for visualization **/
	protected String name;
	/** interface endpoints **/
	protected Interface endpoint1;
	protected Interface endpoint2;
	/** delay is not used for now **/
	protected float delay;
	/** Link Type **/
	protected String linkType;
		
	public Link(int id, int bandwidth) {
		this.id = id;
		this.bandwidth = bandwidth;
		/** Default delay value **/
		this.delay = 0;
		
		int ltype=(int) (Math.random()*3);
		
		switch (ltype){
			case 0: 
				linkType="VLAN";
				break;
			case 1:
				linkType="SONET";
				break;
			case 2:
				linkType="EIGHT0211";
				break;
		}
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

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public int getStress(){
		return stress;
	}
	
	public void setStress(int stress){
		this.stress=stress;
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
	


	/** dummy getCopy. Subclass getCopy will be called **/
	public Object getCopy() {
		return null;
	}

}

