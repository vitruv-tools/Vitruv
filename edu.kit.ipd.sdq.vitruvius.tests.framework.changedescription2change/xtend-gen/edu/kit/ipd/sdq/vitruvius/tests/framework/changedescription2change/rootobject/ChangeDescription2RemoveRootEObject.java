package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject;

import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject.ChangeDescription2RootChangeTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2RemoveRootEObject extends ChangeDescription2RootChangeTest {
  @Before
  @Override
  public void beforeTest() {
    super.beforeTest();
    EList<EObject> _contents = this.resource.getContents();
    _contents.add(this.rootElement);
    this.startRecordingOnResource();
  }
  
  @Test
  public void testRemoveRootEObject() {
    EList<EObject> _contents = this.resource.getContents();
    _contents.clear();
    this.assertRemoveEObject(false);
  }
  
  @Test
  public void testRemoveRootEObjectWithDelete() {
    EcoreUtil.delete(this.rootElement);
    this.assertRemoveEObject(true);
  }
  
  public void assertRemoveEObject(final boolean isDelete) {
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertRemoveRootEObject(changes, this.rootElement, isDelete);
  }
}
