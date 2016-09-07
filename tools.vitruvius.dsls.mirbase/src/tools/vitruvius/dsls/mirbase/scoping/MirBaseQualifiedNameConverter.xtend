package tools.vitruvius.dsls.mirbase.scoping

import org.eclipse.xtext.xbase.XbaseQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName

class MirBaseQualifiedNameConverter extends XbaseQualifiedNameConverter {
	
	override toQualifiedName(String qualifiedNameAsString) {
		if (qualifiedNameAsString.startsWith("http://"))
			return QualifiedName.create(qualifiedNameAsString)
			
		super.toQualifiedName(qualifiedNameAsString)
	}
	
}
