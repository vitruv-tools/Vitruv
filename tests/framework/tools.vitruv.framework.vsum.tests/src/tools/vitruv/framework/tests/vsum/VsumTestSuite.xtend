package tools.vitruv.framework.tests.vsum

import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import org.junit.runners.Suite

@SuiteClasses(VsumPersistenceTest, CorrespondenceTest, DefaultTuidCalculatorTest, FileSystemHelperTest, TuidCacheTest, SimpleVsumTest)
@RunWith(Suite)
class VsumTestSuite {	
}