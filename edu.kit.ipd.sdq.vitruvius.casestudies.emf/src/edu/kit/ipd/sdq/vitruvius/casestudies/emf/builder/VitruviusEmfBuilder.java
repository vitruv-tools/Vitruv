package edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder;

import java.util.List;
import java.util.Map;

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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf.MonitoredEmfEditorImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;

public class VitruviusEmfBuilder extends IncrementalProjectBuilder {

    private static final Logger logger = Logger.getLogger(VitruviusEmfBuilder.class.getSimpleName());

    private List<String> monitoredFileTypes;
    private final MonitoredEmfEditorImpl monitor;

    public static final String VITRUVIUS_EMF_BUILDER_ID = "edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder.id";

    public VitruviusEmfBuilder() {
        final SyncManagerImpl syncManager = SyncManagerImpl.getSyncManagerInstance();
        this.monitor = new MonitoredEmfEditorImpl(syncManager, syncManager.getModelProviding());
    }

    public VitruviusEmfBuilder(final List<String> monitoredFileTypes) {
        this();
        this.monitoredFileTypes = monitoredFileTypes;
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
            if (this.isMonitoredResource(iResource)) {
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
                }
            }
            // return true to continue visiting children.
            return true;
        }

        private boolean isMonitoredResource(final IResource resource) {
            // check if it is relevant resource
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
        delta.accept(new VitruviusEMFDeltaVisitor());
    }

    /**
     * @importToVitruvius: Imports new file to Vitruvius by: 1) adding adapter to root object of
     *                     instance, hence the MonitoredEmfEditor is called when file changes 2)
     *                     calling get with the new URI the resource is added to VSUM.
     * @param iResource
     *            new resource
     */
    private void importToVitruvius(final IResource iResource) {
        if (iResource.getName().endsWith(".java") || iResource.getName().endsWith(".repository")) {
            final FileChange fileChange = new FileChange();
            final VURI vuri = VURI.getInstance(iResource.getFullPath().toString());
            SyncManagerImpl.getSyncManagerInstance().synchronizeChange(fileChange, vuri);
        }
    }

    /**
     * Removes file from Vitruvius control by deleting its root element
     * 
     * @param iResource
     *            resource to remove
     */
    private void removeFromVitruvius(final IResource iResource) {
        if (iResource.getName().endsWith(".java") || iResource.getName().endsWith(".repository")) {

        }
    }

    private void triggerSynchronisation(final IResource iResource) {
        final VURI vuri = VURI.getInstance(EMFBridge.getEMFPlatformUriForIResource(iResource).toString());
        this.monitor.triggerSynchronisation(vuri);

    }
}
