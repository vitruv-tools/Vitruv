/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl;

import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassifierMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Configuration;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediatePackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MIR</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRImpl#getClassMappings <em>Class Mappings</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRImpl#getFeatureMappings <em>Feature Mappings</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRImpl#getPredicates <em>Predicates</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRImpl#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.impl.MIRImpl#getPackages <em>Packages</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MIRImpl extends MinimalEObjectImpl.Container implements MIR {
	/**
	 * The cached value of the '{@link #getClassMappings() <em>Class Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<ClassifierMapping> classMappings;

	/**
	 * The cached value of the '{@link #getFeatureMappings() <em>Feature Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<FeatureMapping> featureMappings;

	/**
	 * The cached value of the '{@link #getPredicates() <em>Predicates</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPredicates()
	 * @generated
	 * @ordered
	 */
	protected EList<Predicate> predicates;

	/**
	 * The cached value of the '{@link #getConfiguration() <em>Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfiguration()
	 * @generated
	 * @ordered
	 */
	protected Configuration configuration;

	/**
	 * The cached value of the '{@link #getPackages() <em>Packages</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackages()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> packages;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MIRImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MIRintermediatePackage.Literals.MIR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ClassifierMapping> getClassMappings() {
		if (classMappings == null) {
			classMappings = new EObjectContainmentEList<ClassifierMapping>(ClassifierMapping.class, this, MIRintermediatePackage.MIR__CLASS_MAPPINGS);
		}
		return classMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FeatureMapping> getFeatureMappings() {
		if (featureMappings == null) {
			featureMappings = new EObjectContainmentEList<FeatureMapping>(FeatureMapping.class, this, MIRintermediatePackage.MIR__FEATURE_MAPPINGS);
		}
		return featureMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Predicate> getPredicates() {
		if (predicates == null) {
			predicates = new EObjectContainmentEList<Predicate>(Predicate.class, this, MIRintermediatePackage.MIR__PREDICATES);
		}
		return predicates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConfiguration(Configuration newConfiguration, NotificationChain msgs) {
		Configuration oldConfiguration = configuration;
		configuration = newConfiguration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.MIR__CONFIGURATION, oldConfiguration, newConfiguration);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfiguration(Configuration newConfiguration) {
		if (newConfiguration != configuration) {
			NotificationChain msgs = null;
			if (configuration != null)
				msgs = ((InternalEObject)configuration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MIRintermediatePackage.MIR__CONFIGURATION, null, msgs);
			if (newConfiguration != null)
				msgs = ((InternalEObject)newConfiguration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MIRintermediatePackage.MIR__CONFIGURATION, null, msgs);
			msgs = basicSetConfiguration(newConfiguration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MIRintermediatePackage.MIR__CONFIGURATION, newConfiguration, newConfiguration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackage> getPackages() {
		if (packages == null) {
			packages = new EObjectResolvingEList<EPackage>(EPackage.class, this, MIRintermediatePackage.MIR__PACKAGES);
		}
		return packages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MIRintermediatePackage.MIR__CLASS_MAPPINGS:
				return ((InternalEList<?>)getClassMappings()).basicRemove(otherEnd, msgs);
			case MIRintermediatePackage.MIR__FEATURE_MAPPINGS:
				return ((InternalEList<?>)getFeatureMappings()).basicRemove(otherEnd, msgs);
			case MIRintermediatePackage.MIR__PREDICATES:
				return ((InternalEList<?>)getPredicates()).basicRemove(otherEnd, msgs);
			case MIRintermediatePackage.MIR__CONFIGURATION:
				return basicSetConfiguration(null, msgs);
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
			case MIRintermediatePackage.MIR__CLASS_MAPPINGS:
				return getClassMappings();
			case MIRintermediatePackage.MIR__FEATURE_MAPPINGS:
				return getFeatureMappings();
			case MIRintermediatePackage.MIR__PREDICATES:
				return getPredicates();
			case MIRintermediatePackage.MIR__CONFIGURATION:
				return getConfiguration();
			case MIRintermediatePackage.MIR__PACKAGES:
				return getPackages();
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
			case MIRintermediatePackage.MIR__CLASS_MAPPINGS:
				getClassMappings().clear();
				getClassMappings().addAll((Collection<? extends ClassifierMapping>)newValue);
				return;
			case MIRintermediatePackage.MIR__FEATURE_MAPPINGS:
				getFeatureMappings().clear();
				getFeatureMappings().addAll((Collection<? extends FeatureMapping>)newValue);
				return;
			case MIRintermediatePackage.MIR__PREDICATES:
				getPredicates().clear();
				getPredicates().addAll((Collection<? extends Predicate>)newValue);
				return;
			case MIRintermediatePackage.MIR__CONFIGURATION:
				setConfiguration((Configuration)newValue);
				return;
			case MIRintermediatePackage.MIR__PACKAGES:
				getPackages().clear();
				getPackages().addAll((Collection<? extends EPackage>)newValue);
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
			case MIRintermediatePackage.MIR__CLASS_MAPPINGS:
				getClassMappings().clear();
				return;
			case MIRintermediatePackage.MIR__FEATURE_MAPPINGS:
				getFeatureMappings().clear();
				return;
			case MIRintermediatePackage.MIR__PREDICATES:
				getPredicates().clear();
				return;
			case MIRintermediatePackage.MIR__CONFIGURATION:
				setConfiguration((Configuration)null);
				return;
			case MIRintermediatePackage.MIR__PACKAGES:
				getPackages().clear();
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
			case MIRintermediatePackage.MIR__CLASS_MAPPINGS:
				return classMappings != null && !classMappings.isEmpty();
			case MIRintermediatePackage.MIR__FEATURE_MAPPINGS:
				return featureMappings != null && !featureMappings.isEmpty();
			case MIRintermediatePackage.MIR__PREDICATES:
				return predicates != null && !predicates.isEmpty();
			case MIRintermediatePackage.MIR__CONFIGURATION:
				return configuration != null;
			case MIRintermediatePackage.MIR__PACKAGES:
				return packages != null && !packages.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MIRImpl
