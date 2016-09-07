package tools.vitruvius.applications.jmljava.tests.plugintests;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

public class ChangeInvariantTest extends FrameworkTestBase {

	@Ignore
	@Test
	public void testRemoveMethodOnlyUsedInThisPlace() throws Exception {
		fail("No methods are used inside invariants/constraints in the evaluation project.");
	}
	
	@Ignore
	@Test
	public void testRemoveMethodUsedInAnotherPlace() throws Exception {
		fail("No methods are used inside invariants/constraints in the evaluation project.");
	}
	
	@Ignore
	@Test
	public void testAddMethodNotUsedInAnyOtherPlace() throws Exception {
		fail("No methods are used inside invariants/constraints in the evaluation project.");
	}
	
	@Ignore
	@Test
	public void testAddMethodUsedInAnotherPlace() throws Exception {
		fail("No methods are used inside invariants/constraints in the evaluation project.");
	}
	
}
