package tools.vitruv.dsls.commonalities.testutils

import org.apache.log4j.Level
import org.apache.log4j.LogManager
import org.apache.log4j.Logger
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationMatcher
import tools.vitruv.testutils.VitruviusApplicationTest

@RunWith(XtextRunner)
@InjectWith(CombinedUiInjectorProvider)
abstract class CommonalitiesExecutionTest extends VitruviusApplicationTest {

	override protected getVitruvDomains() {
		createChangePropagationSpecifications.flatMap[#[sourceDomain, targetDomain]].toSet
	}

	protected def debugReactions() {
		LogManager.currentLoggers.asIterator.map[it as Logger]
			.filter[name.endsWith('Reaction') || name.endsWith('Routine')]
			.forEach [
				level = Level.TRACE
			]
	}

	protected def debugParticipationMatcher() {
		Logger.getLogger(ParticipationMatcher).level = Level.TRACE
	}

	protected def debugOperators() {
		LogManager.currentLoggers.asIterator.map[it as Logger]
			.filter[name.endsWith('Operator')]
			.forEach [
				level = Level.TRACE
			]
	}
}
