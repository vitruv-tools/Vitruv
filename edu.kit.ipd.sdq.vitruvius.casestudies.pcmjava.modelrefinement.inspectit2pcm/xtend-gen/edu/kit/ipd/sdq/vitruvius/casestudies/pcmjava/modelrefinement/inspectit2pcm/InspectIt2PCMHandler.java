package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.modelrefinement.inspectit2pcm;

import com.google.common.base.Objects;
import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.somox.analyzer.SimpleAnalysisResult;
import org.somox.analyzer.simplemodelanalyzer.jobs.SoMoXBlackboard;
import org.somox.ejbmox.inspectit2pcm.II2PCMConfiguration;
import org.somox.ejbmox.inspectit2pcm.II2PCMJob;
import org.somox.ejbmox.inspectit2pcm.launch.II2PCMConfigurationBuilder;
import org.somox.ejbmox.inspectit2pcm.launch.InspectIT2PCMConfigurationAttributes;
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
    final ISelection selection = HandlerUtil.getActiveMenuSelection(event);
    final IStructuredSelection structuredSelection = ((IStructuredSelection) selection);
    final Object firstElement = structuredSelection.getFirstElement();
    final IJavaProject javaProject = ((IJavaProject) firstElement);
    final IProject project = javaProject.getProject();
    final II2PCMJob ii2PCMJob = new II2PCMJob();
    final II2PCMConfiguration ii2PCMConfiguration = this.createIIC2PCMConfiguration(project);
    ii2PCMJob.setJobConfiguration(ii2PCMConfiguration);
    final VSUMImpl vsumImpl = this.getVSUM();
    final SoMoXBlackboard blackboard = this.createSoMoXBlackboard(project, vsumImpl);
    ii2PCMJob.setBlackboard(blackboard);
    try {
      EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(new Callable<Void>() {
        @Override
        public Void call() throws Exception {
          NullProgressMonitor _nullProgressMonitor = new NullProgressMonitor();
          ii2PCMJob.execute(_nullProgressMonitor);
          return null;
        }
      }, vsumImpl);
    } catch (final Throwable _t) {
      if (_t instanceof JobFailedException) {
        final JobFailedException e = (JobFailedException)_t;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Could not execute II2PCM Job. Reason: ");
        String _string = e.toString();
        _builder.append(_string, "");
        throw new RuntimeException(_builder.toString(), e);
      } else if (_t instanceof UserCanceledException) {
        final UserCanceledException e_1 = (UserCanceledException)_t;
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("Could not execute II2PCM Job. Reason: ");
        String _string_1 = e_1.toString();
        _builder_1.append(_string_1, "");
        throw new RuntimeException(_builder_1.toString(), e_1);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return null;
  }
  
  private II2PCMConfiguration createIIC2PCMConfiguration(final IProject project) {
    final II2PCMConfigurationBuilder newConfigBuilder = new II2PCMConfigurationBuilder();
    final Map<String, Object> attributes = new HashMap<String, Object>();
    String cmrURL = II2PCMConfiguration.CMR_REST_API_DEFAULT;
    Integer warmUpMeasurements = II2PCMConfiguration.WARMUP_MEASUREMENTS_DEFAULT;
    Boolean ensureInternalActionsBeforeStopActions = Boolean.valueOf(false);
    final Properties properties = new Properties();
    try {
      final String configFileName = this.findConfigFileOSString(project);
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(configFileName);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        FileInputStream _fileInputStream = new FileInputStream(configFileName);
        properties.load(_fileInputStream);
        String _property = properties.getProperty(InspectIT2PCMConfigurationAttributes.CMR_REST_API_URL, cmrURL);
        cmrURL = _property;
        String _string = warmUpMeasurements.toString();
        String _property_1 = properties.getProperty(InspectIT2PCMConfigurationAttributes.WARMUP_MEASUREMENTS, _string);
        Integer _valueOf = Integer.valueOf(_property_1);
        warmUpMeasurements = _valueOf;
        String _string_1 = ensureInternalActionsBeforeStopActions.toString();
        String _property_2 = properties.getProperty(
          InspectIT2PCMConfigurationAttributes.ENSURE_INTERNAL_ACTIONS_BEFORE_STOP_ACTION, _string_1);
        Boolean _valueOf_1 = Boolean.valueOf(_property_2);
        ensureInternalActionsBeforeStopActions = _valueOf_1;
      }
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        InspectIt2PCMHandler.logger.info("Could not load config file. Using default configurations instead ", e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    attributes.put(InspectIT2PCMConfigurationAttributes.CMR_REST_API_URL, cmrURL);
    attributes.put(InspectIT2PCMConfigurationAttributes.WARMUP_MEASUREMENTS, warmUpMeasurements);
    attributes.put(InspectIT2PCMConfigurationAttributes.ENSURE_INTERNAL_ACTIONS_BEFORE_STOP_ACTION, ensureInternalActionsBeforeStopActions);
    return newConfigBuilder.buildConfiguration(attributes);
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
          boolean _or = false;
          if ((null == fileName)) {
            _or = true;
          } else {
            boolean _equalsIgnoreCase = fileName.equalsIgnoreCase(actualFileName);
            _or = _equalsIgnoreCase;
          }
          final boolean nameEquals = _or;
          final boolean endingEquals = ("." + fileEnding).endsWith(actualFieEnding);
          boolean _and = false;
          if (!nameEquals) {
            _and = false;
          } else {
            _and = endingEquals;
          }
          if (_and) {
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
    final VURI jaMoPPVURI = VURI.getInstance(PCMJaMoPPNamespace.JaMoPP.JAMOPP_METAMODEL_NAMESPACE);
    final VURI pcmVURI = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
    final CorrespondenceInstance<Correspondence> corresponcenceInstance = vsum.getCorrespondenceInstanceOriginal(pcmVURI, jaMoPPVURI);
    return corresponcenceInstance;
  }
  
  private VSUMImpl getVSUM() {
    final MetaRepositoryImpl metaRepository = PCMJavaUtils.createPCMJavaMetarepository();
    final VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository, metaRepository);
    VURI _instance = VURI.getInstance(PCMJaMoPPNamespace.PCM.PCM_METAMODEL_NAMESPACE);
    Metamodel _metamodel = metaRepository.getMetamodel(_instance);
    vsum.getOrCreateAllCorrespondenceInstancesForMM(_metamodel);
    return vsum;
  }
}
