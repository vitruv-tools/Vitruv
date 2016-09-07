package tools.vitruv.applications.pcmjava.reconstructionintegration.transformations.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.somox.sourcecodedecorator.SourcecodedecoratorPackage;
import org.splevo.jamopp.extraction.JaMoPPSoftwareModelExtractor;

import tools.vitruv.domains.pcm.util.RepositoryModelLoader;

/**
 * Helper class for loading {@link Resource}s.
 *
 * @author Benjamin Hettwer
 *
 */
public class JaMoPPResourceHelper {

    /**
     * Loads a set of JaMoPP resources from the defined java project.
     *
     * @param path
     *            src path of the java project
     * @return set of resources containing the extracted JaMoPP resources.
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static ResourceSet loadJaMoPPResourceSet(final String path) throws IOException {

        final JaMoPPSoftwareModelExtractor jamoppreader = new JaMoPPSoftwareModelExtractor();
        final List<File> list = new ArrayList<File>();
        list.add(new File(path));
        return jamoppreader.extractSoftwareModelFromFolders(list, null, null, false);
    }

    /**
     * Loads the specified (.sourcecodedecorator) file.
     *
     * @param path
     *            path to file
     * @return the resolved scdm resource or null if none was found
     */
    public static Resource loadSCDMResource(final String path) {
        return loadSCDMResource(URI.createFileURI(new File(path).getAbsolutePath()));
    }

    /**
     * Loads the specified (.sourcecodedecorator) file.
     *
     * @param fileURI
     *            path to file
     * @return the resolved scdm resource or null if none was found
     */
    public static Resource loadSCDMResource(final URI fileURI) {
        // Load the required meta class packages => SCDM-Package
        SourcecodedecoratorPackage.eINSTANCE.eClass();

        // Obtain a new resource set
        final ResourceSet resourceSet = new ResourceSetImpl();

        // Register XML Factory implementation using DEFAULT_EXTENSION
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());

        // Demand load the resource for this file
        final Resource resource = resourceSet.getResource(fileURI, true);

        return resource;
    }

    /**
     * load the specified pcm (repository) file as model (resource).
     *
     * @param path
     *            to file
     * @return resource file
     */
    public static Resource loadPCMRepositoryResource(final String path) {
        return RepositoryModelLoader.loadPCMResource(path);
    }

}
