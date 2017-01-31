package tools.vitruv.framework.vsum.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.util.Collection;
import java.util.HashSet;

public class ApplicationSelectionPage extends WizardPage {

  private static final String PAGENAME = "Vitruvius Project";
  private static final String DESCRIPTION = "Create a new Vitruvius Project.";

  private Composite container;
  private Collection<IExtension> selectedApplications;

  protected ApplicationSelectionPage() {
    super(PAGENAME);
    setTitle(PAGENAME);
    setDescription(DESCRIPTION);
    selectedApplications = new HashSet<>();
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    container.setLayout(layout);
    layout.numColumns = 1;
    Label label1 = new Label(container, SWT.NONE);
    label1.setText("Select your applications.");
    
    final Tree tree = new Tree(container, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    GridData treeGridData = new GridData(GridData.FILL_BOTH);
    tree.setLayoutData(treeGridData);


    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IExtensionPoint ep = reg.getExtensionPoint("tools.vitruv.framework.vsum.application");
    IExtension[] extensions = null;
    if (ep != null) {
      extensions = ep.getExtensions();
    }
    
    for (IExtension ex : extensions) {
      TreeItem t = new TreeItem(tree, SWT.CHECK);
      t.setText(ex.getLabel());
      t.setData(ex);
    }

    tree.addListener(SWT.Selection, new Listener() {
      
      @Override
      public void handleEvent(Event event) {
        if (event.detail == SWT.CHECK){
          TreeItem item = (TreeItem) event.item;
          if (item.getChecked()) {
            selectedApplications.add((IExtension)item.getData());
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
}
