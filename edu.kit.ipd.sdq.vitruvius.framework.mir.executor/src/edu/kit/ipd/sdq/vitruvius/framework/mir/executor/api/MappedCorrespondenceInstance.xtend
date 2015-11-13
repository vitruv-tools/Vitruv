package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractDelegatingCorrespondenceInstanceDecorator
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.Set
import java.util.stream.Collectors
import org.eclipse.emf.ecore.EObject

import static edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.filterType
import static edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.requireType

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

class MappedCorrespondenceInstance extends AbstractDelegatingCorrespondenceInstanceDecorator<HashMap<Correspondence, Collection<MIRMappingRealization>>> {
	HashMap<Correspondence, Collection<MIRMappingRealization>> correspondence2MappingMap

	@SuppressWarnings("unchecked")
	new(CorrespondenceInstanceDecorator correspondenceInstance) {
		// this seems to be the only way to provide the correct instance of the
		// map class to the ADCID
		super(correspondenceInstance, new HashMap<Correspondence, MIRMappingRealization>().getClass() as Class<HashMap<Correspondence, Collection<MIRMappingRealization>>>)
				this.correspondence2MappingMap = new HashMap<Correspondence, Collection<MIRMappingRealization>>()
	}

	override protected String getDecoratorFileExtPrefix() {
		return MIRHelper::getCorrespondenceDecoratorFileExtPrefix()
	}

	override protected HashMap<Correspondence, Collection<MIRMappingRealization>> getDecoratorObject() {
		return this.correspondence2MappingMap
	}

	override protected void initializeFromDecoratorObject(
		HashMap<Correspondence, Collection<MIRMappingRealization>> object) {
		this.correspondence2MappingMap = object
	}

	override protected void initializeWithoutDecoratorObject() {
		// empty
	}

	/** 
	 * Register a mapping for a correspondence that can then be retrieved by
	 * calling {@link #getMappingsForCorrespondence(Correspondence)}.
	 * @param correspondence
	 * @param mapping
	 */
	def void registerMappingForCorrespondence(Correspondence correspondence, MIRMappingRealization mapping) {
		// FIXME MK (deco): store mapping realization automatically
		if(!this.correspondence2MappingMap.containsKey(correspondence)) this.correspondence2MappingMap.put(
			correspondence, new HashSet())
		this.correspondence2MappingMap.get(correspondence).add(mapping)
	}

	// TODO: clean up methods copied from
	// edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappedCorrespondenceInstance
	def TUID getCorrespondenceTarget(EObject eObject, Correspondence correspondence) {
		var Correspondence sameTypeCorrespondence = requireType(correspondence, typeof(Correspondence))
		var TUID tuid = calculateTUIDsFromEObjects(eObject.toList).claimOne
		var TUID elementATUID = sameTypeCorrespondence.getElementATUID()
		var TUID elementBTUID = sameTypeCorrespondence.getElementBTUID()
		// return the other side of the correspondence
		if (tuid.equals(elementATUID)) {
			return elementBTUID
		}
		if (tuid.equals(elementBTUID)) {
			return elementATUID
		}
		throw new IllegalArgumentException(
			'''«eObject.toString()» is not part of correspondence «correspondence.toString()»'''.toString)
	}

	def Correspondence getMappedCorrespondence(EObject eObject, MIRMappingRealization mapping) {
		return filterType(getCorrespondences(eObject.toList), typeof(Correspondence)).filter(null).findFirst().
			orElse(null)
	}

	/** 
	 * Returns the MIRMappingRealization that created a correspondence, or
	 * <code>null</code>, if no mapping is coupled to the correspondence. To get
	 * all MIRMappingRealizations for an EObject, first get all correspondences
	 * from the {@link CorrespondenceInstance}, then use this method.
	 */
	def Collection<MIRMappingRealization> getMappingsForCorrespondence(Correspondence correspondence) {
		return correspondence2MappingMap.get(correspondence)
	}

	/** 
	 * Returns all Correspondences that correspond to a mapping. 
	 */
	def Set<Correspondence> getCorrespondencesForMapping(MIRMappingRealization mapping) {
		return correspondence2MappingMap.entrySet().stream().filter(null).map(null).collect(Collectors::toSet())
	}

	def void unregisterMappingForCorrespondence(MIRMappingRealization mapping, Correspondence correspondence) {
		if (correspondence2MappingMap.get(correspondence) !== mapping) {
			throw new IllegalArgumentException(
				'''Mapping «mapping.getMappingID()» is not registered for correspondence «correspondence.toString()»'''.
					toString)
		} else {
			correspondence2MappingMap.remove(correspondence)
		}
	}

	/** 
	 * Checks if the given mapping maps <code>eObject</code> and returns the
	 * target.
	 * @param eObjectthe {@link EObject} to check
	 * @param correspondenceInstance
	 * @param mapping
	 * @return The target of the mapping if this mapping maps
	 * <code>eObject</code>, <code>null</code> otherwise.
	 */
	def EObject getMappingTarget(EObject eObject, MIRMappingRealization mapping) {
		return filterType(correspondenceInstance.getCorrespondences(eObject.toList),
			typeof(Correspondence)).filter(null).findFirst().map(null).orElse(null)
	}

	/** 
	 * Checks if the given mapping maps <code>eObject</code>.
	 * @param eObjectthe {@link EObject} to check
	 * @param correspondenceInstance
	 * @param mapping
	 * @return <code>true</code> if this mapping maps <code>eObject</code>
	 */
	def boolean checkIfMappedBy(EObject eObject, MIRMappingRealization mapping) {
		return ( getMappingTarget(eObject, mapping) !== null)
	}

}
		