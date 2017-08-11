package tools.vitruv.framework.versioning.common.impl

import com.google.gson.JsonObject
import com.google.gson.JsonParser

import java.lang.reflect.Constructor
import java.util.List

import org.eclipse.emf.common.notify.impl.NotificationChainImpl
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl

import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.impl.PropagatedChangeImpl
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import com.google.gson.JsonArray
import tools.vitruv.framework.versioning.common.EChangeSerializer

class EChangeSerializerImpl implements EChangeSerializer {
	static val ResourceSet resourceSet = new ResourceSetImpl
	static extension TypeInferringUnresolvingCompoundEChangeFactory = TypeInferringUnresolvingCompoundEChangeFactory::
		instance
	static extension TypeInferringUnresolvingAtomicEChangeFactory = TypeInferringUnresolvingAtomicEChangeFactory::
		instance
	static Resource currentResource
	static extension JsonParser = new JsonParser

	static def EChangeSerializer init() {
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("allelementtypes", new XMIResourceFactoryImpl)
		new EChangeSerializerImpl
	}

	private static def EObject createEObject(String className) {
		val Class<?> clazz = Class.forName(className)
		val Constructor<?> constructor = clazz.declaredConstructor
		constructor.accessible = true
		val object = constructor.newInstance as InternalEObject
		object.eSetResource(currentResource as Resource.Internal, new NotificationChainImpl)
		return object
	}

	private static def EObject createEObject(String className, String eProxyURIString) {
		val object = createEObject(className) as InternalEObject
		val eProxyURI = URI::createURI(eProxyURIString)
		object.eSetProxyURI(eProxyURI)
		return object
	}

	private static def EAttribute createFeature(EObject eObject, JsonObject jsonObject) {
		val featureName = jsonObject.get("name").asString
		val feature = eObject.allFeaturesIterator.filter(EAttribute).findFirst[name == featureName]
		if (null ===
			feature)
			throw new IllegalStateException('''No feature with name  «featureName» in «eObject» of «eObject.eClass.name»''')
		return feature
	}

	private static def String serializeFeature(EStructuralFeature eAttribute) '''
		{
			"class": "«eAttribute.class.name»",
			"lowerBound": "«eAttribute.lowerBound»",
			"name": "«eAttribute.name»",
			"type": "feature"
		}
	'''

	private static def EList<EStructuralFeature> getAllFeaturesIterator(EObject eObject) {
		eObject.eClass.EAllStructuralFeatures
	}

	private static def EReference createReference(EObject eObject, JsonObject jsonObject) {
		val referenceName = jsonObject.get("name").asString
		val reference = eObject.allFeaturesIterator.filter(EReference).findFirst[name == referenceName]
		if (null ===
			reference)
			throw new IllegalStateException('''No reference with name  «referenceName» in «eObject» of «eObject.eClass.name»''')
		return reference
	}

	private static def PropagatedChange deserializeIntern(JsonObject jobject) {
		val id = jobject.get("id").toString
		val originalChange = jobject.get("originalChange").asJsonObject.deserializeVitruviusChange
		val consequentialChanges = jobject.get("consequentialChanges").asJsonObject.deserializeVitruviusChange
		return new PropagatedChangeImpl(id, originalChange, consequentialChanges)
	}

	private new() {
	}

	override serialize(PropagatedChange change) '''
		{
			"type": "«change.class.name»",
			"id": "«change.id»",
			"originalChange": «change.originalChange.serializeVitruviusChange»,
			"consequentialChanges": «change.consequentialChanges.serializeVitruviusChange»
		}
	'''

	override deserialize(String changeString) {
		val jobject = parse(changeString).asJsonObject
		return deserializeIntern(jobject)
	}

	override deserializeAll(String allChangeString) {
		val jsonArray = parse(allChangeString).asJsonArray
		return deserializeAll(jsonArray)
	}
	override deserializeAll(JsonArray jsonArray) {
		return jsonArray.map[deserializeIntern(it as JsonObject)].toList
	}

	override serializeAll(List<PropagatedChange> changes) {
		val strings = changes.map[serialize]
		return '''[
			«FOR s : strings  SEPARATOR ', ' »
				«s»
	    	«ENDFOR»
	    	]
		'''
	}

	private static def dispatch String serializeVitruviusChange(VitruviusChange vitruviusChange) {
		null
	}

	private static def dispatch String serializeEChange(EChange eChange) {
		throw new UnsupportedOperationException('''EChange type «eChange.class.name» not (yet) supported ''')
	}

