package edu.kit.ipd.sdq.vitruvius.dsls.response.scoping

import org.eclipse.xtext.xbase.XbaseQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

/**
 * Copy of edu.kit.ipd.sdq.vitruvius.dsls.mapping.scoping.MappingLanguageQualifiedNameConverter by Dominik Werle
 */
// TODO HK refactor to only one implementation
class ResponseLanguageQualifiedNameConverter extends XbaseQualifiedNameConverter {
	
	override toQualifiedName(String qualifiedNameAsString) {
		if (qualifiedNameAsString.startsWith("http://"))
			return QualifiedName.create(qualifiedNameAsString)
			
		super.toQualifiedName(qualifiedNameAsString)
	}
	
}
