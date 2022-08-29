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
import tools.vitruv.variability.vave.model.expression.Conjunction;
import tools.vitruv.variability.vave.model.expression.ExpressionFactory;
import tools.vitruv.variability.vave.model.expression.True;
import tools.vitruv.variability.vave.model.expression.Variable;
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;
import tools.vitruv.variability.vave.model.featuremodel.FeaturemodelFactory;
import tools.vitruv.variability.vave.model.featuremodel.ViewFeature;
import tools.vitruv.variability.vave.model.featuremodel.ViewTreeConstraint;
import tools.vitruv.variability.vave.model.vave.Configuration;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureOption;
import tools.vitruv.variability.vave.model.vave.GroupType;
import tools.vitruv.variability.vave.model.vave.Option;
import tools.vitruv.variability.vave.model.vave.VaveFactory;
import tools.vitruv.variability.vave.tests.VaveTest.RedundancyChangePropagationSpecification;
import tools.vitruv.variability.vave.util.ExpressionUtil;

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
		Configuration config = VaveFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config).getResult();

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

		True<FeatureOption> trueConstant = ExpressionFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1

		assertEquals(1, vave.getSystem().getSystemRevisions().size());
		assertEquals(1, vave.getSystem().getSystemRevisions().get(0).getRevisionID());
		assertEquals(1, vave.getSystem().getMappings().size());
		assertEquals(vave.getSystem().getSystemRevisions().get(0), ((Variable<Option>) vave.getSystem().getMappings().get(0).getExpression()).getValue());
		assertEquals(1, vave.getSystem().getDeltaModules().size());
		assertEquals(1, vave.getSystem().getMappings().get(0).getDeltaModules().size());
	}

	@Test
	public void MultipleInternalizeChangesWithTrueExpression(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);
		Configuration config = VaveFactory.eINSTANCE.createConfiguration();
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config).getResult();

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

		True<FeatureOption> trueConstant = ExpressionFactory.eINSTANCE.createTrue();
		vave.internalizeChanges(vmp1, trueConstant); // system revision 1

		assertEquals(1, vave.getSystem().getSystemRevisions().size());
		assertEquals(1, vave.getSystem().getSystemRevisions().get(0).getRevisionID());
		assertEquals(1, vave.getSystem().getMappings().size());
		assertEquals(vave.getSystem().getSystemRevisions().get(0), ((Variable<Option>) vave.getSystem().getMappings().get(0).getExpression()).getValue());
		assertEquals(1, vave.getSystem().getDeltaModules().size());
		assertEquals(1, vave.getSystem().getMappings().get(0).getDeltaModules().size());

		config.getOptions().add(vave.getSystem().getSystemRevisions().get(0));
		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config).getResult();

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

		assertEquals(2, vave.getSystem().getSystemRevisions().size());
		assertEquals(2, vave.getSystem().getSystemRevisions().get(1).getRevisionID());
		assertEquals(3, vave.getSystem().getMappings().size()); // changed mappings are copied and old system revision gets replaced with new one
		assertEquals(vave.getSystem().getSystemRevisions().get(0), ((Variable<Option>) vave.getSystem().getMappings().get(0).getExpression()).getValue());
		assertEquals(vave.getSystem().getSystemRevisions().get(1), ((Variable<Option>) vave.getSystem().getMappings().get(1).getExpression()).getValue());
		assertEquals(vave.getSystem().getSystemRevisions().get(1), ((Variable<Option>) vave.getSystem().getMappings().get(2).getExpression()).getValue());
	}

	@Test
	public void InternalizeChangesWithFeatureRevisionExpression(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);
		// Feature a, Feature b
		Feature featureA = VaveFactory.eINSTANCE.createFeature();
		featureA.setName("featureA");
		vave.getSystem().getFeatures().add(featureA);

		Configuration config = VaveFactory.eINSTANCE.createConfiguration();
		config.getOptions().add(featureA);
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config).getResult();

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

		Variable<FeatureOption> variableA = ExpressionFactory.eINSTANCE.createVariable();
		variableA.setValue(featureA);
		vave.internalizeChanges(vmp1, variableA); // system revision 1

		assertEquals(1, vave.getSystem().getSystemRevisions().size());
		assertEquals(1, vave.getSystem().getSystemRevisions().get(0).getRevisionID());
		assertEquals(1, vave.getSystem().getMappings().size());
		assertEquals(1, vave.getSystem().getDeltaModules().size());
		assertEquals(1, vave.getSystem().getMappings().get(0).getDeltaModules().size());

		Conjunction<Option> expectedConjunction = ExpressionFactory.eINSTANCE.createConjunction();

		Variable<Option> expectedVariableSystemRevision1 = ExpressionFactory.eINSTANCE.createVariable();
		expectedVariableSystemRevision1.setValue(vave.getSystem().getSystemRevisions().get(0));
		expectedConjunction.getExpressions().add(expectedVariableSystemRevision1);

		Variable<Option> expectedVariableA = ExpressionFactory.eINSTANCE.createVariable();
		expectedVariableA.setValue(featureA.getFeatureRevisions().get(0));
		expectedConjunction.getExpressions().add(expectedVariableA);

		assertTrue(ExpressionUtil.structuralEquivalence(expectedConjunction, vave.getSystem().getMappings().get(0).getExpression()));

		EqualityHelper eh = new EqualityHelper();
		assertTrue(eh.equals(expectedConjunction, vave.getSystem().getMappings().get(0).getExpression()));
	}

	@Test
	public void MultipleInternalizeChangesWithFeatureRevisionExpression(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);

