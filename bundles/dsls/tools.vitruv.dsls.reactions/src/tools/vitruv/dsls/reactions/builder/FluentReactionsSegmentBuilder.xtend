package tools.vitruv.dsls.reactions.builder

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFactory
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsImport
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
		checkState(segment.routines.size + segment.reactions.size + segment.reactionsImports.size > 0,
			'''Neither routines, nor reactions, nor imports were added to the reaction segment «segment.name»!''')
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

	def importSegment(FluentReactionsSegmentBuilder reactionsSegmentBuilder) {
		return new ReactionsSegmentImportBuilder(this, reactionsSegmentBuilder);
	}

	static class ReactionsSegmentImportBuilder {
		val extension FluentReactionsSegmentBuilder builder
		val ReactionsImport reactionsImport;

		private new(FluentReactionsSegmentBuilder builder, FluentReactionsSegmentBuilder reactionsSegmentBuilder) {
			this.builder = builder
			this.reactionsImport = ReactionsLanguageFactory.eINSTANCE.createReactionsImport => [
				importedReactionsSegment = reactionsSegmentBuilder.segment
			]
			builder.segment.reactionsImports += reactionsImport
		}

		def routinesOnly() {
			reactionsImport.routinesOnly = true;
			this
		}

		def usingQualifiedRoutineNames() {
			reactionsImport.useQualifiedNames = true
			builder
		}

		def usingSimpleRoutineNames() {
			reactionsImport.useQualifiedNames = false
			builder
		}
	}

	def operator_add(FluentReactionBuilder[] reactionBuilders) {
		reactionBuilders.forEach [this += it]
		this
	}

	def operator_add(FluentRoutineBuilder[] routineBuilders) {
		routineBuilders.forEach [this += it]
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
		if (reactionBuilder.segmentBuilder !== this) {
			checkArgument(reactionBuilder.segmentBuilder === null, '''The «reactionBuilder
				» has already been added to the «reactionBuilder.segmentBuilder»''')
			checkArgument(!segment.reactions.exists[it.name == reactionBuilder.reaction.name],
				'''The «this» already contains a reaction with name '«reactionBuilder.reaction.name»'!''')
			segment.reactions += reactionBuilder.reaction
			reactionBuilder.segmentBuilder = this
			childBuilders += reactionBuilder
		}
		this
	}

	def operator_add(FluentRoutineBuilder routineBuilder) {
		checkNotYetAttached()
		if (routineBuilder.segmentBuilder !== this) {
			checkArgument(routineBuilder.segmentBuilder === null, '''The «routineBuilder
				» has already been added to the «routineBuilder.segmentBuilder»''')
			checkArgument(!segment.routines.exists[it.name == routineBuilder.routine.name],
				'''The «this» already contains a routine with name '«routineBuilder.routine.name»'!''')
			segment.routines += routineBuilder.routine
			routineBuilder.segmentBuilder = this
			childBuilders += routineBuilder
		}
		this
	}

	override toString() {
		'''reaction segment builder for “«segment.name»”'''
	}
}
