/**
 */
package tools.vitruv.framework.versioning.conflict.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import tools.vitruv.framework.versioning.branch.BranchDiff;

import tools.vitruv.framework.versioning.conflict.Conflict;
import tools.vitruv.framework.versioning.conflict.ConflictDetector;
import tools.vitruv.framework.versioning.conflict.ConflictPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Detector</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ConflictDetectorImpl extends MinimalEObjectImpl.Container implements ConflictDetector {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConflictDetectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConflictPackage.Literals.CONFLICT_DETECTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Conflict> detectConflicts(BranchDiff diff) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ConflictPackage.CONFLICT_DETECTOR___DETECT_CONFLICTS__BRANCHDIFF:
				return detectConflicts((BranchDiff)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //ConflictDetectorImpl
