package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.OperatorImport

@Utility
package class OperatorImportExtension {

	static def isTypeImport(OperatorImport operatorImport) {
		return (operatorImport.importedType !== null)
	}

	static def isNamespaceImport(OperatorImport operatorImport) {
		return (operatorImport.importedNamespace !== null)
	}
}
