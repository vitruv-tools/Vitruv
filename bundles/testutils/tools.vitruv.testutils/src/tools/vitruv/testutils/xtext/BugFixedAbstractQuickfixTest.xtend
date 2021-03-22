package tools.vitruv.testutils.xtext

import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.testing.AbstractQuickfixTest
import org.junit.jupiter.api.BeforeAll
import org.eclipse.xtext.ui.testing.AbstractWorkbenchTest
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.core.resources.IFile
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI

/**
 * Fixes shortcomings of {@link AbstractQuickfixTest}.
 */
class BugFixedAbstractQuickfixTest extends AbstractQuickfixTest {
	override XtextResource getXtextResource(String model) {
		// fix that the Xtext test does not setup the resource properly
		val xtextResource = super.getXtextResource(model)
		xtextResource.URI = createPlatformResourceURI('''«projectName»/«fileName».«fileExtension»''', true)
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

	override waitForEventProcessing() {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [super.waitForEventProcessing()]
	}

	override tearDown() {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [super.tearDown()]
	}
}
