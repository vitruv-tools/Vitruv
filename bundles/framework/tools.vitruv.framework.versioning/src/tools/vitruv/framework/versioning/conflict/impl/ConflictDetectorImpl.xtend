/** 
 */
package tools.vitruv.framework.versioning.conflict.impl

import java.lang.reflect.InvocationTargetException
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import tools.vitruv.framework.versioning.branch.BranchDiff
import tools.vitruv.framework.versioning.conflict.Conflict
import tools.vitruv.framework.versioning.conflict.ConflictDetector
import tools.vitruv.framework.versioning.conflict.ConflictPackage

/** 
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Detector</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
class ConflictDetectorImpl extends MinimalEObjectImpl.Container implements ConflictDetector {
	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected new() {
		super()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override protected EClass eStaticClass() {
		return ConflictPackage.Literals.CONFLICT_DETECTOR
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override EList<Conflict> detectConflicts(BranchDiff diff) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException()
	}

	/** 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	override Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {

		switch (operationID) {
			case ConflictPackage.CONFLICT_DETECTOR___DETECT_CONFLICTS__BRANCHDIFF: {
				return detectConflicts((arguments.get(0) as BranchDiff))
			}
		}
		return super.eInvoke(operationID, arguments)
	} // ConflictDetectorImpl
}
