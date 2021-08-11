package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.SelfDescribing
import tools.vitruv.framework.correspondence.CorrespondenceModelView
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.hamcrest.Description

@FinalFieldsConstructor
package class CorrespondenceSource implements SelfDescribing {
	val CorrespondenceModelView<?> correspondenceModel
	val List<CorrespondenceOption> options

	def package getCorrespespondingObjects(EObject item) {
		correspondenceModel.getCorrespondences(#[item]).filterWithOptions [ option, correspondences |
			option.filterCorrespondences(correspondences)
		].map [
			if (leftEObjects == List.of(item)) rightEObjects else leftEObjects
		].flatten.filterWithOptions[option, objects|option.filterResultObjects(objects)]
	}

	override describeTo(Description description) {
		options.forEach[describeTo(description)]
	}

	def private <T> Iterable<? extends T> filterWithOptions(Iterable<? extends T> elements,
		(CorrespondenceOption, Iterable<? extends T>)=>Iterable<? extends T> filterFunction) {
		options.fold(elements)[lastElements, option|filterFunction.apply(option, lastElements)]
	}
}