//		// Feature a, Feature b
//		Feature featureA = VaveFactory.eINSTANCE.createFeature();
//		featureA.setName("featureA");
//		vave.getSystem().getFeature().add(featureA);
//		Feature featureB = VaveFactory.eINSTANCE.createFeature();
//		featureB.setName("featureB");
//		vave.getSystem().getFeature().add(featureB);

		FeatureModel fm = FeaturemodelFactory.eINSTANCE.createFeatureModel(); // new FeatureModel(null, null, new HashSet<FeatureOption>(), new HashSet<TreeConstraint>(), new HashSet<CrossTreeConstraint>());
		ViewFeature viewFeatureA = FeaturemodelFactory.eINSTANCE.createViewFeature();
		viewFeatureA.setName("featureA");
		ViewFeature viewFeatureB = FeaturemodelFactory.eINSTANCE.createViewFeature();
		viewFeatureB.setName("featureB");
		fm.getRootFeatures().add(viewFeatureA);
		ViewTreeConstraint tc = FeaturemodelFactory.eINSTANCE.createViewTreeConstraint();
		tc.setType(GroupType.OPTIONAL);
		viewFeatureA.getChildTreeConstraints().add(tc);
		tc.getChildFeatures().add(viewFeatureB);
		vave.internalizeDomain(fm);
		Feature featureA = vave.getSystem().getFeatures().stream().filter(f -> f.getName().equals("featureA")).findAny().get();
		Feature featureB = vave.getSystem().getFeatures().stream().filter(f -> f.getName().equals("featureB")).findAny().get();

		Configuration config = VaveFactory.eINSTANCE.createConfiguration();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1));
		config.getOptions().add(featureA);

		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config).getResult();

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

		Variable<FeatureOption> variableA = ExpressionFactory.eINSTANCE.createVariable();
		variableA.setValue(featureA);
		vave.internalizeChanges(vmp1, variableA); // system revision 1

		assertEquals(1, vave.getSystem().getDeltaModules().size());

		{
			Conjunction<Option> expectedConjunction = ExpressionFactory.eINSTANCE.createConjunction();
			Variable<Option> expectedVariableSystemRevision1 = ExpressionFactory.eINSTANCE.createVariable();
			expectedVariableSystemRevision1.setValue(vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1));
			expectedConjunction.getExpressions().add(expectedVariableSystemRevision1);

			Variable<Option> expectedVariableA = ExpressionFactory.eINSTANCE.createVariable();
			expectedVariableA.setValue(featureA.getFeatureRevisions().get(0));
			expectedConjunction.getExpressions().add(expectedVariableA);

			EqualityHelper eh = new EqualityHelper();
			assertTrue(eh.equals(expectedConjunction, vave.getSystem().getMappings().get(0).getExpression()));
		}

		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1));
		config.getOptions().add(featureA.getFeatureRevisions().get(0));
		config.getOptions().add(featureB);
		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config).getResult();

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

		Variable<FeatureOption> variableB = ExpressionFactory.eINSTANCE.createVariable();
		variableB.setValue(featureB);
		vave.internalizeChanges(vmp1ext, variableB); // system revision 2 and feature revision 1 of feature B

		assertEquals(2, vave.getSystem().getDeltaModules().size());

		{
			Conjunction<Option> expectedConjunction = ExpressionFactory.eINSTANCE.createConjunction();

			Variable<Option> expectedVariableSystemRevision2 = ExpressionFactory.eINSTANCE.createVariable();
			expectedVariableSystemRevision2.setValue(vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1));
			expectedConjunction.getExpressions().add(expectedVariableSystemRevision2);

			Variable<Option> expectedVariableB = ExpressionFactory.eINSTANCE.createVariable();
			expectedVariableB.setValue(featureB.getFeatureRevisions().get(0));
			expectedConjunction.getExpressions().add(expectedVariableB);

			EqualityHelper eh = new EqualityHelper();
			assertTrue(eh.equals(expectedConjunction, vave.getSystem().getMappings().get(2).getExpression()));
		}

		{
			Conjunction<Option> expectedConjunction = ExpressionFactory.eINSTANCE.createConjunction();

			Variable<Option> expectedVariableSystemRevision2 = ExpressionFactory.eINSTANCE.createVariable();
			expectedVariableSystemRevision2.setValue(vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1));
			expectedConjunction.getExpressions().add(expectedVariableSystemRevision2);

			Variable<Option> expectedVariableA = ExpressionFactory.eINSTANCE.createVariable();
			expectedVariableA.setValue(featureA.getFeatureRevisions().get(0));
			expectedConjunction.getExpressions().add(expectedVariableA);

			EqualityHelper eh = new EqualityHelper();
			assertTrue(eh.equals(expectedConjunction, vave.getSystem().getMappings().get(1).getExpression()));
		}

		// externalize product with feature a and b simultaneously
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1));
		config.getOptions().add(featureA.getFeatureRevisions().get(0));
		config.getOptions().add(featureB.getFeatureRevisions().get(0));
		final VirtualProductModel vmp2ext = vave.externalizeProduct(projectFolder.resolve("vmp2ext"), config).getResult();

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

		Conjunction<FeatureOption> expressionConjunction = ExpressionFactory.eINSTANCE.createConjunction();
		expressionConjunction.getExpressions().add(variableA);
		expressionConjunction.getExpressions().add(variableB);
		vave.internalizeChanges(vmp2ext, expressionConjunction); // system revision 3 and feature revision 2 of feature a and feature b

		{
			assertEquals(4, vave.getSystem().getSystemRevisions().size());
			assertEquals(2, vave.getSystem().getFeatures().size());
			assertEquals(6, vave.getSystem().getMappings().size());
			assertEquals(3, vave.getSystem().getDeltaModules().size());
		}
	}

