/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassMappingImpl#getLeft <em>Left</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassMappingImpl#getRight <em>Right</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassMappingImpl#getPredicates <em>Predicates</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassMappingImpl#getInitializer <em>Initializer</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassMappingImpl extends MinimalEObjectImpl.Container implements ClassMapping {
	/**
	 * The cached value of the '{@link #getLeft() <em>Left</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeft()
	 * @generated
	 * @ordered
	 */
	protected EClass left;

	/**
	 * The cached value of the '{@link #getRight() <em>Right</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRight()
	 * @generated
	 * @ordered
	 */
	protected EClass right;

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
	 * The cached value of the '{@link #getInitializer() <em>Initializer</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializer()
	 * @generated
	 * @ordered
	 */
	protected EList<Initializer> initializer;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MIRintermediatePackage.Literals.CLASS_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLeft() {
		if (left != null && left.eIsProxy()) {
			InternalEObject oldLeft = (InternalEObject)left;
			left = (EClass)eResolveProxy(oldLeft);
			if (left != oldLeft) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.CLASS_MAPPING__LEFT, oldLeft, left));
			}
		}
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetLeft() {
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeft(EClass newLeft) {
		EClass oldLeft = left;
		left = newLeft;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.CLASS_MAPPING__LEFT, oldLeft, left));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRight() {
		if (right != null && right.eIsProxy()) {
			InternalEObject oldRight = (InternalEObject)right;
			right = (EClass)eResolveProxy(oldRight);
			if (right != oldRight) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.CLASS_MAPPING__RIGHT, oldRight, right));
			}
		}
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetRight() {
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRight(EClass newRight) {
		EClass oldRight = right;
		right = newRight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.CLASS_MAPPING__RIGHT, oldRight, right));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Predicate> getPredicates() {
		if (predicates == null) {
			predicates = new EObjectResolvingEList<Predicate>(Predicate.class, this, MIRintermediatePackage.CLASS_MAPPING__PREDICATES);
		}
		return predicates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Initializer> getInitializer() {
		if (initializer == null) {
			initializer = new EObjectContainmentEList<Initializer>(Initializer.class, this, MIRintermediatePackage.CLASS_MAPPING__INITIALIZER);
		}
		return initializer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MIRintermediatePackage.CLASS_MAPPING__INITIALIZER:
				return ((InternalEList<?>)getInitializer()).basicRemove(otherEnd, msgs);
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
			case MIRintermediatePackage.CLASS_MAPPING__LEFT:
				if (resolve) return getLeft();
				return basicGetLeft();
			case MIRintermediatePackage.CLASS_MAPPING__RIGHT:
				if (resolve) return getRight();
				return basicGetRight();
			case MIRintermediatePackage.CLASS_MAPPING__PREDICATES:
				return getPredicates();
			case MIRintermediatePackage.CLASS_MAPPING__INITIALIZER:
				return getInitializer();
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
			case MIRintermediatePackage.CLASS_MAPPING__LEFT:
				setLeft((EClass)newValue);
				return;
			case MIRintermediatePackage.CLASS_MAPPING__RIGHT:
				setRight((EClass)newValue);
				return;
			case MIRintermediatePackage.CLASS_MAPPING__PREDICATES:
				getPredicates().clear();
				getPredicates().addAll((Collection<? extends Predicate>)newValue);
				return;
			case MIRintermediatePackage.CLASS_MAPPING__INITIALIZER:
				getInitializer().clear();
				getInitializer().addAll((Collection<? extends Initializer>)newValue);
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
			case MIRintermediatePackage.CLASS_MAPPING__LEFT:
				setLeft((EClass)null);
				return;
			case MIRintermediatePackage.CLASS_MAPPING__RIGHT:
				setRight((EClass)null);
				return;
			case MIRintermediatePackage.CLASS_MAPPING__PREDICATES:
				getPredicates().clear();
				return;
			case MIRintermediatePackage.CLASS_MAPPING__INITIALIZER:
				getInitializer().clear();
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
			case MIRintermediatePackage.CLASS_MAPPING__LEFT:
				return left != null;
			case MIRintermediatePackage.CLASS_MAPPING__RIGHT:
				return right != null;
			case MIRintermediatePackage.CLASS_MAPPING__PREDICATES:
				return predicates != null && !predicates.isEmpty();
			case MIRintermediatePackage.CLASS_MAPPING__INITIALIZER:
				return initializer != null && !initializer.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ClassMappingImpl
