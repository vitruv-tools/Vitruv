package edu.kit.ipd.sdq.vitruvius.integration.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.somox.sourcecodedecorator.SourcecodedecoratorPackage;
import org.splevo.jamopp.extraction.JaMoPPSoftwareModelExtractor;

/**
 * Helper class for loading {@link Resource}s.
 * 
 * @author Benjamin Hettwer
 *
 */
public class ResourceHelper {

    /**
     * Loads a set of JaMoPP resources from the defined java project.
     *
     * @param path
     *            src path of the java project
     * @return set of resources containing the extracted JaMoPP resources.
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static ResourceSet loadJaMoPPResourceSet(String path) throws IOException {

        JaMoPPSoftwareModelExtractor jamoppreader = new JaMoPPSoftwareModelExtractor();
        List<String> list = new ArrayList<String>();
        list.add(path);

        return jamoppreader.extractSoftwareModel(list, null);
    }

    /**
     * Loads the specified (.sourcecodedecorator) file.
     * 
     * @param path
     *            path to file
     * @return the resolved scdm resource or null if none was found
     */
    public static Resource loadSCDMResource(String path) {
        return loadSCDMResource(URI.createFileURI(new File(path).getAbsolutePath()));
    }

    /**
     * Loads the specified (.sourcecodedecorator) file.
     * 
     * @param fileURI
     *            path to file
     * @return the resolved scdm resource or null if none was found
     */
    public static Resource loadSCDMResource(URI fileURI) {
        // Load the required meta class packages => SCDM-Package
        SourcecodedecoratorPackage.eINSTANCE.eClass();

        // Obtain a new resource set
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register XML Factory implementation using DEFAULT_EXTENSION
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());

        // Demand load the resource for this file
        Resource resource = resourceSet.getResource(fileURI, true);

        return resource;
    }

    /**
     * load the specified pcm (repository) file as model (resource).
     *
     * @param path
     *            to file
     * @return resource file
     */
    public static Resource loadPCMRepositoryResource(String path) {
        return RepositoryModelLoader.loadPCMResource(path);
    }

    /**
     * converts an absolute EMF Resource to a workspace relative Eclipse IResource.
     *
     * @param emfResource
     *            : C:\Workspace\Project\Model\model.abc
     * @return : L/Project/Model/model.abc
     */
    public static IResource absoluteEmfResourceToWorkspaceRelativeIResource(Resource emfResource) {
        URI uri = emfResource.getURI();

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();

        String fileString = uri.toFileString();
        String rootString = root.getLocation().toString();
        String relativeString = fileString.substring(rootString.length());

        IResource iResource = root.getFile(new Path(relativeString));

        return iResource;
    }

    /**
     * Creates an platform URI for a resource
     * 
     * @param resource
     *            : the resource
     * @return the model uri
     */
    public static URI createPlatformUriForResource(final Resource resource) {
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IPath workspaceDir = workspace.getRoot().getLocation();
        String workspaceString = workspaceDir.toString();
        workspaceString = workspaceString.replace("/", File.separator);

        final String platRelativeModelLoc = resource.getURI().toFileString().replace(workspaceString, "");
        final URI uri = URI.createPlatformResourceURI(platRelativeModelLoc, true);
        return uri;
    }

}
