/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm;

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
 * <!-- end-user-doc --> <!-- begin-model-doc --> This package is the root package of all packages
 * of the Palladio Component Model (PCM). <!-- end-model-doc -->
 *
 * @see org.palladiosimulator.pcm.PcmFactory
 * @model kind="package" annotation=
 *        "http://www.eclipse.org/OCL/Import ecore='http://www.eclipse.org/emf/2002/Ecore' identifier='../../../plugin/de.uka.ipd.sdq.identifier/model/identifier.ecore#/' stoex='../../../plugin/de.uka.ipd.sdq.stoex/model/stoex.ecore#/' units='../../../plugin/de.uka.ipd.sdq.units/model/Units.ecore#/'"
 * @generated
 */
public interface PcmPackage extends EPackage {

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
    String eNAME = "pcm";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://palladiosimulator.org/PalladioComponentModel/5.1";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "pcm";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    PcmPackage eINSTANCE = org.palladiosimulator.pcm.impl.PcmPackageImpl.init();

    /**
     * The meta object id for the '{@link org.palladiosimulator.pcm.impl.DummyClassImpl
     * <em>Dummy Class</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.palladiosimulator.pcm.impl.DummyClassImpl
     * @see org.palladiosimulator.pcm.impl.PcmPackageImpl#getDummyClass()
     * @generated
     */
    int DUMMY_CLASS = 0;

    /**
     * The number of structural features of the '<em>Dummy Class</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DUMMY_CLASS_FEATURE_COUNT = 0;

    /**
     * Returns the meta object for class '{@link org.palladiosimulator.pcm.DummyClass
     * <em>Dummy Class</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Dummy Class</em>'.
     * @see org.palladiosimulator.pcm.DummyClass
     * @generated
     */
    EClass getDummyClass();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    PcmFactory getPcmFactory();

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
         * The meta object literal for the '{@link org.palladiosimulator.pcm.impl.DummyClassImpl
         * <em>Dummy Class</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.palladiosimulator.pcm.impl.DummyClassImpl
         * @see org.palladiosimulator.pcm.impl.PcmPackageImpl#getDummyClass()
         * @generated
         */
        EClass DUMMY_CLASS = eINSTANCE.getDummyClass();

    }

} // PcmPackage
