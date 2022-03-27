package tools.vitruv.framework.vsum.filtered

import tools.vitruv.framework.change.echange.id.IdResolver
import org.eclipse.emf.ecore.resource.ResourceSet
import java.util.Objects
import tools.vitruv.framework.vsum.filtered.internal.IdCrossResolverImpl
import tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.ViewSourceCorrespondence

interface IdCrossResolver extends IdResolver {
	/**
	 * Returns if no object can be found in the source ResourceSet for the given id 
	 * (the object associated with the given id is created in the change and thus cannot
	 * be found in the source as the change is not evaluated on the source yet)
	 * @param id
	 */
	def boolean notPresentInSource(String id)

	/**
	 * Instantiates a cross resolver which resolves elements from the view to the corresponding elements in the source.
	 * 
	 * @param sourceResourceSet -
	 * 		the {@link ResourceSet} containing the source resources, may not be {@code null}
	 * @param viewResourceSet -
	 * 		the {@link ResourceSet} containing the view resources, may not be {@code null}
	 * @param viewSourceCorrespondence - 
	 * 		the correspondence relation between the elements in the view and the elements in the source
	 * @throws IllegalArgumentException if any given {@link ResourceSet}s is {@code null} or {@link ViewSourceCorrespondence} is {@code null}
	 */
	static def IdCrossResolver create(ResourceSet sourceResourceSet, ResourceSet viewResourceSet,
		ViewSourceCorrespondence viewSourceCorrespondence) {
		Objects.requireNonNull(viewSourceCorrespondence);
		return new IdCrossResolverImpl(IdResolver.create(viewResourceSet), IdResolver.create(sourceResourceSet),
			viewSourceCorrespondence)
	}
}
