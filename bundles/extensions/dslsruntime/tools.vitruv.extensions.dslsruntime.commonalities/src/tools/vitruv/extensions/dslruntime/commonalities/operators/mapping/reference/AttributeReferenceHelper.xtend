package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static extension tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper.*

@Utility
class AttributeReferenceHelper {

	def static <T extends Intermediate> getPotentiallyContainedIntermediates(IReferenceMappingOperator operator,
		EObject containerObject, CorrespondenceModel correspondenceModel, Class<T> intermediateType) {
		val containedObjects = operator.getContainedObjects(containerObject)
		return containedObjects.map[correspondenceModel.getCorrespondingIntermediate(it, intermediateType)]
			.filterNull.toSet
	}

	def static <T extends Intermediate> getPotentialContainerIntermediate(IReferenceMappingOperator operator,
		EObject containedObject, CorrespondenceModel correspondenceModel, Class<T> intermediateType) {
		val containerObject = operator.getContainer(containedObject)
		if (containerObject === null) return null
		return correspondenceModel.getCorrespondingIntermediate(containerObject, intermediateType)
	}

	private def static <T extends Intermediate> getCorrespondingIntermediate(CorrespondenceModel correspondenceModel,
		EObject object, Class<T> intermediateType) {
		// Assumption: Each object has at most one Intermediate correspondence.
		return correspondenceModel.getCorrespondingObjectsOfType(object, null, intermediateType).head
	}
}
