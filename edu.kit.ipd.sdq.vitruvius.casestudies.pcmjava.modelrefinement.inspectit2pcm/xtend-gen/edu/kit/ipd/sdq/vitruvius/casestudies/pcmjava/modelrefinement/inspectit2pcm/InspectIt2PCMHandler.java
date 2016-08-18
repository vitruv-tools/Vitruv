package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.modelrefinement.inspectit2pcm;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.somox.analyzer.SimpleAnalysisResult;
import org.somox.analyzer.simplemodelanalyzer.jobs.SoMoXBlackboard;
import org.somox.sourcecodedecorator.InterfaceSourceCodeLink;
import org.somox.sourcecodedecorator.SEFF2MethodMapping;
import org.somox.sourcecodedecorator.SourceCodeDecoratorRepository;
import org.somox.sourcecodedecorator.SourcecodedecoratorFactory;

/**
 * Handler to enrich the coevolved PCM models with resource demands. Is based on the II2PCMJob from
 * EJBmox. Instead of the SourceCodeDecoratorModel from SoMoX, however, it uses the Vitruvius
 * correspondence model to get links between architectural models and source code.
 * @author langhamm
 */
@SuppressWarnings("all")
public class InspectIt2PCMHandler extends AbstractHandler {
  private final static String INI_CONFIG_FILE_NAME = "InspectIt2PCMHandler";
  
  private final static Logger logger = Logger.getLogger(InspectIt2PCMHandler.class.getSimpleName());
  
  @Override
  public Object execute(final ExecutionEvent event) throws ExecutionException {
    throw new Error("Unresolved compilation problems:"
      + "\nII2PCMJob cannot be resolved to a type."
      + "\nII2PCMConfiguration cannot be resolved to a type."
      + "\nII2PCMJob cannot be resolved."
      + "\nThe method createIIC2PCMConfiguration(IProject) from the type InspectIt2PCMHandler refers to the missing type II2PCMConfiguration"
      + "\nsetJobConfiguration cannot be resolved"
      + "\nsetBlackboard cannot be resolved"
      + "\nexecute cannot be resolved");
  }
  
  private /* II2PCMConfiguration */Object createIIC2PCMConfiguration(final IProject project) {
    throw new Error("Unresolved compilation problems:"
      + "\nII2PCMConfigurationBuilder cannot be resolved to a type."
      + "\nII2PCMConfigurationBuilder cannot be resolved."
      + "\nThe method or field II2PCMConfiguration is undefined"
      + "\nThe method or field II2PCMConfiguration is undefined"
      + "\nThe method or field InspectIT2PCMConfigurationAttributes is undefined"
      + "\nThe method or field InspectIT2PCMConfigurationAttributes is undefined"
      + "\nThe method or field InspectIT2PCMConfigurationAttributes is undefined"
      + "\nThe method or field InspectIT2PCMConfigurationAttributes is undefined"
      + "\nThe method or field InspectIT2PCMConfigurationAttributes is undefined"
      + "\nThe method or field InspectIT2PCMConfigurationAttributes is undefined"
      + "\nCMR_REST_API_DEFAULT cannot be resolved"
      + "\nWARMUP_MEASUREMENTS_DEFAULT cannot be resolved"
      + "\nCMR_REST_API_URL cannot be resolved"
      + "\nWARMUP_MEASUREMENTS cannot be resolved"
      + "\nENSURE_INTERNAL_ACTIONS_BEFORE_STOP_ACTION cannot be resolved"
      + "\nCMR_REST_API_URL cannot be resolved"
      + "\nWARMUP_MEASUREMENTS cannot be resolved"
      + "\nENSURE_INTERNAL_ACTIONS_BEFORE_STOP_ACTION cannot be resolved"
      + "\nbuildConfiguration cannot be resolved");
  }
  
