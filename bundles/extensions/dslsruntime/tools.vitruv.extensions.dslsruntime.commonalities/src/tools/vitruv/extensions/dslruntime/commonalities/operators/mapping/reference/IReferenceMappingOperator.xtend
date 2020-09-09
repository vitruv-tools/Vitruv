package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference

import org.eclipse.emf.ecore.EObject

/**
 * Assumptions which all operators need to fulfill:
 * <ul>
 * <li>Every object is contained by at most one other object. This also implies
 * that reference mapping operators are 'disjunct': If one reference mapping
 * operator provides a container for a certain object then no other reference
 * mapping is allowed to provide a different container for the same object.
 * <li>No cyclic containments.
 * <li>No self containments.
 * </ul>
 */
interface IReferenceMappingOperator {

	def Iterable<? extends EObject> getContainedObjects(EObject container)

	def EObject getContainer(EObject object)

	def boolean isContained(EObject container, EObject contained)

	def void insert(EObject container, EObject object)
}
