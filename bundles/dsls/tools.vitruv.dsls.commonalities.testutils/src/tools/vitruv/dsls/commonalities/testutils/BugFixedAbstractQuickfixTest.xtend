package tools.vitruv.dsls.commonalities.testutils

import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.testing.AbstractQuickfixTest
import tools.vitruv.framework.util.bridges.EMFBridge
import org.junit.jupiter.api.BeforeAll
import org.eclipse.xtext.ui.testing.AbstractWorkbenchTest
import org.eclipse.swt.widgets.Display

/**
 * Fixes shortcomings of {@link AbstractQuickfixTest}.
 */
class BugFixedAbstractQuickfixTest extends AbstractQuickfixTest {
	override XtextResource getXtextResource(String model) {
		// fix that the Xtext test does not setup the resource properly
		val xtextResource = super.getXtextResource(model)
		xtextResource.URI = EMFBridge.createURI('''«projectName»/«fileName».«fileExtension»''')
		return xtextResource
	}

	@BeforeAll
	def static prepareWorkbench() {
		// fix that the Xtext test does not support running outside the UI thread
		Display.^default.asyncExec[AbstractWorkbenchTest.prepareWorkbench()]
	}

	override setUp() {
		// fix that the Xtext test does not support running outside the UI thread
		Display.^default.syncExec[super.setUp()]
	}

	override tearDown() {
		// fix that the Xtext test does not support running outside the UI thread
		Display.^default.syncExec[super.tearDown()]
	}

	override testQuickfixesOn(CharSequence content, String issueCode, AbstractQuickfixTest.Quickfix... quickfixes) {
		Display.^default.syncExec[super.testQuickfixesOn(content, issueCode, quickfixes)]
	}
}
