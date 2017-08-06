package tools.vitruv.framework.versioning.extensions.impl

import com.google.gson.JsonParser
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.change.description.impl.PropagatedChangeImpl
import tools.vitruv.framework.versioning.extensions.EChangeSerializer
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import java.lang.reflect.Constructor
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.impl.CompositeContainerChangeImpl
import tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertRootImpl
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.InternalEObject
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceNonRootImpl
import org.eclipse.emf.ecore.EReference
import com.google.gson.JsonObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.notify.impl.NotificationChainImpl

class EChangeSerializerImpl implements EChangeSerializer {
	static val ResourceSet resourceSet = new ResourceSetImpl
	static extension TypeInferringUnresolvingCompoundEChangeFactory = TypeInferringUnresolvingCompoundEChangeFactory::
		instance
	static extension TypeInferringUnresolvingAtomicEChangeFactory = TypeInferringUnresolvingAtomicEChangeFactory::
		instance
	static Resource currentResource
	extension JsonParser = new JsonParser()

//	static extension Gson = new Gson
	static def EChangeSerializer init() {
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("allelementtypes", new XMIResourceFactoryImpl)
		new EChangeSerializerImpl
	}

	private static def boolean set(Object object, String fieldName, Object fieldValue) {
		var Class<?> clazz = object.getClass();
		while (clazz !== null) {
			try {
				val field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, fieldValue);
				return true;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return false;
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
		val jelement = parse(changeString)
		val jobject = jelement.asJsonObject
		val id = jobject.get("id").toString
		val originalChange = jobject.get("originalChange").asJsonObject.deserializeVitruviusChange
		val consequentialChanges = jobject.get("consequentialChanges").asJsonObject.deserializeVitruviusChange
		return new PropagatedChangeImpl(id, originalChange, consequentialChanges)

	}

	private static def dispatch String serializeVitruviusChange(VitruviusChange vitruviusChange) {
		null
	}

	private static def dispatch String serializeEChange(EChange eChange) '''{}'''

	private static def EChange deserializeEChange(JsonObject jobject) {
		val type = jobject.get("type")?.asString
		var EChange change = null
		if (type == CreateAndInsertRootImpl.name) {
			val className = jobject.get("createdObject").asString
			val object = createEObject(className)
			val index = jobject.get("index").asInt
			val resource = resourceSet.createResource(URI::createURI(jobject.get("uri").asString))
			resource.contents.add(object)
			resource.save(null)
			currentResource = resource
			change = createCreateAndInsertRootChange(object, resource, index)
		}
		if (type == ReplaceSingleValuedEAttributeImpl.name) {
			val className = jobject.get("affectedEObject").asString
			val eProxyURIString = jobject.get("eProxyURI").asString
			val featureObject = jobject.get("feature").asJsonObject
			val newValue = jobject.get("newValue").asString
			val oldValue = jobject.get("oldValue").asString

			val affectedObject = createEObject(className, eProxyURIString)
			val feature = createFeature(affectedObject, featureObject)
			change = createReplaceSingleAttributeChange(
				affectedObject,
				feature,
				if ("" == oldValue) null else oldValue,
				newValue
			)
		}
		if (type == CreateAndReplaceNonRootImpl.name) {
			val className = jobject.get("createdObject").asString
			val affectedEObjectClassName = jobject.get("affectedEObject").asString
			val eProxyURI = jobject.get("eProxyURI").asString
			val featureObject = jobject.get("feature").asJsonObject

			val createdObject = createEObject(className)
			val affectedEObject = createEObject(affectedEObjectClassName, eProxyURI)
			val feature = createReference(affectedEObject, featureObject)
			change = createCreateAndReplaceNonRootChange(affectedEObject, feature, createdObject)
		}
		return change
	}

	private static def dispatch String serializeEChange(CreateAndReplaceNonRoot<?, ?> createAndReplaceNonRoot) '''
		{
			"affectedEObject": "«createAndReplaceNonRoot.insertChange.affectedEObject.class.name»",
			"createdObject": "«createAndReplaceNonRoot.createChange.affectedEObject.class.name»",
			"eProxyURI": "«(createAndReplaceNonRoot.insertChange.affectedEObject as InternalEObject).eProxyURI.toString»",
			"feature":«createAndReplaceNonRoot.insertChange.affectedFeature.serializeFeature»,
			"type": "«CreateAndReplaceNonRootImpl.name»"
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
				"type": "«ReplaceSingleValuedEAttributeImpl.name»"
			}
		'''
	}

	private static def dispatch String serializeEChange(CreateAndInsertRoot<?> createAndInsertRoot) '''
		{
			"createdObject": "«createAndInsertRoot.createChange.affectedEObject.class.name»",
			"index": "«createAndInsertRoot.insertChange.index»",
			"type": "«CreateAndInsertRootImpl.name»",
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
			(jsonElement as JsonObject).deserializeEMFModelChange as VitruviusChange
		].filterNull.toList
		val composite = new CompositeContainerChangeImpl
		changes.forEach[composite.addChange(it)]
		return composite
	}
}
