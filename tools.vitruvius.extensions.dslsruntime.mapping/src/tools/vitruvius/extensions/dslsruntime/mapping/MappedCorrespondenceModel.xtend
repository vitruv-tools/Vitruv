package tools.vitruvius.extensions.dslsruntime.mapping

import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import tools.vitruvius.framework.correspondence.Correspondence
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.MappingRealization
import java.util.function.Supplier
import tools.vitruvius.framework.correspondence.CorrespondenceModel
import org.eclipse.xtend.lib.annotations.Delegate

class MappedCorrespondenceModel implements CorrespondenceModel {
	@Delegate
	val CorrespondenceModel correspondenceModel;

	HashMap<Correspondence, Collection<String>> correspondence2MappingMap

	// TODO DW This class has to be instantiated somewhere. It was registered as an extension before,
	// but now it can only work as a utility class, so the map has to be extracted manually (or better the
	// correspondence elements have to be extended to MappingCorrespondence elements containing a list
	// of mappings they belong to
	@SuppressWarnings("unchecked")
	new(CorrespondenceModel correspondenceModel) {
		this.correspondenceModel = correspondenceModel;
		this.correspondence2MappingMap = new HashMap<Correspondence, Collection<String>>()
	}

	def deleteNonExistantCorrespondencesFromMap() {
		val allContents = correspondenceModel.resource.allContents.toSet
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
		return correspondenceModel.getCorrespondences(eObjects).filter(Correspondence).filter[mappingsForCorrespondence.contains(mapping.mappingID)].
			head
	}
	
	def Correspondence getMappedCorrespondence(List<EObject> eObjects, List<Correspondence> correspondences, MappingRealization mapping) {
		deleteNonExistantCorrespondencesFromMap
		return correspondenceModel.getCorrespondences(eObjects).filter(Correspondence).filter[mappingsForCorrespondence.contains(mapping.mappingID) && correspondences.equals(it.dependsOn)].
			head
	}

	/** 
	 * Returns the MappingRealization that created a correspondence, or
	 * <code>null</code>, if no mapping is coupled to the correspondence. To get
	 * all MappingRealizations for an EObject, first get all correspondences
	 * from the {@link CorrespondenceModel}, then use this method.
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
	 * @param correspondenceModel
	 * @param mapping
	 * @return The target of the mapping if this mapping maps
	 * <code>eObject</code>, <code>null</code> otherwise.
	 */
	def List<EObject> getMappingTarget(List<EObject> eObjects, MappingRealization mapping) {
		return correspondenceModel.getCorrespondences(eObjects)
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
	 * @param correspondenceModel
	 * @param mapping
	 * @return <code>true</code> if this mapping maps <code>eObject</code>
	 */
	def boolean checkIfMappedBy(List<EObject> eObjects, MappingRealization mapping) {
		return ( getMappingTarget(eObjects, mapping) !== null)
	}
	
	override createAndAddCorrespondence(List<EObject> eObjects1, List<EObject> eObjects2, Supplier<Correspondence> correspondenceCreator) {
		correspondenceModel.createAndAddCorrespondence(eObjects1, eObjects2, correspondenceCreator);
	}
	
	override <U extends Correspondence> getEditableView(Class<U> correspondenceType, Supplier<U> correspondenceCreator) {
		correspondenceModel.getEditableView(correspondenceType, correspondenceCreator);
	}
	
}