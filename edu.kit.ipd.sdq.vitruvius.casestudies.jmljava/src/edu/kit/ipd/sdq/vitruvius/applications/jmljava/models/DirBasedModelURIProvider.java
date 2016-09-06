package edu.kit.ipd.sdq.vitruvius.applications.jmljava.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.extensions.SourceDirProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.utils.EclipseUtilities;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

/**
 * Base class for model URI providers, which use folders of Eclipse projects and file extensions to
 * find models. The folders are received from the {@link SourceDirProvider}s from the corresponding
 * extension point.
 */
public abstract class DirBasedModelURIProvider implements ModelURIProvider {

    /**
     * Visitor for Eclipse project resource folders, which notes all files with a given extension.
     */
    private static class FileFinder implements IResourceVisitor {

        private final List<IResource> matchedFiles = new ArrayList<IResource>();
        private final List<String> relevantExtensions;

        /**
         * Constructs the visitor.
         * 
         * @param relevantExtensions
         *            The set of relevant extensions.
         */
        public FileFinder(List<String> relevantExtensions) {
            this.relevantExtensions = relevantExtensions;
        }

        /**
         * @return All files with the extensions given in the constructor.
         */
        public Iterable<IResource> getMatchedFiles() {
            return matchedFiles;
        }

        @Override
        public boolean visit(IResource resource) throws CoreException {
            if (resource.getType() == IResource.FOLDER) {
                return true;
            }
            if (relevantExtensions.contains(resource.getFileExtension())) {
                matchedFiles.add(resource);
            }
            return false;
        }

    }

    private static final Logger LOGGER = Logger.getLogger(DirBasedModelURIProvider.class);
    protected final List<String> relevantExtensions;
    protected final Iterable<SourceDirProvider> relevantProviders;

    /**
     * Constructs the model URI provider.
     * 
     * @param relevantExtensions
     *            The extensions, which are used to identify models.
     */
    protected DirBasedModelURIProvider(String[] relevantExtensions) {
        this.relevantExtensions = Arrays.asList(relevantExtensions);
        this.relevantProviders = getRelevantProviders(this.relevantExtensions);
    }

    /**
     * @return The source directories, which might contain relevant models.
     */
    public Iterable<IResource> getRelevantSourceDirs() {
        List<IResource> sourceDirs = new ArrayList<IResource>();

        for (SourceDirProvider provider : relevantProviders) {
            sourceDirs.addAll(Arrays.asList(provider.getSourceDirs()));
        }

        return sourceDirs;
    }

    /**
     * Determines the {@link SourceDirProvider}s, which are relevant for finding models. A provider
     * is relevant if it provides directories for the wanted extensions.
     * 
     * @param relevantExtensions
     *            The extensions for filtering the providers.
     * @return The set of relevant providers.
     */
    protected Iterable<SourceDirProvider> getRelevantProviders(Iterable<String> relevantExtensions) {
        List<SourceDirProvider> providers = new ArrayList<SourceDirProvider>();
        for (SourceDirProvider provider : EclipseUtilities.getRegisteredExtensions(SourceDirProvider.class)) {
            if (containsWantedExtension(provider, relevantExtensions)) {
                providers.add(provider);
            }
        }
        return providers;
    }

    /**
     * Determines if the given provider is relevant in the context of this class based on the file
     * extensions it can provide.
     * 
     * @param provider
     *            The provider to check.
     * @param relevantExtensions
     *            The extensions for which the provider has to provide a directory. A match for at
     *            least one extension is required.
     * @return True if the provider is relevant.
     */
    protected boolean containsWantedExtension(SourceDirProvider provider, Iterable<String> relevantExtensions) {
        List<String> containerAsList = Arrays.asList(provider.getContainedExtensions());
        for (String wantedExtension : relevantExtensions) {
            if (containerAsList.contains(wantedExtension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<VURI> getAllRelevantURIs() {
        LOGGER.debug("Looking for relevant model URIs with extensions " + StringUtils.join(relevantExtensions, ", "));

        FileFinder finder = new FileFinder(relevantExtensions);
        for (SourceDirProvider provider : relevantProviders) {
            for (IResource sourceDir : provider.getSourceDirs()) {
                LOGGER.trace("Checking directory " + sourceDir.getFullPath());
                try {
                    sourceDir.accept(finder);
                } catch (CoreException e) {
                    LOGGER.warn("The source directory " + sourceDir.getFullPath()
                            + " could not be processed. Skipping this directory.", e);
                }
            }
        }

        List<VURI> modelURIs = new ArrayList<VURI>();
        for (IResource resource : finder.getMatchedFiles()) {
            modelURIs.add(VURI.getInstance(resource));
        }

        LOGGER.debug("Found " + modelURIs.size() + " model URIs.");

        return modelURIs;
    }

}
