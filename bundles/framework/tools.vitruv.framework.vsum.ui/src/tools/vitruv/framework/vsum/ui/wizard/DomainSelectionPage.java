package tools.vitruv.framework.vsum.ui.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.VitruvDomainProvider;
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry;
import tools.vitruv.framework.vsum.ui.util.ProjectCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DomainSelectionPage extends WizardPage {

	private static final String DEFAULT_NEW_PROJECT_NAME = "MyProject";
	private static final String CREATE_PROJECT_BUTTON_LABEL = "Create new project";
	private static final String SELECTION_LABEL = "Select involved projects and their domains:";
	private static final String PAGENAME = "Project and Domain Selection";
	private static final String DESCRIPTION = "Select projects and their domains";
	private Map<IProject, Set<VitruvDomain>> selectedDomainsForProjects;
	private Tree tree;
	private Composite container;

	protected DomainSelectionPage() {
		super(PAGENAME);
		setTitle(PAGENAME);
		setDescription(DESCRIPTION);
		this.selectedDomainsForProjects = new HashMap<>();
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		Label label1 = new Label(container, SWT.NONE);
		label1.setText(SELECTION_LABEL);

		tree = new Tree(container, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData treeGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		treeGridData.heightHint = 300;
		tree.setLayoutData(treeGridData);

		initializeProjectList();

		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					TreeItem item = (TreeItem) event.item;
					TreeItem parent = item.getParentItem();
					if (item.getChecked()) {
						// check a project automatically, when one of it's domains is checked
						if (null != parent) {
							parent.setChecked(true);
							selectedDomainsForProjects.get(parent.getData()).add((VitruvDomain) item.getData());
						}
					} else {
						// all domains get unselected, when project is unselected
						if (null == parent) {
							for (TreeItem child : item.getItems()) {
								child.setChecked(false);
								selectedDomainsForProjects.get(item.getData()).remove(child.getData());
							}
						} else {
							selectedDomainsForProjects.get(parent.getData()).remove(item.getData());
						}
					}
					// only finish if something is checked.
					boolean finished = false;
					for (TreeItem treeItem : tree.getItems()) {
						finished = finished || treeItem.getChecked();
					}
					setPageComplete(finished);
				}
			}
		});
		
		final Text text = new Text(container, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.setText(DEFAULT_NEW_PROJECT_NAME);
		final Button button = new Button(container, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		button.setText(CREATE_PROJECT_BUTTON_LABEL);
		
		text.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				button.setEnabled(!text.getText().isEmpty());
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ProjectCreator(text.getText()).createProject();
				initializeProjectList();
			}
		});
		
		setControl(container);
		setPageComplete(false);
	}

	private void initializeProjectList() {
		tree.removeAll();
		IProject projects[] = getProjects();
		Iterable<VitruvDomainProvider<?>> domainProviders = VitruvDomainProviderRegistry.getAllDomainProviders();
		for (IProject project : projects) {
			TreeItem t = new TreeItem(tree, SWT.CHECK);
			t.setText(project.getName());
			t.setData(project);
			selectedDomainsForProjects.put(project, new HashSet<>());
			for (VitruvDomainProvider<?> provider : domainProviders) {
				TreeItem childItem = new TreeItem(t, SWT.CHECK);
				childItem.setText(provider.getDomain().getName());
				childItem.setData(provider.getDomain());
			}
		}
	}
	
	/**
	 * Returns a HashMap of Projects, where every project is mapped to all of
	 * it's selected domains, respectively.
	 * 
	 * @return a HashMap that maps all project to their checked domains,
	 *         respectively
	 */
	public Map<IProject, Set<VitruvDomain>> getCheckedDomains() {
		return selectedDomainsForProjects;
	}

	private IProject[] getProjects() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}

	Iterable<VitruvDomain> getSelectedDomains() {
		Set<VitruvDomain> domains = new HashSet<>();
		for (Set<VitruvDomain> domainSet : selectedDomainsForProjects.values()) {
			for (VitruvDomain domain : domainSet) {
				domains.add(domain);
			}
		}
		return domains;
	}

}
