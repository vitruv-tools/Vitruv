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

import allElementTypes.NonRoot;
import allElementTypes.Root;
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
import tools.vitruv.variability.vave.impl.VirtualVaVeModelImpl;
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

	private VirtualVaVeModel setupVave(final Path projectFolder) throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final AllElementTypesDomain aetDomain = new AllElementTypesDomainProvider().getDomain();
		domains.add(aetDomain);

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		RedundancyChangePropagationSpecification _redundancyChangePropagationSpecification = new RedundancyChangePropagationSpecification(aetDomain, aetDomain);
		changePropagationSpecifications.add(_redundancyChangePropagationSpecification);

		VirtualVaVeModel vave = new VirtualVaVeModelImpl(domains, changePropagationSpecifications, UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);
		return vave;
	}

	@Test
	public void InternalizeChangesWithTrueExpression(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);
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
		assertEquals(vave.getSystem().getSystemrevision().get(0), ((Variable<Option>) vave.getSystem().getMapping().get(0).getExpression()).getOption());
		assertEquals(1, vave.getSystem().getDeltamodule().size());
		assertEquals(1, vave.getSystem().getMapping().get(0).getDeltamodule().size());
	}

	@Test
	public void MultipleInternalizeChangesWithTrueExpression(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);
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
		assertEquals(vave.getSystem().getSystemrevision().get(0), ((Variable<Option>) vave.getSystem().getMapping().get(0).getExpression()).getOption());
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
		assertEquals(vave.getSystem().getSystemrevision().get(0), ((Variable<Option>) vave.getSystem().getMapping().get(0).getExpression()).getOption());
		assertEquals(vave.getSystem().getSystemrevision().get(1), ((Variable<Option>) vave.getSystem().getMapping().get(1).getExpression()).getOption());
		assertEquals(vave.getSystem().getSystemrevision().get(1), ((Variable<Option>) vave.getSystem().getMapping().get(2).getExpression()).getOption());
	}

	@Test
	public void InternalizeChangesWithFeatureRevisionExpression(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);
		// Feature a, Feature b
		Feature featureA = VavemodelFactory.eINSTANCE.createFeature();
		featureA.setName("featureA");
		vave.getSystem().getFeature().add(featureA);

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
		assertTrue(eh.equals(expectedConjunction, vave.getSystem().getMapping().get(0).getExpression()));
	}

	@Test
	public void MultipleInternalizeChangesWithFeatureRevisionExpression(@TestProject final Path projectFolder) throws Exception {
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
			// add fragment for feature a
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
		}

		vavemodel.Variable<FeatureOption> variableA = VavemodelFactory.eINSTANCE.createVariable();
		variableA.setOption(featureA);
		vave.internalizeChanges(vmp1, variableA); // system revision 1

		assertEquals(1, vave.getSystem().getDeltamodule().size());

		{
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
			assertTrue(eh.equals(expectedConjunction, vave.getSystem().getMapping().get(0).getExpression()));
		}

		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(0));
		config.getOption().add(featureA.getFeaturerevision().get(0));
		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config);

		assertEquals(0, vmp1ext.getDeltas().size());

		// check if element a is in implementation of product
		// TODO assertion

		{
			// add fragment for feature b
			final ResourceSet resourceSet2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource().getResourceSet();
			final ChangeRecorder changeRecorder2 = new ChangeRecorder(resourceSet2);
			changeRecorder2.addToRecording(resourceSet2);
			changeRecorder2.beginRecording();
			final Resource monitoredResource2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
			Root root2 = AllElementTypesCreators.aet.Root();
			root2.setId("elementB");
			monitoredResource2.getContents().add(root2);
			final TransactionalChange recordedChange2 = changeRecorder2.endRecording();
			vmp1ext.propagateChange(recordedChange2);
		}

		vavemodel.Variable<FeatureOption> variableB = VavemodelFactory.eINSTANCE.createVariable();
		variableB.setOption(featureB);
		vave.internalizeChanges(vmp1ext, variableB); // system revision 2 and feature revision 1 of feature B

		assertEquals(2, vave.getSystem().getDeltamodule().size());

		{
			Conjunction<Option> expectedConjunction = VavemodelFactory.eINSTANCE.createConjunction();

			Variable<Option> expectedVariableSystemRevision2 = VavemodelFactory.eINSTANCE.createVariable();
			expectedVariableSystemRevision2.setOption(vave.getSystem().getSystemrevision().get(1));
			expectedConjunction.getTerm().add(expectedVariableSystemRevision2);

			Variable<Option> expectedVariableB = VavemodelFactory.eINSTANCE.createVariable();
			FeatureRevision featrev1 = VavemodelFactory.eINSTANCE.createFeatureRevision();
			featrev1.setRevisionID(1);
			expectedVariableB.setOption(featrev1);
			expectedConjunction.getTerm().add(expectedVariableB);

			EqualityHelper eh = new EqualityHelper();
			assertTrue(eh.equals(expectedConjunction, vave.getSystem().getMapping().get(1).getExpression()));
		}

		{
			Conjunction<Option> expectedConjunction = VavemodelFactory.eINSTANCE.createConjunction();

			Variable<Option> expectedVariableSystemRevision2 = VavemodelFactory.eINSTANCE.createVariable();
			expectedVariableSystemRevision2.setOption(vave.getSystem().getSystemrevision().get(1));
			expectedConjunction.getTerm().add(expectedVariableSystemRevision2);

			Variable<Option> expectedVariableA = VavemodelFactory.eINSTANCE.createVariable();
			FeatureRevision featrev1 = VavemodelFactory.eINSTANCE.createFeatureRevision();
			featrev1.setRevisionID(1);
			expectedVariableA.setOption(featrev1);
			expectedConjunction.getTerm().add(expectedVariableA);

			EqualityHelper eh = new EqualityHelper();
			assertTrue(eh.equals(expectedConjunction, vave.getSystem().getMapping().get(2).getExpression()));
		}

		// externalize product with feature a and b simultaneously
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(1));
		config.getOption().add(featureA.getFeaturerevision().get(0));
		config.getOption().add(featureB.getFeaturerevision().get(0));
		final VirtualProductModel vmp2ext = vave.externalizeProduct(projectFolder.resolve("vmp2ext"), config);

		// add fragment for features a and b
		{
			final ResourceSet resourceSet3 = vmp2ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource().getResourceSet();
			final ChangeRecorder changeRecorder3 = new ChangeRecorder(resourceSet3);
			changeRecorder3.addToRecording(resourceSet3);
			changeRecorder3.beginRecording();
			final Resource monitoredResource3 = vmp2ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
			Root root3 = AllElementTypesCreators.aet.Root();
			root3.setId("elementAB");
			monitoredResource3.getContents().add(root3);
			final TransactionalChange recordedChange3 = changeRecorder3.endRecording();
			vmp2ext.propagateChange(recordedChange3);
		}

		Conjunction<FeatureOption> expressionConjunction = VavemodelFactory.eINSTANCE.createConjunction();
		expressionConjunction.getTerm().add(variableA);
		expressionConjunction.getTerm().add(variableB);
		vave.internalizeChanges(vmp2ext, expressionConjunction); // system revision 3 and feature revision 2 of feature a and feature b

		{
			assertEquals(3, vave.getSystem().getSystemrevision().size());
			assertEquals(2, vave.getSystem().getFeature().size());
			assertEquals(6, vave.getSystem().getMapping().size());
			assertEquals(3, vave.getSystem().getDeltamodule().size());
		}
	}

