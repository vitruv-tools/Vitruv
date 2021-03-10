package tools.vitruv.dsls.reactions.builder

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.function.Consumer
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.dsls.common.elements.MetaclassReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.InputBuilder
import tools.vitruv.dsls.reactions.language.toplevelelements.Reaction
import tools.vitruv.dsls.reactions.language.toplevelelements.Routine
import tools.vitruv.dsls.reactions.language.toplevelelements.TopLevelElementsFactory

/**
 * Entry point for fluent reaction builders. The offered methods each create a 
 * builder for a reactions language element. The builders offer only methods
 * that make sense in the current context and are named to match  the reactions
 * language’s syntax closely. Because of that, using them should be
 * self-explanatory.
 * 
 * <p>Unlike the builders, this class does not hold any state and can thus be
 * reused indefinitely.
 */
@Singleton
class FluentReactionsLanguageBuilder {

	@Inject
	static FluentBuilderContext context

	def reactionsFile(String name) {
		new FluentReactionsFileBuilder(name, context).start()
	}

	def reactionsSegment(String name) {
		new FluentReactionsSegmentBuilder(name, context).start()
	}

	def routine(String name) {
		new FluentRoutineBuilder(name, context).start()
	}

	def from(Routine routine, Consumer<InputBuilder> inputBuilder) {
		new ExistingRoutineBuilder(routine, inputBuilder, context)
	}

	def from(Routine routine) {
		new ExistingRoutineBuilder(routine, context)
	}

	def reaction(String name) {
		new FluentReactionBuilder(name, context).start()
	}

	def from(Reaction reaction) {
		new ExistingReactionBuilder(reaction, context)
	}

	static class ExistingRoutineBuilder extends FluentRoutineBuilder {
		new(Routine routine, Consumer<InputBuilder> inputBuilder, FluentBuilderContext context) {
			super(null, context)
			this.routine = routine
			this.routine.input = TopLevelElementsFactory.eINSTANCE.createRoutineInput
			start().input(inputBuilder)
			init()
		}

		new(Routine routine, FluentBuilderContext context) {
			super(null, context)
			this.routine = routine
			init()
		}

		private def init() {
			this.readyToBeAttached = true
			val contents = EcoreUtil.getAllContents(#[routine])
			contents.filter[it instanceof MetaclassReference].forEach [
				val ref = it as MetaclassReference
				ref.reference(ref.metaclass)
			]
		}
	}

	static class ExistingReactionBuilder extends FluentReactionBuilder {
		new(Reaction reaction, FluentBuilderContext context) {
			super(reaction, context)
			this.readyToBeAttached = true
		}
	}
}
