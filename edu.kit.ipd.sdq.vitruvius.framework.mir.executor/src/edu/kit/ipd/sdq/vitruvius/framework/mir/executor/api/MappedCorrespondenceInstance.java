package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api;

import static edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.requireType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractDelegatingCorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public class MappedCorrespondenceInstance extends AbstractDelegatingCorrespondenceInstanceDecorator<Map<String,Map<String,String>>> {
	private final Map<SameTypeCorrespondence,MIRMappingRealization> correspondence2MappingMap;
 
	public MappedCorrespondenceInstance(CorrespondenceInstanceDecorator correspondenceInstance) {
		super(correspondenceInstance);
		this.correspondence2MappingMap = new HashMap<SameTypeCorrespondence, MIRMappingRealization>();
	}

	@Override
	protected String getDecoratorFileExtPrefix() {
		return MIRHelper.getCorrespondenceDecoratorFileExtPrefix();
	}
	
	@Override
	protected Map<String, Map<String, String>> getDecoratorObject() {
		// FIXME MK (deco): create map for persistence by traversing correspondence2MappingMap
		// BEGIN MOCKUP
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		Map<String,String> secondmap = new HashMap<String, String>();
		secondmap.put("tuid2", "mappingID");
		map.put("tuid1", secondmap);
		// END MOCKUP
		return map;
	}

	@Override
	protected void initializeFromDecoratorObject(Map<String, Map<String, String>> object) {
		// FIXME MK (deco): build correspondence2MappingMap from object
		for (Entry<String, Map<String,String>> e : object.entrySet()) {
			for (Entry<String,String> e2 : e.getValue().entrySet()) {
				System.out.println("correspondence for " + e.getKey() + " and " + e2.getKey() + " created by mapping " + e2.getValue());
			}
		}
	}

	@Override
	public void addSameTypeCorrespondence(SameTypeCorrespondence correspondence) {
		super.addSameTypeCorrespondence(correspondence);
		// FIXME MK (deco): store correct mapping realization
		// this.correspondence2MappingMap.put(correspondence, mapping);
	}
	
	// TODO: clean up methods copied from edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappedCorrespondenceInstance
	public Pair<TUID, EObject> getCorrespondenceTarget(EObject eObject, Correspondence correspondence) {
		SameTypeCorrespondence sameTypeCorrespondence = requireType(correspondence, SameTypeCorrespondence.class);
		
		TUID elementATUID = sameTypeCorrespondence.getElementATUID();
		EObject eObjectA = resolveEObjectFromTUID(elementATUID);
		TUID elementBTUID = sameTypeCorrespondence.getElementBTUID();
		EObject eObjectB = resolveEObjectFromTUID(elementBTUID);
		
		// return the other side of the correspondence
		if (eObjectA.equals(eObject)) { return new Pair<TUID, EObject>(elementBTUID, eObjectB); }
		if (eObjectB.equals(eObject)) { return new Pair<TUID, EObject>(elementATUID, eObjectA); }
		
		throw new IllegalArgumentException(eObject.toString() + " is not part of correspondence " + correspondence.toString());
	}
	
	public SameTypeCorrespondence getMappedCorrespondence(EObject eObject,
			MIRMappingRealization mapping) {
		
		Collection<Correspondence> correspondences = getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			MIRMappingRealization mappingForCorrespondence = getMappingForCorrespondence(correspondence);
			if (mappingForCorrespondence.equals(mapping)) {
				return requireType(correspondence, SameTypeCorrespondence.class);
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the MIRMappingRealization that created a correspondence, or <code>null</code>,
	 * if no mapping is coupled to the correspondence. To get all MIRMappingRealizations for an
	 * EObject, first get all correspondences from the {@link CorrespondenceInstance},
	 * then use this method.
	 */
	public MIRMappingRealization getMappingForCorrespondence(Correspondence correspondence) {
		return correspondence2MappingMap.get(correspondence);
	}
	
	/**
	 * Register a mapping for a correspondence that can then be retrieved by calling
	 * {@link #getMappingForCorrespondence(Correspondence)}.
	 * @param mapping
	 * @param correspondence
	 */
	public void registerMappingForCorrespondence(MIRMappingRealization mapping,
			Correspondence correspondence) {
		throw new UnsupportedOperationException("not implemented");
	}
	
	public void createMappedCorrespondence(EObject eObjectA, EObject eObjectB, MIRMappingRealization mappingRealization) {
		throw new UnsupportedOperationException("not implemented");
	}
	
	public boolean unregisterMappingForCorrespondence(MIRMappingRealization mapping,
			Correspondence correspondence) {
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Checks if the given mapping maps <code>eObject</code> and returns the target.
	 * @param eObject the {@link EObject} to check
	 * @param correspondenceInstance
	 * @param mapping
	 * @return The target of the mapping if this mapping maps <code>eObject</code>,
	 * 	<code>null</code> otherwise.
	 */
	public EObject getMappingTarget(EObject eObject, MIRMappingRealization mapping) {
		Collection<Correspondence> correspondences = correspondenceInstance.getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			MIRMappingRealization mappingForCorrespondence = getMappingForCorrespondence(correspondence);
			if (mappingForCorrespondence.equals(mapping)) {
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
	public boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping) {
		Collection<Correspondence> correspondences = getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			MIRMappingRealization mappingsForCorrespondence = getMappingForCorrespondence(correspondence);
			if (mappingsForCorrespondence.equals(mapping)) {
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping);
	 * CorrespondenceInstance getCorrespondenceInstance(); EObject
	 * getMappingTarget(EObject eObject, CorrespondenceInstance
	 * correspondenceInstance, MIRMappingRealization mapping); boolean
	 * unregisterMappingForCorrespondence(MIRMappingRealization mapping,
	 * Correspondence correspondence); void
	 * registerMappingForCorrespondence(MIRMappingRealization mapping,
	 * Correspondence correspondence); Collection<MIRMappingRealization>
	 * getMappingsForCorrespondence(Correspondence correspondence);
	 * 
	 * SameTypeCorrespondence getMappedCorrespondence(EObject eObject,
	 * MIRMappingRealization abstractMIRMappingRealization); Pair<TUID, EObject>
	 * getCorrespondenceTarget(EObject eObject, Correspondence correspondence);
	 */
}
