package tools.vitruv.variability.vave.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import allElementTypes.Root;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.domains.AllElementTypesDomain;
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider;
import tools.vitruv.testutils.metamodels.AllElementTypesCreators;
import tools.vitruv.variability.vave.VirtualProductModel;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModeIImpl;
import tools.vitruv.variability.vave.tests.VaveTest.RedundancyChangePropagationSpecification;
import vavemodel.Configuration;
import vavemodel.Conjunction;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.FeatureRevision;
import vavemodel.Option;
import vavemodel.Variable;
import vavemodel.VavemodelFactory;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveInternalizeChangesTest {

	private URI createTestModelResourceUri(final String suffix, Path projectFolder) {
		return URI.createFileURI(projectFolder.resolve((("root" + suffix) + ".allElementTypes")).toString());
	}

	@Test
	public void InternalizeChangesWithTrueExpression(@TestProject final Path projectFolder) throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final AllElementTypesDomain aetDomain = new AllElementTypesDomainProvider().getDomain();
		domains.add(aetDomain);

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		RedundancyChangePropagationSpecification _redundancyChangePropagationSpecification = new RedundancyChangePropagationSpecification(aetDomain, aetDomain);
		changePropagationSpecifications.add(_redundancyChangePropagationSpecification);

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, projectFolder);

		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();

		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		final Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", projectFolder));
		Root root = AllElementTypesCreators.aet.Root();
		root.setId("root");
		monitoredResource.getContents().add(root);
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);
		
		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1

		assertEquals(1, vave.getSystem().getSystemrevision().size());
		assertEquals(1, vave.getSystem().getSystemrevision().get(0).getRevisionID());
		assertEquals(1, vave.getSystem().getMapping().size());
		assertEquals(vave.getSystem().getSystemrevision().get(0), ((Variable<Option>)vave.getSystem().getMapping().get(0).getExpression()).getOption());
		assertEquals(1, vave.getSystem().getDeltamodule().size());
		assertEquals(1, vave.getSystem().getMapping().get(0).getDeltamodule().size());
	}
	
	@Test
	public void MultipleInternalizeChangesWithTrueExpression(@TestProject final Path projectFolder) throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final AllElementTypesDomain aetDomain = new AllElementTypesDomainProvider().getDomain();
		domains.add(aetDomain);

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		RedundancyChangePropagationSpecification _redundancyChangePropagationSpecification = new RedundancyChangePropagationSpecification(aetDomain, aetDomain);
		changePropagationSpecifications.add(_redundancyChangePropagationSpecification);

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, projectFolder);

		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();

		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		final Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", projectFolder));
		Root root = AllElementTypesCreators.aet.Root();
		root.setId("root");
		monitoredResource.getContents().add(root);
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);
		
		vavemodel.True<FeatureOption> trueConstant = VavemodelFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1

		assertEquals(1, vave.getSystem().getSystemrevision().size());
		assertEquals(1, vave.getSystem().getSystemrevision().get(0).getRevisionID());
		assertEquals(1, vave.getSystem().getMapping().size());
		assertEquals(vave.getSystem().getSystemrevision().get(0), ((Variable<Option>)vave.getSystem().getMapping().get(0).getExpression()).getOption());
		assertEquals(1, vave.getSystem().getDeltamodule().size());
		assertEquals(1, vave.getSystem().getMapping().get(0).getDeltamodule().size());
		
		config.getOption().add(vave.getSystem().getSystemrevision().get(0));
		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config);
		
		final ResourceSet resourceSet2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource().getResourceSet();
		final ChangeRecorder changeRecorder2 = new ChangeRecorder(resourceSet2);
		changeRecorder2.addToRecording(resourceSet2);
		changeRecorder2.beginRecording();
		final Resource monitoredResource2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
		Root root2 = AllElementTypesCreators.aet.Root();
		root2.setId("root2");
		monitoredResource2.getContents().add(root2);
		final TransactionalChange recordedChange2 = changeRecorder2.endRecording();
		vmp1ext.propagateChange(recordedChange);

		vave.internalizeChanges(vmp1ext, trueConstant); // system revision 2
		
		assertEquals(2, vave.getSystem().getSystemrevision().size());
		assertEquals(2, vave.getSystem().getSystemrevision().get(1).getRevisionID());
		assertEquals(3, vave.getSystem().getMapping().size()); // changed mappings are copied and old system revision gets replaced with new one
		assertEquals(vave.getSystem().getSystemrevision().get(0), ((Variable<Option>)vave.getSystem().getMapping().get(0).getExpression()).getOption());
		assertEquals(vave.getSystem().getSystemrevision().get(1), ((Variable<Option>)vave.getSystem().getMapping().get(1).getExpression()).getOption());
		assertEquals(vave.getSystem().getSystemrevision().get(1), ((Variable<Option>)vave.getSystem().getMapping().get(2).getExpression()).getOption());
	}

	@Test
	public void InternalizeChangesWithFeatureRevisionExpression(@TestProject final Path projectFolder) throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final AllElementTypesDomain aetDomain = new AllElementTypesDomainProvider().getDomain();
		domains.add(aetDomain);

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		RedundancyChangePropagationSpecification _redundancyChangePropagationSpecification = new RedundancyChangePropagationSpecification(aetDomain, aetDomain);
		changePropagationSpecifications.add(_redundancyChangePropagationSpecification);

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, projectFolder);
		
		// Feature a, Feature b
		Feature featureA = VavemodelFactory.eINSTANCE.createFeature();
		featureA.setName("featureA");
		vave.getSystem().getFeature().add(featureA);
		Feature featureB = VavemodelFactory.eINSTANCE.createFeature();
		
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();

		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);
		
		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		final Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", projectFolder));
		Root root = AllElementTypesCreators.aet.Root();
		root.setId("elementA");
		monitoredResource.getContents().add(root);
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		// propagate recorded changes into vmp1
		vmp1.propagateChange(recordedChange);
		
		vavemodel.Variable<FeatureOption> variableA = VavemodelFactory.eINSTANCE.createVariable();
		variableA.setOption(featureA);
		vave.internalizeChanges(vmp1, variableA); // system revision 1
		
		assertEquals(1, vave.getSystem().getSystemrevision().size());
		assertEquals(1, vave.getSystem().getSystemrevision().get(0).getRevisionID());
		assertEquals(1, vave.getSystem().getMapping().size());
		assertEquals(1, vave.getSystem().getDeltamodule().size());
		assertEquals(1, vave.getSystem().getMapping().get(0).getDeltamodule().size());
		
		Conjunction<Option> expectedConjunction = VavemodelFactory.eINSTANCE.createConjunction();
		
		Variable<Option> expectedVariableSystemRevision1 = VavemodelFactory.eINSTANCE.createVariable();
		expectedVariableSystemRevision1.setOption(vave.getSystem().getSystemrevision().get(0));
		expectedConjunction.getTerm().add(expectedVariableSystemRevision1);
		
		Variable<Option> expectedVariableA = VavemodelFactory.eINSTANCE.createVariable();
		FeatureRevision featrev1 = VavemodelFactory.eINSTANCE.createFeatureRevision();
		featrev1.setRevisionID(1); 
		expectedVariableA.setOption(featrev1); 
		expectedConjunction.getTerm().add(expectedVariableA);
				
		EqualityHelper eh = new EqualityHelper();
		boolean equal = eh.equals(expectedConjunction, vave.getSystem().getMapping().get(0).getExpression());
		assertTrue(eh.equals(expectedConjunction, vave.getSystem().getMapping().get(0).getExpression()));

		
	}

	@Test
	public void MultipleInternalizeChangesWithFeatureRevisionExpression() {

	}

}
