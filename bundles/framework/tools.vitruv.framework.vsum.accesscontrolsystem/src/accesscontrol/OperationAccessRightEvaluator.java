package accesscontrol;

import java.util.Collection;

import accesscontrolsystem.accessright.AccessRight;
import accesscontrolsystem.accessright.AcessRightProvider;
import accesscontrolsystem.accessright.Operation;
import accesscontrolsystem.accessright.OperationAccessRight;

/**
 * Evaluator for {@link OperationAccessRight}s to compute filtered views on a
 * source. Implementing {@link #neededRightsViewing()} allows to specify the
 * rights needed to an element for it to be part of a generated view and
 * {@link #neededRightsModifying()} allows to specify the rights needed to
 * modify an element. {@link #hasAccess(Collection, Collection)} allows to
 * specify how {@link OperationAccessRight}s are evaluated against each other
 * (for example the absence of a rule in the given collection allows or denies
 * the access to an element (allow list versus block list approach)).
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public interface OperationAccessRightEvaluator {

	/**
	 * 
	 * @return the {@link OperationAccessRight}s needed to view an element (used to
	 *         filter for a view creation, reasonable could be an
	 *         OperationAccessRight with Operation Read and AccessRight Allow)
	 */
	Collection<OperationAccessRight> neededRightsViewing();

	/**
	 * 
	 * @return the {@link OperationAccessRight}s needed to modify an element (used
	 *         to filter for an element modification, reasonable could be an
	 *         OperationAccessRight with Operation Write and AccessRight Allow)
	 */
	Collection<OperationAccessRight> neededRightsModifying();

	/**
	 * Finds the matching {@link OperationAccessRight}s in the given
	 * AccessRightProvider ({@link AccessRight} and {@link Operation} match). Adds a
	 * new {@link OperationAccessRight} to the given {@link AccessRightProvider}if
	 * it cannot be found.
	 * 
	 * @param rights
	 * @param existingRightsProvider
	 * @return
	 */
	Collection<OperationAccessRight> findExistingRights(Collection<OperationAccessRight> rights,
			AcessRightProvider existingRightsProvider);

	/**
	 * The method computes if for all needed rights a matching given right is
	 * present.
	 * 
	 * @param given
	 * @param needed
	 * @return
	 */
	boolean hasAccess(Collection<OperationAccessRight> given, Collection<OperationAccessRight> needed);

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#ALLOW} and
	 *         {@link Operation#READ}
	 */
	OperationAccessRight allowRead();

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#ALLOW} and
	 *         {@link Operation#WRITE}
	 */
	OperationAccessRight allowWrite();

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#DENY} and
	 *         {@link Operation#READ}
	 */
	OperationAccessRight denyRead();

	/**
	 * 
	 * @return {@link OperationAccessRight} with {@link AccessRight#DENY} and
	 *         {@link Operation#WRITE}
	 */
	OperationAccessRight denyWrite();

}