/**
 */
package tools.vitruv.framework.change.echange.compound;

import tools.vitruv.framework.change.echange.SubtractiveEChange;
import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange;
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import tools.vitruv.framework.change.echange.AdditiveEChange;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruv.framework.change.echange.compound.CompoundPackage
 * @generated
 */
public interface CompoundFactory extends EFactory {
    /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    CompoundFactory eINSTANCE = tools.vitruv.framework.change.echange.compound.impl.CompoundFactoryImpl.init();

    /**
	 * Returns a new object of class '<em>Move EObject</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Move EObject</em>'.
	 * @generated
	 */
    <A extends EObject, B extends EObject, T extends EObject> MoveEObject<A, B, T> createMoveEObject();

    /**
	 * Returns a new object of class '<em>Explicit Unset EFeature</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Explicit Unset EFeature</em>'.
	 * @generated
	 */
    <A extends EObject, F extends EStructuralFeature, T extends Object, S extends FeatureEChange<A, F> & SubtractiveEChange<T>> ExplicitUnsetEFeature<A, F, T, S> createExplicitUnsetEFeature();

    /**
	 * Returns a new object of class '<em>Replace In EList</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Replace In EList</em>'.
	 * @generated
	 */
	<A extends EObject, F extends EStructuralFeature, T extends EObject, R extends RemoveFromListEChange<A, F, T> & FeatureEChange<A, F> & SubtractiveEChange<T>, I extends InsertInListEChange<A, F, T> & FeatureEChange<A, F> & AdditiveEChange<T>> ReplaceInEList<A, F, T, R, I> createReplaceInEList();

				/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
    CompoundPackage getCompoundPackage();

} //CompoundFactory
