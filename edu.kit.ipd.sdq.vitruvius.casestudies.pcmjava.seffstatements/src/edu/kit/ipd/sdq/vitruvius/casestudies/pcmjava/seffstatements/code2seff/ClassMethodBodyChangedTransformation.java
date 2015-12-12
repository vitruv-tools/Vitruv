package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff;

import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.StartAction;
import org.palladiosimulator.pcm.seff.StopAction;
import org.somox.gast2seff.visitors.FunctionCallClassificationVisitor;
import org.somox.gast2seff.visitors.IFunctionClassificationStrategy;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding;
import org.somox.gast2seff.visitors.VisitorUtils;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CustomTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;

/**
 * Class that keeps changes within a class method body consistent with the architecture. Has
 * dependencies to SoMoX as well as to Vitruvius. This is the reason that the class is in its own
 * plugin. The class is written in Java and not in Xtend (we do not need the cool features of Xtend
 * within the class).
 *
 * @author langhamm
 *
 */
public class ClassMethodBodyChangedTransformation implements CustomTransformation {

    private final static Logger logger = Logger.getLogger(ClassMethodBodyChangedTransformation.class.getSimpleName());

    private final ClassMethod oldMethod;
    private final ClassMethod newMethod;
    private final BasicComponentFinding basicComponentFinder;
    private final IFunctionClassificationStrategy iFunctionClassificationStrategy;

    private final InterfaceOfExternalCallFinding interfaceOfExternalCallFinder;

    private final ResourceDemandingBehaviourForClassMethodFinding resourceDemandingBehaviourForClassMethodFinding;

    public ClassMethodBodyChangedTransformation(final ClassMethod oldMethod, final ClassMethod newMethod,
            final BasicComponentFinding basicComponentFinder,
            final IFunctionClassificationStrategy iFunctionClassificationStrategy,
            final InterfaceOfExternalCallFinding interfaceOfExternalCallFinder,
            final ResourceDemandingBehaviourForClassMethodFinding resourceDemandingBehaviourForClassMethodFinding) {
        this.oldMethod = oldMethod;
        this.newMethod = newMethod;
        this.basicComponentFinder = basicComponentFinder;
        this.iFunctionClassificationStrategy = iFunctionClassificationStrategy;
        this.interfaceOfExternalCallFinder = interfaceOfExternalCallFinder;
        this.resourceDemandingBehaviourForClassMethodFinding = resourceDemandingBehaviourForClassMethodFinding;
    }

    /**
     * This method is called after a java method body has been changed. In order to keep the
     * SEFF/ResourceDemandingInternalBehaviour consistent with the method we 1) remove all
     * AbstractActions corresponding to the Method from the SEFF/ResourceDemandingInternalBehaviour
     * (which are currently all AbstractActions in the SEFF/ResourceDemandingInternalBehaviour) and
     * from the correspondenceInstance 2) run the SoMoX SEFF extractor for this method, 3) reconnect
     * the newly extracted SEFF elements with the old elements 4) create new AbstractAction 2 Method
     * correspondences for the new method (and its inner methods)
     *
     */
    @Override
    public TransformationResult execute(final Blackboard blackboard, final UserInteracting userInteracting,
            final SynchronisationAbortedListener abortListener) {
        if (!this.isArchitectureRelevantChange(blackboard.getCorrespondenceInstance())) {
            logger.debug("Change with oldMethod " + this.oldMethod + " and newMethod: " + this.newMethod
                    + " is not an architecture relevant change");
            return new TransformationResult();
        }
        // 1)
        this.removeCorrespondingAbstractActions(blackboard.getCorrespondenceInstance());

        // 2)
        final ResourceDemandingBehaviour resourceDemandingBehaviour = this
                .findRdBehaviorToInsertElements(blackboard.getCorrespondenceInstance());
        final BasicComponent basicComponent = this.basicComponentFinder.findBasicComponentForMethod(this.newMethod,
                blackboard.getCorrespondenceInstance());
        this.executeSoMoXForMethod(basicComponent, resourceDemandingBehaviour);

        // 3)
        this.connectCreatedResourceDemandingBehaviour(resourceDemandingBehaviour,
                blackboard.getCorrespondenceInstance());

        // 4)
        this.createNewCorrespondences(blackboard.getCorrespondenceInstance(), resourceDemandingBehaviour,
                basicComponent);

        return new TransformationResult();
    }

    /**
     * checks whether the change is considered architecture relevant. This is the case if either the
     * new or the old method does have a corresponding ResourceDemandingBehaviour
     *
     * @param ci
     * @return
     */
    private boolean isArchitectureRelevantChange(final CorrespondenceInstance ci) {
        return this.isMethodArchitectureRelevant(this.oldMethod, ci)
                || this.isMethodArchitectureRelevant(this.newMethod, ci);
    }

