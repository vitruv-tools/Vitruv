/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot;

import tools.vitruv.framework.change.echange.root.RemoveRootEObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove And Delete Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class RemoveAndDeleteRootImpl<T extends EObject> extends RemoveAndDeleteEObjectImpl<T, RemoveRootEObject<T>> implements RemoveAndDeleteRoot<T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoveAndDeleteRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.REMOVE_AND_DELETE_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetRemoveChange(RemoveRootEObject<T> newRemoveChange, NotificationChain msgs) {
		return super.basicSetRemoveChange(newRemoveChange, msgs);
	}

} //RemoveAndDeleteRootImpl