//	@Test
	public void InternalizationOptionalDeltaTest(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);

		// Feature a, Feature b
		Feature featureA = VaveFactory.eINSTANCE.createFeature();
		featureA.setName("featureA");
		vave.getSystem().getFeatures().add(featureA);
		Feature featureB = VaveFactory.eINSTANCE.createFeature();
		featureB.setName("featureB");
		vave.getSystem().getFeatures().add(featureB);

		Configuration config = VaveFactory.eINSTANCE.createConfiguration();

		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config).getResult();

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
		Variable<FeatureOption> variableA = ExpressionFactory.eINSTANCE.createVariable();
		variableA.setValue(featureA);
		vave.internalizeChanges(vmp1, variableA); // system revision 1

		assertEquals(1, vave.getSystem().getDeltaModules().size());

		// externalize product with feature A
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(0));
		config.getOptions().add(featureA.getFeatureRevisions().get(0));
		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config).getResult();

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
		Variable<FeatureOption> variableB = ExpressionFactory.eINSTANCE.createVariable();
		variableB.setValue(featureB);
		vave.internalizeChanges(vmp1ext, variableB); // system revision 2 and feature revision 1 of feature B

		assertEquals(2, vave.getSystem().getDeltaModules().size());

		// externalize product with feature a and b
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(1));
		config.getOptions().add(featureA.getFeatureRevisions().get(0));
		config.getOptions().add(featureB.getFeatureRevisions().get(0));
		final VirtualProductModel vmp2ext = vave.externalizeProduct(projectFolder.resolve("vmp2ext"), config).getResult();

		// externalize product with feature a
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(1));
		config.getOptions().add(featureA.getFeatureRevisions().get(0));
		final VirtualProductModel vmp3ext = vave.externalizeProduct(projectFolder.resolve("vmp3ext"), config).getResult();

		// externalize product with feature b
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(1));
		config.getOptions().add(featureB.getFeatureRevisions().get(0));
		final VirtualProductModel vmp4ext = vave.externalizeProduct(projectFolder.resolve("vmp4ext"), config).getResult();
	}

