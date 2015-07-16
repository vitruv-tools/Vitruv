package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff;

import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.ClassMethod;
import org.somox.gast2seff.visitors.FunctionCallClassificationVisitor;
import org.somox.gast2seff.visitors.IFunctionClassificationStrategy;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
import org.somox.gast2seff.visitors.VisitorUtils;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.seff.AbstractAction;
import de.uka.ipd.sdq.pcm.seff.ResourceDemandingBehaviour;
import de.uka.ipd.sdq.pcm.seff.ResourceDemandingSEFF;
import de.uka.ipd.sdq.pcm.seff.SeffFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CustomTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
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
    public EMFChangeResult execute(final CorrespondenceInstance ci, final UserInteracting userInteracting,
            final SynchronisationAbortedListener abortListener) {
        final EMFChangeResult emfChangeResult = new EMFChangeResult();
        // 1)
        this.removeCorrespondingAbstractActions(ci, emfChangeResult);

        // 2)
        final BasicComponent basicComponent = this.basicComponentFinder.findBasicComponentForMethod(this.newMethod, ci);
        final ResourceDemandingSEFF newSeffElements = this.executeSoMoXForMethod(basicComponent);

        // 3)
        this.connectCreatedSeffWithOldSEFF(newSeffElements);

        // 4)
        this.createNewCorrespondences(ci, emfChangeResult, newSeffElements, basicComponent);

        return emfChangeResult;

    }

    private ResourceDemandingSEFF executeSoMoXForMethod(final BasicComponent basicComponent) {
        final FunctionCallClassificationVisitor functionCallClassificationVisitor = new FunctionCallClassificationVisitor(
                this.iFunctionClassificationStrategy);
        final ResourceDemandingSEFF newSeffElements = SeffFactory.eINSTANCE.createResourceDemandingSEFF();

        VisitorUtils.visitJaMoPPMethod(newSeffElements, basicComponent, this.newMethod, null,
                functionCallClassificationVisitor, this.interfaceOfExternalCallFinder);

        return newSeffElements;
    }

    private void createNewCorrespondences(final CorrespondenceInstance ci, final EMFChangeResult emfChangeResult,
            final ResourceDemandingSEFF newSeffElements, final BasicComponent basicComponent) {
        for (final AbstractAction abstractAction : newSeffElements.getSteps_Behaviour()) {
            emfChangeResult.addNewCorrespondence(ci, abstractAction, this.newMethod, null);
        }
        final VURI bcVURI = VURI.getInstance(basicComponent.eResource());
        emfChangeResult.getExistingObjectsToSave().add(bcVURI);
    }

    private void connectCreatedSeffWithOldSEFF(final ResourceDemandingSEFF newSeffElements) {
        newSeffElements.getSteps_Behaviour();
        final ResourceDemandingBehaviour rdBehavior = this.insertAfterAbstractAction
                .getResourceDemandingBehaviour_AbstractAction();
        final int insertIndex = rdBehavior.getSteps_Behaviour().indexOf(this.insertAfterAbstractAction);
        if (0 == insertIndex) {
            // add a StartAction
            final AbstractAction startAction = SeffFactory.eINSTANCE.createStartAction();
            newSeffElements.getSteps_Behaviour().add(0, startAction);
        }
        if (insertIndex == rdBehavior.getSteps_Behaviour().size() - 1) {
            // add a stop action
            final AbstractAction stopAction = SeffFactory.eINSTANCE.createStopAction();
            newSeffElements.getSteps_Behaviour().add(stopAction);
        }

        rdBehavior.getSteps_Behaviour().addAll(insertIndex, newSeffElements.getSteps_Behaviour());
        VisitorUtils.connectActions(rdBehavior);
    }

    private void removeCorrespondingAbstractActions(final CorrespondenceInstance ci,
            final EMFChangeResult emfChangeResult) {
        final Set<EObject> correspondingEObjects = ci.getAllCorrespondingEObjects(this.oldMethod);
        for (final EObject correspondingEObject : correspondingEObjects) {
            if (correspondingEObject instanceof AbstractAction) {
                final AbstractAction abstractAction = (AbstractAction) correspondingEObject;
                final AbstractAction predecessor = abstractAction.getPredecessor_AbstractAction();
                if (!correspondingEObjects.contains(predecessor)) {
                    this.insertAfterAbstractAction = predecessor;
                }
                final TUID tuidToRemove = ci.calculateTUIDFromEObject(correspondingEObject);
                emfChangeResult.addCorrespondenceToDelete(ci, tuidToRemove);
                EcoreUtil.remove(correspondingEObject);
            }
        }

    }
}
