package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute

import java.lang.annotation.Target
import java.lang.annotation.Retention

@Target(TYPE)
@Retention(RUNTIME)
annotation AttributeMappingOperator {

	String name
	// TODO Principally the operator may require a less specific input type and provide a more specific output type,
	// for both the commonality and the participation side.
	AttributeType commonalityAttributeType
	AttributeType participationAttributeType
}
