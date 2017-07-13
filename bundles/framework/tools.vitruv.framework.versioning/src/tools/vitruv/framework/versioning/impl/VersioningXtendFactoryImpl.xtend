package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.VersioningXtendFactory

class VersioningXtendFactoryImpl implements VersioningXtendFactory {
	def static VersioningXtendFactory init() {
		new VersioningXtendFactoryImpl
	}

	private new() {
	}

	override createSourceTargetRecorder() {
		new SourceTargetRecorderImpl
	}

}
