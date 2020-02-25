package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition

import com.google.common.base.Preconditions
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature

abstract class AbstractParticipationConditionOperator implements ParticipationConditionOperator {

	val protected EObject object
	val protected EStructuralFeature feature
	val protected List<Object> parameters // can be empty

	new(EObject object, EStructuralFeature feature, List<Object> parameters) {
		Preconditions.checkNotNull(object, "Object is null")
		Preconditions.checkNotNull(feature, "Feature is null")
		Preconditions.checkNotNull(parameters, "Parameters is null")
		this.object = object
		this.feature = feature
		this.parameters = parameters
	}
}
