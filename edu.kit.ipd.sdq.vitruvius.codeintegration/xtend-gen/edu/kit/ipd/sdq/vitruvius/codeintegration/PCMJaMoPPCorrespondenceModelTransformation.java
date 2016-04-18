package edu.kit.ipd.sdq.vitruvius.codeintegration;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.codeintegration.PCMJaMoPPIntegrationExtending;
import edu.kit.ipd.sdq.vitruvius.codeintegration.ResourceLoadingHelper;
import edu.kit.ipd.sdq.vitruvius.codeintegration.deco.meta.correspondence.integration.IntegrationCorrespondence;
import edu.kit.ipd.sdq.vitruvius.codeintegration.util.IntegrationCorrespondenceHelper;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.commons.Commentable;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.Type;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.Signature;
import org.somox.sourcecodedecorator.ComponentImplementingClassesLink;
import org.somox.sourcecodedecorator.DataTypeSourceCodeLink;
import org.somox.sourcecodedecorator.InnerDatatypeSourceCodeLink;
import org.somox.sourcecodedecorator.InterfaceSourceCodeLink;
import org.somox.sourcecodedecorator.MethodLevelSourceCodeLink;
import org.somox.sourcecodedecorator.impl.SourceCodeDecoratorRepositoryImpl;

/**
 * Class that creates correspondences between PCM and JaMopp model elements.
 * 
 * @author originally by Benjamin Hettwer, changed for thesis by Frederik Petersen
 */
@SuppressWarnings("all")
public class PCMJaMoPPCorrespondenceModelTransformation {
  private HashSet<String> existingEntries = new HashSet<String>();
  
  protected Logger logger = Logger.getRootLogger();
  
  private String scdmPath;
  
  private String pcmPath;
  
  private String jamoppPath;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private Resource scdm;
  
  private Resource pcm;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private List<Resource> jaMoppResources;
  
  private Repository pcmRepo;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private CorrespondenceInstanceDecorator cInstance;
  
  private ModelProviding modelProviding;
  
  private Set<org.emftext.language.java.containers.Package> packages;
  
  private org.emftext.language.java.containers.Package rootPackage;
  
  private IPath projectBase;
  
  public PCMJaMoPPCorrespondenceModelTransformation(final String scdmPath, final String pcmPath, final String jamoppPath, final VSUMImpl vsum, final IPath projectBase) {
    VURI mmUriA = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
    VURI mmURiB = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
    CorrespondenceInstanceDecorator _correspondenceInstanceOriginal = vsum.getCorrespondenceInstanceOriginal(mmUriA, mmURiB);
    this.cInstance = ((CorrespondenceInstanceDecorator) _correspondenceInstanceOriginal);
    this.scdmPath = scdmPath;
    this.pcmPath = pcmPath;
    this.jamoppPath = jamoppPath;
    HashSet<org.emftext.language.java.containers.Package> _hashSet = new HashSet<org.emftext.language.java.containers.Package>();
    this.packages = _hashSet;
    this.projectBase = projectBase;
    this.modelProviding = vsum;
    this.logger.setLevel(Level.ALL);
  }
  
  public void createCorrespondences() {
    this.prepareTransformation();
    this.createPCMtoJaMoppCorrespondences();
  }
  
