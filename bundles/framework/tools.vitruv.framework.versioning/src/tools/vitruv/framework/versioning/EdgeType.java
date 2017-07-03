package tools.vitruv.framework.versioning;

public enum EdgeType {
	PROVIDES("provides"), TRIGGERS("triggers"), ISOMORPHIC("equivalent");

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
