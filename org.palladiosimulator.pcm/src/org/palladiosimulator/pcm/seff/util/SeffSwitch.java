/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.seff.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.AbstractBranchTransition;
import org.palladiosimulator.pcm.seff.AbstractInternalControlFlowAction;
import org.palladiosimulator.pcm.seff.AbstractLoopAction;
import org.palladiosimulator.pcm.seff.AcquireAction;
import org.palladiosimulator.pcm.seff.BranchAction;
import org.palladiosimulator.pcm.seff.CallAction;
import org.palladiosimulator.pcm.seff.CallReturnAction;
import org.palladiosimulator.pcm.seff.CollectionIteratorAction;
import org.palladiosimulator.pcm.seff.EmitEventAction;
import org.palladiosimulator.pcm.seff.ExternalCallAction;
import org.palladiosimulator.pcm.seff.ForkAction;
import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.GuardedBranchTransition;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.InternalCallAction;
import org.palladiosimulator.pcm.seff.LoopAction;
import org.palladiosimulator.pcm.seff.ProbabilisticBranchTransition;
import org.palladiosimulator.pcm.seff.ReleaseAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffPackage;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.SetVariableAction;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.StopAction;
import org.palladiosimulator.pcm.seff.SynchronisationPoint;
import org.palladiosimulator.pcm.seff.seff_reliability.FailureHandlingEntity;

import de.uka.ipd.sdq.identifier.Identifier;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the
 * call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for
 * each class of the model, starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the result of the switch.
 * <!-- end-user-doc -->
 * 
 * @see org.palladiosimulator.pcm.seff.SeffPackage
 * @generated
 */
