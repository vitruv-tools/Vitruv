/**
 * 
 */
package accesscontrol.internal;

import java.util.Collection;
import java.util.Objects;

import accesscontrol.OperationAccessRightEvaluator;
import accesscontrolsystem.accessright.AccessRight;
import accesscontrolsystem.accessright.OperationAccessRight;

/**
 * Utility class containing methods to work with {@link OperationAccessRight}
 * instances.
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public class OperationAccessRightEvaluatorImpl implements OperationAccessRightEvaluator {
	/**
	 * Checks {@link #hasAccess(Collection, OperationAccessRight)} for every
	 * {@link OperationAccessRight} in {@code needed} and reduces the result with
	 * {@link #LOGICAL_AND}. Thus the method computes if for all needed rights a
	 * matching given right is present.
	 * 
	 * @param given
	 * @param needed
	 * @return
	 */
	@Override
	public boolean hasAccess(Collection<OperationAccessRight> given, Collection<OperationAccessRight> needed) {
		Objects.requireNonNull(given);
		Objects.requireNonNull(needed);
		if (needed.isEmpty())
			return true;
		if (given.isEmpty())
			return false;
		// if the lists are not empty and not null the Optional from reduce is present
		// here
		switch (needed.stream().map(it -> hasAccess(given, it)).reduce(CheckResult::combineAnd).get()) {
		case ALLOWED:
			return true;
		case DENIED:
			return false;
		case DIFFERENT_OPERATION:
			return false;
		default:
			break;

		}
		return false;
	}

	/**
	 * Checks {@link #hasAccess(OperationAccessRight, OperationAccessRight)} for
	 * every {@link OperationAccessRight} in {@code given} and reduces the result
	 * with {@link #LOGICAL_OR}. Thus the method computes if for the needed right at
	 * least one matching given right is present.
	 * 
	 * @param given
	 * @param needed
	 * @return
	 */
	private CheckResult hasAccess(Collection<OperationAccessRight> given, OperationAccessRight needed) {
		Objects.requireNonNull(given);
		Objects.requireNonNull(needed);
		if (given.isEmpty())
			return CheckResult.DENIED;
		// if the list is not empty and needed is not null the Optional from reduce is
		// present here
		return given.stream().map(it -> hasAccess(it, needed)).reduce(CheckResult::combine).get();
	}

	/**
	 * Computes whether or not the given right suffice to gain access. Thus the
	 * operations must match and the given {@link AccessRight} must be equal or
	 * greater than the needed one.
	 * 
	 * @param given  the {@link OperationAccessRight} the entity has
	 * @param needed the {@link OperationAccessRight} that is needed to get access
	 * @return whether or not the given rights are at least the needed ones
	 */
	private CheckResult hasAccess(OperationAccessRight given, OperationAccessRight needed) {
		Objects.requireNonNull(given);
		Objects.requireNonNull(needed);
		if (!given.getOperation().equals(needed.getOperation())) {
			return CheckResult.DIFFERENT_OPERATION;
		}
		if (given.getAccessRight().compareTo(needed.getAccessRight()) >= 0) {
			return CheckResult.ALLOWED;
		}
		return CheckResult.DENIED;
	}

	private enum CheckResult {
		DIFFERENT_OPERATION, DENIED, ALLOWED;

		public CheckResult combine(CheckResult other) {
			switch (this) {
			case ALLOWED:
				switch (other) {
				case ALLOWED:
					return ALLOWED;
				case DENIED:
					return DENIED;
				case DIFFERENT_OPERATION:
					return ALLOWED;
				}
				break;
			case DENIED:
				return DENIED;
			case DIFFERENT_OPERATION:
				switch (other) {
				case ALLOWED:
					return ALLOWED;
				case DENIED:
					return DENIED;
				case DIFFERENT_OPERATION:
					return DIFFERENT_OPERATION;
				}
			}
			return DENIED;
		}

		public CheckResult combineAnd(CheckResult other) {
			switch (this) {
			case ALLOWED:
				switch (other) {
				case ALLOWED:
					return ALLOWED;
				case DENIED:
					return DENIED;
				case DIFFERENT_OPERATION:
					return DENIED;
				}
				break;
			case DENIED:
				return DENIED;
			case DIFFERENT_OPERATION:
				return DENIED;
			}
			return DENIED;
		}
	}

}
