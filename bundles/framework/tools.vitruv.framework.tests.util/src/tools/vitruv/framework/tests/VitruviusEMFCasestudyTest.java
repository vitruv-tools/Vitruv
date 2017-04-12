package tools.vitruv.framework.tests;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.description.CompositeContainerChange;
import tools.vitruv.framework.change.description.ConcreteChange;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.change.description.VitruviusChangeFactory.FileChangeKind;
import tools.vitruv.framework.change.recording.AtomicEMFChangeRecorder;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.framework.util.datatypes.VURI;

/**
 * Base class for all Vitruvius EMF case study tests
 *
 * @author langhamm
 *
 */

public abstract class VitruviusEMFCasestudyTest extends VitruviusCasestudyTest {

	protected AtomicEMFChangeRecorder changeRecorder;

	@Override
	public void beforeTest() throws Throwable {
		super.beforeTest();
		this.changeRecorder = new AtomicEMFChangeRecorder();
	}

	@Override
	public void afterTest() {
		if (changeRecorder.isRecording()) {
			changeRecorder.endRecording();
		}
	}

	protected void triggerSynchronization(final VURI vuri) {
		final List<TransactionalChange> changes = this.changeRecorder.endRecording();
		CompositeContainerChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
		this.getVirtualModel().propagateChange(compositeChange);
		this.changeRecorder.beginRecording(vuri, Collections.emptyList());
	}

	protected void triggerSynchronization(final EObject eObject) {
		final VURI vuri = VURI.getInstance(eObject.eResource());
		this.triggerSynchronization(vuri);
	}

	protected void synchronizeFileChange(final FileChangeKind fileChangeKind, final VURI vuri) {
		Resource modelResource = this.getVirtualModel().getModelInstance(vuri).getResource();
		final ConcreteChange fileChange = VitruviusChangeFactory.getInstance().createFileChange(fileChangeKind,
				modelResource);
		this.getVirtualModel().propagateChange(fileChange);
	}

	protected void saveAndSynchronizeChanges(EObject object) throws IOException {
		EcoreResourceBridge.saveResource(object.eResource());
		this.triggerSynchronization(VURI.getInstance(object.eResource()));
	}

	protected void createAndSynchronizeModel(String modelPathInProject, EObject rootElement) throws IOException {
		if (StringUtils.isEmpty(modelPathInProject) || rootElement == null) {
			throw new IllegalArgumentException();
		}
		Resource resource = createModelResource(modelPathInProject);
		EcoreResourceBridge.saveResource(resource);
		this.changeRecorder.beginRecording(VURI.getInstance(resource), Collections.singleton(resource));
		resource.getContents().add(rootElement);
		saveAndSynchronizeChanges(rootElement);
	}
	
	protected void deleteAndSynchronizeModel(String modelPathInProject) throws IOException {
		if (StringUtils.isEmpty(modelPathInProject)) {
			throw new IllegalArgumentException();
		}
		Resource resource = getModelResource(modelPathInProject);
		VURI vuri = VURI.getInstance(resource);
		resource.delete(Collections.EMPTY_MAP);
		triggerSynchronization(vuri);
	}
	

}
