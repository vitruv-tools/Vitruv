package edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.tests

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.ChangeDescription2ChangeConverter
import edu.kit.ipd.sdq.vitruvius.commandexecuter.CommandExecutingImpl
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.EclipseProjectHelper
import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.ChangePreparingImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappingChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRUserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*
import org.apache.log4j.Logger

class VitruviusTestEnv implements SynchronisationListener {
	private final static Logger LOGGER = Logger.getLogger(VitruviusTestEnv)
	protected static final String PROJECT_FOLDER_NAME = "MockupProject"

	private List<Change2CommandTransforming> c2cts

	@Inject private MIRUserInteracting userInteracting
	
	private MetaRepositoryImpl metaRepository
	private VSUMImpl vsum
	private ChangeSynchronizerImpl changeSynchronizer
	private ResourceSetImpl resourceSet
	private ChangeRecorder changeRecorder
	private ChangeDescription2ChangeConverter changeDescrition2ChangeConverter

	public new(Change2CommandTransforming... c2cts) {
		this.c2cts = new ArrayList(c2cts)
		setup
	}

	public new(List<Change2CommandTransforming> c2cts) {
		this.c2cts = new ArrayList(c2cts)
		setup
	}

	protected def setup() {
		val eph = new EclipseProjectHelper(PROJECT_FOLDER_NAME)
		eph.reinitializeJavaProject
		
		/*TestUtil.initializeLogger();
		TestUtil.deleteAllProjectFolderCopies(PROJECT_FOLDER_NAME);
		TestUtil.clearMetaProject();*/

		c2cts.forEach[it.requireType(AbstractMappingChange2CommandTransforming).setUserInteracting(userInteracting)]

        this.metaRepository = new MetaRepositoryImpl
        this.vsum = TestUtil.createVSUM(this.metaRepository);
        val change2CommandTransformingProvider = new MappingLanguageTestChange2CommandTransformingProvidingImpl();
        val commandExecuter = new CommandExecutingImpl();
		val changePreparer = new ChangePreparingImpl(this.vsum, this.vsum);
        this.changeSynchronizer = new ChangeSynchronizerImpl(this.vsum, change2CommandTransformingProvider, this.vsum,
                this.metaRepository, this.vsum, this, changePreparer, commandExecuter);
        this.resourceSet = new ResourceSetImpl();
        this.changeRecorder = new ChangeRecorder();
        this.changeDescrition2ChangeConverter = new ChangeDescription2ChangeConverter();
	}
	
	override syncAborted(EMFModelChange abortedChange) {
		LOGGER.trace("sync aborted: " + abortedChange.toString)
	}
	
	override syncAborted(TransformationAbortCause cause) {
		LOGGER.trace("sync aborted: " + cause.toString)
	}
	
	override syncFinished() {
		LOGGER.trace("sync finished")
	}
	
	override syncStarted() {
		LOGGER.trace("sync started")
	}
	
}