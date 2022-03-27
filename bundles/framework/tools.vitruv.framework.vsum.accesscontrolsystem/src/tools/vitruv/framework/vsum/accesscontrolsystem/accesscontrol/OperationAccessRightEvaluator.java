package tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol;

import java.util.Collection;

import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.internal.OperationAccessRightEvaluatorImpl;
import tools.vitruv.framework.vsum.accesscontrolsystem.accessright.OperationAccessRight;

/**
 * Evaluator for {@link OperationAccessRight}s to compute filtered views on a
 * source. {@link #hasAccess(Collection, Collection)} allows to specify how
 * {@link OperationAccessRight}s are evaluated against each other (for example
 * the absence of a rule in the given collection allows or denies the access to
 * an element (allow list versus block list approach)).
 * 
 * @author Thomas Weber (thomas.weber@student.kit.edu)
 *
 */
public interface OperationAccessRightEvaluator {

	/**
	 * The method computes if for all needed rights a matching given right is
	 * present.
	 * 
	 * @param given rights available, may not be {@code null}
	 * @param needed rights necessary to gain access, may not be {@code null}
	 * @return whether or not sufficient OperationAccessRights are given
	 */
	boolean hasAccess(Collection<OperationAccessRight> given, Collection<OperationAccessRight> needed);

	/**
	 * Creates a new default OperationAccessRightEvaluator. Uses an allow list
	 * approach.
	 * 
	 * @return a new OperationAccessRightEvaluator
	 */
	static OperationAccessRightEvaluator create() {
		return new OperationAccessRightEvaluatorImpl();
	}

}