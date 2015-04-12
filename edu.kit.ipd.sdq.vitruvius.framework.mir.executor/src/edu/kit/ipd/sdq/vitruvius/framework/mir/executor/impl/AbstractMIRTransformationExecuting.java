package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRModelInformationProvider;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseRegistry;

public abstract class AbstractMIRTransformationExecuting implements EMFModelTransformationExecuting, MIRModelInformationProvider {
	private ResponseRegistry responseRegistry;
	private InvariantRegistry invariantRegistry;
	private MIRMappingRegistry mappingRegistry;

	private Map<Correspondence, MIRMapping> correspondence2mapping;
	
	public AbstractMIRTransformationExecuting() {
		responseRegistry = new ResponseRegistryImpl();
		invariantRegistry = new InvariantRegistryImpl();
		mappingRegistry = new MIRMappingRegistryImpl();

		correspondence2mapping = new HashMap<Correspondence, MIRMapping>();
		
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
		return mappingRegistry.applyAllMappings(eChange, correspondenceInstance, this);
	}
	
	@Override
	public Collection<EObject> getReverseFeature(EObject target, EStructuralFeature feature) {
		Collection<Setting> settings = EcoreUtil.UsageCrossReferencer.find(target, target.eResource());
		
		Collection<EObject> result = new ArrayList<EObject>();
		for (Setting setting : settings) {
			if (feature.equals(setting.getEStructuralFeature())) {
				result.add(setting.getEObject());
			}
		}
		
		return result;
	}
	
	@Override
	public boolean isReferencedFromTypeByFeature(EObject target,
			EClassifier sourceType, EStructuralFeature feature) {
		
		Collection<Setting> settings = EcoreUtil.UsageCrossReferencer.find(target, target.eResource());
		
		for (Setting setting : settings) {
			if (feature.equals(setting.getEStructuralFeature())
					&& sourceType.equals(setting.getEObject().getClass())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns the MIRMapping that created a correspondence, or null if
	 * no mapping is coupled to the correspondence. To get all MIRMappings for an
	 * EObject, first get all correspondences from the {@link CorrespondenceInstance},
	 * then use this method.
	 * @param correspondence
	 * @return
	 */
	public MIRMapping getMappingForCorrespondence(Correspondence correspondence) {
		return correspondence2mapping.get(correspondence);
	}
	
	/**
	 * Register a mapping for a correspondence that can then be retrieved by calling
	 * {@link #getMappingForCorrespondence(Correspondence)}.
	 * @param mapping
	 * @param correspondence
	 */
	public void registerMappingForCorrespondence(MIRMapping mapping,
			Correspondence correspondence) {
		correspondence2mapping.put(correspondence, mapping);		
	}
	
	protected abstract void setup();
}
