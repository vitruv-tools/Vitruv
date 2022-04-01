package tools.vitruv.dsls.commonalities.language.elements

import java.util.Set
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage

import static com.google.common.base.Preconditions.*
import java.util.List
import tools.vitruv.dsls.commonalities.language.elements.impl.MetamodelImpl

class MetamodelAdapter extends MetamodelImpl implements Wrapper<EPackage> {
	EPackage wrappedEPackage
	var extension ClassifierProvider classifierProvider

	override forEPackage(EPackage ePackage) {
		this.wrappedEPackage = checkNotNull(ePackage)
		return this
	}

	override withClassifierProvider(ClassifierProvider classifierProvider) {
		this.classifierProvider = checkNotNull(classifierProvider)
		return this
	}

	private def checkDomainSet() {
		checkState(wrappedEPackage !== null, "No ePackage was set on this adapter!")
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
		return Set.of(wrappedEPackage)
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
			.flatMap [EClassifiers]
			.filter(EClass)
			.map [toMetaclass(this)]
			+ List.of(createResourceMetaclass())
	}
	
	private def createResourceMetaclass() {
		LanguageElementsFactory.eINSTANCE.createResourceMetaclass 
			.withClassifierProvider(classifierProvider)
			.fromDomain(this)
	}

	override getName() {
		if (eIsProxy) return null
		checkDomainSet()
		wrappedEPackage.name
	}

	override getWrapped() {
		wrappedEPackage
	}

	override toString() {
		'''{{«wrappedEPackage?.name»}}'''
	}
	
	override equals(Object o) {
		if (this === o) true
		else if (o === null) false
		else if (o instanceof MetamodelAdapter) {
			this.wrappedEPackage == o.wrappedEPackage
		}
		else false
	}
	
	override hashCode() {
		5 * ((wrappedEPackage === null) ? 0 : wrappedEPackage.hashCode())
	}
}
