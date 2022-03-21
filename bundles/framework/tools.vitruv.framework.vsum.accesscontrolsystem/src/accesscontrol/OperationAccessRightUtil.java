package accesscontrol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import accesscontrolsystem.accessright.AccessRight;
import accesscontrolsystem.accessright.AcessRightProvider;
import accesscontrolsystem.accessright.Operation;
import accesscontrolsystem.accessright.OperationAccessRight;
import accesscontrolsystem.accessright.accessrightFactory;

/**
 * Implementing {@link #neededRightsViewing()} allows to specify the rights
 * needed to an element for it to be part of a generated view and
 * {@link #neededRightsModifying()} allows to specify the rights needed to
 * modify an element.
 * {@link #findExistingRights(Collection, AcessRightProvider)} allows to get
 * OperationAccessRights that have the same operations and accessrights as the
 * given ones but are contained in the given AccessRightProvider (by either
 * finding them or adding them to the provider).
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public interface OperationAccessRightUtil {

	static OperationAccessRight allowRead = OperationAccessRightUtil.allowRead();
	static OperationAccessRight allowWrite = OperationAccessRightUtil.allowWrite();
	static OperationAccessRight denyRead = OperationAccessRightUtil.denyRead();
	static OperationAccessRight denyWrite = OperationAccessRightUtil.denyWrite();

	/**
	 * 
	 * @return the {@link OperationAccessRight}s needed to view an element (used to
	 *         filter for a view creation, reasonable could be an
	 *         OperationAccessRight with Operation Read and AccessRight Allow)
	 */
	static Collection<OperationAccessRight> neededRightsViewing() {
		return List.of(allowRead());
	}

	/**
	 * 
	 * @return the {@link OperationAccessRight}s needed to modify an element (used
	 *         to filter for an element modification, reasonable could be an
	 *         OperationAccessRight with Operation Write and AccessRight Allow)
	 */
	static Collection<OperationAccessRight> neededRightsModifying() {
		return List.of(allowWrite());
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#ALLOW} and
	 *         {@link Operation#READ}
	 */
	static OperationAccessRight allowRead() {
		return createOperationAccessRight(allowRead, AccessRight.ALLOW, Operation.READ);
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#ALLOW} and
	 *         {@link Operation#WRITE}
	 */
	static OperationAccessRight allowWrite() {
		return createOperationAccessRight(allowWrite, AccessRight.ALLOW, Operation.WRITE);
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#DENY} and
	 *         {@link Operation#READ}
	 */
	static OperationAccessRight denyRead() {
		return createOperationAccessRight(denyRead, AccessRight.DENY, Operation.READ);
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#DENY} and
	 *         {@link Operation#WRITE}
	 */
	static OperationAccessRight denyWrite() {
		return createOperationAccessRight(denyWrite, AccessRight.DENY, Operation.WRITE);
	}

	/**
	 * Finds the matching {@link OperationAccessRight}s in the given
	 * AccessRightProvider ({@link AccessRight} and {@link Operation} match). Adds a
	 * new {@link OperationAccessRight} to the given {@link AccessRightProvider} if
	 * it cannot be found.
	 * 
	 * @param rights                 the rights to search for
	 * @param existingRightsProvider the provider that either contains the rights or
	 *                               to which the rights are added
	 * @return the found or created rights, either way they are contained in the
	 *         AccessRightProvider
	 */
	static Collection<OperationAccessRight> findExistingRights(Collection<OperationAccessRight> rights,
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
	 * Only creates a new OperationAccessRight if the reference is null!
	 * 
	 * @param reference   the reference the OperationAccessRight is saved in if it
	 *                    is null
	 * @param accessRight
	 * @param operation
	 * @return the newly created OperationAccessRight if the reference is
	 *         {@code null}, otherwise the reference
	 */
	private static OperationAccessRight createOperationAccessRight(OperationAccessRight reference,
			AccessRight accessRight, Operation operation) {
		if (reference != null)
			return reference;
		reference = accessrightFactory.eINSTANCE.createOperationAccessRight();
		reference.setAccessRight(accessRight);
		reference.setOperation(operation);
		return reference;
	}

}
