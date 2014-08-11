package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.JavaModelException;

import changeanalysis.CorrespondencesBuilder;
import changeanalysis.CorrespondencesBuilder.MyEObjectCorrespondence;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.MonitoredEditor;

public class PCMJavaBuilder extends VitruviusEmfBuilder {

    private MonitoredEditor monitoredEditor;

    public PCMJavaBuilder() {
        super();

        System.err.println("PCMJavaBuilder is ALIVE");
        final MetaRepositoryImpl metarepository = PCMJavaUtils.createPCMJavaMetarepository();
        Set<String> monitoredFileTypes = new HashSet<String>(Arrays.asList(PCMJavaNamespace.REPOSITORY_FILE_EXTENSION,
                PCMJavaNamespace.JAVA_FILE_EXTENSION));
        super.initializeBuilder(monitoredFileTypes, metarepository);

        // IProject monitoredProject = getProject();
        // String monitoredProjectName = monitoredProject.getName();

        String monitoredProjectName = "MediaStore";
        System.err.println("Monitor project " + monitoredProjectName);
        Metamodel javaMetamodel = metarepository.getMetamodel("java");
        VURI javaVURI = javaMetamodel.getURI();
        CorrespondenceInstance correspondenceInstance = this.vsum.getOrCreateAllCorrespondenceInstances(javaMetamodel)
                .iterator().next();
        addMediaStoreCorrespondences(correspondenceInstance);
        this.monitoredEditor = new MonitoredEditor(this.changeSynchronizing, this.modelProviding, monitoredProjectName);
    }

    private void addMediaStoreCorrespondences(final CorrespondenceInstance correspondenceInstance) {
        try {
            CorrespondencesBuilder mediaStoreCorrespondencesBuilder = new CorrespondencesBuilder();
            List<MyEObjectCorrespondence> correspondences = mediaStoreCorrespondencesBuilder.buildCorrespondenceModel();
            for (MyEObjectCorrespondence c : correspondences) {
                correspondenceInstance.createAndAddEObjectCorrespondence(c.a, c.b);
            }

        } catch (JavaModelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
        return super.build(kind, args, monitor);
    }

}
