/*
 * generated by Xtext 2.10.0
 */
package tools.vitruv.dsls.reactions.ui.labeling

import com.google.inject.Inject
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.xtext.xbase.ui.labeling.XbaseLabelProvider

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class ReactionsLanguageLabelProvider extends XbaseLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	// Labels and icons can be computed like this:
	
//	def text(Greeting ele) {
//		'A greeting to ' + ele.name
//	}
//
//	def image(Greeting ele) {
//		'Greeting.gif'
//	}
}
