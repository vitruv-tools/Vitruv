package tools.vitruv.extensions.dslruntime.commonalities.participationrelations

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
abstract class AbstractParticipationRelation implements ParticipationRelationOperator {
	protected val EObject[] leftObjects
	protected val EObject[] rightObjects
}