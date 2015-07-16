/**
 * Copyright 2005-2009 by SDQ, IPD, University of Karlsruhe, Germany
 */
package org.palladiosimulator.pcm.usagemodel.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;
import org.palladiosimulator.pcm.usagemodel.Delay;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.ScenarioBehaviour;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelPackage;
import org.palladiosimulator.pcm.usagemodel.UserData;
import org.palladiosimulator.pcm.usagemodel.Workload;

import de.uka.ipd.sdq.identifier.Identifier;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the
 * call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for
 * each class of the model, starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.palladiosimulator.pcm.usagemodel.UsagemodelPackage
 * @generated
 */
public class UsagemodelSwitch<T> {

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
    protected static UsagemodelPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UsagemodelSwitch() {
        if (modelPackage == null) {
            modelPackage = UsagemodelPackage.eINSTANCE;
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
        case UsagemodelPackage.WORKLOAD: {
            final Workload workload = (Workload) theEObject;
            T result = this.caseWorkload(workload);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.USAGE_SCENARIO: {
            final UsageScenario usageScenario = (UsageScenario) theEObject;
            T result = this.caseUsageScenario(usageScenario);
            if (result == null) {
                result = this.caseEntity(usageScenario);
            }
            if (result == null) {
                result = this.caseIdentifier(usageScenario);
            }
            if (result == null) {
                result = this.caseNamedElement(usageScenario);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.USER_DATA: {
            final UserData userData = (UserData) theEObject;
            T result = this.caseUserData(userData);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.USAGE_MODEL: {
            final UsageModel usageModel = (UsageModel) theEObject;
            T result = this.caseUsageModel(usageModel);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.ENTRY_LEVEL_SYSTEM_CALL: {
            final EntryLevelSystemCall entryLevelSystemCall = (EntryLevelSystemCall) theEObject;
            T result = this.caseEntryLevelSystemCall(entryLevelSystemCall);
            if (result == null) {
                result = this.caseAbstractUserAction(entryLevelSystemCall);
            }
            if (result == null) {
                result = this.caseEntity(entryLevelSystemCall);
            }
            if (result == null) {
                result = this.caseIdentifier(entryLevelSystemCall);
            }
            if (result == null) {
                result = this.caseNamedElement(entryLevelSystemCall);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.ABSTRACT_USER_ACTION: {
            final AbstractUserAction abstractUserAction = (AbstractUserAction) theEObject;
            T result = this.caseAbstractUserAction(abstractUserAction);
            if (result == null) {
                result = this.caseEntity(abstractUserAction);
            }
            if (result == null) {
                result = this.caseIdentifier(abstractUserAction);
            }
            if (result == null) {
                result = this.caseNamedElement(abstractUserAction);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.SCENARIO_BEHAVIOUR: {
            final ScenarioBehaviour scenarioBehaviour = (ScenarioBehaviour) theEObject;
            T result = this.caseScenarioBehaviour(scenarioBehaviour);
            if (result == null) {
                result = this.caseEntity(scenarioBehaviour);
            }
            if (result == null) {
                result = this.caseIdentifier(scenarioBehaviour);
            }
            if (result == null) {
                result = this.caseNamedElement(scenarioBehaviour);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.BRANCH_TRANSITION: {
            final BranchTransition branchTransition = (BranchTransition) theEObject;
            T result = this.caseBranchTransition(branchTransition);
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.BRANCH: {
            final Branch branch = (Branch) theEObject;
            T result = this.caseBranch(branch);
            if (result == null) {
                result = this.caseAbstractUserAction(branch);
            }
            if (result == null) {
                result = this.caseEntity(branch);
            }
            if (result == null) {
                result = this.caseIdentifier(branch);
            }
            if (result == null) {
                result = this.caseNamedElement(branch);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.LOOP: {
            final Loop loop = (Loop) theEObject;
            T result = this.caseLoop(loop);
            if (result == null) {
                result = this.caseAbstractUserAction(loop);
            }
            if (result == null) {
                result = this.caseEntity(loop);
            }
            if (result == null) {
                result = this.caseIdentifier(loop);
            }
            if (result == null) {
                result = this.caseNamedElement(loop);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.STOP: {
            final Stop stop = (Stop) theEObject;
            T result = this.caseStop(stop);
            if (result == null) {
                result = this.caseAbstractUserAction(stop);
            }
            if (result == null) {
                result = this.caseEntity(stop);
            }
            if (result == null) {
                result = this.caseIdentifier(stop);
            }
            if (result == null) {
                result = this.caseNamedElement(stop);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.START: {
            final Start start = (Start) theEObject;
            T result = this.caseStart(start);
            if (result == null) {
                result = this.caseAbstractUserAction(start);
            }
            if (result == null) {
                result = this.caseEntity(start);
            }
            if (result == null) {
                result = this.caseIdentifier(start);
            }
            if (result == null) {
                result = this.caseNamedElement(start);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.OPEN_WORKLOAD: {
            final OpenWorkload openWorkload = (OpenWorkload) theEObject;
            T result = this.caseOpenWorkload(openWorkload);
            if (result == null) {
                result = this.caseWorkload(openWorkload);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.DELAY: {
            final Delay delay = (Delay) theEObject;
            T result = this.caseDelay(delay);
            if (result == null) {
                result = this.caseAbstractUserAction(delay);
            }
            if (result == null) {
                result = this.caseEntity(delay);
            }
            if (result == null) {
                result = this.caseIdentifier(delay);
            }
            if (result == null) {
                result = this.caseNamedElement(delay);
            }
            if (result == null) {
                result = this.defaultCase(theEObject);
            }
            return result;
        }
        case UsagemodelPackage.CLOSED_WORKLOAD: {
            final ClosedWorkload closedWorkload = (ClosedWorkload) theEObject;
            T result = this.caseClosedWorkload(closedWorkload);
            if (result == null) {
                result = this.caseWorkload(closedWorkload);
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
     * Returns the result of interpreting the object as an instance of '<em>Workload</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Workload</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWorkload(final Workload object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Usage Scenario</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Usage Scenario</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUsageScenario(final UsageScenario object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>User Data</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>User Data</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUserData(final UserData object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Usage Model</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Usage Model</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUsageModel(final UsageModel object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Entry Level System Call</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Entry Level System Call</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseEntryLevelSystemCall(final EntryLevelSystemCall object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '
     * <em>Abstract User Action</em>'. <!-- begin-user-doc --> This implementation returns null;
     * returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '
     *         <em>Abstract User Action</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractUserAction(final AbstractUserAction object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Scenario Behaviour</em>
     * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Scenario Behaviour</em>
     *         '.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseScenarioBehaviour(final ScenarioBehaviour object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Branch Transition</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Branch Transition</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBranchTransition(final BranchTransition object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Branch</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Branch</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBranch(final Branch object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Loop</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Loop</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLoop(final Loop object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Stop</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Stop</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStop(final Stop object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Start</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Start</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseStart(final Start object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Open Workload</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Open Workload</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOpenWorkload(final OpenWorkload object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delay</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delay</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDelay(final Delay object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Closed Workload</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Closed Workload</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseClosedWorkload(final ClosedWorkload object) {
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

} // UsagemodelSwitch
