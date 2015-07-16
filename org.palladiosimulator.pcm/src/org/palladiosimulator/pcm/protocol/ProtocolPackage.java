/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.protocol;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta
 * objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc --> <!-- begin-model-doc -->
 * <p>
 * The PCM is prepared to support interface protocols. This package contains a protocol stub.
 * Multiple protocols following different formalisms are supported by the PCM and distinguished by a
 * protocol ID.
 * </p>
 * <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.protocol.ProtocolFactory
 * @model kind="package"
 * @generated
 */
public interface ProtocolPackage extends EPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "protocol";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/Protocol/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "protocol";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ProtocolPackage eINSTANCE = org.palladiosimulator.pcm.protocol.impl.ProtocolPackageImpl.init();

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.protocol.impl.ProtocolImpl
     * <em>Protocol</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.protocol.impl.ProtocolImpl
     * @see org.palladiosimulator.pcm.protocol.impl.ProtocolPackageImpl#getProtocol()
     * @generated
     */
    int PROTOCOL = 0;

    /**
     * The feature id for the '<em><b>Protocol Type ID</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROTOCOL__PROTOCOL_TYPE_ID = 0;

    /**
     * The number of structural features of the '<em>Protocol</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int PROTOCOL_FEATURE_COUNT = 1;

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.protocol.Protocol
     * <em>Protocol</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Protocol</em>'.
     * @see org.palladiosimulator.pcm.protocol.Protocol
     * @generated
     */
    EClass getProtocol();

    /**
     * Returns the meta object for the attribute '
     * {@link org.palladiosimulator.pcm.protocol.Protocol#getProtocolTypeID
     * <em>Protocol Type ID</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Protocol Type ID</em>'.
     * @see org.palladiosimulator.pcm.protocol.Protocol#getProtocolTypeID()
     * @see #getProtocol()
     * @generated
     */
    EAttribute getProtocol_ProtocolTypeID();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ProtocolFactory getProtocolFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {

        /**
         * The meta object literal for the '
         * {@link org.palladiosimulator.pcm.protocol.impl.ProtocolImpl <em>Protocol</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.protocol.impl.ProtocolImpl
         * @see org.palladiosimulator.pcm.protocol.impl.ProtocolPackageImpl#getProtocol()
         * @generated
         */
        EClass PROTOCOL = eINSTANCE.getProtocol();

        /**
         * The meta object literal for the '<em><b>Protocol Type ID</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute PROTOCOL__PROTOCOL_TYPE_ID = eINSTANCE.getProtocol_ProtocolTypeID();

    }

} // ProtocolPackage
