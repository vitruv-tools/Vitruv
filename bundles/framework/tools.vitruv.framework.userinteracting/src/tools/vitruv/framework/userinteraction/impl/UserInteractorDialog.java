package tools.vitruv.framework.userinteraction.impl;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class UserInteractorDialog {

    public static void showMessage(Shell parentShell, boolean modal, String message) {
        InformationDialog dialog = new InformationDialog(parentShell, modal, message);
        dialog.open();
    }

    public static int selectFromMessage(Shell parentShell, boolean modal, boolean postponeable, String message,
            String... selectionDescriptions) {
        SelectionDialog dialog = new SelectionDialog(parentShell, modal, postponeable, message, selectionDescriptions);
        return dialog.open();
    }
}

class InformationDialog extends Dialog {

    private final String message;

    protected InformationDialog(Shell parentShell, boolean modal, String message) {
        super(parentShell);
        // default Dialog behavior is a modal window
        if (!modal) {
            setShellStyle(getShellStyle() & ~SWT.APPLICATION_MODAL | SWT.MODELESS);
        }
        this.message = message;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        composite.getShell().setText("Information");
        Label msgLabel = new Label(composite, SWT.HORIZONTAL);
        msgLabel.setText(this.message);
        return composite;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }
}

/**
 * Returns code 0 if first message is selected, 1 for second etc. and -1 if user clicks 'postpone'
 * button.
 */
class SelectionDialog extends Dialog {

    private final String message;
    private final String[] selectionDescriptions;
    private final boolean postponable;
    private static final int POSTPONE = -1;

    protected SelectionDialog(Shell parentShell, boolean modal, boolean postponeable, String message,
            String[] selectionDescriptions) {
        super(parentShell);
        if (!modal) {
            setShellStyle(getShellStyle() & ~SWT.APPLICATION_MODAL | SWT.MODELESS);
        }
        this.message = message;
        this.selectionDescriptions = selectionDescriptions;
        this.postponable = postponeable;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        composite.getShell().setText("Select");
        composite.setLayout(new GridLayout(1, true));
        Label msgLabel = new Label(composite, SWT.HORIZONTAL);
        msgLabel.setText(this.message);
        createRadioButtons(this.selectionDescriptions, parent);
        return composite;
    }

    private void createRadioButtons(String[] selections, Composite parent) {
        Group radioGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
        radioGroup.setLayout(new GridLayout());
        for (int i = 0; i < selections.length; i++) {
            Button radio = new Button(radioGroup, SWT.RADIO);
            radio.setText(selections[i]);
            final int j = i;
            radio.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    setReturnCode(j);
                }
            });
        }
        radioGroup.pack();
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        if (this.postponable)
            createButton(parent, POSTPONE, "Decide Later", false);
    }

    @Override
    protected void buttonPressed(int buttonId) {
        if (buttonId == POSTPONE) {
            setReturnCode(POSTPONE);
        }
        close();
    }
}
