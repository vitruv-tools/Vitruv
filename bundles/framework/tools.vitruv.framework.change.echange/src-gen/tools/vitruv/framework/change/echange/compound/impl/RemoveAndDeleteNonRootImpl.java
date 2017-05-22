/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot;

import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove And Delete Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class RemoveAndDeleteNonRootImpl<A extends EObject, T extends EObject> extends RemoveAndDeleteEObjectImpl<T, RemoveEReference<A, T>> implements RemoveAndDeleteNonRoot<A, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoveAndDeleteNonRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.REMOVE_AND_DELETE_NON_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetRemoveChange(RemoveEReference<A, T> newRemoveChange, NotificationChain msgs) {
		return super.basicSetRemoveChange(newRemoveChange, msgs);
	}

} //RemoveAndDeleteNonRootImpl
