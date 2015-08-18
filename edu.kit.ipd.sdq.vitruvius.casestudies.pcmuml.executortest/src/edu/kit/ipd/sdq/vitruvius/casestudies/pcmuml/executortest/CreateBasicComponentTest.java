package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.executortest;

import static edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.MIRTestUtil.createAttributeTUIDMetamodel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.ChangeDescription2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.mir.generated.modified.PCM2UMLExecutor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause;
import edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.MIRTestUtil;
import edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine.EMFModelPropagationEngineImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager.SyncManagerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class CreateBasicComponentTest implements SynchronisationListener {
	private static final Logger LOGGER = Logger.getLogger(CreateBasicComponentTest.class);
	
	private ChangeRecorder changeRecorder;
	private ResourceSetImpl resourceSet;
	private ChangeDescription2ChangeConverter changeDescription2ChangeConverter;
	private VSUMImpl vsum;
	private ChangeSynchronizing syncManager;

	
	private Resource getResource(String resourcePath) {
        final VURI repoVURI = VURI.getInstance(resourcePath);
        final Resource resource = resourceSet.createResource(repoVURI.getEMFUri());
        
        return resource;
	}

	@Before
	public void setUpTest() {
		// reset log4j
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		PropertyConfigurator.configure("log4j.properties");
		Logger.getRootLogger().setLevel(Level.ALL);
		
		this.changeRecorder = new ChangeRecorder();
		this.resourceSet = new ResourceSetImpl();
		this.changeDescription2ChangeConverter = new ChangeDescription2ChangeConverter();
		
		this.vsum = MIRTestUtil.createEmptyVSUM(
			createAttributeTUIDMetamodel("http://palladiosimulator.org/PalladioComponentModel/Repository/5.1", "repository"),
			createAttributeTUIDMetamodel("http://www.eclipse.org/uml2/5.0.0/UML", "uml"));
		
		TransformationExecutingProvidingImplCustom transformationProvider = new TransformationExecutingProvidingImplCustom();
        //transformationProvider.addEMFModelTransformationExecuting(new PCM2UMLExecutor());
        
        EMFModelPropagationEngineImpl propagationEngine = new EMFModelPropagationEngineImpl(
        		transformationProvider);
        
        this.syncManager = new SyncManagerImpl(vsum, propagationEngine, vsum, null, vsum, this);
	}
	
	@Test
	public void createSimpleUML() throws IOException {
		final Resource resource = getResource("testproject/models/uml1.uml");
		resource.getContents().clear();
		EcoreResourceBridge.saveResource(resource);
		
		LOGGER.trace("Starting first change");		
		changeRecorder.beginRecording(Collections.singletonList(resource));
		Package pkg = UMLFactory.eINSTANCE.createPackage();
		resource.getContents().add(pkg);
		pkg.setName("FirstPackageName");
		EcoreResourceBridge.saveResource(resource);
		final ChangeDescription cd = changeRecorder.endRecording();
		cd.applyAndReverse();
		final List<Change> changes = changeDescription2ChangeConverter.getChanges(cd, VURI.getInstance(resource));
		cd.applyAndReverse();
		syncManager.synchronizeChanges(changes);
		
		LOGGER.trace("Starting second change");
		changeRecorder.beginRecording(Collections.singletonList(resource));
		pkg.setName("SecondPackageName");
		EcoreResourceBridge.saveResource(resource);
		final ChangeDescription cd2 = changeRecorder.endRecording();
		cd2.applyAndReverse();
		final List<Change> changes2 = changeDescription2ChangeConverter.getChanges(cd2, VURI.getInstance(resource));
		cd2.applyAndReverse();
		syncManager.synchronizeChanges(changes2);
	}
	
	@Test
	@Ignore
	public void changePacketName() throws IOException {
		final Resource resource = getResource("testproject/models/uml1.uml");
		EcoreResourceBridge.saveResource(resource);
		
		Package pkg = UMLFactory.eINSTANCE.createPackage();
		resource.getContents().add(pkg);
		pkg.setName("ABC");
		EcoreResourceBridge.saveResource(resource);

		
		changeRecorder.beginRecording(Collections.singletonList(resource));
		org.eclipse.uml2.uml.Interface iface = pkg.createOwnedInterface("TestInterface");
		iface.setIsAbstract(true);
		ChangeDescription cd = changeRecorder.endRecording();
		
		cd.applyAndReverse();
		List<Change> changes = changeDescription2ChangeConverter.getChanges(cd, VURI.getInstance(resource));
		cd.applyAndReverse();
		syncManager.synchronizeChanges(changes);
		
		changeRecorder.beginRecording(Collections.singletonList(resource));
		pkg.setName("DEF");
		pkg.setName("GHI");
		cd = changeRecorder.endRecording();
		
		cd.applyAndReverse();
		changes = changeDescription2ChangeConverter.getChanges(cd, VURI.getInstance(resource));
		cd.applyAndReverse();
		syncManager.synchronizeChanges(changes);
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
		LOGGER.trace("syncAborted 1");		
	}

	@Override
	public void syncAborted(TransformationAbortCause cause) {
		LOGGER.trace("syncAborted 2");		
	}
}
