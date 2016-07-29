/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.FeatureEChange;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Explicit Unset EFeature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ExplicitUnsetEFeature#getSubtractiveChanges <em>Subtractive Changes</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getExplicitUnsetEFeature()
 * @model TBounds="org.eclipse.emf.ecore.EJavaObject"
 * @generated
 */
public interface ExplicitUnsetEFeature<A extends EObject, F extends EStructuralFeature, T extends Object, S extends FeatureEChange<A, F> & SubtractiveEChange<T>> extends CompoundEChange {
    /**
     * Returns the value of the '<em><b>Subtractive Changes</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Subtractive Changes</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Subtractive Changes</em>' reference list.
     * @see #isSetSubtractiveChanges()
     * @see #unsetSubtractiveChanges()
     * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage#getExplicitUnsetEFeature_SubtractiveChanges()
     * @model unsettable="true" required="true"
     * @generated
     */
    EList<S> getSubtractiveChanges();

    /**
     * Unsets the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ExplicitUnsetEFeature#getSubtractiveChanges <em>Subtractive Changes</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetSubtractiveChanges()
     * @see #getSubtractiveChanges()
     * @generated
     */
    void unsetSubtractiveChanges();

    /**
     * Returns whether the value of the '{@link edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.ExplicitUnsetEFeature#getSubtractiveChanges <em>Subtractive Changes</em>}' reference list is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Subtractive Changes</em>' reference list is set.
     * @see #unsetSubtractiveChanges()
     * @see #getSubtractiveChanges()
     * @generated
     */
    boolean isSetSubtractiveChanges();

} // ExplicitUnsetEFeature
