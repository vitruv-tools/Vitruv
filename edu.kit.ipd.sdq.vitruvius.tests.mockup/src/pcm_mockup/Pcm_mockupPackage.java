/**
 */
package pcm_mockup;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see pcm_mockup.Pcm_mockupFactory
 * @model kind="package"
 * @generated
 */
public interface Pcm_mockupPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "pcm_mockup";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://edu.kit.ipd.sdq.vitruvius.examples.pcm_mockup";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "pcm_mockup";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    Pcm_mockupPackage eINSTANCE = pcm_mockup.impl.Pcm_mockupPackageImpl.init();

    /**
     * The meta object id for the '{@link pcm_mockup.impl.RepositoryImpl <em>Repository</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see pcm_mockup.impl.RepositoryImpl
     * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getRepository()
     * @generated
     */
    int REPOSITORY = 0;

    /**
     * The meta object id for the '{@link pcm_mockup.impl.InterfaceImpl <em>Interface</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see pcm_mockup.impl.InterfaceImpl
     * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getInterface()
     * @generated
     */
    int INTERFACE = 1;

    /**
     * The meta object id for the '{@link pcm_mockup.impl.IdentifierImpl <em>Identifier</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see pcm_mockup.impl.IdentifierImpl
     * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getIdentifier()
     * @generated
     */
    int IDENTIFIER = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IDENTIFIER__ID = 0;

    /**
     * The number of structural features of the '<em>Identifier</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IDENTIFIER_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Identifier</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IDENTIFIER_OPERATION_COUNT = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY__ID = IDENTIFIER__ID;

    /**
     * The feature id for the '<em><b>Interfaces</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY__INTERFACES = IDENTIFIER_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Repository</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_FEATURE_COUNT = IDENTIFIER_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Repository</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REPOSITORY_OPERATION_COUNT = IDENTIFIER_OPERATION_COUNT + 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE__ID = IDENTIFIER__ID;

    /**
     * The number of structural features of the '<em>Interface</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_FEATURE_COUNT = IDENTIFIER_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Interface</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERFACE_OPERATION_COUNT = IDENTIFIER_OPERATION_COUNT + 0;


    /**
     * Returns the meta object for class '{@link pcm_mockup.Repository <em>Repository</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Repository</em>'.
     * @see pcm_mockup.Repository
     * @generated
     */
    EClass getRepository();

    /**
     * Returns the meta object for the containment reference list '{@link pcm_mockup.Repository#getInterfaces <em>Interfaces</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Interfaces</em>'.
     * @see pcm_mockup.Repository#getInterfaces()
     * @see #getRepository()
     * @generated
     */
    EReference getRepository_Interfaces();

    /**
     * Returns the meta object for class '{@link pcm_mockup.Interface <em>Interface</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Interface</em>'.
     * @see pcm_mockup.Interface
     * @generated
     */
    EClass getInterface();

    /**
     * Returns the meta object for class '{@link pcm_mockup.Identifier <em>Identifier</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Identifier</em>'.
     * @see pcm_mockup.Identifier
     * @generated
     */
    EClass getIdentifier();

    /**
     * Returns the meta object for the attribute '{@link pcm_mockup.Identifier#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see pcm_mockup.Identifier#getId()
     * @see #getIdentifier()
     * @generated
     */
    EAttribute getIdentifier_Id();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    Pcm_mockupFactory getPcm_mockupFactory();

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
         * The meta object literal for the '{@link pcm_mockup.impl.RepositoryImpl <em>Repository</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see pcm_mockup.impl.RepositoryImpl
         * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getRepository()
         * @generated
         */
        EClass REPOSITORY = eINSTANCE.getRepository();

        /**
         * The meta object literal for the '<em><b>Interfaces</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference REPOSITORY__INTERFACES = eINSTANCE.getRepository_Interfaces();

        /**
         * The meta object literal for the '{@link pcm_mockup.impl.InterfaceImpl <em>Interface</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see pcm_mockup.impl.InterfaceImpl
         * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getInterface()
         * @generated
         */
        EClass INTERFACE = eINSTANCE.getInterface();

        /**
         * The meta object literal for the '{@link pcm_mockup.impl.IdentifierImpl <em>Identifier</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see pcm_mockup.impl.IdentifierImpl
         * @see pcm_mockup.impl.Pcm_mockupPackageImpl#getIdentifier()
         * @generated
         */
        EClass IDENTIFIER = eINSTANCE.getIdentifier();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute IDENTIFIER__ID = eINSTANCE.getIdentifier_Id();

    }

} //Pcm_mockupPackage
