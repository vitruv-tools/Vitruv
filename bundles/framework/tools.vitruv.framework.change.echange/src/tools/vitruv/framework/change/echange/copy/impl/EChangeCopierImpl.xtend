package tools.vitruv.framework.change.echange.copy.impl

import java.util.Set

import org.apache.log4j.Level
import org.apache.log4j.Logger

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import tools.vitruv.framework.change.echange.copy.EChangeCopier
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference

class EChangeCopierImpl implements EChangeCopier {
	// Extensions.
	static extension Logger = Logger::getLogger(EChangeCopierImpl)
	// Values.
	val Set<Pair<String, String>> replacePairs
	val Set<Pair<String, String>> nameReplacePairs

	private static def getNameExtracted(String pathWithName) {
		val segments = pathWithName.split("/")
		val file = segments.last
		val nameSegments = file.split("\\.")
		val nameWithoutSuffix = nameSegments.get(0)
		return nameWithoutSuffix
	}

	private static def adjustString(Set<Pair<String, String>> setOfPairs, String stringToModify) {
		val containsSource = setOfPairs.exists[stringToModify.contains(key)]
		if (!containsSource && !setOfPairs.empty)
			throw new IllegalStateException('''AffectedEObject «stringToModify» lies not under any source of «setOfPairs»''')
		if (setOfPairs.empty)
			return stringToModify;
		val pair = setOfPairs.findFirst[stringToModify.contains(key)]
		val newProxyUriString = stringToModify.replace(pair.key, pair.value)
		return newProxyUriString
	}

	new(Set<Pair<String, String>> replacePairs) {
		this.replacePairs = newHashSet(replacePairs)
		nameReplacePairs = newHashSet
		replacePairs.forEach[nameReplacePairs += key.nameExtracted -> value.nameExtracted]
		level = Level::DEBUG
	}

	override copy(EChange e) {
		copyThisEChange(e)
	}

	private dispatch def EChange copyThisEChange(EChange e) {
		throw new UnsupportedOperationException('''Copying EChange «e» is not supported yet!''')
	}

	private dispatch def CreateAndInsertNonRoot<?, ?> copyThisEChange(
		CreateAndInsertNonRoot<?, ?> createAndInsertNonRoot
	) {
		val insertChange = createAndInsertNonRoot.insertChange
		val affectedEObject = insertChange.affectedEObject as InternalEObject
		val newAffectedEObject = adjust(affectedEObject)
		val affectedFeature = insertChange.affectedFeature
		val index = insertChange.index
		val newValue = insertChange.newValue
		val change = TypeInferringUnresolvingCompoundEChangeFactory::instance.createCreateAndInsertNonRootChange(
			newAffectedEObject,
			affectedFeature,
			newValue,
			index,
			EcoreUtil::getID(newValue)
		)
		return change
	}
	private dispatch def RemoveAndDeleteNonRoot<?, ?> copyThisEChange(
		RemoveAndDeleteNonRoot<?, ?> createAndInsertNonRoot
	) {
		val removeChange = createAndInsertNonRoot.removeChange
		val affectedEObject = removeChange.affectedEObject as InternalEObject
		val newAffectedEObject = adjust(affectedEObject)
		val affectedFeature = removeChange.affectedFeature
		val index = removeChange.index
		val oldValue = removeChange.oldValue
		val change = TypeInferringUnresolvingCompoundEChangeFactory::instance.createRemoveAndDeleteNonRootChange(
			newAffectedEObject,
			affectedFeature,
			oldValue,
			index,
			EcoreUtil::getID(oldValue)
		)
		return change
	}

	private dispatch def CreateAndInsertRoot<?> copyThisEChange(
		CreateAndInsertRoot<?> createAndInsertRoot
	) {
		val copiedChange = EcoreUtil::copy(createAndInsertRoot)
		val oldUri = copiedChange.insertChange.uri
		val newUri = adjustProxyUri(oldUri)
		copiedChange.insertChange.uri = newUri
		return copiedChange
	}

	private dispatch def CreateAndReplaceNonRoot<?, ?> copyThisEChange(
		CreateAndReplaceNonRoot<?, ?> createAndReplaceNonRoot
	) {
		val insertChange = createAndReplaceNonRoot.insertChange
		val affectedEObject = insertChange.affectedEObject as InternalEObject
		val newAffectedEObject = adjust(affectedEObject)
		val affectedFeature = insertChange.affectedFeature
		val newValue = insertChange.newValue
		val change = TypeInferringUnresolvingCompoundEChangeFactory::instance.createCreateAndReplaceNonRootChange(
			newAffectedEObject,
			affectedFeature,
			newValue,
			EcoreUtil::getID(newValue)
		)
		return change
	}

	private dispatch def ReplaceSingleValuedEAttribute<?, ?> copyThisEChange(
		ReplaceSingleValuedEAttribute<?, ?> replaceSingleValuedEAttribute
	) {
		val affectedAttribute = replaceSingleValuedEAttribute.affectedFeature
		val affectedEObject = replaceSingleValuedEAttribute.affectedEObject as InternalEObject
		val newAffectedEObject = adjust(affectedEObject)
		val newValue = replaceSingleValuedEAttribute.newValue
		val oldValue = replaceSingleValuedEAttribute.oldValue
		var newValueAfterAjustment = newValue
		if (newValue instanceof String) {
			if (nameReplacePairs.exists[key == newValue])
				newValueAfterAjustment = adjustName(newValue)
		}
		val x = TypeInferringUnresolvingAtomicEChangeFactory::instance.createReplaceSingleAttributeChange(
			newAffectedEObject,
			affectedAttribute,
			oldValue,
			newValueAfterAjustment
		)
		return x
	}
	private dispatch def ReplaceSingleValuedEReference<?, ?> copyThisEChange(
		ReplaceSingleValuedEReference<?, ?> replaceSingleValuedEAttribute
	) {
		val affectedAttribute = replaceSingleValuedEAttribute.affectedFeature
		val affectedEObject = replaceSingleValuedEAttribute.affectedEObject as InternalEObject
		val newAffectedEObject = adjust(affectedEObject)
		val newValue = replaceSingleValuedEAttribute.newValue
		val oldValue = replaceSingleValuedEAttribute.oldValue
		var newValueAfterAjustment = newValue
		val x = TypeInferringUnresolvingAtomicEChangeFactory::instance.createReplaceSingleReferenceChange(
			newAffectedEObject,
			affectedAttribute,
			oldValue,
			newValueAfterAjustment
		)
		return x
	}

	private def InternalEObject adjust(InternalEObject affectedEObject) {
		if (!affectedEObject.eIsProxy)
			return adjust(EChangeUnresolver::createProxy(affectedEObject))
		val proxyUriString = affectedEObject.eProxyURI.toString
		val newProxyUriString = adjustProxyUri(proxyUriString)
		val newProxyUri = URI::createURI(newProxyUriString)
		var InternalEObject newAffectedEObject = EChangeUnresolver::createProxy(EcoreUtil::copy(affectedEObject))
		newAffectedEObject.eSetProxyURI(newProxyUri)
		return newAffectedEObject
	}

	private def adjustProxyUri(String proxyUriString) {
		adjustString(replacePairs, proxyUriString)
	}

	private def adjustName(String oldName) {
		adjustString(nameReplacePairs, oldName)
	}

}
