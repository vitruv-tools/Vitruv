package edu.kit.ipd.sdq.vitruvius.framework.mir.scoping

import org.eclipse.xtext.xbase.XbaseQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

class MIRQualifiedNameConverter extends XbaseQualifiedNameConverter {
	
	override toQualifiedName(String qualifiedNameAsString) {
		if (qualifiedNameAsString.startsWith("http://"))
			return QualifiedName.create(qualifiedNameAsString)
			
		super.toQualifiedName(qualifiedNameAsString)
	}
	
}