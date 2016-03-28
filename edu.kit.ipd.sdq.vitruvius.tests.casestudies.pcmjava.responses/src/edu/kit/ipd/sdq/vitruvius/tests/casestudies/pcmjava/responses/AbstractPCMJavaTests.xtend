package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses

import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractResponseTests
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl
import java.util.Arrays
import java.util.HashSet
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping
import java.util.function.Supplier
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.utils.jamopp.JaMoPPTUIDCalculatorAndResolver

abstract class AbstractPCMJavaTests extends AbstractResponseTests {
	protected static val MODEL_FILE_EXTENSION_SOURCE = "repository";
	protected static val MODEL_FILE_EXTENSION_TARGET = "java";
	
	new(Supplier<? extends Change2CommandTransformingProviding> change2CommandTransformingProvidingSupplier) {
		super(change2CommandTransformingProvidingSupplier)
	}
	
	protected override createMetaRepository() {
		val metarepository = new MetaRepositoryImpl();
        // PCM
		val pcmMMUri = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
        val pcmNSUris = new HashSet<String>();
        pcmNSUris.addAll(Arrays.asList(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE_URIS));
        val fileExtensions = <String>newArrayOfSize(2);
        fileExtensions.set(0, PCMJaMoPPNamespace.PCM.REPOSITORY_FILE_EXTENSION);
        fileExtensions.set(1, PCMJaMoPPNamespace.PCM.SYSTEM_FILE_EXTENSION);
        val pcmMM = new Metamodel(pcmNSUris, pcmMMUri, fileExtensions);
        metarepository.addMetamodel(pcmMM);
        // JaMoPP
        val jaMoPPURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
        val jamoppNSURIs = new HashSet<String>();
        jamoppNSURIs.addAll(Arrays.asList(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE_URIS));
        val jaMoPPMM = new Metamodel(jamoppNSURIs, jaMoPPURI, new JaMoPPTUIDCalculatorAndResolver(),
                PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION);
        metarepository.addMetamodel(jaMoPPMM);
        
		val pcmJavaMapping = new Mapping(pcmMM, jaMoPPMM);
        metarepository.addMapping(pcmJavaMapping);
        return metarepository;
	}
}