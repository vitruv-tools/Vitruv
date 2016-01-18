/**
 */
package attribute_to_structure_struct_1;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see attribute_to_structure_struct_1.Attribute_to_structure_struct_1Package
 * @generated
 */
public interface Attribute_to_structure_struct_1Factory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Attribute_to_structure_struct_1Factory eINSTANCE = attribute_to_structure_struct_1.impl.Attribute_to_structure_struct_1FactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Model B</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model B</em>'.
	 * @generated
	 */
	ModelB createModelB();

	/**
	 * Returns a new object of class '<em>Structured</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Structured</em>'.
	 * @generated
	 */
	Structured createStructured();

	/**
	 * Returns a new object of class '<em>Int Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int Container</em>'.
	 * @generated
	 */
	IntContainer createIntContainer();

	/**
	 * Returns a new object of class '<em>Str Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Str Container</em>'.
	 * @generated
	 */
	StrContainer createStrContainer();

	/**
	 * Returns a new object of class '<em>Float Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float Container</em>'.
	 * @generated
	 */
	FloatContainer createFloatContainer();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	Attribute_to_structure_struct_1Package getAttribute_to_structure_struct_1Package();

} //Attribute_to_structure_struct_1Factory
