package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.common.util.DelegatingEList.UnmodifiableEList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.dsls.commonalities.language.elements.impl.EClassMetaclassImpl

import static com.google.common.base.Preconditions.*

class EClassAdapter extends EClassMetaclassImpl implements Wrapper<EClass> {

	EClass wrappedEClass
	extension ClassifierProvider classifierProvider
	Domain containingDomain

	override withClassifierProvider(ClassifierProvider classifierProvider) {
		this.classifierProvider = checkNotNull(classifierProvider)
		return this
	}

	override forEClass(EClass eClass) {
		this.wrappedEClass = checkNotNull(eClass)
		return this
	}

	override fromDomain(Domain domain) {
		this.containingDomain = domain
		return this
	}

	private def checkEClassSet() {
		checkState(wrappedEClass !== null, "No EClass was set on this adapter!")
	}

	private def checkClassifierProviderSet() {
		checkState(classifierProvider !== null, "No classifier provider was set on this element!")
	}

	private def checkDomainSet() {
		checkState(containingDomain !== null, "No domain was set on this metaclass!")
	}

	override getName() {
		if (eIsProxy) return null
		checkEClassSet()
		wrappedEClass.name
	}

	override getAttributes() {
		// For commonality participations we sometimes get participation
		// classes with unresolvable proxies for their superMetaclass (eg. when
		// starting an Eclipse runtime application and having an editor for a
		// commonality with commonality participation open). We currently
		// return an empty list of attributes in this case, which seems to not
		// cause issues so far.
		// TODO Figure out why these proxies cannot be resolved to their
		// concrete Commonality instances.
		if (eIsProxy) {
			return super.getAttributes()
		}
		if (attributes === null) {
			checkEClassSet()
			checkClassifierProviderSet()
			super.getAttributes() += loadAttributes()
			classifierProvider = null
		}
		return attributes
	}

	private def loadAttributes() {
		wrappedEClass.EAllStructuralFeatures.map [ eFeature |
			LanguageElementsFactory.eINSTANCE.createEFeatureAttribute.withClassifierProvider(classifierProvider)
				.forEFeature(eFeature).fromMetaclass(this)
		]
	}

	override getWrapped() {
		wrappedEClass
	}

	override basicGetPackageLikeContainer() {
		checkDomainSet()
		domain
	}

	override basicGetDomain() {
		if (eIsProxy) return null
		checkDomainSet()
		return containingDomain
	}

	def dispatch isSuperTypeOf(Classifier subType) {
		false
	}

	def dispatch isSuperTypeOf(EClassAdapter eClassAdapter) {
		if (this === eClassAdapter) return true
		if (wrappedEClass === EcorePackage.eINSTANCE.EObject) return true
		return this.wrappedEClass.isSuperTypeOf(eClassAdapter.wrappedEClass)
	}

	def dispatch isSuperTypeOf(MostSpecificType mostSpecificType) {
		true
	}

	def dispatch isSuperTypeOf(LeastSpecificType leastSpecificType) {
		false
	}

	override getAllMembers() {
		new UnmodifiableEList(getAttributes())
	}

	override toString() {
		if (eIsProxy) {
			'''unresolved «class.simpleName»: «eProxyURI»'''
		} else {
			'''«containingDomain.name»:«wrappedEClass?.name»'''
		}
	}

	override isAbstract() {
		if (eIsProxy) return false
		checkEClassSet()
		wrappedEClass.isAbstract
	}
	
	override equals(Object o) {
		if (this === o) true
		else if (o === null) false
		else if (o instanceof EClassAdapter) {
			this.containingDomain == o.containingDomain && this.wrappedEClass == o.wrappedEClass
		}
		else false
	}
	
	override hashCode() {
		val prime = 53
		return (prime + ((containingDomain === null) ? 0 : containingDomain.hashCode()))
			* prime + ((wrappedEClass === null) ? 0 : wrappedEClass.hashCode())
	}
}
