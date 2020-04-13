package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.OperatorImport

@Utility
package class OperatorImportExtension {

	def static isTypeImport(OperatorImport operatorImport) {
		return (operatorImport.importedType !== null)
	}

	def static isNamespaceImport(OperatorImport operatorImport) {
		return (operatorImport.importedNamespace !== null)
	}
}
