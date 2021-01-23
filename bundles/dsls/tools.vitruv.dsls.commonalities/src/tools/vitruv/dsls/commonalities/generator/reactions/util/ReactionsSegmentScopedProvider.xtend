package tools.vitruv.dsls.commonalities.generator.reactions.util

import java.util.HashMap
import java.util.Map
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped
import tools.vitruv.dsls.commonalities.generator.util.guice.InjectingFactoryBase
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder

/**
 * Base class for providers which create one object per reactions segment
 * builder and then keep returning that same object in subsequent requests.
 */
@GenerationScoped
abstract class ReactionsSegmentScopedProvider<T> extends InjectingFactoryBase {
	val Map<FluentReactionsSegmentBuilder, T> bySegment = new HashMap

	def getFor(FluentReactionsSegmentBuilder segment) {
		return bySegment.computeIfAbsent(segment) [
			createFor(segment)
		]
	}

	protected abstract def T createFor(FluentReactionsSegmentBuilder segment);
}
