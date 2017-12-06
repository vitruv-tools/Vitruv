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
	
	def private checkEClassSet() {
		checkState(wrappedEClass !== null, "No EClass was set on this adapter!")
	}
	
	def private checkClassifierProviderSet() {
		checkState(classifierProvider !== null, "No classifier provider was set on this element!")
	}
	
	def private checkDomainSet() {
		checkState(containingDomain !== null, "No domain was set on this metaclass!")
	}

	override getName() {
		if (eIsProxy) return null
		checkEClassSet()
		wrappedEClass.name
	}

	override getAttributes() {
		if (attributes === null) {
			checkEClassSet()
			checkClassifierProviderSet()
			super.getAttributes() += loadAttributes()
			classifierProvider = null
		}
		/* 		val attributes = super.getAttributes()
		 * 		if (!attributesInitialized && !eIsProxy) {
		 * 			attributes.addAll(loadAttributes())
		 * 			attributesInitialized = true
		 }*/
		attributes
	}

	def private loadAttributes() {
		wrappedEClass.EAllStructuralFeatures.map [ eFeature |
			LanguageElementsFactory.eINSTANCE.createEFeatureAttribute.withClassifierProvider(classifierProvider).
				forEFeature(eFeature).fromMetaclass(this)
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
		if (eClassAdapter == WellKnownClassifiers.MOST_SPECIFIC_TYPE) return true
		if (wrappedEClass == EcorePackage.eINSTANCE.EObject) return true
		return this.wrappedEClass.isSuperTypeOf(eClassAdapter.wrappedEClass)
	}
	
	def dispatch isSuperTypeOf(MostSpecificType mostSpecificType) {
		true
	}
	
	override getAllMembers() {
		new UnmodifiableEList(attributes)
	}
	
	override toString() {
		if (eIsProxy) {
			'''unresolved «class.simpleName»: «eProxyURI»'''
		} else {
			'''{{«containingDomain.name»:«wrappedEClass?.name»}}'''
		}
	}

	override isAbstract() {
		if (eIsProxy) return false
		checkEClassSet()
		wrappedEClass.isAbstract
	}
	
}
