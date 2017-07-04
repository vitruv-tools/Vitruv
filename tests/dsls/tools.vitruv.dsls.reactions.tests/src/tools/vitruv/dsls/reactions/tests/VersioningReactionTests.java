package tools.vitruv.dsls.reactions.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tools.vitruv.dsls.reactions.tests.simpleChangesTests.ConflictExistsGraphIsomorphismTest;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.ConflictNotExistsDependencyGraphTest;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.ConflictNotExistsGraphIsomorphismTest;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.ReapplyTest;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SourceTargetRecorderTest;

@RunWith(Suite.class)
@SuiteClasses({ 
		ConflictNotExistsDependencyGraphTest.class, ConflictNotExistsGraphIsomorphismTest.class, ReapplyTest.class,
		SourceTargetRecorderTest.class, ConflictExistsGraphIsomorphismTest.class

})
public class VersioningReactionTests {

}
