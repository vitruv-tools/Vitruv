package tools.vitruv.testutils.matchers

import tools.vitruv.framework.correspondence.CorrespondenceModelView
import tools.vitruv.framework.correspondence.Correspondence

interface CorrespondenceModelContainer {
	def CorrespondenceModelView<? extends Correspondence> getCorrespondenceModel()
}
