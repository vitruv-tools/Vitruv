package tools.vitruv.testutils.matchers

import tools.vitruv.framework.correspondence.CorrespondenceModelView

interface CorrespondenceModelContainer {
	def CorrespondenceModelView<?> getCorrespondenceModel()
}
