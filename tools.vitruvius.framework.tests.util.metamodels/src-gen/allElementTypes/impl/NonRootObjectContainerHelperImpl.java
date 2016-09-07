/**
 */
package allElementTypes.impl;

import allElementTypes.AllElementTypesPackage;
import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Non Root Object Container Helper</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link allElementTypes.impl.NonRootObjectContainerHelperImpl#getNonRootObjectsContainment <em>Non Root Objects Containment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NonRootObjectContainerHelperImpl extends IdentifiedImpl implements NonRootObjectContainerHelper {
	/**
	 * The cached value of the '{@link #getNonRootObjectsContainment() <em>Non Root Objects Containment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonRootObjectsContainment()
	 * @generated
	 * @ordered
	 */
	protected EList<NonRoot> nonRootObjectsContainment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NonRootObjectContainerHelperImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AllElementTypesPackage.Literals.NON_ROOT_OBJECT_CONTAINER_HELPER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NonRoot> getNonRootObjectsContainment() {
		if (nonRootObjectsContainment == null) {
			nonRootObjectsContainment = new EObjectContainmentEList<NonRoot>(NonRoot.class, this, AllElementTypesPackage.NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT);
		}
		return nonRootObjectsContainment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AllElementTypesPackage.NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT:
				return ((InternalEList<?>)getNonRootObjectsContainment()).basicRemove(otherEnd, msgs);
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
			case AllElementTypesPackage.NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT:
				return getNonRootObjectsContainment();
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
			case AllElementTypesPackage.NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT:
				getNonRootObjectsContainment().clear();
				getNonRootObjectsContainment().addAll((Collection<? extends NonRoot>)newValue);
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
			case AllElementTypesPackage.NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT:
				getNonRootObjectsContainment().clear();
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
			case AllElementTypesPackage.NON_ROOT_OBJECT_CONTAINER_HELPER__NON_ROOT_OBJECTS_CONTAINMENT:
				return nonRootObjectsContainment != null && !nonRootObjectsContainment.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //NonRootObjectContainerHelperImpl
