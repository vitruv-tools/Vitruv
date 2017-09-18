package tools.vitruv.dsls.reactions.builder

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.framework.domains.VitruvDomain

import static com.google.common.base.Preconditions.*

@Accessors(PACKAGE_GETTER)
class FluentReactionsSegmentBuilder extends FluentReactionElementBuilder {
	val ReactionsSegment segment

	package new(String segmentName, FluentBuilderContext context) {
		super(context)
		this.segment = ReactionsLanguageFactory.eINSTANCE.createReactionsSegment => [
			name = segmentName
		]
	}

	def package start() {
		new ReactionsSegmentSourceBuilder(this)
	}

	override protected attachmentPreparation() {
		super.attachmentPreparation()
		checkState(segment.routines.size + segment.reactions.size > 0, '''Neither routines nor reactions were added to the reaction segment «segment.name»!''')
	}
	
	static class ReactionsSegmentSourceBuilder {
		val extension FluentReactionsSegmentBuilder builder

		private new(FluentReactionsSegmentBuilder builder) {
			this.builder = builder
		}

		def inReactionToChangesIn(VitruvDomain domain) {
			inReactionToChangesIn(domain.name)
		}

		def inReactionToChangesIn(String domainName) {
			segment.fromDomain = domainName.domainReference
			new ReactionsSegmentTargetBuilder(builder)
		}
	}

	static class ReactionsSegmentTargetBuilder {
		val extension FluentReactionsSegmentBuilder builder

		private new(FluentReactionsSegmentBuilder builder) {
			this.builder = builder
		}

		def executeActionsIn(VitruvDomain domain) {
			executeActionsIn(domain.name)
		}

		def executeActionsIn(String domainName) {
			segment.toDomain = domainName.domainReference
			readyToBeAttached = true
			builder
		}
	}

	def private static domainReference(String domainName) {
		MirBaseFactory.eINSTANCE.createDomainReference => [
			domain = domainName
		]
	}
	
	def operator_add(FluentReactionBuilder[] reactionBuilders) {
		reactionBuilders.forEach [this += it]
		this
	}
	
	def dispatch add(FluentReactionBuilder reactionBuilder) {
		this += reactionBuilder
	}
	
	def dispatch add(FluentRoutineBuilder routineBuilder) {
		this += routineBuilder
	}
	
	def operator_add(FluentReactionBuilder reactionBuilder) {
		checkNotYetAttached()
		segment.reactions += reactionBuilder.reaction
		reactionBuilder.segmentBuilder = this
		childBuilders += reactionBuilder
		this
	}

	def operator_add(FluentRoutineBuilder routineBuilder) {
		checkNotYetAttached()
		segment.routines += routineBuilder.routine
		routineBuilder.segmentBuilder = this
		childBuilders += routineBuilder
		this
	}
	
	override toString() {
		'''reaction segment builder for “«segment.name»”'''
	}
	
}
