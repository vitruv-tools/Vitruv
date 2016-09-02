package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.ui.projectwizard;

import org.eclipse.core.resources.IContainer;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IStringButtonAdapter;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Langhamm
 * 
 */
@SuppressWarnings("restriction")
class NewPCMJavaModelPathWizardPage extends WizardPage {

    private static final String DEFAULT_MODEL_PATH = "model";
    private String modelPath;

    private SelectionButtonDialogField useDefaultModelPathButtonDialogField;
    private StringButtonDialogField chooseModelPathStringButtonDialogField;
    private SelectionButtonDialogField createRepositoryFileSelectionButtonDialogField;
    private StringDialogField chooseRepositoryFileStringDialogField;
    private SelectionButtonDialogField createSystemFileSelectionButtonDialogField;
    private StringDialogField chooseSystemFileStringDialogField;

    protected NewPCMJavaModelPathWizardPage(String pageName, IWizardPage previousPage) {
        super(pageName);
        modelPath = DEFAULT_MODEL_PATH;
        setTitle("PCM model path selection");
        setDescription("Select a model path for the PCM models.");
        setPreviousPage(previousPage);
        setPageComplete(false);
    }

    @Override
    public void createControl(Composite parent) {
        final Composite composite = new Composite(parent, SWT.NULL);
        composite.setFont(parent.getFont());
        int nColumns = 4;
        GridLayout gridLayout = new GridLayout(nColumns, true);
        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

        createChooseModelPathControls(composite, nColumns);
        createRepositoryControls(composite, nColumns);
        createSystemControls(composite, nColumns);

        setControl(composite);
    }

    public String getModelPath() {
        if (null == chooseModelPathStringButtonDialogField || useDefaultModelPathButtonDialogField.isSelected()) {
            return DEFAULT_MODEL_PATH;
        }
        return modelPath;
    }

    public String getRepositoryFileName() {
        if (null == createRepositoryFileSelectionButtonDialogField
                || !createRepositoryFileSelectionButtonDialogField.isSelected()) {
            return null;
        }
        return chooseRepositoryFileStringDialogField.getText();
    }

    public String getSystemFileName() {
        if (null == createSystemFileSelectionButtonDialogField
                || !createSystemFileSelectionButtonDialogField.isSelected()) {
            return null;
        }
        return chooseSystemFileStringDialogField.getText();
    }

    private void createChooseModelPathControls(Composite composite, int nColumns) {
        chooseModelPathStringButtonDialogField = new StringButtonDialogField(new IStringButtonAdapter() {
            @Override
            public void changeControlPressed(DialogField field) {
                IContainer[] result = WorkspaceResourceDialog.openFolderSelection(null, "Select folder",
                        "Select folder", false, null, null);
                modelPath = result[0].getName();
                chooseModelPathStringButtonDialogField.setText(modelPath);
            }
        });
        chooseModelPathStringButtonDialogField.setEnabled(false);
        chooseModelPathStringButtonDialogField.setLabelText("Define model path: ");
        chooseModelPathStringButtonDialogField.setText(modelPath);
        chooseModelPathStringButtonDialogField.setButtonLabel("Browse");

        useDefaultModelPathButtonDialogField = new SelectionButtonDialogField(SWT.CHECK);
        useDefaultModelPathButtonDialogField.setLabelText("Use default model path");
        useDefaultModelPathButtonDialogField.setSelection(true);
        useDefaultModelPathButtonDialogField.setEnabled(true);
        useDefaultModelPathButtonDialogField.setDialogFieldListener(new IDialogFieldListener() {
            @Override
            public void dialogFieldChanged(DialogField field) {
                chooseModelPathStringButtonDialogField.setEnabled(!useDefaultModelPathButtonDialogField.isSelected());
            }
        });

        // fill grid
        useDefaultModelPathButtonDialogField.doFillIntoGrid(composite, nColumns);
        chooseModelPathStringButtonDialogField.doFillIntoGrid(composite, nColumns);
    }

    private void createRepositoryControls(Composite composite, int nColumns) {
        createRepositoryFileSelectionButtonDialogField = new SelectionButtonDialogField(SWT.CHECK);
        chooseRepositoryFileStringDialogField = new StringDialogField();
        createSelectAndChooseFileControls(composite, nColumns, createRepositoryFileSelectionButtonDialogField,
                chooseRepositoryFileStringDialogField, "Repository");
    }

    private void createSystemControls(Composite composite, int nColumns) {
        createSystemFileSelectionButtonDialogField = new SelectionButtonDialogField(SWT.CHECK);
        chooseSystemFileStringDialogField = new StringDialogField();
        createSelectAndChooseFileControls(composite, nColumns, createSystemFileSelectionButtonDialogField,
                chooseSystemFileStringDialogField, "System");
    }

    private void createSelectAndChooseFileControls(Composite composite, int nColumns,
            final SelectionButtonDialogField createFileSelectionButtonDialogField,
            final StringDialogField chooseFileStringButtonDialogField, String fileType) {
        createFileSelectionButtonDialogField.setEnabled(true);
        createFileSelectionButtonDialogField.setLabelText("Create " + fileType + " file");
        createFileSelectionButtonDialogField.setSelection(true);
        createFileSelectionButtonDialogField.setDialogFieldListener(new IDialogFieldListener() {

            @Override
            public void dialogFieldChanged(DialogField field) {
                chooseFileStringButtonDialogField.setEnabled(createFileSelectionButtonDialogField.isSelected());
            }
        });

        chooseFileStringButtonDialogField.setLabelText(fileType + " file: ");
        chooseFileStringButtonDialogField.setText("Default" + fileType);
        chooseFileStringButtonDialogField.setEnabled(true);

        // fill grid
        createFileSelectionButtonDialogField.doFillIntoGrid(composite, nColumns);
        chooseFileStringButtonDialogField.doFillIntoGrid(composite, nColumns);
    }

}
