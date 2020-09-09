package tools.vitruv.dsls.commonalities.generator.reactions.operator

import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.operators.AttributeOperand

interface OperatorContext {

	/**
	 * Gets the {@link TypeProvider}.
	 */
	def TypeProvider getTypeProvider()

	/**
	 * Whether to pass attribute values instead of {@link AttributeOperand}
	 * instances for participation attribute operands.
	 * <p>
	 * May throw an {@link UnsupportedOperationException} if the context does
	 * not support participation attribute operands.
	 */
	def boolean passParticipationAttributeValues()

	/**
	 * Whether to pass attribute values instead of {@link AttributeOperand}
	 * instances for commonality attribute operands.
	 * <p>
	 * May throw an {@link UnsupportedOperationException} if the context does
	 * not support commonality attribute operands.
	 */
	def boolean passCommonalityAttributeValues()

	/**
	 * Gets the expression for accessing the involved commonality instance.
	 * <p>
	 * May throw an {@link UnsupportedOperationException} if the context does
	 * not support operands that require access to the commonality instance.
	 */
	def XExpression getIntermediate()

	/**
	 * Gets the expression for accessing the participation object for the
	 * given participation class.
	 * <p>
	 * May throw an {@link UnsupportedOperationException} if the context does
	 * not support operands that require access to participation objects.
	 */
	def XExpression getParticipationObject(ParticipationClass participationClass)
}
