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
import tools.vitruv.framework.applications.VitruvApplicationsRegistry;

import java.util.Collection;
import java.util.HashSet;

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
		addApplications();
	}

	public Collection<VitruvApplication> getSelectedApplications() {
		return selectedApplications;
	}

	private void addApplications() {
		Iterable<VitruvApplication> applications = VitruvApplicationsRegistry.getInstance().getApplications();
		for (VitruvApplication application : applications) {
			addToTree(application);
		}
	}

	private void addToTree(VitruvApplication application) {
		if (tree == null) {
			return;
		}
		TreeItem t = new TreeItem(tree, SWT.CHECK);
		t.setText(application.getName());
		t.setData(application);
	}

}
