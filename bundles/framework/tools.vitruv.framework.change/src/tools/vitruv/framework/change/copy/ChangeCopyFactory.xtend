package tools.vitruv.framework.change.copy

import tools.vitruv.framework.change.copy.impl.ChangeCopyFactoryImpl
import java.util.List

interface ChangeCopyFactory {
	ChangeCopyFactory instance = ChangeCopyFactoryImpl::init

	def EChangeCopier createEChangeCopier(List<Pair<String, String>> replacePairs)
}
