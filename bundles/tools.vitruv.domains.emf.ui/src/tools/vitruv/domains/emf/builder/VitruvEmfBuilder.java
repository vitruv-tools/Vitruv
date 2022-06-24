package tools.vitruv.domains.emf.builder;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createPlatformResourceURI;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.getIFileForEMFUri;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory;
import tools.vitruv.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor;
import tools.vitruv.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor.IVitruviusAccessor;
import tools.vitruv.domains.emf.monitorededitor.monitor.DefaultEditorPartAdapterFactoryImpl;
import tools.vitruv.domains.emf.monitorededitor.monitor.EMFEditorMonitorFactory;
import tools.vitruv.framework.domains.ui.builder.VitruvProjectBuilder;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewTypeFactory;

public class VitruvEmfBuilder extends VitruvProjectBuilder {
	public static final String BUILDER_ID = "tools.vitruv.domains.emf.builder.VitruvEmfBuilder.id";
    private static final Logger LOGGER = Logger.getLogger(VitruvEmfBuilder.class);

    private final VitruviusEMFDeltaVisitor vitruviusEMFDeltaVisitor;

    private final IResourceDeltaProviding resourceDeltaProviding;
    private final IProjectProviding projectProviding;

    protected final EMFEditorMonitorFactory monitorFactory;
    protected IVitruviusEMFEditorMonitor emfMonitor;
    
    private final boolean isInTestingMode = true;

    public VitruvEmfBuilder() {
        this(null, null, null);
    }
    
    public VitruvEmfBuilder(final IResourceDeltaProviding resourceDeltaProviding,
            final IProjectProviding projectProviding, final EMFEditorMonitorFactory monitorFactory) {
        LOGGER.trace("Created a VitruviusEmfBuilder.");
        this.vitruviusEMFDeltaVisitor = new VitruviusEMFDeltaVisitor();

        if (resourceDeltaProviding == null) {
            this.resourceDeltaProviding = new IResourceDeltaProviding() {
                @Override
                public IResourceDelta getDelta(final IProject project) {
                    return VitruvEmfBuilder.this.getDelta(project);
                }
            };
        } else {
            this.resourceDeltaProviding = resourceDeltaProviding;
        }

        if (projectProviding == null) {
            this.projectProviding = new IProjectProviding() {
                @Override
                public IProject getProject() {
                    return VitruvEmfBuilder.this.getProject();
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

    @Override
    protected void startMonitoring() {//final Set<String> monitoredFileTypes, final MetaRepositoryImpl metaRepository) {
        this.createAndStartEMFMonitor();
        this.emfMonitor.initialize();
        LOGGER.trace("Start monitoring changes");
    }

    protected void createAndStartEMFMonitor() {
        final IVitruviusAccessor vitruviusAcc = this.createVitruviusAccessor();
        final IEditorPartAdapterFactory epaFactory = new DefaultEditorPartAdapterFactoryImpl(getMonitoredFileExtensions());
        this.emfMonitor = this.monitorFactory.createVitruviusModelEditorSyncMgr(epaFactory, this.getVirtualModel(), vitruviusAcc);
    }

    private IVitruviusAccessor createVitruviusAccessor() {
        return new IVitruviusAccessor() {
            @Override
            public boolean isModelMonitored(final URI modelUri) {
                boolean doMonitor = isMonitoringChangesOfFilesWithExtension(modelUri.fileExtension());
                doMonitor &= VitruvEmfBuilder.this.isFileBelongingToThisProject(modelUri);
                LOGGER.trace("Monitor " + modelUri + "? " + doMonitor);
                return doMonitor;
            }
        };
    }

    private boolean isFileBelongingToThisProject(final URI fileURI) {
        if (this.isInTestingMode == false) {
            if (this.getBuildConfig() == null) {
                LOGGER.warn("Unable to determine whether " + fileURI
                        + " should be monitored with this builder: No build configuration.");
                return true;
            }
            final IFile referencedFile = getIFileForEMFUri(fileURI);
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
            final boolean isMonitoredResource = isMonitoringChangesOfFilesWithExtension(fileExtension);
            if (isMonitoredResource) {
                switch (delta.getKind()) {
                case IResourceDelta.ADDED:
                    VitruvEmfBuilder.this.importToVitruvius(iResource);
                    break;
                case IResourceDelta.REMOVED:
                    VitruvEmfBuilder.this.removeFromVitruvius(iResource);
                    break;
                case IResourceDelta.CHANGED:
                    VitruvEmfBuilder.this.triggerSynchronisation(iResource);
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
    	super.build(kind, args, monitor);
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
        this.emfMonitor.addModel(createPlatformResourceURI(iResource));
        this.triggerFileChangeSynchronisation(iResource, FileChangeKind.Create);
    }

    /**
     * Removes file from Vitruvius control by deleting its root element
     *
     * @param iResource
     *            resource to remove
     */
    private void removeFromVitruvius(final IResource iResource) {
        LOGGER.trace("Removing " + iResource);
        this.emfMonitor.removeModel(createPlatformResourceURI(iResource));
        this.triggerFileChangeSynchronisation(iResource, FileChangeKind.Delete);
    }

    enum FileChangeKind {
		Create,
		Delete
	}

	private void triggerFileChangeSynchronisation(final IResource iResource, final FileChangeKind fileChangeKind) {
		final String fileExtension = iResource.getFileExtension();
		if (isMonitoringChangesOfFilesWithExtension(fileExtension)) {
			final URI uri = createPlatformResourceURI(iResource);
			try (CommittableView view = getViewForURI(uri).withChangeDerivingTrait()) {
				switch (fileChangeKind) {
				case Create:
					Resource resource = new ResourceSetImpl().getResource(uri, true);
					for (EObject rootElement : new ArrayList<>(resource.getContents())) {
						view.registerRoot(rootElement, uri);
					}
					break;
				case Delete:
					for (EObject rootElement : new ArrayList<>(view.getRootObjects())) {
						EcoreUtil.delete(rootElement);
					}
					break;
				}
				view.commitChanges();
			} catch (Exception e) {
				LOGGER.error("Failed to close view for URI " + uri.toFileString(), e);
			}
		}
	}

	private View getViewForURI(URI resourceURI) {
		ViewSelector selector = getVirtualModel().createSelector(ViewTypeFactory.createIdentityMappingViewType(resourceURI.toString()));
		for (EObject element : selector.getSelectableElements()) {
			if (element.eResource() != null && element.eResource().getURI().equals(resourceURI)) {
				selector.setSelected(element, true);
			}
		}
		return selector.createView();
	}

    private void triggerSynchronisation(final IResource iResource) {
        LOGGER.trace("Triggering synchronization for " + iResource);
        if (isMonitoringChangesOfFilesWithExtension(iResource.getFileExtension())) {
            final URI uri = createPlatformResourceURI(iResource);
            this.emfMonitor.triggerSynchronisation(uri);
        }
    }

    public static interface IResourceDeltaProviding {
        public IResourceDelta getDelta(IProject project);
    }

    public static interface IProjectProviding {
        public IProject getProject();
    }
}
