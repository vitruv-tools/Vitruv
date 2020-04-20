package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation

import java.lang.annotation.Target
import java.lang.annotation.Retention

@Target(TYPE)
@Retention(RUNTIME)
annotation ParticipationRelationOperator {

	String name
}
