package tools.vitruv.framework.change.copy

import java.util.Set
import tools.vitruv.framework.change.copy.impl.ChangeCopyFactoryImpl

interface ChangeCopyFactory {
	ChangeCopyFactory instance = ChangeCopyFactoryImpl::init

	def EChangeCopier createEChangeCopier(Set<Pair<String, String>> replacePairs)
}
