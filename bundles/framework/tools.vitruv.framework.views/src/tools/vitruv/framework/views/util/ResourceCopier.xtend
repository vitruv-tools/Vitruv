package tools.vitruv.framework.views.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.nio.file.Files
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.util.EcoreUtil.Copier

import static extension com.google.common.base.Preconditions.checkNotNull
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.isPathmap

/**
 * A utility class to create copies of resources.
 */
@Utility
class ResourceCopier {
    /**
     * Indicates whether the given resource requires a full copy.
     * If <code>false</code> is returned, the resource supports a partial copy,
     * i.e. not all root elements need to be contained in the copy.
     */
    static def boolean requiresFullCopy(Resource resource) {
        resource.isWritableUmlResource
    }

    /**
     * Copies some or all of the elements and references in the given resources to the given new resource set.
     * The copied root elements can be restricted with the <code>rootElementPredicate</code>.
     * 
     * @param originalResources The resources to copy
     * @param newResourceSet The resource set to which the copies are attached
     * @param rootElementPredicate A predicate to include only a subset of the root elements of the <code>originalResources</code>
     */
    static def copyResources(Iterable<Resource> originalResources, ResourceSet newResourceSet, (EObject)=>Boolean rootElementPredicate) {
        for (umlResource : originalResources.filter[isWritableUmlResource].toList) {
            tools.vitruv.framework.views.util.ResourceCopier.copyUmlResource(umlResource, newResourceSet)
        }
        originalResources.filter[!isWritableUmlResource].toList.copyResourcesInternal(newResourceSet, rootElementPredicate)
    }

    /**
     * Copies all elements and references in the given resources to the given new resource set.
     * 
     * @param originalResources The resources to copy
     * @param newResourceSet The resource set to which the copies are attached
     */
    static def copyResources(Iterable<Resource> originalResources, ResourceSet newResourceSet) {
        copyResources(originalResources, newResourceSet) [true]
    }

    /**
     * NOTE: This is a hack, because {@link EcoreUtil#copyAll} does not work for UML models.
     * This is due to the fact that the UML metamodel uses some weird manually and partly derived
     * collections, which are not properly handled by the ordinary {@link EcoreUtil.Copier}.
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
    private static def void copyUmlResource(Resource originalResource, ResourceSet newResourceSet) {
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
    private static def void copyResourcesInternal(Iterable<Resource> originalResources, ResourceSet newResourceSet,
        (EObject)=>Boolean rootElementPredicate) {
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
            val selectedRootElements = originalResource.contents.filter[rootElementPredicate.apply(it)]
            val mappedRootElements = selectedRootElements.map [
                checkNotNull(copier.get(it), "corresponding object for %s is null", it)
            ]
            viewResource.contents.addAll(mappedRootElements)
            newResourceSet.resources += viewResource
        }
    }

    private static def boolean isContainedInOtherThanOwnResource(EObject eObject, Iterable<Resource> resources) {
        val rootContainerResource = EcoreUtil.getRootContainer(eObject).eResource
        return rootContainerResource !== eObject.eResource && resources.contains(rootContainerResource)
    }

    private static def boolean isWritableUmlResource(Resource resource) {
        resource.URI.fileExtension == "uml" && !resource.URI.isPathmap
    }
}
