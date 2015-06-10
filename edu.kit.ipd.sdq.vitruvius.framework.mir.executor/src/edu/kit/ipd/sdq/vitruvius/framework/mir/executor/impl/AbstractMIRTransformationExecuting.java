package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRModelInformationProvider;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseRegistry;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public abstract class AbstractMIRTransformationExecuting implements EMFModelTransformationExecuting, MIRModelInformationProvider {
	private ResponseRegistry responseRegistry;
	private InvariantRegistry invariantRegistry;
	private Collection<MIRMapping> mappings;

	private Map<Correspondence, MIRMapping> correspondence2mapping;
	
	public AbstractMIRTransformationExecuting() {
		responseRegistry = new ResponseRegistryImpl();
		invariantRegistry = new InvariantRegistryImpl();
		mappings = new HashSet<MIRMapping>();

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
		mappings.add(mapping);
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
		EMFChangeResult result = new EMFChangeResult();
		Collection<MIRMapping> relevantMappings = getCandidateMappings(eChange, correspondenceInstance);
		for (MIRMapping mapping : relevantMappings) {
			// TODO: dependency on AbstractMIRTransformationExecuting
			result.addChangeResult(mapping.applyEChange(eChange, correspondenceInstance, this));
		}
		return result;
	}
	
	/**
	 * Returns mappings that could be affected by the given {@link EChange}. Always
	 * returns a conservative estimate. Should be overwritten by the generated
	 * subclass of {@link AbstractMIRTransformationExecuting}, since the base
	 * implementation returns all mappings (i.e. the most conservative estimate).  
	 * @return
	 */
	protected Collection<MIRMapping> getCandidateMappings(EChange eChange, CorrespondenceInstance correspondenceInstance) {
		return mappings;
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
	@Deprecated
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
	
	@Override
	public Pair<EObject, EObject> getReverseFeatureMappedBy(EObject target,
			EStructuralFeature feature, CorrespondenceInstance correspondenceInstance,
			MIRMapping mapping) {
		Collection<EObject> candidates = getReverseFeature(target, feature);
		for (EObject candidate : candidates) {
			EObject candidateTarget = getMappingTarget(candidate, correspondenceInstance, mapping);
			if (candidateTarget != null) { // i.e. the candidate is not mapped by mapping
				return new Pair(candidate, candidateTarget);
			}
		}
		return null;
	}

	/**
	 * Checks if the given mapping maps <code>eObject</code> and returns the target.
	 * @param eObject the {@link EObject} to check
	 * @param correspondenceInstance
	 * @param mapping
	 * @return The target of the mapping if this mapping maps <code>eObject</code>,
	 * 	<code>null</code> otherwise.
	 */
	public EObject getMappingTarget(EObject eObject , CorrespondenceInstance correspondenceInstance,
			MIRMapping mapping) {
		Collection<Correspondence> correspondences = correspondenceInstance.getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			MIRMapping mappingForCorrespondence = getMappingForCorrespondence(correspondence);
			if (mappingForCorrespondence == mapping) {
				return getCorrespondenceTarget(eObject, correspondence);
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the other side of a correspondence
	 */
	private EObject getCorrespondenceTarget(EObject source, Correspondence correspondence) {
		throw new IllegalStateException("Operation not implemented");
	}

	/**
	 * Checks if the given mapping maps <code>eObject</code>.
	 * @param eObject the {@link EObject} to check
	 * @param correspondenceInstance
	 * @param mapping
	 * @return <code>true</code> if this mapping maps <code>eObject</code>
	 */
	public boolean checkIfMappedBy(EObject eObject, CorrespondenceInstance correspondenceInstance,
			MIRMapping mapping) {
		Collection<Correspondence> correspondences = correspondenceInstance.getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			MIRMapping mappingForCorrespondence = getMappingForCorrespondence(correspondence);
			if (mappingForCorrespondence == mapping) {
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
