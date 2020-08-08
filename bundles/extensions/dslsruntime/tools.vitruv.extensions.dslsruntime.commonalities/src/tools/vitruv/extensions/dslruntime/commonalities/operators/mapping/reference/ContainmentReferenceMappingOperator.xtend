package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState

import static com.google.common.base.Preconditions.*

/**
 * Base class for {@link IReferenceMappingOperator reference mapping operators}
 * which involve a regular containment reference between a container and its
 * contained objects.
 * <p>
 * The default implementation mimics the behavior of simple reference mappings.
 * <p>
 * TODO This type of operator is not properly supported by the language yet.
 */
class ContainmentReferenceMappingOperator extends AbstractReferenceMappingOperator {

	protected val EReference reference

	new(ReactionExecutionState executionState, EReference reference) {
		super(executionState)
		checkNotNull(reference, "reference is null")
		checkArgument(reference.isContainment, "reference is not a containment")
		this.reference = reference
	}

	protected def void validateContainer(EObject container) {
		checkNotNull(container, "container is null")
		checkArgument(container.eClass === reference.EContainingClass,
			"container is not of type " + reference.EContainingClass.name)
	}

	protected def void validateContainedObject(EObject containedObject) {
		checkNotNull(containedObject, "containedObject is null")
		checkArgument(containedObject.eClass === reference.EReferenceType,
			"containedObject is not of type " + reference.EReferenceType.name)
	}

	override getContainedObjects(EObject container) {
		validateContainer(container)
		val value = container.eGet(reference)
		if (reference.many) {
			return value as List<? extends EObject>
		} else {
			return #[value as EObject]
		}
	}

	override getContainer(EObject object) {
		validateContainedObject(object)
		return object.eContainer
	}

	override isContained(EObject container, EObject contained) {
		validateContainer(container)
		validateContainedObject(contained)
		return (contained.eContainer === container && contained.eContainmentFeature === reference)
	}

	override insert(EObject container, EObject object) {
		validateContainer(container)
		validateContainedObject(object)
		if (reference.many) {
			// Note: Unchecked cast, but we know that the list can contain the object.
			(container.eGet(reference) as List<EObject>) += object
		} else {
			container.eSet(reference, object)
		}
	}
}
