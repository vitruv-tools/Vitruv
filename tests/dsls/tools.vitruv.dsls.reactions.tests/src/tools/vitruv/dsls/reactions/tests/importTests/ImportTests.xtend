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

	private def triggerReaction(String... data) {
		setElementId(rootElement, ImportTestsUtils.toTestDataString(testName.methodName, data));
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
	 * direct2SN -> commonRoutines r qn
	 * directRoutinesQN -> transitive2SN, transitiveRoutinesSN r, transitiveRoutinesQN r qn
	 * transitiveSN -> commonRoutines r qn
	 * transitive2SN
	 * transitiveRoutinesSN
	 * transitiveRoutinesQN
	 * commonRoutines
	 */

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
		triggerReaction("callRoutineFromReaction", "rootRoutine");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsImportedRoutine_SN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromReaction", "directRoutine_SN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsImportedRoutine_QN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromReaction", "directRoutine_QN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	// call transitive imported routines from within reaction:

	@Test
	public def void testReactionCallsTransitiveImportedRoutine_SN_SN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromReaction", "transitiveRoutine_SN_SN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsTransitiveImportedRoutine_SN_QN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromReaction", "transitiveRoutine_SN_QN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsTransitiveImportedRoutine_QN_SN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromReaction", "transitiveRoutine_QN_SN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testReactionCallsTransitiveImportedRoutine_QN_QN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromReaction", "transitiveRoutine_QN_QN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	// call imported routines from within routine:

	@Test
	public def void testRoutineCallsRootRoutine() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromRoutine", "rootRoutine");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.RootRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsImportedRoutine_SN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromRoutine", "directRoutine_SN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsImportedRoutine_QN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromRoutine", "directRoutine_QN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	// call transitive imported routines from within routine:

	@Test
	public def void testRoutineCallsTransitiveImportedRoutine_SN_SN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromRoutine", "transitiveRoutine_SN_SN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsTransitiveImportedRoutine_SN_QN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromRoutine", "transitiveRoutine_SN_QN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsTransitiveImportedRoutine_QN_SN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromRoutine", "transitiveRoutine_QN_SN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesSNRoutine);
		monitor.assertEqualWithStatic();
	}

	@Test
	public def void testRoutineCallsTransitiveImportedRoutine_QN_QN() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromRoutine", "transitiveRoutine_QN_QN");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.TransitiveRoutinesQNRoutine);
		monitor.assertEqualWithStatic();
	}

	// multiple imports: combination of imported routines

	@Test
	public def void testMultipleImportedRoutines() {
		resetExecutionMonitor();
		triggerReaction("callRoutineFromReaction",
			"rootRoutine", "directRoutine_SN", "direct2Routine_SN", "transitiveRoutine_SN_SN");
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
		triggerReaction("testRoutinesOnlyReactions");
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
		triggerReaction("testRoutinesOnlyRoutines");
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
		triggerReaction("testImportedSegmentsWorking");
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
		triggerReaction("testReactionOverride");
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
		triggerReaction("testTransitiveReactionOverride");
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
		triggerReaction("testCallOverriddenRoutine", "fromRoot");
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
		triggerReaction("testCallOverriddenRoutine", "fromOverriddenSegment");
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
		triggerReaction("testCallOverriddenTransitiveRoutine", "fromRoot");
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
		triggerReaction("testCallOverriddenTransitiveRoutine", "fromOverriddenSegment");
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
		triggerReaction("testCallOverriddenTransitiveRoutine", "fromSegmentInBetween");
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
		triggerReaction("testCallOverriddenTransitiveRoutineWithSeparateOverrideHierarchy", "fromRoot");
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
		triggerReaction("testCallOverriddenTransitiveRoutineWithSeparateOverrideHierarchy", "fromOverriddenSegment");
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
		triggerReaction("testCallOverriddenTransitiveRoutineWithSeparateOverrideHierarchy", "fromSegmentInBetween");
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
		triggerReaction("testCallAlreadyOverriddenTransitiveRoutine", "fromRoot");
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
		triggerReaction("testCallAlreadyOverriddenTransitiveRoutine", "fromOverriddenSegment");
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
		triggerReaction("testCallAlreadyOverriddenTransitiveRoutine", "fromSegmentInBetween");
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
		triggerReaction("testCallTransitiveRoutineOverriddenByImportedSegment", "fromRoot");
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
		triggerReaction("testCallTransitiveRoutineOverriddenByImportedSegment", "fromOverriddenSegment");
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
		triggerReaction("testCallTransitiveRoutineOverriddenByImportedSegment", "fromSegmentInBetween");
		val monitor = new ImportTestsExecutionMonitor();
		monitor.setAll(rootReactions);
		monitor.set(ExecutionType.DirectSNTransitiveSNOverriddenRoutine3);
		monitor.set(ExecutionType.DirectSNRoutine);
		monitor.assertEqualWithStatic();
		assertIsNotSet(ExecutionType.TransitiveSNOverriddenRoutine3);
	}
}
