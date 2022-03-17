package tools.vitruv.framework.vsum.filtered.changemodification;

public enum EChangeModificationType {
	INDEX_BASED_CHANGE("index"), ID_BASED_CHANGE("id");
	private final String representation;
	
	EChangeModificationType(String representation) {
		this.representation = representation;
	}
	
	@Override
	public String toString() {
		return representation;
	}
}
