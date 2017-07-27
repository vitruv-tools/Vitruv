package tools.vitruv.dsls.reactions.tests.versioning;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruv.dsls.reactions.tests.versioning.TheirAdditionalElementTest;
import tools.vitruv.dsls.reactions.tests.versioning.ConflictExistsGraphIsomorphismTest;
import tools.vitruv.dsls.reactions.tests.versioning.ConflictNotExistsDependencyGraphTest;
import tools.vitruv.dsls.reactions.tests.versioning.ConflictNotExistsGraphIsomorphismTest;
import tools.vitruv.dsls.reactions.tests.versioning.ReapplyTest;
import tools.vitruv.dsls.reactions.tests.versioning.SourceTargetRecorderTest;

@RunWith(Suite.class)
@SuiteClasses({ ConflictNotExistsDependencyGraphTest.class, ConflictNotExistsGraphIsomorphismTest.class,
		ReapplyTest.class, SourceTargetRecorderTest.class, ConflictExistsGraphIsomorphismTest.class,
		TheirAdditionalElementTest.class, MyAdditionalElementTest.class, HardConflictTest.class,
		EMFStoreBaseline.class })

public class VersioningReactionTests {

}
