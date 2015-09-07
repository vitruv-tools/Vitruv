package edu.kit.ipd.sdq.vitruvius.integration.transformations;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.integration.transformations.BasicCorrespondenceModelTransformation;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Level;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.commons.Commentable;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.Type;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Interface;
import org.somox.sourcecodedecorator.ComponentImplementingClassesLink;
import org.somox.sourcecodedecorator.DataTypeSourceCodeLink;
import org.somox.sourcecodedecorator.InnerDatatypeSourceCodeLink;
import org.somox.sourcecodedecorator.InterfaceSourceCodeLink;
import org.somox.sourcecodedecorator.MethodLevelSourceCodeLink;
import org.somox.sourcecodedecorator.impl.SourceCodeDecoratorRepositoryImpl;

/**
 * Class that creates correspondences between PCM and JaMopp model elements.
 * 
 * @author Benjamin Hettwer
 */
@SuppressWarnings("all")
public class PCMJaMoPPCorrespondenceModelTransformation extends BasicCorrespondenceModelTransformation {
  private String scdmPath;
  
  private String pcmPath;
  
  private String jamoppPath;
  
  private Resource scdm;
  
  private Resource pcm;
  
  private ResourceSet jaMoppResourceSet;
  
  private /* Repository */Object pcmRepo;
  
  private CorrespondenceInstance cInstance;
  
  private Set<org.emftext.language.java.containers.Package> packages;
  
  private org.emftext.language.java.containers.Package rootPackage;
  
  public PCMJaMoPPCorrespondenceModelTransformation(final String scdmPath, final String pcmPath, final String jamoppPath, final VSUMImpl vsum) {
    VURI mmUriA = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
    VURI mmURiB = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
    InternalCorrespondenceInstance _correspondenceInstanceOriginal = vsum.getCorrespondenceInstanceOriginal(mmUriA, mmURiB);
    this.cInstance = _correspondenceInstanceOriginal;
    this.scdmPath = scdmPath;
    this.pcmPath = pcmPath;
    this.jamoppPath = jamoppPath;
    HashSet<org.emftext.language.java.containers.Package> _hashSet = new HashSet<org.emftext.language.java.containers.Package>();
    this.packages = _hashSet;
    this.logger.setLevel(Level.ALL);
  }
  
  @Override
  public CorrespondenceInstance getCorrespondenceInstance() {
    return this.cInstance;
  }
  
  @Override
  public void createCorrespondences() {
    this.prepareTransformation();
    this.createPCMtoJaMoppCorrespondences();
  }
  
  /**
   * Loads PCM, SDCDM and JaMoPP resources.
   */
  private void prepareTransformation() {
    throw new Error("Unresolved compilation problems:"
      + "\nRepository cannot be resolved to a type.");
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
    final Procedure1<ComponentImplementingClassesLink> _function = new Procedure1<ComponentImplementingClassesLink>() {
      @Override
      public void apply(final ComponentImplementingClassesLink it) {
        PCMJaMoPPCorrespondenceModelTransformation.this.createComponentClassCorrespondence(it);
      }
    };
    IterableExtensions.<ComponentImplementingClassesLink>forEach(_componentImplementingClassesLink, _function);
    EList<InterfaceSourceCodeLink> _interfaceSourceCodeLink = scdmRepo.getInterfaceSourceCodeLink();
    final Procedure1<InterfaceSourceCodeLink> _function_1 = new Procedure1<InterfaceSourceCodeLink>() {
      @Override
      public void apply(final InterfaceSourceCodeLink it) {
        PCMJaMoPPCorrespondenceModelTransformation.this.createInterfaceCorrespondence(it);
      }
    };
    IterableExtensions.<InterfaceSourceCodeLink>forEach(_interfaceSourceCodeLink, _function_1);
    EList<MethodLevelSourceCodeLink> _methodLevelSourceCodeLink = scdmRepo.getMethodLevelSourceCodeLink();
    final Procedure1<MethodLevelSourceCodeLink> _function_2 = new Procedure1<MethodLevelSourceCodeLink>() {
      @Override
      public void apply(final MethodLevelSourceCodeLink it) {
        PCMJaMoPPCorrespondenceModelTransformation.this.createMethodCorrespondence(it);
      }
    };
    IterableExtensions.<MethodLevelSourceCodeLink>forEach(_methodLevelSourceCodeLink, _function_2);
    EList<DataTypeSourceCodeLink> _dataTypeSourceCodeLink = scdmRepo.getDataTypeSourceCodeLink();
    final Procedure1<DataTypeSourceCodeLink> _function_3 = new Procedure1<DataTypeSourceCodeLink>() {
      @Override
      public void apply(final DataTypeSourceCodeLink it) {
        PCMJaMoPPCorrespondenceModelTransformation.this.createDataTypeCorrespondence(it);
      }
    };
    IterableExtensions.<DataTypeSourceCodeLink>forEach(_dataTypeSourceCodeLink, _function_3);
  }
  
