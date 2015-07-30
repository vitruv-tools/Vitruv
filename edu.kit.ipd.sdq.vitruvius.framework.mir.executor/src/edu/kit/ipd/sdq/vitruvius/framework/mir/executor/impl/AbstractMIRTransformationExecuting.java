package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EclipseHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public abstract class AbstractMIRTransformationExecuting implements EMFModelTransformationExecuting {
    private final static Logger LOGGER = Logger.getLogger(AbstractMIRTransformationExecuting.class);

    private final ResponseRegistry responseRegistry;
    private final InvariantRegistry invariantRegistry;
    private final Collection<MIRMappingRealization> mappings;

    public AbstractMIRTransformationExecuting() {
        this.responseRegistry = new ResponseRegistryImpl();
        this.invariantRegistry = new InvariantRegistryImpl();
        this.mappings = new HashSet<MIRMappingRealization>();

        this.setup();
    }

    protected void addResponse(final Response response) {
        this.responseRegistry.addResponse(response);
    }

    protected void addInvariant(final Invariant invariant) {
        this.invariantRegistry.addInvariant(invariant);
    }

    protected void addMIRMapping(final MIRMappingRealization mapping) {
        this.mappings.add(mapping);
    }

    protected abstract MappedCorrespondenceInstance getMappedCorrespondenceInstance();

    protected abstract void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance);

    @Override
    public EMFChangeResult executeTransformation(final EMFModelChange change,
            final CorrespondenceInstance correspondenceInstance) {
        this.setCorrespondenceInstance(correspondenceInstance);
        return this.handleEChange(change.getEChange());
    }

    @Override
    public EMFChangeResult executeTransformation(final CompositeChange compositeChange,
            final CorrespondenceInstance correspondenceInstance) {
        final EMFChangeResult result = new EMFChangeResult();
        for (final Change c : compositeChange.getChanges()) {
            if (c instanceof CompositeChange) {
                result.addChangeResult(this.executeTransformation((CompositeChange) c, correspondenceInstance));
            } else if (c instanceof EMFModelChange) {
                result.addChangeResult(this.executeTransformation((EMFModelChange) c, correspondenceInstance));
            } else {
                throw new IllegalArgumentException("Change subtype " + c.getClass().getName() + " not handled");
            }
        }

        return result;
    }

    protected MIRMappingChangeResult callRelevantMappings(final EChange eChange) {
        final MIRMappingChangeResult result = new MIRMappingChangeResult();
        final Collection<MIRMappingRealization> relevantMappings = this.getCandidateMappings(eChange);
        for (final MIRMappingRealization mapping : relevantMappings) {
            result.add(mapping.applyEChange(eChange, this.getMappedCorrespondenceInstance()));
        }
        return result;
    }

    private EMFChangeResult handleNonContainedEObjects(final MIRMappingChangeResult change) {
        final EMFChangeResult emfChangeResult = new EMFChangeResult();
        final CorrespondenceInstance ci = this.getMappedCorrespondenceInstance().getCorrespondenceInstance();

        for (final EObject object : change.getObjectsToDelete()) {
            if (null == object.eContainer()) {
                LOGGER.warn("EObject is not contained, it is already deleted: " + object.toString());
            } else if (null == object.eResource()) {
                LOGGER.warn("EObject is not contained in resource, changed resource cannot be infered: "
                        + object.toString());
            } else {
                final VURI resourceVURI = VURI.getInstance(object.eResource());
                if (EcoreHelper.isRoot(object)) {
                    emfChangeResult.getExistingObjectsToDelete().add(resourceVURI);
                } else { // non-root object: ensure it isn't contained anymore
                    emfChangeResult.getExistingObjectsToSave().add(resourceVURI);
                    EcoreUtil.remove(object);
                }
            }
        }

        // Taken from edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.
        // PCMJaMoPPTransformationExecuter.handleEObjectsToSaveInTransformationChange(Set<EObject>,
        // Set<VURI>)
        for (final EObject obj : change.getObjectsToSave()) {
            Resource eObjResource = obj.eResource();
            if (eObjResource == null) {
                LOGGER.info("EObject " + obj.toString() + " not contained in any resource.");
                eObjResource = EclipseHelper.askAndSaveResource(obj);
            }

            final VURI existingVURIToSave = VURI.getInstance(eObjResource);
            emfChangeResult.getExistingObjectsToSave().add(existingVURIToSave);
        }

        for (final Pair<EObject, EObject> correspondenceObjects : change.getCorrespondencesToAdd()) {
            emfChangeResult.addNewCorrespondence(ci, correspondenceObjects.getFirst(),
                    correspondenceObjects.getSecond());
        }

        return emfChangeResult;
    }

    protected EMFChangeResult handleEChange(final EChange eChange) {
        LOGGER.trace("handleEChange(" + eChange + ")");
        final MIRMappingChangeResult changeResult = this.callRelevantMappings(eChange);
        final EMFChangeResult emfChangeResult = this.handleNonContainedEObjects(changeResult);

        return emfChangeResult;
    }

    /**
     * Returns mappings that could be affected by the given {@link EChange}. Always returns a
     * conservative estimate. Should be overwritten by the generated subclass of
     * {@link AbstractMIRTransformationExecuting}, since the base implementation returns all
     * mappings (i.e. the most conservative estimate).
     * 
     * @return
     */
    protected Collection<MIRMappingRealization> getCandidateMappings(final EChange eChange) {
        return this.mappings;
    }

    protected abstract void setup();
}
