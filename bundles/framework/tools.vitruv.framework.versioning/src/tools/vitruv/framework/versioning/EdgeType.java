package tools.vitruv.framework.versioning;

/**
 * Enum for the different edge types in the dependency graph. The types are
 * REQUIRED, REQUIRES, TRIGGERS, ISOMORPHIC, CONFLICTS, UNKNOWN. REQUIRED and
 * REQUIRES are defined in one dependency graph and one model. The TRIGGERS type
 * is defined in one dependency graph and between nodes from different models.
 * The ISOMORPHIC edge indicates, that two nodes from two different dependency
 * graphs are isomorphic. The CONFLICTS type states that theses two nodes from
 * two different dependency graphs are conflicting each other.
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 *
 */
public enum EdgeType {
	REQUIRED("required"), REQUIRES("requires"), TRIGGERS("triggers"), ISOMORPHIC("equivalent"), CONFLICTS(
			"conflicts"), UNKNOWN("unknown");

	private final String name;

	private EdgeType(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
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
