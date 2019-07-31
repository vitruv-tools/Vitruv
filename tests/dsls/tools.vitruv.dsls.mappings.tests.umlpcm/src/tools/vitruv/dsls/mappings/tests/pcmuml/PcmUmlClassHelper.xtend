package tools.vitruv.dsls.mappings.tests.pcmuml

import java.util.function.Function
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsLanguageFactory
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel

class PcmUmlClassHelper {
	private val CorrespondenceModel correspondenceModel
	private val Function<URI, EObject> eObjectRetriever

	public new(CorrespondenceModel testCorrespondenceModel, Function<URI, EObject> eObjectRetriever,
		Function<URI, Resource> resourceRetriever) {
		this.correspondenceModel = testCorrespondenceModel
		this.eObjectRetriever = eObjectRetriever
	}

	def public <T extends EObject> getModifiableInstance(T original) {
		val originalURI = EcoreUtil.getURI(original)
		return eObjectRetriever.apply(originalURI) as T
	}

//	def public <T extends EObject> Set<T> getCorrSet(EObject source, Class<T> typeFilter) {
//		return correspondenceModel.getCorrespondingEObjectsByType(source, typeFilter) as Set<T>
//	}

	def public <T extends EObject> T getCorr(EObject source, Class<T> typeFilter, String tag) {
		return ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, source, tag,
			typeFilter).head
	}

//	def public <T extends EObject> Set<T> getModifiableCorrSet(EObject source, Class<T> typeFilter) {
//		return getCorrSet(source, typeFilter).map[getModifiableInstance(it)].filter[it !== null].toSet
//	}

	def public <T extends EObject> T getModifiableCorr(EObject source, Class<T> typeFilter, String tag) {
		val correspondence = getCorr(source, typeFilter, tag)
		if(correspondence === null) return null
		return getModifiableInstance(getCorr(source, typeFilter, tag))
	}
	
	def public printCorrModel(){
		correspondenceModel.allCorrespondences.forEach[
			println(it)
		]
	}


	 def public static createMappingParameter(EClass eclass, String name) {
		MappingsLanguageFactory.eINSTANCE.createMappingParameter => [
			it.value = MirBaseFactory.eINSTANCE.createNamedMetaclassReference => [
				it.name = name
				it.metaclass = eclass
			]
		]
	}

}
