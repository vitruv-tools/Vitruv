package tools.vitruv.framework.versioning.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite)
@SuiteClasses(#[ConflictNotExistsDependencyGraphTest, ConflictNotExistsGraphIsomorphismTest, ReapplyTest,
	SourceTargetRecorderTest, ConflictExistsGraphIsomorphismTest, TheirAdditionalElementTest, MyAdditionalElementTest,
	HardConflictTest, EMFStoreBaseline])
class VersioningReactionTests {
	
}