    private boolean isMethodArchitectureRelevant(final ClassMethod method, final CorrespondenceInstance ci) {
        if (null != method) {
            final Set<ResourceDemandingBehaviour> correspondingEObjectsByType = CorrespondenceInstanceUtil
                    .getCorrespondingEObjectsByType(ci, method, ResourceDemandingBehaviour.class);
            if (null != correspondingEObjectsByType && !correspondingEObjectsByType.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void executeSoMoXForMethod(final BasicComponent basicComponent,
            final ResourceDemandingBehaviour targetResourceDemandingBehaviour) {
        final FunctionCallClassificationVisitor functionCallClassificationVisitor = new FunctionCallClassificationVisitor(
                this.iFunctionClassificationStrategy);
        VisitorUtils.visitJaMoPPMethod(targetResourceDemandingBehaviour, basicComponent, this.newMethod, null,
                functionCallClassificationVisitor, this.interfaceOfExternalCallFinder,
                this.resourceDemandingBehaviourForClassMethodFinding);
    }

    private void createNewCorrespondences(final CorrespondenceInstance ci,
            final ResourceDemandingBehaviour newResourceDemandingBehaviourElements,
            final BasicComponent basicComponent) {
        for (final AbstractAction abstractAction : newResourceDemandingBehaviourElements.getSteps_Behaviour()) {
            CorrespondenceInstanceUtil.createAndAddCorrespondence(ci, abstractAction, this.newMethod);
        }
    }

    private void connectCreatedResourceDemandingBehaviour(final ResourceDemandingBehaviour rdBehavior,
            final CorrespondenceInstance ci) {
        final EList<AbstractAction> steps = rdBehavior.getSteps_Behaviour();
        final boolean addStartAction = 0 == steps.size() || !(steps.get(0) instanceof StartAction);
        final boolean addStopAction = 0 == steps.size() || !(steps.get(steps.size() - 1) instanceof StopAction);

        if (addStartAction) {
            rdBehavior.getSteps_Behaviour().add(0, SeffFactory.eINSTANCE.createStartAction());
        }
        if (addStopAction) {
            final AbstractAction stopAction = SeffFactory.eINSTANCE.createStopAction();
            rdBehavior.getSteps_Behaviour().add(stopAction);
        }
        VisitorUtils.connectActions(rdBehavior);
    }

    private void removeCorrespondingAbstractActions(final CorrespondenceInstance ci) {
        final Set<AbstractAction> correspondingAbstractActions = CorrespondenceInstanceUtil
                .getCorrespondingEObjectsByType(ci, this.oldMethod, AbstractAction.class);
        if (null == correspondingAbstractActions) {
            return;
        }
        final ResourceDemandingBehaviour resourceDemandingBehaviour = this
                .getAndValidateResourceDemandingBehavior(correspondingAbstractActions);
        if (null == resourceDemandingBehaviour) {
            return;
        }
        for (final AbstractAction correspondingAbstractAction : correspondingAbstractActions) {
            final TUID tuidToRemove = ci.calculateTUIDFromEObject(correspondingAbstractAction);
            ci.removeCorrespondencesThatInvolveAtLeastAndDependendForTUIDs(CollectionBridge.toSet(tuidToRemove));
            EcoreUtil.remove(correspondingAbstractAction);
        }

        for (final AbstractAction abstractAction : resourceDemandingBehaviour.getSteps_Behaviour()) {
            if (!(abstractAction instanceof StartAction || abstractAction instanceof StopAction)) {
                logger.warn(
                        "The resource demanding behavior should be empty, but it contains at least following AbstractAction "
                                + abstractAction);
            }
        }
    }

    private ResourceDemandingBehaviour getAndValidateResourceDemandingBehavior(
            final Set<AbstractAction> correspondingAbstractActions) {
        ResourceDemandingBehaviour resourceDemandingBehaviour = null;
        for (final AbstractAction abstractAction : correspondingAbstractActions) {
            if (null == abstractAction.getResourceDemandingBehaviour_AbstractAction()) {
                logger.warn("AbstractAction " + abstractAction
                        + " does not have a parent ResourceDemandingBehaviour - this should not happen.");
                continue;
            }
            if (null == resourceDemandingBehaviour) {
                // set resourceDemandingBehaviour in first cycle
                resourceDemandingBehaviour = abstractAction.getResourceDemandingBehaviour_AbstractAction();
                continue;
            }
            if (resourceDemandingBehaviour != abstractAction.getResourceDemandingBehaviour_AbstractAction()) {
                logger.warn("resourceDemandingBehaviour " + resourceDemandingBehaviour
                        + " is different that current resourceDemandingBehaviour: "
                        + abstractAction.getResourceDemandingBehaviour_AbstractAction());
            }

        }
        return resourceDemandingBehaviour;
    }

    private ResourceDemandingBehaviour findRdBehaviorToInsertElements(final CorrespondenceInstance ci) {
        final Set<ResourceDemandingBehaviour> correspondingResourceDemandingBehaviours = CorrespondenceInstanceUtil
                .getCorrespondingEObjectsByType(ci, this.oldMethod, ResourceDemandingBehaviour.class);
        if (null == correspondingResourceDemandingBehaviours || correspondingResourceDemandingBehaviours.isEmpty()) {
            logger.warn("No ResourceDemandingBehaviours found for method " + this.oldMethod
                    + ". Could not create ResourceDemandingBehavoir to insert SEFF elements");
            return null;
        }
        return correspondingResourceDemandingBehaviours.iterator().next();
    }
}