  private SoMoXBlackboard createSoMoXBlackboard(final IProject project, final VSUMImpl vsum) {
    final SoMoXBlackboard blackboard = new SoMoXBlackboard();
    final SimpleAnalysisResult anlysisResult = new SimpleAnalysisResult(null);
    final Repository repository = this.findRepositoryInProject(project);
    anlysisResult.setInternalArchitectureModel(repository);
    final SourceCodeDecoratorRepository sourceCodeDecorator = this.createSourceCodeDecoratorModelFromVitruvCorrespondenceModel(repository, vsum);
    anlysisResult.setSourceCodeDecoratorRepository(sourceCodeDecorator);
    blackboard.setAnalysisResult(anlysisResult);
    return blackboard;
  }
  
  private Repository findRepositoryInProject(final IProject project) {
    final IResource repoResource = this.findResourceWithEnding(project, null, "repository", true);
    final URI emfURIRepo = EMFBridge.getEMFPlatformUriForIResource(repoResource);
    final ResourceSet rs = new ResourceSetImpl();
    final Resource resource = EcoreResourceBridge.loadResourceAtURI(emfURIRepo, rs);
    final Repository repo = EcoreResourceBridge.<Repository>getUniqueContentRootIfCorrectlyTyped(resource, "repository", Repository.class);
    return repo;
  }
  
