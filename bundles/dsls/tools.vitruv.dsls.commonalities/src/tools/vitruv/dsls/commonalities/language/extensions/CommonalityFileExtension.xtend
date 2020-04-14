package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.CommonalityFile

import static extension tools.vitruv.dsls.commonalities.language.extensions.OperatorImportExtension.*

@Utility
package class CommonalityFileExtension {

	def static getOperatorTypeImports(CommonalityFile commonalityFile) {
		return commonalityFile.operatorImports.filter[isTypeImport]
	}

	def static getOperatorNamespaceImports(CommonalityFile commonalityFile) {
		return commonalityFile.operatorImports.filter[isNamespaceImport]
	}
}