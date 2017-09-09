/**
 */
package tools.vitruv.framework.change.echange.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EChange</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class EChangeImpl extends MinimalEObjectImpl.Container implements EChange {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EChangePackage.Literals.ECHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResolved() {
		return tools.vitruv.framework.change.echange.resolve.ResolutionChecker.isResolved(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getInvolvedEObjects() {
		org.eclipse.emf.common.util.EList<EObject> result = new org.eclipse.emf.common.util.BasicEList<EObject>();
		for (EObject involvedEObject : tools.vitruv.framework.change.echange.EChangeInformationExtractor.getInvolvedEObjects(this)) {
			result.add(involvedEObject);
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case EChangePackage.ECHANGE___IS_RESOLVED:
				return isResolved();
			case EChangePackage.ECHANGE___GET_INVOLVED_EOBJECTS:
				return getInvolvedEObjects();
		}
		return super.eInvoke(operationID, arguments);
	}

} //EChangeImpl
