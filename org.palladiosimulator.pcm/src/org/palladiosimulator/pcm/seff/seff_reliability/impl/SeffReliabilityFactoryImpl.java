/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_reliability.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryAction;
import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityFactory;
import org.palladiosimulator.pcm.seff.seff_reliability.SeffReliabilityPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class SeffReliabilityFactoryImpl extends EFactoryImpl implements SeffReliabilityFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static SeffReliabilityFactory init() {
        try {
            final SeffReliabilityFactory theSeffReliabilityFactory = (SeffReliabilityFactory) EPackage.Registry.INSTANCE
                    .getEFactory(SeffReliabilityPackage.eNS_URI);
            if (theSeffReliabilityFactory != null) {
                return theSeffReliabilityFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new SeffReliabilityFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public SeffReliabilityFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EObject create(final EClass eClass) {
        switch (eClass.getClassifierID()) {
        case SeffReliabilityPackage.RECOVERY_ACTION_BEHAVIOUR:
            return this.createRecoveryActionBehaviour();
        case SeffReliabilityPackage.RECOVERY_ACTION:
            return this.createRecoveryAction();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public RecoveryActionBehaviour createRecoveryActionBehaviour() {
        final RecoveryActionBehaviourImpl recoveryActionBehaviour = new RecoveryActionBehaviourImpl();
        return recoveryActionBehaviour;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public RecoveryAction createRecoveryAction() {
        final RecoveryActionImpl recoveryAction = new RecoveryActionImpl();
        return recoveryAction;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SeffReliabilityPackage getSeffReliabilityPackage() {
        return (SeffReliabilityPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @deprecated
     * @generated
     */
    @Deprecated
    public static SeffReliabilityPackage getPackage() {
        return SeffReliabilityPackage.eINSTANCE;
    }

} // SeffReliabilityFactoryImpl
