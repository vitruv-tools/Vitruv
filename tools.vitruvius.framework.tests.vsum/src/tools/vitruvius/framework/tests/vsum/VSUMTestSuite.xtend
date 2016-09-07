package tools.vitruvius.framework.tests.vsum

import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import org.junit.runners.Suite

@SuiteClasses(VSUMPersistentTest, CorrespondenceTest, DefaultTUIDCalculatorTest, FileSystemHelperTest, TUIDCacheTest)
@RunWith(Suite)
class VSUMTestSuite {	
}