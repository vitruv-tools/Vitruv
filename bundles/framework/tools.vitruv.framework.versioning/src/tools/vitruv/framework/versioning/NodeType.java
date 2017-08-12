package tools.vitruv.framework.versioning;

/**
 * Enum for the different node types in the dependency graph.
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 *
 */
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
