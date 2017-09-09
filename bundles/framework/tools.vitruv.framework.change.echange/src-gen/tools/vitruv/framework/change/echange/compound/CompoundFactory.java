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
	 * Returns a new object of class '<em>Explicit Unset EAttribute</em>'.
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
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CompoundPackage getCompoundPackage();

} //CompoundFactory