  /**
   * Loads PCM, SDCDM and JaMoPP resources.
   */
  private void prepareTransformation() {
    try {
      Resource _loadSCDMResource = ResourceLoadingHelper.loadSCDMResource(this.scdmPath);
      this.scdm = _loadSCDMResource;
      Resource _loadPCMRepositoryResource = ResourceLoadingHelper.loadPCMRepositoryResource(this.pcmPath);
      this.pcm = _loadPCMRepositoryResource;
      EList<EObject> _contents = this.pcm.getContents();
      EObject _get = _contents.get(0);
      this.pcmRepo = ((Repository) _get);
      List<Resource> _loadJaMoPPResourceSet = ResourceLoadingHelper.loadJaMoPPResourceSet(this.jamoppPath);
      this.jaMoppResources = _loadJaMoPPResourceSet;
      final Consumer<Resource> _function = (Resource it) -> {
        EList<EObject> _contents_1 = it.getContents();
        Iterable<org.emftext.language.java.containers.Package> _filter = Iterables.<org.emftext.language.java.containers.Package>filter(_contents_1, org.emftext.language.java.containers.Package.class);
        Iterables.<org.emftext.language.java.containers.Package>addAll(this.packages, _filter);
      };
      this.jaMoppResources.forEach(_function);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Creates the following correspondence hierarchy from the mappings
   * given by the SoMoX SourceCodeDecorator model. Additionally information
   * of the jaMoPP ResourceSet is used as well.
   * 
   * 
   * 1. PCMRepo <-> JaMopp Root-Package Correspondence
   * 		2. RepositoryComponent <-> Package correspondences
   * 		3. RepositoryComponent <-> CompilationUnit Correspondences
   * 		4. RepositoryComponent <-> jaMopp Class
   * 		5. PCM Interface <-> CompilationUnit Correspondences
   * 		6. PCM Interface <-> jaMopp Type (Class or Interface) Correspondences
   * 			7. OperationSignature <-> Method Correspondences
   * 				8. PCM Parameter <-> Ordinary Parameter Correspondences
   *  	9. PCM DataType <-> CompUnit correspondence
   * 		10. PCM DataType <-> JaMopp Type correspondence
   * 			11.PCM InnerDeclaration <-> JaMopp Field correspondence
   */
  private void createPCMtoJaMoppCorrespondences() {
    EList<EObject> _contents = this.scdm.getContents();
    EObject _get = _contents.get(0);
    SourceCodeDecoratorRepositoryImpl scdmRepo = ((SourceCodeDecoratorRepositoryImpl) _get);
    this.createRepoPackageCorrespondence();
    EList<ComponentImplementingClassesLink> _componentImplementingClassesLink = scdmRepo.getComponentImplementingClassesLink();
    final Consumer<ComponentImplementingClassesLink> _function = (ComponentImplementingClassesLink it) -> {
      this.createComponentClassCorrespondence(it);
    };
    _componentImplementingClassesLink.forEach(_function);
    EList<InterfaceSourceCodeLink> _interfaceSourceCodeLink = scdmRepo.getInterfaceSourceCodeLink();
    final Consumer<InterfaceSourceCodeLink> _function_1 = (InterfaceSourceCodeLink it) -> {
      this.createInterfaceCorrespondence(it);
    };
    _interfaceSourceCodeLink.forEach(_function_1);
    EList<MethodLevelSourceCodeLink> _methodLevelSourceCodeLink = scdmRepo.getMethodLevelSourceCodeLink();
    final Consumer<MethodLevelSourceCodeLink> _function_2 = (MethodLevelSourceCodeLink it) -> {
      this.createMethodCorrespondence(it);
    };
    _methodLevelSourceCodeLink.forEach(_function_2);
    EList<DataTypeSourceCodeLink> _dataTypeSourceCodeLink = scdmRepo.getDataTypeSourceCodeLink();
    final Consumer<DataTypeSourceCodeLink> _function_3 = (DataTypeSourceCodeLink it) -> {
      this.createDataTypeCorrespondence(it);
    };
    _dataTypeSourceCodeLink.forEach(_function_3);
    this.findAndExecuteAfterTransformationExtensions();
    Resource _eResource = this.pcmRepo.eResource();
    VURI _instance = VURI.getInstance(_eResource);
    this.modelProviding.saveExistingModelInstanceOriginal(_instance);
  }
  
  private void findAndExecuteAfterTransformationExtensions() {
    String _extensionPropertyName = VitruviusConstants.getExtensionPropertyName();
    List<PCMJaMoPPIntegrationExtending> _registeredExtensions = EclipseBridge.<PCMJaMoPPIntegrationExtending>getRegisteredExtensions(PCMJaMoPPIntegrationExtending.ID, _extensionPropertyName, PCMJaMoPPIntegrationExtending.class);
    final Consumer<PCMJaMoPPIntegrationExtending> _function = (PCMJaMoPPIntegrationExtending it) -> {
      it.afterBasicTransformations(this);
    };
    _registeredExtensions.forEach(_function);
  }
  
  private Correspondence createRepoPackageCorrespondence() {
    org.emftext.language.java.containers.Package _rootPackage = this.getRootPackage();
    return this.addCorrespondence(this.pcmRepo, _rootPackage);
  }
  
  private Correspondence createComponentClassCorrespondence(final ComponentImplementingClassesLink componentClassLink) {
    Correspondence _xblockexpression = null;
    {
      RepositoryComponent pcmComponent = componentClassLink.getComponent();
      Correspondence _xifexpression = null;
      if ((pcmComponent instanceof BasicComponent)) {
        Correspondence _xblockexpression_1 = null;
        {
          EList<ConcreteClassifier> _implementingClasses = componentClassLink.getImplementingClasses();
          ConcreteClassifier _get = _implementingClasses.get(0);
          final ConcreteClassifier desreolvedClassInSCDM = this.<ConcreteClassifier>deresolveIfNesessary(_get);
          ConcreteClassifier jamoppClass = this.<ConcreteClassifier>resolveJaMoppProxy(desreolvedClassInSCDM);
          final org.emftext.language.java.containers.Package package_ = this.getPackageForCommentable(jamoppClass);
          final Repository deresolvedPcmRepo = this.<Repository>deresolveIfNesessary(this.pcmRepo);
          org.emftext.language.java.containers.Package _rootPackage = this.getRootPackage();
          final org.emftext.language.java.containers.Package deresolvedRootPackage = this.<org.emftext.language.java.containers.Package>deresolveIfNesessary(_rootPackage);
          Set<EObject> _set = CollectionBridge.<EObject>toSet(deresolvedPcmRepo);
          Set<EObject> _set_1 = CollectionBridge.<EObject>toSet(deresolvedRootPackage);
          Set<Correspondence> _correspondencesBetweenEObjects = CorrespondenceInstanceUtil.<Correspondence>getCorrespondencesBetweenEObjects(this.cInstance, _set, _set_1);
          Correspondence parentRepoPackageCorr = CollectionBridge.<Correspondence>claimOne(_correspondencesBetweenEObjects);
          this.addCorrespondence(pcmComponent, package_, parentRepoPackageCorr);
          CompilationUnit _containingCompilationUnit = jamoppClass.getContainingCompilationUnit();
          this.addCorrespondence(pcmComponent, _containingCompilationUnit, parentRepoPackageCorr);
          _xblockexpression_1 = this.addCorrespondence(pcmComponent, jamoppClass, parentRepoPackageCorr);
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  private Correspondence createInterfaceCorrespondence(final InterfaceSourceCodeLink interfaceLink) {
    Correspondence _xblockexpression = null;
    {
      Interface pcmInterface = interfaceLink.getInterface();
      ConcreteClassifier _gastClass = interfaceLink.getGastClass();
      ConcreteClassifier _resolveJaMoppProxy = this.<ConcreteClassifier>resolveJaMoppProxy(_gastClass);
      Type jamoppType = ((Type) _resolveJaMoppProxy);
      final Repository deresolvedPcmRepo = this.<Repository>deresolveIfNesessary(this.pcmRepo);
      org.emftext.language.java.containers.Package _rootPackage = this.getRootPackage();
      final org.emftext.language.java.containers.Package deresolvedRootPackage = this.<org.emftext.language.java.containers.Package>deresolveIfNesessary(_rootPackage);
      Set<EObject> _set = CollectionBridge.<EObject>toSet(deresolvedPcmRepo);
      Set<EObject> _set_1 = CollectionBridge.<EObject>toSet(deresolvedRootPackage);
      Set<Correspondence> _correspondencesBetweenEObjects = CorrespondenceInstanceUtil.<Correspondence>getCorrespondencesBetweenEObjects(this.cInstance, _set, _set_1);
      Correspondence parentCorrespondence = CollectionBridge.<Correspondence>claimOne(_correspondencesBetweenEObjects);
      CompilationUnit _containingCompilationUnit = jamoppType.getContainingCompilationUnit();
      this.addCorrespondence(pcmInterface, _containingCompilationUnit, parentCorrespondence);
      _xblockexpression = this.addCorrespondence(pcmInterface, jamoppType, parentCorrespondence);
    }
    return _xblockexpression;
  }
  
  private void createMethodCorrespondence(final MethodLevelSourceCodeLink methodLink) {
    Member _function = methodLink.getFunction();
    Member _resolveJaMoppProxy = this.<Member>resolveJaMoppProxy(_function);
    Method jamoppMethod = ((Method) _resolveJaMoppProxy);
    Signature _operation = methodLink.getOperation();
    OperationSignature pcmMethod = ((OperationSignature) _operation);
    ConcreteClassifier jamoppInterface = jamoppMethod.getContainingConcreteClassifier();
    OperationInterface pcmInterface = pcmMethod.getInterface__OperationSignature();
    final OperationInterface deresolvedPcmInterface = this.<OperationInterface>deresolveIfNesessary(pcmInterface);
    final ConcreteClassifier deresolvedJamoppInterface = this.<ConcreteClassifier>deresolveIfNesessary(jamoppInterface);
    Set<EObject> _set = CollectionBridge.<EObject>toSet(deresolvedPcmInterface);
    Set<EObject> _set_1 = CollectionBridge.<EObject>toSet(deresolvedJamoppInterface);
    Set<Correspondence> interfaceCorrespondence = CorrespondenceInstanceUtil.<Correspondence>getCorrespondencesBetweenEObjects(this.cInstance, _set, _set_1);
    boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(interfaceCorrespondence);
    if (_isNullOrEmpty) {
      return;
    }
    final Set<Correspondence> _converted_interfaceCorrespondence = (Set<Correspondence>)interfaceCorrespondence;
    Correspondence _get = ((Correspondence[])Conversions.unwrapArray(_converted_interfaceCorrespondence, Correspondence.class))[0];
    Correspondence methodCorrespondence = this.addCorrespondence(pcmMethod, jamoppMethod, _get);
    EList<Parameter> _parameters__OperationSignature = pcmMethod.getParameters__OperationSignature();
    for (final Parameter pcmParam : _parameters__OperationSignature) {
      {
        EList<org.emftext.language.java.parameters.Parameter> _parameters = jamoppMethod.getParameters();
        final Function1<org.emftext.language.java.parameters.Parameter, Boolean> _function_1 = (org.emftext.language.java.parameters.Parameter jp) -> {
          String _name = jp.getName();
          String _entityName = pcmParam.getEntityName();
          return Boolean.valueOf(_name.equals(_entityName));
        };
        org.emftext.language.java.parameters.Parameter jamoppParam = IterableExtensions.<org.emftext.language.java.parameters.Parameter>findFirst(_parameters, _function_1);
        boolean _notEquals = (!Objects.equal(jamoppParam, null));
        if (_notEquals) {
          this.addCorrespondence(pcmParam, ((OrdinaryParameter) jamoppParam), methodCorrespondence);
        }
      }
    }
  }
  
  private void createDataTypeCorrespondence(final DataTypeSourceCodeLink dataTypeLink) {
    DataType pcmDataType = dataTypeLink.getPcmDataType();
    Type _jaMoPPType = dataTypeLink.getJaMoPPType();
    Type _resolveJaMoppProxy = this.<Type>resolveJaMoppProxy(_jaMoPPType);
    Type jamoppType = ((Type) _resolveJaMoppProxy);
    final Repository deresolvedPcmRepo = this.<Repository>deresolveIfNesessary(this.pcmRepo);
    org.emftext.language.java.containers.Package _rootPackage = this.getRootPackage();
    final org.emftext.language.java.containers.Package deresolvedRootPackage = this.<org.emftext.language.java.containers.Package>deresolveIfNesessary(_rootPackage);
    Set<EObject> _set = CollectionBridge.<EObject>toSet(deresolvedPcmRepo);
    Set<EObject> _set_1 = CollectionBridge.<EObject>toSet(deresolvedRootPackage);
    Set<Correspondence> _correspondencesBetweenEObjects = CorrespondenceInstanceUtil.<Correspondence>getCorrespondencesBetweenEObjects(this.cInstance, _set, _set_1);
    Correspondence parentCorrespondence = CollectionBridge.<Correspondence>claimOne(_correspondencesBetweenEObjects);
    CompilationUnit _containingCompilationUnit = jamoppType.getContainingCompilationUnit();
    this.addCorrespondence(pcmDataType, _containingCompilationUnit, parentCorrespondence);
    Correspondence dataTypeCorrespondence = this.addCorrespondence(pcmDataType, jamoppType, parentCorrespondence);
    EList<InnerDatatypeSourceCodeLink> _innerDatatypeSourceCodeLink = dataTypeLink.getInnerDatatypeSourceCodeLink();
    boolean _notEquals = (!Objects.equal(_innerDatatypeSourceCodeLink, null));
    if (_notEquals) {
      EList<InnerDatatypeSourceCodeLink> _innerDatatypeSourceCodeLink_1 = dataTypeLink.getInnerDatatypeSourceCodeLink();
      for (final InnerDatatypeSourceCodeLink innerDataTypeLink : _innerDatatypeSourceCodeLink_1) {
        {
          InnerDeclaration innerDeclaration = innerDataTypeLink.getInnerDeclaration();
          Field _field = innerDataTypeLink.getField();
          Field _resolveJaMoppProxy_1 = this.<Field>resolveJaMoppProxy(_field);
          Field jamoppField = ((Field) _resolveJaMoppProxy_1);
          this.addCorrespondence(innerDeclaration, jamoppField, dataTypeCorrespondence);
        }
      }
    }
  }
  
  /**
   * Returns the {@link Package} for the given {@link Commentable} or null if none was found.
   */
  private org.emftext.language.java.containers.Package getPackageForCommentable(final Commentable commentable) {
    CompilationUnit _containingCompilationUnit = commentable.getContainingCompilationUnit();
    String namespace = _containingCompilationUnit.getNamespacesAsString();
    boolean _or = false;
    boolean _endsWith = namespace.endsWith("$");
    if (_endsWith) {
      _or = true;
    } else {
      boolean _endsWith_1 = namespace.endsWith(".");
      _or = _endsWith_1;
    }
    if (_or) {
      int _length = namespace.length();
      int _minus = (_length - 1);
      String _substring = namespace.substring(0, _minus);
      namespace = _substring;
    }
    final String finalNamespace = namespace;
    final Function1<org.emftext.language.java.containers.Package, Boolean> _function = (org.emftext.language.java.containers.Package pack) -> {
      String _namespacesAsString = pack.getNamespacesAsString();
      String _name = pack.getName();
      String _plus = (_namespacesAsString + _name);
      return Boolean.valueOf(finalNamespace.equals(_plus));
    };
    return IterableExtensions.<org.emftext.language.java.containers.Package>findFirst(this.packages, _function);
  }
  
  /**
   * Returns the resolved EObject for the given jaMopp proxy.
   */
  public <T extends EObject> T resolveJaMoppProxy(final T proxy) {
    boolean _or = false;
    boolean _equals = Objects.equal(proxy, null);
    if (_equals) {
      _or = true;
    } else {
      boolean _eIsProxy = proxy.eIsProxy();
      boolean _not = (!_eIsProxy);
      _or = _not;
    }
    if (_or) {
      return proxy;
    }
    Resource _get = this.jaMoppResources.get(0);
    ResourceSet _resourceSet = _get.getResourceSet();
    EObject _resolve = EcoreUtil.resolve(proxy, _resourceSet);
    return ((T) _resolve);
  }
  
  /**
   * Returns top-level package of the loaded jamopp resource set.
   */
  private org.emftext.language.java.containers.Package getRootPackage() {
    boolean _notEquals = (!Objects.equal(this.rootPackage, null));
    if (_notEquals) {
      return this.rootPackage;
    }
    org.emftext.language.java.containers.Package _get = ((org.emftext.language.java.containers.Package[])Conversions.unwrapArray(this.packages, org.emftext.language.java.containers.Package.class))[0];
    this.rootPackage = _get;
    for (final org.emftext.language.java.containers.Package package_ : this.packages) {
      {
        String _namespacesAsString = package_.getNamespacesAsString();
        String _name = package_.getName();
        String fullyQualifiedName = (_namespacesAsString + _name);
        int _length = fullyQualifiedName.length();
        String _name_1 = this.rootPackage.getName();
        int _length_1 = _name_1.length();
        String _namespacesAsString_1 = this.rootPackage.getNamespacesAsString();
        int _length_2 = _namespacesAsString_1.length();
        int _plus = (_length_1 + _length_2);
        boolean _lessThan = (_length < _plus);
        if (_lessThan) {
          this.rootPackage = package_;
        }
      }
    }
    return this.rootPackage;
  }
  
  protected Correspondence addCorrespondence(final EObject pcmObject, final EObject jamoppObject) {
    return this.addCorrespondence(pcmObject, jamoppObject, null);
  }
  
  /**
   * Creates an {@link EObjectCorrespondence} between the given EObjects
   * and adds it to the {@link CorrespondenceInstance}
   */
  public Correspondence addCorrespondence(final EObject objectA, final EObject objectB, final Correspondence parent) {
    boolean _or = false;
    boolean _equals = Objects.equal(objectA, null);
    if (_equals) {
      _or = true;
    } else {
      boolean _equals_1 = Objects.equal(objectB, null);
      _or = _equals_1;
    }
    if (_or) {
      throw new IllegalArgumentException("Corresponding elements must not be null!");
    }
    EObject deresolvedA = this.<EObject>deresolveIfNesessary(objectA);
    EObject deresolvedB = this.<EObject>deresolveIfNesessary(objectB);
    TUID _calculateTUIDFromEObject = this.cInstance.calculateTUIDFromEObject(deresolvedA);
    String _string = _calculateTUIDFromEObject.toString();
    TUID _calculateTUIDFromEObject_1 = this.cInstance.calculateTUIDFromEObject(deresolvedB);
    String _string_1 = _calculateTUIDFromEObject_1.toString();
    String identifier = (_string + _string_1);
    boolean _contains = this.existingEntries.contains(identifier);
    boolean _not = (!_contains);
    if (_not) {
      final CorrespondenceInstance<IntegrationCorrespondence> integrationCorrespondenceView = IntegrationCorrespondenceHelper.getEditableView(this.cInstance);
      List<EObject> _list = CollectionBridge.<EObject>toList(deresolvedA);
      List<EObject> _list_1 = CollectionBridge.<EObject>toList(deresolvedB);
      final Correspondence integratedCorrespondence = integrationCorrespondenceView.createAndAddCorrespondence(_list, _list_1);
      this.existingEntries.add(identifier);
      this.logger.info(((("Created Correspondence for element: " + objectA) + " and Element: ") + objectB));
      return integratedCorrespondence;
    }
    return null;
  }
  
  /**
   * Converts the absolute resource URI of given EObject to platform URI
   * or does nothing if it already has one.
   */
  public <T extends EObject> T deresolveIfNesessary(final T object) {
    Resource _eResource = object.eResource();
    boolean _equals = Objects.equal(null, _eResource);
    if (_equals) {
      return object;
    }
    Resource _eResource_1 = object.eResource();
    URI uri = _eResource_1.getURI();
    boolean _isPlatform = uri.isPlatform();
    boolean _not = (!_isPlatform);
    if (_not) {
      String _string = this.projectBase.toString();
      String _plus = (_string + Character.valueOf(IPath.SEPARATOR));
      URI base = URI.createFileURI(_plus);
      URI relativeUri = uri.deresolve(base);
      Resource _eResource_2 = object.eResource();
      String _string_1 = relativeUri.toString();
      URI _createPlatformResourceURI = EMFBridge.createPlatformResourceURI(_string_1);
      _eResource_2.setURI(_createPlatformResourceURI);
    }
    return object;
  }
  
  @Pure
  public Resource getScdm() {
    return this.scdm;
  }
  
  @Pure
  public List<Resource> getJaMoppResources() {
    return this.jaMoppResources;
  }
  
  @Pure
  public CorrespondenceInstanceDecorator getCInstance() {
    return this.cInstance;
  }
}
