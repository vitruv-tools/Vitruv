package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import java.lang.annotation.Target
import java.lang.annotation.Retention
import tools.vitruv.extensions.dslruntime.commonalities.operators.ClassProcessor
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.extensions.dslruntime.commonalities.operators.OperatorNameProcessor
import org.eclipse.xtend.lib.macro.Active

@Target(TYPE)
@Retention(RUNTIME)
@Active(ParticipationConditionOperatorProcessor)
annotation ParticipationConditionOperator {
	String name
}

final class ParticipationConditionOperatorProcessor implements ClassProcessor {
	@Delegate val ClassProcessor delegate = new OperatorNameProcessor(ParticipationConditionOperator)
}