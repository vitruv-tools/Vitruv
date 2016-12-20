package tools.vitruv.framework.vsum.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewVitruvWizard extends Wizard implements INewWizard {
  
  private static final String WINDOWTITLE = "New Vitruvius Project";
  protected ProjectNamePage one;
  protected ModelSelectionPage two;

  public NewVitruvWizard() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    // TODO Auto-generated method stub

  }

  @Override
  public String getWindowTitle() {
    return WINDOWTITLE;
  }
  
  @Override
  public void addPages() {
    one = new ProjectNamePage(this);
    two = new ModelSelectionPage();
    addPage(one);
    addPage(two);
  }
  
  @Override
  public boolean performFinish() {
//    System.out.println(one.getText1());
//    System.out.println(two.getText1());
    return true;
  }

}
