package tools.vitruv.dsls.reactions.builder

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport

/**
 * Entry point for fluent reaction builders. The offered methods each create a 
 * builder for a reactions language element. The builders offer only methods
 * that make sense in the current context and are named to match closely the 
 * reactions language’s syntax of. Because of that, using them should be 
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

	def routine(Routine routine) {
		new IntegratedRoutineBuilder(routine, null, context)
	}

	def routine(Routine routine, EClass inputType) {
		new IntegratedRoutineBuilder(routine, inputType, context)
	}

	def reaction(String name) {
		new FluentReactionBuilder(name, context).start()
	}

	def reaction(Reaction reaction) {
		new IntegratedReactionBuilder(reaction, context)
	}
	
	static class IntegratedRoutineBuilder extends FluentRoutineBuilder {
		new(Routine routine, EClass inputType, FluentBuilderContext context) {
			super(null, context)
			this.routine = routine
			this.readyToBeAttached = true
			if (inputType !== null) {
				this.requireAffectedEObject = true
				this.affectedObjectType = inputType
				// clear the model inputs, because the parameter will be reconstructed by the generator
				this.routine.input.modelInputElements.clear
			}
		}
	}

	static class IntegratedReactionBuilder extends FluentReactionBuilder {
		new(Reaction reaction, FluentBuilderContext context) {
			super(null, context)
			this.reaction = reaction
			this.readyToBeAttached = true
		}
	}
}
