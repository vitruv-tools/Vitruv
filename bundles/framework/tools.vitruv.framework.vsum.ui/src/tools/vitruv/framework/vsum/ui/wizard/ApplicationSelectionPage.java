package tools.vitruv.framework.vsum.ui.wizard;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ApplicationSelectionPage extends WizardPage {

	private static final String SELECTION_LABEL = "Select your applications:";
	private static final String PAGENAME = "Application Selection";
	private static final String DESCRIPTION = "Select applications for consistency preservation";

	private Composite container;
	private Collection<VitruvApplication> selectedApplications;
	private Tree tree;

	protected ApplicationSelectionPage() {
		super(PAGENAME);
		setTitle(PAGENAME);
		setDescription(DESCRIPTION);
		selectedApplications = new HashSet<>();
		tree = null;
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		Label label1 = new Label(container, SWT.NONE);
		label1.setText(SELECTION_LABEL);
		tree = new Tree(container, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		GridData treeGridData = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(treeGridData);
		tree.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					TreeItem item = (TreeItem) event.item;
					if (item.getChecked()) {
						selectedApplications.add((VitruvApplication) item.getData());
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

	public Collection<VitruvApplication> getSelectedApplications() {
		return selectedApplications;
	}

	public void setDomains(Iterable<VitruvDomain> domains) {
		tree.removeAll();
		List<VitruvDomain> selectedDomains = new ArrayList<>();
		for (VitruvDomain domain : domains) {
			selectedDomains.add(domain);	
		}
		
		Iterable<VitruvApplication> applications = VitruvApplication.getAllApplicationsFromExtensionPoint();
		// Display only those who are in selectedDomains
		for (VitruvApplication application : applications) {
			boolean containsAll = true;
			for (VitruvDomain domain : application.getVitruvDomains()) {
				if (!selectedDomains.contains(domain)) {
					containsAll = false;
					break;
				}
			}
			if (containsAll) {
				addToTree(application);
			}
		}
	}

	private void addToTree(VitruvApplication application) {
		TreeItem t = new TreeItem(tree, SWT.CHECK);
		t.setText(application.getName());
		t.setData(application);
	}

}
