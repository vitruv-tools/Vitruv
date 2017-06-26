package tools.vitruv.framework.versioning;

public enum EdgeType {
	REQUIRES("requires");
	private final String name;

	private EdgeType(String s) {
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
