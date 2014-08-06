package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.MonitoredEditor;

public class PCMJavaBuilder extends VitruviusEmfBuilder {

    private MonitoredEditor monitoredEditor;

    public PCMJavaBuilder() {
        super();

        final MetaRepositoryImpl metarepository = PCMJavaUtils.createPCMJavaMetarepository();
        Set<String> monitoredFileTypes = new HashSet<String>(Arrays.asList(PCMJavaNamespace.REPOSITORY_FILE_EXTENSION,
                PCMJavaNamespace.JAVA_FILE_EXTENSION));

        super.initializeBuilder(monitoredFileTypes, metarepository);
        String monitoredProjectName = getProject().getName();
        this.monitoredEditor = new MonitoredEditor(this.changeSynchronizing, this.modelProviding, monitoredProjectName);
    }

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
        return super.build(kind, args, monitor);
    }

}
