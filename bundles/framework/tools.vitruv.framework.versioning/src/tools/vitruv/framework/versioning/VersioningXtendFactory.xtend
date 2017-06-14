package tools.vitruv.framework.versioning

import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.framework.versioning.impl.VersioningXtendFactoryImpl

/**
 * Factory for the versioning xtend classes. 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-05-31
 */
interface VersioningXtendFactory {
	val VersioningXtendFactory instance = VersioningXtendFactoryImpl::init

	def SourceTargetRecorder createSourceTargetRecorder(InternalVirtualModel virtualModel)
}
