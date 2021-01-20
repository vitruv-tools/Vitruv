package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation

import java.lang.annotation.Target
import java.lang.annotation.Retention
import tools.vitruv.extensions.dslruntime.commonalities.operators.ClassProcessor
import org.eclipse.xtend.lib.macro.Active
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.extensions.dslruntime.commonalities.operators.OperatorNameProcessor

@Target(TYPE)
@Retention(RUNTIME)
@Active(ParticipationRelationOperatorProcessor)
annotation ParticipationRelationOperator {
	String name
}

final class ParticipationRelationOperatorProcessor implements ClassProcessor {
	@Delegate val ClassProcessor processsor = new OperatorNameProcessor(ParticipationRelationOperator)
}