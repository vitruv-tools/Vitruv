package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import java.util.List
import java.util.Objects
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.extensions.dslruntime.commonalities.operators.OperatorName

@OperatorName('=')
class EqualsOperator extends AbstractSingleArgumentOperator {

	new(EObject object, EStructuralFeature feature, List<Object> parameters) {
		super(object, feature, parameters)
	}

	override check() {
		val value = object.eGet(feature)
		return Objects.equals(value, parameters.head)
	}

	override enforce() {
		object.eSet(feature, parameters.head)
	}
}
