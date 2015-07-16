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
	
	private ResponseRegistry responseRegistry;
	private InvariantRegistry invariantRegistry;
	private Collection<MIRMappingRealization> mappings;
	
	public AbstractMIRTransformationExecuting() {
		responseRegistry = new ResponseRegistryImpl();
		invariantRegistry = new InvariantRegistryImpl();
		mappings = new HashSet<MIRMappingRealization>();
		
		setup();
	}
	
	protected void addResponse(Response response) {
		responseRegistry.addResponse(response);
	}
	
	protected void addInvariant(Invariant invariant) {
		invariantRegistry.addInvariant(invariant);
	}
	
	protected void addMIRMapping(MIRMappingRealization mapping) {
		mappings.add(mapping);
	}
	
	protected abstract MappedCorrespondenceInstance getMappedCorrespondenceInstance();
	
	protected abstract void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance);

	@Override
	public EMFChangeResult executeTransformation(EMFModelChange change, CorrespondenceInstance correspondenceInstance) {
		setCorrespondenceInstance(correspondenceInstance);
		return handleEChange(change.getEChange());
	}

	@Override
	public EMFChangeResult executeTransformation(CompositeChange compositeChange, CorrespondenceInstance correspondenceInstance) {
		final EMFChangeResult result = new EMFChangeResult();
		for (Change c : compositeChange.getChanges()) {
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
	
	protected MIRMappingChangeResult callRelevantMappings(EChange eChange) {
		MIRMappingChangeResult result = new MIRMappingChangeResult();
		Collection<MIRMappingRealization> relevantMappings = getCandidateMappings(eChange);
		for (MIRMappingRealization mapping : relevantMappings) {
			result.add(mapping.applyEChange(eChange, getMappedCorrespondenceInstance()));
		}
		return result;
	}
	
	private EMFChangeResult handleNonContainedEObjects(MIRMappingChangeResult change) {
		EMFChangeResult emfChangeResult = new EMFChangeResult();
		CorrespondenceInstance ci = getMappedCorrespondenceInstance().getCorrespondenceInstance();
		
		for (EObject object : change.getObjectsToDelete()) {
			if (null == object.eContainer()) {
				LOGGER.warn("EObject is not contained, it is already deleted: " + object.toString());
			} else if (null == object.eResource()) {
				LOGGER.warn("EObject is not contained in resource, changed resource cannot be infered: " + object.toString());
			} else {
				VURI resourceVURI = VURI.getInstance(object.eResource());
				if (EcoreHelper.isRoot(object)) {
					emfChangeResult.getExistingObjectsToDelete().add(resourceVURI);
				} else { // non-root object: ensure it isn't contained anymore
					emfChangeResult.getExistingObjectsToSave().add(resourceVURI);
					EcoreUtil.remove(object);
				}
			}
		}
		
		// Taken from edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.
		// PCMJaMoPPTransformationExecuter.handleEObjectsToSaveInTransformationChange(Set<EObject>, Set<VURI>)
		for (EObject obj : change.getObjectsToSave()) {
			Resource eObjResource = obj.eResource();
			if (eObjResource == null) {
				LOGGER.info("EObject " + obj.toString() + " not contained in any resource.");
				eObjResource = EclipseHelper.askAndSaveResource(obj);
			}
			
			VURI existingVURIToSave = VURI.getInstance(eObjResource);
			emfChangeResult.getExistingObjectsToSave().add(existingVURIToSave);
		}
		
		for (Pair<EObject, EObject> correspondenceObjects : change.getCorrespondencesToAdd()) {
			// TODO: respect correspondence hierarchy
			emfChangeResult.addNewCorrespondence(ci,
					correspondenceObjects.getFirst(),
					correspondenceObjects.getSecond(), null);
		}
		
		return emfChangeResult;
	}

	protected EMFChangeResult handleEChange(EChange eChange) {
		LOGGER.trace("handleEChange(" + eChange + ")");
		MIRMappingChangeResult changeResult = callRelevantMappings(eChange);
		EMFChangeResult emfChangeResult = handleNonContainedEObjects(changeResult);
	
		return emfChangeResult;
	}
	
	/**
	 * Returns mappings that could be affected by the given {@link EChange}. Always
	 * returns a conservative estimate. Should be overwritten by the generated
	 * subclass of {@link AbstractMIRTransformationExecuting}, since the base
	 * implementation returns all mappings (i.e. the most conservative estimate).  
	 * @return
	 */
	protected Collection<MIRMappingRealization> getCandidateMappings(EChange eChange) {
		return mappings;
	}

	protected abstract void setup();
}
