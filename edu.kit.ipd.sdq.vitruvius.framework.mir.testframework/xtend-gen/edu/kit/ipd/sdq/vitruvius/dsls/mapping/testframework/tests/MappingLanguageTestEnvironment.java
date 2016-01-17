package edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import edu.kit.ipd.sdq.vitruvius.casestudies.emf.changedescription2change.ChangeDescription2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.commandexecuter.CommandExecutingImpl;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.AbstractMappingChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests.AbstractMappingTestBase;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests.MappingLanguageTestChange2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests.MappingLanguageTestUserInteracting;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.util.ClaimableSingletonContainer;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.util.MappingLanguageTestUtil;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.EclipseProjectHelper;
import edu.kit.ipd.sdq.vitruvius.framework.changepreparer.ChangePreparingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.run.changesynchronizer.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMConstants;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.junit.runners.model.FrameworkMethod;

@SuppressWarnings("all")
public class MappingLanguageTestEnvironment implements SynchronisationListener {
  private final static Logger LOGGER = Logger.getLogger(MappingLanguageTestEnvironment.class);
  
  public final static String MODEL_PATH = "/model";
  
  private List<Change2CommandTransforming> c2cts;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  @Inject
  private MappingLanguageTestUserInteracting userInteracting;
  
  private final AbstractMappingTestBase mappingTest;
  
  private MetaRepositoryImpl metaRepository;
  
  private VSUMImpl vsum;
  
  private ChangeSynchronizerImpl changeSynchronizer;
  
  private ResourceSetImpl resourceSet;
  
  private ChangeRecorder changeRecorder;
  
  private ChangeDescription2ChangeConverter changeDescription2ChangeConverter;
  
  private FrameworkMethod method;
  
  private String projectName;
  
  private String baseModelPath;
  
  @Inject
  public MappingLanguageTestEnvironment(final AbstractMappingTestBase mappingTest, final Change2CommandTransforming c2ct) {
    this.c2cts = Collections.<Change2CommandTransforming>unmodifiableList(CollectionLiterals.<Change2CommandTransforming>newArrayList(c2ct));
    this.mappingTest = mappingTest;
  }
  
