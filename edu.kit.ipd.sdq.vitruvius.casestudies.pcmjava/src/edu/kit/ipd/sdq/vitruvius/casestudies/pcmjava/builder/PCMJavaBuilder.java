package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.builder.VitruviusEmfBuilder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.JaMoPPTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;

public class PCMJavaBuilder extends VitruviusEmfBuilder {

    public PCMJavaBuilder() {
        super();
        final MetaRepositoryImpl metarepository = new MetaRepositoryImpl();

        final VURI pcmMMUri = VURI.getInstance(PCMJavaNamespace.PCM_METAMODEL_NAMESPACE);
        final Set<String> pcmNSUris = new HashSet<String>();
        pcmNSUris.add(PCMJavaNamespace.PCM_METAMODEL_NAMESPACE_URI);
        pcmNSUris.add(PCMJavaNamespace.PCM_METAMODEL_NAMESPACE_URI_REPOSITORY);
        final Metamodel pcmMM = new Metamodel(pcmNSUris, pcmMMUri, PCMJavaNamespace.REPOSITORY_FILE_EXTENSION);
        metarepository.addMetamodel(pcmMM);

        final VURI jaMoPPURI = VURI.getInstance(PCMJavaNamespace.JAMOPP_METAMODEL_NAMESPACE);
        final Set<String> jamoppNSURIs = new HashSet<String>();
        jamoppNSURIs.add(PCMJavaNamespace.JAMOPP_METAMODEL_NAMESPACE_URI);
        jamoppNSURIs.add(PCMJavaNamespace.JAMPPP_METAMODEL_NAMESPACE_URI_CONTAINER);
        final Metamodel jaMoPPMM = new Metamodel(jamoppNSURIs, jaMoPPURI, new JaMoPPTUIDCalculatorAndResolver(),
                PCMJavaNamespace.JAVA_FILE_EXTENSION);
        metarepository.addMetamodel(jaMoPPMM);

        final Mapping pcmJavaMapping = new Mapping(pcmMM, jaMoPPMM);
        metarepository.addMapping(pcmJavaMapping);
        Set<String> monitoredFileTypes = new HashSet<String>(Arrays.asList(PCMJavaNamespace.REPOSITORY_FILE_EXTENSION,
                PCMJavaNamespace.JAVA_FILE_EXTENSION));
        super.initializeBuilder(monitoredFileTypes, metarepository);
    }

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
            throws CoreException {
        return super.build(kind, args, monitor);
    }

}
