package tools.vitruv.framework.versioning;

public enum ConflictSolvability {
	MANUAL("manual"), AUTOMATIC("automatic");
	private final String name;

	private ConflictSolvability(String s) {
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
