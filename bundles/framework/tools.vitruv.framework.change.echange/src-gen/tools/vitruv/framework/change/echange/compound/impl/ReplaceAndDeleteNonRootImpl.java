/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.ReplaceAndDeleteNonRoot;

import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replace And Delete Non Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ReplaceAndDeleteNonRootImpl<A extends EObject, T extends EObject> extends RemoveAndDeleteEObjectImpl<T, ReplaceSingleValuedEReference<A, T>> implements ReplaceAndDeleteNonRoot<A, T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReplaceAndDeleteNonRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompoundPackage.Literals.REPLACE_AND_DELETE_NON_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetRemoveChange(ReplaceSingleValuedEReference<A, T> newRemoveChange, NotificationChain msgs) {
		return super.basicSetRemoveChange(newRemoveChange, msgs);
	}

} //ReplaceAndDeleteNonRootImpl
