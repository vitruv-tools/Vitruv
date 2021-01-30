package tools.vitruv.testutils

import java.nio.file.Path
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.notify.Notifier
import java.util.function.Consumer
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.EObject
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.util.bridges.EMFBridge
import static extension java.nio.file.Files.move
import static java.nio.file.Files.createDirectories
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.util.SavingResourceSet
import tools.vitruv.framework.domains.repository.DomainAwareResourceSet
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.loadOrCreateResource

/**
 * A minimal test view that gives access to resources, but does not record any changes.
 */ 
class BasicTestView implements TestView {
	val Path persistenceDirectory
	val SavingResourceSet resourceSet
	@Accessors
	val TestUserInteraction userInteraction
	val UriMode uriMode
	
	/**
	 * Creates a test view for the provided {@code targetDomains} that will store its persisted resources in the
	 *  provided {@code persistenceDirectory}, and use the provided {@code uriMode}.
	 */
	new(Path persistenceDirectory, UriMode uriMode, VitruvDomainRepository targetDomains) {
		this(persistenceDirectory, new TestUserInteraction(), uriMode, targetDomains)
	}

	/**
	 * Creates a test view for the provided {@code targetDomains} that will store its persisted resources in the
	 * provided {@code persistenceDirectory}, allow to program interactions through the provided {@code userInteraction},
	 * and use the provided {@code uriMode}.
	 */
	new(
		Path persistenceDirectory,
		TestUserInteraction userInteraction,
		UriMode uriMode,
		VitruvDomainRepository targetDomains
	) {
		this(persistenceDirectory, new DomainAwareResourceSet(targetDomains), userInteraction, uriMode)
	}
	
	/**
	 * Creates a test view that will store its persisted resources in the provided {@code persistenceDirectory}, access
	 * resources through the provided {@code resourceSet}, allow to program interactions through the provided 
	 * {@code userInteraction}, and use the provided {@code uriMode}.
	 */
	new(
		Path persistenceDirectory,
		SavingResourceSet resourceSet,
		TestUserInteraction userInteraction,
		UriMode uriMode
	) {
		this.persistenceDirectory = persistenceDirectory
		this.resourceSet = resourceSet
		this.userInteraction = userInteraction
		this.uriMode = uriMode
	}
	
	override resourceAt(URI modelUri) {
		resourceSet.loadOrCreateResource(modelUri)
	}

	override <T extends EObject> T from(Class<T> clazz, URI modelUri) {
		val resource = resourceSet.getResource(modelUri, true)
		return clazz.from(resource)
	}

	override <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
		consumer.accept(notifier)
		return notifier
	}

	override <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
		val toSave = determineResource(notifier)
		consumer.accept(notifier)
		(toSave ?: determineResource(notifier))?.saveOrDelete()
		return emptyList()
	}
	
	override moveTo(Resource resource, Path newViewRelativePath) {
		resource.URI.absolutePath.moveSafelyTo(persistenceDirectory.resolve(newViewRelativePath))
		resource.URI = newViewRelativePath.uri
	}

	override moveTo(Resource resource, URI newUri) {
		resource.URI.absolutePath.moveSafelyTo(newUri.absolutePath)
		resource.URI = newUri
	}
	
	def private moveSafelyTo(Path source, Path target) {
		createDirectories(target.parent)
		move(source, target)
	}

	def private saveOrDelete(Resource resource) {
		if (resource.contents.isEmpty) {
			resource.delete(emptyMap)
		} else {
			resourceSet.save(resource)
		}
	}
	 
	override getUri(Path viewRelativePath) {
		checkArgument(viewRelativePath !== null, "The viewRelativePath must not be null!")
		checkArgument(!viewRelativePath.isEmpty, "The viewRelativePath must not be empty!")
		return switch (uriMode) {
			case PLATFORM_URIS: {
				// platform URIs must always use '/' and be relative to the project (fileName) rather than the workspace
				EMFBridge.createURI(persistenceDirectory.fileName.resolve(viewRelativePath).normalize().join('/'))
			}
			case FILE_URIS:
				EMFBridge.getEmfFileUriForFile(persistenceDirectory.resolve(viewRelativePath).normalize().toFile())
		}
	}
	
	def private getAbsolutePath(URI uri) {
		if (uri.isFile) {
			checkArgument(uri.hasAbsolutePath, '''«uri» is a file URI but does not have an absolute path!''')
			Path.of(URI.decode(uri.path))
		} else if (uri.isPlatformResource) {
			persistenceDirectory.resolveSibling(uri.toPlatformString(true))
		} else {
			throw new IllegalArgumentException('''This URI is not supported by this view: «uri»''')
		}
	}

	def private determineResource(Notifier notifier) {
		switch (notifier) {
			Resource: notifier
			EObject: notifier.eResource
			default: null
		}
	}

	override close() throws Exception {
		resourceSet.resources.forEach [unload()]
		resourceSet.resources.clear()
	}
}