//	@Test
	public void InternalizationOptionalDeltaTest(@TestProject final Path projectFolder) throws Exception {
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
			// add fragment for feature a
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
		}

		// internalize product with feature A
		vavemodel.Variable<FeatureOption> variableA = VavemodelFactory.eINSTANCE.createVariable();
		variableA.setOption(featureA);
		vave.internalizeChanges(vmp1, variableA); // system revision 1

		assertEquals(1, vave.getSystem().getDeltamodule().size());

		// externalize product with feature A
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(0));
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
			Root root2 = AllElementTypesCreators.aet.Root();
			root2.setId("elementB");
			monitoredResource2.getContents().add(root2);
			final TransactionalChange recordedChange2 = changeRecorder2.endRecording();
			vmp1ext.propagateChange(recordedChange2);
		}

		// internalize product with features A and B
		vavemodel.Variable<FeatureOption> variableB = VavemodelFactory.eINSTANCE.createVariable();
		variableB.setOption(featureB);
		vave.internalizeChanges(vmp1ext, variableB); // system revision 2 and feature revision 1 of feature B

		assertEquals(2, vave.getSystem().getDeltamodule().size());

		// externalize product with feature a and b
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(1));
		config.getOption().add(featureA.getFeaturerevision().get(0));
		config.getOption().add(featureB.getFeaturerevision().get(0));
		final VirtualProductModel vmp2ext = vave.externalizeProduct(projectFolder.resolve("vmp2ext"), config);

		// externalize product with feature a
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(1));
		config.getOption().add(featureA.getFeaturerevision().get(0));
		final VirtualProductModel vmp3ext = vave.externalizeProduct(projectFolder.resolve("vmp3ext"), config);

		// externalize product with feature b
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(1));
		config.getOption().add(featureB.getFeaturerevision().get(0));
		final VirtualProductModel vmp4ext = vave.externalizeProduct(projectFolder.resolve("vmp4ext"), config);
	}

//	@Test
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
			Root root = AllElementTypesCreators.aet.Root();
			root.setId("root");
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
			NonRoot nonroot = AllElementTypesCreators.aet.NonRoot();
			nonroot.setId("elementA");
			Root root = (Root) monitoredResource.getContents().get(0);
			root.getMultiValuedUnorderedContainmentEReference().add(nonroot);
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
			NonRoot nonroot = AllElementTypesCreators.aet.NonRoot();
			nonroot.setId("elementB");
			Root root = (Root) monitoredResource2.getContents().get(0);
			root.getMultiValuedUnorderedContainmentEReference().add(nonroot);
			final TransactionalChange recordedChange2 = changeRecorder2.endRecording();
			vmp1ext.propagateChange(recordedChange2);
		}

		// internalize product with features A and B
		vavemodel.Variable<FeatureOption> variableB = VavemodelFactory.eINSTANCE.createVariable();
		variableB.setOption(featureB);
		vave.internalizeChanges(vmp1ext, variableB); // system revision 3 and feature revision 1 of feature B

		assertEquals(3, vave.getSystem().getDeltamodule().size());

		// externalize product with feature a and b
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(2));
		config.getOption().add(featureA.getFeaturerevision().get(0));
		config.getOption().add(featureB.getFeaturerevision().get(0));
		final VirtualProductModel vmp2ext = vave.externalizeProduct(projectFolder.resolve("vmp2ext"), config);

		// externalize product with feature a
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(2));
		config.getOption().add(featureA.getFeaturerevision().get(0));
		final VirtualProductModel vmp3ext = vave.externalizeProduct(projectFolder.resolve("vmp3ext"), config);

		// externalize product with feature b
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(2));
		config.getOption().add(featureB.getFeaturerevision().get(0));
		final VirtualProductModel vmp4ext = vave.externalizeProduct(projectFolder.resolve("vmp4ext"), config);
	}

}
