package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.common.util.DelegatingEList.UnmodifiableEList
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.commonalities.language.elements.impl.ResourceMetaclassImpl
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage

import static com.google.common.base.Preconditions.*

// TODO remove once resources are handled by domains
class ResourceMetaclassI extends ResourceMetaclassImpl implements Wrapper<EClass> {

	static val RESOURCE_METACLASS_NAME = 'Resource'
	var Metaclass adapter
	Domain containingDomain
	ClassifierProvider classifierProvider

	override withClassifierProvider(ClassifierProvider classifierProvider) {
		this.classifierProvider = checkNotNull(classifierProvider)
		if (containingDomain !== null) readAdapter()
		return this
	}

	override fromDomain(Domain domain) {
		this.containingDomain = checkNotNull(domain)
		if (classifierProvider !== null) readAdapter()
		return this
	}

	def private readAdapter() {
		adapter = classifierProvider.toMetaclass(ResourcesPackage.eINSTANCE.resource, containingDomain)
		classifierProvider = null
	}

	def private checkDomainSet() {
		checkState(containingDomain !== null, "No domain was set on this metaclass!")
	}

	def private checkAdapterCreated() {
		if (adapter === null) {
			checkDomainSet()
			checkState(classifierProvider !== null, "No classifierProvider was set on this adapter!")
		}
	}

	override getName() {
		RESOURCE_METACLASS_NAME
	}

	override basicGetPackageLikeContainer() {
		checkDomainSet()
		domain
	}

	override getAttributes() {
		if (attributes === null) {
			checkAdapterCreated()
			super.getAttributes() += adapter.attributes
		}
		attributes
	}

	override basicGetDomain() {
		checkDomainSet()
		return containingDomain
	}

	override getWrapped() {
		ResourcesPackage.eINSTANCE.resource
	}

	def dispatch isSuperTypeOf(Classifier subType) {
		subType == this
	}
	
	def dispatch isSuperTypeOf(MostSpecificType mostSpecificType) {
		true
	}

	override getAllMembers() {
		new UnmodifiableEList(adapter.allMembers)
	}
	
	override toString() {
		'''Resource Metaclass (‹«domain»›)'''
	}
	
	override isAbstract() {
		false
	}

}
