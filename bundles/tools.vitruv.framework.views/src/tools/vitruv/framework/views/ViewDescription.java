package tools.vitruv.framework.views;

public class ViewDescription {
	private String versionIdentifier;
	
	public ViewDescription(String versionIdentifier) {
		this.versionIdentifier = versionIdentifier;
	}
	
	public String versionIdentifier() {
		return versionIdentifier;
	}
}
