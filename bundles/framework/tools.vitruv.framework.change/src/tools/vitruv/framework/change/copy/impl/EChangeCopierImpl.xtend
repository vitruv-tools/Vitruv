package tools.vitruv.framework.change.copy.impl

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.copy.EChangeCopier
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertNonRootImpl
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import tools.vitruv.framework.util.datatypes.VURI
import java.util.List
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot

class EChangeCopierImpl implements EChangeCopier {
	static extension Logger = Logger::getLogger(EChangeCopierImpl)
	val List<Pair<String, String>> replacePairs

	new(List<Pair<String, String>> replacePairs) {
		level = Level::DEBUG
		this.replacePairs = replacePairs
	}

	override copyEChanges(EChange changeToCopy, VURI vuri) {
		val copiedEChange = copyThisEChange(changeToCopy)
		return VitruviusChangeFactory::instance.
			createEMFModelChangeFromEChanges(#[copiedEChange], vuri) as VitruviusChange
	}

	override copyEMFModelChangeToList(VitruviusChange changeToCopy, VURI vuri) {
		val newChanges = changeToCopy.copiedEChangeIterator.map [
			VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(#[it], vuri) as VitruviusChange
		].toList
		return newChanges
	}

	override copyEMFModelChangeToSingleChange(VitruviusChange changeToCopy, VURI vuri) {
		val newEchanges = changeToCopy.copiedEChangeIterator.toList
		val newChange = VitruviusChangeFactory::instance.createEMFModelChangeFromEChanges(newEchanges, vuri)
		return newChange
	}

	private dispatch def EChange copyThisEChange(EChange e) {
		throw new UnsupportedOperationException('''Copying EChange «e» is not supported yet!''')
	}

	private dispatch def CreateAndInsertRoot<?> copyThisEChange(CreateAndInsertRoot<?> createAndInsertNonRoot) {
		return null
	}

	private dispatch def CreateAndInsertNonRoot<?, ?> copyThisEChange(
		CreateAndInsertNonRootImpl<?, ?> createAndInsertNonRoot) {
		val insertChange = createAndInsertNonRoot.insertChange
		val affectedEObject = insertChange.affectedEObject as InternalEObject
		val newAffectedEObject = adjust(affectedEObject)
		val affectedFeature = insertChange.affectedFeature
		val index = insertChange.index
		val newValue = insertChange.newValue
		val change = TypeInferringUnresolvingCompoundEChangeFactory::instance.
			createCreateAndInsertNonRootChange(newAffectedEObject, affectedFeature, newValue, index)
		return change
	}

	private dispatch def CreateAndReplaceNonRoot<?, ?> copyThisEChange(
		CreateAndReplaceNonRoot<?, ?> createAndReplaceNonRoot) {
		val insertChange = createAndReplaceNonRoot.insertChange
		val affectedEObject = insertChange.affectedEObject as InternalEObject
		val newAffectedEObject = adjust(affectedEObject)
		val affectedFeature = insertChange.affectedFeature
		val newValue = insertChange.newValue
		val change = TypeInferringUnresolvingCompoundEChangeFactory::instance.
			createCreateAndReplaceNonRootChange(newAffectedEObject, affectedFeature, newValue)
		return change
	}

	private dispatch def ReplaceSingleValuedEAttribute<?, ?> copyThisEChange(
		ReplaceSingleValuedEAttribute<?, ?> replaceSingleValuedEAttribute) {
		val affectedAttribute = replaceSingleValuedEAttribute.affectedFeature
		val affectedEObject = replaceSingleValuedEAttribute.affectedEObject as InternalEObject
		val newAffectedEObject = adjust(affectedEObject)
		val newValue = replaceSingleValuedEAttribute.newValue
		val oldValue = replaceSingleValuedEAttribute.oldValue
		val x = TypeInferringUnresolvingAtomicEChangeFactory::instance.createReplaceSingleAttributeChange(
			newAffectedEObject,
			affectedAttribute,
			oldValue,
			newValue
		)
		return x
	}

	private def InternalEObject adjust(InternalEObject affectedEObject) {
		if (!affectedEObject.eIsProxy)
			return adjust(EChangeUnresolver::createProxy(affectedEObject))
		val proxyUriString = affectedEObject.eProxyURI.toString
		val containsSource = replacePairs.exists[proxyUriString.contains(key)]
		if (!containsSource) {
			error('''AffectedEObject «affectedEObject» lies not under any source of «replacePairs»''')
			return affectedEObject
		}
		val pair = replacePairs.findFirst[proxyUriString.contains(key)]
		val newProxyUriString = proxyUriString.replace(pair.key, pair.value)
		val newProxyUri = URI::createURI(newProxyUriString)
		var InternalEObject newAffectedEObject = EChangeUnresolver::createProxy(EcoreUtil::copy(affectedEObject))
		newAffectedEObject.eSetProxyURI(newProxyUri)
		return newAffectedEObject
	}

	private def getCopiedEChangeIterator(VitruviusChange changeToCopy) {
		changeToCopy.getEChanges.map[copyThisEChange].filterNull
	}

	override copy(EChange e) {
		copyThisEChange(e)
	}

}
