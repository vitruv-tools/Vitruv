package tools.vitruv.testutils.matchers

import tools.vitruv.framework.correspondence.Correspondence
import org.eclipse.emf.ecore.EObject
import org.hamcrest.Description

interface CorrespondenceOption {
	def Iterable<? extends Correspondence> filterCorrespondences(Iterable<? extends Correspondence> results)

	def Iterable<? extends EObject> filterResultObjects(Iterable<? extends EObject> results)

	def void describeTo(Description description)

	def static filterBy(Iterable<? extends Correspondence> results, Iterable<? extends CorrespondenceOption> options) {
		options.fold(results)[previousResults, option|option.filterCorrespondences(previousResults)]
	}
}
