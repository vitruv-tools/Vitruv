package tools.vitruv.framework.tests;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.description.CompositeContainerChange;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.framework.util.datatypes.VURI;

/**
 * Basic test class for all Vitruvius application tests that require a test
 * project and a VSUM project within the test workspace and use a monitor for
 * recording changes of models. The class creates a test project and a VSUM for
 * each test case within the workspace of the Eclipse test instance.
 * 
 * It provides several methods for handling models and their resources, as well
 * as for recording changes, triggering their synchronization and creating and
 * deleting synchronized models.
 *
 * @author langhamm
 * @author Heiko Klare
 *
 */

public abstract class VitruviusApplicationTest extends VitruviusUnmonitoredApplicationTest {

	private AtomicEmfChangeRecorder changeRecorder;

	@Override
	public final void beforeTest() {
		super.beforeTest();
		this.changeRecorder = new AtomicEmfChangeRecorder(true);
		setup();
	}

	@Override
	public final void afterTest() {
		if (changeRecorder.isRecording()) {
			changeRecorder.endRecording();
		}
		cleanup();
	}

	/**
	 * This method gets called at the beginning of each test case, after the
	 * test project and VSUM have been initialized. It can be used, for example,
	 * to initialize the test models.
	 */
	protected abstract void setup();

	/**
	 * This method gets called at the end of each test case. It can be used for
	 * clean up actions.
	 */
	protected abstract void cleanup();

	private void propagateChanges(final VURI vuri) {
		final List<TransactionalChange> changes = this.changeRecorder.endRecording();
		CompositeContainerChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
		this.getVirtualModel().propagateChange(compositeChange);
	}

	private void startRecordingChanges(Resource resource) {
		VURI vuri = VURI.getInstance(resource);
		this.changeRecorder.beginRecording(vuri, Collections.singleton(resource));
	}

	/**
	 * Starts recording changes for the model of the given {@link EObject}
	 * 
	 * @param object
	 *            the {@link EObject} to record changes for
	 */
	protected void startRecordingChanges(EObject object) {
		startRecordingChanges(object.eResource());
	}

	/**
	 * The model containing the given {@link EObject} gets saved and changes
	 * that were recorded for that model get propagated. Recording is restarted
	 * afterwards.
	 * 
	 * @param object
	 *            the {@link EObject} to save and propagated recorded changes
	 *            for
	 * @throws IOException
	 */
	protected void saveAndSynchronizeChanges(EObject object) throws IOException {
		Resource resource = object.eResource();
		EcoreResourceBridge.saveResource(resource);
		this.propagateChanges(VURI.getInstance(resource));
		this.startRecordingChanges(resource);
	}

	/**
	 * A model with the given root element at the given path within the test
	 * project gets created. The changes for the insertion of the root element
	 * are propagated and recording of further changes is started. No call to
	 * {@link #startRecordingChanges(EObject)} is necessary.
	 * 
	 * @param modelPathInProject
	 *            path within project to persist the model at
	 * @param rootElement
	 *            root element to persist
	 * @throws IOException
	 */
	protected void createAndSynchronizeModel(String modelPathInProject, EObject rootElement) throws IOException {
		if (StringUtils.isEmpty(modelPathInProject) || rootElement == null) {
			throw new IllegalArgumentException();
		}
		Resource resource = createModelResource(modelPathInProject);
		this.startRecordingChanges(resource);
		resource.getContents().add(rootElement);
		saveAndSynchronizeChanges(rootElement);
	}

	/**
	 * The model at the given path is deleted. The old root element is removed
	 * and changes for that removal are propagated.
	 * 
	 * @param modelPathInProject
	 *            path within project to remove model from
	 * @throws IOException
	 */
	protected void deleteAndSynchronizeModel(String modelPathInProject) throws IOException {
		if (StringUtils.isEmpty(modelPathInProject)) {
			throw new IllegalArgumentException();
		}
		Resource resource = getModelResource(modelPathInProject);
		VURI vuri = VURI.getInstance(resource);
		resource.delete(Collections.EMPTY_MAP);
		propagateChanges(vuri);
	}

}
