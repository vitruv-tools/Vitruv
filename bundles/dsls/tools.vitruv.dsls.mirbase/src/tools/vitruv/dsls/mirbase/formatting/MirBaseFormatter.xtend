package tools.vitruv.dsls.mirbase.formatting

import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFile
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEAttributeReference

class MirBaseFormatter extends AbstractFormatter2 {
	
	def dispatch format(MirBaseFile mirBaseFile, extension IFormattableDocument document) {
		mirBaseFile.formatStatic(document)
	}
	
	def protected void formatStatic(MirBaseFile mirBaseFile, extension IFormattableDocument document) {
		mirBaseFile.metamodelImports.tail.forEach [prepend [newLine]]
	}
	
	def protected void formatStatic(MetaclassReference metaclassReference, extension IFormattableDocument document) {
		metaclassReference.regionFor.keyword('::').prepend[noSpace].append[noSpace]
	}
	
	def protected void formatStatic(MetaclassEAttributeReference attributeReference, extension IFormattableDocument document) {
		(attributeReference as MetaclassReference).formatStatic(document)
		attributeReference.regionFor.keyword('[').prepend[noSpace].append[noSpace]
		attributeReference.regionFor.keyword(']').prepend[noSpace]
	}
}