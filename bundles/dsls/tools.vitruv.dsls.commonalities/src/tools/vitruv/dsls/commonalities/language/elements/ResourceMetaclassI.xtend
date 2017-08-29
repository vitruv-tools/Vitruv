package tools.vitruv.dsls.commonalities.language.elements

import edu.kit.ipd.sdq.activextendannotations.Lazy
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.commonalities.language.elements.impl.ResourceMetaclassImpl
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage

// TODO remove once resources are handled by domains
class ResourceMetaclassI extends ResourceMetaclassImpl implements Wrapper<EClass> {

	static val RESOURCE_METACLASS_NAME = 'Resource'
	@Lazy static val EClassMetaclass adapter = LanguageElementsFactory.eINSTANCE.createEClassMetaclass => [
		wrapEClass(ResourcesPackage.eINSTANCE.resource)
	]

	override getName() {
		RESOURCE_METACLASS_NAME
	}

	override basicGetPackageLikeContainer() {
		domain
	}
	
	override getAttributes() {
		adapter.attributes
	}
	
	override getReferences() {
		adapter.references
	}
	
	override getChangeClass() {
		ResourcesPackage.eINSTANCE.intermediateResourceBridge
	}
	
	override getWrapped() {
		ResourcesPackage.eINSTANCE.resource
	}
	
}
