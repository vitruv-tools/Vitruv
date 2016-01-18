/**
 */
package attribute_to_structure_attr;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see attribute_to_structure_attr.Attribute_to_structure_attrPackage
 * @generated
 */
public interface Attribute_to_structure_attrFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Attribute_to_structure_attrFactory eINSTANCE = attribute_to_structure_attr.impl.Attribute_to_structure_attrFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Model A</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model A</em>'.
	 * @generated
	 */
	ModelA createModelA();

	/**
	 * Returns a new object of class '<em>Attributed</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attributed</em>'.
	 * @generated
	 */
	Attributed createAttributed();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	Attribute_to_structure_attrPackage getAttribute_to_structure_attrPackage();

} //Attribute_to_structure_attrFactory
