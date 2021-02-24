package tools.vitruv.framework.util.datatypes;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.util.bridges.EMFBridge;

/**
 * Implements the multiton design pattern.
 *
 * @author kramerm
 *
 */
public class VURI implements Comparable<VURI> {
    private static final Map<String, VURI> INSTANCES = new HashMap<String, VURI>();

    private org.eclipse.emf.common.util.URI emfURI;

    /** Multiton classes should not have a public or default constructor. */
    private VURI(final String uriString) {
        this.emfURI = EMFBridge.createURI(uriString);
    }

    public static synchronized VURI getInstance(final String key) {
        VURI instance = INSTANCES.get(key);
        if (instance == null) {
            instance = new VURI(key);
            String newKey = instance.toString();
            VURI oldInstance = INSTANCES.get(newKey);
            if (oldInstance != null) {
                INSTANCES.put(key, oldInstance);
                return oldInstance;
            } else {
                // we also have to map the actual string representation of the key after the vuri
                // was
                // created because a prefix may be prepended to the key while the VURI is created
                INSTANCES.put(newKey, instance);
            }
            INSTANCES.put(key, instance);
        }
        return instance;
    }

    public static VURI getInstance(final Resource resource) {
        return getInstance(resource.getURI());
    }

    public static VURI getInstance(final URI uri) {
        checkNotNull(uri, "URI for VURI");
        if (null == uri.toFileString()) {
            return getInstance(uri.toString());
        }
        return getInstance(uri.toFileString());
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
     * Returns a new VURI that is created from the actual VURI by replacing its file extension with
     * newFileExt
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
