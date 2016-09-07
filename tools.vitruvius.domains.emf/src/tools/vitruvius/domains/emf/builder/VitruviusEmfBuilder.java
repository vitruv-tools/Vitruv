package tools.vitruvius.domains.emf.builder;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruvius.domains.emf.monitorededitor.IEditorPartAdapterFactory;
import tools.vitruvius.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor;
import tools.vitruvius.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor.IVitruviusAccessor;
import tools.vitruvius.domains.emf.monitorededitor.monitor.DefaultEditorPartAdapterFactoryImpl;
import tools.vitruvius.domains.emf.monitorededitor.monitor.EMFEditorMonitorFactory;
import tools.vitruvius.framework.change.description.FileChange;
import tools.vitruvius.framework.change.description.VitruviusChangeFactory;
import tools.vitruvius.framework.change.description.FileChange.FileChangeKind;
import tools.vitruvius.framework.change.processing.Change2CommandTransformingProviding;
import tools.vitruvius.framework.change.processing.impl.Change2CommandTransformingProvidingImpl;
import tools.vitruvius.framework.metamodel.ModelProviding;
import tools.vitruvius.framework.metarepository.MetaRepositoryImpl;
import tools.vitruvius.framework.modelsynchronization.ChangeSynchronizerImpl;
import tools.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import tools.vitruvius.framework.modelsynchronization.SynchronisationListener;
import tools.vitruvius.framework.util.bridges.EMFBridge;
import tools.vitruvius.framework.util.datatypes.VURI;
import tools.vitruvius.framework.vsum.VSUMImpl;

public abstract class VitruviusEmfBuilder extends IncrementalProjectBuilder implements SynchronisationListener {

    private static final Logger LOGGER = Logger.getLogger(VitruviusEmfBuilder.class.getSimpleName());

    private Set<String> monitoredFileTypes;
    private final VitruviusEMFDeltaVisitor vitruviusEMFDeltaVisitor;

    private final IResourceDeltaProviding resourceDeltaProviding;
    private final IProjectProviding projectProviding;

    protected final EMFEditorMonitorFactory monitorFactory;
    protected IVitruviusEMFEditorMonitor emfMonitor;
    protected ChangeSynchronizing changeSynchronizing;
    protected ModelProviding modelProviding;
    protected VSUMImpl vsum;
    protected Change2CommandTransformingProviding transformingProviding;
    
    private final boolean isInTestingMode = true;

    protected VitruviusEmfBuilder() {
        this(null, null, null);
    }

    protected VitruviusEmfBuilder(final IResourceDeltaProviding resourceDeltaProviding,
            final IProjectProviding projectProviding, final EMFEditorMonitorFactory monitorFactory) {
        LOGGER.setLevel(Level.ALL); // TODO
        Logger.getRootLogger().setLevel(Level.ALL);
        LOGGER.trace("Created a VitruviusEmfBuilder.");
        this.vitruviusEMFDeltaVisitor = new VitruviusEMFDeltaVisitor();

        if (resourceDeltaProviding == null) {
            this.resourceDeltaProviding = new IResourceDeltaProviding() {
                @Override
                public IResourceDelta getDelta(final IProject project) {
                    return VitruviusEmfBuilder.this.getDelta(project);
                }
            };
        } else {
            this.resourceDeltaProviding = resourceDeltaProviding;
        }

        if (projectProviding == null) {
            this.projectProviding = new IProjectProviding() {
                @Override
                public IProject getProject() {
                    return VitruviusEmfBuilder.this.getProject();
                }
            };
        } else {
            this.projectProviding = projectProviding;
        }

        if (monitorFactory == null) {
            this.monitorFactory = new EMFEditorMonitorFactory();
        } else {
            this.monitorFactory = monitorFactory;
        }
    }

    protected void initializeBuilder(final Set<String> monitoredFileTypes, final MetaRepositoryImpl metaRepository) {
        this.monitoredFileTypes = monitoredFileTypes;
        this.modelProviding = this.createChangeSynchronizing(metaRepository);

        this.createAndStartEMFMonitor();
        this.emfMonitor.initialize();
        LOGGER.trace("Initialized the builder.");
    }

    protected void createAndStartEMFMonitor() {
        final IVitruviusAccessor vitruviusAcc = this.createVitruviusAccessor();
        final IEditorPartAdapterFactory epaFactory = new DefaultEditorPartAdapterFactoryImpl(this.monitoredFileTypes);
        this.emfMonitor = this.monitorFactory.createVitruviusModelEditorSyncMgr(epaFactory, this.changeSynchronizing,
                null /* TODO */, vitruviusAcc);
    }

    private ModelProviding createChangeSynchronizing(final MetaRepositoryImpl metaRepositoryImpl) {
        this.vsum = new VSUMImpl(metaRepositoryImpl, metaRepositoryImpl);
        this.transformingProviding = new Change2CommandTransformingProvidingImpl();
        final ChangeSynchronizerImpl changeSynchronizerImpl = new ChangeSynchronizerImpl(this.vsum,
        		this.transformingProviding, this.vsum, this);
        this.changeSynchronizing = changeSynchronizerImpl;
        return this.vsum;
    }

