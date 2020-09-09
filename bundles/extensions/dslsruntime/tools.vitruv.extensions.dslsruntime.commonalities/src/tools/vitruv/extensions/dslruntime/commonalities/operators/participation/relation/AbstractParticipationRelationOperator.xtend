package tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
abstract class AbstractParticipationRelationOperator implements IParticipationRelationOperator {

	protected val EObject[] leftObjects
	protected val EObject[] rightObjects
}
