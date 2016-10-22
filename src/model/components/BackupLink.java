package model.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * BackupLink Class. Subclass of RequestLink.
 */

public class BackupLink extends RequestLink {
	
	private List<RequestLink> workingLinks = null;
	
	public BackupLink(int id, int bandwidth) {
		super(id, bandwidth);
		name = "backupLink"+id;
		workingLinks = new ArrayList<RequestLink>();
	}

	public List<RequestLink> getWorkingLinks() {
		return workingLinks;
	}

	public void setWorkingLinks(List<RequestLink> workingLink) {
		this.workingLinks = workingLink;
	}

	public void addWorkingLink(RequestLink rl) {
		this.workingLinks.add(rl);
	}
	
}
