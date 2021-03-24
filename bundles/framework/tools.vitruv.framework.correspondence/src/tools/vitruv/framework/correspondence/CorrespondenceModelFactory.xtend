package tools.vitruv.framework.correspondence

import tools.vitruv.framework.correspondence.impl.InternalCorrespondenceModelImpl
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class CorrespondenceModelFactory {
	static def createCorrespondenceModel(URI resourceUri) {
		return new InternalCorrespondenceModelImpl(resourceUri);
	}
}