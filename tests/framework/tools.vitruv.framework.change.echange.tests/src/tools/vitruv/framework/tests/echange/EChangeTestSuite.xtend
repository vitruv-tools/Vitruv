package tools.vitruv.framework.tests.echange

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.framework.tests.echange.command.AddToStagingAreaCommandTest
import tools.vitruv.framework.tests.echange.command.RemoveAtCommandTest
import tools.vitruv.framework.tests.echange.command.RemoveFromStagingAreaCommandTest
import tools.vitruv.framework.tests.echange.compound.CreateAndInsertNonRootTest
import tools.vitruv.framework.tests.echange.compound.CreateAndInsertRootTest
import tools.vitruv.framework.tests.echange.compound.CreateAndReplaceAndDeleteNonRootTest
import tools.vitruv.framework.tests.echange.compound.CreateAndReplaceNonRootTest
import tools.vitruv.framework.tests.echange.compound.ExplicitUnsetEAttributeTest
import tools.vitruv.framework.tests.echange.compound.ExplicitUnsetEReferenceTest
import tools.vitruv.framework.tests.echange.compound.RemoveAndDeleteNonRootTest
import tools.vitruv.framework.tests.echange.compound.RemoveAndDeleteRootTest
import tools.vitruv.framework.tests.echange.compound.ReplaceAndDeleteNonRootTest
import tools.vitruv.framework.tests.echange.eobject.CreateEObjectTest
import tools.vitruv.framework.tests.echange.eobject.DeleteEObjectTest
import tools.vitruv.framework.tests.echange.eobject.EObjectExistenceEChangeTest
import tools.vitruv.framework.tests.echange.feature.FeatureEChangeTest
import tools.vitruv.framework.tests.echange.feature.attribute.InsertEAttributeValueTest
import tools.vitruv.framework.tests.echange.feature.attribute.RemoveEAttributeValueTest
import tools.vitruv.framework.tests.echange.feature.attribute.ReplaceSingleValuedEAttributeTest
import tools.vitruv.framework.tests.echange.feature.reference.InsertEReferenceTest
import tools.vitruv.framework.tests.echange.feature.reference.RemoveEReferenceTest
import tools.vitruv.framework.tests.echange.feature.reference.ReplaceSingleValuedEReferenceTest
import tools.vitruv.framework.tests.echange.root.InsertRootEObjectTest
import tools.vitruv.framework.tests.echange.root.RemoveRootEObjectTest

@RunWith(Suite) @SuiteClasses(#[ // Atomic changes
FeatureEChangeTest, InsertEAttributeValueTest, RemoveEAttributeValueTest, ReplaceSingleValuedEAttributeTest,
	InsertEReferenceTest, RemoveEReferenceTest, ReplaceSingleValuedEReferenceTest, InsertRootEObjectTest,
	RemoveRootEObjectTest, EObjectExistenceEChangeTest, CreateEObjectTest, DeleteEObjectTest, // Compound changes
	ExplicitUnsetEAttributeTest, ExplicitUnsetEReferenceTest, CreateAndInsertRootTest, RemoveAndDeleteRootTest,
	CreateAndInsertNonRootTest, RemoveAndDeleteNonRootTest, CreateAndReplaceNonRootTest, ReplaceAndDeleteNonRootTest,
	CreateAndReplaceAndDeleteNonRootTest, // Additional tests
	RemoveAtCommandTest, AddToStagingAreaCommandTest, RemoveFromStagingAreaCommandTest])
class EChangeTestSuite {
}
