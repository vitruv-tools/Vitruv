package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import java.util.Collection
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver

class EChangeCopier<A extends EObject> {
	static val logger = Logger::getLogger(VitruviusChangeFactory)

	val Collection<EObject> elements

	val A source
	val A target

	new(A source, A target) {
		this.source = EChangeUnresolver::createProxy(source)
		this.target = EChangeUnresolver::createProxy(target)
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

	private def <T extends Object> EChange copyCre(ReplaceSingleValuedEAttribute<A, T> replaceSingleValuedEAttribute) {
		val affectedEObject = replaceSingleValuedEAttribute.affectedEObject
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

	private def <T extends EObject> copyCre(CreateAndReplaceNonRoot<A, T> createAndReplaceNonRoot) {
		val affectedEObject = createAndReplaceNonRoot.insertChange.affectedEObject
		val affectedFeature = createAndReplaceNonRoot.insertChange.affectedFeature
		val newValue = createAndReplaceNonRoot.insertChange.newValue
		logger.debug("WHO")
		logger.debug(affectedEObject)
		logger.debug(source.eResource)
		if (affectedEObject == source) {
			logger.debug("Heureka!!!!!!!")
			val change = TypeInferringUnresolvingCompoundEChangeFactory::instance.
				createCreateAndReplaceNonRootChange(target, affectedFeature, newValue)
			val testNewValue = change.insertChange.newValue
			elements += testNewValue
			return change
		} else
			TypeInferringUnresolvingCompoundEChangeFactory::instance.
				createCreateAndReplaceNonRootChange(affectedEObject, affectedFeature, newValue)
	}
}
