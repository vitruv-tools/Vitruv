package tools.vitruv.extensions.dslruntime.commonalities.helper

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.framework.correspondence.CorrespondenceModel

import static extension tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper.*

@Utility
class IntermediateModelHelper {

	static def Intermediate getCorrespondingIntermediate(CorrespondenceModel correspondenceModel, EObject object) {
		return correspondenceModel.getCorrespondingIntermediate(object, Intermediate)
	}

	// This explicitly checks that the corresponding intermediate is of the specified type and otherwise returns null:
	static def <I extends Intermediate> I getCorrespondingIntermediate(CorrespondenceModel correspondenceModel,
		EObject object, Class<I> intermediateType) {
		// Assumption: Each object has at most one Intermediate correspondence.
		return correspondenceModel.getCorrespondingObjectsOfType(object, null, intermediateType).head
	}
}
