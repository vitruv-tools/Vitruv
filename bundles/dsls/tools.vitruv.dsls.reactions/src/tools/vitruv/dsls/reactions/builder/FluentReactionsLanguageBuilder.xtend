package tools.vitruv.dsls.reactions.builder

import com.google.inject.Inject
import com.google.inject.Singleton

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
	
	def reaction(String name) {
		new FluentReactionBuilder(name, context).start()	
	}
}