package tools.vitruv.framework.vsum.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.VitruvDomainProvider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class ApplicationSelectionPage extends WizardPage {

	private static final String PAGENAME = "Vitruvius Project";
	private static final String DESCRIPTION = "Create a new Vitruvius Project.";

	private Composite container;
	private Collection<IExtension> selectedApplications;
	private Tree tree;
	private Collection<String> alreadyDrawnIDs;

	protected ApplicationSelectionPage() {
		super(PAGENAME);
		setTitle(PAGENAME);
		setDescription(DESCRIPTION);
		selectedApplications = new HashSet<>();
		tree = null;
		alreadyDrawnIDs = new HashSet<>();
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		tree = new Tree(container, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		Label label1 = new Label(container, SWT.NONE);
		label1.setText("Select your applications.");
		GridData treeGridData = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(treeGridData);
		tree.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					TreeItem item = (TreeItem) event.item;
					if (item.getChecked()) {
						selectedApplications.add((IExtension) item.getData());
					} else {
						selectedApplications.remove(item.getData());
					}
					setPageComplete(!selectedApplications.isEmpty());
				}
			}
		});

		setControl(container);
		setPageComplete(false);
	}

	public Collection<IExtension> getSelectedApplications() {
		return selectedApplications;
	}

	public void displayCheckedDomains(Map<IProject, HashSet<IExtension>> domains) throws CoreException {
		tree.removeAll();
		alreadyDrawnIDs.clear();
		
		// Get a Set of all selected Domains
		Collection<String> selectedDomainsIDs = new HashSet<>();
		for (Collection<IExtension> collection : domains.values()) {
			for (IExtension ex : collection) {
				for (IConfigurationElement config : ex.getConfigurationElements()) {
					VitruvDomainProvider<?> domainProvider = (VitruvDomainProvider<?>) config.createExecutableExtension("class");
					selectedDomainsIDs.add(domainProvider.getDomain().getName());
				}
			}
		}
		// Get all available applications
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint ep = reg.getExtensionPoint(VitruvApplication.EXTENSION_POINT_ID);
		IExtension[] extensions = null;
		if (ep != null) {
			extensions = ep.getExtensions();
		}

		// Display only those who are in selectedDomains
		Collection<IExtension> toDraw = new HashSet<>();
		for (IExtension ex : extensions) {
			for (IConfigurationElement config : ex.getConfigurationElements()) {
				VitruvApplication appl = (VitruvApplication) config.createExecutableExtension("class");
				if (null != tree) {
					boolean containsAll = true;
					for (VitruvDomain domain : appl.getVitruvDomains()) {
						if (!selectedDomainsIDs.contains(domain.getName())) {
							containsAll = false;
							break;
						}
					}
					if (containsAll) {
						toDraw.add(ex);
					}
				}
			}
		}
		for (IExtension ex : toDraw) {
			addToTree(ex);
		}
	}

	private void addToTree(IExtension extension) {
		if (!alreadyDrawnIDs.contains(extension.getUniqueIdentifier())) {
			TreeItem t = new TreeItem(tree, SWT.CHECK);
			t.setText(extension.getLabel());
			t.setData(extension);
			alreadyDrawnIDs.add(extension.getUniqueIdentifier());
		}
	}
}
