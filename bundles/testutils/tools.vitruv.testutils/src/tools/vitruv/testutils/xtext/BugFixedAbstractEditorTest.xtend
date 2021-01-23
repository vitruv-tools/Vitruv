package tools.vitruv.testutils.xtext

import org.junit.jupiter.api.BeforeAll
import org.eclipse.xtext.ui.testing.AbstractWorkbenchTest
import org.eclipse.xtext.ui.testing.AbstractEditorTest
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.core.resources.IFile

/**
 * Fixes shortcomings of {@link AbstractEditorTest}.
 */
class BugFixedAbstractEditorTest extends AbstractEditorTest {
	@BeforeAll
	def static prepareWorkbench() {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [AbstractWorkbenchTest.prepareWorkbench()]
	}

	override setUp() {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [super.setUp()]
	}

	override tearDown() {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [super.tearDown()]
	}

	override XtextEditor openEditor(IFile file) {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [super.openEditor(file)]
	}
	
	override waitForEventProcessing() {
		// fix that the Xtext test does not support running outside the UI thread
		UIThread.runSync [super.waitForEventProcessing()]
	}
}
