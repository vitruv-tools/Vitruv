package tools.vitruv.dsls.reactions.scoping

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescriptionsProvider
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguagePackage

@Singleton
class ReactionsImportScopeHelper {

	@Inject IResourceDescription.Manager descriptionManager;
	@Inject IResourceDescriptionsProvider descriptionsProvider;
	@Inject IContainer.Manager containerManager;

	def Iterable<IEObjectDescription> getVisibleReactionsSegmentDescriptions(Resource resource, boolean includeLocalReactionsSegments) {
		val resourceDesc = descriptionManager.getResourceDescription(resource);
		val resourceDescriptions = descriptionsProvider.getResourceDescriptions(resource.resourceSet);
		val visibleContainers = containerManager.getVisibleContainers(resourceDesc, resourceDescriptions);
		val visibleResourceDescriptions = visibleContainers.map[it.resourceDescriptions].flatten.filter [
			includeLocalReactionsSegments || !it.URI.equals(resourceDesc.URI)
		];
		val visibleReactionsSegmentDescs = visibleResourceDescriptions.map [
			getExportedObjectsByType(ReactionsLanguagePackage.eINSTANCE.reactionsSegment)
		].flatten;
		return visibleReactionsSegmentDescs;
	}
}
