package tools.vitruv.dsls.reactions.tests.importTests

import allElementTypes.AllElementTypesFactory
import allElementTypes.Identified
import allElementTypes.Root
import org.junit.Test
import tools.vitruv.dsls.reactions.tests.importTests.ImportTestsExecutionMonitor.ExecutionType

class ImportTests extends AbstractReactionImportsTests {

	private static val TEST_SOURCE_MODEL_NAME = "ImportTestsModelSource";

	private def Root getRootElement() {
		return TEST_SOURCE_MODEL_NAME.projectModelPath.firstRootElement as Root;
	}

	private def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}

	protected override setup() {
		val root = AllElementTypesFactory.eINSTANCE.createRoot()
		root.setId(TEST_SOURCE_MODEL_NAME)
		createAndSynchronizeModel(TEST_SOURCE_MODEL_NAME.projectModelPath, root)
	}

	override protected cleanup() {
		// do nothing
	}

	private def void setElementId(Identified element, String newId) {
		element.setId(newId);
		saveAndSynchronizeChanges(element);
	}

	private def triggerReaction(String... dataTags) {
		setElementId(rootElement, ImportTestsUtils.toTestDataString(testName.methodName, dataTags));
	}

	// execution monitor:

	private static def resetExecutionMonitor() {
		ImportTestsExecutionMonitor.instance.reset();
	}

	private static def assertIsSet(ExecutionType executionType) {
		ImportTestsExecutionMonitor.instance.assertIsSet(executionType);
	}

	private static def assertIsNotSet(ExecutionType executionType) {
		ImportTestsExecutionMonitor.instance.assertIsNotSet(executionType);
	}

	// all reactions included by the root segment:
	private static def getRootReactions() {
		return #[ExecutionType.RootReaction, ExecutionType.DirectSNReaction, ExecutionType.Direct2SNReaction,
			ExecutionType.TransitiveSNReaction, ExecutionType.Transitive3SNReaction];
	}

	/*
	 * Import hierarchy:
	 *
	 * root -> directSN, direct2SN, directRoutinesQN r qn
	 * directSN -> transitiveSN, transitiveRoutinesQN r qn
	 * direct2SN -> transitive3SN, commonRoutines r qn
	 * directRoutinesQN -> transitive2SN, transitiveRoutinesSN r, transitiveRoutinesQN r qn
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

	// execute imported and transitive imported reactions:

	@Test
	public def void testRootReaction() {
		resetExecutionMonitor();
		triggerReaction();
		assertIsSet(ExecutionType.RootReaction);
	}

	@Test
	public def void testImportedReaction() {
		resetExecutionMonitor();
		triggerReaction();
		assertIsSet(ExecutionType.DirectSNReaction);
	}

	@Test
	public def void testTransitiveImportedReaction() {
		resetExecutionMonitor();
		triggerReaction();
		assertIsSet(ExecutionType.TransitiveSNReaction);
	}

	// multiple imports: combination of imported reactions

	@Test
	public def void testMultipleImportedReactions() {
		resetExecutionMonitor();
		triggerReaction();
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.assertEqualWithStatic();
	}

	// call imported routines from within reaction:

	@Test
	public def void testReactionCallsRootRoutine() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_ROOT_ROUTINE);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsImportedRoutine_SN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_DIRECT_ROUTINE_SN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsImportedRoutine_QN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_DIRECT_ROUTINE_QN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	// call transitive imported routines from within reaction:

	@Test
	public def void testReactionCallsTransitiveImportedRoutine_SN_SN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_TRANSITIVE_ROUTINE_SN_SN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsTransitiveImportedRoutine_SN_QN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_TRANSITIVE_ROUTINE_SN_QN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsTransitiveImportedRoutine_QN_SN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_TRANSITIVE_ROUTINE_QN_SN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsTransitiveImportedRoutine_QN_QN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_REACTION, TAG_TRANSITIVE_ROUTINE_QN_QN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	// call imported routines from within routine:

	@Test
	public def void testRoutineCallsRootRoutine() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_ROOT_ROUTINE);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsImportedRoutine_SN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_DIRECT_ROUTINE_SN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsImportedRoutine_QN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_DIRECT_ROUTINE_QN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	// call transitive imported routines from within routine:

	@Test
	public def void testRoutineCallsTransitiveImportedRoutine_SN_SN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_TRANSITIVE_ROUTINE_SN_SN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsTransitiveImportedRoutine_SN_QN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_TRANSITIVE_ROUTINE_SN_QN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsTransitiveImportedRoutine_QN_SN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_TRANSITIVE_ROUTINE_QN_SN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsTransitiveImportedRoutine_QN_QN() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_ROUTINE, TAG_TRANSITIVE_ROUTINE_QN_QN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	// multiple imports: combination of imported routines

	@Test
	public def void testMultipleImportedRoutines() {
		resetExecutionMonitor();
		triggerReaction(TAG_CALL_ROUTINE_FROM_REACTION,
			TAG_ROOT_ROUTINE, TAG_DIRECT_ROUTINE_SN, TAG_DIRECT2_ROUTINE_SN, TAG_TRANSITIVE_ROUTINE_SN_SN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootRoutine);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.set(ExecutionType.Direct2SNRoutine);
		monitor.set(ExecutionType.TransitiveSNRoutine);
		monitor.assertEqualWithStatic();
	}

	// import only routines:

	@Test
	public def void testRoutinesOnlyReactions() {
		resetExecutionMonitor();
		triggerReaction(TAG_TEST_ROUTINES_ONLY_REACTIONS);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.assertEqualWithStatic();
		// direct: routines-only
		assertIsNotSet(ExecutionType.DirectRoutinesQNReaction);
		// transitive: full > routines-only
		assertIsNotSet(ExecutionType.TransitiveRoutinesQNReaction);
		// transitive: routines-only QN > routines-only SN
		assertIsNotSet(ExecutionType.TransitiveRoutinesSNReaction);
		// transitive: routines-only > full
		assertIsNotSet(ExecutionType.Transitive2SNReaction);
	}

	@Test
	public def void testRoutinesOnlyRoutines() {
		resetExecutionMonitor();
		triggerReaction(TAG_TEST_ROUTINES_ONLY_ROUTINES);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		// direct: routines-only
		monitor.set(ExecutionType.DirectRoutinesQNRoutine);
		// transitive: full > routines-only
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		// transitive: routines-only QN > routines-only SN
		monitor.set(ExecutionType.TransitiveRoutinesSNRoutine);
		// transitive: routines-only > full
		monitor.set(ExecutionType.Transitive2SNRoutine);
		monitor.assertEqualWithStatic();
	}

	// check (transitive) imported segments still working:

	@Test
	public def void testImportedSegmentsWorking() {
		resetExecutionMonitor();
		triggerReaction(TAG_TEST_IMPORTED_SEGMENTS_WORKING);
		val monitor = new ImportTestsExecutionMonitor();
		// own and imported reactions:
		monitor.setAll(rootReactions);
		// call inside imported segment:
		monitor.set(ExecutionType.DirectSNInnerRoutine);
		// call from imported to transitive imported segments:
		monitor.set(ExecutionType.TransitiveSNRoutine);
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		// call inside transitive imported segment:
		monitor.set(ExecutionType.TransitiveSNInnerRoutine);
		monitor.assertEqualWithStatic();
	}

	// reaction overrides:

	@Test
	public def void testOverrideReaction() {
		resetExecutionMonitor();
		triggerReaction(TAG_TEST_REACTION_OVERRIDE);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		// overriding imported reaction:
		monitor.set(ExecutionType.RootDirectSNOverriddenReaction);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.DirectSNOverriddenReaction);
	}

	@Test
	public def void testOverrideTransitiveReaction() {
		resetExecutionMonitor();
		triggerReaction(TAG_TEST_TRANSITIVE_REACTION_OVERRIDE);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		// overriding transitive imported reaction:
		monitor.set(ExecutionType.RootTransitiveSNOverriddenReaction);
		monitor.set(ExecutionType.RootRoutine);
		// overriding transitive imported reaction, that was also overridden from in-between segment:
		monitor.set(ExecutionType.RootTransitiveSNOverriddenReaction2);
		// in-between segment overriding transitive imported reaction:
		monitor.set(ExecutionType.DirectSNTransitiveSNOverriddenReaction3);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenReaction);
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenReaction2);
		assertIsNotSet(ExecutionType.DirectSNTransitiveSNOverriddenReaction2);
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenReaction3);
	}

	// override of imported routine:

	@Test
	public def void testCallOverriddenRoutineFromRoot() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_OVERRIDDEN_ROUTINE, TAG_FROM_ROOT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootDirectSNOverriddenRoutine);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.DirectSNOverriddenRoutine);
	}

	@Test
	public def void testCallOverriddenRoutineFromOverriddenSegment() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_OVERRIDDEN_ROUTINE, TAG_FROM_OVERRIDDEN_SEGMENT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootDirectSNOverriddenRoutine);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.DirectSNOverriddenRoutine);
	}

	// override of transitive imported routine:

	@Test
	public def void testCallOverriddenTransitiveRoutineFromRoot() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_ROOT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootTransitiveSNOverriddenRoutine);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine);
	}

	@Test
	public def void testCallOverriddenTransitiveRoutineFromOverriddenSegment() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_OVERRIDDEN_SEGMENT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootTransitiveSNOverriddenRoutine);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine);
	}

	@Test
	public def void testCallOverriddenTransitiveRoutineFromSegmentInBetween() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_SEGMENT_IN_BETWEEN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootTransitiveSNOverriddenRoutine);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine);
	}

	// override of transitive imported routine, with independent override hierarchy:

	@Test
	public def void testCallOverriddenTransitiveRoutineWithSeparateOverrideHierarchyFromRoot() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE_WITH_SEPARATE_OVERRIDE_HIERARCHY, TAG_FROM_ROOT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootTransitive3SNOverriddenRoutine);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.Transitive3SNOverriddenRoutine);
	}

	@Test
	public def void testCallOverriddenTransitiveRoutineWithSeparateOverrideHierarchyFromOverriddenSegment() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE_WITH_SEPARATE_OVERRIDE_HIERARCHY, TAG_FROM_OVERRIDDEN_SEGMENT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootTransitive3SNOverriddenRoutine);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.Transitive3SNOverriddenRoutine);
	}

	@Test
	public def void testCallOverriddenTransitiveRoutineWithSeparateOverrideHierarchyFromSegmentInBetween() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_OVERRIDDEN_TRANSITIVE_ROUTINE_WITH_SEPARATE_OVERRIDE_HIERARCHY, TAG_FROM_SEGMENT_IN_BETWEEN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootTransitive3SNOverriddenRoutine);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.Transitive3SNOverriddenRoutine);
	}

	// override of transitive imported routine, that has also already been overridden by imported segment:

	@Test
	public def void testCallAlreadyOverriddenTransitiveRoutineFromRoot() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_ALREADY_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_ROOT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootTransitiveSNOverriddenRoutine2);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine2);
		assertIsNotSet(ExecutionType.DirectSNTransitiveSNOverriddenRoutine2);
	}

	@Test
	public def void testCallAlreadyOverriddenTransitiveRoutineFromOverriddenSegment() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_ALREADY_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_OVERRIDDEN_SEGMENT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootTransitiveSNOverriddenRoutine2);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine2);
		assertIsNotSet(ExecutionType.DirectSNTransitiveSNOverriddenRoutine2);
	}

	@Test
	public def void testCallAlreadyOverriddenTransitiveRoutineFromSegmentInBetween() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_ALREADY_OVERRIDDEN_TRANSITIVE_ROUTINE, TAG_FROM_SEGMENT_IN_BETWEEN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootTransitiveSNOverriddenRoutine2);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine2);
		assertIsNotSet(ExecutionType.DirectSNTransitiveSNOverriddenRoutine2);
	}

	// imported segment overriding transitive imported routine:

	@Test
	public def void testCallTransitiveRoutineOverriddenByImportedSegmentFromRoot() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_TRANSITIVE_ROUTINE_OVERRIDDEN_BY_IMPORTED_SEGMENT, TAG_FROM_ROOT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectSNTransitiveSNOverriddenRoutine3);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine3);
	}

	@Test
	public def void testCallTransitiveRoutineOverriddenByImportedSegmentFromOverriddenSegment() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_TRANSITIVE_ROUTINE_OVERRIDDEN_BY_IMPORTED_SEGMENT, TAG_FROM_OVERRIDDEN_SEGMENT);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectSNTransitiveSNOverriddenRoutine3);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine3);
	}

	@Test
	public def void testCallTransitiveRoutineOverriddenByImportedSegmentFromSegmentInBetween() {
		resetExecutionMonitor();
		triggerReaction(ImportTests.TAG_CALL_TRANSITIVE_ROUTINE_OVERRIDDEN_BY_IMPORTED_SEGMENT, TAG_FROM_SEGMENT_IN_BETWEEN);
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectSNTransitiveSNOverriddenRoutine3);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine3);
	}
}
