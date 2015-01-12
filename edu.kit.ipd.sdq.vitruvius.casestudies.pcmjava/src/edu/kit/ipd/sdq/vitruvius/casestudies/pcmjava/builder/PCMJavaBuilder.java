package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.MonitoredEditor;

public class PCMJavaBuilder extends VitruviusEmfBuilder {

    private static Logger logger = Logger.getLogger(PCMJavaBuilder.class.getSimpleName());

    private MonitoredEditor monitoredEditor;

    public PCMJavaBuilder() {
        super();
        logger.trace("PCMJavaBuilder is ALIVE");
        final MetaRepositoryImpl metarepository = PCMJavaUtils.createPCMJavaMetarepository();
        Set<String> monitoredFileTypes = new HashSet<String>(
                Arrays.asList(PCMJaMoPPNamespace.PCM.REPOSITORY_FILE_EXTENSION
                // since java files already monitored by the java monitor we do not have to monitor
                // them here
                /* , PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION */));

        super.initializeBuilder(monitoredFileTypes, metarepository);
    }

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
        if (null == this.monitoredEditor) {
            initializeCodeMonitor();
        }
        return super.build(kind, args, monitor);
    }

    private void initializeCodeMonitor() {
        IProject monitoredProject = getProject();
        String monitoredProjectName = monitoredProject.getName();
        this.monitoredEditor = new MonitoredEditor(this.changeSynchronizing, this.modelProviding, monitoredProjectName);
    }

}
