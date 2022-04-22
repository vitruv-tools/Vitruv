package tools.vitruv.framework.views.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.nio.file.Files
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.util.EcoreUtil.Copier
import org.eclipse.emf.ecore.xmi.XMLResource

import static extension com.google.common.base.Preconditions.checkNotNull
import static extension com.google.common.base.Preconditions.checkState
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
     * Copies some or all of the elements and references in the given view source resources to the given new resource set.
     * The copied root elements can be restricted with the <code>rootElementPredicate</code>.
     * 
     * @param originalResources The view source resources to copy
     * @param newResourceSet The resource set to which the copies are attached
     * @param rootElementPredicate A predicate to include only a subset of the root elements of the <code>originalResources</code>
     * @returns A mapping from each original resource to its copy
     */
    static def Map<Resource, Resource> copyViewSourceResources(Iterable<Resource> originalResources,
        ResourceSet newResourceSet, (EObject)=>Boolean rootElementPredicate) {
        // do not copy xmi:id for view source resources as the change recorder does not record them
        // and thus the information will be lost when propagating changes back to the view source
        copyResources(originalResources, newResourceSet, false, rootElementPredicate)
    }

    /**
     * Copies all elements and references in the given view resources to the given new resource set.
     * 
     * @param originalResources The view resources to copy
     * @param newResourceSet The resource set to which the copies are attached
     * @returns A mapping from each original resource to its copy
     */
    static def Map<Resource, Resource> copyViewResources(Iterable<Resource> originalResources,
        ResourceSet newResourceSet) {
        // copy xmi:id for view resources such that an exact copy is created
        // this is e.g. necessary for change deriving strategies that rely on identifiers
        copyResources(originalResources, newResourceSet, true)[true]
    }

    /**
     * Copies all elements and references in the give view resource to the given new resource set.
     * 
     * @param originalResource The view resource to copy
     * @param newResourceSet The resource set to which the copy is attached
     * @returns The newly created copy of the resource
     */
    static def Resource copyViewResource(Resource originalResource, ResourceSet newResourceSet) {
        val mapping = copyViewResources(#[originalResource], newResourceSet)
        return mapping.get(originalResource)
    }

    private static def Map<Resource, Resource> copyResources(Iterable<Resource> originalResources,
        ResourceSet newResourceSet, Boolean copyXmlIds, (EObject)=>Boolean rootElementPredicate) {
        val resourceMapping = new HashMap<Resource, Resource>()
        for (umlResource : originalResources.filter[isWritableUmlResource].toList) {
            val copy = copyUmlResource(umlResource, newResourceSet)
            if (copyXmlIds) {
                if (umlResource instanceof XMLResource && copy instanceof XMLResource) {
                    copyUmlIds(umlResource as XMLResource, copy as XMLResource)
                }
            }
            resourceMapping.put(umlResource, copy)
        }
        val otherMapping = originalResources.filter[!isWritableUmlResource].toList.copyResourcesInternal(newResourceSet,
            copyXmlIds, rootElementPredicate)
        otherMapping.keySet.forEach[resourceMapping.put(it, otherMapping.get(it))]
        return resourceMapping
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
    private static def Resource copyUmlResource(Resource originalResource, ResourceSet newResourceSet) {
        val originalURI = originalResource.URI
        val tempFilePath = Files.createTempFile(null, "." + originalURI.fileExtension)
        val tempURI = URI.createFileURI(tempFilePath.toString)
        originalResource.URI = tempURI
        originalResource.save(null)
        originalResource.URI = originalURI
        val copiedResource = newResourceSet.getResource(tempURI, true)
        copiedResource.URI = originalURI
        Files.delete(tempFilePath)
        EcoreUtil.resolveAll(copiedResource)
        return copiedResource
    }

    /**
     * Copies the selected elements in the given resources to the given new resource set.
     * It is necessary to process all resources together, because there may be elements that are root 
     * elements of one resource but have a container in another resource (e.g. a Java CompilationUnit 
     * is root of a resource but can be contained in a package persisted in another resource).
     * We must NOT copy these elements but only copy the containing element and then resolve it to avoid 
     * element duplication.
     */
    private static def Map<Resource, Resource> copyResourcesInternal(Iterable<Resource> originalResources,
        ResourceSet newResourceSet, Boolean copyXmlIds, (EObject)=>Boolean rootElementPredicate) {
        val copier = new Copier(true)
        var resourceMapping = new HashMap<Resource, Resource>()
        for (originalResource : originalResources) {
            val elementsContainedInResource = originalResource.contents.filter [
                !isContainedInOtherThanOwnResource(originalResources)
            ].toList
            copier.copyAll(elementsContainedInResource)
        }
        copier.copyReferences()
        for (originalResource : originalResources) {
            val copiedResource = newResourceSet.resourceFactoryRegistry?.getFactory(originalResource.URI)?.
                createResource(originalResource.URI).checkNotNull("Cannot create resource copy: %s",
                    originalResource.URI)
            val selectedRootElements = originalResource.contents.filter[rootElementPredicate.apply(it)]
            val mappedRootElements = selectedRootElements.map [
                checkNotNull(copier.get(it), "corresponding object for %s is null", it)
            ]
            copiedResource.contents.addAll(mappedRootElements)
            newResourceSet.resources += copiedResource
            resourceMapping.put(originalResource, copiedResource)
        }

        if (copyXmlIds) {
            for (entry: copier.entrySet) {
                val sourceElement = entry.key
                val targetElement = entry.value
                if (sourceElement.eResource instanceof XMLResource && targetElement.eResource instanceof XMLResource) {
                    val id = (sourceElement.eResource as XMLResource).getID(sourceElement)
                    (targetElement.eResource as XMLResource).setID(targetElement, id)
                }
            }
        }
        return resourceMapping
    }

    private static def boolean isContainedInOtherThanOwnResource(EObject eObject, Iterable<Resource> resources) {
        val rootContainerResource = EcoreUtil.getRootContainer(eObject).eResource
        return rootContainerResource !== eObject.eResource && resources.contains(rootContainerResource)
    }

    private static def boolean isWritableUmlResource(Resource resource) {
        resource.URI.fileExtension == "uml" && !resource.URI.isPathmap
    }

    /**
     * Copies the IDs of the source's elements to the target. 
     * It is expected that both resources are in an identical state.
     */
    private static def void copyUmlIds(XMLResource source, XMLResource target) {
        val sourceIterator = source.allContents
        val targetIterator = target.allContents
        while (sourceIterator.hasNext && targetIterator.hasNext) {
            val sourceObject = sourceIterator.next
            val targetObject = targetIterator.next
            checkState(sourceObject.eClass === targetObject.eClass, "non matching elements %s and %s", sourceObject,
                targetObject)
            target.setID(targetObject, source.getID(sourceObject))
        }
        checkState(!sourceIterator.hasNext, "source uml resource has too many elements")
        checkState(!targetIterator.hasNext, "target uml resource has too many elements")
    }
}
