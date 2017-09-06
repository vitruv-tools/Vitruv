package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.dsls.commonalities.language.elements.impl.VitruviusDomainImpl
import tools.vitruv.framework.domains.VitruvDomain

import static com.google.common.base.Preconditions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class VitruvDomainAdapter extends VitruviusDomainImpl implements Wrapper<VitruvDomain> {
	VitruvDomain wrappedVitruvDomain
	var extension ClassifierProvider classifierProvider
	
	override forVitruvDomain(VitruvDomain vitruvDomain) {
		this.wrappedVitruvDomain = checkNotNull(vitruvDomain)
		return this
	}
	
	override withClassifierProvider(ClassifierProvider classifierProvider) {
		this.classifierProvider = checkNotNull(classifierProvider)
		return this
	}

	def private checkDomainSet() {
		checkState(wrappedVitruvDomain !== null, "No VitruvDomain was set on this adapter!")
	}
	
	def private checkClassifierProviderSet() {
		checkState(classifierProvider !== null, "No classifier provider was set on this element!")
	}

	def private static Iterable<EPackage> getRecursiveSubPackages(EPackage ePackage) {
		#[ePackage] + ePackage.ESubpackages.flatMap [recursiveSubPackages]
	}
	
	
	override getMetaclasses() {
		if (metaclasses === null) {
			checkDomainSet()
			checkClassifierProviderSet()
			super.getMetaclasses() += loadMetaclasses()
			classifierProvider = null
		}
		metaclasses
	}

	def private loadMetaclasses() {
		(
			#[wrappedVitruvDomain.metamodelRootPackage]
			+ wrappedVitruvDomain.furtherRootPackages
		)
			.flatMap [recursiveSubPackages]
			.flatMap [EClassifiers]
			.filter(EClass)
			.map [toMetaclass(this)]
			+ #[LanguageElementsFactory.eINSTANCE.createResourceMetaclass
				.withClassifierProvider(classifierProvider).fromDomain(this)]
	}

	override getName() {
		if (eIsProxy) return null
		checkDomainSet()
		wrappedVitruvDomain.name
	}
	
	override getWrapped() {
		wrappedVitruvDomain
	}
	
	override toString() {
		'''{{«wrappedVitruvDomain?.name»}}'''
	}
	
}