  public ChangeDescription2ChangeConverter setup(final FrameworkMethod method) {
    try {
      ChangeDescription2ChangeConverter _xblockexpression = null;
      {
        this.method = method;
        Class<?> _declaringClass = method.getDeclaringClass();
        String _name = _declaringClass.getName();
        String _plus = (_name + "_");
        String _name_1 = method.getName();
        String _plus_1 = (_plus + _name_1);
        String _plus_2 = (_plus_1 + "_");
        LocalDateTime _now = LocalDateTime.now();
        DateTimeFormatter _ofPattern = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String _format = _now.format(_ofPattern);
        String _plus_3 = (_plus_2 + _format);
        this.projectName = _plus_3;
        this.baseModelPath = (this.projectName + MappingLanguageTestEnvironment.MODEL_PATH);
        final EclipseProjectHelper eph = new EclipseProjectHelper(this.projectName);
        eph.reinitializeJavaProject();
        IProject _project = eph.getProject();
        IFolder _folder = _project.getFolder("model");
        _folder.create(true, true, null);
        final MappingLanguageTestChange2CommandTransformingProvidingImpl change2CommandTransformingProvider = new MappingLanguageTestChange2CommandTransformingProvidingImpl();
        for (final Change2CommandTransforming c2ct : this.c2cts) {
          {
            AbstractMappingChange2CommandTransforming _requireType = JavaHelper.<AbstractMappingChange2CommandTransforming, Change2CommandTransforming>requireType(c2ct, AbstractMappingChange2CommandTransforming.class);
            _requireType.setUserInteracting(this.userInteracting);
            change2CommandTransformingProvider.addChange2CommandTransforming(c2ct);
          }
        }
        Collection<Pair<String, String>> _metamodelURIsAndExtensions = this.mappingTest.getMetamodelURIsAndExtensions();
        final Function1<Pair<String, String>, Metamodel> _function = (Pair<String, String> it) -> {
          String _first = it.getFirst();
          String _second = it.getSecond();
          return MappingLanguageTestUtil.createAttributeTUIDMetamodel(_first, _second);
        };
        Iterable<Metamodel> _map = IterableExtensions.<Pair<String, String>, Metamodel>map(_metamodelURIsAndExtensions, _function);
        MetaRepositoryImpl _createEmptyMetaRepository = MappingLanguageTestUtil.createEmptyMetaRepository(((Metamodel[])Conversions.unwrapArray(_map, Metamodel.class)));
        this.metaRepository = _createEmptyMetaRepository;
        VSUMImpl _createVSUM = TestUtil.createVSUM(this.metaRepository);
        this.vsum = _createVSUM;
        final CommandExecutingImpl commandExecuter = new CommandExecutingImpl();
        final ChangePreparingImpl changePreparer = new ChangePreparingImpl(this.vsum, this.vsum);
        ChangeSynchronizerImpl _changeSynchronizerImpl = new ChangeSynchronizerImpl(this.vsum, change2CommandTransformingProvider, this.vsum, 
          this.metaRepository, this.vsum, this, changePreparer, commandExecuter);
        this.changeSynchronizer = _changeSynchronizerImpl;
        ResourceSetImpl _resourceSetImpl = new ResourceSetImpl();
        this.resourceSet = _resourceSetImpl;
        ChangeRecorder _changeRecorder = new ChangeRecorder();
        this.changeRecorder = _changeRecorder;
        ChangeDescription2ChangeConverter _changeDescription2ChangeConverter = new ChangeDescription2ChangeConverter();
        _xblockexpression = this.changeDescription2ChangeConverter = _changeDescription2ChangeConverter;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void after() {
    final EclipseProjectHelper eph = new EclipseProjectHelper(VSUMConstants.VSUM_PROJECT_NAME);
    eph.move(((this.projectName + "_") + VSUMConstants.VSUM_PROJECT_NAME));
  }
  
  @Override
  public void syncAborted(final EMFModelChange abortedChange) {
    String _string = abortedChange.toString();
    String _plus = ("sync aborted: " + _string);
    MappingLanguageTestEnvironment.LOGGER.trace(_plus);
  }
  
  @Override
  public void syncAborted(final TransformationAbortCause cause) {
    String _string = cause.toString();
    String _plus = ("sync aborted: " + _string);
    MappingLanguageTestEnvironment.LOGGER.trace(_plus);
  }
  
  @Override
  public void syncFinished() {
    MappingLanguageTestEnvironment.LOGGER.trace("sync finished");
  }
  
  @Override
  public void syncStarted() {
    MappingLanguageTestEnvironment.LOGGER.trace("sync started");
  }
  
  public CorrespondenceInstance getCorrespondenceInstance() throws Throwable {
    int _size = this.c2cts.size();
    boolean _greaterThan = (_size > 1);
    if (_greaterThan) {
      String _name = Change2CommandTransforming.class.getName();
      String _plus = ("cannot determine correspondence instance for multiple registered " + _name);
      String _plus_1 = (_plus + " instances.");
      throw new IllegalStateException(_plus_1);
    }
    final Function1<Change2CommandTransforming, List<InternalCorrespondenceInstance>> _function = (Change2CommandTransforming c2ct) -> {
      List<InternalCorrespondenceInstance> _xblockexpression = null;
      {
        final List<Pair<VURI, VURI>> mms = c2ct.getTransformableMetamodels();
        final Function1<Pair<VURI, VURI>, InternalCorrespondenceInstance> _function_1 = (Pair<VURI, VURI> mm) -> {
          VURI _first = mm.getFirst();
          VURI _second = mm.getSecond();
          return this.vsum.getCorrespondenceInstanceOriginal(_first, _second);
        };
        _xblockexpression = ListExtensions.<Pair<VURI, VURI>, InternalCorrespondenceInstance>map(mms, _function_1);
      }
      return _xblockexpression;
    };
    List<List<InternalCorrespondenceInstance>> _map = ListExtensions.<Change2CommandTransforming, List<InternalCorrespondenceInstance>>map(this.c2cts, _function);
    Iterable<InternalCorrespondenceInstance> _flatten = Iterables.<InternalCorrespondenceInstance>concat(_map);
    return JavaHelper.<InternalCorrespondenceInstance>claimIdenticalElements(_flatten);
  }
  
  public void triggerSynchronization(final VURI vuri) {
    final ChangeDescription cd = this.changeRecorder.endRecording();
    cd.applyAndReverse();
    final List<Change> changes = this.changeDescription2ChangeConverter.getChanges(cd, vuri);
    cd.applyAndReverse();
    this.changeSynchronizer.synchronizeChanges(changes);
    this.changeRecorder.beginRecording(Collections.EMPTY_LIST);
  }
  
  public void triggerSynchronization(final EObject eObject) {
    Resource _eResource = eObject.eResource();
    final VURI vuri = VURI.getInstance(_eResource);
    this.triggerSynchronization(vuri);
  }
  
  public void synchronizeFileChange(final FileChange.FileChangeKind fileChangeKind, final VURI vuri) {
    final FileChange fileChange = new FileChange(fileChangeKind, vuri);
    this.changeSynchronizer.synchronizeChange(fileChange);
  }
  
  @SuppressWarnings("unchecked")
  public <T extends EObject> T reloadByTUID(final T eObject) {
    try {
      CorrespondenceInstance _correspondenceInstance = this.getCorrespondenceInstance();
      final TUID tuid = _correspondenceInstance.calculateTUIDFromEObject(eObject);
      CorrespondenceInstance _correspondenceInstance_1 = this.getCorrespondenceInstance();
      EObject _resolveEObjectFromTUID = _correspondenceInstance_1.resolveEObjectFromTUID(tuid);
      return ((T) _resolveEObjectFromTUID);
    } catch (final Throwable _t) {
      if (_t instanceof Throwable) {
        final Throwable e = (Throwable)_t;
        MappingLanguageTestEnvironment.LOGGER.error(e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return null;
  }
  
  public URI createModelURI(final String fileName) {
    return URI.createPlatformResourceURI(((this.baseModelPath + "/") + fileName), false);
  }
  
  /**
   * Creates a model, saves it and triggers synchronisation. The Path of the
   * model is relative to {@link MODEL_PATH} (normally: MockupProject/model).
   */
  public <T extends EObject> T createManipulateSaveAndSyncModel(final String modelPath, final Supplier<T> manipulate) throws IOException {
    final String resourcePath = ((this.baseModelPath + "/") + modelPath);
    final VURI resourceVURI = VURI.getInstance(resourcePath);
    final T result = manipulate.get();
    final URI resourceURI = URI.createPlatformResourceURI(resourcePath, false);
    final Resource resource = EcoreResourceBridge.loadResourceAtURI(resourceURI, this.resourceSet);
    EcoreResourceBridge.saveEObjectAsOnlyContent(result, resource);
    this.changeRecorder.beginRecording(Collections.<Object>unmodifiableList(CollectionLiterals.<Object>newArrayList(result)));
    this.synchronizeFileChange(FileChange.FileChangeKind.CREATE, resourceVURI);
    return result;
  }
  
  public EObject createAndSyncModelWithRootObject(final String modelPath, final EObject rootEObject) throws IOException {
    final Supplier<EObject> _function = () -> {
      return rootEObject;
    };
    return this.<EObject>createManipulateSaveAndSyncModel(modelPath, _function);
  }
  
  public <T extends EObject, R extends Object> R recordManipulateSaveAndSync(final T input, final Function<T, R> manipulate) throws IOException {
    List<T> _singletonList = Collections.<T>singletonList(input);
    this.changeRecorder.beginRecording(_singletonList);
    this.vsum.detachTransactionalEditingDomain();
    final ClaimableSingletonContainer<R> resultContainer = new ClaimableSingletonContainer<R>(true);
    R _apply = manipulate.apply(input);
    resultContainer.put(_apply);
    Resource _eResource = input.eResource();
    EcoreResourceBridge.saveResource(_eResource);
    this.triggerSynchronization(input);
    final R result = resultContainer.claim();
    return result;
  }
  
  public <T extends EObject> void recordManipulateSaveAndSync(final T input, final Consumer<T> manipulate) throws IOException {
    final Function<T, Object> _function = (T it) -> {
      manipulate.accept(it);
      return null;
    };
    this.<T, Object>recordManipulateSaveAndSync(input, _function);
  }
  
  @Pure
  public MappingLanguageTestUserInteracting getUserInteracting() {
    return this.userInteracting;
  }
}
