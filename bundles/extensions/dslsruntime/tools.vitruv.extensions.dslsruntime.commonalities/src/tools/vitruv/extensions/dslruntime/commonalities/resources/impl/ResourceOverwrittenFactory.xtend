package tools.vitruv.extensions.dslruntime.commonalities.resources.impl

// TODO remove once resources are handled by domains
class ResourceOverwrittenFactory extends ResourcesFactoryImpl {
	
	override createIntermediateResourceBridge() {
		new IntermediateResourceBridgeI
	}
	
}