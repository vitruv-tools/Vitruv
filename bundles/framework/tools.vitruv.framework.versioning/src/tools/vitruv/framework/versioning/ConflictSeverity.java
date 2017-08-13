package tools.vitruv.framework.versioning;

/**
 * Enum for the different conflict severities. The values are HARD, SOFT and
 * UNKNOWN_SEVERITY
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 *
 */
public enum ConflictSeverity {
	HARD("manual"), SOFT("automatic"), UNKNOWN_SEVERITY("unknow");
	private final String name;

	private ConflictSeverity(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	public String toString() {
		return name;
	}
}
