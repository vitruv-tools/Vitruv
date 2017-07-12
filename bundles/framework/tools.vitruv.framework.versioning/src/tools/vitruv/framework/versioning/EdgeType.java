package tools.vitruv.framework.versioning;

public enum EdgeType {
	REQUIRED("required"), REQUIRES("requires"), TRIGGERS("triggers"), ISOMORPHIC("equivalent"), CONFLICTS(
			"conflicts"), UNKNOWN("unknown");

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

	public EdgeType getInverse() {
		EdgeType inverse;
		switch (this) {
		case REQUIRED:
			inverse = REQUIRES;
			break;
		case REQUIRES:
			inverse = REQUIRED;
			break;
		default:
			inverse = UNKNOWN;
			break;
		}
		return inverse;
	}
}
