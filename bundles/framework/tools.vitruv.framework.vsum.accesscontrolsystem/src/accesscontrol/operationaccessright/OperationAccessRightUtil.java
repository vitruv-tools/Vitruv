/**
 * 
 */
package accesscontrol.operationaccessright;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;

import accesscontrol.OperationAccessRightEvaluator;
import accesscontrolsystem.accessright.AccessRight;
import accesscontrolsystem.accessright.AcessRightProvider;
import accesscontrolsystem.accessright.Operation;
import accesscontrolsystem.accessright.OperationAccessRight;
import accesscontrolsystem.accessright.accessrightFactory;

/**
 * Utility class containing methods to work with {@link OperationAccessRight}
 * instances.
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public class OperationAccessRightUtil implements OperationAccessRightEvaluator {

	private static OperationAccessRight allowRead;
	private static OperationAccessRight allowWrite;
	private static OperationAccessRight denyRead;
	private static OperationAccessRight denyWrite;

	public static final BinaryOperator<Boolean> LOGICAL_OR = (b1, b2) -> b1 || b2;
	public static final BinaryOperator<Boolean> LOGICAL_AND = (b1, b2) -> b1 && b2;

	@Override
	public Collection<OperationAccessRight> neededRightsViewing() {
		return List.of(allowRead());
	}

	@Override
	public Collection<OperationAccessRight> neededRightsModifying() {
		return List.of(allowWrite());
	}

	@Override
	public Collection<OperationAccessRight> findExistingRights(Collection<OperationAccessRight> rights,
			AcessRightProvider existingRightsProvider) {
		Collection<OperationAccessRight> found = new ArrayList<>();
		for (OperationAccessRight right : rights) {
			Optional<OperationAccessRight> existingRight = existingRightsProvider.getOperationAccessRights().stream()
					.filter(it -> it.getOperation().equals(right.getOperation()))
					.filter(it -> it.getAccessRight().equals(right.getAccessRight())).findFirst();
			if (existingRight.isPresent()) {
				found.add(existingRight.get());
			} else {
				// add a new OperationAccessRight to the provider and to the list
				OperationAccessRight created = accessrightFactory.eINSTANCE.createOperationAccessRight();
				created.setAccessRight(right.getAccessRight());
				created.setOperation(right.getOperation());
				existingRightsProvider.getOperationAccessRights().add(created);
				found.add(created);
			}
		}
		return found;
	}

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

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#ALLOW} and
	 *         {@link Operation#READ}
	 */
	@Override
	public OperationAccessRight allowRead() {
		return createOperationAccessRight(allowRead, AccessRight.ALLOW, Operation.READ);
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#ALLOW} and
	 *         {@link Operation#WRITE}
	 */
	@Override
	public OperationAccessRight allowWrite() {
		return createOperationAccessRight(allowWrite, AccessRight.ALLOW, Operation.WRITE);
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#DENY} and
	 *         {@link Operation#READ}
	 */
	@Override
	public OperationAccessRight denyRead() {
		return createOperationAccessRight(denyRead, AccessRight.DENY, Operation.READ);
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#DENY} and
	 *         {@link Operation#WRITE}
	 */
	@Override
	public OperationAccessRight denyWrite() {
		return createOperationAccessRight(denyWrite, AccessRight.DENY, Operation.WRITE);
	}

	/**
	 * Only creates a new OperationAccessRight if the reference is null!
	 * 
	 * @param reference   the reference the OperationAccessRight is saved in if it
	 *                    is null
	 * @param accessRight
	 * @param operation
	 * @return the newly created OperationAccessRight if the reference is
	 *         {@code null}, otherwise the reference
	 */
	private OperationAccessRight createOperationAccessRight(OperationAccessRight reference,
			AccessRight accessRight, Operation operation) {
		if (reference != null)
			return reference;
		reference = accessrightFactory.eINSTANCE.createOperationAccessRight();
		reference.setAccessRight(accessRight);
		reference.setOperation(operation);
		return reference;
	}

}
