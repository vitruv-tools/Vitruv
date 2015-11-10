package edu.kit.ipd.sdq.vitruvius.dsls.mapping.scoping

import org.eclipse.xtext.xbase.XbaseQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

class MappingLanguageQualifiedNameConverter extends XbaseQualifiedNameConverter {
	
	override toQualifiedName(String qualifiedNameAsString) {
		if (qualifiedNameAsString.startsWith("http://"))
			return QualifiedName.create(qualifiedNameAsString)
			
		super.toQualifiedName(qualifiedNameAsString)
	}
	
}