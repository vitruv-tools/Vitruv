package tools.vitruv.dsls.commonalities.language.elements

import java.util.Set
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.dsls.commonalities.language.elements.impl.VitruviusDomainImpl
import tools.vitruv.framework.domains.VitruvDomain

import static com.google.common.base.Preconditions.*

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

	private def checkDomainSet() {
		checkState(wrappedVitruvDomain !== null, "No VitruvDomain was set on this adapter!")
	}

	private def checkClassifierProviderSet() {
		checkState(classifierProvider !== null, "No classifier provider was set on this element!")
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

	private def Set<EPackage> getRootPackages() {
		return (#[wrappedVitruvDomain.metamodelRootPackage] + wrappedVitruvDomain.furtherRootPackages).toSet
	}

	def Set<EPackage> getAllPackages() {
		val rootPackages = rootPackages
		return (rootPackages + rootPackages.flatMap[recursiveSubPackages]).toSet
	}

	private static def Iterable<EPackage> getRecursiveSubPackages(EPackage ePackage) {
		return ePackage.ESubpackages + ePackage.ESubpackages.flatMap[recursiveSubPackages]
	}

	private def loadMetaclasses() {
		allPackages
			.flatMap[EClassifiers]
			.filter(EClass)
			.map[toMetaclass(this)]
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
