package tools.vitruv.dsls.reactions.scoping

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.IResourceDescriptionsProvider
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguagePackage

@Singleton
class RoutinesImportScopeHelper {

	@Inject IResourceDescriptionsProvider provider;
	@Inject IContainer.Manager containerManager;

	def Iterable<IEObjectDescription> getVisibleReactionsSegmentDescriptions(Resource resource, boolean includeLocalReactionsSegments) {
		val resourceDescriptions = provider.getResourceDescriptions(resource.resourceSet);
		val resourceDesc = resourceDescriptions.getResourceDescription(resource.URI)
		val visibleContainers = containerManager.getVisibleContainers(resourceDesc, resourceDescriptions);
		val visibleResourceDescriptions = visibleContainers.map[it.resourceDescriptions].flatten;
		val filteredResourceDescriptions = visibleResourceDescriptions.filter [
			includeLocalReactionsSegments || !it.equals(resourceDesc)
		];
		val visibleReactionsSegments = filteredResourceDescriptions.map [
			getExportedObjectsByType(ReactionsLanguagePackage.eINSTANCE.reactionsSegment)
		].flatten;
		return visibleReactionsSegments
	}
}
