package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.CommonalityFile
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.Import
import static extension tools.vitruv.dsls.commonalities.modelextension.CommonalitiesLanguageModelExtensions.*
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtext.scoping.impl.SimpleScope
import org.eclipse.xtext.resource.EObjectDescription

package class ImportScope implements IScope {
	
	val CommonalityFile commonalityFile;
	@Delegate val SimpleScope importSimpleScope;
	
	new (CommonalityFile commonalityFile) {
		this.commonalityFile = commonalityFile;
		importSimpleScope = new SimpleScope(commonalityFile.imports.map [importDescription])
	} 
	
	def private importDescription(Import importElement) {
		EObjectDescription.create(importElement.referenceName, importElement.importedPackageLikeObject)
	}
	
}