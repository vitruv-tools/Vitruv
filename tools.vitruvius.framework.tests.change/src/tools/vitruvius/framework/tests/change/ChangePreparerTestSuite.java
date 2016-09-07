package tools.vitruvius.framework.tests.change;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruvius.framework.tests.change.attribute.ChangeDescription2InsertEAttributeValueTest;
import tools.vitruvius.framework.tests.change.attribute.ChangeDescription2RemoveEAttributeValueTest;
import tools.vitruvius.framework.tests.change.attribute.ChangeDescription2ReplaceSingleValuedEAttributeTest;
import tools.vitruvius.framework.tests.change.integration.ChangeDescriptionComplexSequencesTest;
import tools.vitruvius.framework.tests.change.reference.ChangeDescription2InsertEReferenceTest;
import tools.vitruvius.framework.tests.change.reference.ChangeDescription2RemoveEReferenceTest;
import tools.vitruvius.framework.tests.change.reference.ChangeDescription2ReplaceSingleValuedEReferenceTest;
import tools.vitruvius.framework.tests.change.rootobject.ChangeDescription2InsertRootEObjectTest;
import tools.vitruvius.framework.tests.change.rootobject.ChangeDescription2RemoveRootEObject;

@RunWith(Suite.class)

@SuiteClasses({ ChangeDescription2InsertEAttributeValueTest.class,
		ChangeDescription2RemoveEAttributeValueTest.class, ChangeDescription2ReplaceSingleValuedEAttributeTest.class,
		ChangeDescription2InsertEReferenceTest.class, ChangeDescription2RemoveEReferenceTest.class,
		ChangeDescription2ReplaceSingleValuedEReferenceTest.class, ChangeDescription2InsertRootEObjectTest.class,
		ChangeDescription2RemoveRootEObject.class, ChangeDescriptionComplexSequencesTest.class})
public class ChangePreparerTestSuite {

}
