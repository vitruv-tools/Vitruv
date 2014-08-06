package edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder;

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

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.correspmmprovider.CorrespondenceMMProviderImpl;
import edu.kit.ipd.sdq.vitruvius.framework.design.metamodelmanager.MetamodelManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.design.viewtype.manager.ViewTypeManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IEditorPartAdapterFactory;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IVitruviusEMFEditorMonitor;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IVitruviusEMFEditorMonitor.IVitruviusAccessor;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor.DefaultEditorPartAdapterFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.monitor.EMFEditorMonitorFactory;
import edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine.EMFModelPropagationEngineImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.synctransprovider.TransformationExecutingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class VitruviusEmfBuilder extends IncrementalProjectBuilder {

    private static final Logger LOGGER = Logger.getLogger(VitruviusEmfBuilder.class.getSimpleName());

    private Set<String> monitoredFileTypes;
    private IVitruviusEMFEditorMonitor monitor;
    private final VitruviusEMFDeltaVisitor vitruviusEMFDeltaVisitor;
    private ChangeSynchronizing changeSynchronizing;

    private final IResourceDeltaProviding resourceDeltaProviding;
    private final IProjectProviding projectProviding;
    private final EMFEditorMonitorFactory monitorFactory;

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
        final ModelProviding mp = this.createChangeSynchronizing(metaRepository);

        final IVitruviusAccessor vitruviusAcc = this.createVitruviusAccessor();
        final IEditorPartAdapterFactory epaFactory = new DefaultEditorPartAdapterFactoryImpl(monitoredFileTypes);
        this.monitor = this.monitorFactory.createVitruviusModelEditorSyncMgr(epaFactory, this.changeSynchronizing,
                null /* TODO */, vitruviusAcc);
        this.monitor.initialize();
        LOGGER.trace("Initialized the builder.");
    }

    private ModelProviding createChangeSynchronizing(final MetaRepositoryImpl metaRepositoryImpl) {
        final MetamodelManagerImpl metaModelManager = new MetamodelManagerImpl(metaRepositoryImpl);
        final ViewTypeManagerImpl viewTypeManager = new ViewTypeManagerImpl();
        final CorrespondenceMMProviderImpl correspondenceProvider = new CorrespondenceMMProviderImpl();
        final VSUMImpl vsum = new VSUMImpl(metaModelManager, viewTypeManager, metaRepositoryImpl,
                correspondenceProvider);
        // create syncTransformationProvider
        final TransformationExecutingProvidingImpl syncTransformationProvider = new TransformationExecutingProvidingImpl();
        final EMFModelPropagationEngineImpl propagatingChange = new EMFModelPropagationEngineImpl(syncTransformationProvider);

        final SyncManagerImpl smi = new SyncManagerImpl(vsum, propagatingChange, vsum, metaRepositoryImpl, vsum);
        // create syncManager
        this.changeSynchronizing = smi;
        return smi.getModelProviding();
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
                    LOGGER.debug("Resource changed: " + iResource);
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
        this.monitor.addModel(resUri);
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
        this.monitor.removeModel(resUri);
        this.triggerFileChangeSynchronisation(iResource, FileChangeKind.DELETE);
    }

    private void triggerFileChangeSynchronisation(final IResource iResource, final FileChangeKind fileChangeKind) {
        final String fileExtension = iResource.getFileExtension();
        if (this.monitoredFileTypes.contains(fileExtension)) {
            final VURI vuri = VURI.getInstance(iResource);
            final FileChange fileChange = new FileChange(fileChangeKind, vuri);
            this.changeSynchronizing.synchronizeChange(fileChange);
        }
    }

    private void triggerSynchronisation(final IResource iResource) {
        LOGGER.trace("Triggering synchronization for " + iResource);
        if (this.monitoredFileTypes.contains(iResource.getFileExtension())) {
            final VURI vuri = VURI.getInstance(iResource);
            this.monitor.triggerSynchronisation(vuri);
        }
    }

    public static interface IResourceDeltaProviding {
        public IResourceDelta getDelta(IProject project);
    }

    public static interface IProjectProviding {
        public IProject getProject();
    }
}
