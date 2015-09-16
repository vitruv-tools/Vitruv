package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.executortest;

import static edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.MIRTestUtil.createAttributeTUIDMetamodel;

import java.io.IOException;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.PCM2UMLExecutor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.MIRTestUtil;
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.VitruviusEMFCasestudyTest;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;
import uml_mockup.Interface;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;

public class CreateBasicComponentTest extends VitruviusEMFCasestudyTest implements SynchronisationListener {
	private static final Logger LOGGER = Logger.getLogger(CreateBasicComponentTest.class);
	private static final String MODEL_PATH = TestUtil.PROJECT_URI + "/model";

	@Override
	protected void synchronizeFileChange(FileChangeKind fileChangeKind, VURI vuri) {
		LOGGER.trace("synchronizing file change for " + vuri.toString());
		super.synchronizeFileChange(fileChangeKind, vuri);
	}

	@Override
	protected void triggerSynchronization(VURI vuri) {
		LOGGER.trace("triggering synchronization for " + vuri.toString());
		super.triggerSynchronization(vuri);
	}

	private <T extends EObject> T createManipulateSaveAndSyncResource(String resourcePath, Supplier<T> manipulate)
			throws IOException {
		final VURI resourceVURI = VURI.getInstance(resourcePath);

		final T result = manipulate.get();
		final URI resourceURI = URI.createPlatformResourceURI(resourcePath, false);
		final Resource resource = EcoreResourceBridge.loadResourceAtURI(resourceURI, resourceSet);
		EcoreResourceBridge.saveEObjectAsOnlyContent(result, resource);

		this.synchronizeFileChange(FileChangeKind.CREATE, resourceVURI);

		return result;
	}

	private EObject createAndSyncResourceWithRootObject(String resourcePath, EObject rootEObject) throws IOException {
		return createManipulateSaveAndSyncResource(resourcePath, () -> rootEObject);
	}

	private <T extends EObject, R> R recordManipulateSaveAndSync(T input, Function<T, R> manipulate)
			throws IOException {
		changeRecorder.beginRecording(Collections.singletonList(input));
		R result = manipulate.apply(input);
		EcoreResourceBridge.saveResource(input.eResource());
		this.triggerSynchronization(input);

		return result;
	}

	private <T extends EObject> void recordManipulateSaveAndSync(T input, Consumer<T> manipulate) throws IOException {
		recordManipulateSaveAndSync(input, it -> {
			manipulate.accept(it);
			return null;
		});
	}

	private UPackage createPackage(String name) {
		UPackage pkg = Uml_mockupFactory.eINSTANCE.createUPackage();
		pkg.setName(name);
		return pkg;
	}

	@Override
	protected void setUserInteractor(final UserInteracting newUserInteracting,
			final ChangeSynchronizerImpl changeSynchronizerImpl) throws Throwable {
	};

	@Before
	public void setUpTest() throws Throwable {
		super.setUpTest();

		// reset log4j
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		PropertyConfigurator.configure("log4j.properties");
		Logger.getRootLogger().setLevel(Level.ALL);
	}

	@Test
	public void createMapAndUnmapPackage() throws IOException {
		UPackage pkg = createPackage("FirstPackageName_mapped");
		createAndSyncResourceWithRootObject(MODEL_PATH + "/uml.uml", pkg);
		recordManipulateSaveAndSync(pkg, it -> {
			it.setName("FirstPackageName_nomap");
		});
	}

	private void doNStepsOfTest(int n) throws IOException {
		LOGGER.trace("Executing " + n + " steps of test workflow.");
		
		// step 0
		LOGGER.trace("Step 0");
		UPackage pkg = createPackage("FirstPackageName");
		createAndSyncResourceWithRootObject(MODEL_PATH + "/uml.uml", pkg);

		if (n < 1) {
			return;
		}

		// step 1
		LOGGER.trace("Step 1");
		recordManipulateSaveAndSync(pkg, it -> {
			UClass clazz = Uml_mockupFactory.eINSTANCE.createUClass();
			clazz.setName("TestNestedClass");
			it.getClasses().add(clazz);
		});

		if (n < 2) {
			return;
		}

		// step 2
		LOGGER.trace("Step 2");
		final UClass classToRename = recordManipulateSaveAndSync(pkg, it -> {
			UClass clazz = Uml_mockupFactory.eINSTANCE.createUClass();
			clazz.setName("TestNestedClass2");
			it.getClasses().add(clazz);
			return clazz;
		});

		if (n < 3) {
			return;
		}

		// step 3
		LOGGER.trace("Step 3");
		recordManipulateSaveAndSync(pkg, it -> {
			UClass clazz = Uml_mockupFactory.eINSTANCE.createUClass();
			clazz.setName("TestNestedClass3_nomap");
			it.getClasses().add(clazz);
		});

		if (n < 4) {
			return;
		}

		// step 4
		LOGGER.trace("Step 4");
		recordManipulateSaveAndSync(classToRename, it -> {
			it.setName("TestNestedClass2_nomap");
		});

		if (n < 5) {
			return;
		}

		// step 5
		LOGGER.trace("Step 5");
		recordManipulateSaveAndSync(classToRename, it -> {
			it.setName("TestNestedClass2_remapped");
		});

		if (n < 6) {
			return;
		}

		// step 6
		LOGGER.trace("Step 6");
		recordManipulateSaveAndSync(pkg, it -> {
			pkg.setName("FirstPackageName_nomap");
		});

		if (n < 7) {
			return;
		}

		// step 7
		LOGGER.trace("Step 7");
		recordManipulateSaveAndSync(pkg, it -> {
			pkg.setName("FirstPackageName");
		});
	}

	@Ignore
	@Test
	public void step_0_createPackage() throws IOException {
		doNStepsOfTest(0);
	}

	@Ignore
	@Test
	public void step_1_AddClass() throws IOException {
		doNStepsOfTest(1);
	}

	@Ignore
	@Test
	public void step_2_AddSecondClass() throws IOException {
		doNStepsOfTest(2);
	}

	@Ignore
	@Test
	public void step_3_AddUnmappedClass() throws IOException {
		doNStepsOfTest(3);
	}

	@Ignore
	@Test
	public void step_4_UnmapSecondClass() throws IOException {
		doNStepsOfTest(4);
	}

	@Ignore
	@Test
	public void step_5_RemapSecondClass() throws IOException {
		doNStepsOfTest(5);
	}

	@Ignore
	@Test
	public void step_6_UnmapPackage() throws IOException {
		doNStepsOfTest(6);
	}

	@Ignore
	@Test
	public void step_7_RemapPackage() throws IOException {
		doNStepsOfTest(7);
	}

	@Override
	public void syncStarted() {
		LOGGER.trace("syncStarted");
	}

	@Override
	public void syncFinished() {
		LOGGER.trace("syncFinished");
	}

	@Override
	public void syncAborted(EMFModelChange abortedChange) {
		LOGGER.trace("syncAborted: " + abortedChange.toString());
	}

	@Override
	public void syncAborted(TransformationAbortCause cause) {
		LOGGER.trace("syncAborted: " + cause.toString());
	}

	@Override
	protected MetaRepositoryImpl createMetaRepository() {
		return MIRTestUtil.createEmptyMetaRepository(
				createAttributeTUIDMetamodel("http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.pcm_mockup",
						"repository"),
				createAttributeTUIDMetamodel("http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.uml_mockup", "uml"));
	}

	@Override
	protected Class<?> getChange2CommandTransformerClass() {
		return PCM2UMLExecutor.class;
	}
}
