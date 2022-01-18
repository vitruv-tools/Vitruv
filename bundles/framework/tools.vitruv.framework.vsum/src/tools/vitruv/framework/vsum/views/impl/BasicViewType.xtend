package tools.vitruv.framework.vsum.views.impl

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import java.nio.file.Files
import java.nio.file.Path
import static extension com.google.common.base.Preconditions.checkNotNull
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.vsum.views.ChangeableViewSource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.isPathmap
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil.Copier
import tools.vitruv.framework.vsum.views.selectors.BasicViewElementSelector
import tools.vitruv.framework.vsum.views.ViewSelection

/**
 * A basic view type that allows creating views based on a basic element-wise selection mechanism.
 */
class BasicViewType extends AbstractViewType<BasicViewElementSelector> {

	new(String name) {
		super(name)
	}

	override createSelector(ChangeableViewSource viewSource) {
		return new BasicViewElementSelector(this, viewSource,
			viewSource.viewSourceModels.map[contents.head].filterNull.toList)
	}

	override createView(BasicViewElementSelector selector) {
		checkArgument(selector.viewType === this, "cannot create view with selector for different view type")
		return new BasicModelView(selector.viewType, selector.viewSource, selector.selection)
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
	// TODO HK Provide an appropriate temporary resource instead of the current magic file
	private static def void copyUmlModel(Resource originalResource, ResourceSet newResourceSet) {
		val originalURI = originalResource.URI
		val tempURI = originalURI.trimFileExtension.appendSegment("save." + originalURI.fileExtension)
		originalResource.URI = tempURI
		originalResource.save(null)
		originalResource.URI = originalURI
		val viewResource = newResourceSet.getResource(tempURI, true)
		viewResource.URI = originalURI
		Files.delete(Path.of(org.eclipse.emf.common.util.URI.decode(tempURI.path)))
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
