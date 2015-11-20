package edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.tests

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.ChangeDescription2ChangeConverter
import edu.kit.ipd.sdq.vitruvius.commandexecuter.CommandExecutingImpl
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.EclipseProjectHelper
import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.ChangePreparingImpl
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappingChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.ClaimableSingletonContainer
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Collections
import java.util.List
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.runners.model.FrameworkMethod

import static edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.MappingLanguageTestUtil.*

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants
import org.eclipse.core.runtime.Path

class MappingLanguageTestEnvironment implements SynchronisationListener {
	private final static Logger LOGGER = Logger.getLogger(MappingLanguageTestEnvironment)
	public static final String MODEL_PATH = "/model"

	private List<Change2CommandTransforming> c2cts

	@Accessors(PUBLIC_GETTER)
	@Inject private MappingLanguageTestUserInteracting userInteracting
	
	private val AbstractMappingTestBase mappingTest
	
	private MetaRepositoryImpl metaRepository
	private VSUMImpl vsum
	private ChangeSynchronizerImpl changeSynchronizer
	private ResourceSetImpl resourceSet
	private ChangeRecorder changeRecorder
	private ChangeDescription2ChangeConverter changeDescription2ChangeConverter
	
	private FrameworkMethod method
	private String projectName
	private String baseModelPath

	@Inject
	public new(AbstractMappingTestBase mappingTest, Change2CommandTransforming c2ct) {
		this.c2cts = #[c2ct]
		this.mappingTest = mappingTest
	}

	public def setup(FrameworkMethod method) {
		this.method = method
		projectName = method.declaringClass.name + "_" + method.name + "_" + LocalDateTime.now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
		baseModelPath = projectName + MODEL_PATH
		
		val eph = new EclipseProjectHelper(projectName)
		eph.reinitializeJavaProject
		eph.getProject().getFolder("model").create(true, true, null);

	    val change2CommandTransformingProvider = new MappingLanguageTestChange2CommandTransformingProvidingImpl();
        for (c2ct : c2cts) {
			c2ct.requireType(AbstractMappingChange2CommandTransforming).setUserInteracting(userInteracting)
			change2CommandTransformingProvider.addChange2CommandTransforming(c2ct)
        }

        this.metaRepository = createEmptyMetaRepository(mappingTest.metamodelURIsAndExtensions.map [
        	createAttributeTUIDMetamodel(first, second)
        ])
        this.vsum = TestUtil.createVSUM(this.metaRepository);
        val commandExecuter = new CommandExecutingImpl();
		val changePreparer = new ChangePreparingImpl(this.vsum, this.vsum);
        this.changeSynchronizer = new ChangeSynchronizerImpl(this.vsum, change2CommandTransformingProvider, this.vsum,
                this.metaRepository, this.vsum, this, changePreparer, commandExecuter);
        this.resourceSet = new ResourceSetImpl();
        this.changeRecorder = new ChangeRecorder();
        this.changeDescription2ChangeConverter = new ChangeDescription2ChangeConverter();
	}
	
	public def after() {
		val eph = new EclipseProjectHelper(VSUMConstants.VSUM_PROJECT_NAME)
		eph.move(projectName + "_" + VSUMConstants.VSUM_PROJECT_NAME)
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
	
	// FROM VitruviusEMFCaseStudyTest
	public def CorrespondenceInstance getCorrespondenceInstance() throws Throwable {
		if (c2cts.size > 1) {
			throw new IllegalStateException("cannot determine correspondence instance for multiple registered " + Change2CommandTransforming.name + " instances.")
		}
		
		return c2cts.map [ c2ct |
			val mms = c2ct.transformableMetamodels
			mms.map [ mm |
				vsum.getCorrespondenceInstanceOriginal(mm.first, mm.second)
			]
		].flatten.claimIdenticalElements
    }

    public def void triggerSynchronization(VURI vuri) {
        val cd = this.changeRecorder.endRecording();
        cd.applyAndReverse();
        val changes = this.changeDescription2ChangeConverter.getChanges(cd, vuri);
        cd.applyAndReverse();
        this.changeSynchronizer.synchronizeChanges(changes);
        this.changeRecorder.beginRecording(Collections.EMPTY_LIST);
    }

    public def void triggerSynchronization(EObject eObject) {
        val vuri = VURI.getInstance(eObject.eResource());
        this.triggerSynchronization(vuri);
    }

    public def void synchronizeFileChange(FileChangeKind fileChangeKind, VURI vuri) {
        val fileChange = new FileChange(fileChangeKind, vuri);
        this.changeSynchronizer.synchronizeChange(fileChange);
    }
	
	// Utility functions
	@SuppressWarnings("unchecked")
	public def <T extends EObject> T reloadByTUID(T eObject) {
		try {
			val tuid = getCorrespondenceInstance().calculateTUIDFromEObject(eObject);
			return getCorrespondenceInstance().resolveEObjectFromTUID(tuid) as T;
		} catch (Throwable e) {
			LOGGER.error(e);
		}
		
		return null;
	}
	
	public def URI createModelURI(String fileName) {
		return URI.createPlatformResourceURI(baseModelPath + "/" + fileName, false);
	}
	
	
	/**
	 * Creates a model, saves it and triggers synchronisation. The Path of the
	 * model is relative to {@link MODEL_PATH} (normally: MockupProject/model).
	 */
	public def <T extends EObject> T createManipulateSaveAndSyncModel(String modelPath, Supplier<T> manipulate)
			throws IOException {
		val resourcePath = baseModelPath + "/" + modelPath;

		val resourceVURI = VURI.getInstance(resourcePath);

		val result = manipulate.get();
		val resourceURI = URI.createPlatformResourceURI(resourcePath, false);
		val resource = EcoreResourceBridge.loadResourceAtURI(resourceURI, resourceSet);
		EcoreResourceBridge.saveEObjectAsOnlyContent(result, resource);

		this.synchronizeFileChange(FileChangeKind.CREATE, resourceVURI);

		return result;
	}

	public def EObject createAndSyncModelWithRootObject(String modelPath, EObject rootEObject) throws IOException {
		return createManipulateSaveAndSyncModel(modelPath, [rootEObject]);
	}

	public def <T extends EObject, R> R recordManipulateSaveAndSync(T input, Function<T, R> manipulate)
			throws IOException {
		changeRecorder.beginRecording(Collections.singletonList(input));
		
		vsum.detachTransactionalEditingDomain();
		
		val resultContainer = new ClaimableSingletonContainer(true); // is set inside the closure
		// TODO: should we create a command on the stack here
		// instead of detaching the transactional editing domain?
//		domain.getCommandStack().execute(new RecordingCommand(domain) {
//			@Override
//			protected void doExecute() {
		
				resultContainer.put(manipulate.apply(input));				

//			}
//		});
		EcoreResourceBridge.saveResource(input.eResource());
		triggerSynchronization(input);
		
		val result = resultContainer.claim();

		return result;
	}

	public def <T extends EObject> void recordManipulateSaveAndSync(T input, Consumer<T> manipulate) throws IOException {
		recordManipulateSaveAndSync(input, [
			manipulate.accept(it);
			return null;
		]);
	}
	
	/*protected static def void assertOCL(String message, EObject context, String oclPredicate) {
		OCL ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();

		OCLExpression<EClassifier> predicate = null;
		try {
			predicate = helper.createQuery(oclPredicate);
		} catch (ParserException e) {
			predicate = null;
			e.printStackTrace();
		}
		
		assertTrue(message, ocl.check(context, predicate));
	}*/
	
}