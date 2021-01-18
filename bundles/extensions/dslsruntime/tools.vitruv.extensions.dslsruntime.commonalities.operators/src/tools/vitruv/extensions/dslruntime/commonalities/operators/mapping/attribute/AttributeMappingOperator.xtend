package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute

import java.lang.annotation.Target
import java.lang.annotation.Retention
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.extensions.dslruntime.commonalities.operators.OperatorNameProcessor
import tools.vitruv.extensions.dslruntime.commonalities.operators.ClassProcessor
import org.eclipse.xtend.lib.macro.Active

@Target(TYPE)
@Retention(RUNTIME)
@Active(AttributeMappingOperatorProcessor)
annotation AttributeMappingOperator {
	String name
	// TODO Principally the operator may require a less specific input type and provide a more specific output type,
	// for both the commonality and the participation side.
	AttributeType commonalityAttributeType
	AttributeType participationAttributeType
}

final class AttributeMappingOperatorProcessor implements ClassProcessor {
	@Delegate val ClassProcessor processsor = new OperatorNameProcessor(AttributeMappingOperator)
}