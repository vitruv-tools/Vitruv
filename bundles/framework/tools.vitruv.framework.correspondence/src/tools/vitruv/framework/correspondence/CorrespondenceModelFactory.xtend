package tools.vitruv.framework.correspondence

import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.correspondence.impl.InternalCorrespondenceModelImpl
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class CorrespondenceModelFactory {
	static def createCorrespondenceModel(UuidResolver uuidResolver, URI resourceUri) {
		return new InternalCorrespondenceModelImpl(uuidResolver, resourceUri);
	}
}