package tools.vitruv.variability.vave.tests

import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.testutils.TestLogging
import tools.vitruv.testutils.TestProjectManager
import org.junit.jupiter.api.^extension.ExtendWith
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

@ExtendWith(TestProjectManager, TestLogging, RegisterMetamodelsInStandalone)
class VaveTest {
	@Test
	def void testVaveModelCreation() {
		assertTrue(true);
	}
}
