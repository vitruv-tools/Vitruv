package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.correspondence.Correspondence
import org.eclipse.emf.ecore.EObject
import org.hamcrest.Description

@FinalFieldsConstructor
package class TypeOption implements CorrespondenceOption {
	val Class<? extends EObject> expectedType

	override filterCorrespondences(Iterable<? extends Correspondence> results) {
		results
	}

	override filterResultObjects(Iterable<? extends EObject> results) {
		results.filter(expectedType)
	}

	override describeTo(Description description) {
		description.appendText(" of type ").appendText('''<«expectedType.name»>''')
	}
}

@FinalFieldsConstructor
package class TagOption implements CorrespondenceOption {
	val String expectedTag

	override filterCorrespondences(Iterable<? extends Correspondence> results) {
		results.filter[tag == expectedTag]
	}

	override filterResultObjects(Iterable<? extends EObject> results) {
		results
	}

	override describeTo(Description description) {
		description.appendText(" tagged with ").appendValue(expectedTag)
	}
}
