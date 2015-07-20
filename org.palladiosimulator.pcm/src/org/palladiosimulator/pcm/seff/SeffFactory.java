/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each
 * non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.seff.SeffPackage
 * @generated
 */
public interface SeffFactory extends EFactory {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    SeffFactory eINSTANCE = org.palladiosimulator.pcm.seff.impl.SeffFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Stop Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Stop Action</em>'.
     * @generated
     */
    StopAction createStopAction();

    /**
     * Returns a new object of class '<em>Resource Demanding Behaviour</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Resource Demanding Behaviour</em>'.
     * @generated
     */
    ResourceDemandingBehaviour createResourceDemandingBehaviour();

    /**
     * Returns a new object of class '<em>Branch Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Branch Action</em>'.
     * @generated
     */
    BranchAction createBranchAction();

    /**
     * Returns a new object of class '<em>Start Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Start Action</em>'.
     * @generated
     */
    StartAction createStartAction();

    /**
     * Returns a new object of class '<em>Resource Demanding SEFF</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Resource Demanding SEFF</em>'.
     * @generated
     */
    ResourceDemandingSEFF createResourceDemandingSEFF();

    /**
     * Returns a new object of class '<em>Resource Demanding Internal Behaviour</em>'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Resource Demanding Internal Behaviour</em>'.
     * @generated
     */
    ResourceDemandingInternalBehaviour createResourceDemandingInternalBehaviour();

    /**
     * Returns a new object of class '<em>Release Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Release Action</em>'.
     * @generated
     */
    ReleaseAction createReleaseAction();

    /**
     * Returns a new object of class '<em>Loop Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Loop Action</em>'.
     * @generated
     */
    LoopAction createLoopAction();

    /**
     * Returns a new object of class '<em>Fork Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Fork Action</em>'.
     * @generated
     */
    ForkAction createForkAction();

    /**
     * Returns a new object of class '<em>Forked Behaviour</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Forked Behaviour</em>'.
     * @generated
     */
    ForkedBehaviour createForkedBehaviour();

    /**
     * Returns a new object of class '<em>Synchronisation Point</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Synchronisation Point</em>'.
     * @generated
     */
    SynchronisationPoint createSynchronisationPoint();

    /**
     * Returns a new object of class '<em>External Call Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>External Call Action</em>'.
     * @generated
     */
    ExternalCallAction createExternalCallAction();

    /**
     * Returns a new object of class '<em>Call Return Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Call Return Action</em>'.
     * @generated
     */
    CallReturnAction createCallReturnAction();

    /**
     * Returns a new object of class '<em>Probabilistic Branch Transition</em>'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Probabilistic Branch Transition</em>'.
     * @generated
     */
    ProbabilisticBranchTransition createProbabilisticBranchTransition();

    /**
     * Returns a new object of class '<em>Acquire Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Acquire Action</em>'.
     * @generated
     */
    AcquireAction createAcquireAction();

    /**
     * Returns a new object of class '<em>Collection Iterator Action</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Collection Iterator Action</em>'.
     * @generated
     */
    CollectionIteratorAction createCollectionIteratorAction();

    /**
     * Returns a new object of class '<em>Guarded Branch Transition</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Guarded Branch Transition</em>'.
     * @generated
     */
    GuardedBranchTransition createGuardedBranchTransition();

    /**
     * Returns a new object of class '<em>Set Variable Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Set Variable Action</em>'.
     * @generated
     */
    SetVariableAction createSetVariableAction();

    /**
     * Returns a new object of class '<em>Internal Call Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Internal Call Action</em>'.
     * @generated
     */
    InternalCallAction createInternalCallAction();

    /**
     * Returns a new object of class '<em>Emit Event Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Emit Event Action</em>'.
     * @generated
     */
    EmitEventAction createEmitEventAction();

    /**
     * Returns a new object of class '<em>Internal Action</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return a new object of class '<em>Internal Action</em>'.
     * @generated
     */
    InternalAction createInternalAction();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the package supported by this factory.
     * @generated
     */
    SeffPackage getSeffPackage();

} // SeffFactory
