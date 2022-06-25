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

import tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilderApplicator;
import tools.vitruv.framework.vsum.ui.util.ProjectCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicatorSelectionPage extends WizardPage {

	private static final String DEFAULT_NEW_PROJECT_NAME = "MyProject";
	private static final String CREATE_PROJECT_BUTTON_LABEL = "Create new project";
	private static final String SELECTION_LABEL = "Select involved projects and their monitors:";
	private static final String PAGENAME = "Project and Monitor Selection";
	private static final String DESCRIPTION = "Select projects and their monitors";
	private Map<IProject, Set<VitruvProjectBuilderApplicator>> selectedApplicatorsForProjects;
	private Tree tree;
	private Composite container;
	
	protected ApplicatorSelectionPage() {
		super(PAGENAME);
		setTitle(PAGENAME);
		setDescription(DESCRIPTION);
		this.selectedApplicatorsForProjects = new HashMap<>();
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
							selectedApplicatorsForProjects.get(parent.getData()).add((VitruvProjectBuilderApplicator) item.getData());
						}
					} else {
						// all domains get unselected, when project is unselected
						if (null == parent) {
							for (TreeItem child : item.getItems()) {
								child.setChecked(false);
								selectedApplicatorsForProjects.get(item.getData()).remove(child.getData());
							}
						} else {
							selectedApplicatorsForProjects.get(parent.getData()).remove(item.getData());
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
		Iterable<VitruvProjectBuilderApplicator> applicators = VitruvProjectBuilderApplicator.getApplicators();
		for (IProject project : projects) {
			TreeItem t = new TreeItem(tree, SWT.CHECK);
			t.setText(project.getName());
			t.setData(project);
			selectedApplicatorsForProjects.put(project, new HashSet<>());
			for (VitruvProjectBuilderApplicator applicator : applicators) {
				TreeItem childItem = new TreeItem(t, SWT.CHECK);
				childItem.setText(applicator.getName());
				childItem.setData(applicator);
			}
		}
	}
	
	/**
	 * Returns a HashMap of Projects, where every project is mapped to all of
	 * it's selected applicators, respectively.
	 * 
	 * @return a HashMap that maps all project to their checked applicators,
	 *         respectively
	 */
	public Map<IProject, Set<VitruvProjectBuilderApplicator>> getCheckedApplicators() {
		return selectedApplicatorsForProjects;
	}

	private IProject[] getProjects() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}

	Iterable<VitruvProjectBuilderApplicator> getSelectedApplicators() {
		Set<VitruvProjectBuilderApplicator> applicators = new HashSet<>();
		for (Set<VitruvProjectBuilderApplicator> applicatorsSet : selectedApplicatorsForProjects.values()) {
			for (VitruvProjectBuilderApplicator applicator : applicatorsSet) {
				applicators.add(applicator);
			}
		}
		return applicators;
	}

}
