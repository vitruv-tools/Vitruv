package accesscontrol;

import java.util.Collection;

import accesscontrol.internal.OperationAccessRightEvaluatorImpl;
import accesscontrolsystem.accessright.AccessRight;
import accesscontrolsystem.accessright.AcessRightProvider;
import accesscontrolsystem.accessright.Operation;
import accesscontrolsystem.accessright.OperationAccessRight;

/**
 * Evaluator for {@link OperationAccessRight}s to compute filtered views on a
 * source. {@link #hasAccess(Collection, Collection)} allows to
 * specify how {@link OperationAccessRight}s are evaluated against each other
 * (for example the absence of a rule in the given collection allows or denies
 * the access to an element (allow list versus block list approach)).
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public interface OperationAccessRightEvaluator {

	/**
	 * The method computes if for all needed rights a matching given right is
	 * present.
	 * 
	 * @param given
	 * @param needed
	 * @return whether or not sufficient OperationAccessRights are given
	 */
	boolean hasAccess(Collection<OperationAccessRight> given, Collection<OperationAccessRight> needed);

	static OperationAccessRightEvaluator create() {
		return new OperationAccessRightEvaluatorImpl();
	}

}