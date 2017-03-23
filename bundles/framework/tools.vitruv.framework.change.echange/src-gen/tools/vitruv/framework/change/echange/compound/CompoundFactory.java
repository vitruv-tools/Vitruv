/**
 */
package tools.vitruv.framework.change.echange.compound;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

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
<<<<<<< HEAD
	 * Returns a new object of class '<em>Explicit Unset EFeature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Explicit Unset EFeature</em>'.
	 * @generated
	 */
	<A extends EObject, T extends Object> ExplicitUnsetEFeature<A, T> createExplicitUnsetEFeature();

	/**
	 * Returns a new object of class '<em>Replace In EList</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Replace In EList</em>'.
	 * @generated
	 */
	<A extends EObject, F extends EStructuralFeature, T extends Object, R extends RemoveFromListEChange<A, F, T> & FeatureEChange<A, F> & SubtractiveEChange<T>, I extends InsertInListEChange<A, F, T> & FeatureEChange<A, F> & AdditiveEChange<T>> ReplaceInEList<A, F, T, R, I> createReplaceInEList();

	/**
	 * Returns a new object of class '<em>Subtraction</em>'.
=======
	 * Returns a new object of class '<em>Explicit Unset EAttribute</em>'.
>>>>>>> remotes/origin/master
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Explicit Unset EAttribute</em>'.
	 * @generated
	 */
	<A extends EObject, T extends Object> ExplicitUnsetEAttribute<A, T> createExplicitUnsetEAttribute();

	/**
	 * Returns a new object of class '<em>Explicit Unset EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Explicit Unset EReference</em>'.
	 * @generated
	 */
	<A extends EObject> ExplicitUnsetEReference<A> createExplicitUnsetEReference();

	/**
	 * Returns a new object of class '<em>Create And Insert Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create And Insert Root</em>'.
	 * @generated
	 */
	<T extends EObject> CreateAndInsertRoot<T> createCreateAndInsertRoot();

	/**
	 * Returns a new object of class '<em>Remove And Delete Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remove And Delete Root</em>'.
	 * @generated
	 */
	<T extends EObject> RemoveAndDeleteRoot<T> createRemoveAndDeleteRoot();

	/**
	 * Returns a new object of class '<em>Create And Insert Non Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create And Insert Non Root</em>'.
	 * @generated
	 */
	<A extends EObject, T extends EObject> CreateAndInsertNonRoot<A, T> createCreateAndInsertNonRoot();

	/**
	 * Returns a new object of class '<em>Remove And Delete Non Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remove And Delete Non Root</em>'.
	 * @generated
	 */
	<A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A, T> createRemoveAndDeleteNonRoot();

	/**
	 * Returns a new object of class '<em>Create And Replace With Non Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create And Replace With Non Root</em>'.
	 * @generated
	 */
	<A extends EObject, T extends EObject> CreateAndReplaceWithNonRoot<A, T> createCreateAndReplaceWithNonRoot();

	/**
	 * Returns a new object of class '<em>Replace And Delete Non Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Replace And Delete Non Root</em>'.
	 * @generated
	 */
	<A extends EObject, T extends EObject> ReplaceAndDeleteNonRoot<A, T> createReplaceAndDeleteNonRoot();

	/**
	 * Returns a new object of class '<em>Create And Replace And Delete Non Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create And Replace And Delete Non Root</em>'.
	 * @generated
	 */
	<A extends EObject, T extends EObject> CreateAndReplaceAndDeleteNonRoot<A, T> createCreateAndReplaceAndDeleteNonRoot();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CompoundPackage getCompoundPackage();

} //CompoundFactory
