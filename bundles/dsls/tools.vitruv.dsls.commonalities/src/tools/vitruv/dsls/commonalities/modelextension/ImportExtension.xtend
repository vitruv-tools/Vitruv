package tools.vitruv.dsls.commonalities.modelextension

import tools.vitruv.dsls.commonalities.commonalitiesLanguage.Import
import static extension tools.vitruv.dsls.commonalities.names.EPackageURINameResolver.getPackageName
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.MetapackageImportReference
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.CommonalityImportReference
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility package class ImportExtension {
	
	def static getImportedPackageLikeObject(Import importElement) {
		importElement.element.referencedEObject
	}
	
	def static getReferenceName(Import importElement) {
		importElement.alias ?: importElement.element.referencedName
	}	
	
	def private static dispatch getReferencedEObject(MetapackageImportReference metaImport) {
		metaImport.package
	}
	
	def private static dispatch getReferencedEObject(CommonalityImportReference commImport) {
		commImport.commonality
	}
	
	def private static dispatch getReferencedName(MetapackageImportReference metaImport) {
		metaImport.package.packageName
	}
	
	def private static dispatch getReferencedName(CommonalityImportReference commImport) {
		commImport.commonality.commonality.name
	}
}