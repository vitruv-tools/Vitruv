package tools.vitruv.dsls.reactions.tests.versioning

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import tools.vitruv.dsls.reactions.tests.versioning.TheirAdditionalElementTest
import tools.vitruv.dsls.reactions.tests.versioning.ConflictExistsGraphIsomorphismTest
import tools.vitruv.dsls.reactions.tests.versioning.ConflictNotExistsDependencyGraphTest
import tools.vitruv.dsls.reactions.tests.versioning.ConflictNotExistsGraphIsomorphismTest
import tools.vitruv.dsls.reactions.tests.versioning.ReapplyTest
import tools.vitruv.dsls.reactions.tests.versioning.SourceTargetRecorderTest

@RunWith(Suite)
@SuiteClasses(#[ConflictNotExistsDependencyGraphTest, ConflictNotExistsGraphIsomorphismTest, ReapplyTest,
	SourceTargetRecorderTest, ConflictExistsGraphIsomorphismTest, TheirAdditionalElementTest, MyAdditionalElementTest,
	HardConflictTest, EMFStoreBaseline])
class VersioningReactionTests {
	
}
