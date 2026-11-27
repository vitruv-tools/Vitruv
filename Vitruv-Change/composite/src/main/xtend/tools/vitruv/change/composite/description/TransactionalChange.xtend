package tools.vitruv.change.composite.description

import tools.vitruv.change.composite.description.VitruviusChange
import tools.vitruv.change.interaction.UserInteractionBase
import tools.vitruv.change.composite.MetamodelDescriptor
import java.util.Set

/**
 * A {@link TransactionalChange} defines one or more {@link VitruviusChange}s, which have to be performed
 * together. They were recorded together and have to propagated to models completely or not at all.
 */
interface TransactionalChange<Element> extends VitruviusChange<Element> {
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

	override TransactionalChange<Element> copy()
}
