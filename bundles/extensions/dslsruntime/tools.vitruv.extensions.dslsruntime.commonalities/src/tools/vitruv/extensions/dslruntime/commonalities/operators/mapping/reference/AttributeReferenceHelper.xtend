package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static extension tools.vitruv.extensions.dslruntime.commonalities.helper.IntermediateModelHelper.*

@Utility
class AttributeReferenceHelper {

	static def <I extends Intermediate> Iterable<I> getPotentiallyContainedIntermediates(
		IReferenceMappingOperator operator, EObject containerObject, CorrespondenceModel correspondenceModel,
		Class<I> intermediateType) {
		val containedObjects = operator.getContainedObjects(containerObject)
		return containedObjects.map[correspondenceModel.getCorrespondingIntermediate(it, intermediateType)]
			.filterNull.toSet
	}

	static def <I extends Intermediate> I getPotentialContainerIntermediate(IReferenceMappingOperator operator,
		EObject containedObject, CorrespondenceModel correspondenceModel, Class<I> intermediateType) {
		val containerObject = operator.getContainer(containedObject)
		if (containerObject === null) return null
		return correspondenceModel.getCorrespondingIntermediate(containerObject, intermediateType)
	}
}
