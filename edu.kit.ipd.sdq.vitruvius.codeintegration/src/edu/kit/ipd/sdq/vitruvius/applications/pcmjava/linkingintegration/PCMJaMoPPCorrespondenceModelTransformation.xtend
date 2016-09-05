package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.linkingintegration

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelProviding
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl
import java.util.HashSet
import java.util.List
import java.util.Set
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.core.runtime.IPath
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.Accessors
import org.emftext.language.java.commons.Commentable
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.parameters.OrdinaryParameter
import org.emftext.language.java.types.Type
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.repository.Repository
import org.somox.sourcecodedecorator.ComponentImplementingClassesLink
import org.somox.sourcecodedecorator.DataTypeSourceCodeLink
import org.somox.sourcecodedecorator.InterfaceSourceCodeLink
import org.somox.sourcecodedecorator.MethodLevelSourceCodeLink
import org.somox.sourcecodedecorator.impl.SourceCodeDecoratorRepositoryImpl


import static extension edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.parameters.Parametrizable
import edu.kit.ipd.sdq.vitruvius.domains.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.domains.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.extensions.integration.correspondence.util.IntegrationCorrespondenceHelper

/**
 * Class that creates correspondences between PCM and JaMopp model elements.
 * 
 * @author originally by Benjamin Hettwer, changed for thesis by Frederik Petersen
 */
class PCMJaMoPPCorrespondenceModelTransformation {

	private HashSet<String> existingEntries = new HashSet
	protected Logger logger = Logger.getRootLogger

	// Absolute paths needed
	private String scdmPath // in, single file
	private String pcmPath // in, single file
	private List<IPath> jamoppPaths // all src folders
	@Accessors(PUBLIC_GETTER)
	private Resource scdm
	private Resource pcm
	@Accessors(PUBLIC_GETTER)
	private List<Resource> jaMoppResources
	private Repository pcmRepo
	@Accessors(PUBLIC_GETTER)
	private CorrespondenceModel cInstance

	private ModelProviding modelProviding

	private Set<Package> packages
	private Package rootPackage

	private IPath projectBase

