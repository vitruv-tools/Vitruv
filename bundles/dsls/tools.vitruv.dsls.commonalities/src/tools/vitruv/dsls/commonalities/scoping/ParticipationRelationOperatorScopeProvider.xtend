package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Singleton
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.IParticipationRelationOperator

import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtext.scoping.IScopeProvider
import javax.inject.Inject

@Singleton
class ParticipationRelationOperatorScopeProvider implements IScopeProvider {
	@Delegate val IScopeProvider delegate
	
	@Inject
	new(OperatorScopeProvider.Factory factory) {
		delegate = factory.forOperatorType(IParticipationRelationOperator)
			.withDefaultImports("tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation._")
	}
}
