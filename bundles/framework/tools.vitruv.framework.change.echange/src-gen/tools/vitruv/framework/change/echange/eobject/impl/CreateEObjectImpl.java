/**
 */
package tools.vitruv.framework.change.echange.eobject.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.EChangePackage;

import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Create EObject</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CreateEObjectImpl<A extends EObject> extends EObjectExistenceEChangeImpl<A> implements CreateEObject<A> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreateEObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EobjectPackage.Literals.CREATE_EOBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean resolveBefore(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean resolveAfter(final ResourceSet resourceSet) {
		return this.resolve(resourceSet, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == EChange.class) {
			switch (baseOperationID) {
				case EChangePackage.ECHANGE___RESOLVE_BEFORE__RESOURCESET: return EobjectPackage.CREATE_EOBJECT___RESOLVE_BEFORE__RESOURCESET;
				case EChangePackage.ECHANGE___RESOLVE_AFTER__RESOURCESET: return EobjectPackage.CREATE_EOBJECT___RESOLVE_AFTER__RESOURCESET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case EobjectPackage.CREATE_EOBJECT___RESOLVE_BEFORE__RESOURCESET:
				return resolveBefore((ResourceSet)arguments.get(0));
			case EobjectPackage.CREATE_EOBJECT___RESOLVE_AFTER__RESOURCESET:
				return resolveAfter((ResourceSet)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //CreateEObjectImpl
