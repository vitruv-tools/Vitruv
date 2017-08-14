package tools.vitruv.dsls.reactions.formatting

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.formatting2.IFormattableDocument
import tools.vitruv.dsls.mirbase.formatting.MirBaseFormatter
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.MirBaseFile
import tools.vitruv.dsls.reactions.reactionsLanguage.Action
import tools.vitruv.dsls.reactions.reactionsLanguage.ActionStatement
import tools.vitruv.dsls.reactions.reactionsLanguage.CreateModelElement
import tools.vitruv.dsls.reactions.reactionsLanguage.Matcher
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelAttributeChange
import tools.vitruv.dsls.reactions.reactionsLanguage.ModelElementChange
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.Trigger
import tools.vitruv.dsls.reactions.reactionsLanguage.MatcherStatement
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutineInput
import tools.vitruv.dsls.reactions.reactionsLanguage.RetrieveModelElement

class ReactionsLanguageFormatter extends MirBaseFormatter {

	def dispatch void format(ReactionsFile reactionsFile, extension IFormattableDocument document) {
		(reactionsFile as MirBaseFile).formatStatic(document)
		reactionsFile.formatStatic(document)
	}

	def formatStatic(ReactionsFile reactionsFile, extension IFormattableDocument document) {
		reactionsFile.reactionsSegments.forEach [formatStatic(document)]
		reactionsFile.reactionsSegments.head.prepend [newLines = 2]
		reactionsFile.reactionsSegments.tail.forEach [prepend [newLines = 4]]
	}

	def formatStatic(ReactionsSegment segment, extension IFormattableDocument document) {
		segment.regionFor.keyword('in reaction to changes in').prepend [newLine]
		segment.regionFor.keyword('execute').prepend [newLine]
		segment.reactions.forEach [formatStatic(document)]
		segment.routines.forEach [formatStatic(document)]
	}

	def formatStatic(Reaction reaction, extension IFormattableDocument document) {
		reaction.prepend [newLines = 2]
		if (reaction.documentation !== null) {
			reaction.regionFor.keyword('reaction').prepend [newLine]
		}
		reaction.formatInteriorBlock(document)
		reaction.trigger.formatStatic(document)
		reaction.callRoutine.prepend [newLine]
		reaction.callRoutine.code.regionFor.keyword('(').prepend [noSpace].append [noSpace]
		reaction.callRoutine.code.regionFor.keyword(')').prepend [noSpace]
	}
	
	def formatStatic(Trigger trigger, extension IFormattableDocument document) {
		trigger.regionFor.keyword('with').prepend [newLine;indent]
		trigger.format(document)
	}
	
	def dispatch void format(ModelElementChange modelElementChange, extension IFormattableDocument document) {
		modelElementChange.elementType.formatStatic(document)
	}
	
	def dispatch void format(ModelAttributeChange modelAttributeChange, extension IFormattableDocument document) {
		modelAttributeChange.feature.formatStatic(document)
	}
	
	def formatStatic(Routine routine, extension IFormattableDocument document) {
		routine.prepend [newLines = 2 ]
		if (routine.documentation !== null) {
			routine.regionFor.keyword('routine').prepend [newLine]
		}
		routine.input.formatStatic(document)
		routine.formatInteriorBlock(document)
		routine.matcher?.formatStatic(document)
		routine.action.formatStatic(document)
	}
	
	def formatStatic(RoutineInput routineInput, extension IFormattableDocument document) {
		routineInput.regionFor.keyword('(').prepend [noSpace].append [noSpace]
		routineInput.modelInputElements.forEach [formatStatic(document)]
		routineInput.allRegionsFor.keyword(',').prepend [noSpace].append [oneSpace]
		routineInput.regionFor.keyword(')').prepend [noSpace]
	}
	
	def formatStatic(Matcher matcher, extension IFormattableDocument document) {
		matcher.prepend [newLine]
		matcher.formatInteriorBlock(document)
		matcher.matcherStatements.forEach [formatStatic(document)]
	}
	
	def formatStatic(MatcherStatement matcherStatement, extension IFormattableDocument document) {
		matcherStatement.prepend [newLine]
		matcherStatement.format(document)
	}
	
	def dispatch void format(RetrieveModelElement retrieveStatement, extension IFormattableDocument document) {
		(retrieveStatement as MetaclassReference).formatStatic(document)
	}
	
	def formatStatic(Action action, extension IFormattableDocument document) {
		action.prepend [newLine]
		action.formatInteriorBlock(document)
		action.actionStatements.forEach [formatStatic(document)]
	}
	
	def formatStatic(ActionStatement actionStatement, extension IFormattableDocument document) {
		actionStatement.prepend [newLine]
		actionStatement.format(document)
	}
	
	def dispatch void format(CreateModelElement createModelElement, extension IFormattableDocument document) {
		(createModelElement as MetaclassReference).formatStatic(document)
	}
	
	def formatInteriorBlock(EObject element, extension IFormattableDocument document) {
		interior(
			element.regionFor.keyword('{').append [newLine],
        	element.regionFor.keyword('}').prepend [newLine],
        	[indent]
		)
	}
}
