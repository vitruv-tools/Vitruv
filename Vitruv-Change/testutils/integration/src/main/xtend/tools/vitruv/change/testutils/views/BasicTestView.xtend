package tools.vitruv.change.testutils.views

import java.nio.file.Path
import java.util.List
import java.util.function.Consumer
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.change.composite.description.PropagatedChange
import tools.vitruv.change.testutils.TestUserInteraction

import static com.google.common.base.Preconditions.checkArgument
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static java.nio.file.Files.createDirectories
import static java.nio.file.Files.move
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

/**
 * A minimal test view that gives access to resources, but does not record any changes.
 */
class BasicTestView implements TestView {
	val Path persistenceDirectory
	val ResourceSet resourceSet
	@Accessors
	val TestUserInteraction userInteraction
	val UriMode uriMode

	/**
	 * Creates a test view that will store its persisted resources in the
	 * provided {@code persistenceDirectory}, and use the provided {@code uriMode}.
	 */
	new(Path persistenceDirectory, UriMode uriMode) {
		this(persistenceDirectory, new TestUserInteraction(), uriMode)
	}

	/**
	 * Creates a test view that will store its persisted resources in the
	 * provided {@code persistenceDirectory}, allow to program interactions through the provided {@code userInteraction},
	 * and use the provided {@code uriMode}.
	 */
	new(
		Path persistenceDirectory,
		TestUserInteraction userInteraction,
		UriMode uriMode
	) {
		this(
			persistenceDirectory,
			new ResourceSetImpl().withGlobalFactories(),
			userInteraction,
			uriMode
		)
	}

	/**
	 * Creates a test view that will store its persisted resources in the provided {@code persistenceDirectory}, access
	 * resources through the provided {@code resourceSet}, allow to program interactions through the provided
	 * {@code userInteraction}, and use the provided {@code uriMode}.
	 */
	new(
		Path persistenceDirectory,
		ResourceSet resourceSet,
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
			resource.save(emptyMap)
		}
	}

	override getUri(Path viewRelativePath) {
		checkArgument(viewRelativePath !== null, "The viewRelativePath must not be null!")
		checkArgument(!viewRelativePath.isEmpty, "The viewRelativePath must not be empty!")
		return switch (uriMode) {
			case PLATFORM_URIS: {
				// platform URIs must always use '/' and be relative to the project (fileName) rather than the workspace
				createPlatformResourceURI(persistenceDirectory.fileName.resolve(viewRelativePath).normalize().join('/'), true)
			}
			case FILE_URIS:
				createFileURI(persistenceDirectory.resolve(viewRelativePath).normalize().toFile())
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
