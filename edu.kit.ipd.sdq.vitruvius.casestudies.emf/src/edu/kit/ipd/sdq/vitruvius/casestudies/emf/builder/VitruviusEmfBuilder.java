package edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
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
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf.MonitoredEmfEditorImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;

public class VitruviusEmfBuilder extends IncrementalProjectBuilder {

    private static final Logger logger = Logger.getLogger(VitruviusEmfBuilder.class.getSimpleName());

    private Set<String> monitoredFileTypes;
    private MonitoredEmfEditorImpl monitor;
    private final VitruviusEMFDeltaVisitor vitruviusEMFDeltaVisitor;

    protected VitruviusEmfBuilder() {
        this.vitruviusEMFDeltaVisitor = new VitruviusEMFDeltaVisitor();
    }

    protected void initializeBuilder(final Set<String> monitoredFileTypes, final MetaRepositoryImpl metarepository) {
        this.monitoredFileTypes = monitoredFileTypes;
        SyncManagerImpl.setMetaRepositoryImpl(metarepository);
        final SyncManagerImpl syncManager = SyncManagerImpl.getSyncManagerInstance();
        this.monitor = new MonitoredEmfEditorImpl(syncManager, syncManager.getModelProviding());
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
                    VitruviusEmfBuilder.this.importToVitruvius(iResource);
                    break;
                case IResourceDelta.REMOVED:
                    VitruviusEmfBuilder.this.removeFromVitruvius(iResource);
                    break;
                case IResourceDelta.CHANGED:
                    VitruviusEmfBuilder.this.triggerSynchronisation(iResource);
                    break;
                default:
                    logger.debug("No action for change kind: '" + delta.getKind() + "' executed.");
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
            final IResourceDelta delta = this.getDelta(this.getProject());
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
            this.getProject().accept(new VitruviusEMFResourceVisitor());
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
        this.triggerFileChangeSynchronisation(iResource, FileChangeKind.CREATE);
    }

    /**
     * Removes file from Vitruvius control by deleting its root element
     * 
     * @param iResource
     *            resource to remove
     */
    private void removeFromVitruvius(final IResource iResource) {
        this.triggerFileChangeSynchronisation(iResource, FileChangeKind.DELETE);
    }

    private void triggerFileChangeSynchronisation(final IResource iResource, final FileChangeKind fileChangeKind) {
        final String fileExtension = iResource.getFileExtension();
        if (this.monitoredFileTypes.contains(fileExtension)) {
            final FileChange fileChange = new FileChange(fileChangeKind);
            final VURI vuri = VURI.getInstance(iResource);
            SyncManagerImpl.getSyncManagerInstance().synchronizeChange(fileChange, vuri);
        }
    }

    private void triggerSynchronisation(final IResource iResource) {
        if (this.monitoredFileTypes.contains(iResource.getFileExtension())) {
            final VURI vuri = VURI.getInstance(iResource);
            this.monitor.triggerSynchronisation(vuri);
        }
    }
}
