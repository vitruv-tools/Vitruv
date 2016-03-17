package edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.rootobject;

import edu.kit.ipd.sdq.vitruvius.tests.framework.changedescription2change.ChangeDescription2ChangeTransformationTest;
import java.util.Collections;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.Before;

@SuppressWarnings("all")
public class ChangeDescription2RootChangeTest extends ChangeDescription2ChangeTransformationTest {
  protected Resource resource;
  
  @Before
  @Override
  public void beforeTest() {
    super.beforeTest();
    final ResourceSetImpl rs = new ResourceSetImpl();
    URI _createFileURI = URI.createFileURI("dummyURI");
    rs.createResource(_createFileURI);
  }
  
  protected void startRecordingOnResource() {
    this.changeRecorder.beginRecording(Collections.<Object>unmodifiableList(CollectionLiterals.<Object>newArrayList(this.resource)));
  }
}
