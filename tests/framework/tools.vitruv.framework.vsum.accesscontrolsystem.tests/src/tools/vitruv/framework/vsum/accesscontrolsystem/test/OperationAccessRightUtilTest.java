package tools.vitruv.framework.vsum.accesscontrolsystem.test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import accesscontrol.OperationAccessRightEvaluator;
import accesscontrol.OperationAccessRightUtil;
import accesscontrol.internal.OperationAccessRightEvaluatorImpl;

class OperationAccessRightUtilTest {

	@Test
	final void testHasAccessCollectionOfOperationAccessRightCollectionOfOperationAccessRight() {
		// granted rights exactly match needed ones
		OperationAccessRightEvaluator evaluator = new OperationAccessRightEvaluatorImpl();
		assertTrue(evaluator.hasAccess(
				Arrays.asList(OperationAccessRightUtil.allowRead(), OperationAccessRightUtil.allowWrite()),
				Arrays.asList(OperationAccessRightUtil.allowRead(), OperationAccessRightUtil.allowWrite())));
		// granted rights are less than needed ones
		assertFalse(evaluator.hasAccess(
				Arrays.asList(OperationAccessRightUtil.allowRead(), OperationAccessRightUtil.denyWrite()),
				Arrays.asList(OperationAccessRightUtil.allowRead(), OperationAccessRightUtil.allowWrite())));
		// no right for a needed operation granted
		assertFalse(evaluator.hasAccess(
				Arrays.asList(OperationAccessRightUtil.allowRead()),
				Arrays.asList(OperationAccessRightUtil.allowRead(), OperationAccessRightUtil.allowWrite())));
		// needed rights are less than granted ones
		assertTrue(evaluator.hasAccess(
				Arrays.asList(OperationAccessRightUtil.allowRead(), OperationAccessRightUtil.allowWrite()),
				Arrays.asList(OperationAccessRightUtil.denyRead(), OperationAccessRightUtil.denyWrite())));
	}

	@Test
	final void testHasAccessCollectionOfOperationAccessRightOperationAccessRight() {
		OperationAccessRightEvaluator evaluator = new OperationAccessRightEvaluatorImpl();
		assertFalse(evaluator.hasAccess(List.of(), List.of(OperationAccessRightUtil.allowRead())));
		assertTrue(evaluator.hasAccess(List.of(OperationAccessRightUtil.allowRead()),
				List.of(OperationAccessRightUtil.allowRead())));
		assertTrue(evaluator.hasAccess(
				List.of(OperationAccessRightUtil.allowRead(), OperationAccessRightUtil.denyWrite()),
						List.of(OperationAccessRightUtil.allowRead())));
		assertTrue(evaluator.hasAccess(
				List.of(OperationAccessRightUtil.allowRead(), OperationAccessRightUtil.allowWrite()),
				List.of(OperationAccessRightUtil.allowRead())));
		assertFalse(evaluator.hasAccess(
				List.of(OperationAccessRightUtil.allowRead(), OperationAccessRightUtil.denyRead()),
				List.of(OperationAccessRightUtil.allowRead())));

		assertFalse(evaluator.hasAccess(
				List.of(OperationAccessRightUtil.denyRead(), OperationAccessRightUtil.allowWrite()),
				List.of(OperationAccessRightUtil.allowRead())));
	}

	@Test
	final void testHasAccessOperationAccessRightOperationAccessRight() {
		OperationAccessRightEvaluator evaluator = new OperationAccessRightEvaluatorImpl();
		assertTrue(evaluator.hasAccess(List.of(OperationAccessRightUtil.allowRead()),
				List.of(OperationAccessRightUtil.allowRead())));
		assertTrue(evaluator.hasAccess(List.of(OperationAccessRightUtil.allowRead()),
				List.of(OperationAccessRightUtil.denyRead())));
		assertTrue(evaluator.hasAccess(List.of(OperationAccessRightUtil.denyRead()),
				List.of(OperationAccessRightUtil.denyRead())));

		assertFalse(evaluator.hasAccess(List.of(OperationAccessRightUtil.denyRead()),
				List.of(OperationAccessRightUtil.allowRead())));
	}

}
