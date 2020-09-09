package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import java.lang.annotation.Target
import java.lang.annotation.Retention

@Target(TYPE)
@Retention(RUNTIME)
annotation ParticipationConditionOperator {

	String name
}
