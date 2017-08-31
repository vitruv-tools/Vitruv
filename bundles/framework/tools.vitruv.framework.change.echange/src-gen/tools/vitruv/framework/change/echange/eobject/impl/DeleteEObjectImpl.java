/**
 */
package tools.vitruv.framework.change.echange.eobject.impl;

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.eobject.EobjectPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delete EObject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.eobject.impl.DeleteEObjectImpl#getConsequentialRemoveChanges <em>Consequential Remove Changes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeleteEObjectImpl<A extends EObject> extends EObjectExistenceEChangeImpl<A> implements DeleteEObject<A> {
	/**
	 * The cached value of the '{@link #getConsequentialRemoveChanges() <em>Consequential Remove Changes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConsequentialRemoveChanges()
	 * @generated
	 * @ordered
	 */
	protected EList<EChange> consequentialRemoveChanges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeleteEObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EobjectPackage.Literals.DELETE_EOBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EChange> getConsequentialRemoveChanges() {
		if (consequentialRemoveChanges == null) {
			consequentialRemoveChanges = new EObjectResolvingEList<EChange>(EChange.class, this, EobjectPackage.DELETE_EOBJECT__CONSEQUENTIAL_REMOVE_CHANGES);
		}
		return consequentialRemoveChanges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EobjectPackage.DELETE_EOBJECT__CONSEQUENTIAL_REMOVE_CHANGES:
				return getConsequentialRemoveChanges();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EobjectPackage.DELETE_EOBJECT__CONSEQUENTIAL_REMOVE_CHANGES:
				getConsequentialRemoveChanges().clear();
				getConsequentialRemoveChanges().addAll((Collection<? extends EChange>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case EobjectPackage.DELETE_EOBJECT__CONSEQUENTIAL_REMOVE_CHANGES:
				getConsequentialRemoveChanges().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EobjectPackage.DELETE_EOBJECT__CONSEQUENTIAL_REMOVE_CHANGES:
				return consequentialRemoveChanges != null && !consequentialRemoveChanges.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DeleteEObjectImpl
