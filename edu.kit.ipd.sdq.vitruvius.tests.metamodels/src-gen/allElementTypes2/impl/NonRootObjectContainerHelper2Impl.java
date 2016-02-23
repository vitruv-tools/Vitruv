/**
 */
package allElementTypes2.impl;

import allElementTypes2.AllElementTypes2Package;
import allElementTypes2.NonRoot2;
import allElementTypes2.NonRootObjectContainerHelper2;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Non Root Object Container Helper2</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link allElementTypes2.impl.NonRootObjectContainerHelper2Impl#getNonRootObjectsContainment2 <em>Non Root Objects Containment2</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NonRootObjectContainerHelper2Impl extends Identified2Impl implements NonRootObjectContainerHelper2 {
	/**
	 * The cached value of the '{@link #getNonRootObjectsContainment2() <em>Non Root Objects Containment2</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonRootObjectsContainment2()
	 * @generated
	 * @ordered
	 */
	protected EList<NonRoot2> nonRootObjectsContainment2;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NonRootObjectContainerHelper2Impl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AllElementTypes2Package.Literals.NON_ROOT_OBJECT_CONTAINER_HELPER2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NonRoot2> getNonRootObjectsContainment2() {
		if (nonRootObjectsContainment2 == null) {
			nonRootObjectsContainment2 = new EObjectContainmentEList<NonRoot2>(NonRoot2.class, this, AllElementTypes2Package.NON_ROOT_OBJECT_CONTAINER_HELPER2__NON_ROOT_OBJECTS_CONTAINMENT2);
		}
		return nonRootObjectsContainment2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AllElementTypes2Package.NON_ROOT_OBJECT_CONTAINER_HELPER2__NON_ROOT_OBJECTS_CONTAINMENT2:
				return ((InternalEList<?>)getNonRootObjectsContainment2()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AllElementTypes2Package.NON_ROOT_OBJECT_CONTAINER_HELPER2__NON_ROOT_OBJECTS_CONTAINMENT2:
				return getNonRootObjectsContainment2();
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
			case AllElementTypes2Package.NON_ROOT_OBJECT_CONTAINER_HELPER2__NON_ROOT_OBJECTS_CONTAINMENT2:
				getNonRootObjectsContainment2().clear();
				getNonRootObjectsContainment2().addAll((Collection<? extends NonRoot2>)newValue);
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
			case AllElementTypes2Package.NON_ROOT_OBJECT_CONTAINER_HELPER2__NON_ROOT_OBJECTS_CONTAINMENT2:
				getNonRootObjectsContainment2().clear();
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
			case AllElementTypes2Package.NON_ROOT_OBJECT_CONTAINER_HELPER2__NON_ROOT_OBJECTS_CONTAINMENT2:
				return nonRootObjectsContainment2 != null && !nonRootObjectsContainment2.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //NonRootObjectContainerHelper2Impl
