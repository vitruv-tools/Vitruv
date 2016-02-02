/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.InsertInEList;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.list.RemoveFromEList;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.CompoundPackage
 * @generated
 */
public interface CompoundFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    CompoundFactory eINSTANCE = edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.compound.impl.CompoundFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Move EObject</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Move EObject</em>'.
     * @generated
     */
    <A extends EObject, B extends EObject, T extends EObject> MoveEObject<A, B, T> createMoveEObject();

    /**
     * Returns a new object of class '<em>Replace In EList</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Replace In EList</em>'.
     * @generated
     */
    <A extends EObject, F extends EStructuralFeature, T extends EObject, R extends RemoveFromEList & EFeatureChange<A, F> & SubtractiveEChange<T>, I extends InsertInEList & EFeatureChange<A, F> & AdditiveEReferenceChange<T>> ReplaceInEList<A, F, T, R, I> createReplaceInEList();

    /**
     * Returns a new object of class '<em>Explicit Unset EFeature</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Explicit Unset EFeature</em>'.
     * @generated
     */
    <A extends EObject, F extends EStructuralFeature, T extends Object, S extends EFeatureChange<A, F> & SubtractiveEChange<T>> ExplicitUnsetEFeature<A, F, T, S> createExplicitUnsetEFeature();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    CompoundPackage getCompoundPackage();

} //CompoundFactory
