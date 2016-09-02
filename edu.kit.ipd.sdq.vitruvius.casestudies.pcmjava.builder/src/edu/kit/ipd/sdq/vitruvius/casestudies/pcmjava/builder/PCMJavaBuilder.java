package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.PCMNamespace;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.util.PCMJavaRepositoryCreationUtil;
import edu.kit.ipd.sdq.vitruvius.domains.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.domains.java.monitorededitor.MonitoredEditor;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.TransformationAbortCause;

public class PCMJavaBuilder extends VitruviusEmfBuilder implements SynchronisationListener {
    // ID of PCMJavaBuilder
    public static final String BUILDER_ID = "edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder.PCMJavaBuilder.id";

    private static Logger logger = Logger.getLogger(PCMJavaBuilder.class.getSimpleName());

    private MonitoredEditor javaMonitoredEditor;

    public PCMJavaBuilder() {
        super();
        logger.trace("PCMJavaBuilder is ALIVE");
        final MetaRepositoryImpl metarepository = PCMJavaRepositoryCreationUtil.createPCMJavaMetarepository();
        Set<String> monitoredFileTypes = new HashSet<String>(
                Arrays.asList(PCMNamespace.REPOSITORY_FILE_EXTENSION
                        // since java files already monitored by the java monitor we do not have to monitor
                        // them here
                        /* , PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION */));

        super.initializeBuilder(monitoredFileTypes, metarepository);
    }

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
        if (null == this.javaMonitoredEditor) {
            initializeCodeMonitor();
        }
        return super.build(kind, args, monitor);
    }

    private void initializeCodeMonitor() {
        IProject monitoredProject = getProject();
        String monitoredProjectName = monitoredProject.getName();
        this.javaMonitoredEditor = new MonitoredEditor(this.changeSynchronizing, this.modelProviding,
                monitoredProjectName);
    }

    /**
     * deactivate all monitors
     */
    @Override
    public void syncStarted() {
        // enableAutoBuild(false);
        // if (null != this.emfMonitor) {
        // logger.info("Stop Vitruvius EMF monitor");
        // this.emfMonitor.dispose();
        // this.emfMonitor.setReportChanges(false);
        // }
        // if (null != this.javaMonitoredEditor) {
        // this.javaMonitoredEditor.stopASTListening();
        // this.javaMonitoredEditor.setReportChanges(false);
        // }
    }

    /**
     * reactivate the monitors
     */
    @Override
    public void syncFinished() {
        // enableAutoBuild(true);
        try {
            // waitForPreviosOperationToFinish();
        } finally {
            // logger.info("Restart Vitruvius EMF monitor");
            // if (null != this.emfMonitor) {
            // this.emfMonitor.initialize();
            // this.emfMonitor.setReportChanges(true);
            // }
            // if (null == this.javaMonitoredEditor) {
            // initializeCodeMonitor();
            // }
            // if (null != this.javaMonitoredEditor) {
            // this.javaMonitoredEditor.startASTListening();
            // this.javaMonitoredEditor.setReportChanges(true);
            // }
        }

    }

//    private void waitForPreviosOperationToFinish() {
//        try {
//            Object lock = new Object();
//            synchronized (lock) {
//                lock.wait(2000);
//            }
//        } catch (InterruptedException e) {
//            logger.info("Could not wait for auto sync to pass. Reason: " + e);
//        }
//    }

    @Override
    public void syncAborted(final TransformationAbortCause cause) {
        // nothing to do
    }

//    private boolean enableAutoBuild(final boolean enable) {
//        IWorkspace workspace = ResourcesPlugin.getWorkspace();
//        IWorkspaceDescription desc = workspace.getDescription();
//        boolean isAutoBuilding = desc.isAutoBuilding();
//        if (isAutoBuilding != enable) {
//            desc.setAutoBuilding(enable);
//            try {
//                workspace.setDescription(desc);
//            } catch (CoreException e) {
//                logger.info("Could not activate/deactivate auto build. Reason: " + e);
//            }
//        }
//        return isAutoBuilding;
//    }
}
