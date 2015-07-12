package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collection;
import java.util.HashSet;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseRegistry;

public abstract class AbstractMIRTransformationExecuting implements EMFModelTransformationExecuting {
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
	

	protected EMFChangeResult handleEChange(EChange eChange) {
		TransformationChangeResult result = new TransformationChangeResult();
		Collection<MIRMappingRealization> relevantMappings = getCandidateMappings(eChange);
		for (MIRMappingRealization mapping : relevantMappings) {
			result.addChangeResult(mapping.applyEChange(eChange, getMappedCorrespondenceInstance()));
		}
		
		// transform TCR into EMFCR
		EMFChangeResult emfChangeResult = new EMFChangeResult();
		
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
