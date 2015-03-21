package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FeatureInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.EChangeListener;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseRegistry;

public abstract class AbstractMIRTransformationExecuting implements EMFModelTransformationExecuting {
	private ResponseRegistry responseRegistry;
	private InvariantRegistry invariantRegistry;
	private MIRMappingRegistry mappingRegistry;
	
	private Map<EObject, MIRMapping> eObject2Mapping;
	private Map<FeatureInstance, MIRMapping> featureInstance2Mapping;
	
	public AbstractMIRTransformationExecuting() {
		responseRegistry = new ResponseRegistryImpl();
		invariantRegistry = new InvariantRegistryImpl();
		mappingRegistry = new MIRMappingRegistryImpl();
		
		eObject2Mapping = new HashMap<EObject, MIRMapping>();
		featureInstance2Mapping = new HashMap<FeatureInstance, MIRMapping>();
		
		setup();
	}
	
	protected void addResponse(Response response) {
		responseRegistry.addResponse(response);
	}
	
	protected void addInvariant(Invariant invariant) {
		invariantRegistry.addInvariant(invariant);
	}
	
	protected void addMIRMapping(MIRMapping mapping) {
		mappingRegistry.addMapping(mapping);
	}

	@Override
	public EMFChangeResult executeTransformation(EMFModelChange change, CorrespondenceInstance correspondenceInstance) {
		return handleEChange(change.getEChange(), correspondenceInstance);
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

	protected EMFChangeResult handleEChange(EChange eChange, CorrespondenceInstance correspondenceInstance) {
		return null;
	}
	
	protected abstract void setup();
}
