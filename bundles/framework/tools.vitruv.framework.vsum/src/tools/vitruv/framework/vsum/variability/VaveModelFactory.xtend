package tools.vitruv.framework.vsum.variability

import tools.vitruv.framework.vsum.variability.VaveModelImpl
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class VaveModelFactory {
	static def createVaveModel(URI resourceUri) {
		return new VaveModelImpl(resourceUri);
	}
}
