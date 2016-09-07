//package tools.vitruvius.dsls.mapping.testframework.tests
//
//import com.google.inject.Inject
//import tools.vitruvius.framework.modelsynchronization.commandexecution.CommandExecutingImpl
//import tools.vitruvius.dsls.mapping.testframework.util.ClaimableSingletonContainer
//import tools.vitruvius.dsls.mapping.util.EclipseProjectHelper
//import tools.vitruvius.framework.changepreparer.ChangePreparingImpl
//import tools.vitruvius.framework.change.description.GeneralChange
//import tools.vitruvius.framework.util.datatypes.VURI
//import tools.vitruvius.framework.change.processing.Change2CommandTransforming
//import tools.vitruvius.framework.modelsynchronization.SynchronisationListener
//import tools.vitruvius.framework.modelsynchronization.TransformationAbortCause
//import tools.vitruvius.framework.metarepository.MetaRepositoryImpl
//import tools.vitruvius.framework.changeynchronizer.ChangeSynchronizerImpl
//import tools.vitruvius.framework.util.bridges.EcoreResourceBridge
//import tools.vitruvius.framework.vsum.VSUMConstants
//import tools.vitruvius.framework.vsum.VSUMImpl
//import tools.vitruvius.tests.util.TestUtil
//import java.io.IOException
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//import java.util.Collections
//import java.util.List
//import java.util.function.Supplier
//import org.apache.log4j.Logger
//import org.eclipse.emf.common.util.URI
//import org.eclipse.emf.ecore.EObject
//import org.eclipse.emf.ecore.change.util.ChangeRecorder
//import org.eclipse.emf.ecore.resource.Resource
//import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
//import org.junit.runners.model.FrameworkMethod
//
//import static tools.vitruvius.dsls.mapping.testframework.util.MappingLanguageTestUtil.*
//
//import static extension tools.vitruvius.framework.util.bridges.JavaHelper.*
//import tools.vitruvius.framework.correspondence.CorrespondenceModel
//
//class MappingLanguageTestEnvironment implements SynchronisationListener {
//	private final static Logger LOGGER = Logger.getLogger(MappingLanguageTestEnvironment)
//	public static final String MODEL_PATH = "/model"
//
//	private List<Change2CommandTransforming> c2cts
//
//	private val AbstractMappingTestBase mappingTest
//	
//	private MetaRepositoryImpl metaRepository
//	private VSUMImpl vsum
//	private ChangeSynchronizerImpl changeSynchronizer
//	private ResourceSetImpl resourceSet
//	private ChangeRecorder changeRecorder
//	private ChangeDescription2ChangeConverter changeDescription2ChangeConverter
//	
//	private FrameworkMethod method
//	private String projectName
//	private String baseModelPath
//
//	@Inject
//	public new(AbstractMappingTestBase mappingTest, Change2CommandTransforming c2ct) {
//		this.c2cts = #[c2ct]
//		this.mappingTest = mappingTest
//	}
//
//	public def setup(FrameworkMethod method) {
//		this.method = method
//		projectName = method.declaringClass.name + "_" + method.name + "_" + LocalDateTime.now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
//		baseModelPath = projectName + MODEL_PATH
//		
//		val eph = new EclipseProjectHelper(projectName)
//		eph.reinitializeJavaProject
//		eph.getProject().getFolder("model").create(true, true, null);
//
//	    val change2CommandTransformingProvider = new MappingLanguageTestChange2CommandTransformingProvidingImpl();
//        for (c2ct : c2cts) {
//			change2CommandTransformingProvider.addChange2CommandTransforming(c2ct)
//        }
//
//        this.metaRepository = createEmptyMetaRepository(mappingTest.metamodelURIsAndExtensions.map [
//        	createAttributeTUIDMetamodel(first, second)
//        ])
//        this.vsum = TestUtil.createVSUM(this.metaRepository);
//        val commandExecuter = new CommandExecutingImpl();
//		val changePreparer = new ChangePreparingImpl(this.vsum, this.vsum);
//        this.changeSynchronizer = new ChangeSynchronizerImpl(this.vsum, change2CommandTransformingProvider, this.vsum,
//                this.metaRepository, this.vsum, this, changePreparer, commandExecuter);
//        this.resourceSet = new ResourceSetImpl();
//        this.changeRecorder = new ChangeRecorder();
//        this.changeDescription2ChangeConverter = new ChangeDescription2ChangeConverter();
//	}
//	
//	public def after() {
//		val eph = new EclipseProjectHelper(VSUMConstants.VSUM_PROJECT_NAME)
//		eph.move(projectName + "_" + VSUMConstants.VSUM_PROJECT_NAME)
//	}
//	
//	override syncAborted(EMFModelChange abortedChange) {
//		LOGGER.trace("sync aborted: " + abortedChange.toString)
//	}
//	
//	override syncAborted(TransformationAbortCause cause) {
//		LOGGER.trace("sync aborted: " + cause.toString)
//	}
//	
//	override syncFinished() {
//		LOGGER.trace("sync finished")
//	}
//	
//	override syncStarted() {
//		LOGGER.trace("sync started")
//	}
//	
//	// FROM VitruviusEMFCaseStudyTest
//	public def CorrespondenceModel getCorrespondenceModel() throws Throwable {
//		if (c2cts.size > 1) {
//			throw new IllegalStateException("cannot determine correspondence instance for multiple registered " + Change2CommandTransforming.name + " instances.")
//		}
//		
//		return c2cts.map [ c2ct |
//			val mms = c2ct.transformableMetamodels
//			mms.map [ mm |
//				vsum.getCorrespondenceModelOriginal(mm.first, mm.second)
//			]
//		].flatten.claimIdenticalElements
//    }
//
//    public def void triggerSynchronization(VURI vuri) {
//        val cd = this.changeRecorder.endRecording();
//        cd.applyAndReverse();
//        val changes = this.changeDescription2ChangeConverter.getChanges(cd, vuri);
//        cd.applyAndReverse();
//        this.changeSynchronizer.synchronizeChanges(changes);
//        this.changeRecorder.beginRecording(Collections.EMPTY_LIST);
//    }
//
//    public def void triggerSynchronization(EObject eObject) {
//        val vuri = VURI.getInstance(eObject.eResource());
//        this.triggerSynchronization(vuri);
//    }
//    
//    public def void triggerSynchronization(Resource resource) {
//        val vuri = VURI.getInstance(resource);
//        this.triggerSynchronization(vuri);
//    }
//
//    public def void synchronizeFileChange(FileChangeKind fileChangeKind, VURI vuri) {
//        val fileChange = new FileChange(fileChangeKind, vuri);
//        this.changeSynchronizer.synchronizeChange(fileChange);
//    }
//	
//	// Utility functions
//	@SuppressWarnings("unchecked")
//	public def <T extends EObject> T reloadByTUID(T eObject) {
//		try {
//			val tuid = getCorrespondenceModel().calculateTUIDFromEObject(eObject);
//			return getCorrespondenceModel().resolveEObjectFromTUID(tuid) as T;
//		} catch (Throwable e) {
//			LOGGER.error(e);
//		}
//		
//		return null;
//	}
//	
//	public def URI createModelURI(String fileName) {
//		return URI.createPlatformResourceURI(baseModelPath + "/" + fileName, false);
//	}
//	
//	
//	/**
//	 * Creates a model, saves it and triggers synchronisation. The Path of the
//	 * model is relative to {@link MODEL_PATH} (normally: MockupProject/model).
//	 */
//	public def <T extends EObject> T createManipulateSaveAndSyncModel(String modelPath, Supplier<T> manipulate)
//			throws IOException {
//		val resourcePath = baseModelPath + "/" + modelPath;
//
//		val resourceVURI = VURI.getInstance(resourcePath);
//
//		val result = manipulate.get();
//		val resourceURI = URI.createPlatformResourceURI(resourcePath, false);
//		val resource = EcoreResourceBridge.loadResourceAtURI(resourceURI, resourceSet);
////		val resource = resourceSet.createResource(resourceURI);
//		EcoreResourceBridge.saveEObjectAsOnlyContent(result, resource);
//
//		this.changeRecorder.beginRecording(#[result]);
//
//		this.synchronizeFileChange(FileChangeKind.CREATE, resourceVURI);
//
//		return result;
//	}
//
//	public def EObject createAndSyncModelWithRootObject(String modelPath, EObject rootEObject) throws IOException {
//		return createManipulateSaveAndSyncModel(modelPath, [rootEObject]);
//	}
//
//	public def <R> R recordManipulateSaveAndSync(Resource resourceToSaveAfter, Supplier<R> manipulate)
//			throws IOException {
//		changeRecorder.beginRecording(#[resourceToSaveAfter]);
//		
//		vsum.detachTransactionalEditingDomain();
//		
//		val resultContainer = new ClaimableSingletonContainer(true); // is set inside the closure
//		resultContainer.put(manipulate.get);
//		EcoreResourceBridge.saveResource(resourceToSaveAfter);
//		triggerSynchronization(resourceToSaveAfter);
//		
//		val result = resultContainer.claim();
//		return result;
//	}
//
//	public def void recordManipulateSaveAndSync(Resource resource, Runnable manipulate) throws IOException {
//		recordManipulateSaveAndSync(resource, [
//			manipulate.run
//			return null
//		]);
//	}
//}