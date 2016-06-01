package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.UIJob;

public abstract class NonBlockingNextStepDialog extends MessageDialog {

	public NonBlockingNextStepDialog(Shell parent, List<UIJob> jobs, int nextJobIdx) {
		super(parent, "Perform Next Step", null, getJobName(jobs, nextJobIdx), MessageDialog.CONFIRM, createButtonLabels(), 0);
		this.setBlockOnOpen(false);
		this.setShellStyle(SWT.TITLE | SWT.CLOSE);
	}

	private static String getJobName(List<UIJob> jobs, int nextJobIdx) {
		UIJob job = jobs.get(nextJobIdx);
		return job.getName();
	}

	private static String[] createButtonLabels() {
		String[] button = new String[1];
		button[0] = "Next";
		return button;
	}
	
	@Override
	public boolean close() {
		boolean result = super.close();
		onClose(getReturnCode());
		return result;
	}

	abstract protected void onClose(int returnCode);

}
