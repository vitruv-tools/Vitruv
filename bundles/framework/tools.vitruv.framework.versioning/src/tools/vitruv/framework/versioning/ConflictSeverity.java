package tools.vitruv.framework.versioning;

public enum ConflictSeverity {
	HARD("manual"), SOFT("automatic"), UNKNOWN_SEVERITY("unknow");
	private final String name;

	private ConflictSeverity(String s) {
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
