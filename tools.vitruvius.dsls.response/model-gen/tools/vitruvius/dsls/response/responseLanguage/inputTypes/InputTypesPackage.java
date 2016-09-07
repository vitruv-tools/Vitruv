/**
 */
package tools.vitruvius.dsls.response.responseLanguage.inputTypes;

import java.lang.String;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.InputTypesFactory
 * @model kind="package"
 * @generated
 */
public interface InputTypesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "inputTypes";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.kit.edu/ipd/sdq/vitruvius/dsls/response/ResponseLanguage/InputTypes";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "inputTypes";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InputTypesPackage eINSTANCE = tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.InputTypesPackageImpl.init();

	/**
	 * The meta object id for the '{@link tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.StringImpl <em>String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.StringImpl
	 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.InputTypesPackageImpl#getString()
	 * @generated
	 */
	int STRING = 0;

	/**
	 * The number of structural features of the '<em>String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.IntImpl <em>Int</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.IntImpl
	 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.InputTypesPackageImpl#getInt()
	 * @generated
	 */
	int INT = 1;

	/**
	 * The number of structural features of the '<em>Int</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Int</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link tools.vitruvius.dsls.response.responseLanguage.inputTypes.String <em>String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String</em>'.
	 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.String
	 * @generated
	 */
	EClass getString();

	/**
	 * Returns the meta object for class '{@link tools.vitruvius.dsls.response.responseLanguage.inputTypes.Int <em>Int</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Int</em>'.
	 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.Int
	 * @generated
	 */
	EClass getInt();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InputTypesFactory getInputTypesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.StringImpl <em>String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.StringImpl
		 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.InputTypesPackageImpl#getString()
		 * @generated
		 */
		EClass STRING = eINSTANCE.getString();

		/**
		 * The meta object literal for the '{@link tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.IntImpl <em>Int</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.IntImpl
		 * @see tools.vitruvius.dsls.response.responseLanguage.inputTypes.impl.InputTypesPackageImpl#getInt()
		 * @generated
		 */
		EClass INT = eINSTANCE.getInt();

	}

} //InputTypesPackage
