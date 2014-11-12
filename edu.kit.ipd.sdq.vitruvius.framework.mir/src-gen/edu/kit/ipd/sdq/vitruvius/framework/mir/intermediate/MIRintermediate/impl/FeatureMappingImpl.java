/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl#getLeft <em>Left</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl#getRight <em>Right</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl#getPredicates <em>Predicates</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeatureMappingImpl extends MinimalEObjectImpl.Container implements FeatureMapping {
	/**
	 * The cached value of the '{@link #getLeft() <em>Left</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeft()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature left;

	/**
	 * The cached value of the '{@link #getRight() <em>Right</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRight()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature right;

	/**
	 * The cached value of the '{@link #getPredicates() <em>Predicates</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPredicates()
	 * @generated
	 * @ordered
	 */
	protected EList<Predicate> predicates;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MIRintermediatePackage.Literals.FEATURE_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature getLeft() {
		if (left != null && left.eIsProxy()) {
			InternalEObject oldLeft = (InternalEObject)left;
			left = (EStructuralFeature)eResolveProxy(oldLeft);
			if (left != oldLeft) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.FEATURE_MAPPING__LEFT, oldLeft, left));
			}
		}
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature basicGetLeft() {
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeft(EStructuralFeature newLeft) {
		EStructuralFeature oldLeft = left;
		left = newLeft;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.FEATURE_MAPPING__LEFT, oldLeft, left));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature getRight() {
		if (right != null && right.eIsProxy()) {
			InternalEObject oldRight = (InternalEObject)right;
			right = (EStructuralFeature)eResolveProxy(oldRight);
			if (right != oldRight) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.FEATURE_MAPPING__RIGHT, oldRight, right));
			}
		}
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature basicGetRight() {
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRight(EStructuralFeature newRight) {
		EStructuralFeature oldRight = right;
		right = newRight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.FEATURE_MAPPING__RIGHT, oldRight, right));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Predicate> getPredicates() {
		if (predicates == null) {
			predicates = new EObjectResolvingEList<Predicate>(Predicate.class, this, MIRintermediatePackage.FEATURE_MAPPING__PREDICATES);
		}
		return predicates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MIRintermediatePackage.FEATURE_MAPPING__LEFT:
				if (resolve) return getLeft();
				return basicGetLeft();
			case MIRintermediatePackage.FEATURE_MAPPING__RIGHT:
				if (resolve) return getRight();
				return basicGetRight();
			case MIRintermediatePackage.FEATURE_MAPPING__PREDICATES:
				return getPredicates();
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
			case MIRintermediatePackage.FEATURE_MAPPING__LEFT:
				setLeft((EStructuralFeature)newValue);
				return;
			case MIRintermediatePackage.FEATURE_MAPPING__RIGHT:
				setRight((EStructuralFeature)newValue);
				return;
			case MIRintermediatePackage.FEATURE_MAPPING__PREDICATES:
				getPredicates().clear();
				getPredicates().addAll((Collection<? extends Predicate>)newValue);
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
			case MIRintermediatePackage.FEATURE_MAPPING__LEFT:
				setLeft((EStructuralFeature)null);
				return;
			case MIRintermediatePackage.FEATURE_MAPPING__RIGHT:
				setRight((EStructuralFeature)null);
				return;
			case MIRintermediatePackage.FEATURE_MAPPING__PREDICATES:
				getPredicates().clear();
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
			case MIRintermediatePackage.FEATURE_MAPPING__LEFT:
				return left != null;
			case MIRintermediatePackage.FEATURE_MAPPING__RIGHT:
				return right != null;
			case MIRintermediatePackage.FEATURE_MAPPING__PREDICATES:
				return predicates != null && !predicates.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //FeatureMappingImpl
