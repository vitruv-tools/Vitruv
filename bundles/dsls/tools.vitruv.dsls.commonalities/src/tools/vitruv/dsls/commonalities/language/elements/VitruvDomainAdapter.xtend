package tools.vitruv.dsls.commonalities.language.elements

import tools.vitruv.dsls.commonalities.language.elements.impl.VitruvDomainImpl
import tools.vitruv.framework.domains.VitruvDomain
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.eclipse.emf.ecore.EClass
import static com.google.common.base.Preconditions.*
import org.eclipse.emf.ecore.EPackage

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
			super.getMetaclasses().addAll(loadMetaclasses())
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
	}

	override getName() {
		checkWrappedVitruvDomainIsSet()
		wrappedVitruvDomain.name
	}

	override getWrapped() {
		wrappedVitruvDomain
	}
}
