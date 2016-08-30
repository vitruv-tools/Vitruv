package edu.kit.ipd.sdq.vitruvius.tests;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.runner.Description;

import edu.kit.ipd.sdq.vitruvius.commandexecutor.CommandExecutingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.change.preparation.ChangePreparingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.change.recording.AtomicEMFChangeRecorder;
import edu.kit.ipd.sdq.vitruvius.framework.changesynchronizer.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

/**
 * Base class for all Vitruvius EMF case study tests
 *
 * @author langhamm
 *
 */

public abstract class VitruviusEMFCasestudyTest extends VitruviusCasestudyTest implements SynchronisationListener {

    protected VSUMImpl vsum;
    protected ChangeSynchronizerImpl changeSynchronizer;
    protected MetaRepositoryImpl metaRepository;
    protected AtomicEMFChangeRecorder changeRecorder;
    protected CorrespondenceModel correspondenceModel;
    protected Change2CommandTransformingProviding transformingProviding;
    
    /**
     * Initialize a VitruviusEMFCasestudyTest with the default {@link Supplier} for {@link Change2CommandTransformingProvidingImpl}.
     */
    public VitruviusEMFCasestudyTest() {
    	super();
    }
    
    /**
     * Initialize a VitruviusEMFCasestudyTest with the specified {@link Supplier} for {@link Change2CommandTransformingProviding}.
     */
    public VitruviusEMFCasestudyTest(Supplier<? extends Change2CommandTransformingProviding> change2CommandTransformingProvidingSupplier) {
    	super(change2CommandTransformingProvidingSupplier);
    }
    
    /**
     * Set up SyncMangaer and metaRepository facility. Creates a fresh VSUM, Metarepository etc.
     * before each test
     *
     * @throws Throwable
     */
    @Override
    public void beforeTest(final Description description) throws Throwable {
        super.beforeTest(description);

        this.metaRepository = this.createMetaRepository();
        this.vsum = TestUtil.createVSUM(this.metaRepository);
        final CommandExecuting commandExecuter = new CommandExecutingImpl();
        final ChangePreparing changePreparer = new ChangePreparingImpl(this.vsum);
        this.transformingProviding = syncTransformationProviderSupplier.get();
        this.changeSynchronizer = new ChangeSynchronizerImpl(this.vsum, this.transformingProviding,
                this.vsum, this, changePreparer, commandExecuter);
        this.testUserInteractor = new TestUserInteractor();
        this.setUserInteractor(this.testUserInteractor, this.transformingProviding);
        this.resourceSet = new ResourceSetImpl();
        this.changeRecorder = new AtomicEMFChangeRecorder();
    }
    
    protected void setUserInteractor(UserInteracting newUserInteracting) throws Throwable {
    	setUserInteractor(newUserInteracting, transformingProviding);
    }

    protected abstract MetaRepositoryImpl createMetaRepository();

    @Override
    protected void afterTest(final org.junit.runner.Description description) {
        this.correspondenceModel = null;
    }

    @Override
    protected CorrespondenceModel getCorrespondenceModel() throws Throwable {
        final Metamodel firstMM = this.metaRepository.getAllMetamodels()[0];
        final Mapping mapping = this.metaRepository.getAllMappings(firstMM).iterator().next();
        final VURI mm1VURI = mapping.getMetamodelA().getURI();
        final VURI mm2VURI = mapping.getMetamodelB().getURI();
        return this.vsum.getCorrespondenceModelOriginal(mm1VURI, mm2VURI);
    }

    protected void triggerSynchronization(final VURI vuri) {
        final List<EMFModelChange> changes = this.changeRecorder.endRecording();
        CompositeChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
        this.changeSynchronizer.synchronizeChange(compositeChange);
        this.changeRecorder.beginRecording(vuri, Collections.emptyList());
    }

    protected void triggerSynchronization(final EObject eObject) {
        final VURI vuri = VURI.getInstance(eObject.eResource());
        this.triggerSynchronization(vuri);
    }

    protected void synchronizeFileChange(final FileChangeKind fileChangeKind, final VURI vuri) {
    	Resource modelResource = this.vsum.getAndLoadModelInstanceOriginal(vuri).getResource();
        final FileChange fileChange = VitruviusChangeFactory.getInstance().createFileChange(fileChangeKind, modelResource);
        this.changeSynchronizer.synchronizeChange(fileChange);
    }

    @Override
    public void syncStarted() {

    }

    @Override
    public void syncFinished() {

    }

    @Override
    public void syncAborted(final TransformationAbortCause cause) {

    }

}
