package tools.vitruv.framework.vsum.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import java.util.HashMap;
import java.util.HashSet;

public class NewVitruvWizard extends Wizard implements INewWizard {

  private static final String WINDOWTITLE = "New Vitruvius Project";
  protected ProjectNamePage one;
  protected DomainSelectionPage two;
  protected ApplicationSelectionPage three;

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
    three = new ApplicationSelectionPage();
    two = new DomainSelectionPage(three);
    addPage(one);
    addPage(two);
    addPage(three);
  }

  @Override
  public boolean performFinish() {
    System.out.println("Name " + one.getEnteredName());
    HashMap<IProject, HashSet<IExtension>> map = two.getCheckedDomains();
    for (IProject p : map.keySet()) {
      for (IExtension ex : map.get(p)) {
        System.out.println("Ausgewaehlt wurde zu " + p.getName()+": " + ex.getLabel());
      }
    }
    for(IExtension ex : three.getSelectedApplications()){
      System.out.println("Ausgewaehlte Application: "+ex.getLabel());
    }
    return true;
  }
  
  

}
