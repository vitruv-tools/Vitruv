/**
 */
package tools.vitruvius.domains.java.echange.feature.attribute;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see tools.vitruvius.domains.java.echange.feature.attribute.AttributePackage
 * @generated
 */
public interface AttributeFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AttributeFactory eINSTANCE = tools.vitruvius.domains.java.echange.feature.attribute.impl.AttributeFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Java Insert EAttribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Insert EAttribute Value</em>'.
	 * @generated
	 */
	<A extends EObject, T extends Object> JavaInsertEAttributeValue<A, T> createJavaInsertEAttributeValue();

	/**
	 * Returns a new object of class '<em>Java Remove EAttribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Remove EAttribute Value</em>'.
	 * @generated
	 */
	<A extends EObject, T extends Object> JavaRemoveEAttributeValue<A, T> createJavaRemoveEAttributeValue();

	/**
	 * Returns a new object of class '<em>Java Replace Single Valued EAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Replace Single Valued EAttribute</em>'.
	 * @generated
	 */
	<A extends EObject, T extends Object> JavaReplaceSingleValuedEAttribute<A, T> createJavaReplaceSingleValuedEAttribute();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AttributePackage getAttributePackage();

} //AttributeFactory
