package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api;

import static edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.filterType;
import static edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.requireType;

import java.util.HashMap;
import java.util.Map;

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

public class MappedCorrespondenceInstance extends AbstractDelegatingCorrespondenceInstanceDecorator<Map<SameTypeCorrespondence,MIRMappingRealization>> {
	private Map<SameTypeCorrespondence,MIRMappingRealization> correspondence2MappingMap;
 
	@SuppressWarnings("unchecked")
	public MappedCorrespondenceInstance(CorrespondenceInstanceDecorator correspondenceInstance) {
		// this seems to be the only way to provide the correct instance of the map class to the ADCID
		super(correspondenceInstance, (Class<Map<SameTypeCorrespondence,MIRMappingRealization>>) new HashMap<SameTypeCorrespondence,MIRMappingRealization>().getClass());
		this.correspondence2MappingMap = new HashMap<SameTypeCorrespondence, MIRMappingRealization>();
	}

	@Override
	protected String getDecoratorFileExtPrefix() {
		return MIRHelper.getCorrespondenceDecoratorFileExtPrefix();
	}
	
	@Override
	protected Map<SameTypeCorrespondence,MIRMappingRealization> getDecoratorObject() {
		return this.correspondence2MappingMap;
	}

	@Override
	protected void initializeFromDecoratorObject(Map<SameTypeCorrespondence,MIRMappingRealization> object) {
		this.correspondence2MappingMap = object;
	}
	
	@Override
	protected void initializeWithoutDecoratorObject() {
		// empty
	}

	public void registerMappingForCorrespondence(SameTypeCorrespondence correspondence, MIRMappingRealization mapping) {
		// FIXME MK (deco): store mapping realization automatically
		 this.correspondence2MappingMap.put(correspondence, mapping);
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
		
		return filterType(getAllCorrespondences(eObject), SameTypeCorrespondence.class)
				.filter(it -> mapping.equals(getMappingForCorrespondence(it)))
				.findFirst()
				.orElse(null);
	}
	
	/**
	 * Returns the MIRMappingRealization that created a correspondence, or <code>null</code>,
	 * if no mapping is coupled to the correspondence. To get all MIRMappingRealizations for an
	 * EObject, first get all correspondences from the {@link CorrespondenceInstance},
	 * then use this method.
	 */
	public MIRMappingRealization getMappingForCorrespondence(SameTypeCorrespondence correspondence) {
		return correspondence2MappingMap.get(correspondence);
	}
	
	/**
	 * Register a mapping for a correspondence that can then be retrieved by calling
	 * {@link #getMappingForCorrespondence(Correspondence)}.
	 * @param mapping
	 * @param correspondence
	 */
	public void registerMappingForCorrespondence(MIRMappingRealization mapping,
			SameTypeCorrespondence correspondence) {
		correspondence2MappingMap.put(correspondence, mapping);
	}
	
	public SameTypeCorrespondence createMappedCorrespondence(EObject eObjectA, EObject eObjectB, MIRMappingRealization mappingRealization) {
		SameTypeCorrespondence stc = createAndAddEObjectCorrespondence(eObjectA, eObjectB);
		registerMappingForCorrespondence(mappingRealization, stc);
		return stc;
	}
	
	public void unregisterMappingForCorrespondence(MIRMappingRealization mapping,
			SameTypeCorrespondence correspondence) {
		if (correspondence2MappingMap.get(correspondence) != mapping) {
			throw new IllegalArgumentException("Mapping " + mapping.getMappingID() + " is not registered for correspondence " + correspondence.toString());
		} else {
			correspondence2MappingMap.remove(correspondence);
		}
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
		return filterType(correspondenceInstance.getAllCorrespondences(eObject), SameTypeCorrespondence.class)
			.filter(it -> mapping.equals(getMappingForCorrespondence(it)))
			.findFirst()
			.map(it -> getCorrespondenceTarget(eObject, it).getSecond())
			.orElse(null);
	}
	
	/**
	 * Checks if the given mapping maps <code>eObject</code>.
	 * @param eObject the {@link EObject} to check
	 * @param correspondenceInstance
	 * @param mapping
	 * @return <code>true</code> if this mapping maps <code>eObject</code>
	 */
	public boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping) {
		return (getMappingTarget(eObject, mapping) != null);
	}
}
