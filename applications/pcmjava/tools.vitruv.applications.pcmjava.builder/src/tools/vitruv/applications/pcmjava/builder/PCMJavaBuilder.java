package tools.vitruv.applications.pcmjava.builder;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import tools.vitruv.domains.java.monitorededitor.MonitoredEditor;
import tools.vitruv.framework.monitorededitor.VitruviusProjectBuilder;

public class PCMJavaBuilder extends VitruviusProjectBuilder {
    // ID of JavaBuilder
    public static final String BUILDER_ID = "tools.vitruv.applications.pcmjava.builder.PCMJavaBuilder.id";

    private static Logger logger = Logger.getLogger(PCMJavaBuilder.class.getSimpleName());

    @SuppressWarnings("unused")
    private MonitoredEditor javaMonitoredEditor;

    public PCMJavaBuilder() {
        super();
        logger.trace("PCMJavaBuilder is ALIVE");
    }

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
        return super.build(kind, args, monitor);
    }
    
    @Override
    protected void startMonitoring() {
    	IProject monitoredProject = getProject();
        String monitoredProjectName = monitoredProject.getName();
        this.javaMonitoredEditor = new MonitoredEditor(this.getVirtualModel(), monitoredProjectName);
    }

}
