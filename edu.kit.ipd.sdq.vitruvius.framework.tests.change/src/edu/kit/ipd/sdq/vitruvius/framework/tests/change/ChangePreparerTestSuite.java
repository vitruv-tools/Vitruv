package edu.kit.ipd.sdq.vitruvius.framework.tests.change;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.kit.ipd.sdq.vitruvius.framework.tests.change.attribute.ChangeDescription2InsertEAttributeValueTest;
import edu.kit.ipd.sdq.vitruvius.framework.tests.change.attribute.ChangeDescription2RemoveEAttributeValueTest;
import edu.kit.ipd.sdq.vitruvius.framework.tests.change.attribute.ChangeDescription2ReplaceSingleValuedEAttributeTest;
import edu.kit.ipd.sdq.vitruvius.framework.tests.change.integration.ChangeDescriptionComplexSequencesTest;
import edu.kit.ipd.sdq.vitruvius.framework.tests.change.reference.ChangeDescription2InsertEReferenceTest;
import edu.kit.ipd.sdq.vitruvius.framework.tests.change.reference.ChangeDescription2RemoveEReferenceTest;
import edu.kit.ipd.sdq.vitruvius.framework.tests.change.reference.ChangeDescription2ReplaceSingleValuedEReferenceTest;
import edu.kit.ipd.sdq.vitruvius.framework.tests.change.rootobject.ChangeDescription2InsertRootEObjectTest;
import edu.kit.ipd.sdq.vitruvius.framework.tests.change.rootobject.ChangeDescription2RemoveRootEObject;

@RunWith(Suite.class)

@SuiteClasses({ ChangeDescription2InsertEAttributeValueTest.class,
		ChangeDescription2RemoveEAttributeValueTest.class, ChangeDescription2ReplaceSingleValuedEAttributeTest.class,
		ChangeDescription2InsertEReferenceTest.class, ChangeDescription2RemoveEReferenceTest.class,
		ChangeDescription2ReplaceSingleValuedEReferenceTest.class, ChangeDescription2InsertRootEObjectTest.class,
		ChangeDescription2RemoveRootEObject.class, ChangeDescriptionComplexSequencesTest.class})
public class ChangePreparerTestSuite {

}
