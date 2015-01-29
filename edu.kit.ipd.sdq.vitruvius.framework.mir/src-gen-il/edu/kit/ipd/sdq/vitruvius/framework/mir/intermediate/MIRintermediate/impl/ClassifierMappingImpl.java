/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassifierMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Classifier Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassifierMappingImpl#getLeft <em>Left</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassifierMappingImpl#getRight <em>Right</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassifierMappingImpl#getInitializer <em>Initializer</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.ClassifierMappingImpl#getFeatureMapping <em>Feature Mapping</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassifierMappingImpl extends MappingImpl implements ClassifierMapping {
	/**
	 * The cached value of the '{@link #getLeft() <em>Left</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeft()
	 * @generated
	 * @ordered
	 */
	protected EClassifier left;

	/**
	 * The cached value of the '{@link #getRight() <em>Right</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRight()
	 * @generated
	 * @ordered
	 */
	protected EClassifier right;

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
	 * The cached value of the '{@link #getFeatureMapping() <em>Feature Mapping</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureMapping()
	 * @generated
	 * @ordered
	 */
	protected FeatureMapping featureMapping;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassifierMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MIRintermediatePackage.Literals.CLASSIFIER_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier getLeft() {
		if (left != null && left.eIsProxy()) {
			InternalEObject oldLeft = (InternalEObject)left;
			left = (EClassifier)eResolveProxy(oldLeft);
			if (left != oldLeft) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.CLASSIFIER_MAPPING__LEFT, oldLeft, left));
			}
		}
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier basicGetLeft() {
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeft(EClassifier newLeft) {
		EClassifier oldLeft = left;
		left = newLeft;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.CLASSIFIER_MAPPING__LEFT, oldLeft, left));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier getRight() {
		if (right != null && right.eIsProxy()) {
			InternalEObject oldRight = (InternalEObject)right;
			right = (EClassifier)eResolveProxy(oldRight);
			if (right != oldRight) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.CLASSIFIER_MAPPING__RIGHT, oldRight, right));
			}
		}
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier basicGetRight() {
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRight(EClassifier newRight) {
		EClassifier oldRight = right;
		right = newRight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.CLASSIFIER_MAPPING__RIGHT, oldRight, right));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Initializer> getInitializer() {
		if (initializer == null) {
			initializer = new EObjectContainmentEList<Initializer>(Initializer.class, this, MIRintermediatePackage.CLASSIFIER_MAPPING__INITIALIZER);
		}
		return initializer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMapping getFeatureMapping() {
		if (featureMapping != null && featureMapping.eIsProxy()) {
			InternalEObject oldFeatureMapping = (InternalEObject)featureMapping;
			featureMapping = (FeatureMapping)eResolveProxy(oldFeatureMapping);
			if (featureMapping != oldFeatureMapping) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING, oldFeatureMapping, featureMapping));
			}
		}
		return featureMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMapping basicGetFeatureMapping() {
		return featureMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeatureMapping(FeatureMapping newFeatureMapping, NotificationChain msgs) {
		FeatureMapping oldFeatureMapping = featureMapping;
		featureMapping = newFeatureMapping;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING, oldFeatureMapping, newFeatureMapping);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureMapping(FeatureMapping newFeatureMapping) {
		if (newFeatureMapping != featureMapping) {
			NotificationChain msgs = null;
			if (featureMapping != null)
				msgs = ((InternalEObject)featureMapping).eInverseRemove(this, MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING, FeatureMapping.class, msgs);
			if (newFeatureMapping != null)
				msgs = ((InternalEObject)newFeatureMapping).eInverseAdd(this, MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING, FeatureMapping.class, msgs);
			msgs = basicSetFeatureMapping(newFeatureMapping, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING, newFeatureMapping, newFeatureMapping));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING:
				if (featureMapping != null)
					msgs = ((InternalEObject)featureMapping).eInverseRemove(this, MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING, FeatureMapping.class, msgs);
				return basicSetFeatureMapping((FeatureMapping)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MIRintermediatePackage.CLASSIFIER_MAPPING__INITIALIZER:
				return ((InternalEList<?>)getInitializer()).basicRemove(otherEnd, msgs);
			case MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING:
				return basicSetFeatureMapping(null, msgs);
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
			case MIRintermediatePackage.CLASSIFIER_MAPPING__LEFT:
				if (resolve) return getLeft();
				return basicGetLeft();
			case MIRintermediatePackage.CLASSIFIER_MAPPING__RIGHT:
				if (resolve) return getRight();
				return basicGetRight();
			case MIRintermediatePackage.CLASSIFIER_MAPPING__INITIALIZER:
				return getInitializer();
			case MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING:
				if (resolve) return getFeatureMapping();
				return basicGetFeatureMapping();
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
			case MIRintermediatePackage.CLASSIFIER_MAPPING__LEFT:
				setLeft((EClassifier)newValue);
				return;
			case MIRintermediatePackage.CLASSIFIER_MAPPING__RIGHT:
				setRight((EClassifier)newValue);
				return;
			case MIRintermediatePackage.CLASSIFIER_MAPPING__INITIALIZER:
				getInitializer().clear();
				getInitializer().addAll((Collection<? extends Initializer>)newValue);
				return;
			case MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING:
				setFeatureMapping((FeatureMapping)newValue);
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
			case MIRintermediatePackage.CLASSIFIER_MAPPING__LEFT:
				setLeft((EClassifier)null);
				return;
			case MIRintermediatePackage.CLASSIFIER_MAPPING__RIGHT:
				setRight((EClassifier)null);
				return;
			case MIRintermediatePackage.CLASSIFIER_MAPPING__INITIALIZER:
				getInitializer().clear();
				return;
			case MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING:
				setFeatureMapping((FeatureMapping)null);
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
			case MIRintermediatePackage.CLASSIFIER_MAPPING__LEFT:
				return left != null;
			case MIRintermediatePackage.CLASSIFIER_MAPPING__RIGHT:
				return right != null;
			case MIRintermediatePackage.CLASSIFIER_MAPPING__INITIALIZER:
				return initializer != null && !initializer.isEmpty();
			case MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING:
				return featureMapping != null;
		}
		return super.eIsSet(featureID);
	}

} //ClassifierMappingImpl
