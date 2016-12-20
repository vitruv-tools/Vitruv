package tools.vitruv.framework.vsum.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
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

public class ModelSelectionPage extends WizardPage {

  private static final String PAGENAME = "Vitruvius Project";
  private static final String DESCRIPTION = "Create a new Vitruvius Project.";
  private int numOfChecked;

  private Text text1;

  private Composite container;

  protected ModelSelectionPage() {
    super(PAGENAME);
    setTitle(PAGENAME);
    setDescription(DESCRIPTION);
    numOfChecked = 0;
    // setControl(text1);

  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    container.setLayout(layout);
    layout.numColumns = 1;
    Label label1 = new Label(container, SWT.NONE);
    label1.setText("Say hello to Fred");
    // text1 = new Text(container, SWT.BORDER | SWT.SINGLE);

    IProject projects[] = getProjects();

    final Tree tree = new Tree(container, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    System.out.println(projects.length);
    tree.setItemCount(projects.length);

    GridData treeGridData = new GridData(GridData.FILL_HORIZONTAL);
    tree.setLayoutData(treeGridData);
    for (int i = 0; i < projects.length; i++) {
      System.out.println(projects[i] + " " + i);
    }
    System.out.println(projects);
    for (IProject project : projects) {
      System.out.println("Loop");
      TreeItem t = new TreeItem(tree, SWT.CHECK);
      t.setText(project.getName());
      for (int i = 0; i < 3; i++) {
        TreeItem childItem = new TreeItem(t, SWT.CHECK);
        childItem.setText("Model " + i);
      }
    }
    tree.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        if (event.detail == SWT.CHECK) {
          TreeItem item = (TreeItem) event.item;
          System.out.println(item.getClass());
          System.out.println(item);
          System.out.println(item.getParentItem());
          TreeItem parent = item.getParentItem();
          if (item.getChecked()) {
            // check a project automatically, when one of it's model is checked
            if (null != parent) {
              parent.setChecked(true);
              //TODO schlechte idee. zählt auch, wen schon geckecht
              numOfChecked++;
            }
            numOfChecked++;
          } else {
            // all models get deselected, when project is deselected.
            if (null == parent) {
              for (TreeItem child : item.getItems()) {
                child.setChecked(false);
                numOfChecked--;
              }
            }
            numOfChecked--;
          }
          if(numOfChecked > 0){
            setPageComplete(true);
          } else{
            setPageComplete(false);
          }
        }
        System.out.println(numOfChecked);
      }
    });

    // text1.setText("");
    // text1.addKeyListener(new KeyListener() {
    //
    // @Override
    // public void keyPressed(KeyEvent e) {
    // // TODO Auto-generated method stub
    // }
    //
    // @Override
    // public void keyReleased(KeyEvent e) {
    // if (!text1.getText().isEmpty()) {
    // setPageComplete(true);
    // }
    // }
    //
    // });
    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    // text1.setLayoutData(gd);
    Label labelCheck = new Label(container, NONE);
    labelCheck.setText("This is a check");
    Button b = new Button(container, SWT.CHECK);
    b.setSelection(true);

    setControl(container);
    setPageComplete(false);
  }

  public Text getText1() {
    return text1;
  }

  private IProject[] getProjects() {
    return ResourcesPlugin.getWorkspace().getRoot().getProjects();
  }

}
