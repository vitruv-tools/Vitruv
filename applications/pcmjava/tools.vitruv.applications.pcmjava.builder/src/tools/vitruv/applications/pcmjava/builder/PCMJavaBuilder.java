package tools.vitruv.applications.pcmjava.builder;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import tools.vitruv.domains.emf.builder.VitruviusEmfBuilder;
import tools.vitruv.domains.java.monitorededitor.MonitoredEditor;
import tools.vitruv.framework.modelsynchronization.SynchronisationListener;
import tools.vitruv.framework.modelsynchronization.TransformationAbortCause;

public class PCMJavaBuilder extends VitruviusEmfBuilder implements SynchronisationListener {
    // ID of PCMJavaBuilder
    public static final String BUILDER_ID = "tools.vitruv.applications.pcmjava.builder.PCMJavaBuilder.id";

    private static Logger logger = Logger.getLogger(PCMJavaBuilder.class.getSimpleName());

    private MonitoredEditor javaMonitoredEditor;

    public PCMJavaBuilder() {
        super();
        logger.trace("PCMJavaBuilder is ALIVE");
    }

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
    	IProject[] result = super.build(kind, args, monitor);
    	if (null == this.javaMonitoredEditor) {
            initializeCodeMonitor();
        }
        return result;
    }

    private void initializeCodeMonitor() {
        IProject monitoredProject = getProject();
        String monitoredProjectName = monitoredProject.getName();
        this.javaMonitoredEditor = new MonitoredEditor(this.getVirtualModel(), monitoredProjectName);
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
