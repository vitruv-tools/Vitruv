package tools.vitruv.dsls.reactions.builder

import org.eclipse.xtend.lib.annotations.Accessors

abstract package class FluentReactionSegmentChildBuilder extends FluentReactionElementBuilder {
	@Accessors(PROTECTED_GETTER, PACKAGE_SETTER)
	var FluentReactionsSegmentBuilder segmentBuilder
	
	protected new(FluentBuilderContext context) {
		super(context)
	}

	def protected mustBeInSameSegmentAs(FluentReactionSegmentChildBuilder a, FluentReactionSegmentChildBuilder b) {
		checkAssociation(a, b)
		a.beforeAttached [infect(b)]
		b.beforeAttached [infect(a)]
	}
	
	def private infect(FluentReactionSegmentChildBuilder infector, FluentReactionSegmentChildBuilder infected) {
		checkAssociation(infector, infected)
		if (infector.segmentBuilder !== null && infected.segmentBuilder === null) {
			infector.segmentBuilder.add(infected)
		}
	}

	def private checkAssociation(FluentReactionSegmentChildBuilder a, FluentReactionSegmentChildBuilder b) {
		if (a.segmentBuilder !== null && b.segmentBuilder !== null && a.segmentBuilder != b.segmentBuilder) {
			throw new RuntimeException('''«a» is in a different reaction segment than «b»!''')
		}
	}
}
