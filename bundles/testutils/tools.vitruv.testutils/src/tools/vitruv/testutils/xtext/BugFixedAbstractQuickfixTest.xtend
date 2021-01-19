package tools.vitruv.testutils.xtext

import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.testing.AbstractQuickfixTest
import tools.vitruv.framework.util.bridges.EMFBridge
import org.junit.jupiter.api.BeforeAll
import org.eclipse.xtext.ui.testing.AbstractWorkbenchTest
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.core.resources.IFile

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
		UIThread.runSync [AbstractWorkbenchTest.prepareWorkbench()]
	}

	override XtextEditor openEditor(IFile file) {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [super.openEditor(file)]
	}

	override setUp() {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [super.setUp()]
	}

	override tearDown() {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [super.tearDown()]
	}
}
