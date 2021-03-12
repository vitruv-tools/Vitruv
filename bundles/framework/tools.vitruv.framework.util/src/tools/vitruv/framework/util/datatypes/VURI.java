package tools.vitruv.framework.util.datatypes;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.framework.util.bridges.EMFBridge;
import static com.google.common.base.Preconditions.checkState;

/**
 * Implements the multiton design pattern.
 *
 * @author kramerm
 *
 */
public class VURI implements Comparable<VURI> {
	private static final Map<String, VURI> INSTANCES = new HashMap<String, VURI>();

	private URI emfURI;

	/** Multiton classes should not have a public or default constructor. */
	private VURI(final String uriString, final URI uri) {
		this.emfURI = uri;
	}

	private static synchronized VURI putInstance(final String key, final URI uri) {
		checkState(!INSTANCES.containsKey(key), "already contains %s", key);
		VURI newInstance = new VURI(key, uri);
		VURI existingInstance = INSTANCES.get(newInstance.toString());
		if (existingInstance != null) {
			checkState(existingInstance.emfURI.equals(uri), "%s was already mapped to URI '%s' while adding '%s'", key,
					existingInstance.emfURI, uri);
			INSTANCES.put(key, existingInstance);
			return existingInstance;
		} else {
			INSTANCES.put(key, newInstance);
			// We also have to map the actual string representation of the key after the
			// VURI was created because a prefix may be prepended to the key while the VURI
			// is created
			INSTANCES.put(newInstance.toString(), newInstance);
			return newInstance;
		}
	}

	public static synchronized VURI getInstance(final String key) {
		VURI instance = INSTANCES.get(key);
		if (instance == null) {
			instance = putInstance(key, EMFBridge.createURI(key));
		}
		return instance;
	}

	public static VURI getInstance(final Resource resource) {
		return getInstance(resource.getURI());
	}

	public static VURI getInstance(final URI uri) {
		checkNotNull(uri, "URI for VURI");
		if (null == INSTANCES.get(uri.toString())) {
			putInstance(uri.toString(), uri);
		}
		return getInstance(uri.toString());
	}

	public static VURI getInstance(final IResource iResource) {
		String[] keyStrSegments = iResource.getFullPath().segments();
		StringBuilder keyString = new StringBuilder();
		for (int i = 0; i < keyStrSegments.length; i++) {
			if (i > 0) {
				keyString.append("/");
			}
			keyString.append(keyStrSegments[i]);
		}

		return getInstance(keyString.toString());
	}

	public static VURI getInstance(EObject object) {
		return getInstance(getResolvableUri(object));
	}

	private static URI getResolvableUri(EObject object) {
		// we cannot simply use EcoreUtil#getURI, because object’s domain might use XMI
		// UUIDs. Since XMI UUIDs can be different for different resource sets, we
		// cannot use URIs with XMI UUIDs to identify across resource sets. Hence, we
		// force hierarchical URIs. This assumes that the resolved object’s graph has
		// the same topology in the resolving resource set. This assumption holds when
		// we use this method.
		Resource resource = object.eResource();
		if (resource == null) {
			return URI.createURI(EcoreUtil.getRelativeURIFragmentPath(null, object));
		} else {
			int rootElementIndex = 0;
			EObject resourceRoot;
			if (resource.getContents().size() <= 1) {
				resourceRoot = object.eResource().getContents().get(0);
			} else {
				// move up containment hierarchy until some container is one of the resource's
				// root elements
				EObject container = object;
				while (container != null && (rootElementIndex = resource.getContents().indexOf(container)) == -1) {
					container = container.eContainer();
				}
				checkState(container != null, "some container of %s must be a root element of its resource", object);
				resourceRoot = container;
			}
			String fragmentPath = EcoreUtil.getRelativeURIFragmentPath(resourceRoot, object);
			if (fragmentPath == "") {
				return resource.getURI().appendFragment("/" + rootElementIndex);
			} else {
				return resource.getURI().appendFragment("/" + rootElementIndex + "/" + fragmentPath);
			}
		}
	}

	@Override
	public String toString() {
		return this.emfURI.toString();
	}

	public String toResolvedAbsolutePath() {
		return CommonPlugin.resolve(this.emfURI).toFileString();
	}

	public URI getEMFUri() {
		return this.emfURI;
	}

	public String getFileExtension() {
		return this.emfURI.fileExtension();
	}

	public String getLastSegment() {
		String lastSegment = this.emfURI.lastSegment();
		return (lastSegment == null ? "" : lastSegment);
	}

	/**
	 * Returns a new VURI that is created from the actual VURI by replacing its file
	 * extension with newFileExt
	 *
	 * @param newFileExt
	 * @return the new VURI with the replaced file extension
	 */
	public VURI replaceFileExtension(final String newFileExt) {
		return VURI.getInstance(this.emfURI.trimFileExtension().appendFileExtension(newFileExt));
	}

	@Override
	public int compareTo(final VURI otherVURI) {
		return this.emfURI.toString().compareTo(otherVURI.toString());
	}
}