  private IResource findResourceWithEnding(final IProject project, final String fileName, final String fileEnding, final boolean claimOne) {
    final List<IResource> iResources = new ArrayList<IResource>();
    try {
      final IResourceVisitor _function = (IResource resource) -> {
        final String name = resource.getName();
        boolean _contains = name.contains(".");
        if (_contains) {
          int _lastIndexOf = name.lastIndexOf(".");
          final String actualFileName = name.substring(0, _lastIndexOf);
          int _lastIndexOf_1 = name.lastIndexOf(".");
          int _length = name.length();
          final String actualFieEnding = name.substring(_lastIndexOf_1, _length);
          final boolean nameEquals = ((null == fileName) || fileName.equalsIgnoreCase(actualFileName));
          final boolean endingEquals = ("." + fileEnding).endsWith(actualFieEnding);
          if ((nameEquals && endingEquals)) {
            iResources.add(resource);
          }
        }
        return true;
      };
      project.accept(_function);
    } catch (final Throwable _t) {
      if (_t instanceof CoreException) {
        final CoreException e = (CoreException)_t;
        throw new RuntimeException(e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    if (claimOne) {
      return CollectionBridge.<IResource>claimOne(iResources);
    } else {
      return CollectionBridge.<IResource>claimNotMany(iResources);
    }
  }
  
  private String findConfigFileOSString(final IProject project) {
    final IResource resource = this.findResourceWithEnding(project, InspectIt2PCMHandler.INI_CONFIG_FILE_NAME, "ini", false);
    boolean _equals = Objects.equal(null, resource);
    if (_equals) {
      return null;
    }
    return EclipseBridge.getAbsolutePathString(resource);
  }
  
  /**
   * The method creates a SCDM based on the Vitruvius correspondence model. The resulting SCDM can
   * be used by the II2PCMJob to annotate the InternalActions with resource demands. The SCDM is
   * not complete. It only contains the SEFF2MethodMappings and the InterfaceSourceCodeLinks -
   * nothing else.
   */
  private SourceCodeDecoratorRepository createSourceCodeDecoratorModelFromVitruvCorrespondenceModel(final Repository repo, final VSUMImpl vsum) {
    final SourceCodeDecoratorRepository sourceCodeDecoratorModel = SourcecodedecoratorFactory.eINSTANCE.createSourceCodeDecoratorRepository();
    final CorrespondenceInstance<Correspondence> correspondenceInstance = this.getCorrespondenceInstance(vsum);
    final Set<Method> methods = CorrespondenceInstanceUtil.<Method, Correspondence>getAllEObjectsOfTypeInCorrespondences(correspondenceInstance, Method.class);
    for (final Method method : methods) {
      if ((method instanceof ClassMethod)) {
        final ClassMethod classMethod = ((ClassMethod) method);
        final Set<ServiceEffectSpecification> correspondingSEFFs = CorrespondenceInstanceUtil.<ServiceEffectSpecification, Correspondence>getCorrespondingEObjectsByType(correspondenceInstance, classMethod, ServiceEffectSpecification.class);
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(correspondingSEFFs);
        boolean _not = (!_isNullOrEmpty);
        if (_not) {
          final Consumer<ServiceEffectSpecification> _function = (ServiceEffectSpecification it) -> {
            final SEFF2MethodMapping seff2Method = SourcecodedecoratorFactory.eINSTANCE.createSEFF2MethodMapping();
            seff2Method.setSeff(it);
            seff2Method.setStatementListContainer(classMethod);
            EList<SEFF2MethodMapping> _seff2MethodMappings = sourceCodeDecoratorModel.getSeff2MethodMappings();
            _seff2MethodMappings.add(seff2Method);
          };
          correspondingSEFFs.forEach(_function);
        }
      }
    }
    final Set<ConcreteClassifier> concreteClassifiers = CorrespondenceInstanceUtil.<ConcreteClassifier, Correspondence>getAllEObjectsOfTypeInCorrespondences(correspondenceInstance, ConcreteClassifier.class);
    for (final ConcreteClassifier concreteClassifier : concreteClassifiers) {
      {
        final Set<OperationInterface> correspondingOpIfs = CorrespondenceInstanceUtil.<OperationInterface, Correspondence>getCorrespondingEObjectsByType(correspondenceInstance, concreteClassifier, OperationInterface.class);
        boolean _isNullOrEmpty_1 = IterableExtensions.isNullOrEmpty(correspondingOpIfs);
        boolean _not_1 = (!_isNullOrEmpty_1);
        if (_not_1) {
          final Consumer<OperationInterface> _function_1 = (OperationInterface it) -> {
            final InterfaceSourceCodeLink interfaceLink = SourcecodedecoratorFactory.eINSTANCE.createInterfaceSourceCodeLink();
            interfaceLink.setGastClass(concreteClassifier);
            interfaceLink.setInterface(it);
            EList<InterfaceSourceCodeLink> _interfaceSourceCodeLink = sourceCodeDecoratorModel.getInterfaceSourceCodeLink();
            _interfaceSourceCodeLink.add(interfaceLink);
          };
          correspondingOpIfs.forEach(_function_1);
        }
      }
    }
    EList<SEFF2MethodMapping> _seff2MethodMappings = sourceCodeDecoratorModel.getSeff2MethodMappings();
    final Consumer<SEFF2MethodMapping> _function_1 = (SEFF2MethodMapping it) -> {
      ServiceEffectSpecification _seff = it.getSeff();
      EObject _resolve = EcoreUtil.resolve(_seff, repo);
      final ServiceEffectSpecification resolvedSeff = ((ServiceEffectSpecification) _resolve);
      it.setSeff(resolvedSeff);
    };
    _seff2MethodMappings.forEach(_function_1);
    return sourceCodeDecoratorModel;
  }
  
  private CorrespondenceInstance<Correspondence> getCorrespondenceInstance(final VSUMImpl vsum) {
    final VURI jaMoPPVURI = VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE);
    final VURI pcmVURI = VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE);
    final CorrespondenceInstance<Correspondence> corresponcenceInstance = vsum.getCorrespondenceInstanceOriginal(pcmVURI, jaMoPPVURI);
    return corresponcenceInstance;
  }
  
  private VSUMImpl getVSUM() {
    final MetaRepositoryImpl metaRepository = PCMJavaUtils.createPCMJavaMetarepository();
    final VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository, metaRepository);
    VURI _instance = VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE);
    Metamodel _metamodel = metaRepository.getMetamodel(_instance);
    vsum.getOrCreateAllCorrespondenceInstancesForMM(_metamodel);
    return vsum;
  }
}
