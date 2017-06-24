package tools.vitruv.framework.change.copy.impl

import tools.vitruv.framework.change.copy.ChangeCopyFactory
import java.util.List

class ChangeCopyFactoryImpl implements ChangeCopyFactory {
	static def ChangeCopyFactory init() {
		new ChangeCopyFactoryImpl
	}

	private new() {
	}

	override createEChangeCopier(List<Pair<String, String>> replacePairs) {
		new EChangeCopierImpl(replacePairs)
	}

}
