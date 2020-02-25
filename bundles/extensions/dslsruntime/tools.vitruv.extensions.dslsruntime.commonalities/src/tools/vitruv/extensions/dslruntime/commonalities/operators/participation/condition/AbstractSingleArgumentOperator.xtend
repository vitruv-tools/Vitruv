package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import com.google.common.base.Preconditions
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature

abstract class AbstractSingleArgumentOperator extends AbstractParticipationConditionOperator {

	new(EObject object, EStructuralFeature feature, List<Object> parameters) {
		super(object, feature, parameters)
		Preconditions.checkArgument(!parameters.empty, "Missing parameter(s)!")
		Preconditions.checkArgument(parameters.size == 1, "Too many parameters!")
	}
}