	private static def EChange deserializeEChange(JsonObject jobject) {
		val type = jobject.get("type")?.asString
		var EChange change = null
		if (type == CreateAndInsertRoot.name) {
			val className = jobject.get("createdObject").asString
			val object = createEObject(className)
			val index = jobject.get("index").asInt
			val resource = resourceSet.createResource(URI::createURI(jobject.get("uri").asString))
			resource.contents.add(object)
			resource.save(null)
			currentResource = resource
			change = createCreateAndInsertRootChange(object, resource, index)
		}
		if (type == ReplaceSingleValuedEAttribute.name) {
			val className = jobject.get("affectedEObject").asString
			val eProxyURIString = jobject.get("eProxyURI").asString
			val featureObject = jobject.get("feature").asJsonObject
			val newValue = jobject.get("newValue").asString
			val oldValue = jobject.get("oldValue").asString
			
			val affectedObject = createEObject(className, eProxyURIString)
			val feature = createFeature(affectedObject, featureObject)
			var String realOldValue = null 
			if ("" != oldValue) {
				realOldValue =  oldValue
			}
			change = createReplaceSingleAttributeChange(
				affectedObject,
				feature,
				realOldValue,
				newValue
			)
		}
		if (type == CreateAndReplaceNonRoot.name) {
			val className = jobject.get("createdObject").asString
			val affectedEObjectClassName = jobject.get("affectedEObject").asString
			val eProxyURI = jobject.get("eProxyURI").asString
			val featureObject = jobject.get("feature").asJsonObject

			val createdObject = createEObject(className)
			val affectedEObject = createEObject(affectedEObjectClassName, eProxyURI)
			val feature = createReference(affectedEObject, featureObject)
			change = createCreateAndReplaceNonRootChange(affectedEObject, feature, createdObject)
		}
		if (type == CreateAndInsertNonRoot.name) {
			val affectedEObjectClassName = jobject.get("affectedEObject").asString
			val affectedEObjectEProxyURI = jobject.get("eProxyURI").asString
			val createdObjectClassName = jobject.get("createdObject").asString
			val featureObject = jobject.get("feature").asJsonObject
			val index = jobject.get("index").asInt

			val createdObject = createEObject(createdObjectClassName)
			val affectedEObject = createEObject(affectedEObjectClassName, affectedEObjectEProxyURI)
			val feature = createReference(affectedEObject, featureObject)
			change = createCreateAndInsertNonRootChange(affectedEObject, feature, createdObject, index)
		}
		return change
	}

	private static def dispatch String serializeEChange(CreateAndInsertNonRoot<?, ?> createAndInsertNonRoot) '''
		{
			"affectedEObject": "«createAndInsertNonRoot.insertChange.affectedEObject.class.name»",
			"createdObject": "«createAndInsertNonRoot.createChange.affectedEObject.class.name»",
			"eProxyURI": "«(createAndInsertNonRoot.insertChange.affectedEObject as InternalEObject).eProxyURI.toString»",
			"feature":«createAndInsertNonRoot.insertChange.affectedFeature.serializeFeature»,
			"index": "«createAndInsertNonRoot.insertChange.index»",
			"type": "«CreateAndInsertNonRoot.name»"
		}
	'''

	private static def dispatch String serializeEChange(CreateAndReplaceNonRoot<?, ?> createAndReplaceNonRoot) '''
		{
			"affectedEObject": "«createAndReplaceNonRoot.insertChange.affectedEObject.class.name»",
			"createdObject": "«createAndReplaceNonRoot.createChange.affectedEObject.class.name»",
			"eProxyURI": "«(createAndReplaceNonRoot.insertChange.affectedEObject as InternalEObject).eProxyURI.toString»",
			"feature":«createAndReplaceNonRoot.insertChange.affectedFeature.serializeFeature»,
			"type": "«CreateAndReplaceNonRoot.name»"
		}
	'''

	private static def dispatch String serializeEChange(
		ReplaceSingleValuedEAttribute<?, ?> replaceSingleValuedEAttribute
	) {
		return '''
			{
				"affectedEObject": "«replaceSingleValuedEAttribute.affectedEObject.class.name»",
				"eProxyURI": "«(replaceSingleValuedEAttribute.affectedEObject as InternalEObject).eProxyURI.toString»",
				"feature":«replaceSingleValuedEAttribute.affectedFeature.serializeFeature»,
				"newValue": "«replaceSingleValuedEAttribute.newValue»",
				"oldValue": "«replaceSingleValuedEAttribute.oldValue»",
				"type": "«ReplaceSingleValuedEAttribute.name»"
			}
		'''
	}

	private static def dispatch String serializeEChange(CreateAndInsertRoot<?> createAndInsertRoot) '''
		{
			"createdObject": "«createAndInsertRoot.createChange.affectedEObject.class.name»",
			"index": "«createAndInsertRoot.insertChange.index»",
			"type": "«CreateAndInsertRoot.name»",
			"uri": "«createAndInsertRoot.insertChange.uri»"
		}
	'''

	private static def dispatch String serializeVitruviusChange(EMFModelChangeImpl vitruviusChange) '''
		{
			"type": "«vitruviusChange.class.name»",
			"vuri": "«vitruviusChange.URI.EMFUri.toString»",
			"echanges": [
				«FOR echange : vitruviusChange.EChanges SEPARATOR ','»
					«echange.serializeEChange»
				«ENDFOR»
			]
		}
	'''

	private static def dispatch String serializeVitruviusChange(CompositeContainerChangeImpl containerChange) '''
		{
			"type": "«containerChange.class.name»",
			"changes": [
				«FOR change : containerChange.changes SEPARATOR ','»
					«change.serializeVitruviusChange»
				«ENDFOR»
			]
		}
	'''

	private static def VitruviusChange deserializeVitruviusChange(JsonObject jobject) {
		if (null !== jobject.get("echanges"))
			return jobject.deserializeEMFModelChange
		if (null !== jobject.get("changes"))
			return jobject.deserializeComposite
	}

	private static def EMFModelChangeImpl deserializeEMFModelChange(JsonObject jobject) {
		val echanges = jobject.get("echanges").asJsonArray.filterNull.map [ jsonElement |
			(jsonElement as JsonObject).deserializeEChange
		].filterNull.toList
		return new EMFModelChangeImpl(echanges)
	}

	private static def CompositeContainerChangeImpl deserializeComposite(JsonObject jobject) {
		val changes = jobject.get("changes").asJsonArray.filterNull.map [ jsonElement |
			(jsonElement as JsonObject).deserializeEMFModelChange
		].filterNull.toList
		val composite = new CompositeContainerChangeImpl
		changes.forEach[composite.addChange(it)]
		return composite
	}

}
