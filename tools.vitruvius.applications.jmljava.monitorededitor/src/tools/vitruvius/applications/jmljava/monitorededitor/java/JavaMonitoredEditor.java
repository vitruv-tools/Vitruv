package tools.vitruvius.applications.jmljava.monitorededitor.java;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.widgets.Display;
import org.emftext.language.java.containers.CompilationUnit;

import tools.vitruvius.applications.jmljava.changesynchronizer.ChangeSynchronizerRegistry;
import tools.vitruvius.applications.jmljava.changesynchronizer.JavaTransformation;
import tools.vitruvius.applications.jmljava.changesynchronizer.JmlSynchronizationListener;
import tools.vitruvius.applications.jmljava.extensions.SourceDirProvider;
import tools.vitruvius.domains.java.echange.feature.JavaFeatureEChange;
import tools.vitruvius.domains.java.monitorededitor.MonitoredEditor;
import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import tools.vitruvius.framework.modelsynchronization.SynchronisationListener;
import tools.vitruvius.framework.modelsynchronization.TransformationAbortCause;
import tools.vitruvius.framework.monitorededitor.AbstractMonitoredEditor;
import tools.vitruvius.framework.monitorededitor.registries.MonitoredProjectsRegistry;
import tools.vitruvius.framework.monitorededitor.registries.RegisteredMonitoredEditor;

/**
 * A monitored editor for Java, which supports change detection.
 */
public class JavaMonitoredEditor extends MonitoredEditor implements JmlSynchronizationListener, RegisteredMonitoredEditor {

    private static final String[] SUPPORTED_EXTENSIONS = { "java" };
    private static final Logger LOGGER = Logger.getLogger(JavaMonitoredEditor.class);
    private boolean syncInProgress = false;
    private final Object astListenerStartedMonitorObject = new Object();

    /**
     * Provider for Java source directories. The directories are inferred from the monitored
     * projects automatically.
     */
    public static class JavaSourceDirProvider implements SourceDirProvider {

        @Override
        public String[] getContainedExtensions() {
            return SUPPORTED_EXTENSIONS;
        }

        @Override
        public IResource[] getSourceDirs() {

            final List<IResource> sourceDirs = new ArrayList<IResource>();

            try {
                final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
                for (final String projectName : MonitoredProjectsRegistry.getInstance().getRegisteredElements()) {
                    final IProject project = root.getProject(projectName);
                    final IJavaProject javaProject = JavaCore.create(project);
                    for (final IPackageFragmentRoot fragmentRoot : javaProject.getAllPackageFragmentRoots()) {
                        if (fragmentRoot.getKind() == IPackageFragmentRoot.K_SOURCE) {
                            sourceDirs.add(fragmentRoot.getResource());
                        }
                    }
                }
            } catch (final JavaModelException e) {
                LOGGER.warn("Could not determine source directories of "
                        + StringUtils.join(MonitoredProjectsRegistry.getInstance().getRegisteredElements(), ", ") + ".",
                        e);
            }

            return sourceDirs.toArray(new IResource[0]);

        }

    }

    /**
     * Constructor
     */
    public JavaMonitoredEditor() {
        super(new ChangeSynchronizing() {
            @Override
            public List<List<VitruviusChange>> synchronizeChange(final VitruviusChange changes) {
                // new Thread() {
                // @Override
                // public void run() {
                return ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().synchronizeChange(changes);
                // }
                // } .start();
            }
        }, null, MonitoredProjectsRegistry.getInstance().getRegisteredElements().toArray(new String[0]));
        ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().register(this);
        LOGGER.info("Initialized Java monitored editor.");
    }

    @Override
    public void syncStarted() {
        this.syncInProgress = true;
        this.stopASTListening();
    }

    @Override
    public void syncFinished() {
        this.syncInProgress = false;
        this.startASTListening();
    }

    @Override
    public void syncAborted(final GeneralChange abortedChange) {
        final JavaFeatureEChange<?,?> featureChange = (JavaFeatureEChange<?,?>) abortedChange.getEChanges().get(0);
        EObject affectedObject = featureChange.getAffectedEObject();
        if (affectedObject == null) {
            affectedObject = featureChange.getOldAffectedEObject();
        }
        final EObject rootObject = EcoreUtil.getRootContainer(affectedObject);
        if (!(rootObject instanceof CompilationUnit)) {
            return;
        }

        this.undoLastOperation();
    }

    @Override
    public void syncAborted(final TransformationAbortCause cause) {
        if (cause instanceof JavaTransformation) {
            this.undoLastOperation();
        }
    }

    @Override
    public void startASTListening() {
        if (!this.syncInProgress && !this.refactoringInProgress) {
            super.startASTListening();
            synchronized (this.astListenerStartedMonitorObject) {
                this.astListenerStartedMonitorObject.notifyAll();
            }
        }
    }

    /**
     * Undoes the last operation by using the Eclipse undo history. Attention: This does NOT work
     * for refactorings, since at the time the synchronisation is called, it is not finished yet.
     */
    private void undoLastOperation() {
        try {
            synchronized (this.astListenerStartedMonitorObject) {
                while (this.refactoringInProgress) {
                    this.astListenerStartedMonitorObject.wait(10000);
                    Thread.sleep(1000);
                }
            }
        } catch (final InterruptedException e) {
            LOGGER.error("Interrupted. Performing undo operation.", e);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Display.getDefault().syncExec(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final IUndoableOperation[] undoOps = OperationHistoryFactory.getOperationHistory()
                                    .getUndoHistory(IOperationHistory.GLOBAL_UNDO_CONTEXT);
                            if (undoOps.length > 0) {
                                final IUndoableOperation lastOperation = undoOps[undoOps.length - 1];
                                LOGGER.info("Undoing operation: " + lastOperation.getLabel());
                                JavaMonitoredEditor.this.stopASTListening();
                                lastOperation.undo(new NullProgressMonitor(), null);
                                JavaMonitoredEditor.this.startASTListening();
                            }
                        } catch (final ExecutionException e) {
                            LOGGER.error("Unable to undo the last operation:", e);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void shutdown() {
        ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().unregister(this);
        super.revokeRegistrations();
    }

}
