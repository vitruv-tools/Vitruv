package tools.vitruv.variability.vave.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import allElementTypes.ValueBased;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
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
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.VavemodelFactory;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveConsistencyTest {
	
	private URI createTestModelResourceUri(final String suffix, Path projectFolder) {
		return URI.createFileURI(projectFolder.resolve((("root" + suffix) + ".allElementTypes")).toString());
	}

	private VirtualVaVeModel setupVave(final Path projectFolder) throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final AllElementTypesDomain aetDomain = new AllElementTypesDomainProvider().getDomain();
		domains.add(aetDomain);

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		RedundancyChangePropagationSpecification _redundancyChangePropagationSpecification = new RedundancyChangePropagationSpecification(aetDomain, aetDomain);
		changePropagationSpecifications.add(_redundancyChangePropagationSpecification);

		VirtualVaVeModel vave = new VirtualVaVeModeIImpl(domains, changePropagationSpecifications, UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);
		return vave;
	}
	
	@Test
	public void InternalizationOptionalDeltaTestMetamodelConform(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);

		// Feature a, Feature b
		Feature featureA = VavemodelFactory.eINSTANCE.createFeature();
		featureA.setName("featureA");
		vave.getSystem().getFeature().add(featureA);
		Feature featureB = VavemodelFactory.eINSTANCE.createFeature();
		featureB.setName("featureB");
		vave.getSystem().getFeature().add(featureB);

		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();

		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);

		{
			final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
			final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
			changeRecorder.addToRecording(resourceSet);
			changeRecorder.beginRecording();
			final Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", projectFolder));
			ValueBased root = AllElementTypesCreators.aet.ValueBased();
			root.setValue("root");
			monitoredResource.getContents().add(root);
			final TransactionalChange recordedChange = changeRecorder.endRecording();
			// propagate recorded changes into vmp1
			vmp1.propagateChange(recordedChange);
		}

		// internalize product with expression TRUE
		vave.internalizeChanges(vmp1, VavemodelFactory.eINSTANCE.createTrue()); // system revision 1

		assertEquals(1, vave.getSystem().getDeltamodule().size());

		// externalize product with empty config
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(0));
		final VirtualProductModel vmp0ext = vave.externalizeProduct(projectFolder.resolve("vmp0ext"), config);

		assertEquals(0, vmp0ext.getDeltas().size());

		{
			// add fragment for feature a
			final ResourceSet resourceSet = vmp0ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource().getResourceSet();
			final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
			changeRecorder.addToRecording(resourceSet);
			changeRecorder.beginRecording();
			final Resource monitoredResource = vmp0ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
			ValueBased valueBasedA = AllElementTypesCreators.aet.ValueBased();
			valueBasedA.setValue("elementA");
			ValueBased root = (ValueBased) monitoredResource.getContents().get(0);
			root.getChildren().add(valueBasedA);
			monitoredResource.getContents().add(root);
			final TransactionalChange recordedChange = changeRecorder.endRecording();
			// propagate recorded changes into vmp1
			vmp0ext.propagateChange(recordedChange);
		}

		// internalize product with feature A
		vavemodel.Variable<FeatureOption> variableA = VavemodelFactory.eINSTANCE.createVariable();
		variableA.setOption(featureA);
		vave.internalizeChanges(vmp0ext, variableA); // system revision 2 and feature revision 1 of feature A

		assertEquals(2, vave.getSystem().getDeltamodule().size());

		// externalize product with feature A
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(1));
		config.getOption().add(featureA.getFeaturerevision().get(0));
		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config);

		assertEquals(0, vmp1ext.getDeltas().size());

		{
			// add fragment for feature b
			final ResourceSet resourceSet2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource().getResourceSet();
			final ChangeRecorder changeRecorder2 = new ChangeRecorder(resourceSet2);
			changeRecorder2.addToRecording(resourceSet2);
			changeRecorder2.beginRecording();
			final Resource monitoredResource2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
			ValueBased valueBasedB = AllElementTypesCreators.aet.ValueBased();
			valueBasedB.setValue("elementB");
			ValueBased root = (ValueBased) monitoredResource2.getContents().get(0);
			root.getChildren().add(valueBasedB);
			final TransactionalChange recordedChange2 = changeRecorder2.endRecording();
			vmp1ext.propagateChange(recordedChange2);
		}

		// internalize product with features A and B
		vavemodel.Variable<FeatureOption> variableB = VavemodelFactory.eINSTANCE.createVariable();
		variableB.setOption(featureB);
		vave.internalizeChanges(vmp1ext, variableB); // system revision 3 and feature revision 1 of feature B

		assertEquals(3, vave.getSystem().getDeltamodule().size());
		
		// externalize product with feature A and B
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(2));
		config.getOption().add(featureA.getFeaturerevision().get(1));
		config.getOption().add(featureB.getFeaturerevision().get(0));
		final VirtualProductModel vmp2ext = vave.externalizeProduct(projectFolder.resolve("vmp2ext"), config);
		
		// add dependency on solution space between feature A and B that were independent before both on problem and solution space
		final ResourceSet resourceSet3 = vmp2ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource().getResourceSet();
		final ChangeRecorder changeRecorder3 = new ChangeRecorder(resourceSet3);
		changeRecorder3.addToRecording(resourceSet3);
		changeRecorder3.beginRecording();
		final Resource monitoredResource3 = vmp2ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
		
		ValueBased valueBasedA = (ValueBased) monitoredResource3.getContents().get(1);
		ValueBased valueBasedB = (ValueBased) monitoredResource3.getContents().get(2);
		valueBasedA.getReferenced().add(valueBasedB);
		
		final TransactionalChange recordedChange3 = changeRecorder3.endRecording();
		vmp1ext.propagateChange(recordedChange3);
		
		// internalize product with features A and B. Feature a now references references feature b.
//		vavemodel.Variable<FeatureOption> variableAB = VavemodelFactory.eINSTANCE.createVariable();
//		variableAB.setOption(featureAB);
		vavemodel.Expression<FeatureOption> expr = VavemodelFactory.eINSTANCE.createConjunction();
		

		vave.internalizeChanges(vmp2ext, variableB); // system revision 3 and feature revision 1 of feature B

	}

}