    private IVitruviusAccessor createVitruviusAccessor() {
        return new IVitruviusAccessor() {
            @Override
            public boolean isModelMonitored(final VURI modelUri) {
                boolean doMonitor = VitruviusEmfBuilder.this.monitoredFileTypes.contains(modelUri.getFileExtension());
                doMonitor &= VitruviusEmfBuilder.this.isFileBelongingToThisProject(modelUri);
                LOGGER.trace("Monitor " + modelUri + "? " + doMonitor);
                return doMonitor;
            }
        };
    }

    private boolean isFileBelongingToThisProject(final VURI fileVURI) {
        if (this.isInTestingMode == false) {
            if (this.getBuildConfig() == null) {
                LOGGER.warn("Unable to determine whether " + fileVURI
                        + " should be monitored with this builder: No build configuration.");
                return true;
            }
            final IFile referencedFile = EMFBridge.getIFileForEMFUri(fileVURI.getEMFUri());
            return referencedFile.getProject() == this.projectProviding.getProject();
        } else {
            return true;
        }
    }

    class VitruviusEMFDeltaVisitor implements IResourceDeltaVisitor {
        /*
         * (non-Javadoc)
         *
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.
         * IResourceDelta)
         */
        @Override
        public boolean visit(final IResourceDelta delta) throws CoreException {
            final IResource iResource = delta.getResource();
            final String fileExtension = iResource.getFileExtension();
            final boolean isMonitoredResource = VitruviusEmfBuilder.this.monitoredFileTypes.contains(fileExtension);
            if (isMonitoredResource) {
                switch (delta.getKind()) {
                case IResourceDelta.ADDED:
                    LOGGER.debug("Importing to Vitruvius: " + iResource);
                    VitruviusEmfBuilder.this.importToVitruvius(iResource);
                    break;
                case IResourceDelta.REMOVED:
                    LOGGER.debug("Removing from Vitruvius: " + iResource);
                    VitruviusEmfBuilder.this.removeFromVitruvius(iResource);
                    break;
                case IResourceDelta.CHANGED:
                    // LOGGER.debug("Resource changed: " + iResource);
                    VitruviusEmfBuilder.this.triggerSynchronisation(iResource);
                    break;
                default:
                    LOGGER.debug("No action for change kind: '" + delta.getKind() + "' executed.");
                }
            }
            // return true to continue visiting children.
            return true;
        }
    }

    class VitruviusEMFResourceVisitor implements IResourceVisitor {
        @Override
        public boolean visit(final IResource resource) {
            // return true to continue visiting children.
            return true;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.core.internal.events.InternalBuilder#build(int, java.util.Map,
     * org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
        if (kind == FULL_BUILD) {
            this.fullBuild(monitor);
        } else {
            final IResourceDelta delta = this.resourceDeltaProviding.getDelta(this.projectProviding.getProject());
            if (delta == null) {
                this.fullBuild(monitor);
            } else {
                this.incrementalBuild(delta, monitor);
            }
        }
        return null;
    }

    protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
        try {
            this.projectProviding.getProject().accept(new VitruviusEMFResourceVisitor());
        } catch (final CoreException e) {
        }
    }

    protected void incrementalBuild(final IResourceDelta delta, final IProgressMonitor monitor) throws CoreException {
        // the visitor does the work.
        delta.accept(this.vitruviusEMFDeltaVisitor);
    }

    /**
     * @importToVitruvius: Imports new file to Vitruvius by: 1) adding adapter to root object of
     *                     instance, hence the MonitoredEmfEditor is called when file changes 2)
     *                     calling get with the new URI the resource is added to VSUM.
     * @param iResource
     *            new resource
     */
    private void importToVitruvius(final IResource iResource) {
        LOGGER.trace("Importing " + iResource);
        final VURI resUri = VURI.getInstance(iResource);
        this.emfMonitor.addModel(resUri);
        this.triggerFileChangeSynchronisation(iResource, FileChangeKind.CREATE);
    }

    /**
     * Removes file from Vitruvius control by deleting its root element
     *
     * @param iResource
     *            resource to remove
     */
    private void removeFromVitruvius(final IResource iResource) {
        LOGGER.trace("Removing " + iResource);
        final VURI resUri = VURI.getInstance(iResource);
        this.emfMonitor.removeModel(resUri);
        this.triggerFileChangeSynchronisation(iResource, FileChangeKind.DELETE);
    }

    private void triggerFileChangeSynchronisation(final IResource iResource, final FileChangeKind fileChangeKind) {
        final String fileExtension = iResource.getFileExtension();
        if (this.monitoredFileTypes.contains(fileExtension)) {
            final VURI vuri = VURI.getInstance(iResource);
            Resource modelResource = this.vsum.getAndLoadModelInstanceOriginal(vuri).getResource();
            final FileChange fileChange = VitruviusChangeFactory.getInstance().createFileChange(fileChangeKind, modelResource);
            this.changeSynchronizing.synchronizeChange(fileChange);
        }
    }

    private void triggerSynchronisation(final IResource iResource) {
        LOGGER.trace("Triggering synchronization for " + iResource);
        if (this.monitoredFileTypes.contains(iResource.getFileExtension())) {
            final VURI vuri = VURI.getInstance(iResource);
            this.emfMonitor.triggerSynchronisation(vuri);
        }
    }

    public static interface IResourceDeltaProviding {
        public IResourceDelta getDelta(IProject project);
    }

    public static interface IProjectProviding {
        public IProject getProject();
    }
}
