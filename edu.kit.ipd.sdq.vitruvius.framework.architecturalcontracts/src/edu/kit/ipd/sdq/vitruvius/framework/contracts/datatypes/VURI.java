package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFBridge;

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
            INSTANCES.put(key, instance);
        }
        return instance;
    }

    public static VURI getInstance(final Resource resource) {
        return getInstance(resource.getURI());
    }

    public static VURI getInstance(final URI uri) {
        if (null == uri.toFileString()) {
            return getInstance(uri.toString());
        }
        return getInstance(uri.toFileString());
    }

    public static VURI getInstance(final IResource iResource) {
        return getInstance(iResource.getFullPath().toOSString());
    }

    @Override
    public String toString() {
        return this.emfURI.toString();
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

    @Override
    public int compareTo(final VURI otherVURI) {
        return this.emfURI.toString().compareTo(otherVURI.toString());
    }
}
