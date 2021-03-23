package tools.vitruv.dsls.reactions.scoping

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.Collections
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescriptionsProvider
import tools.vitruv.dsls.reactions.language.toplevelelements.TopLevelElementsPackage
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsSegment
import org.eclipse.emf.ecore.util.EcoreUtil

@Singleton
class ReactionsImportScopeHelper {

	@Inject IResourceDescription.Manager descriptionManager;
	@Inject IResourceDescriptionsProvider descriptionsProvider;
	@Inject IContainer.Manager containerManager;

	def Iterable<IEObjectDescription> getVisibleReactionsSegmentDescriptions(ReactionsSegment reactionsSegment) {
		if (reactionsSegment === null) return Collections.emptyList();
		val resource = reactionsSegment.eResource;
		if (resource === null) return Collections.emptyList();
		val resourceDesc = descriptionManager.getResourceDescription(resource);
		val resourceDescriptions = descriptionsProvider.getResourceDescriptions(resource.resourceSet);
		val visibleContainers = containerManager.getVisibleContainers(resourceDesc, resourceDescriptions);
		val visibleReactionsResources = visibleContainers.map[it.resourceDescriptions].flatten.filter [
			// we are only interested in .reactions resources here:
			val lastURISegment = it.URI.lastSegment;
			return lastURISegment !== null && lastURISegment.endsWith(".reactions");
		];
		val reactionsSegmentURI = EcoreUtil.getURI(reactionsSegment);
		val visibleReactionsSegmentDescriptions = visibleReactionsResources.map [
			getExportedObjectsByType(TopLevelElementsPackage.eINSTANCE.reactionsSegment)
		].flatten.filter [
			// the validator also uses this to globally find reactions segments with duplicate names,
			// so filtering out all reactions segments with equal name would break that,
			// whereas filtering by URI filters only the given reactions segment
			!it.EObjectURI.equals(reactionsSegmentURI)
		];
		return visibleReactionsSegmentDescriptions;
	}
}
