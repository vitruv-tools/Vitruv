package tools.vitruv.framework.tests;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.description.CompositeContainerChange;
import tools.vitruv.framework.change.description.ConcreteChange;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.change.description.VitruviusChangeFactory.FileChangeKind;
import tools.vitruv.framework.change.recording.AtomicEMFChangeRecorder;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.modelsynchronization.ChangePropagationListener;
import tools.vitruv.framework.modelsynchronization.ChangePropagationAbortCause;
import tools.vitruv.framework.util.datatypes.VURI;

/**
 * Base class for all Vitruvius EMF case study tests
 *
 * @author langhamm
 *
 */

public abstract class VitruviusEMFCasestudyTest extends VitruviusCasestudyTest implements ChangePropagationListener {

	protected AtomicEMFChangeRecorder changeRecorder;

	/**
	 * Set up SyncMangaer and metaRepository facility. Creates a fresh VSUM,
	 * Metarepository etc. before each test
	 *
	 * @throws Throwable
	 */
	@Override
	public void beforeTest() throws Throwable {
		super.beforeTest();
		this.changeRecorder = new AtomicEMFChangeRecorder();
	}

	protected abstract List<Metamodel> createMetamodels();

	@Override
	public void afterTest() {
		if (changeRecorder.isRecording()) {
			changeRecorder.endRecording();
		}
	}

	@Override
	protected CorrespondenceModel getCorrespondenceModel() throws Throwable {
		// TODO HK Implement correctly
		Iterator<Metamodel> it = metamodels.iterator();
		return this.getVirtualModel().getCorrespondenceModel(it.next().getURI(), it.next().getURI());
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

	@Override
	public void startedChangePropagation() {

	}

	@Override
	public void finishedChangePropagation() {

	}

	@Override
	public void abortedChangePropagation(final ChangePropagationAbortCause cause) {

	}

}
