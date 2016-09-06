package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.StringOperationsJaMoPP
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.members.Method

abstract class Java2JMLTransformationBase extends AbortableEObjectMappingTransformationBase {

	protected val ShadowCopyFactory shadowCopyFactory

	@Inject
	protected new(ShadowCopyFactory shadowCopyFactory, 
		SynchronisationAbortedListener synchronisationAbortedListener) {
		super(synchronisationAbortedListener)
		this.shadowCopyFactory = shadowCopyFactory
	}

	protected def renameInAllJMLSpecifications(NamedElement renamedElement, String newName) {
		val changedObjects = new ArrayList<EObject>()

		// TODO this only works if the old state of the model is serialized to a file
		val modelInstanceRenamedElement = renamedElement.modelInstanceElement

		val shadowCopy = shadowCopyFactory.create(correspondenceModel)
		shadowCopy.setupShadowCopyWithJMLSpecifications(true)
		val obj = shadowCopy.shadowCopyCorrespondences.getShadow(modelInstanceRenamedElement)
		renameAllParentsIfApplicable(obj, newName);
		obj.name = newName
		val refactoredObjects = shadowCopy.updateOriginalJMLSpecifications()
		changedObjects.addAll(refactoredObjects)

		return changedObjects
	}

	private def dispatch renameAllParentsIfApplicable(Method m, String newName) {
		val classifier = m.containingConcreteClassifier
		for (superClassifier : classifier.allSuperClassifiers) {
			val candidates = superClassifier.methods.filter[name.equals(m.name)].filter [
				parameters.size == m.parameters.size
			].filter [
				StringOperationsJaMoPP.getStringRepresentation(typeReference, arrayDimension).equals(
					StringOperationsJaMoPP.getStringRepresentation(m.typeReference, m.arrayDimension))
			]
			for (candidate : candidates) {
				var allParamsMatched = true
				for (var i = 0; i < candidate.parameters.size; i++) {
					val candParam = candidate.parameters.get(i)
					val refParam = m.parameters.get(i)
					allParamsMatched = allParamsMatched &&
						StringOperationsJaMoPP.getStringRepresentation(candParam.typeReference,
							candParam.arrayDimension).equals(
							StringOperationsJaMoPP.getStringRepresentation(refParam.typeReference,
								refParam.arrayDimension))
				}
				if (allParamsMatched) {
					candidate.name = newName
				}
			}
		}
	}

	private def dispatch renameAllParentsIfApplicable(NamedElement ne, String newName) {
	}

	protected def simulateElementRemoval(NamedElement removedElement) {
		val shadowCopy = shadowCopyFactory.create(correspondenceModel)
		shadowCopy.setupShadowCopyWithJMLSpecifications(true)
		val shadowRemovedElement = shadowCopy.shadowCopyCorrespondences.getShadow(removedElement)
		val references = shadowCopy.shadowCopyCorrespondences.findReferencesToJavaObject(shadowRemovedElement)
		if (references.size > 0) {
			logger.trace(references.getLogMessageForFoundReferences)
			return false;
		}
		return true;
	}

	private static def getLogMessageForFoundReferences(Collection<EStructuralFeature.Setting> references) '''
	Found references:
	�FOR reference : references�
		Owner:		�reference.EObject�
		Feature:	�reference.EStructuralFeature.name�
	�ENDFOR�'''

}