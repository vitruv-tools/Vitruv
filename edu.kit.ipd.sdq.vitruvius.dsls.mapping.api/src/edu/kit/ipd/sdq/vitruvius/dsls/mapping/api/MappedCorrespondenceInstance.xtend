package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractDelegatingCorrespondenceInstanceDecorator
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MappingRealization
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.MappingHelper

class MappedCorrespondenceInstance extends AbstractDelegatingCorrespondenceInstanceDecorator<HashMap<Correspondence, Collection<String>>> {
	HashMap<Correspondence, Collection<String>> correspondence2MappingMap

	@SuppressWarnings("unchecked")
	new(CorrespondenceInstanceDecorator correspondenceInstance) {
		// this seems to be the only way to provide the correct instance of the
		// map class to the ADCID
		super(correspondenceInstance,
			new HashMap<Correspondence, MappingRealization>().
				getClass() as Class<HashMap<Correspondence, Collection<String>>>)
		this.correspondence2MappingMap = new HashMap<Correspondence, Collection<String>>()
	}

	override protected String getDecoratorFileExtPrefix() {
		return MappingHelper::getCorrespondenceDecoratorFileExtPrefix()
	}

	override protected HashMap<Correspondence, Collection<String>> getDecoratorObject() {
		return this.correspondence2MappingMap
	}

	override protected void initializeFromDecoratorObject(
		HashMap<Correspondence, Collection<String>> object) {
		this.correspondence2MappingMap = object
	}

	override protected void initializeWithoutDecoratorObject() {
		// empty
	}

	def deleteNonExistantCorrespondencesFromMap() {
		val allContents = resource.allContents.toSet
		val nonExistantCorrespondences = correspondence2MappingMap.keySet.filter[ it | !allContents.contains(it) ].toSet
		
		if (!nonExistantCorrespondences.empty) {
			println("DELETED CORRESPONDENCES: " + nonExistantCorrespondences.toString)
		}
		
		nonExistantCorrespondences.forEach[correspondence2MappingMap.remove(it)]
	}
	
	/** 
	 * Register a mapping for a correspondence that can then be retrieved by
	 * calling {@link #getMappingsForCorrespondence(Correspondence)}.
	 * @param correspondence
	 * @param mapping
	 */
	def void registerMappingForCorrespondence(Correspondence correspondence, MappingRealization mapping) {
		deleteNonExistantCorrespondencesFromMap
		// FIXME MK (deco): store mapping realization automatically
		if (!this.correspondence2MappingMap.containsKey(correspondence))
			this.correspondence2MappingMap.put(correspondence, new HashSet())
		this.correspondence2MappingMap.get(correspondence).add(mapping.mappingID)
	}
	

	def Correspondence getMappedCorrespondence(List<EObject> eObjects, MappingRealization mapping) {
		deleteNonExistantCorrespondencesFromMap
		return getCorrespondences(eObjects).filter(Correspondence).filter[mappingsForCorrespondence.contains(mapping.mappingID)].
			head
	}
	
	def Correspondence getMappedCorrespondence(List<EObject> eObjects, List<Correspondence> correspondences, MappingRealization mapping) {
		deleteNonExistantCorrespondencesFromMap
		return getCorrespondences(eObjects).filter(Correspondence).filter[mappingsForCorrespondence.contains(mapping.mappingID) && correspondences.equals(it.dependsOn)].
			head
	}

	/** 
	 * Returns the MappingRealization that created a correspondence, or
	 * <code>null</code>, if no mapping is coupled to the correspondence. To get
	 * all MappingRealizations for an EObject, first get all correspondences
	 * from the {@link CorrespondenceInstance}, then use this method.
	 */
	def Collection<String> getMappingsForCorrespondence(Correspondence correspondence) {
		deleteNonExistantCorrespondencesFromMap
		return correspondence2MappingMap.get(correspondence)
	}

	/** 
	 * Returns all Correspondences that correspond to a mapping. 
	 */
	def Set<Correspondence> getCorrespondencesForMapping(MappingRealization mapping) {
		deleteNonExistantCorrespondencesFromMap
		return correspondence2MappingMap.entrySet().filter[value.contains(mapping.mappingID)].map[key].toSet
	}

	def void unregisterMappingForCorrespondence(MappingRealization mapping, Correspondence correspondence) {
		deleteNonExistantCorrespondencesFromMap
		if (!correspondence2MappingMap.containsKey(correspondence) || !correspondence2MappingMap.get(correspondence).contains(mapping.mappingID)) {
			throw new IllegalArgumentException(
				'''Mapping «mapping.mappingID» is not registered for correspondence «correspondence.toString()»'''.
					toString)
		} else {
			correspondence2MappingMap.get(correspondence).remove(mapping.mappingID)
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
	def List<EObject> getMappingTarget(List<EObject> eObjects, MappingRealization mapping) {
		return correspondenceInstance.getCorrespondences(eObjects)
			.filter(Correspondence)
			.filter[mappingsForCorrespondence.contains(mapping.mappingID)]
			.head?.getOpposite(eObjects)
	}
	
	def static List<EObject> getOpposite(Correspondence correspondence, List<EObject> objects) {
		val ^as = correspondence.^as
		val bs = correspondence.bs
		
		if (objects == ^as)
			return bs
		else if (objects == bs)
			return ^as
		else
			throw new IllegalArgumentException('''The given List<EObject> («objects.toString») is not on one side of correspondence «correspondence.toString»''')
	}
	
	

	/** 
	 * Checks if the given mapping maps <code>eObject</code>.
	 * @param eObject the {@link EObject} to check
	 * @param correspondenceInstance
	 * @param mapping
	 * @return <code>true</code> if this mapping maps <code>eObject</code>
	 */
	def boolean checkIfMappedBy(List<EObject> eObjects, MappingRealization mapping) {
		return ( getMappingTarget(eObjects, mapping) !== null)
	}
	
	/**
	 * Gets the side of a correspondence for the given metamodel nsURI.
	 * @param correspondence the correspondence for which the correct side should be chosen
	 * @param mmNsUri the namespace URI for the requested side
	 * 
	 * @throws IllegalArgumentException if the <code>mmNsUri</code> is not the namespace URI of one of the mapped
	 * meta models. 
	 */
	def List<EObject> getObjectsForMetamodel(Correspondence correspondence, String mmNsUri) {
	 	if (mapping.metamodelA.nsURIs.contains(mmNsUri)) {
	 		return correspondence.^as
	 	} else if (mapping.metamodelA.nsURIs.contains(mmNsUri)) {
	 		return correspondence.bs
	 	} else {
	 		throw new IllegalArgumentException('''Metamodel namespace URI "«mmNsUri»" is not a namespace URI of one of the metamodels for the associated mapping''')
	 	}
	 }
}