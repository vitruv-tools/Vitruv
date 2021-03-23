package tools.vitruv.framework.vsum.variability

import tools.vitruv.framework.vsum.variability.VaveModelImpl
import tools.vitruv.framework.uuid.UuidResolver
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class VaveModelFactory {
	static def createVaveModel(UuidResolver uuidResolver, URI resourceUri) {
		return new VaveModelImpl(uuidResolver, resourceUri);
	}

//	def createVaveModel() {
//		return new VaveModelImpl();
//	}
}
