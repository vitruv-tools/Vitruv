package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.interaction.UserInteractionBase
import tools.vitruv.framework.change.MetamodelDescriptor
import java.util.Set

/**
 * A {@link TransactionalChange} defines one or more {@link VitruviusChange}s, which have to be performed
 * together. They were recorded together and have to propagated to models completely or not at all.
 */
interface TransactionalChange extends VitruviusChange {
	/**
	 * Returns the unique metamodel descriptor for the metamodels of the elements whose instances 
	 * have been modified in this change.
	 * 
	 * @see VitruviusChange#getAffectedEObjectsMetamodelDescriptors 
	 */
	def MetamodelDescriptor getAffectedEObjectsMetamodelDescriptor()

	override getAffectedEObjectsMetamodelDescriptors() {
		Set.of(affectedEObjectsMetamodelDescriptor)
	}

	def void setUserInteractions(Iterable<UserInteractionBase> userInputs)

	override TransactionalChange copy()
}
