/**
 */
package tools.vitruv.framework.change.echange.compound.impl;

import tools.vitruv.framework.change.echange.SubtractiveEChange;
import tools.vitruv.framework.change.echange.compound.CompoundPackage;
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature;
import tools.vitruv.framework.change.echange.compound.impl.CompoundEChangeImpl;
import tools.vitruv.framework.change.echange.feature.FeatureEChange;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Explicit Unset EFeature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.change.echange.compound.impl.ExplicitUnsetEFeatureImpl#getSubtractiveChanges <em>Subtractive Changes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExplicitUnsetEFeatureImpl<A extends EObject, F extends EStructuralFeature, T extends Object, S extends FeatureEChange<A, F> & SubtractiveEChange<T>> extends CompoundEChangeImpl implements ExplicitUnsetEFeature<A, F, T, S> {
    /**
	 * The cached value of the '{@link #getSubtractiveChanges() <em>Subtractive Changes</em>}' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSubtractiveChanges()
	 * @generated
	 * @ordered
	 */
    protected EList<S> subtractiveChanges;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected ExplicitUnsetEFeatureImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return CompoundPackage.Literals.EXPLICIT_UNSET_EFEATURE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<S> getSubtractiveChanges() {
		if (subtractiveChanges == null) {
			subtractiveChanges = new EObjectResolvingEList.Unsettable<S>(FeatureEChange.class, this, CompoundPackage.EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES);
		}
		return subtractiveChanges;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetSubtractiveChanges() {
		if (subtractiveChanges != null) ((InternalEList.Unsettable<?>)subtractiveChanges).unset();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetSubtractiveChanges() {
		return subtractiveChanges != null && ((InternalEList.Unsettable<?>)subtractiveChanges).isSet();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES:
				return getSubtractiveChanges();
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
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES:
				getSubtractiveChanges().clear();
				getSubtractiveChanges().addAll((Collection<? extends S>)newValue);
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
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES:
				unsetSubtractiveChanges();
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
			case CompoundPackage.EXPLICIT_UNSET_EFEATURE__SUBTRACTIVE_CHANGES:
				return isSetSubtractiveChanges();
		}
		return super.eIsSet(featureID);
	}

} //ExplicitUnsetEFeatureImpl
