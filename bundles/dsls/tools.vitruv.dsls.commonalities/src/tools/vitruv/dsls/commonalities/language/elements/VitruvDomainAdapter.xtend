package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.dsls.commonalities.language.elements.impl.VitruvDomainImpl
import tools.vitruv.framework.domains.VitruvDomain

import static com.google.common.base.Preconditions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class VitruvDomainAdapter extends VitruvDomainImpl implements Wrapper<VitruvDomain> {

	VitruvDomain wrappedVitruvDomain

	override wrapVitruvDomain(VitruvDomain vitruvDomain) {
		checkState(wrappedVitruvDomain === null, "This object already has a VitruvDomain!")
		this.wrappedVitruvDomain = checkNotNull(vitruvDomain)
	}

	def private checkWrappedVitruvDomainIsSet() {
		checkState(wrappedVitruvDomain !== null, "No VitruvDomain was set on this object!");
	}
	
	def private static Iterable<EPackage> getRecursiveSubPackages(EPackage ePackage) {
		#[ePackage] + ePackage.ESubpackages.flatMap [recursiveSubPackages]
	}
	
	override getMetaclasses() {
		if (metaclasses === null) {
			super.getMetaclasses() += loadMetaclasses()
		}
		metaclasses
	}

	def private loadMetaclasses() {
		checkWrappedVitruvDomainIsSet()

		(
			#[wrappedVitruvDomain.metamodelRootPackage]
			+ wrappedVitruvDomain.furtherRootPackages
		)
			.flatMap [recursiveSubPackages]
			.flatMap [EClassifiers]
			.filter(EClass)
			.map [eClass |
				LanguageElementsFactory.eINSTANCE.createEClassMetaclass => [
					wrapEClass(eClass)
				]
			]
			+ #[LanguageElementsFactory.eINSTANCE.createResourceMetaclass]
	}

	override getName() {
		if (eIsProxy) return null
		checkWrappedVitruvDomainIsSet()
		wrappedVitruvDomain.name
	}
	
	override getVitruvDomain() {
		wrappedVitruvDomain
	}

	override getWrapped() {
		wrappedVitruvDomain
	}
}
