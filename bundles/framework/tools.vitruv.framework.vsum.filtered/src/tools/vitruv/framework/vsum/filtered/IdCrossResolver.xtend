package tools.vitruv.framework.vsum.filtered

import tools.vitruv.framework.change.echange.id.IdResolver
import org.eclipse.emf.ecore.resource.ResourceSet
import java.util.Objects
import tools.vitruv.framework.vsum.filtered.internal.IdCrossResolverImpl
import accesscontrol.ViewSourceCorrespondence

interface IdCrossResolver extends IdResolver {
	def boolean isNew(String id)

	/**
	 * Instantiates a cross resolver which resolves elements from the view to the corresponding elements in the source.
	 * 
	 * @param sourceResourceSet -
	 * 		the {@link ResourceSet} containing the source resources, may not be {@code null}
	 * @param viewResourceSet -
	 * 		the {@link ResourceSet} containing the view resources, may not be {@code null}
	 * @param viewSourceCorrespondence - 
	 * 		the correspondence relation between the elements in the view and the elements in the source
	 * @throws IllegalArgumentException if any given {@link ResourceSet}s is {@code null} or {@link OneSidedCorrespondence} is {@code null}
	 */
	static def IdCrossResolver create(ResourceSet sourceResourceSet, ResourceSet viewResourceSet,
		ViewSourceCorrespondence viewSourceCorrespondence) {
		Objects.requireNonNull(viewSourceCorrespondence);
		return new IdCrossResolverImpl(IdResolver.create(viewResourceSet), IdResolver.create(sourceResourceSet),
			viewSourceCorrespondence)
	}
}
