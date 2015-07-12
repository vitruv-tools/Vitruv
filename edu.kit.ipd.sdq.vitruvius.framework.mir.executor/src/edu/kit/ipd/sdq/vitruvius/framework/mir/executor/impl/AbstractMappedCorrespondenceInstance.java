package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

import static edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*;

public abstract class AbstractMappedCorrespondenceInstance implements MappedCorrespondenceInstance {
	
	public abstract CorrespondenceInstance getCorrespondenceInstance();
	
	private Map<Correspondence, Collection<MIRMappingRealization>> correspondence2mappings;
	
	public AbstractMappedCorrespondenceInstance() {
		correspondence2mappings = new HashMap<Correspondence, Collection<MIRMappingRealization>>();
	}

	@Override
	public EObject getMappingTarget(EObject eObject, MIRMappingRealization mapping) {
		Collection<Correspondence> correspondences =
				getCorrespondenceInstance().getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			Collection<MIRMappingRealization> mappingsForCorrespondence = getMappingsForCorrespondence(correspondence);
			if (mappingsForCorrespondence.contains(mapping)) {
				return getCorrespondenceTarget(eObject, correspondence).getSecond();
			}
		}
		
		return null;
	}

	@Override
	public Pair<TUID, EObject> getCorrespondenceTarget(EObject eObject, Correspondence correspondence) {
		SameTypeCorrespondence sameTypeCorrespondence = requireType(correspondence, SameTypeCorrespondence.class);
		
		TUID elementATUID = sameTypeCorrespondence.getElementATUID();
		EObject eObjectA = getCorrespondenceInstance().resolveEObjectFromTUID(elementATUID);
		TUID elementBTUID = sameTypeCorrespondence.getElementBTUID();
		EObject eObjectB = getCorrespondenceInstance().resolveEObjectFromTUID(elementBTUID);
		
		// return the other side of the correspondence
		if (eObjectA.equals(eObject)) { return new Pair<TUID, EObject>(elementBTUID, eObjectB); }
		if (eObjectB.equals(eObject)) { return new Pair<TUID, EObject>(elementATUID, eObjectA); }
		
		throw new IllegalArgumentException(eObject.toString() + " is not part of correspondence " + correspondence.toString());
	}
	
	@Override
	public SameTypeCorrespondence getMappedCorrespondence(EObject eObject,
			MIRMappingRealization mapping) {
		
		Collection<Correspondence> correspondences = getCorrespondenceInstance().getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			Collection<MIRMappingRealization> mappingsForCorrespondence = getMappingsForCorrespondence(correspondence);
			if (mappingsForCorrespondence.contains(mapping)) {
				return requireType(correspondence, SameTypeCorrespondence.class);
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the MIRMappingRealizations that created a correspondence, or an empty
	 * set, if no mapping is coupled to the correspondence. To get all MIRMappings for an
	 * EObject, first get all correspondences from the {@link CorrespondenceInstance},
	 * then use this method.
	 * @param correspondence
	 * @return
	 */
	@Override
	public Collection<MIRMappingRealization> getMappingsForCorrespondence(Correspondence correspondence) {
		if (correspondence2mappings.containsKey(correspondence))
			return correspondence2mappings.get(correspondence);
		else
			return Collections.emptySet();
	}
	
	/**
	 * Register a mapping for a correspondence that can then be retrieved by calling
	 * {@link #getMappingForCorrespondence(Correspondence)}.
	 * @param mapping
	 * @param correspondence
	 */
	@Override
	public void registerMappingForCorrespondence(MIRMappingRealization mapping,
			Correspondence correspondence) {
		if (!correspondence2mappings.containsKey(correspondence))
			correspondence2mappings.put(correspondence, new HashSet<MIRMappingRealization>());
		
		correspondence2mappings.get(correspondence).add(mapping);
	}
	
	@Override
	public boolean unregisterMappingForCorrespondence(MIRMappingRealization mapping,
			Correspondence correspondence) {
		
		if (correspondence2mappings.containsKey(correspondence))
			return correspondence2mappings.get(correspondence).remove(mapping);
		else
			return false;
	}
	
	/**
	 * Checks if the given mapping maps <code>eObject</code> and returns the target.
	 * @param eObject the {@link EObject} to check
	 * @param correspondenceInstance
	 * @param mapping
	 * @return The target of the mapping if this mapping maps <code>eObject</code>,
	 * 	<code>null</code> otherwise.
	 */
	@Override
	public EObject getMappingTarget(EObject eObject, CorrespondenceInstance correspondenceInstance,
			MIRMappingRealization mapping) {
		Collection<Correspondence> correspondences = correspondenceInstance.getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			Collection<MIRMappingRealization> mappingsForCorrespondence = getMappingsForCorrespondence(correspondence);
			if (mappingsForCorrespondence.contains(mapping)) {
				return getCorrespondenceTarget(eObject, correspondence).getSecond();
			}
		}
		
		return null;
	}
	
	/**
	 * Checks if the given mapping maps <code>eObject</code>.
	 * @param eObject the {@link EObject} to check
	 * @param correspondenceInstance
	 * @param mapping
	 * @return <code>true</code> if this mapping maps <code>eObject</code>
	 */
	@Override
	public boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping) {
		Collection<Correspondence> correspondences = getCorrespondenceInstance().getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			Collection<MIRMappingRealization> mappingsForCorrespondence = getMappingsForCorrespondence(correspondence);
			if (mappingsForCorrespondence.contains(mapping)) {
				return true;
			}
		}
		
		return false;
	}

}
