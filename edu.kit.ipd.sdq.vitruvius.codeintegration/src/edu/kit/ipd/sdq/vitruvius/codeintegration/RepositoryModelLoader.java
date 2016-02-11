package edu.kit.ipd.sdq.vitruvius.codeintegration;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import org.palladiosimulator.pcm.repository.RepositoryPackage;

/**
 * Helper class for loading PCM Repositories from file system.
 *
 * @author Benjamin Hettwer
 */
public class RepositoryModelLoader {

    /**
     * Return the EObjects of the loaded PCM resource.
     *
     * @param path
     *            path to model
     * @return the EObjects contained in the PCM resource
     */
    public static EList<EObject> loadPCMModel(String path) {

        return loadPCMResource(path).getContents();

    }

    /**
     * Loads a PCM resource (*.repository file) from file system
     * 
     * @param path
     *            relative path to the corresponding resource file
     * @return the loaded resource , or null if there was none found
     */
    public static Resource loadPCMResource(String path) {
        return loadPCMResource(URI.createFileURI(new File(path).getAbsolutePath()));
    }

    /**
     * Loads PCM resource.
     *
     * @param fileURI
     *            the file uri
     * @return the resource, or null if there was none found
     */
    public static Resource loadPCMResource(URI fileURI) {
        // Load the required meta class packages => PCM Repository
        RepositoryPackage.eINSTANCE.eClass();

        // Obtain a new resource set
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register XML Factory implementation using DEFAULT_EXTENSION
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());

        // Demand load the resource for this file
        Resource resource = resourceSet.getResource(fileURI, true);

        return resource;
    }

}
