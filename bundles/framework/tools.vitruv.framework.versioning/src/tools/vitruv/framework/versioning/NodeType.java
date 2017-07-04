package tools.vitruv.framework.versioning;

public enum NodeType {
	UNPAIRED("unpaired");

	private final String name;

	private NodeType(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		// (otherName == null) check is not needed because name.equals(null)
		// returns false
		return name.equals(otherName);
	}

	public String toString() {
		return name;
	}
}
