package tools.vitruv.framework.vsum.accesscontrolsystem.test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import accesscontrol.OperationAccessRightEvaluator;
import accesscontrol.operationaccessright.OperationAccessRightUtil;

class OperationAccessRightUtilTest {

	@Test
	final void testHasAccessCollectionOfOperationAccessRightCollectionOfOperationAccessRight() {
		// granted rights exactly match needed ones
		OperationAccessRightEvaluator evaluator = new OperationAccessRightUtil();
		assertTrue(evaluator.hasAccess(
				Arrays.asList(evaluator.allowRead(), evaluator.allowWrite()),
				Arrays.asList(evaluator.allowRead(), evaluator.allowWrite())));
		// granted rights are less than needed ones
		assertFalse(evaluator.hasAccess(
				Arrays.asList(evaluator.allowRead(), evaluator.denyWrite()),
				Arrays.asList(evaluator.allowRead(), evaluator.allowWrite())));
		// no right for a needed operation granted
		assertFalse(evaluator.hasAccess(
				Arrays.asList(evaluator.allowRead()),
				Arrays.asList(evaluator.allowRead(), evaluator.allowWrite())));
		// needed rights are less than granted ones
		assertTrue(evaluator.hasAccess(
				Arrays.asList(evaluator.allowRead(), evaluator.allowWrite()),
				Arrays.asList(evaluator.denyRead(), evaluator.denyWrite())));
	}

	@Test
	final void testHasAccessCollectionOfOperationAccessRightOperationAccessRight() {
		OperationAccessRightEvaluator evaluator = new OperationAccessRightUtil();
		assertFalse(evaluator.hasAccess(List.of(), List.of(evaluator.allowRead())));
		assertTrue(evaluator.hasAccess(List.of(evaluator.allowRead()),
				List.of(evaluator.allowRead())));
		assertTrue(evaluator.hasAccess(
				List.of(evaluator.allowRead(), evaluator.denyWrite()),
						List.of(evaluator.allowRead())));
		assertTrue(evaluator.hasAccess(
				List.of(evaluator.allowRead(), evaluator.allowWrite()),
				List.of(evaluator.allowRead())));
		assertFalse(evaluator.hasAccess(
				List.of(evaluator.allowRead(), evaluator.denyRead()),
				List.of(evaluator.allowRead())));

		assertFalse(evaluator.hasAccess(
				List.of(evaluator.denyRead(), evaluator.allowWrite()),
				List.of(evaluator.allowRead())));
	}

	@Test
	final void testHasAccessOperationAccessRightOperationAccessRight() {
		OperationAccessRightEvaluator evaluator = new OperationAccessRightUtil();
		assertTrue(evaluator.hasAccess(List.of(evaluator.allowRead()),
				List.of(evaluator.allowRead())));
		assertTrue(evaluator.hasAccess(List.of(evaluator.allowRead()),
				List.of(evaluator.denyRead())));
		assertTrue(evaluator.hasAccess(List.of(evaluator.denyRead()),
				List.of(evaluator.denyRead())));

		assertFalse(evaluator.hasAccess(List.of(evaluator.denyRead()),
				List.of(evaluator.allowRead())));
	}

}
