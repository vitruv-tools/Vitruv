package tools.vitruv.framework.change.description.impl

import java.util.HashMap
import java.util.Map
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.EChangeCopier
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertNonRootImpl
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver

class EChangeCopierImpl implements EChangeCopier {
	static val logger = Logger::getLogger(EChangeCopierImpl)
	val URI source
	val URI target
	val Map<URI, EObject> objectsAlreadyThere

	new(URI source, URI target, Map<URI, EObject> objectsAlreadyThere) {
		this.source = source
		this.target = target
		this.objectsAlreadyThere = new HashMap
		objectsAlreadyThere.entrySet.stream.forEach[this.objectsAlreadyThere.put(key, value)]
//		if (this.objectsAlreadyThere.size !==
//			objectsAlreadyThere.
//				size) {
//					throw new IllegalStateException('''this.objectsAlreadyThere should have size «objectsAlreadyThere.size» but has «this.objectsAlreadyThere.size»''')
//				}
//			}
	}

	override copyEMFModelChange(EMFModelChangeImpl changeToCopy, VURI vuri) {
		val oldEChanges = changeToCopy.EChanges
		val echanges = oldEChanges.map[copyThisShit].filterNull
		val change = new EMFModelChangeImpl(echanges, vuri)
		return change
	}

	private dispatch def EChange copyThisShit(EChange e) {
		logger.debug("EChange copyThisShit(EChange e)")
		logger.debug(e)
		EcoreUtil::copy(e)
	}

	private dispatch def CreateAndInsertNonRoot<?, ?> copyThisShit(
		CreateAndInsertNonRootImpl<?, ?> createAndInsertNonRoot) {
		val affectedEObject = createAndInsertNonRoot.insertChange.affectedEObject as InternalEObject
		val InternalEObject newAffectedEObject = adjust(affectedEObject)
		val affectedFeature = EcoreUtil::copy(createAndInsertNonRoot.insertChange.affectedFeature)
		val newValue = createAndInsertNonRoot.insertChange.newValue
		val index = createAndInsertNonRoot.insertChange.index
		val change = TypeInferringUnresolvingCompoundEChangeFactory::instance.
			createCreateAndInsertNonRootChange(newAffectedEObject, affectedFeature, newValue, index)
		return change
	}

	private dispatch def CreateAndReplaceNonRoot<?, ?> copyThisShit(
		CreateAndReplaceNonRoot<?, ?> createAndReplaceNonRoot) {
		val affectedEObject = createAndReplaceNonRoot.insertChange.affectedEObject as InternalEObject
		val InternalEObject newAffectedEObject = EChangeUnresolver::createProxy(adjust(affectedEObject))
		val affectedFeature = createAndReplaceNonRoot.insertChange.affectedFeature
		val newValue = EcoreUtil::copy(createAndReplaceNonRoot.insertChange.newValue)
		val change = TypeInferringUnresolvingCompoundEChangeFactory::instance.
			createCreateAndReplaceNonRootChange(newAffectedEObject, affectedFeature, newValue)
		val testNewValue = change.insertChange.newValue as InternalEObject
		val proxyURI = URI::createURI(source.toString + testNewValue.eProxyURI.toString)
		objectsAlreadyThere.put(proxyURI, testNewValue)
		return change
	}

	private dispatch def ReplaceSingleValuedEAttribute<?, ?> copyThisShit(
		ReplaceSingleValuedEAttribute<?, ?> replaceSingleValuedEAttribute) {
		val affectedEObject = replaceSingleValuedEAttribute.affectedEObject as InternalEObject
		val InternalEObject newAffectedEObject = adjust(affectedEObject)
		val affectedAttribute = EcoreUtil::copy(replaceSingleValuedEAttribute.affectedFeature)
		val oldValue = replaceSingleValuedEAttribute.oldValue
		val newValue = replaceSingleValuedEAttribute.newValue
		TypeInferringUnresolvingAtomicEChangeFactory::instance.createReplaceSingleAttributeChange(
			newAffectedEObject,
			affectedAttribute,
			oldValue,
			newValue
		)
		return null
	}

	private def InternalEObject adjust(InternalEObject affectedEObject) {
		val proxyUri = affectedEObject.eProxyURI
		val proxyUriString = proxyUri.toString
		val sourceUriString = source.toString
		val containsSource = proxyUriString.contains(sourceUriString)

		var InternalEObject newAffectedEObject = null
		if (containsSource) {
			val targetURIString = target.toString
			val newProxyUriString = proxyUriString.replace(sourceUriString, targetURIString)
			val newProxyUri = URI::createURI(newProxyUriString)
			val contains = objectsAlreadyThere.containsKey(newProxyUri)
			if (contains) {
				newAffectedEObject = objectsAlreadyThere.get(newProxyUri) as InternalEObject
			} else {
				newAffectedEObject = EcoreUtil::copy(affectedEObject)
				newAffectedEObject.eSetProxyURI(newProxyUri)
			}
		} else {
			logger.warn('''AffectedEObject «affectedEObject» lies not under «source»''')
			newAffectedEObject = affectedEObject
		}
		return newAffectedEObject
	}

}
