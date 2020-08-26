package tools.vitruv.dsls.commonalities.generator.reactions.matching

import org.eclipse.emf.ecore.EcorePackage
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder

import static com.google.common.base.Preconditions.*

class DeleteObjectRoutineBuilder extends ReactionsGenerationHelper {

	static class Provider extends ReactionsSegmentScopedProvider<DeleteObjectRoutineBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new DeleteObjectRoutineBuilder(segment).injectMembers
		}

		def getDeleteObjectRoutine(FluentReactionsSegmentBuilder segment) {
			return getFor(segment).getDeleteObjectRoutine()
		}
	}

	var FluentRoutineBuilder deleteObjectRoutine = null

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		// Note: The reactions segment is unused here. But having the provider
		// require it ensures that we only create one instance of this class
		// per reactions segment.
	}

	// Dummy constructor for Guice
	package new() {
		throw new IllegalStateException("Use the Provider to get instances of this class!")
	}

	def getDeleteObjectRoutine() {
		if (deleteObjectRoutine === null) {
			deleteObjectRoutine = create.routine('deleteObject')
				.input [
					model(EcorePackage.Literals.EOBJECT, 'object')
				].action [
					delete('object')
				]
		}
		return deleteObjectRoutine
	}
}
