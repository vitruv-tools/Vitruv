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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf.MonitoredEmfEditorImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;

public class VitruviusEmfBuilder extends IncrementalProjectBuilder {

    private static final Logger logger = Logger.getLogger(VitruviusEmfBuilder.class.getSimpleName());

    private List<String> monitoredFileTypes;
    private ResourceSet resourceSet;
    private MonitoredEmfEditorImpl monitor;

    public static final String VITRUVIUS_EMF_BUILDER_ID = "edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder.id";

    public VitruviusEmfBuilder() {
        this.resourceSet = new ResourceSetImpl();
        SyncManagerImpl syncManager = SyncManagerImpl.getSyncManagerInstance();
        this.monitor = new MonitoredEmfEditorImpl(syncManager, syncManager.getModelProviding());
    }

    public VitruviusEmfBuilder(List<String> monitoredFileTypes) {
        this();
        this.monitoredFileTypes = monitoredFileTypes;
        this.resourceSet = new ResourceSetImpl();
    }

    class VitruviusEMFDeltaVisitor implements IResourceDeltaVisitor {
        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.
         * IResourceDelta)
         */
        public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();
            if (isMonitoredResource(resource)) {
                switch (delta.getKind()) {
                case IResourceDelta.ADDED:
                    importToVitruvius(resource);
                    break;
                case IResourceDelta.REMOVED:
                    removeFromVitruvius(resource);
                    break;
                case IResourceDelta.CHANGED:
                    /**
                     * We do not need to track changes here. Changes are tracked via adapters on the
                     * EMF models.
                     */
                    break;
                }
            }
            // return true to continue visiting children.
            return true;
        }

        private boolean isMonitoredResource(IResource resource) {
            // check if it is relevant resource
            return true;
        }
    }

    class VitruviusEMFResourceVisitor implements IResourceVisitor {
        public boolean visit(IResource resource) {
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
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        if (kind == FULL_BUILD) {
            fullBuild(monitor);
        } else {
            IResourceDelta delta = getDelta(getProject());
            if (delta == null) {
                fullBuild(monitor);
            } else {
                incrementalBuild(delta, monitor);
            }
        }
        return null;
    }

    protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
        try {
            getProject().accept(new VitruviusEMFResourceVisitor());
        } catch (CoreException e) {
        }
    }

    protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
        // the visitor does the work.
        delta.accept(new VitruviusEMFDeltaVisitor());
    }

    /**
     * @importToVitruvius: Imports new file to Vitruvius by: 1) adding adapter to root object of
     *                     instance, hence the MonitoredEmfEditor is called when file changes 2)
     *                     calling get with the new URI the resource is added to VSUM.
     * @param resource
     *            new resource
     */
    private void importToVitruvius(IResource resource) {
        if (resource.getName().endsWith(".java") || resource.getName().endsWith(".repository")) {
            SyncManagerImpl syncManager = SyncManagerImpl.getSyncManagerInstance();
            VURI vuri = new VURI(resource);
            ModelInstance model = syncManager.getModelProviding().getModelInstanceOriginal(vuri);
            EObject rootObject = EcoreResourceBridge.getResourceContentRootFromVURIIfUnique(vuri, resourceSet);
            if (null == rootObject) {
                logger.error("Could not get EObject from resource: '" + resource.getFullPath().toOSString()
                        + "' . Resource is not included to Vitruvius.");
                return;
            }
            rootObject.eAdapters().add(monitor.getContentAdapter());
        }
    }

    /**
     * Removes file from Vitruvius control by deleting its root element
     * 
     * @param resource
     *            resource to remove
     */
    private void removeFromVitruvius(IResource resource) {
        SyncManagerImpl syncManager = SyncManagerImpl.getSyncManagerInstance();
        VURI vuri = new VURI(resource);
        ModelInstance model = syncManager.getModelProviding().getModelInstanceOriginal(vuri);
        EObject rootObject = EcoreResourceBridge.getResourceContentRootFromVURIIfUnique(model.getVURI(), resourceSet);
        // TODO: create change that contains remove of rootObject
        // Change change = new Change(Change.KIND.REMOVE, rootObject);
        syncManager.synchronizeChange(null, model);
    }

}
