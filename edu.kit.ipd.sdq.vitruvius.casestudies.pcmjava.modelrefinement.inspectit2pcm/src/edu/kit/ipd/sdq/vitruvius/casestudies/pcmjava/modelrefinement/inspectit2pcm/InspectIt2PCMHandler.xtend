package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.modelrefinement.inspectit2pcm

import de.uka.ipd.sdq.workflow.jobs.JobFailedException
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJavaUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl
import java.io.FileInputStream
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Properties
import java.util.concurrent.Callable
import org.apache.log4j.Logger
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jface.viewers.ISelection
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.ui.handlers.HandlerUtil
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Method
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification
import org.somox.analyzer.SimpleAnalysisResult
import org.somox.analyzer.simplemodelanalyzer.jobs.SoMoXBlackboard
import org.somox.ejbmox.inspectit2pcm.II2PCMConfiguration
import org.somox.ejbmox.inspectit2pcm.II2PCMJob
import org.somox.ejbmox.inspectit2pcm.launch.II2PCMConfigurationBuilder
import org.somox.ejbmox.inspectit2pcm.launch.InspectIT2PCMConfigurationAttributes
import org.somox.sourcecodedecorator.SourceCodeDecoratorRepository
import org.somox.sourcecodedecorator.SourcecodedecoratorFactory

import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil.*
import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace

/** 
 * Handler to enrich the coevolved PCM models with resource demands. Is based on the II2PCMJob from
 * EJBmox. Instead of the SourceCodeDecoratorModel from SoMoX, however, it uses the Vitruvius
 * correspondence model to get links between architectural models and source code.
 * @author langhamm
 */
class InspectIt2PCMHandler extends AbstractHandler {
	static final String INI_CONFIG_FILE_NAME = "InspectIt2PCMHandler"
	static final Logger logger = Logger.getLogger(InspectIt2PCMHandler.getSimpleName())

	override Object execute(ExecutionEvent event) throws ExecutionException {
		val ISelection selection = HandlerUtil.getActiveMenuSelection(event)
		val IStructuredSelection structuredSelection = (selection as IStructuredSelection)
		val Object firstElement = structuredSelection.getFirstElement()
		val IJavaProject javaProject = (firstElement as IJavaProject)
		val IProject project = javaProject.getProject()
		val II2PCMJob ii2PCMJob = new II2PCMJob()
		val II2PCMConfiguration ii2PCMConfiguration = this.createIIC2PCMConfiguration(project)
		ii2PCMJob.setJobConfiguration(ii2PCMConfiguration)
		val vsumImpl = getVSUM
		val SoMoXBlackboard blackboard = this.createSoMoXBlackboard(project, vsumImpl)
		ii2PCMJob.setBlackboard(blackboard)
		try {
			//necessary in order to allow manipulation of the repo. 
			EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(new Callable<Void>() {

           override public Void call() throws Exception {
				ii2PCMJob.execute(new NullProgressMonitor())
				return null	
            }
        }, vsumImpl);			
		} catch (JobFailedException e) {
			throw new RuntimeException('''Could not execute II2PCM Job. Reason: «e.toString()»''', e)
		} catch (UserCanceledException e) {
			throw new RuntimeException('''Could not execute II2PCM Job. Reason: «e.toString()»''', e)
		}

		return null
	}

	def private II2PCMConfiguration createIIC2PCMConfiguration(IProject project) {
		val II2PCMConfigurationBuilder newConfigBuilder = new II2PCMConfigurationBuilder()
		val Map<String, Object> attributes = new HashMap<String, Object>()
		var String cmrURL = II2PCMConfiguration.CMR_REST_API_DEFAULT
		var Integer warmUpMeasurements = II2PCMConfiguration.WARMUP_MEASUREMENTS_DEFAULT
		var Boolean ensureInternalActionsBeforeStopActions = false
		val Properties properties = new Properties()
		try {
			val String configFileName = this.findConfigFileOSString(project)
			if (!configFileName.nullOrEmpty) {
				properties.load(new FileInputStream(configFileName))
				cmrURL = properties.getProperty(InspectIT2PCMConfigurationAttributes.CMR_REST_API_URL, cmrURL)
				warmUpMeasurements = Integer.valueOf(
					properties.getProperty(InspectIT2PCMConfigurationAttributes.WARMUP_MEASUREMENTS,
						warmUpMeasurements.toString()))
				ensureInternalActionsBeforeStopActions = Boolean.valueOf(
					properties.getProperty(
						InspectIT2PCMConfigurationAttributes.ENSURE_INTERNAL_ACTIONS_BEFORE_STOP_ACTION,
						ensureInternalActionsBeforeStopActions.toString()))
			}
		} catch (IOException e) {
			logger.info("Could not load config file. Using default configurations instead ", e)
		}

		attributes.put(InspectIT2PCMConfigurationAttributes.CMR_REST_API_URL, cmrURL)
		attributes.put(InspectIT2PCMConfigurationAttributes.WARMUP_MEASUREMENTS, warmUpMeasurements)
		attributes.put(InspectIT2PCMConfigurationAttributes.ENSURE_INTERNAL_ACTIONS_BEFORE_STOP_ACTION,
			ensureInternalActionsBeforeStopActions)
		return newConfigBuilder.buildConfiguration(attributes)
	}

