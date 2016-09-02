package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.ui.projectwizard;

import org.eclipse.jdt.internal.ui.wizards.JavaProjectWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;

/**
 * @author Langhamm A wizard to create a new PCM Java project according to a chosen template.
 */
@SuppressWarnings("restriction")
public class NewPCMJavaProjectWizard extends JavaProjectWizard {

    private NewPCMJavaModelPathWizardPage pcmJavaModelPathWizardPage;

    public NewPCMJavaProjectWizard() {
        super();
        setNeedsProgressMonitor(true);
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        super.init(workbench, selection);
    }

    @Override
    public boolean performFinish() {
        boolean ret = super.performFinish();
        if(ret){
            //TODO: add vitruvius nature with model path and model files to create
        }
        return ret;
    }
    
    @Override
    public void addPages(){
        super.addPages();
        if(null == pcmJavaModelPathWizardPage){
            IWizardPage lastWizardPage = this.getPages()[this.getPages().length - 1];
            pcmJavaModelPathWizardPage = new NewPCMJavaModelPathWizardPage("Select pcm model path", lastWizardPage);
        }
        super.addPage(pcmJavaModelPathWizardPage);
    }
}
