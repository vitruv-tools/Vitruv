/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.seff_performance.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.seff.seff_performance.InfrastructureCall;
import org.palladiosimulator.pcm.seff.seff_performance.ParametricResourceDemand;
import org.palladiosimulator.pcm.seff.seff_performance.ResourceCall;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformanceFactory;
import org.palladiosimulator.pcm.seff.seff_performance.SeffPerformancePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class SeffPerformanceFactoryImpl extends EFactoryImpl implements SeffPerformanceFactory {

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
    public static SeffPerformanceFactory init() {
        try {
            final SeffPerformanceFactory theSeffPerformanceFactory = (SeffPerformanceFactory) EPackage.Registry.INSTANCE
                    .getEFactory(SeffPerformancePackage.eNS_URI);
            if (theSeffPerformanceFactory != null) {
                return theSeffPerformanceFactory;
            }
        } catch (final Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new SeffPerformanceFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public SeffPerformanceFactoryImpl() {
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
        case SeffPerformancePackage.INFRASTRUCTURE_CALL:
            return this.createInfrastructureCall();
        case SeffPerformancePackage.RESOURCE_CALL:
            return this.createResourceCall();
        case SeffPerformancePackage.PARAMETRIC_RESOURCE_DEMAND:
            return this.createParametricResourceDemand();
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
    public InfrastructureCall createInfrastructureCall() {
        final InfrastructureCallImpl infrastructureCall = new InfrastructureCallImpl();
        return infrastructureCall;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceCall createResourceCall() {
        final ResourceCallImpl resourceCall = new ResourceCallImpl();
        return resourceCall;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ParametricResourceDemand createParametricResourceDemand() {
        final ParametricResourceDemandImpl parametricResourceDemand = new ParametricResourceDemandImpl();
        return parametricResourceDemand;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public SeffPerformancePackage getSeffPerformancePackage() {
        return (SeffPerformancePackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @deprecated
     * @generated
     */
    @Deprecated
    public static SeffPerformancePackage getPackage() {
        return SeffPerformancePackage.eINSTANCE;
    }

} // SeffPerformanceFactoryImpl
