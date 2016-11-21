package tools.vitruv.framework.tests.vsum

import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import org.junit.runners.Suite

@SuiteClasses(VSUMPersistentTest, CorrespondenceTest, DefaultTUIDCalculatorTest, FileSystemHelperTest, TUIDCacheTest, SimpleVSUMTest)
@RunWith(Suite)
class VSUMTestSuite {	
}