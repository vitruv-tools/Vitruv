package tools.vitruv.extensions.dslruntime.commonalities.helper

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Intermediate
import tools.vitruv.extensions.dslruntime.commonalities.resources.IntermediateResourceBridge
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.VitruviusConstants

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper.*

@Utility
class IntermediateModelHelper {

	static def getMetadataModelKey(String conceptDomainName) {
		return #[
			'commonalities',
			conceptDomainName + VitruviusConstants.fileExtSeparator + conceptDomainName.toFirstLower
		]
	}

	static def Intermediate getCorrespondingIntermediate(CorrespondenceModel correspondenceModel, EObject object) {
		return correspondenceModel.getCorrespondingIntermediate(object, Intermediate)
	}

	// This explicitly checks that the corresponding intermediate is of the specified type and otherwise returns null:
	static def <I extends Intermediate> I getCorrespondingIntermediate(CorrespondenceModel correspondenceModel,
		EObject object, Class<I> intermediateType) {
		// Assumption: Each object has at most one Intermediate correspondence.
		return correspondenceModel.getCorrespondingObjectsOfType(object, null, intermediateType).head
	}

	static def IntermediateResourceBridge getCorrespondingResourceBridge(CorrespondenceModel correspondenceModel,
		EObject object) {
		checkArgument(!(object instanceof Intermediate), "object cannot be of type Intermediate")
		// Assumption: Each object has at most one Resource correspondence.
		return correspondenceModel.getCorrespondingObjectsOfType(object, null, IntermediateResourceBridge).head
	}
}
