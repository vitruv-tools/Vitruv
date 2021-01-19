package tools.vitruv.testutils.xtext

import org.junit.jupiter.api.BeforeAll
import org.eclipse.xtext.ui.testing.AbstractWorkbenchTest
import org.eclipse.swt.widgets.Display
import org.eclipse.xtext.ui.testing.AbstractEditorTest
import org.eclipse.xtext.ui.editor.XtextEditor
import tools.vitruv.framework.util.Capture
import static extension tools.vitruv.framework.util.Capture.*
import org.eclipse.core.resources.IFile

/**
 * Fixes shortcomings of {@link AbstractEditorTest}.
 */
class BugFixedAbstractEditorTest extends AbstractEditorTest {
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

	override XtextEditor openEditor(IFile file) {
		// fix that the Xtext test does not support running outside the UI thread
		val editor = new Capture<XtextEditor>
		Display.^default.syncExec[super.openEditor(file) >> editor]
		return -editor
	}
	
	override waitForEventProcessing() {
		// fix that the Xtext test does not support running outside the UI thread
		Display.^default.syncExec[super.waitForEventProcessing()]
	}
}
