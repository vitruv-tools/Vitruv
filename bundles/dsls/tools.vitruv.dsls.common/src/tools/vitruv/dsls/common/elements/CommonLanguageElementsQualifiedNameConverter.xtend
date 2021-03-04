package tools.vitruv.dsls.common.elements

import org.eclipse.xtext.xbase.XbaseQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

class CommonLanguageElementsQualifiedNameConverter extends XbaseQualifiedNameConverter {

	override toQualifiedName(String qualifiedNameAsString) {
		if (qualifiedNameAsString.startsWith("http://"))
			return QualifiedName.create(qualifiedNameAsString)

		super.toQualifiedName(qualifiedNameAsString)
	}

}