	new(String scdmPath, String pcmPath, List<IPath> jamoppPaths, VSUMImpl vsum, IPath projectBase) {

		// Initialize CorrepondenceInstance for PCM <-> JaMoPP mappings
		var mmUriA = VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE)
		var mmURiB = VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE)
		// FIXME do that without a cast
		this.cInstance = vsum.getCorrespondenceModel(mmUriA, mmURiB)

		this.scdmPath = scdmPath
		this.pcmPath = pcmPath
		this.jamoppPaths = jamoppPaths
		this.packages = new HashSet<Package>()
		this.projectBase = projectBase
		this.modelProviding = vsum
		logger.level = Level.ALL
	}

	def createCorrespondences() {
		prepareTransformation()
		createPCMtoJaMoppCorrespondences()
	}

	/**
	 * Loads PCM, SDCDM and JaMoPP resources.
	 */
	private def prepareTransformation() {

		// Load pcm, scdm and jamopp resourceSet
		scdm = ResourceLoadingHelper.loadSCDMResource(scdmPath)
		pcm = ResourceLoadingHelper.loadPCMRepositoryResource(pcmPath)
		pcmRepo = pcm.contents.get(0) as Repository
		jaMoppResources = ResourceLoadingHelper.loadJaMoPPResourceSet(jamoppPaths.map[path | path.toFile])

		// Get all jaMopp packages from resourceSet  
		jaMoppResources.forEach[packages.addAll((contents.filter(typeof(Package))))]
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
	private def createPCMtoJaMoppCorrespondences() {

		var scdmRepo = scdm.contents.get(0) as SourceCodeDecoratorRepositoryImpl

		// Add Repository <-> Package Correspondence only for top package
		createRepoPackageCorrespondence()

		scdmRepo.componentImplementingClassesLink.forEach[createComponentClassCorrespondence]

		scdmRepo.interfaceSourceCodeLink.forEach[createInterfaceCorrespondence]

		scdmRepo.methodLevelSourceCodeLink.forEach[createMethodCorrespondence]

		scdmRepo.dataTypeSourceCodeLink.forEach[createDataTypeCorrespondence]

		findAndExecuteAfterTransformationExtensions()

		// forces saving of correspondence instance
		this.modelProviding.saveExistingModelInstanceOriginal(VURI.getInstance(pcmRepo.eResource))
	}

	def private findAndExecuteAfterTransformationExtensions() {
		EclipseBridge.getRegisteredExtensions(PCMJaMoPPIntegrationExtending.ID,
			VitruviusConstants.getExtensionPropertyName(), PCMJaMoPPIntegrationExtending).forEach [
			it.afterBasicTransformations(this)
		]

	}

	private def createRepoPackageCorrespondence() {
		addCorrespondence(pcmRepo, getRootPackage)
	}

//	private def createRepoPackageCorrespondence(Package jaMoppPackage) {
//		addCorrespondence(pcmRepo, jaMoppPackage)
//	}
	private def createComponentClassCorrespondence(ComponentImplementingClassesLink componentClassLink) {
		var pcmComponent = componentClassLink.component

		// CompositeComponents do not have a corresponding implementing class
		// TODO: What correspondence for compComponent ?
		if (pcmComponent instanceof BasicComponent) {

			for (implementingClass : componentClassLink.implementingClasses) {
				val desreolvedClassInSCDM = deresolveIfNesessary(implementingClass)
				var jamoppClass = resolveJaMoppProxy(desreolvedClassInSCDM)
				val package = getPackageForCommentable(jamoppClass)
	
				val deresolvedPcmRepo = deresolveIfNesessary(pcmRepo)
				val deresolvedRootPackage = deresolveIfNesessary(getRootPackage)
	
				var parentRepoPackageCorr = cInstance.getCorrespondencesBetweenEObjects(deresolvedPcmRepo.toSet,
					deresolvedRootPackage.toSet).claimOne
	
				// 2. Component <-> Package correspondence
				addCorrespondence(pcmComponent, package, parentRepoPackageCorr)
	
				// 3. Component <-> CompUnit correspondence
				addCorrespondence(pcmComponent, jamoppClass.containingCompilationUnit, parentRepoPackageCorr)
	
				// 4. Component <-> Class correspondence
				addCorrespondence(pcmComponent, jamoppClass, parentRepoPackageCorr)
			}
		}
	}

	private def createInterfaceCorrespondence(InterfaceSourceCodeLink interfaceLink) {
		var pcmInterface = interfaceLink.interface
		var jamoppType = resolveJaMoppProxy(interfaceLink.gastClass) as Type

		// Get parent Repository <-> Package correspondence from correspondence instance	
		val deresolvedPcmRepo = deresolveIfNesessary(pcmRepo)
		val deresolvedRootPackage = deresolveIfNesessary(getRootPackage)
		var parentCorrespondence = cInstance.getCorrespondencesBetweenEObjects(deresolvedPcmRepo.toSet,
			deresolvedRootPackage.toSet).claimOne

		// 5. PCM Interface <-> CompUnit correspondence
		addCorrespondence(pcmInterface, jamoppType.containingCompilationUnit, parentCorrespondence)

		// 6. PCM Interface <-> Type correspondence
		addCorrespondence(pcmInterface, jamoppType, parentCorrespondence)
	}

	private def createMethodCorrespondence(MethodLevelSourceCodeLink methodLink) {
		val jamoppFunction = resolveJaMoppProxy(methodLink.function)
		// need both these interfaces below which constructor and method both implement.
		// No common interface for both in the hierarchy though.
		var Commentable jamoppCommentable
		var Parametrizable jamoppParametrizable
		if (jamoppFunction instanceof Method) {
			jamoppCommentable = jamoppFunction
			jamoppParametrizable = jamoppFunction
		} else if (jamoppFunction instanceof Constructor) {
			jamoppCommentable = jamoppFunction
			jamoppParametrizable = jamoppFunction
		} else {
			throw new RuntimeException("Unexpected type in method level source code link.")
		}
		var pcmMethod = methodLink.operation as OperationSignature
		var jamoppInterface = jamoppCommentable.containingConcreteClassifier
		var pcmInterface = pcmMethod.interface__OperationSignature

		// Get parent Interface <-> Type correspondence from correspondence instance
		val deresolvedPcmInterface = deresolveIfNesessary(pcmInterface)
		val deresolvedJamoppInterface = deresolveIfNesessary(jamoppInterface)
		var interfaceCorrespondence = cInstance.getCorrespondencesBetweenEObjects(deresolvedPcmInterface.toSet,
			deresolvedJamoppInterface.toSet)
		if (interfaceCorrespondence.nullOrEmpty) {
			return
		}

		// 7. OperationSignature <-> jaMopp Method correspondence
		var methodCorrespondence = addCorrespondence(pcmMethod, jamoppFunction, interfaceCorrespondence.get(0))

		for (pcmParam : pcmMethod.parameters__OperationSignature) {

			// Find matching jaMopp parameter by name
			var jamoppParam = jamoppParametrizable.parameters.findFirst[jp|jp.name.equals(pcmParam.entityName)]
			if (jamoppParam != null) {

				// 8. PCM Parameter <-> jaMopp Parameter correspondence	
				addCorrespondence(pcmParam, jamoppParam as OrdinaryParameter, methodCorrespondence)
			}
		}
	}

	private def createDataTypeCorrespondence(DataTypeSourceCodeLink dataTypeLink) {
		var pcmDataType = dataTypeLink.pcmDataType
		var jamoppType = resolveJaMoppProxy(dataTypeLink.jaMoPPType) as Type

		// Get parent Repository <-> Package correspondence from correspondence instance
		val deresolvedPcmRepo = deresolveIfNesessary(pcmRepo)
		val deresolvedRootPackage = deresolveIfNesessary(getRootPackage)
		var parentCorrespondence = cInstance.getCorrespondencesBetweenEObjects(deresolvedPcmRepo.toSet,
			deresolvedRootPackage.toSet).claimOne

		// 9. PCM DataType <-> JaMopp CompUnit correspondence
		addCorrespondence(pcmDataType, jamoppType.containingCompilationUnit, parentCorrespondence)

		// 10. PCM DataType <-> JaMopp Type correspondence
		var dataTypeCorrespondence = addCorrespondence(pcmDataType, jamoppType, parentCorrespondence)

		if (dataTypeLink.innerDatatypeSourceCodeLink != null) {
			for (innerDataTypeLink : dataTypeLink.innerDatatypeSourceCodeLink) {
				var innerDeclaration = innerDataTypeLink.innerDeclaration
				var jamoppField = resolveJaMoppProxy(innerDataTypeLink.field) as Field

				// 11.PCM InnerDeclaration <-> JaMopp Field correspondence
				addCorrespondence(innerDeclaration, jamoppField, dataTypeCorrespondence)
			}
		}
	}

	/**
	 * Returns the {@link Package} for the given {@link Commentable} or null if none was found.
	 */
	private def Package getPackageForCommentable(Commentable commentable) {
		var namespace = commentable.containingCompilationUnit.namespacesAsString
		if (namespace.endsWith("$") || namespace.endsWith(".")) {
			namespace = namespace.substring(0, namespace.length - 1)
		}
		val finalNamespace = namespace
		return packages.findFirst[pack|finalNamespace.equals(pack.namespacesAsString + pack.name)]
	}

	/** 
	 * Returns the resolved EObject for the given jaMopp proxy.
	 * */
	public def <T extends EObject> T resolveJaMoppProxy(T proxy) {
		if (proxy == null || !proxy.eIsProxy())
			return proxy
		return EcoreUtil.resolve(proxy, jaMoppResources.get(0).resourceSet) as T
	}

	/**
	 * Returns top-level package of the loaded jamopp resource set.
	 */
	private def Package getRootPackage() {
		if (rootPackage != null)
			return rootPackage

		// Let's assume it's the one with the shortest namespace
		rootPackage = packages.get(0)
		for (Package package : packages) {
			var fullyQualifiedName = package.namespacesAsString + package.name
			if (fullyQualifiedName.length < (rootPackage.name.length + rootPackage.namespacesAsString.length)) {
				rootPackage = package
			}
		}
		return rootPackage
	}

	protected def Correspondence addCorrespondence(EObject pcmObject, EObject jamoppObject) {
		return addCorrespondence(pcmObject, jamoppObject, null)
	}

	/**
	 * Creates an {@link EObjectCorrespondence} between the given EObjects
	 * and adds it to the {@link CorrespondenceModel}
	 */
	public def Correspondence addCorrespondence(EObject objectA, EObject objectB, Correspondence parent) {
		if (objectA == null || objectB == null)
			throw new IllegalArgumentException("Corresponding elements must not be null!")

		// deresolve Objects
		var deresolvedA = deresolveIfNesessary(objectA)
		var deresolvedB = deresolveIfNesessary(objectB)

		// check if the correspondence was already created, because the SCDM may contain duplicate entries
		var identifier = cInstance.calculateTUIDFromEObject(deresolvedA).toString +
			cInstance.calculateTUIDFromEObject(deresolvedB).toString
		var Correspondence correspondence = null
		if (!existingEntries.contains(identifier)) {
			val useIntegrationCorrespondence = this.decideIntegrationCorrespondenceUsage(objectA, objectB)
			if(useIntegrationCorrespondence){
				val integrationCorrespondenceView = IntegrationCorrespondenceHelper.getEditableView(cInstance) 
				correspondence = integrationCorrespondenceView.createAndAddCorrespondence(deresolvedA.toList, deresolvedB.toList)
			}else{ // create a standard response correspondence
				correspondence = cInstance.createAndAddCorrespondence(deresolvedA, deresolvedB)
			}
			existingEntries.add(identifier)
			logger.info("Created Correspondence for element: " + objectA + " and Element: " + objectB)

			return correspondence 
		}
	}
	
	def decideIntegrationCorrespondenceUsage(EObject objectA, EObject objectB) {
		val correspondenceTypeDeciders = EclipseBridge.getRegisteredExtensions(CorrespondenceTypeDeciding.ID,
			VitruviusConstants.getExtensionPropertyName(), CorrespondenceTypeDeciding)
		val correspondenceTypeDecider = correspondenceTypeDeciders.claimNotMany
		if(null == correspondenceTypeDecider){
			return true
		}
		return correspondenceTypeDecider.useIntegratedCorrespondence(objectA, objectB, cInstance, this.jaMoppResources)
	}
	
	/**
	 * Converts the absolute resource URI of given EObject to platform URI
	 * or does nothing if it already has one.
	 */
	public def <T extends EObject> T deresolveIfNesessary(T object) {
		if (null == object.eResource) {
			return object
		}
		var URI uri = object.eResource.URI
		if (!uri.platform) {
			var base = URI.createFileURI(projectBase.toString + IPath.SEPARATOR)
			var relativeUri = uri.deresolve(base)
			object.eResource.URI = EMFBridge.createPlatformResourceURI(relativeUri.toString)
		}
		return object
	}

}
