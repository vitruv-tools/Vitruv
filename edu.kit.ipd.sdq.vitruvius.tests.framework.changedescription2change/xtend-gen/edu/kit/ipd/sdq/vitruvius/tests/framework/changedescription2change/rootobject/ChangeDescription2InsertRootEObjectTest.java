package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject;

import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject.ChangeDescription2RootChangeTest;
import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.util.ChangeAssertHelper;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

@SuppressWarnings("all")
public class ChangeDescription2InsertRootEObjectTest extends ChangeDescription2RootChangeTest {
  @Test
  public void insertRootEObjectInResource() {
    this.startRecordingOnResource();
    EList<EObject> _contents = this.resource.getContents();
    _contents.add(this.rootElement);
    final List<?> changes = this.getChanges();
    ChangeAssertHelper.assertInsertRootEObject(changes, this.rootElement, false);
  }
}
