package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff;

import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.somox.gast2seff.visitors.FunctionCallClassificationVisitor;
import org.somox.gast2seff.visitors.IFunctionClassificationStrategy;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
import org.somox.gast2seff.visitors.VisitorUtils;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CustomTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;

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

    private AbstractAction insertAfterAbstractAction;

    private final InterfaceOfExternalCallFinding interfaceOfExternalCallFinder;

    public ClassMethodBodyChangedTransformation(final ClassMethod oldMethod, final ClassMethod newMethod,
            final BasicComponentFinding basicComponentFinder,
            final IFunctionClassificationStrategy iFunctionClassificationStrategy,
            final InterfaceOfExternalCallFinding interfaceOfExternalCallFinder) {
        this.oldMethod = oldMethod;
        this.newMethod = newMethod;
        this.basicComponentFinder = basicComponentFinder;
        this.iFunctionClassificationStrategy = iFunctionClassificationStrategy;
        this.interfaceOfExternalCallFinder = interfaceOfExternalCallFinder;
    }

    /**
     * This method is called after a java method body has been changed. In order to keep the SEFF
     * consistent with the method we 1) remove all AbstractActions corresponding to the Method from
     * the SEFF and from the correspondenceInstance 2) run the SoMoX SEFF extractor for this method,
     * 3) reconnect the newly extracted SEFF elements with the old elements 4) create new
     * AbstractAction 2 Method correspondences for the new method (and its inner methods)
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
        final BasicComponent basicComponent = this.basicComponentFinder.findBasicComponentForMethod(this.newMethod,
                blackboard.getCorrespondenceInstance());
        final ResourceDemandingSEFF newSeffElements = this.executeSoMoXForMethod(basicComponent);

        // 3)
        this.connectCreatedSeffWithOldSEFF(newSeffElements, blackboard.getCorrespondenceInstance());

        // 4)
        this.createNewCorrespondences(blackboard.getCorrespondenceInstance(), newSeffElements, basicComponent);

        return new TransformationResult();
    }

    /**
     * checks whether the change is considered architecture relevant. This is the case if either the
     * new or the old method does have a corresponding SEFF
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
            final Set<ResourceDemandingSEFF> correspondingEObjectsByType = ci.getCorrespondingEObjectsByType(method,
                    ResourceDemandingSEFF.class);
            if (null != correspondingEObjectsByType && !correspondingEObjectsByType.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private ResourceDemandingSEFF executeSoMoXForMethod(final BasicComponent basicComponent) {
        final FunctionCallClassificationVisitor functionCallClassificationVisitor = new FunctionCallClassificationVisitor(
                this.iFunctionClassificationStrategy);
        final ResourceDemandingSEFF newSeffElements = SeffFactory.eINSTANCE.createResourceDemandingSEFF();

        VisitorUtils.visitJaMoPPMethod(newSeffElements, basicComponent, this.newMethod, null,
                functionCallClassificationVisitor, this.interfaceOfExternalCallFinder);

        return newSeffElements;
    }

    private void createNewCorrespondences(final CorrespondenceInstance ci, final ResourceDemandingSEFF newSeffElements,
            final BasicComponent basicComponent) {
        for (final AbstractAction abstractAction : newSeffElements.getSteps_Behaviour()) {
            ci.createAndAddEObjectCorrespondence(abstractAction, this.newMethod);
        }
    }

    private void connectCreatedSeffWithOldSEFF(final ResourceDemandingSEFF newSeffElements,
            final CorrespondenceInstance ci) {
        final ResourceDemandingBehaviour rdBehavior = this.findRdBehaviorToInsertElements(ci);
        if (null == rdBehavior) {
            return;
        }
        int insertIndex = 0;
        if (null != this.insertAfterAbstractAction
                && -1 != rdBehavior.getSteps_Behaviour().indexOf(this.insertAfterAbstractAction)) {
            insertIndex = rdBehavior.getSteps_Behaviour().indexOf(this.insertAfterAbstractAction);
        }
        if (0 == insertIndex) {
            // add a StartAction
            final AbstractAction startAction = SeffFactory.eINSTANCE.createStartAction();
            newSeffElements.getSteps_Behaviour().add(0, startAction);
        }
        if (!this.isLastElementStopAction(rdBehavior)) {
            if (insertIndex == rdBehavior.getSteps_Behaviour().size() - 1 || null == this.insertAfterAbstractAction) {
                // add a stop action if the last element is not a stop action and it was inserted at
                // the
                // end of the seff or
                // insertAfterAbstractAction is null (which means there was no correspondence
                // before)
                final AbstractAction stopAction = SeffFactory.eINSTANCE.createStopAction();
                newSeffElements.getSteps_Behaviour().add(stopAction);
            }
        }
        rdBehavior.getSteps_Behaviour().addAll(insertIndex, newSeffElements.getSteps_Behaviour());
        VisitorUtils.connectActions(rdBehavior);
    }

    private boolean isLastElementStopAction(final ResourceDemandingBehaviour rdBehavior) {
        if (rdBehavior.getSteps_Behaviour().isEmpty()) {
            return false;
        }
        final AbstractAction lastAction = rdBehavior.getSteps_Behaviour()
                .get(rdBehavior.getSteps_Behaviour().size() - 1);
        return lastAction instanceof AbstractAction;
    }

    private void removeCorrespondingAbstractActions(final CorrespondenceInstance ci) {
        final Set<EObject> correspondingEObjects = ci.getAllCorrespondingEObjects(this.oldMethod);
        for (final EObject correspondingEObject : correspondingEObjects) {
            if (correspondingEObject instanceof AbstractAction) {
                final AbstractAction abstractAction = (AbstractAction) correspondingEObject;
                final AbstractAction predecessor = abstractAction.getPredecessor_AbstractAction();
                if (!correspondingEObjects.contains(predecessor)) {
                    this.insertAfterAbstractAction = predecessor;
                }
                final TUID tuidToRemove = ci.calculateTUIDFromEObject(correspondingEObject);
                ci.removeDirectAndChildrenCorrespondencesOnBothSides(tuidToRemove);
                EcoreUtil.remove(correspondingEObject);
            }
        }
    }

    private ResourceDemandingBehaviour findRdBehaviorToInsertElements(final CorrespondenceInstance ci) {
        if (null != this.insertAfterAbstractAction) {
            final ResourceDemandingBehaviour rdBehavior = this.insertAfterAbstractAction
                    .getResourceDemandingBehaviour_AbstractAction();
            return rdBehavior;
        }
        final Set<ResourceDemandingSEFF> correspondingSeffs = ci.getCorrespondingEObjectsByType(this.oldMethod,
                ResourceDemandingSEFF.class);
        if (null == correspondingSeffs || correspondingSeffs.isEmpty()) {
            logger.warn("No SEFF found for method " + this.oldMethod
                    + ". Could not create ResourceDemandingBehavoir to insert SEFF elements");
            return null;
        }
        return correspondingSeffs.iterator().next();
    }
}
