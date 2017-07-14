package tools.vitruv.framework.vsum.ui.wizard;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.vsum.ui.util.VitruvInstanceCreator;

import java.util.Map;
import java.util.Set;

public class CreateVsumWizard extends Wizard implements INewWizard {
	private static Logger logger = Logger.getLogger(CreateVsumWizard.class);
	private static final String WINDOWTITLE = "New Vitruv Project";
	protected ProjectNamePage projectNamePage;
	protected DomainSelectionPage domainSelectionPage;
	protected ApplicationSelectionPage applicationSelectionPage;
	private boolean finished = false;
	
	public CreateVsumWizard() {
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
		// Avoid duplicate execution, when repeatedly hitting finish
		if (finished) {
			return true;
		}
		finished = true;
		String name = projectNamePage.getEnteredName();
		logger.info("Vitruvius wizard completed: ");
		logger.info("  Vsum name: " + name);
		Map<IProject, Set<VitruvDomain>> projectsToDomains = domainSelectionPage.getCheckedDomains();
		Iterable<VitruvApplication> applications = applicationSelectionPage.getSelectedApplications();
		for (IProject project : projectsToDomains.keySet()) {
			for (VitruvDomain domain : projectsToDomains.get(project)) {
				logger.info("  Selected domain " + domain.getName() + " in project " + project.getName());
			}
		}
		for (VitruvApplication application : applications) {
			logger.info("  Selected application: " + application.getName());
		}
		new VitruvInstanceCreator(name, projectsToDomains, applications).createVsumProject();
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
