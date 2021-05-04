package tools.vitruv.framework.vsum.variability

import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class VaveModelFactory {
	static def createVaveModel(URI resourceUri) {
		return new InternalVaveModelImpl(resourceUri);
	}
}
