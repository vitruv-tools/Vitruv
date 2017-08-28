package tools.vitruv.dsls.mirbase.formatting

import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFile
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEAttributeReference
import tools.vitruv.dsls.mirbase.mirBase.MetaclassEReferenceReference

class MirBaseFormatter extends AbstractFormatter2 {

	def dispatch format(MirBaseFile mirBaseFile, extension IFormattableDocument document) {
		mirBaseFile.formatMirBaseFile(document)
	}

	def protected void formatMirBaseFile(MirBaseFile mirBaseFile, extension IFormattableDocument document) {
		mirBaseFile.metamodelImports.tail.forEach[prepend [newLine]]
		mirBaseFile.metamodelImports.last?.append[newLines = 2]
	}

	def protected void formatMetaclassReference(MetaclassReference metaclassReference,
		extension IFormattableDocument document) {
		metaclassReference.regionFor.keyword('::').prepend[noSpace].append[noSpace]
	}

	def protected void formatEAttributeReference(MetaclassEAttributeReference attributeReference,
		extension IFormattableDocument document) {
		attributeReference.formatMetaclassReference(document)
		attributeReference.regionFor.keyword('[').prepend[noSpace].append[noSpace]
		attributeReference.regionFor.keyword(']').prepend[noSpace]
	}

	def protected void formatEReferenceReference(MetaclassEReferenceReference referenceReference,
		extension IFormattableDocument document) {
		referenceReference.formatMetaclassReference(document)
		referenceReference.regionFor.keyword('[').prepend[noSpace].append[noSpace]
		referenceReference.regionFor.keyword(']').prepend[noSpace]
	}

}
