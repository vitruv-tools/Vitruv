package tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.AccessRight;
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.AccessrightFactory;
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.AcessRightProvider;
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.Operation;
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.OperationAccessRight;

/**
 * Implementing {@link #neededRightsViewing()} allows to specify the rights
 * needed to an element for it to be part of a generated view and
 * {@link #neededRightsModifying()} allows to specify the rights needed to
 * modify an element.
 * {@link #findOrAddRights(Collection, AcessRightProvider)} allows to get
 * OperationAccessRights that have the same operations and accessrights as the
 * given ones but are contained in the given AccessRightProvider (by either
 * finding them or adding them to the provider).
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public interface OperationAccessRightUtil {

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
		return createOperationAccessRight(AccessRight.ALLOW, Operation.READ);
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#ALLOW} and
	 *         {@link Operation#WRITE}
	 */
	static OperationAccessRight allowWrite() {
		return createOperationAccessRight(AccessRight.ALLOW, Operation.WRITE);
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#DENY} and
	 *         {@link Operation#READ}
	 */
	static OperationAccessRight denyRead() {
		return createOperationAccessRight(AccessRight.DENY, Operation.READ);
	}

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#DENY} and
	 *         {@link Operation#WRITE}
	 */
	static OperationAccessRight denyWrite() {
		return createOperationAccessRight(AccessRight.DENY, Operation.WRITE);
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
	static Collection<OperationAccessRight> findOrAddRights(Collection<OperationAccessRight> rights,
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
				OperationAccessRight created = AccessrightFactory.eINSTANCE.createOperationAccessRight();
				created.setAccessRight(right.getAccessRight());
				created.setOperation(right.getOperation());
				existingRightsProvider.getOperationAccessRights().add(created);
				found.add(created);
			}
		}
		return found;
	}

	private static OperationAccessRight createOperationAccessRight(
			AccessRight accessRight, Operation operation) {
		OperationAccessRight reference = AccessrightFactory.eINSTANCE.createOperationAccessRight();
		reference.setAccessRight(accessRight);
		reference.setOperation(operation);
		return reference;
	}

}
