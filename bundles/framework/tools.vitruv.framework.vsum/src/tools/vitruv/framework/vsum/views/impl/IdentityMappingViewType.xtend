package tools.vitruv.framework.vsum.views.impl

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import java.nio.file.Files
import static extension com.google.common.base.Preconditions.checkNotNull
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.vsum.views.ChangeableViewSource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.isPathmap
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil.Copier
import tools.vitruv.framework.vsum.views.ViewSelection
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.vsum.views.selectors.DirectViewElementSelector

/**
 * A view type that allows creating views based on a basic element-wise selection mechanism
 * and providing a one-to-one (identity) mapping of elements within the {@link ViewSource}
 * to a created {@link View}.
 */
class IdentityMappingViewType extends AbstractViewType<DirectViewElementSelector> {

	new(String name) {
		super(name)
	}

	override createSelector(ChangeableViewSource viewSource) {
		return new DirectViewElementSelector(this, viewSource, viewSource.viewSourceModels.flatMap [
			if (isWritableUmlResource) {
				#[contents.head] // We can only copy writable UML resources as a whole, so no option to select specific root elements
			} else {
				contents
			}
		].filterNull.toList)
	}

	override createView(DirectViewElementSelector selector) {
		checkArgument(selector.viewType === this, "cannot create view with selector for different view type")
		return new RecordingView(selector.viewType, selector.viewSource, selector.selection)
	}

	override updateView(ModifiableView view) {
		view.modifyContents [ viewResourceSet |
			viewResourceSet.resources.forEach[unload]
			viewResourceSet.resources.clear
			val viewSources = view.viewSource.viewSourceModels
			val selection = view.selection
			val resourcesWithSelectedElements = viewSources.filter[contents.exists[selection.isViewObjectSelected(it)]]
			for (umlResource : resourcesWithSelectedElements.filter[isWritableUmlResource].toList) {
				copyUmlModel(umlResource, viewResourceSet)
			}
			resourcesWithSelectedElements.filter[!isWritableUmlResource].toList.copyModels(viewResourceSet, selection)
		]
	}

	/**
	 * NOTE: This is a hack, because {@link EcoreUtil#copyAll} does not work for UML models.
	 * This is due to the fact that the UML metamodel uses some weird manually and partly derived
	 * collections, which are not properly handled by the ordinary {@link EcoreUtil$Copier}.
	 * For example, the {@linkplain InterfaceRealization.clients} are a set of manually added
	 * elements combined with the {@linkplain InterfaceRealization.implementingClassifier}, which,
	 * in turn, is just the {@code eContainer} of the interface realization. The copier copies
	 * this implementing classifier reference although it is automatically inferred by the metamodel
	 * implementation, such that after the copy process the {@linkplain InterfaceRelization.clients}
	 * reference contains the according element twice. This happens at different places in the
	 * UML models.
	 * To circumvent the issue, we store the UML model to a temporary resource and reload it,
	 * because save/load properly handles the situation not covered by the copier. 
	 */
	private static def void copyUmlModel(Resource originalResource, ResourceSet newResourceSet) {
		val originalURI = originalResource.URI
		val tempFilePath = Files.createTempFile(null, "." + originalURI.fileExtension)
		val tempURI = URI.createFileURI(tempFilePath.toString)
		originalResource.URI = tempURI
		originalResource.save(null)
		originalResource.URI = originalURI
		val viewResource = newResourceSet.getResource(tempURI, true)
		viewResource.URI = originalURI
		Files.delete(tempFilePath)
		EcoreUtil.resolveAll(viewResource)
	}

	/**
	 * Copies the selected elements in the given resources to the given new resource set.
	 * It is necessary to process all resources together, because there may be elements that are root 
	 * elements of one resource but have a container in another resource (e.g. a Java CompilationUnit 
	 * is root of a resource but can be contained in a package persisted in another resource).
	 * We must NOT copy these elements but only copy the containing element and then resolve it to avoid 
	 * element duplication.
	 */
	private static def void copyModels(Iterable<Resource> originalResources, ResourceSet newResourceSet,
		ViewSelection viewSelection) {
		val copier = new Copier(true)
		for (originalResource : originalResources) {
			val elementsContainedInResource = originalResource.contents.filter [
				!isContainedInOtherThanOwnResource(originalResources)
			].toList
			copier.copyAll(elementsContainedInResource)
		}
		copier.copyReferences()
		for (originalResource : originalResources) {
			val viewResource = newResourceSet.resourceFactoryRegistry?.getFactory(originalResource.URI)?.createResource(
				originalResource.URI).checkNotNull("Cannot create view resource: %s", originalResource.URI)
			val selectedRootElements = originalResource.contents.filter[viewSelection.isViewObjectSelected(it)]
			val mappedRootElements = selectedRootElements.map [
				checkNotNull(copier.get(it), "corresponding object for %s is null", it)
			]
			viewResource.contents.addAll(mappedRootElements)
			newResourceSet.resources += viewResource
		}
	}

	private static def boolean isWritableUmlResource(Resource resource) {
		resource.URI.fileExtension == "uml" && !resource.URI.isPathmap
	}

	private static def boolean isContainedInOtherThanOwnResource(EObject eObject, Iterable<Resource> resources) {
		val rootContainerResource = EcoreUtil.getRootContainer(eObject).eResource
		return rootContainerResource !== eObject.eResource && resources.contains(rootContainerResource)
	}

}
