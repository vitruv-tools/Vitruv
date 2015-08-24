package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.executortest;

import static edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.MIRTestUtil.createAttributeTUIDMetamodel;

import java.io.IOException;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Before;
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

public class CreateBasicComponentTest extends VitruviusEMFCasestudyTest implements SynchronisationListener {
	private static final Logger LOGGER = Logger.getLogger(CreateBasicComponentTest.class);
	private static final String MODEL_PATH = TestUtil.PROJECT_URI + "/model";
	
	private <T> T createManipulateSaveAndSyncResource(String resourcePath, Function<Resource, T> manipulate) throws IOException {
        final VURI resourceVURI = VURI.getInstance(resourcePath);
        final Resource resource = resourceSet.createResource(resourceVURI.getEMFUri());
        
        T result = manipulate.apply(resource);
        
        EcoreResourceBridge.saveResource(resource);
        this.synchronizeFileChange(FileChangeKind.CREATE, resourceVURI);
        this.triggerSynchronization(resourceVURI);
        
        return result;
	}
	
	private Resource createAndSyncResourceWithRootObject(String resourcePath, EObject rootEObject) throws IOException {
		return createManipulateSaveAndSyncResource(resourcePath,
			res -> {
				res.getContents().add(rootEObject);
				return res;
			});
	}
	
	private Resource createSaveAndSyncResource(String resourcePath) throws IOException {
		return createManipulateSaveAndSyncResource(resourcePath, Function.identity());
	}
	
	private <T extends EObject> void recordManipulateSaveAndSync(T input, Consumer<T> manipulate) throws IOException {
		changeRecorder.beginRecording(Collections.singletonList(input));
		manipulate.accept(input);
		EcoreResourceBridge.saveResource(input.eResource());
	}
	
	private Package createPackage(String name) {
		Package pkg = UMLFactory.eINSTANCE.createPackage();
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
	public void createSimpleUML() throws IOException {
		LOGGER.trace("Starting first change");		

		Package pkg = createPackage("FirstPackageName");
		createAndSyncResourceWithRootObject(MODEL_PATH + "/uml1.uml", pkg);		
		LOGGER.trace("Finished first change");
		
		LOGGER.trace("Starting second change");
		recordManipulateSaveAndSync(pkg,
			it -> { it.setName("SecondPackageName"); });
		LOGGER.trace("Finished second change");
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