	def private SoMoXBlackboard createSoMoXBlackboard(IProject project, VSUMImpl vsum) {
		val SoMoXBlackboard blackboard = new SoMoXBlackboard()
		val SimpleAnalysisResult anlysisResult = new SimpleAnalysisResult(null)
		val Repository repository = this.findRepositoryInProject(project)
		anlysisResult.setInternalArchitectureModel(repository)
		val SourceCodeDecoratorRepository sourceCodeDecorator = this.
			createSourceCodeDecoratorModelFromVitruvCorrespondenceModel(repository, vsum)
		anlysisResult.setSourceCodeDecoratorRepository(sourceCodeDecorator)
		blackboard.setAnalysisResult(anlysisResult)
		return blackboard
	}

	def private Repository findRepositoryInProject(IProject project) {
		val IResource repoResource = this.findResourceWithEnding(project, null, "repository", true)
		val URI emfURIRepo = EMFBridge.getEMFPlatformUriForIResource(repoResource)
		val ResourceSet rs = new ResourceSetImpl()
		val Resource resource = EcoreResourceBridge.loadResourceAtURI(emfURIRepo, rs)
		val Repository repo = EcoreResourceBridge.
			getUniqueContentRootIfCorrectlyTyped(resource, "repository", Repository)
		return repo
	}

	def private IResource findResourceWithEnding(IProject project, String fileName, String fileEnding,
		boolean claimOne) {
		val List<IResource> iResources = new ArrayList<IResource>()
		try {
			project.accept([ IResource resource |
				val String name = resource.getName()
				if (name.contains(".")) {
					val String actualFileName = name.substring(0, name.lastIndexOf("."))
					val String actualFieEnding = name.substring(name.lastIndexOf("."), name.length())
					val boolean nameEquals = null === fileName || fileName.equalsIgnoreCase(actualFileName)
					val boolean endingEquals = ("." +fileEnding).endsWith(actualFieEnding)
					if (nameEquals && endingEquals) {
						iResources.add(resource)
					}
				}
				return true
			])
		} catch (CoreException e) {
			throw new RuntimeException(e)
		}

		if (claimOne) {
			return CollectionBridge.claimOne(iResources)
		} else {
			return CollectionBridge.claimNotMany(iResources)
		}
	}

	def private String findConfigFileOSString(IProject project) {
		val IResource resource = this.findResourceWithEnding(project, INI_CONFIG_FILE_NAME, "ini", false)
		if(null == resource){
			return null
		}
		return EclipseBridge.getAbsolutePathString(resource)
	}

	/** 
	 * The method creates a SCDM based on the Vitruvius correspondence model. The resulting SCDM can
	 * be used by the II2PCMJob to annotate the InternalActions with resource demands. The SCDM is
	 * not complete. It only contains the SEFF2MethodMappings and the InterfaceSourceCodeLinks -
	 * nothing else.
	 */
	def private SourceCodeDecoratorRepository createSourceCodeDecoratorModelFromVitruvCorrespondenceModel(Repository repo, VSUMImpl vsum) {
		val SourceCodeDecoratorRepository sourceCodeDecoratorModel = SourcecodedecoratorFactory.eINSTANCE.
			createSourceCodeDecoratorRepository()
		val CorrespondenceInstance<Correspondence> correspondenceInstance = this.getCorrespondenceInstance(vsum)
		val methods = correspondenceInstance.getAllEObjectsOfTypeInCorrespondences(Method)
		for (Method method : methods) {
			if (method instanceof ClassMethod) {
				val classMethod = method as ClassMethod
				val correspondingSEFFs = correspondenceInstance.getCorrespondingEObjectsByType(classMethod, ServiceEffectSpecification)
				if (!correspondingSEFFs.nullOrEmpty) {
					// --> create SEFF 2 Methodmapping
					correspondingSEFFs.forEach [
						val seff2Method = SourcecodedecoratorFactory.eINSTANCE.createSEFF2MethodMapping
						seff2Method.seff = it
						seff2Method.statementListContainer = classMethod
						sourceCodeDecoratorModel.seff2MethodMappings.add(seff2Method)
					]
				}
			}
		}
		val concreteClassifiers = correspondenceInstance.getAllEObjectsOfTypeInCorrespondences(ConcreteClassifier)
		for(concreteClassifier : concreteClassifiers){
			val correspondingOpIfs = correspondenceInstance.getCorrespondingEObjectsByType(concreteClassifier, OperationInterface)
			if(!correspondingOpIfs.nullOrEmpty){
				correspondingOpIfs.forEach[
					val interfaceLink = SourcecodedecoratorFactory.eINSTANCE.createInterfaceSourceCodeLink
					interfaceLink.gastClass = concreteClassifier
					interfaceLink.interface = it
					sourceCodeDecoratorModel.interfaceSourceCodeLink.add(interfaceLink)
				]
			}
		}
		
		
		sourceCodeDecoratorModel.seff2MethodMappings.forEach[
			val resolvedSeff = EcoreUtil.resolve(it.seff, repo) as ServiceEffectSpecification
			it.seff = resolvedSeff	
		]
		return sourceCodeDecoratorModel
	}

	def private CorrespondenceInstance<Correspondence> getCorrespondenceInstance(VSUMImpl vsum) {
		val VURI jaMoPPVURI = VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE)
		val VURI pcmVURI = VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE)
		val CorrespondenceInstance<Correspondence> corresponcenceInstance = vsum.
			getCorrespondenceInstanceOriginal(pcmVURI, jaMoPPVURI)
		return corresponcenceInstance
	}
	
	def private getVSUM(){
		val MetaRepositoryImpl metaRepository = PCMJavaUtils.createPCMJavaMetarepository()
		val VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository, metaRepository)
		vsum.getOrCreateAllCorrespondenceInstancesForMM(
			metaRepository.getMetamodel(VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE)))
		return vsum
	}
}
