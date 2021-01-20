package tools.vitruv.framework.vsum.ui

import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.ui.PlatformUI
import org.eclipse.jface.wizard.WizardDialog
import tools.vitruv.framework.vsum.ui.wizard.CreateVsumWizard

class CreateVsumButtonHandler extends AbstractHandler {
	
	override execute(ExecutionEvent event) throws ExecutionException {
		val wizard = new CreateVsumWizard();
		val wd = new  WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
     	wd.setTitle(wizard.getWindowTitle());
     	wd.open();
	}
	
}