public class SeffSwitch<T> {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright 2005-2015 by palladiosimulator.org";

    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static SeffPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public SeffSwitch() {
        if (modelPackage == null) {
            modelPackage = SeffPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result;
     * it yields that result. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(final EObject theEObject) {
        return this.doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result;
     * it yields that result. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(final EClass theEClass, final EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return this.doSwitch(theEClass.getClassifierID(), theEObject);
        } else {
            final List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return eSuperTypes.isEmpty() ? this.defaultCase(theEObject) : this.doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result;
     * it yields that result. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(final int classifierID, final EObject theEObject) {
        switch (classifierID) {
        case SeffPackage.STOP_ACTION: {
            final StopAction stopAction = (StopAction) theEObject;
            T result = this.caseStopAction(stopAction);
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(stopAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(stopAction);
            }
            if (result == null) {
                result = this.caseEntity(stopAction);
            }
            if (result == null) {
                result = this.caseIdentifier(stopAction);
            }
            if (result == null) {
                result = this.caseNamedElement(stopAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.ABSTRACT_INTERNAL_CONTROL_FLOW_ACTION: {
            final AbstractInternalControlFlowAction abstractInternalControlFlowAction = (AbstractInternalControlFlowAction) theEObject;
            T result = this.caseAbstractInternalControlFlowAction(abstractInternalControlFlowAction);
            if (result == null) {
                result = this.caseAbstractAction(abstractInternalControlFlowAction);
            }
            if (result == null) {
                result = this.caseEntity(abstractInternalControlFlowAction);
            }
            if (result == null) {
                result = this.caseIdentifier(abstractInternalControlFlowAction);
            }
            if (result == null) {
                result = this.caseNamedElement(abstractInternalControlFlowAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.ABSTRACT_ACTION: {
            final AbstractAction abstractAction = (AbstractAction) theEObject;
            T result = this.caseAbstractAction(abstractAction);
            if (result == null) {
                result = this.caseEntity(abstractAction);
            }
            if (result == null) {
                result = this.caseIdentifier(abstractAction);
            }
            if (result == null) {
                result = this.caseNamedElement(abstractAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.RESOURCE_DEMANDING_BEHAVIOUR: {
            final ResourceDemandingBehaviour resourceDemandingBehaviour = (ResourceDemandingBehaviour) theEObject;
            T result = this.caseResourceDemandingBehaviour(resourceDemandingBehaviour);
            if (result == null) {
                result = this.caseIdentifier(resourceDemandingBehaviour);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.ABSTRACT_LOOP_ACTION: {
            final AbstractLoopAction abstractLoopAction = (AbstractLoopAction) theEObject;
            T result = this.caseAbstractLoopAction(abstractLoopAction);
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(abstractLoopAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(abstractLoopAction);
            }
            if (result == null) {
                result = this.caseEntity(abstractLoopAction);
            }
            if (result == null) {
                result = this.caseIdentifier(abstractLoopAction);
            }
            if (result == null) {
                result = this.caseNamedElement(abstractLoopAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.ABSTRACT_BRANCH_TRANSITION: {
            final AbstractBranchTransition abstractBranchTransition = (AbstractBranchTransition) theEObject;
            T result = this.caseAbstractBranchTransition(abstractBranchTransition);
            if (result == null) {
                result = this.caseEntity(abstractBranchTransition);
            }
            if (result == null) {
                result = this.caseIdentifier(abstractBranchTransition);
            }
            if (result == null) {
                result = this.caseNamedElement(abstractBranchTransition);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.BRANCH_ACTION: {
            final BranchAction branchAction = (BranchAction) theEObject;
            T result = this.caseBranchAction(branchAction);
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(branchAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(branchAction);
            }
            if (result == null) {
                result = this.caseEntity(branchAction);
            }
            if (result == null) {
                result = this.caseIdentifier(branchAction);
            }
            if (result == null) {
                result = this.caseNamedElement(branchAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.CALL_ACTION: {
            final CallAction callAction = (CallAction) theEObject;
            T result = this.caseCallAction(callAction);
            if (result == null) {
                result = this.caseAbstractAction(callAction);
            }
            if (result == null) {
                result = this.caseEntity(callAction);
            }
            if (result == null) {
                result = this.caseIdentifier(callAction);
            }
            if (result == null) {
                result = this.caseNamedElement(callAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.START_ACTION: {
            final StartAction startAction = (StartAction) theEObject;
            T result = this.caseStartAction(startAction);
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(startAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(startAction);
            }
            if (result == null) {
                result = this.caseEntity(startAction);
            }
            if (result == null) {
                result = this.caseIdentifier(startAction);
            }
            if (result == null) {
                result = this.caseNamedElement(startAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.SERVICE_EFFECT_SPECIFICATION: {
            final ServiceEffectSpecification serviceEffectSpecification = (ServiceEffectSpecification) theEObject;
            T result = this.caseServiceEffectSpecification(serviceEffectSpecification);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.RESOURCE_DEMANDING_SEFF: {
            final ResourceDemandingSEFF resourceDemandingSEFF = (ResourceDemandingSEFF) theEObject;
            T result = this.caseResourceDemandingSEFF(resourceDemandingSEFF);
            if (result == null) {
                result = this.caseServiceEffectSpecification(resourceDemandingSEFF);
            }
            if (result == null) {
                result = this.caseResourceDemandingBehaviour(resourceDemandingSEFF);
            }
            if (result == null) {
                result = this.caseIdentifier(resourceDemandingSEFF);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR: {
            final ResourceDemandingInternalBehaviour resourceDemandingInternalBehaviour = (ResourceDemandingInternalBehaviour) theEObject;
            T result = this.caseResourceDemandingInternalBehaviour(resourceDemandingInternalBehaviour);
            if (result == null) {
                result = this.caseResourceDemandingBehaviour(resourceDemandingInternalBehaviour);
            }
            if (result == null) {
                result = this.caseIdentifier(resourceDemandingInternalBehaviour);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.RELEASE_ACTION: {
            final ReleaseAction releaseAction = (ReleaseAction) theEObject;
            T result = this.caseReleaseAction(releaseAction);
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(releaseAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(releaseAction);
            }
            if (result == null) {
                result = this.caseEntity(releaseAction);
            }
            if (result == null) {
                result = this.caseIdentifier(releaseAction);
            }
            if (result == null) {
                result = this.caseNamedElement(releaseAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.LOOP_ACTION: {
            final LoopAction loopAction = (LoopAction) theEObject;
            T result = this.caseLoopAction(loopAction);
            if (result == null) {
                result = this.caseAbstractLoopAction(loopAction);
            }
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(loopAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(loopAction);
            }
            if (result == null) {
                result = this.caseEntity(loopAction);
            }
            if (result == null) {
                result = this.caseIdentifier(loopAction);
            }
            if (result == null) {
                result = this.caseNamedElement(loopAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.FORK_ACTION: {
            final ForkAction forkAction = (ForkAction) theEObject;
            T result = this.caseForkAction(forkAction);
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(forkAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(forkAction);
            }
            if (result == null) {
                result = this.caseEntity(forkAction);
            }
            if (result == null) {
                result = this.caseIdentifier(forkAction);
            }
            if (result == null) {
                result = this.caseNamedElement(forkAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.FORKED_BEHAVIOUR: {
            final ForkedBehaviour forkedBehaviour = (ForkedBehaviour) theEObject;
            T result = this.caseForkedBehaviour(forkedBehaviour);
            if (result == null) {
                result = this.caseResourceDemandingBehaviour(forkedBehaviour);
            }
            if (result == null) {
                result = this.caseIdentifier(forkedBehaviour);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.SYNCHRONISATION_POINT: {
            final SynchronisationPoint synchronisationPoint = (SynchronisationPoint) theEObject;
            T result = this.caseSynchronisationPoint(synchronisationPoint);
            if (result == null) {
                result = this.caseIdentifier(synchronisationPoint);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.EXTERNAL_CALL_ACTION: {
            final ExternalCallAction externalCallAction = (ExternalCallAction) theEObject;
            T result = this.caseExternalCallAction(externalCallAction);
            if (result == null) {
                result = this.caseCallReturnAction(externalCallAction);
            }
            if (result == null) {
                result = this.caseFailureHandlingEntity(externalCallAction);
            }
            if (result == null) {
                result = this.caseEntity(externalCallAction);
            }
            if (result == null) {
                result = this.caseCallAction(externalCallAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(externalCallAction);
            }
            if (result == null) {
                result = this.caseIdentifier(externalCallAction);
            }
            if (result == null) {
                result = this.caseNamedElement(externalCallAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.CALL_RETURN_ACTION: {
            final CallReturnAction callReturnAction = (CallReturnAction) theEObject;
            T result = this.caseCallReturnAction(callReturnAction);
            if (result == null) {
                result = this.caseCallAction(callReturnAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(callReturnAction);
            }
            if (result == null) {
                result = this.caseEntity(callReturnAction);
            }
            if (result == null) {
                result = this.caseIdentifier(callReturnAction);
            }
            if (result == null) {
                result = this.caseNamedElement(callReturnAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.PROBABILISTIC_BRANCH_TRANSITION: {
            final ProbabilisticBranchTransition probabilisticBranchTransition = (ProbabilisticBranchTransition) theEObject;
            T result = this.caseProbabilisticBranchTransition(probabilisticBranchTransition);
            if (result == null) {
                result = this.caseAbstractBranchTransition(probabilisticBranchTransition);
            }
            if (result == null) {
                result = this.caseEntity(probabilisticBranchTransition);
            }
            if (result == null) {
                result = this.caseIdentifier(probabilisticBranchTransition);
            }
            if (result == null) {
                result = this.caseNamedElement(probabilisticBranchTransition);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.ACQUIRE_ACTION: {
            final AcquireAction acquireAction = (AcquireAction) theEObject;
            T result = this.caseAcquireAction(acquireAction);
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(acquireAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(acquireAction);
            }
            if (result == null) {
                result = this.caseEntity(acquireAction);
            }
            if (result == null) {
                result = this.caseIdentifier(acquireAction);
            }
            if (result == null) {
                result = this.caseNamedElement(acquireAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.COLLECTION_ITERATOR_ACTION: {
            final CollectionIteratorAction collectionIteratorAction = (CollectionIteratorAction) theEObject;
            T result = this.caseCollectionIteratorAction(collectionIteratorAction);
            if (result == null) {
                result = this.caseAbstractLoopAction(collectionIteratorAction);
            }
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(collectionIteratorAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(collectionIteratorAction);
            }
            if (result == null) {
                result = this.caseEntity(collectionIteratorAction);
            }
            if (result == null) {
                result = this.caseIdentifier(collectionIteratorAction);
            }
            if (result == null) {
                result = this.caseNamedElement(collectionIteratorAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.GUARDED_BRANCH_TRANSITION: {
            final GuardedBranchTransition guardedBranchTransition = (GuardedBranchTransition) theEObject;
            T result = this.caseGuardedBranchTransition(guardedBranchTransition);
            if (result == null) {
                result = this.caseAbstractBranchTransition(guardedBranchTransition);
            }
            if (result == null) {
                result = this.caseEntity(guardedBranchTransition);
            }
            if (result == null) {
                result = this.caseIdentifier(guardedBranchTransition);
            }
            if (result == null) {
                result = this.caseNamedElement(guardedBranchTransition);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.SET_VARIABLE_ACTION: {
            final SetVariableAction setVariableAction = (SetVariableAction) theEObject;
            T result = this.caseSetVariableAction(setVariableAction);
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(setVariableAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(setVariableAction);
            }
            if (result == null) {
                result = this.caseEntity(setVariableAction);
            }
            if (result == null) {
                result = this.caseIdentifier(setVariableAction);
            }
            if (result == null) {
                result = this.caseNamedElement(setVariableAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.INTERNAL_CALL_ACTION: {
            final InternalCallAction internalCallAction = (InternalCallAction) theEObject;
            T result = this.caseInternalCallAction(internalCallAction);
            if (result == null) {
                result = this.caseCallAction(internalCallAction);
            }
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(internalCallAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(internalCallAction);
            }
            if (result == null) {
                result = this.caseEntity(internalCallAction);
            }
            if (result == null) {
                result = this.caseIdentifier(internalCallAction);
            }
            if (result == null) {
                result = this.caseNamedElement(internalCallAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.EMIT_EVENT_ACTION: {
            final EmitEventAction emitEventAction = (EmitEventAction) theEObject;
            T result = this.caseEmitEventAction(emitEventAction);
            if (result == null) {
                result = this.caseCallAction(emitEventAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(emitEventAction);
            }
            if (result == null) {
                result = this.caseEntity(emitEventAction);
            }
            if (result == null) {
                result = this.caseIdentifier(emitEventAction);
            }
            if (result == null) {
                result = this.caseNamedElement(emitEventAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case SeffPackage.INTERNAL_ACTION: {
            final InternalAction internalAction = (InternalAction) theEObject;
            T result = this.caseInternalAction(internalAction);
            if (result == null) {
                result = this.caseAbstractInternalControlFlowAction(internalAction);
            }
            if (result == null) {
                result = this.caseAbstractAction(internalAction);
            }
            if (result == null) {
                result = this.caseEntity(internalAction);
            }
            if (result == null) {
                result = this.caseIdentifier(internalAction);
            }
            if (result == null) {
                result = this.caseNamedElement(internalAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        default:
            return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Stop Action</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Stop Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStopAction(final StopAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Abstract Internal Control Flow Action</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Abstract Internal Control Flow Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractInternalControlFlowAction(final AbstractInternalControlFlowAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Abstract Action</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Abstract Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractAction(final AbstractAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Resource Demanding Behaviour</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Resource Demanding Behaviour</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceDemandingBehaviour(final ResourceDemandingBehaviour object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Abstract Loop Action</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Abstract Loop Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractLoopAction(final AbstractLoopAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Abstract Branch Transition</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Abstract Branch Transition</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractBranchTransition(final AbstractBranchTransition object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Branch Action</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Branch Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBranchAction(final BranchAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Call Action</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Call Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCallAction(final CallAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Start Action</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Start Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStartAction(final StartAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Service Effect Specification</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Service Effect Specification</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseServiceEffectSpecification(final ServiceEffectSpecification object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Resource Demanding SEFF</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Resource Demanding SEFF</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceDemandingSEFF(final ResourceDemandingSEFF object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Resource Demanding Internal Behaviour</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Resource Demanding Internal Behaviour</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseResourceDemandingInternalBehaviour(final ResourceDemandingInternalBehaviour object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Release Action</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Release Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseReleaseAction(final ReleaseAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Loop Action</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Loop Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLoopAction(final LoopAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Fork Action</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Fork Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseForkAction(final ForkAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Forked Behaviour</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Forked Behaviour</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseForkedBehaviour(final ForkedBehaviour object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Synchronisation Point</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Synchronisation Point</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSynchronisationPoint(final SynchronisationPoint object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>External Call Action</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>External Call Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseExternalCallAction(final ExternalCallAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Call Return Action</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Call Return Action</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCallReturnAction(final CallReturnAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Probabilistic Branch Transition</em>'. <!-- begin-user-doc --> This implementation
     * returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Probabilistic Branch Transition</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseProbabilisticBranchTransition(final ProbabilisticBranchTransition object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Acquire Action</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Acquire Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAcquireAction(final AcquireAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Collection Iterator Action</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Collection Iterator Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCollectionIteratorAction(final CollectionIteratorAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Guarded Branch Transition</em>'. <!-- begin-user-doc --> This implementation returns
     * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Guarded Branch Transition</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGuardedBranchTransition(final GuardedBranchTransition object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Set Variable Action</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Set Variable Action</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSetVariableAction(final SetVariableAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Internal Call Action</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Internal Call Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInternalCallAction(final InternalCallAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Emit Event Action</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Emit Event Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEmitEventAction(final EmitEventAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Internal Action</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Internal Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInternalAction(final InternalAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Identifier</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Identifier</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIdentifier(final Identifier object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseNamedElement(final NamedElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Entity</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEntity(final Entity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Failure Handling Entity</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * 
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Failure Handling Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFailureHandlingEntity(final FailureHandlingEntity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(final EObject object) {
        return null;
    }

} // SeffSwitch
