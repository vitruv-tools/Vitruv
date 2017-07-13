package tools.vitruv.framework.vsum.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.framework.domains.VitruvDomain;

import java.util.HashMap;
import java.util.HashSet;

public class NewVitruvWizard extends Wizard implements INewWizard {

	private static final String WINDOWTITLE = "New Vitruvius Project";
	protected ProjectNamePage projectNamePage;
	protected DomainSelectionPage domainSelectionPage;
	protected ApplicationSelectionPage applicationSelectionPage;

	public NewVitruvWizard() {
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public String getWindowTitle() {
		return WINDOWTITLE;
	}

	@Override
	public void addPages() {
		projectNamePage = new ProjectNamePage(this);
		domainSelectionPage = new DomainSelectionPage();
		applicationSelectionPage = new ApplicationSelectionPage();
		addPage(projectNamePage);
		addPage(domainSelectionPage);
		addPage(applicationSelectionPage);
	}

	@Override
	public boolean performFinish() {
		System.out.println("Name " + projectNamePage.getEnteredName());
		HashMap<IProject, HashSet<VitruvDomain>> map = domainSelectionPage.getCheckedDomains();
		for (IProject project : map.keySet()) {
			for (VitruvDomain domain : map.get(project)) {
				System.out.println("Selected in project" + project.getName() + ": " + domain.getName());
			}
		}
		for (VitruvApplication application : applicationSelectionPage.getSelectedApplications()) {
			System.out.println("Selected Application: " + application.getName());
		}
		return true;
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == domainSelectionPage) {
			applicationSelectionPage.setDomains(domainSelectionPage.getSelectedDomains());
		}
		return super.getNextPage(page);
	}

}
