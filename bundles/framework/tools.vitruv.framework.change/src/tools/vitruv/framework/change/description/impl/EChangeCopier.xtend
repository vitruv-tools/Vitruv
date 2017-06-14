package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import java.util.Collection
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory

class EChangeCopier {
	static val logger = Logger::getLogger(VitruviusChangeFactory)

	val Collection<EObject> elements

	val URI source
	val URI target

	new(URI source, URI target) {
		this.source = source
		this.target = target
		elements = new ArrayList<EObject>
	}

	def EChange copyEChange(EChange e) {
		copyThisShit(e)
	}

	private dispatch def EChange copyThisShit(EChange e) {
		logger.debug("EChange copyThisShit(EChange e)")
		logger.debug(e)
		EcoreUtil::copy(e)
	}

	private dispatch def EChange copyThisShit(CreateAndReplaceNonRoot createAndReplaceNonRoot) {
		logger.debug("EChange copyThisShit(CreateAndReplaceNonRoot createAndReplaceNonRoot)")
		logger.debug(createAndReplaceNonRoot)
		return copyCre(createAndReplaceNonRoot)
	}

	private dispatch def EChange copyThisShit(ReplaceSingleValuedEAttribute replaceSingleValuedEAttribute) {
		return null
//		
//		logger.debug("EChange copyThisShit(ReplaceSingleValuedEAttribute replaceSingleValuedEAttribute)")
//		logger.debug(replaceSingleValuedEAttribute)
//		return copyCre(replaceSingleValuedEAttribute)
	}

	private def <A extends EObject, T extends Object> EChange copyCre(
		ReplaceSingleValuedEAttribute<A, T> replaceSingleValuedEAttribute) {
		val affectedEObject = replaceSingleValuedEAttribute.affectedEObject as InternalEObject
		val affectedAttribute = replaceSingleValuedEAttribute.affectedFeature
		val oldValue = replaceSingleValuedEAttribute.oldValue
		val newValue = replaceSingleValuedEAttribute.newValue
		val real = elements.findFirst[it == affectedEObject]
		if (real ===
			null)
			throw new IllegalStateException('''There is no element corresponding to «affectedEObject» already inserted in the model''')
		return TypeInferringUnresolvingAtomicEChangeFactory::instance.createReplaceSingleAttributeChange(
			real,
			affectedAttribute,
			oldValue,
			newValue
		)
	}

	private def <A extends EObject, T extends EObject> EChange copyCre(
		CreateAndReplaceNonRoot<A, T> createAndReplaceNonRoot) {
		val affectedEObject = createAndReplaceNonRoot.insertChange.affectedEObject as InternalEObject
		val proxyUri = affectedEObject.eProxyURI
		val proxyUriString = proxyUri.toString
		val sourceUriString = source.toString
		val containsSource = proxyUriString.contains(sourceUriString)
		var InternalEObject newAffectedEObject = null
		if (containsSource) {
			newAffectedEObject = EcoreUtil::copy(affectedEObject)
			val targetURIString = target.toString
			val newProxyUriString = proxyUriString.replace(sourceUriString, targetURIString)
			val newProxyUri = URI.createURI(newProxyUriString)
			newAffectedEObject.eSetProxyURI(newProxyUri)
			if (newProxyUri == target)
				logger.debug("I've got no roots! :)!")
		} else {
			logger.warn('''AffectedEObject «affectedEObject» lies not under «source»''')
			newAffectedEObject = affectedEObject
		}

		val affectedFeature = createAndReplaceNonRoot.insertChange.affectedFeature
		val newValue = createAndReplaceNonRoot.insertChange.newValue
		logger.debug("WHO")
		val change = TypeInferringUnresolvingCompoundEChangeFactory::instance.
			createCreateAndReplaceNonRootChange(newAffectedEObject, affectedFeature, newValue)
		return change
	}
}
