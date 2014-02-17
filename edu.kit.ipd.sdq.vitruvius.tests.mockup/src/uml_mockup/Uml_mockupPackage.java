/**
 */
package uml_mockup;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see uml_mockup.Uml_mockupFactory
 * @model kind="package"
 * @generated
 */
public interface Uml_mockupPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "uml_mockup";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius.examples.uml_mockup";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "uml_mockup";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    Uml_mockupPackage eINSTANCE = uml_mockup.impl.Uml_mockupPackageImpl.init();

    /**
     * The meta object id for the '{@link uml_mockup.impl.UPackageImpl <em>UPackage</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see uml_mockup.impl.UPackageImpl
     * @see uml_mockup.impl.Uml_mockupPackageImpl#getUPackage()
     * @generated
     */
    int UPACKAGE = 0;

    /**
     * The feature id for the '<em><b>Interfaces</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPACKAGE__INTERFACES = 0;

    /**
     * The number of structural features of the '<em>UPackage</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPACKAGE_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>UPackage</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UPACKAGE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link uml_mockup.impl.InterfaceImpl <em>Interface</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see uml_mockup.impl.InterfaceImpl
     * @see uml_mockup.impl.Uml_mockupPackageImpl#getInterface()
     * @generated
     */
    int INTERFACE = 1;

    /**
     * The number of structural features of the '<em>Interface</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Interface</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_OPERATION_COUNT = 0;


    /**
     * Returns the meta object for class '{@link uml_mockup.UPackage <em>UPackage</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>UPackage</em>'.
     * @see uml_mockup.UPackage
     * @generated
     */
    EClass getUPackage();

    /**
     * Returns the meta object for the containment reference list '{@link uml_mockup.UPackage#getInterfaces <em>Interfaces</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Interfaces</em>'.
     * @see uml_mockup.UPackage#getInterfaces()
     * @see #getUPackage()
     * @generated
     */
    EReference getUPackage_Interfaces();

    /**
     * Returns the meta object for class '{@link uml_mockup.Interface <em>Interface</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Interface</em>'.
     * @see uml_mockup.Interface
     * @generated
     */
    EClass getInterface();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    Uml_mockupFactory getUml_mockupFactory();

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
         * The meta object literal for the '{@link uml_mockup.impl.UPackageImpl <em>UPackage</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see uml_mockup.impl.UPackageImpl
         * @see uml_mockup.impl.Uml_mockupPackageImpl#getUPackage()
         * @generated
         */
        EClass UPACKAGE = eINSTANCE.getUPackage();

        /**
         * The meta object literal for the '<em><b>Interfaces</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference UPACKAGE__INTERFACES = eINSTANCE.getUPackage_Interfaces();

        /**
         * The meta object literal for the '{@link uml_mockup.impl.InterfaceImpl <em>Interface</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see uml_mockup.impl.InterfaceImpl
         * @see uml_mockup.impl.Uml_mockupPackageImpl#getInterface()
         * @generated
         */
        EClass INTERFACE = eINSTANCE.getInterface();

    }

} //Uml_mockupPackage
