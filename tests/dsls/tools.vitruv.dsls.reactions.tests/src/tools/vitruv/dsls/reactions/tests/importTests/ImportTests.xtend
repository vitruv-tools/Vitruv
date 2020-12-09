package tools.vitruv.dsls.reactions.tests.importTests

import allElementTypes.Root
import java.util.EnumSet
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo

import static org.hamcrest.CoreMatchers.hasItem
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.dsls.reactions.tests.ExecutionMonitor.observedExecutions
import static tools.vitruv.dsls.reactions.tests.importTests.ImportTestsExecutionMonitor.ExecutionType.*
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes
import tools.vitruv.dsls.reactions.tests.ReactionsExecutionTest
import tools.vitruv.dsls.reactions.tests.TestReactionsCompiler

class ImportTests extends ReactionsExecutionTest {
	static val SOURCE_MODEL = 'ImportTestsModelSource'.allElementTypes
	String testName

	override protected createCompiler(TestReactionsCompiler.Factory factory) {
		factory.createCompiler [
			// ordered: segments with imports after their imported segments
			reactions = #["CommonRoutines.reactions", "TransitiveRoutinesQN.reactions",
				"TransitiveRoutinesSN.reactions", "Transitive2SN.reactions", "Transitive3SN.reactions",
				"TransitiveSN.reactions", "DirectRoutinesQN.reactions", "Direct2SN.reactions", "DirectSN.reactions",
				"Root.reactions"]
			changePropagationSegments = #["importTestsRoot"]
		]
	}

	private static def ImportTestsExecutionMonitor getExecutionMonitor() {
		ImportTestsExecutionMonitor.instance
	}

	@BeforeEach
	def void createRoot() {
		resourceAt(SOURCE_MODEL).propagate [
			contents += aet.Root => [id = 'ImportTestsModelSource']
		]
	}

	@BeforeEach
	def void captureTestName(TestInfo testInfo) {
		testName = testInfo.displayName
	}

	@BeforeEach
	def resetExecutionMonitor() {
		executionMonitor.reset()
	}

	private def triggerSetRootIdReaction(String... dataTags) {
		Root.from(SOURCE_MODEL).propagate [
			id = ImportTestsUtils.toTestDataString(testName, dataTags)
		]
	}

	// all reactions included by the root segment:
	static val rootReactions = EnumSet.of(RootReaction, DirectSNReaction, Direct2SNReaction, TransitiveSNReaction,
		Transitive3SNReaction)

	/*
	 * Import hierarchy:
	 * 
	 * root -> directSN, direct2SN, directRoutinesQN r qn
	 * directSN -> transitiveSN, transitiveRoutinesQN r qn
	 * direct2SN -> transitive3SN
	 * directRoutinesQN -> transitive2SN, transitiveRoutinesSN r, transitiveRoutinesQN r qn, commonRoutines r qn
	 * transitiveSN -> commonRoutines r qn
	 * transitive2SN
	 * transitive3SN
	 * transitiveRoutinesSN
	 * transitiveRoutinesQN
	 * commonRoutines
	 */
	// data tags:
	public static val TAG_CALL_ROUTINE_FROM_REACTION = "callRoutineFromReaction";
	public static val TAG_CALL_ROUTINE_FROM_ROUTINE = "callRoutineFromRoutine";
	public static val TAG_ROOT_ROUTINE = "rootRoutine";
	public static val TAG_DIRECT_ROUTINE_SN = "directRoutine_SN";
	public static val TAG_DIRECT2_ROUTINE_SN = "direct2Routine_SN";
	public static val TAG_DIRECT_ROUTINE_QN = "directRoutine_QN";
	public static val TAG_TRANSITIVE_ROUTINE_SN_SN = "transitiveRoutine_SN_SN";
	public static val TAG_TRANSITIVE_ROUTINE_SN_QN = "transitiveRoutine_SN_QN";
	public static val TAG_TRANSITIVE_ROUTINE_QN_SN = "transitiveRoutine_QN_SN";
	public static val TAG_TRANSITIVE_ROUTINE_QN_QN = "transitiveRoutine_QN_QN";

	public static val TAG_TEST_ROUTINES_ONLY_REACTIONS = "testRoutinesOnlyReactions";
	public static val TAG_TEST_ROUTINES_ONLY_ROUTINES = "testRoutinesOnlyRoutines";
	public static val TAG_TEST_IMPORTED_SEGMENTS_WORKING = "testImportedSegmentsWorking";
	public static val TAG_TEST_REACTION_OVERRIDE = "testReactionOverride";
	public static val TAG_TEST_TRANSITIVE_REACTION_OVERRIDE = "testTransitiveReactionOverride";

	public static val TAG_CALL_OVERRIDDEN_ROUTINE = "callOverriddenRoutine";
	public static val TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE = "callOverriddenTransitiveRoutine";
	public static val TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE_WITH_SEPARATE_OVERRIDE_HIERARCHY = "callOverriddenTransitiveRoutineWithSeparateOverrideHierarchy";
	public static val TAG_CALL_ALREADY_OVERRIDDEN_TRANSITIVE_ROUTINE = "callAlreadyOverriddenTransitiveRoutine";
	public static val TAG_CALL_TRANSITIVE_ROUTINE_OVERRIDDEN_BY_IMPORTED_SEGMENT = "callTransitiveRoutineOverriddenByImportedSegment";
	public static val TAG_FROM_ROOT = "fromRoot";
	public static val TAG_FROM_OVERRIDDEN_SEGMENT = "fromOverriddenSegment";
	public static val TAG_FROM_SEGMENT_IN_BETWEEN = "fromSegmentInBetween";

	public static val TAG_TEST_MULTIPLE_IMPORTS_OF_SAME_ROUTINES_IMPORT_PATH_1 = "testMultipleImportsOfSameRoutinesImportPath1";
	public static val TAG_TEST_MULTIPLE_IMPORTS_OF_SAME_ROUTINES_IMPORT_PATH_2 = "testMultipleImportsOfSameRoutinesImportPath2";

	// execute imported and transitive imported reactions:
	@Test
	def void testRootReaction() {
		triggerSetRootIdReaction()
		assertThat(executionMonitor.getObservedExecutions, hasItem(RootReaction))
	}

	@Test
	def void testImportedReaction() {
		triggerSetRootIdReaction()
		assertThat(executionMonitor.getObservedExecutions, hasItem(DirectSNReaction))
	}

	@Test
	def void testTransitiveImportedReaction() {
		triggerSetRootIdReaction()
		assertThat(executionMonitor.getObservedExecutions, hasItem(TransitiveSNReaction))
	}

	// multiple imports: combination of imported reactions
	@Test
	def void testMultipleImportedReactions() {
		triggerSetRootIdReaction()
		assertThat(executionMonitor, observedExecutions(rootReactions))
	}

	// call imported routines from within reaction:
	@Test
	def void testReactionCallsRootRoutine() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_ROOT_ROUTINE)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[RootRoutine]))
	}

	@Test
	def void testReactionCallsImportedRoutine_SN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_DIRECT_ROUTINE_SN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[DirectSNRoutine]))
	}

	@Test
	def void testReactionCallsImportedRoutine_QN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_DIRECT_ROUTINE_QN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[DirectRoutinesQNRoutine]))
	}

	// call transitive imported routines from within reaction:
	@Test
	def void testReactionCallsTransitiveImportedRoutine_SN_SN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_TRANSITIVE_ROUTINE_SN_SN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[TransitiveSNRoutine]))
	}

	@Test
	def void testReactionCallsTransitiveImportedRoutine_SN_QN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_TRANSITIVE_ROUTINE_SN_QN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[TransitiveRoutinesQNRoutine]))
	}

	@Test
	def void testReactionCallsTransitiveImportedRoutine_QN_SN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_TRANSITIVE_ROUTINE_QN_SN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[TransitiveRoutinesSNRoutine]))
	}

	@Test
	def void testReactionCallsTransitiveImportedRoutine_QN_QN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_TRANSITIVE_ROUTINE_QN_QN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[TransitiveRoutinesQNRoutine]))
	}

	// call imported routines from within routine:
	@Test
	def void testRoutineCallsRootRoutine() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_ROOT_ROUTINE)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[RootRoutine]))
	}

	@Test
	def void testRoutineCallsImportedRoutine_SN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_DIRECT_ROUTINE_SN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[DirectSNRoutine]))
	}

	@Test
	def void testRoutineCallsImportedRoutine_QN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_DIRECT_ROUTINE_QN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[DirectRoutinesQNRoutine]))
	}

	// call transitive imported routines from within routine:
	@Test
	def void testRoutineCallsTransitiveImportedRoutine_SN_SN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_TRANSITIVE_ROUTINE_SN_SN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[TransitiveSNRoutine]))
	}

	@Test
	def void testRoutineCallsTransitiveImportedRoutine_SN_QN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_TRANSITIVE_ROUTINE_SN_QN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[TransitiveRoutinesQNRoutine]))
	}

	@Test
	def void testRoutineCallsTransitiveImportedRoutine_QN_SN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_TRANSITIVE_ROUTINE_QN_SN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[TransitiveRoutinesSNRoutine]))
	}

	@Test
	def void testRoutineCallsTransitiveImportedRoutine_QN_QN() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_TRANSITIVE_ROUTINE_QN_QN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[TransitiveRoutinesQNRoutine]))
	}

	// multiple imports: combination of imported routines
	@Test
	def void testMultipleImportedRoutines() {
		triggerSetRootIdReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_ROOT_ROUTINE, TAG_DIRECT_ROUTINE_SN,
			TAG_DIRECT2_ROUTINE_SN, TAG_TRANSITIVE_ROUTINE_SN_SN)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[
			RootRoutine,
			DirectSNRoutine,
			Direct2SNRoutine,
			TransitiveSNRoutine
		]))
	}

	// import only routines:
	@Test
	def void testRoutinesOnlyReactions() {
		triggerSetRootIdReaction(TAG_TEST_ROUTINES_ONLY_REACTIONS)
		assertThat(executionMonitor, observedExecutions(rootReactions))
	}

	@Test
	def void testRoutinesOnlyRoutines() {
		triggerSetRootIdReaction(TAG_TEST_ROUTINES_ONLY_ROUTINES)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[
			// direct: routines-only 
			DirectRoutinesQNRoutine,
			// transitive: full > routines-only
			TransitiveRoutinesQNRoutine,
			// transitive: routines-only QN > routines-only SN
			TransitiveRoutinesSNRoutine,
			// transitive: routines-only > full
			Transitive2SNRoutine
		]))
	}

	// check (transitive) imported segments still working:
	@Test
	def void testImportedSegmentsWorking() {
		triggerSetRootIdReaction(TAG_TEST_IMPORTED_SEGMENTS_WORKING)
		assertThat(executionMonitor, observedExecutions( // own and imported reactions:
		rootReactions + #[
			// call inside imported segment:
			DirectSNInnerRoutine,
			// call from imported to transitive imported segments:
			TransitiveSNRoutine,
			TransitiveRoutinesQNRoutine,
			// call inside transitive imported segment:
			TransitiveSNInnerRoutine
		]))
	}

	// reaction overrides:
	@Test
	def void testOverrideReaction() {
		triggerSetRootIdReaction(TAG_TEST_REACTION_OVERRIDE)
		assertThat(executionMonitor, observedExecutions(rootReactions + // overriding imported reaction:
		#[RootDirectSNOverriddenReaction, RootRoutine]))
	}

	@Test
	def void testOverrideTransitiveReaction() {
		triggerSetRootIdReaction(TAG_TEST_TRANSITIVE_REACTION_OVERRIDE)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[
			// overriding transitive imported reaction:
			RootTransitiveSNOverriddenReaction,
			RootRoutine,
			// overriding transitive imported reaction, that was also overridden from in-between segment:
			RootTransitiveSNOverriddenReaction2,
			// in-between segment overriding transitive imported reaction:
			DirectSNTransitiveSNOverriddenReaction3,
			DirectSNRoutine
		]))
	}

	// override of imported routine:
	@Test
	def void testCallOverriddenRoutineFromRoot() {
		triggerSetRootIdReaction(TAG_CALL_OVERRIDDEN_ROUTINE, TAG_FROM_ROOT)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[RootDirectSNOverriddenRoutine, RootRoutine]))
	}

	@Test
	def void testCallOverriddenRoutineFromOverriddenSegment() {
		triggerSetRootIdReaction(TAG_CALL_OVERRIDDEN_ROUTINE, TAG_FROM_OVERRIDDEN_SEGMENT)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[RootDirectSNOverriddenRoutine, RootRoutine]))
	}

	// override of transitive imported routine:
	@Test
	def void testCallOverriddenTransitiveRoutineFromRoot() {
		triggerSetRootIdReaction(TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_ROOT)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[RootTransitiveSNOverriddenRoutine, RootRoutine]))
	}

	@Test
	def void testCallOverriddenTransitiveRoutineFromOverriddenSegment() {
		triggerSetRootIdReaction(TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_OVERRIDDEN_SEGMENT)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[RootTransitiveSNOverriddenRoutine, RootRoutine]))
	}

	@Test
	def void testCallOverriddenTransitiveRoutineFromSegmentInBetween() {
		triggerSetRootIdReaction(TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_SEGMENT_IN_BETWEEN)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[RootTransitiveSNOverriddenRoutine, RootRoutine]))
	}

	// override of transitive imported routine, with independent override hierarchy:
	@Test
	def void testCallOverriddenTransitiveRoutineWithSeparateOverrideHierarchyFromRoot() {
		triggerSetRootIdReaction(TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE_WITH_SEPARATE_OVERRIDE_HIERARCHY, TAG_FROM_ROOT)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[RootTransitive3SNOverriddenRoutine, RootRoutine]))
	}

	@Test
	def void testCallOverriddenTransitiveRoutineWithSeparateOverrideHierarchyFromOverriddenSegment() {
		triggerSetRootIdReaction(TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE_WITH_SEPARATE_OVERRIDE_HIERARCHY,
			TAG_FROM_OVERRIDDEN_SEGMENT);
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[RootTransitive3SNOverriddenRoutine, RootRoutine]))
	}

	@Test
	def void testCallOverriddenTransitiveRoutineWithSeparateOverrideHierarchyFromSegmentInBetween() {
		triggerSetRootIdReaction(TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE_WITH_SEPARATE_OVERRIDE_HIERARCHY,
			TAG_FROM_SEGMENT_IN_BETWEEN)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[RootTransitive3SNOverriddenRoutine, RootRoutine]))
	}

	// override of transitive imported routine, that has also already been overridden by imported segment:
	@Test
	def void testCallAlreadyOverriddenTransitiveRoutineFromRoot() {
		triggerSetRootIdReaction(TAG_CALL_ALREADY_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_ROOT)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[RootTransitiveSNOverriddenRoutine2, RootRoutine]))
	}

	@Test
	def void testCallAlreadyOverriddenTransitiveRoutineFromOverriddenSegment() {
		triggerSetRootIdReaction(TAG_CALL_ALREADY_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_OVERRIDDEN_SEGMENT)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[RootTransitiveSNOverriddenRoutine2, RootRoutine]))
	}

	@Test
	def void testCallAlreadyOverriddenTransitiveRoutineFromSegmentInBetween() {
		triggerSetRootIdReaction(TAG_CALL_ALREADY_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_SEGMENT_IN_BETWEEN)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[RootTransitiveSNOverriddenRoutine2, RootRoutine]))
	}

	// imported segment overriding transitive imported routine:
	@Test
	def void testCallTransitiveRoutineOverriddenByImportedSegmentFromRoot() {
		triggerSetRootIdReaction(TAG_CALL_TRANSITIVE_ROUTINE_OVERRIDDEN_BY_IMPORTED_SEGMENT, TAG_FROM_ROOT)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[DirectSNTransitiveSNOverriddenRoutine3, DirectSNRoutine]))
	}

	@Test
	def void testCallTransitiveRoutineOverriddenByImportedSegmentFromOverriddenSegment() {
		triggerSetRootIdReaction(TAG_CALL_TRANSITIVE_ROUTINE_OVERRIDDEN_BY_IMPORTED_SEGMENT,
			TAG_FROM_OVERRIDDEN_SEGMENT)
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[DirectSNTransitiveSNOverriddenRoutine3, DirectSNRoutine]))
	}

	@Test
	def void testCallTransitiveRoutineOverriddenByImportedSegmentFromSegmentInBetween() {
		triggerSetRootIdReaction(TAG_CALL_TRANSITIVE_ROUTINE_OVERRIDDEN_BY_IMPORTED_SEGMENT,
			TAG_FROM_SEGMENT_IN_BETWEEN);
		assertThat(executionMonitor,
			observedExecutions(rootReactions + #[DirectSNTransitiveSNOverriddenRoutine3, DirectSNRoutine]))
	}

	// multiple imports and overrides of the same routines at different import paths:
	@Test
	def void testMultipleImportsOfSameRoutines() {
		// import path 1:
		triggerSetRootIdReaction(TAG_TEST_MULTIPLE_IMPORTS_OF_SAME_ROUTINES_IMPORT_PATH_1)
		assertThat(executionMonitor,
			observedExecutions(rootReactions +
				#[RootCommonRoutinesRoutine1, RootCommonRoutinesRoutine2, RootCommonRoutinesRoutine3]))

		// import path 2:
		executionMonitor.reset()
		triggerSetRootIdReaction(TAG_TEST_MULTIPLE_IMPORTS_OF_SAME_ROUTINES_IMPORT_PATH_2)
		assertThat(executionMonitor, observedExecutions(rootReactions + #[
			CommonRoutinesRoutine1,
			CommonRoutinesRoutine2,
			// multiple overrides of same routine along different import paths:
			RootCommonRoutines2Routine3
		]))
	}
}
