package tools.vitruv.dsls.commonalities.generator.reactions.resource

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsSegmentScopedProvider
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class SetupResourceBridgeRoutineBuilder extends ReactionsGenerationHelper {

	static class Provider extends ReactionsSegmentScopedProvider<SetupResourceBridgeRoutineBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new SetupResourceBridgeRoutineBuilder(segment).injectMembers
		}

		def getSetupResourceBridgeRoutine(FluentReactionsSegmentBuilder segment, ParticipationClass resourceClass) {
			return getFor(segment).getSetupResourceBridgeRoutine(resourceClass)
		}
	}

	@Inject extension ResourceBridgeHelper resourceBridgeHelper

	// One routine per intermediate model (concept) is sufficient (keyed by concept name):
	val Map<String, FluentRoutineBuilder> setupResourceBridgeRoutines = new HashMap

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

	def getSetupResourceBridgeRoutine(ParticipationClass resourceClass) {
		checkNotNull(resourceClass, "resourceClass is null")
		checkArgument(resourceClass.isForResource, "The given resourceClass does to refer to the Resource metaclass")
		val concept = resourceClass.declaringCommonality.concept
		return setupResourceBridgeRoutines.computeIfAbsent(concept.name) [
			create.routine('''setupResourceBridge_«concept.name»''')
				.input [
					model(ResourcesPackage.eINSTANCE.intermediateResourceBridge, RESOURCE_BRIDGE)
				]
				.action [
					update(RESOURCE_BRIDGE) [
						initExistingResourceBridge(resourceClass, variable(RESOURCE_BRIDGE))
					]
				]
		]
	}
}
