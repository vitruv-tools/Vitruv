package tools.vitruv.dsls.commonalities.generator.reactions.attribute

import java.util.Collections
import java.util.function.BiFunction
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.language.elements.Attribute
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EDataTypeAdapter
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder.RoutineCallBuilder

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*

class AttributeChangeReactionsHelper extends ReactionsGenerationHelper {

	package new() {
	}

	enum AttributeChangeReactionType {
		VALUE_REPLACED,
		VALUE_INSERTED,
		VALUE_REMOVED
	}

	def getAttributeChangeReactions(Attribute attribute,
		BiFunction<AttributeChangeReactionType, RoutineCallBuilder, FluentReactionBuilder> actionBuilder) {
		return attribute.getAttributeChangeReactions("", actionBuilder)
	}

	def getAttributeChangeReactions(Attribute attribute, String reactionNameSuffix,
		BiFunction<AttributeChangeReactionType, RoutineCallBuilder, FluentReactionBuilder> actionBuilder) {
		switch (attribute.type) {
			EDataTypeAdapter case !attribute.isMultiValued:
				Collections.singleton(
					attribute.singleAttributeReplacedReaction(reactionNameSuffix, actionBuilder)
				)

			EDataTypeAdapter case attribute.isMultiValued:
				#[
					attribute.multiAttributeInsertReaction(reactionNameSuffix, actionBuilder),
					attribute.multiAttributeRemoveReaction(reactionNameSuffix, actionBuilder)
				]

			EClassAdapter case !attribute.isMultiValued:
				Collections.singleton(
					attribute.singleReferenceReplacedReaction(reactionNameSuffix, actionBuilder)
				)

			EClassAdapter case attribute.isMultiValued:
				#[
					attribute.multiReferenceInsertReaction(reactionNameSuffix, actionBuilder),
					attribute.multiReferenceRemoveReaction(reactionNameSuffix, actionBuilder)
				]
		}
	}

	private def singleAttributeReplacedReaction(Attribute attribute, String reactionNameSuffix,
		BiFunction<AttributeChangeReactionType, RoutineCallBuilder, FluentReactionBuilder> actionBuilder) {
		val reaction = create.reaction('''«attribute.reactionName»Change«reactionNameSuffix»''')
			.afterAttributeReplacedAt(attribute.classLikeContainer.changeClass, attribute.correspondingEAttribute)
		return actionBuilder.apply(AttributeChangeReactionType.VALUE_REPLACED, reaction)
	}

	private def multiAttributeInsertReaction(Attribute attribute, String reactionNameSuffix,
		BiFunction<AttributeChangeReactionType, RoutineCallBuilder, FluentReactionBuilder> actionBuilder) {
		val reaction = create.reaction('''«attribute.reactionName»Insert«reactionNameSuffix»''')
			.afterAttributeInsertIn(attribute.classLikeContainer.changeClass, attribute.correspondingEAttribute)
		return actionBuilder.apply(AttributeChangeReactionType.VALUE_INSERTED, reaction)
	}

	private def multiAttributeRemoveReaction(Attribute attribute, String reactionNameSuffix,
		BiFunction<AttributeChangeReactionType, RoutineCallBuilder, FluentReactionBuilder> actionBuilder) {
		val reaction = create.reaction('''«attribute.reactionName»Remove«reactionNameSuffix»''')
			.afterAttributeRemoveFrom(attribute.classLikeContainer.changeClass, attribute.correspondingEAttribute)
		return actionBuilder.apply(AttributeChangeReactionType.VALUE_REMOVED, reaction)
	}

	private def singleReferenceReplacedReaction(Attribute attribute, String reactionNameSuffix,
		BiFunction<AttributeChangeReactionType, RoutineCallBuilder, FluentReactionBuilder> actionBuilder) {
		val reaction = create.reaction('''«attribute.reactionName»Change«reactionNameSuffix»''')
			.afterElement.replacedAt(attribute.classLikeContainer.changeClass, attribute.correspondingEReference)
		return actionBuilder.apply(AttributeChangeReactionType.VALUE_REPLACED, reaction)
	}

	private def multiReferenceInsertReaction(Attribute attribute, String reactionNameSuffix,
		BiFunction<AttributeChangeReactionType, RoutineCallBuilder, FluentReactionBuilder> actionBuilder) {
		val reaction = create.reaction('''«attribute.reactionName»Insert«reactionNameSuffix»''')
			.afterElement.insertedIn(attribute.classLikeContainer.changeClass, attribute.correspondingEReference)
		return actionBuilder.apply(AttributeChangeReactionType.VALUE_INSERTED, reaction)
	}

	private def multiReferenceRemoveReaction(Attribute attribute, String reactionNameSuffix,
		BiFunction<AttributeChangeReactionType, RoutineCallBuilder, FluentReactionBuilder> actionBuilder) {
		val reaction = create.reaction('''«attribute.reactionName»Remove«reactionNameSuffix»''')
			.afterElement.removedFrom(attribute.classLikeContainer.changeClass, attribute.correspondingEReference)
		return actionBuilder.apply(AttributeChangeReactionType.VALUE_REMOVED, reaction)
	}
}
