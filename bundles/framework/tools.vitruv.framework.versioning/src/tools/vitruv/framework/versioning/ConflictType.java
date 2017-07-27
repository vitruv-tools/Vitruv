package tools.vitruv.framework.versioning;

public enum ConflictType {
	UNKNOWN("unknown"), INSERTING_IN_SAME_CONTANER("same_container"), REPLACING_SAME_VALUE("same_replacing_");
	private final String name;

	private ConflictType(String s) {
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
