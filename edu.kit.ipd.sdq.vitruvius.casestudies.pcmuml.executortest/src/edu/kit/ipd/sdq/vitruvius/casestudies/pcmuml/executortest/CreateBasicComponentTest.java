package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.executortest;

import static edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.MIRTestUtil.createAttributeTUIDMetamodel;

import java.io.IOException;
import java.util.Collections;
import java.util.function.Consumer;
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
	
	private <T extends EObject> T createManipulateSaveAndSyncResource(String resourcePath, Supplier<T> manipulate) throws IOException {
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
	
	private <T extends EObject> void recordManipulateSaveAndSync(T input, Consumer<T> manipulate) throws IOException {
		changeRecorder.beginRecording(Collections.singletonList(input));
		manipulate.accept(input);
		EcoreResourceBridge.saveResource(input.eResource());
		this.triggerSynchronization(input);
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

	@Ignore
	@Test
	public void createPackage() throws IOException {
		LOGGER.trace("Starting: createPackage");		
		UPackage pkg = createPackage("FirstPackageName1");
		createAndSyncResourceWithRootObject(MODEL_PATH + "/uml1.uml", pkg);		
		LOGGER.trace("Finished: createPackage");		
	}
	
	@Test
	public void createPackageAndRename() throws IOException {
		LOGGER.trace("Starting: createPackageAndRename");		
		LOGGER.trace("creating Package");
		UPackage pkg = createPackage("FirstPackageName2");
		createAndSyncResourceWithRootObject(MODEL_PATH + "/uml2.uml", pkg);		
		
		LOGGER.trace("Renaming Package");
		recordManipulateSaveAndSync(pkg, it -> { it.setName("SecondPackageName2"); });
		LOGGER.trace("Finished: createPackageAndRename");
	}
	
	@Ignore
	@Test
	public void createPackageAndNestedInterface() throws IOException {
		LOGGER.trace("Starting: createPackageAndNestedInterface");		
		LOGGER.trace("creating Package");
		UPackage pkg = createPackage("FirstPackageName3");
		createAndSyncResourceWithRootObject(MODEL_PATH + "/uml3.uml", pkg);		
		recordManipulateSaveAndSync(pkg, it -> {
			UClass clazz = Uml_mockupFactory.eINSTANCE.createUClass();
			clazz.setName("TestNestedClass");
			it.getClasses().add(clazz);
		});
		LOGGER.trace("Finished: createPackageAndNestedInterface");
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
				createAttributeTUIDMetamodel("http://palladiosimulator.org/PalladioComponentModel/Repository/5.1", "repository"),
				createAttributeTUIDMetamodel("http://www.eclipse.org/uml2/5.0.0/UML", "uml"));
	}

	@Override
	protected Class<?> getChange2CommandTransformerClass() {
		return PCM2UMLExecutor.class;
	}
}
