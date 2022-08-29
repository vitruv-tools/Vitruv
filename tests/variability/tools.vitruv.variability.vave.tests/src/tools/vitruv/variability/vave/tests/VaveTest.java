package tools.vitruv.variability.vave.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.Iterables;

import allElementTypes.Root;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.propagation.ResourceAccess;
import tools.vitruv.framework.propagation.impl.AbstractChangePropagationSpecification;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.internal.ModelInstance;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.domains.AllElementTypesDomain;
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider;
import tools.vitruv.testutils.matchers.ModelMatchers;
import tools.vitruv.testutils.metamodels.AllElementTypesCreators;
import tools.vitruv.variability.vave.VirtualProductModel;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModelImpl;
import tools.vitruv.variability.vave.model.expression.Conjunction;
import tools.vitruv.variability.vave.model.expression.Disjunction;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.expression.ExpressionFactory;
import tools.vitruv.variability.vave.model.expression.Implication;
import tools.vitruv.variability.vave.model.expression.Not;
import tools.vitruv.variability.vave.model.expression.True;
import tools.vitruv.variability.vave.model.expression.Variable;
import tools.vitruv.variability.vave.model.vave.Configuration;
import tools.vitruv.variability.vave.model.vave.CrossTreeConstraint;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureOption;
import tools.vitruv.variability.vave.model.vave.FeatureRevision;
import tools.vitruv.variability.vave.model.vave.GroupType;
import tools.vitruv.variability.vave.model.vave.Option;
import tools.vitruv.variability.vave.model.vave.System;
import tools.vitruv.variability.vave.model.vave.TreeConstraint;
import tools.vitruv.variability.vave.model.vave.VaveFactory;
import tools.vitruv.variability.vave.util.ExpressionUtil;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveTest {

	Configuration config = VaveFactory.eINSTANCE.createConfiguration();

	public static class RedundancyChangePropagationSpecification extends AbstractChangePropagationSpecification {
		public static URI getTargetResourceUri(final URI sourceUri) {
			String _fileString = sourceUri.trimFileExtension().toFileString();
			String _plus = (_fileString + "Copy.");
			String _fileExtension = sourceUri.fileExtension();
			String _plus_1 = (_plus + _fileExtension);
			return URI.createFileURI(_plus_1);
		}

		public RedundancyChangePropagationSpecification(final VitruvDomain sourceDomain, final VitruvDomain targetDomain) {
			super(sourceDomain, targetDomain);
		}

		@Override
		public boolean doesHandleChange(final EChange change, final CorrespondenceModel correspondenceModel) {
			if ((change instanceof InsertRootEObject)) {
				EObject _newValue = ((InsertRootEObject) change).getNewValue();
				return (_newValue instanceof Root);
			}
			return false;
		}

		@Override
		public void propagateChange(final EChange change, final CorrespondenceModel correspondenceModel, @Extension final ResourceAccess resourceAccess) {
			boolean _doesHandleChange = this.doesHandleChange(change, correspondenceModel);
			boolean _not = (!_doesHandleChange);
			if (_not) {
				return;
			}
			final InsertRootEObject<Root> typedChange = ((InsertRootEObject<Root>) change);
			final Root insertedRoot = typedChange.getNewValue();
			final Iterable<Root> correspondingRoots = Iterables.<Root>filter(CorrespondenceModelUtil.<Correspondence>getCorrespondingEObjects(correspondenceModel, insertedRoot), Root.class);
			Root _xifexpression = null;
			int _size = IterableExtensions.size(correspondingRoots);
			boolean _equals = (_size == 1);
			if (_equals) {
				_xifexpression = ((Root[]) Conversions.unwrapArray(correspondingRoots, Root.class))[0];
			} else {
				Root _xblockexpression = null;
				{
					Root _Root = AllElementTypesCreators.aet.Root();
					final Procedure1<Root> _function = (Root it) -> {
						it.setId(insertedRoot.getId());
					};
					final Root newRoot = ObjectExtensions.<Root>operator_doubleArrow(_Root, _function);
					correspondenceModel.createAndAddCorrespondence(List.<EObject>of(insertedRoot), List.<EObject>of(newRoot));
					_xblockexpression = newRoot;
				}
				_xifexpression = _xblockexpression;
			}
			final Root correspondingRoot = _xifexpression;
			EObject _eContainer = insertedRoot.eContainer();
			boolean _tripleNotEquals = (_eContainer != null);
			if (_tripleNotEquals) {
				final Set<Root> correspondingObjects = CorrespondenceModelUtil.<Root, Correspondence>getCorrespondingEObjects(correspondenceModel, insertedRoot.eContainer(), Root.class);
				Assertions.assertEquals(1, correspondingObjects.size());
				Root _get = ((Root[]) Conversions.unwrapArray(correspondingObjects, Root.class))[0];
				_get.setRecursiveRoot(correspondingRoot);
			}
			final URI resourceURI = typedChange.getResource().getURI();
			resourceAccess.persistAsRoot(correspondingRoot, RedundancyChangePropagationSpecification.getTargetResourceUri(resourceURI));
		}
	}

	private Expression<FeatureOption> createExpression(System system) {
		Conjunction<FeatureOption> conjunction = ExpressionFactory.eINSTANCE.createConjunction();
		Feature car = VaveFactory.eINSTANCE.createFeature();
		car.setName("Car");
		Feature engineType = VaveFactory.eINSTANCE.createFeature();
		engineType.setName("EngineType");
		system.getFeatures().add(car);
		system.getFeatures().add(engineType);
		Variable<FeatureOption> variable1 = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variable2 = ExpressionFactory.eINSTANCE.createVariable();
		variable1.setValue(car);
		variable2.setValue(engineType);
		conjunction.getExpressions().add(variable1);
		conjunction.getExpressions().add(variable2);
		return conjunction;
	}

	private static final String MODEL_PATH = "models";

	private URI createTestModelResourceUri(final String suffix, Path projectFolder) {
		return URI.createFileURI(projectFolder.resolve((("root" + suffix) + ".allElementTypes")).toString());
	}

	private Path projectFolder;

	@BeforeEach
	public void initializeProjectFolder(@TestProject final Path projectFolder) {
		this.projectFolder = projectFolder;
	}

	@Test
	public void testVSUMCreation() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		VirtualVaVeModel vave = new VirtualVaVeModelImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), this.projectFolder);
		VirtualProductModel vsum = vave.externalizeProduct(this.projectFolder, config).getResult();
		assertNotNull(vsum);
	}

	@Test
	public void testVSUMPropagation() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new AllElementTypesDomainProvider().getDomain());
		VirtualVaVeModel vave = new VirtualVaVeModelImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), this.projectFolder);
		Expression<FeatureOption> expression = ExpressionFactory.eINSTANCE.createTrue();

		final VirtualProductModel virtualModel = vave.externalizeProduct(this.projectFolder.resolve("vsum"), config).getResult();
		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", this.projectFolder));
		monitoredResource.getContents().add(AllElementTypesCreators.aet.Root());
		AllElementTypesCreators.aet.Root().setId("root");

		final TransactionalChange recordedChange = changeRecorder.endRecording();
		virtualModel.propagateChange(recordedChange);
		final ModelInstance vsumModel = virtualModel.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		MatcherAssert.<Resource>assertThat(vsumModel.getResource(), ModelMatchers.containsModelOf(monitoredResource));

		vave.internalizeChanges(virtualModel, expression);
	}

	@Test
	public void testVSUMPropagationAndConsistency() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final AllElementTypesDomain aetDomain = new AllElementTypesDomainProvider().getDomain();
		domains.add(aetDomain);

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		RedundancyChangePropagationSpecification _redundancyChangePropagationSpecification = new RedundancyChangePropagationSpecification(aetDomain, aetDomain);
		changePropagationSpecifications.add(_redundancyChangePropagationSpecification);

		VirtualVaVeModel vave = new VirtualVaVeModelImpl(domains, changePropagationSpecifications, UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), this.projectFolder);

		final VirtualProductModel virtualModel = vave.externalizeProduct(this.projectFolder.resolve("vsum"), config).getResult();

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();

		Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", this.projectFolder));
		monitoredResource.getContents().add(AllElementTypesCreators.aet.Root());
		AllElementTypesCreators.aet.Root().setId("root");

		final TransactionalChange recordedChange = changeRecorder.endRecording();

		virtualModel.propagateChange(recordedChange);

		final ModelInstance sourceModel = virtualModel.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		final ModelInstance targetModel = virtualModel.getModelInstance(RedundancyChangePropagationSpecification.getTargetResourceUri(this.createTestModelResourceUri("", this.projectFolder)));

		MatcherAssert.<Resource>assertThat(targetModel.getResource(), ModelMatchers.containsModelOf(monitoredResource));
		Assertions.assertEquals(1, CorrespondenceModelUtil.<Correspondence>getCorrespondingEObjects(virtualModel.getCorrespondenceModel(), sourceModel.getResource().getContents().get(0)).size());
	}

	@Test
	public void testDeltaApplication() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new AllElementTypesDomainProvider().getDomain());
		VirtualVaVeModel vave = new VirtualVaVeModelImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), this.projectFolder);
		// Expression<FeatureOption> expression = createExpression(vave.getSystem());
		True<FeatureOption> trueConstant = ExpressionFactory.eINSTANCE.createTrue();
		final VirtualProductModel virtualModel = vave.externalizeProduct(this.projectFolder.resolve("vsum"), config).getResult(); // empty product

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		Resource _createResource = resourceSet.createResource(this.createTestModelResourceUri("", this.projectFolder));
		final Procedure1<Resource> _function = (Resource it) -> {
			EList<EObject> _contents = it.getContents();
			Root _Root = AllElementTypesCreators.aet.Root();
			final Procedure1<Root> _function_1 = (Root it_1) -> {
				it_1.setId("root");
			};
			Root _doubleArrow = ObjectExtensions.<Root>operator_doubleArrow(_Root, _function_1);
			_contents.add(_doubleArrow);
		};
		final Resource monitoredResource = ObjectExtensions.<Resource>operator_doubleArrow(_createResource, _function);
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		virtualModel.propagateChange(recordedChange);
		final ModelInstance vsumModel = virtualModel.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		MatcherAssert.<Resource>assertThat(vsumModel.getResource(), ModelMatchers.containsModelOf(monitoredResource));

		vave.internalizeChanges(virtualModel, trueConstant);

		config.getOptions().add(vave.getSystem().getSystemRevisions().get(0));

		final VirtualProductModel virtualModel2 = vave.externalizeProduct(this.projectFolder.resolve("vsum2"), config).getResult();

		final ModelInstance vsumModel2 = virtualModel2.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		MatcherAssert.<Resource>assertThat(vsumModel2.getResource(), ModelMatchers.containsModelOf(monitoredResource));
	}

	@Test // Test wrt. problem space and feature revisions
	public void testCarVaveModelCreationWithFeaturesOnly() {
		// create tree content of simple vave model instance
		System system = VaveFactory.eINSTANCE.createSystem();
		Feature car = VaveFactory.eINSTANCE.createFeature();
		Feature engineType = VaveFactory.eINSTANCE.createFeature();
		Feature gasoline = VaveFactory.eINSTANCE.createFeature();
		Feature electric = VaveFactory.eINSTANCE.createFeature();
		Feature smogControl = VaveFactory.eINSTANCE.createFeature();
		car.setName("car");
		engineType.setName("engineType");
		gasoline.setName("gasoline");
		electric.setName("electric");
		smogControl.setName("smogControl");
		system.getFeatures().add(car);
		system.getFeatures().add(gasoline);
		system.getFeatures().add(electric);
		system.getFeatures().add(smogControl);
		system.getFeatures().add(engineType);
		TreeConstraint treeconstr1 = VaveFactory.eINSTANCE.createTreeConstraint();
		treeconstr1.setType(GroupType.MANDATORY);
		// Make Engine Type mandatory by adding a tree constraint of type XOR to its
		// parent feature Car
		car.getChildTreeConstraints().add(treeconstr1);
		treeconstr1.getChildFeatures().add(engineType);
		// Make Smog Control optional
		TreeConstraint treeconstr2 = VaveFactory.eINSTANCE.createTreeConstraint();
		treeconstr2.setType(GroupType.OPTIONAL);
		car.getChildTreeConstraints().add(treeconstr2);
		treeconstr2.getChildFeatures().add(smogControl);
		// Make OR-Group between Gasoline and Electric with Engine Type parent
		TreeConstraint treeconstr3 = VaveFactory.eINSTANCE.createTreeConstraint();
		treeconstr3.setType(GroupType.OR);
		engineType.getChildTreeConstraints().add(treeconstr3);
		treeconstr3.getChildFeatures().add(gasoline);
		treeconstr3.getChildFeatures().add(electric);
		// create cross-tree constraint implication: gasoline implies smog control
		CrossTreeConstraint crosstreeconstr1 = VaveFactory.eINSTANCE.createCrossTreeConstraint();
		Implication<FeatureOption> implication1 = ExpressionFactory.eINSTANCE.createImplication();
		Variable<FeatureOption> variable1 = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variable2 = ExpressionFactory.eINSTANCE.createVariable();
		crosstreeconstr1.setExpression(implication1);
		implication1.setLeft(variable1);
		implication1.setRight(variable2);
		variable1.setValue(gasoline);
		variable2.setValue(smogControl);
		system.getConstraints().add(crosstreeconstr1);

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("vavemodel", new XMIResourceFactoryImpl());
		ResourceSet resSet = new ResourceSetImpl();
		Resource resource = resSet.createResource(URI.createFileURI(this.projectFolder.resolve("models/car_withFeatures.vavemodel").toString()));
		resource.getContents().add(system);

		Diagnostic d = Diagnostician.INSTANCE.validate(system);
		java.lang.System.out.println("MESSAGE: " + d.getMessage());

		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		java.lang.System.out.println("FOLDER: " + this.projectFolder);
	}

	@Test // Test wrt. problem space and feature revisions
	public void testCarVaveModelCreationWithFeatureRevisions() {
		// create tree content of simple vave model instance
		System system = VaveFactory.eINSTANCE.createSystem();
		Feature car = VaveFactory.eINSTANCE.createFeature();
		Feature engineType = VaveFactory.eINSTANCE.createFeature();
		Feature gasoline = VaveFactory.eINSTANCE.createFeature();
		Feature electric = VaveFactory.eINSTANCE.createFeature();
		Feature smogControl = VaveFactory.eINSTANCE.createFeature();
		car.setName("car");
		engineType.setName("engineType");
		gasoline.setName("gasoline");
		electric.setName("electric");
		smogControl.setName("smogControl");
		system.getFeatures().add(car);
		system.getFeatures().add(gasoline);
		system.getFeatures().add(electric);
		system.getFeatures().add(smogControl);
		system.getFeatures().add(engineType);
		FeatureRevision car_1 = VaveFactory.eINSTANCE.createFeatureRevision();
		car_1.setRevisionID(1);
		car.getFeatureRevisions().add(car_1);
		FeatureRevision gasoline_1 = VaveFactory.eINSTANCE.createFeatureRevision();
		gasoline_1.setRevisionID(1);
		gasoline.getFeatureRevisions().add(gasoline_1);
		FeatureRevision electric_1 = VaveFactory.eINSTANCE.createFeatureRevision();
		electric_1.setRevisionID(1);
		electric.getFeatureRevisions().add(electric_1);
		FeatureRevision smogControl_1 = VaveFactory.eINSTANCE.createFeatureRevision();
		smogControl_1.setRevisionID(1);
		smogControl.getFeatureRevisions().add(smogControl_1);
		TreeConstraint treeconstr1 = VaveFactory.eINSTANCE.createTreeConstraint();
		treeconstr1.setType(GroupType.MANDATORY);
		// Make Engine Type mandatory by adding a tree constraint of type XOR to its
		// parent feature Car
		car.getChildTreeConstraints().add(treeconstr1);
		treeconstr1.getChildFeatures().add(engineType);
		TreeConstraint treeconstr2 = VaveFactory.eINSTANCE.createTreeConstraint();
		treeconstr2.setType(GroupType.OPTIONAL);
		car.getChildTreeConstraints().add(treeconstr2);
		treeconstr2.getChildFeatures().add(smogControl);
		TreeConstraint treeconstr3 = VaveFactory.eINSTANCE.createTreeConstraint();
		treeconstr3.setType(GroupType.OR);
		engineType.getChildTreeConstraints().add(treeconstr3);
		treeconstr3.getChildFeatures().add(gasoline);
		treeconstr3.getChildFeatures().add(electric);
		// create cross-tree constraint implication: gasoline implies smog control
		CrossTreeConstraint crosstreeconstr1 = VaveFactory.eINSTANCE.createCrossTreeConstraint();
		Implication<FeatureOption> implication1 = ExpressionFactory.eINSTANCE.createImplication();
		Variable<FeatureOption> variable1 = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variable2 = ExpressionFactory.eINSTANCE.createVariable();
		crosstreeconstr1.setExpression(implication1);
		implication1.setLeft(variable1);
		implication1.setRight(variable2);
		variable1.setValue(gasoline);
		variable2.setValue(smogControl);
		system.getConstraints().add(crosstreeconstr1);

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("vavemodel", new XMIResourceFactoryImpl());
		ResourceSet resSet = new ResourceSetImpl();
		Resource resource = resSet.createResource(URI.createFileURI(this.projectFolder.resolve("models/car_withFeatureRevisions.vavemodel").toString()));
		resource.getContents().add(system);
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveAndLoad() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new AllElementTypesDomainProvider().getDomain());
		VirtualVaVeModel vaveSaved = new VirtualVaVeModelImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), this.projectFolder);
		// Expression<FeatureOption> expression = createExpression(vaveSaved.getSystem());
		True<FeatureOption> trueConstant = ExpressionFactory.eINSTANCE.createTrue();
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("vave", new XMIResourceFactoryImpl());

		final VirtualProductModel virtualModel = vaveSaved.externalizeProduct(this.projectFolder.resolve("vsum"), config).getResult(); // empty product

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		Resource _createResource = resourceSet.createResource(this.createTestModelResourceUri("", this.projectFolder));
		final Procedure1<Resource> _function = (Resource it) -> {
			EList<EObject> _contents = it.getContents();
			Root _Root = AllElementTypesCreators.aet.Root();
			final Procedure1<Root> _function_1 = (Root it_1) -> {
				it_1.setId("root");
			};
			Root _doubleArrow = ObjectExtensions.<Root>operator_doubleArrow(_Root, _function_1);
			_contents.add(_doubleArrow);
		};
		final Resource monitoredResource = ObjectExtensions.<Resource>operator_doubleArrow(_createResource, _function);
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		virtualModel.propagateChange(recordedChange);
		final ModelInstance vsumModel = virtualModel.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		MatcherAssert.<Resource>assertThat(vsumModel.getResource(), ModelMatchers.containsModelOf(monitoredResource));

		vaveSaved.internalizeChanges(virtualModel, trueConstant);

		config.getOptions().add(vaveSaved.getSystem().getSystemRevisions().get(0));

		final VirtualProductModel virtualModel2 = vaveSaved.externalizeProduct(this.projectFolder.resolve("vsum2"), config).getResult();

		final ModelInstance vsumModel2 = virtualModel2.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		MatcherAssert.<Resource>assertThat(vsumModel2.getResource(), ModelMatchers.containsModelOf(monitoredResource));

		VirtualVaVeModel vaveLoaded = new VirtualVaVeModelImpl(domains, new HashSet<>(), UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), this.projectFolder);

		config.getOptions().clear();
		config.getOptions().add(vaveLoaded.getSystem().getSystemRevisions().get(0));

		final VirtualProductModel virtualModel3 = vaveLoaded.externalizeProduct(this.projectFolder.resolve("vsum3"), config).getResult();

		final ModelInstance vsumModel3 = virtualModel3.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));

		MatcherAssert.<Resource>assertThat(vsumModel3.getResource(), ModelMatchers.containsModelOf(monitoredResource));

	}

	@Test
	public void conjunctionEvalTest() {
		Feature a = VaveFactory.eINSTANCE.createFeature();
		Feature b = VaveFactory.eINSTANCE.createFeature();

		Conjunction<Option> conjunction = ExpressionFactory.eINSTANCE.createConjunction();
		Variable<Option> va = ExpressionFactory.eINSTANCE.createVariable();
		va.setValue(a);
		Variable<Option> vb = ExpressionFactory.eINSTANCE.createVariable();
		vb.setValue(b);
		conjunction.getExpressions().add(va);
		conjunction.getExpressions().add(vb);

		Configuration configuration = VaveFactory.eINSTANCE.createConfiguration();
		configuration.getOptions().add(a);

		assertFalse(ExpressionUtil.eval(conjunction, configuration));

		configuration.getOptions().add(b);

		assertTrue(ExpressionUtil.eval(conjunction, configuration));
	}

	@Test
	public void implicationEvalTest() {
		Feature a = VaveFactory.eINSTANCE.createFeature();
		Feature b = VaveFactory.eINSTANCE.createFeature();

		Implication<Option> implication = ExpressionFactory.eINSTANCE.createImplication();
		Variable<Option> va = ExpressionFactory.eINSTANCE.createVariable();
		va.setValue(a);
		Variable<Option> vb = ExpressionFactory.eINSTANCE.createVariable();
		vb.setValue(b);
		implication.setLeft(va);
		implication.setRight(vb);

		Configuration configuration = VaveFactory.eINSTANCE.createConfiguration();
		configuration.getOptions().add(a);

		assertFalse(ExpressionUtil.eval(implication, configuration));

		configuration.getOptions().add(b);

		assertTrue(ExpressionUtil.eval(implication, configuration));
	}

	@Test
	public void disjunctionEvalTest() {
		Feature a = VaveFactory.eINSTANCE.createFeature();
		Feature b = VaveFactory.eINSTANCE.createFeature();

		Disjunction<Option> disjunction = ExpressionFactory.eINSTANCE.createDisjunction();
		Variable<Option> va = ExpressionFactory.eINSTANCE.createVariable();
		va.setValue(a);
		Variable<Option> vb = ExpressionFactory.eINSTANCE.createVariable();
		vb.setValue(b);
		disjunction.getExpressions().add(va);
		disjunction.getExpressions().add(vb);

		Configuration configuration = VaveFactory.eINSTANCE.createConfiguration();

		assertFalse(ExpressionUtil.eval(disjunction, configuration));

		configuration.getOptions().add(a);

		assertTrue(ExpressionUtil.eval(disjunction, configuration));

		configuration.getOptions().add(b);

		assertTrue(ExpressionUtil.eval(disjunction, configuration));
	}

	@Test
	public void disjunctionNotEvalTest() {
		Feature a = VaveFactory.eINSTANCE.createFeature();
		Feature b = VaveFactory.eINSTANCE.createFeature();

		Disjunction<Option> disjunction = ExpressionFactory.eINSTANCE.createDisjunction();
		Variable<Option> va = ExpressionFactory.eINSTANCE.createVariable();
		va.setValue(a);
		Variable<Option> vb = ExpressionFactory.eINSTANCE.createVariable();
		vb.setValue(b);
		disjunction.getExpressions().add(va);
		disjunction.getExpressions().add(vb);

		Not<Option> not = ExpressionFactory.eINSTANCE.createNot();
		not.setExpression(disjunction);

		Configuration configuration = VaveFactory.eINSTANCE.createConfiguration();
		configuration.getOptions().add(a);

		assertFalse(ExpressionUtil.eval(not, configuration));

		configuration.getOptions().add(b);

		assertFalse(ExpressionUtil.eval(not, configuration));
	}

}
