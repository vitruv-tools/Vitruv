//package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api;
//
//import static edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.filterType;
//import static edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.requireType;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.eclipse.emf.ecore.EObject;
//
//import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractDelegatingCorrespondenceInstanceDecorator;
//import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
//import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
//import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRHelper;
//import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
//
//public class MappedCorrespondenceInstance extends
//		AbstractDelegatingCorrespondenceInstanceDecorator<Map<Correspondence, Collection<MIRMappingRealization>>> {
//	private Map<Correspondence, Collection<MIRMappingRealization>> correspondence2MappingMap;
//
//	@SuppressWarnings("unchecked")
//	public MappedCorrespondenceInstance(CorrespondenceInstanceDecorator correspondenceInstance) {
//		// this seems to be the only way to provide the correct instance of the
//		// map class to the ADCID
//		super(correspondenceInstance,
//				(Class<Map<Correspondence, Collection<MIRMappingRealization>>>) new HashMap<Correspondence, MIRMappingRealization>()
//						.getClass());
//		this.correspondence2MappingMap = new HashMap<Correspondence, Collection<MIRMappingRealization>>();
//	}
//
//	@Override
//	protected String getDecoratorFileExtPrefix() {
//		return MIRHelper.getCorrespondenceDecoratorFileExtPrefix();
//	}
//
//	@Override
//	protected Map<Correspondence, Collection<MIRMappingRealization>> getDecoratorObject() {
//		return this.correspondence2MappingMap;
//	}
//
//	@Override
//	protected void initializeFromDecoratorObject(
//			Map<Correspondence, Collection<MIRMappingRealization>> object) {
//		this.correspondence2MappingMap = object;
//	}
//
//	@Override
//	protected void initializeWithoutDecoratorObject() {
//		// empty
//	}
//
//	/**
//	 * Register a mapping for a correspondence that can then be retrieved by
//	 * calling {@link #getMappingsForCorrespondence(Correspondence)}.
//	 * 
//	 * @param correspondence
//	 * @param mapping
//	 */
//	public void registerMappingForCorrespondence(Correspondence correspondence, MIRMappingRealization mapping) {
//		// FIXME MK (deco): store mapping realization automatically
//		if (!this.correspondence2MappingMap.containsKey(correspondence))
//			this.correspondence2MappingMap.put(correspondence, new HashSet<>());
//
//		this.correspondence2MappingMap.get(correspondence).add(mapping);
//	}
//
//	// TODO: clean up methods copied from
//	// edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappedCorrespondenceInstance
//	public TUID getCorrespondenceTarget(EObject eObject, Correspondence correspondence) {
//		Correspondence sameTypeCorrespondence = requireType(correspondence, Correspondence.class);
//		TUID tuid = calculateTUIDsFromEObjects(null).get(0);
//
//		TUID elementATUID = sameTypeCorrespondence.getElementATUID();
//		TUID elementBTUID = sameTypeCorrespondence.getElementBTUID();
//
//		// return the other side of the correspondence
//		if (tuid.equals(elementATUID)) {
//			return elementBTUID;
//		}
//		if (tuid.equals(elementBTUID)) {
//			return elementATUID;
//		}
//
//		throw new IllegalArgumentException(
//				eObject.toString() + " is not part of correspondence " + correspondence.toString());
//	}
//
//	public Correspondence getMappedCorrespondence(EObject eObject, MIRMappingRealization mapping) {
//
//		return filterType(getAllCorrespondences(eObject), Correspondence.class)
//				.filter(it -> getMappingsForCorrespondence(it).contains(mapping)).findFirst().orElse(null);
//	}
//
//	/**
//	 * Returns the MIRMappingRealization that created a correspondence, or
//	 * <code>null</code>, if no mapping is coupled to the correspondence. To get
//	 * all MIRMappingRealizations for an EObject, first get all correspondences
//	 * from the {@link CorrespondenceInstance}, then use this method.
//	 */
//	public Collection<MIRMappingRealization> getMappingsForCorrespondence(Correspondence correspondence) {
//		return correspondence2MappingMap.get(correspondence);
//	}
//
//	/**
//	 * Returns all Correspondences that correspond to a mapping. 
//	 */
//	public Set<Correspondence> getCorrespondencesForMapping(MIRMappingRealization mapping) {
//		return correspondence2MappingMap.entrySet().stream().filter(it -> it.getValue().contains(mapping))
//				.map(it -> it.getKey()).collect(Collectors.toSet());
//	}
//
//	public void unregisterMappingForCorrespondence(MIRMappingRealization mapping,
//			Correspondence correspondence) {
//		if (correspondence2MappingMap.get(correspondence) != mapping) {
//			throw new IllegalArgumentException("Mapping " + mapping.getMappingID()
//					+ " is not registered for correspondence " + correspondence.toString());
//		} else {
//			correspondence2MappingMap.remove(correspondence);
//		}
//	}
//
//	/**
//	 * Checks if the given mapping maps <code>eObject</code> and returns the
//	 * target.
//	 * 
//	 * @param eObject
//	 *            the {@link EObject} to check
//	 * @param correspondenceInstance
//	 * @param mapping
//	 * @return The target of the mapping if this mapping maps
//	 *         <code>eObject</code>, <code>null</code> otherwise.
//	 */
//	public EObject getMappingTarget(EObject eObject, MIRMappingRealization mapping) {
//		return filterType(correspondenceInstance.getAllCorrespondences(eObject), Correspondence.class)
//				.filter(it -> getMappingsForCorrespondence(it).contains(mapping)).findFirst()
//				.map(it -> resolveEObjectFromTUID(getCorrespondenceTarget(eObject, it))).orElse(null);
//	}
//
//	/**
//	 * Checks if the given mapping maps <code>eObject</code>.
//	 * 
//	 * @param eObject
//	 *            the {@link EObject} to check
//	 * @param correspondenceInstance
//	 * @param mapping
//	 * @return <code>true</code> if this mapping maps <code>eObject</code>
//	 */
//	public boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping) {
//		return (getMappingTarget(eObject, mapping) != null);
//	}
//}
