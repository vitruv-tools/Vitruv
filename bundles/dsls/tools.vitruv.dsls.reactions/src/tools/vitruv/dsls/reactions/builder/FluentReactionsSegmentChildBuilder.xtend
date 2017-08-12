package tools.vitruv.dsls.reactions.builder

import org.eclipse.xtend.lib.annotations.Accessors

abstract package class FluentReactionsSegmentChildBuilder extends FluentReactionElementBuilder {
	@Accessors(PACKAGE_SETTER)
	var FluentReactionsSegmentBuilder segmentBuilder

	protected new(FluentBuilderContext context) {
		super(context)
	}

	def protected transferReactionsSegmentTo(FluentReactionsSegmentChildBuilder infector,
		FluentReactionsSegmentChildBuilder infected) {
		infector.beforeAttached[infect(infected)]
		infected.beforeAttached[checkReactionsSegmentIsCompatibleTo(infector)]
	}

	def private infect(FluentReactionsSegmentChildBuilder infector, FluentReactionsSegmentChildBuilder infected) {
		infector.checkReactionsSegmentIsCompatibleTo(infected)
		if (infector.segmentBuilder !== null && infected.segmentBuilder === null) {
			infector.segmentBuilder.add(infected)
		}
	}

	def private checkReactionsSegmentIsCompatibleTo(FluentReactionsSegmentChildBuilder a,
		FluentReactionsSegmentChildBuilder b) {
		if (a.attachedReactionsFile !== null && b.attachedReactionsFile !== null &&
			a.attachedReactionsFile != b.attachedReactionsFile) {
			throw new RuntimeException('''«a» is in a different reaction file than «b»!''')
		}
	}
}