//	@Test
	public void InternalizationOptionalDeltaTestMetamodelConform(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);

		// Feature a, Feature b
		Feature featureA = VaveFactory.eINSTANCE.createFeature();
		featureA.setName("featureA");
		vave.getSystem().getFeatures().add(featureA);
		Feature featureB = VaveFactory.eINSTANCE.createFeature();
		featureB.setName("featureB");
		vave.getSystem().getFeatures().add(featureB);

		Configuration config = VaveFactory.eINSTANCE.createConfiguration();

		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config).getResult();

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
		vave.internalizeChanges(vmp1, ExpressionFactory.eINSTANCE.createTrue()); // system revision 1

		assertEquals(1, vave.getSystem().getDeltaModules().size());

		// externalize product with empty config
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(0));
		final VirtualProductModel vmp0ext = vave.externalizeProduct(projectFolder.resolve("vmp0ext"), config).getResult();

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
		Variable<FeatureOption> variableA = ExpressionFactory.eINSTANCE.createVariable();
		variableA.setValue(featureA);
		vave.internalizeChanges(vmp0ext, variableA); // system revision 2 and feature revision 1 of feature A

		assertEquals(2, vave.getSystem().getDeltaModules().size());

		// externalize product with feature A
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(1));
		config.getOptions().add(featureA.getFeatureRevisions().get(0));
		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config).getResult();

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
		Variable<FeatureOption> variableB = ExpressionFactory.eINSTANCE.createVariable();
		variableB.setValue(featureB);
		vave.internalizeChanges(vmp1ext, variableB); // system revision 3 and feature revision 1 of feature B

		assertEquals(3, vave.getSystem().getDeltaModules().size());

		// externalize product with feature a and b
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(2));
		config.getOptions().add(featureA.getFeatureRevisions().get(0));
		config.getOptions().add(featureB.getFeatureRevisions().get(0));
		final VirtualProductModel vmp2ext = vave.externalizeProduct(projectFolder.resolve("vmp2ext"), config).getResult();

		// externalize product with feature a
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(2));
		config.getOptions().add(featureA.getFeatureRevisions().get(0));
		final VirtualProductModel vmp3ext = vave.externalizeProduct(projectFolder.resolve("vmp3ext"), config).getResult();

		// externalize product with feature b
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(2));
		config.getOptions().add(featureB.getFeatureRevisions().get(0));
		final VirtualProductModel vmp4ext = vave.externalizeProduct(projectFolder.resolve("vmp4ext"), config).getResult();
	}

}
