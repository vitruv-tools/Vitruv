package tools.vitruv.framework.change.description.impl

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.EChangeCopier
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertNonRootImpl
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import tools.vitruv.framework.util.datatypes.VURI

class EChangeCopierImpl implements EChangeCopier {
	static val logger = Logger::getLogger(EChangeCopierImpl)
	val String source
	val String target

	new(URI source, URI target) {
		logger.level = Level::DEBUG
		this.source = source.toString
		this.target = target.toString
	}

	override copyEMFModelChangeToList(EMFModelChangeImpl changeToCopy, VURI vuri) {
		val newChanges = changeToCopy.copiedEChangeIterator.map [
			new EMFModelChangeImpl(#[it], vuri) as TransactionalChange
		].toList
		return newChanges
	}

	override copyEMFModelChangeToSingleChange(EMFModelChangeImpl changeToCopy, VURI vuri) {
		val newEchanges = changeToCopy.copiedEChangeIterator.toList
		val newChange = new EMFModelChangeImpl(newEchanges, vuri)
		return newChange
	}

	private dispatch def EChange copyThisEChange(EChange e) {
		throw new UnsupportedOperationException('''Copying EChange «e» is not supported yet!''')
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
		val newValue = EcoreUtil::copy(insertChange.newValue)
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

	private def adjust(InternalEObject affectedEObject) {
		val proxyUriString = affectedEObject.eProxyURI.toString
		val containsSource = proxyUriString.contains(source)
		if (!containsSource) {
			logger.error('''AffectedEObject «affectedEObject» lies not under «source»''')
			return affectedEObject
		}
		val newProxyUriString = proxyUriString.replace(source, target)
		val newProxyUri = URI::createURI(newProxyUriString)
		var InternalEObject newAffectedEObject = EChangeUnresolver::createProxy(EcoreUtil::copy(affectedEObject))
		newAffectedEObject.eSetProxyURI(newProxyUri)
		return newAffectedEObject

	}

	private def getCopiedEChangeIterator(EMFModelChangeImpl changeToCopy) {
		changeToCopy.EChanges.map[copyThisEChange].filterNull
	}
}
