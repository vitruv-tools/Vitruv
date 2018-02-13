package tools.vitruv.testutils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.commons.util.java.lang.StringUtil;
import tools.vitruv.framework.change.description.CompositeContainerChange;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.change.recording.AtomicEmfChangeRecorder;
import tools.vitruv.framework.util.bridges.EcoreResourceBridge;
import tools.vitruv.framework.vsum.PropagatedChangeListener;

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

	//############################ ChangeVisualization
	/**
	 * A list of ChangeListeners that are informed of all changes made
	 */
	private static Set<PropagatedChangeListener> changeListeners=new HashSet<PropagatedChangeListener>();

	/**
	 * Registers a given {@link PropagatedChangeListener}.
	 * 
	 * @param changeListener The listener to register
	 */
	public static void addChangeListener(PropagatedChangeListener changeListener) {
		changeListeners.add(changeListener);		
	}

	/**
	 * Removes a given ChangeListener. Does nothing if the listener is not registered.
	 * 
	 * @param changeListener The listener to remove
	 */
	public static void removeChangeListener(PropagatedChangeListener changeListener) {
		changeListeners.remove(changeListener);
	}	

	/**
	 * This method informs all registered {@link PropagatedChangeListener}s of changes made.
	 * 
	 * @param propagationResult The changes made
	 */
	private static void informChangeListeners(List<PropagatedChange> propagationResult) {
		String testName=extractTestName();	
		for(PropagatedChangeListener cl:changeListeners) {
			cl.postChanges(testName,propagationResult);
		}		
	}

	/**
	 * Until a better way to get the junit test name is known, we derive the test name from the stack trace
	 * assuming the test method has a reproduceable position. Please do not consider this method during code review
	 * as it will for sure be altered soon and get its javadoc updated to not have this hint.
	 * 
	 * @return The extracted TestName
	 */
	private static String extractTestName() {
		//			0:getStackTrace
		//			1:extractTestName
		//			2:propagateChanges
		//			3:saveAndSynchronizeChanges
		//			4:saveAndSynchronizeChanges
		//			5:createAndSynchronizeModel
		//			6:createAndSyncSystem
		//			7:testRenameSystem
		//			8:invoke0
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		if(trace==null||trace.length<4) return "should never happen";//This should never happen
		String last=null;
		for(int n=0;n<trace.length;n++) {
			//System.out.println(n+":"+trace[n].getMethodName());
			String methodName=trace[n].getMethodName();
			//If the name convention is fullfilled, the first method named testXYZ is the tested method
			if(methodName.startsWith("test")) {
				return methodName;
			}
			//If not, then the last method before the first method starting with invoke is considered to be the test method
			if(last!=null&&methodName.startsWith("invoke")) {
				return last;
			}
			//Save the actual methodName
			last=methodName;
		}
		return "unknown";
	}	
	//############################  ChangeVisualization

	private AtomicEmfChangeRecorder changeRecorder;

	@Override
	public final void beforeTest() {
		super.beforeTest();
		this.changeRecorder = new AtomicEmfChangeRecorder(getLocalUuidGeneratorAndResolver());
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

	private List<PropagatedChange> propagateChanges() {
		this.changeRecorder.endRecording();
		final List<TransactionalChange> changes = changeRecorder.getChanges();
		CompositeContainerChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
		List<PropagatedChange> propagationResult = this.getVirtualModel().propagateChange(compositeChange);

		//###############  ChangeVisualization
		informChangeListeners(propagationResult);
		//###############  ChangeVisualization

		this.changeRecorder.beginRecording();
		return propagationResult;
	}

	private void startRecordingChanges(Resource resource) {
		this.changeRecorder.addToRecording(resource);
		if (!changeRecorder.isRecording()) {
			this.changeRecorder.beginRecording();
		}
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
	 * @return a list with the {@link PropagatedChange}s, containing the original and consequential changes
	 * @throws IOException
	 */
	protected List<PropagatedChange> saveAndSynchronizeChanges(EObject object) throws IOException {
		Resource resource = object.eResource();
		return saveAndSynchronizeChanges(resource);
	}

	protected  List<PropagatedChange> saveAndSynchronizeChanges(Resource resource) throws IOException {
		EcoreResourceBridge.saveResource(resource);
		List<PropagatedChange> result = this.propagateChanges();
		this.startRecordingChanges(resource);
		return result;
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
		if (StringUtil.isEmpty(modelPathInProject) || rootElement == null) {
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
	protected List<PropagatedChange> deleteAndSynchronizeModel(String modelPathInProject) throws IOException {
		if (StringUtil.isEmpty(modelPathInProject)) {
			throw new IllegalArgumentException();
		}
		Resource resource = getModelResource(modelPathInProject);
		resource.delete(Collections.EMPTY_MAP);
		List<PropagatedChange> changes = propagateChanges();
		this.changeRecorder.removeFromRecording(resource);
		return changes;
	}

	protected Resource resourceAt(String modelPathInProject) {
		try {
			final ResourceSet resourceSet = new ResourceSetImpl();
			return getModelResource(modelPathInProject, resourceSet);
		} catch (RuntimeException e) {
			if (e.getCause() instanceof FileNotFoundException) {
				return createModelResource(modelPathInProject);
			}
			throw e;
		}
	}

	protected <T> T from(Class<T> clazz, String modelPathInProject) {
		final Resource requestedResource = getModelResource(modelPathInProject);
		startRecordingChanges(requestedResource);
		return clazz.cast(requestedResource.getContents().get(0));
	}
}
