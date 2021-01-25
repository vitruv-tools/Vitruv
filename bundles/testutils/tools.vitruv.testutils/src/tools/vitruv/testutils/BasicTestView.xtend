package tools.vitruv.testutils

import org.eclipse.emf.ecore.resource.ResourceSet
import java.nio.file.Path
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.common.notify.Notifier
import java.util.function.Consumer
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.EObject
import static com.google.common.base.Preconditions.checkArgument
import tools.vitruv.framework.util.bridges.EMFBridge
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.loadOrCreateResource
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import static extension java.nio.file.Files.move
import static java.nio.file.Files.createDirectories

/**
 * A minimal test view that gives access to resources, but does not record any changes.
 */
class BasicTestView implements TestView {
	val ResourceSet resourceSet
	val Path persistenceDirectory
	@Accessors
	val TestUserInteraction userInteraction
	val UriMode uriMode

	/**
	 * Creates a test view that will store its persisted resources in the provided {@code persistenceDirectory},
	 * and use the provided {@code uriMode}.
	 */
	new(Path persistenceDirectory, UriMode uriMode) {
		this(persistenceDirectory, UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null),
			uriMode)
	}

	/**
	 * Creates a test view that will store its persisted resources in the provided {@code persistenceDirectory},
	 * allow to program interactions for the provided {@code interactionProvider}, and use the provided {@code uriMode}.
	 */
	new(Path persistenceDirectory, PredefinedInteractionResultProvider interactionProvider, UriMode uriMode) {
		this(new ResourceSetImpl().withGlobalFactories(), persistenceDirectory, interactionProvider, uriMode)
	}

	/**
	 * Creates a test view that will store its persisted resources in the provided {@code persistenceDirectory}, load
	 * resources into the provided {@code resourceSet}, allow to program interactions for the provided
	 *  {@code interactionProvider}, and use the provided {@code uriMode}.
	 */
	new(ResourceSet resourceSet, Path persistenceDirectory, PredefinedInteractionResultProvider interactionProvider,
		UriMode uriMode) {
		this.resourceSet = resourceSet
		this.persistenceDirectory = persistenceDirectory
		this.uriMode = uriMode
		this.userInteraction = new TestUserInteraction(interactionProvider)
	}

	override resourceAt(URI modelUri) {
		synchronized (resourceSet) {
			resourceSet.loadOrCreateResource(modelUri)
		}
	}

	override <T> T from(Class<T> clazz, URI modelUri) {
		return from(clazz, resourceSet.getResource(modelUri, true))
	}

	override <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
		consumer.accept(notifier)
		return notifier
	}

	override <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
		val toSave = determineResource(notifier)
		notifier.record(consumer)
		(toSave ?: determineResource(notifier)).save(emptyMap())
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
		resourceSet.resources.forEach[unload]
		resourceSet.resources.clear
	}
}
