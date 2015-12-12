package edu.kit.ipd.sdq.vitruvius.tests;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.runner.Description;

import edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.ChangeDescription2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.commandexecuter.CommandExecutingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.ChangePreparingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl;
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
    protected ChangeRecorder changeRecorder;
    private ChangeDescription2ChangeConverter changeDescrition2ChangeConverter;
    protected CorrespondenceInstance correspondenceInstance;

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
        final Change2CommandTransformingProviding syncTransformationProvider = new Change2CommandTransformingProvidingImpl();
        final CommandExecuting commandExecuter = new CommandExecutingImpl();
        final ChangePreparing changePreparer = new ChangePreparingImpl(this.vsum, this.vsum);
        this.changeSynchronizer = new ChangeSynchronizerImpl(this.vsum, syncTransformationProvider, this.vsum,
                this.metaRepository, this.vsum, this, changePreparer, commandExecuter);
        this.testUserInteractor = new TestUserInteractor();
        this.setUserInteractor(this.testUserInteractor, this.changeSynchronizer);
        this.resourceSet = new ResourceSetImpl();
        this.changeRecorder = new ChangeRecorder();
        this.changeDescrition2ChangeConverter = new ChangeDescription2ChangeConverter();
    }

    protected abstract MetaRepositoryImpl createMetaRepository();

    @Override
    protected void afterTest(final org.junit.runner.Description description) {
        this.correspondenceInstance = null;
    }

    @Override
    protected CorrespondenceInstance getCorrespondenceInstance() throws Throwable {
        final Metamodel firstMM = this.metaRepository.getAllMetamodels()[0];
        final Mapping mapping = this.metaRepository.getAllMappings(firstMM).iterator().next();
        final VURI mm1VURI = mapping.getMetamodelA().getURI();
        final VURI mm2VURI = mapping.getMetamodelB().getURI();
        return this.vsum.getCorrespondenceInstanceOriginal(mm1VURI, mm2VURI);
    }

    protected void triggerSynchronization(final VURI vuri) {
        final ChangeDescription cd = this.changeRecorder.endRecording();
        cd.applyAndReverse();
        final List<Change> changes = this.changeDescrition2ChangeConverter.getChanges(cd, vuri);
        cd.applyAndReverse();
        this.changeSynchronizer.synchronizeChanges(changes);
        this.changeRecorder.beginRecording(Collections.EMPTY_LIST);
    }

    protected void triggerSynchronization(final EObject eObject) {
        final VURI vuri = VURI.getInstance(eObject.eResource());
        this.triggerSynchronization(vuri);
    }

    protected void synchronizeFileChange(final FileChangeKind fileChangeKind, final VURI vuri) {
        final FileChange fileChange = new FileChange(fileChangeKind, vuri);
        this.changeSynchronizer.synchronizeChange(fileChange);
    }

    @Override
    public void syncStarted() {

    }

    @Override
    public void syncFinished() {

    }

    @Override
    public void syncAborted(final EMFModelChange abortedChange) {

    }

    @Override
    public void syncAborted(final TransformationAbortCause cause) {

    }

}
