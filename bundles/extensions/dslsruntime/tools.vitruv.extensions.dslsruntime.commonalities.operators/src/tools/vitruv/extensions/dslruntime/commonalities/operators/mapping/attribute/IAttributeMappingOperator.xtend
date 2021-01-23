package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute

/**
 * @param <C> The commonality attribute type
 * @param <P> The participation attribute type
 */
interface IAttributeMappingOperator<C, P> {

	/**
	 * Applies this operator in the direction towards the commonality.
	 * 
	 * @param participationAttributeValue
	 * 			the participation attribute value, or <code>null</code> if the
	 * 			mapping did not specify any participation attribute operand
	 * @return the result value
	 */
	def C applyTowardsCommonality(P participationAttributeValue)

	/**
	 * Applies this operator in the direction towards the commonality.
	 * 
	 * @param commonalityAttributeValue
	 * 			the commonality attribute value
	 * @return the result value
	 */
	def P applyTowardsParticipation(C commonalityAttributeValue)
}