  private Correspondence createRepoPackageCorrespondence() {
    org.emftext.language.java.containers.Package _rootPackage = this.getRootPackage();
    return this.addCorrespondence(this.pcmRepo, _rootPackage);
  }
  
  private Correspondence createComponentClassCorrespondence(final ComponentImplementingClassesLink componentClassLink) {
    throw new Error("Unresolved compilation problems:"
      + "\nBasicComponent cannot be resolved to a type.");
  }
  
  private Correspondence createInterfaceCorrespondence(final InterfaceSourceCodeLink interfaceLink) {
    Correspondence _xblockexpression = null;
    {
      Interface pcmInterface = interfaceLink.getInterface();
      ConcreteClassifier _gastClass = interfaceLink.getGastClass();
      EObject _resolveJaMoppProxy = this.resolveJaMoppProxy(_gastClass);
      Type jamoppType = ((Type) _resolveJaMoppProxy);
      org.emftext.language.java.containers.Package _rootPackage = this.getRootPackage();
      SameTypeCorrespondence parentCorrespondence = this.getUniqueSameTypeCorrespondence(this.pcmRepo, _rootPackage);
      CompilationUnit _containingCompilationUnit = jamoppType.getContainingCompilationUnit();
      this.addCorrespondence(pcmInterface, _containingCompilationUnit, parentCorrespondence);
      _xblockexpression = this.addCorrespondence(pcmInterface, jamoppType, parentCorrespondence);
    }
    return _xblockexpression;
  }
  
  private void createMethodCorrespondence(final MethodLevelSourceCodeLink methodLink) {
    throw new Error("Unresolved compilation problems:"
      + "\nOperationSignature cannot be resolved to a type."
      + "\ninterface__OperationSignature cannot be resolved"
      + "\nparameters__OperationSignature cannot be resolved"
      + "\nentityName cannot be resolved");
  }
  
  private void createDataTypeCorrespondence(final DataTypeSourceCodeLink dataTypeLink) {
    DataType pcmDataType = dataTypeLink.getPcmDataType();
    Type _jaMoPPType = dataTypeLink.getJaMoPPType();
    EObject _resolveJaMoppProxy = this.resolveJaMoppProxy(_jaMoPPType);
    Type jamoppType = ((Type) _resolveJaMoppProxy);
    org.emftext.language.java.containers.Package _rootPackage = this.getRootPackage();
    SameTypeCorrespondence parentCorrespondence = this.getUniqueSameTypeCorrespondence(this.pcmRepo, _rootPackage);
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
          EObject _resolveJaMoppProxy_1 = this.resolveJaMoppProxy(_field);
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
    final Function1<org.emftext.language.java.containers.Package, Boolean> _function = new Function1<org.emftext.language.java.containers.Package, Boolean>() {
      @Override
      public Boolean apply(final org.emftext.language.java.containers.Package pack) {
        String _namespacesAsString = pack.getNamespacesAsString();
        String _name = pack.getName();
        String _plus = (_namespacesAsString + _name);
        return Boolean.valueOf(finalNamespace.equals(_plus));
      }
    };
    return IterableExtensions.<org.emftext.language.java.containers.Package>findFirst(this.packages, _function);
  }
  
  /**
   * Returns the resolved EObject for the given jaMopp proxy.
   */
  private EObject resolveJaMoppProxy(final EObject proxy) {
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
    return EcoreUtil.resolve(proxy, this.jaMoppResourceSet);
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
}
