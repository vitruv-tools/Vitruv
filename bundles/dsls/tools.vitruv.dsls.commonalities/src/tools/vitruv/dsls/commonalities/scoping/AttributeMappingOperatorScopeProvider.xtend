package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Singleton
import tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.IAttributeMappingOperator

import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtext.scoping.IScopeProvider
import javax.inject.Inject

@Singleton
class AttributeMappingOperatorScopeProvider implements IScopeProvider {
	@Delegate val IScopeProvider delegate
	
	@Inject
	new(OperatorScopeProvider.Factory factory) {
		delegate = factory.forOperatorType(IAttributeMappingOperator)
			.withDefaultImports("tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute._")
	}
}
