/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassifierMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.EClassifierFeature;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl#getLeft <em>Left</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl#getRight <em>Right</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.FeatureMappingImpl#getClassifierMapping <em>Classifier Mapping</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeatureMappingImpl extends MappingImpl implements FeatureMapping {
	/**
	 * The cached value of the '{@link #getLeft() <em>Left</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeft()
	 * @generated
	 * @ordered
	 */
	protected EList<EClassifierFeature> left;

	/**
	 * The cached value of the '{@link #getRight() <em>Right</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRight()
	 * @generated
	 * @ordered
	 */
	protected EList<EClassifierFeature> right;

	/**
	 * The cached value of the '{@link #getClassifierMapping() <em>Classifier Mapping</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassifierMapping()
	 * @generated
	 * @ordered
	 */
	protected ClassifierMapping classifierMapping;

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
	public EList<EClassifierFeature> getLeft() {
		if (left == null) {
			left = new EObjectContainmentEList<EClassifierFeature>(EClassifierFeature.class, this, MIRintermediatePackage.FEATURE_MAPPING__LEFT);
		}
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EClassifierFeature> getRight() {
		if (right == null) {
			right = new EObjectContainmentEList<EClassifierFeature>(EClassifierFeature.class, this, MIRintermediatePackage.FEATURE_MAPPING__RIGHT);
		}
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassifierMapping getClassifierMapping() {
		if (classifierMapping != null && classifierMapping.eIsProxy()) {
			InternalEObject oldClassifierMapping = (InternalEObject)classifierMapping;
			classifierMapping = (ClassifierMapping)eResolveProxy(oldClassifierMapping);
			if (classifierMapping != oldClassifierMapping) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING, oldClassifierMapping, classifierMapping));
			}
		}
		return classifierMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassifierMapping basicGetClassifierMapping() {
		return classifierMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClassifierMapping(ClassifierMapping newClassifierMapping, NotificationChain msgs) {
		ClassifierMapping oldClassifierMapping = classifierMapping;
		classifierMapping = newClassifierMapping;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING, oldClassifierMapping, newClassifierMapping);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassifierMapping(ClassifierMapping newClassifierMapping) {
		if (newClassifierMapping != classifierMapping) {
			NotificationChain msgs = null;
			if (classifierMapping != null)
				msgs = ((InternalEObject)classifierMapping).eInverseRemove(this, MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING, ClassifierMapping.class, msgs);
			if (newClassifierMapping != null)
				msgs = ((InternalEObject)newClassifierMapping).eInverseAdd(this, MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING, ClassifierMapping.class, msgs);
			msgs = basicSetClassifierMapping(newClassifierMapping, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING, newClassifierMapping, newClassifierMapping));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING:
				if (classifierMapping != null)
					msgs = ((InternalEObject)classifierMapping).eInverseRemove(this, MIRintermediatePackage.CLASSIFIER_MAPPING__FEATURE_MAPPING, ClassifierMapping.class, msgs);
				return basicSetClassifierMapping((ClassifierMapping)otherEnd, msgs);
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
			case MIRintermediatePackage.FEATURE_MAPPING__LEFT:
				return ((InternalEList<?>)getLeft()).basicRemove(otherEnd, msgs);
			case MIRintermediatePackage.FEATURE_MAPPING__RIGHT:
				return ((InternalEList<?>)getRight()).basicRemove(otherEnd, msgs);
			case MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING:
				return basicSetClassifierMapping(null, msgs);
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
			case MIRintermediatePackage.FEATURE_MAPPING__LEFT:
				return getLeft();
			case MIRintermediatePackage.FEATURE_MAPPING__RIGHT:
				return getRight();
			case MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING:
				if (resolve) return getClassifierMapping();
				return basicGetClassifierMapping();
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
				getLeft().clear();
				getLeft().addAll((Collection<? extends EClassifierFeature>)newValue);
				return;
			case MIRintermediatePackage.FEATURE_MAPPING__RIGHT:
				getRight().clear();
				getRight().addAll((Collection<? extends EClassifierFeature>)newValue);
				return;
			case MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING:
				setClassifierMapping((ClassifierMapping)newValue);
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
				getLeft().clear();
				return;
			case MIRintermediatePackage.FEATURE_MAPPING__RIGHT:
				getRight().clear();
				return;
			case MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING:
				setClassifierMapping((ClassifierMapping)null);
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
				return left != null && !left.isEmpty();
			case MIRintermediatePackage.FEATURE_MAPPING__RIGHT:
				return right != null && !right.isEmpty();
			case MIRintermediatePackage.FEATURE_MAPPING__CLASSIFIER_MAPPING:
				return classifierMapping != null;
		}
		return super.eIsSet(featureID);
	}

} //FeatureMappingImpl
