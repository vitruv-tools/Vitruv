package tools.vitruv.framework.tests.change;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruv.framework.tests.change.attribute.ChangeDescription2InsertEAttributeValueTest;
import tools.vitruv.framework.tests.change.attribute.ChangeDescription2RemoveEAttributeValueTest;
import tools.vitruv.framework.tests.change.attribute.ChangeDescription2ReplaceSingleValuedEAttributeTest;
import tools.vitruv.framework.tests.change.compound.ChangeDescription2MoveEObjectTest;
import tools.vitruv.framework.tests.change.integration.ChangeDescriptionComplexSequencesTest;
import tools.vitruv.framework.tests.change.reference.ChangeDescription2InsertEReferenceTest;
import tools.vitruv.framework.tests.change.reference.ChangeDescription2RemoveEReferenceTest;
import tools.vitruv.framework.tests.change.reference.ChangeDescription2ReplaceSingleValuedEReferenceTest;
import tools.vitruv.framework.tests.change.rootobject.ChangeDescription2InsertRootEObjectTest;
import tools.vitruv.framework.tests.change.rootobject.ChangeDescription2MoveRootTest;
import tools.vitruv.framework.tests.change.rootobject.ChangeDescription2RemoveRootEObject;

@RunWith(Suite.class)

@SuiteClasses({ ChangeDescription2InsertEAttributeValueTest.class,
		ChangeDescription2RemoveEAttributeValueTest.class, ChangeDescription2ReplaceSingleValuedEAttributeTest.class,
		ChangeDescription2InsertEReferenceTest.class, ChangeDescription2RemoveEReferenceTest.class,
		ChangeDescription2ReplaceSingleValuedEReferenceTest.class, ChangeDescription2InsertRootEObjectTest.class,
		ChangeDescription2RemoveRootEObject.class, ChangeDescription2MoveEObjectTest.class,
		ChangeDescription2MoveRootTest.class, ChangeDescriptionComplexSequencesTest.class})
public class ChangePreparerTestSuite {

}
