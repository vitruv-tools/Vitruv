/*
 * generated by Xtext 2.12.0
 */
package tools.vitruv.dsls.commonalities.ui.labeling

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.xbase.ui.labeling.XbaseDescriptionLabelProvider

/**
 * Provides labels for IEObjectDescriptions and IResourceDescriptions.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class CommonalitiesLanguageDescriptionLabelProvider extends XbaseDescriptionLabelProvider {

	override text(IEObjectDescription description) {
		text(description.EObjectOrProxy, description)
	}

	// REMARK: should be dispatch when the other operations are not commented out
	def text(EObject object, IEObjectDescription description) {
		super.text(description)
	}


/* 	def dispatch text(Metaclass metaclass, IEObjectDescription description) {
		new StyledString()
			.append(description.name.firstSegment, QUALIFIER_STYLER)
			.append(DOMAIN_METACLASS_SEPARATOR, QUALIFIER_STYLER)
			.append(description.name.getSegment(1))
	}*/

	// Labels and icons can be computed like this:

//	override text(IEObjectDescription ele) {
//		ele.name.toString
//	}

	override image(IEObjectDescription ele) {
		ele.EClass.name + '.gif'
	}
}
