package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.containers.Package
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.CompositeComponent
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.system.System
import org.palladiosimulator.pcm.system.SystemFactory

import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.java2pcm.JaMoPP2PCMUtils

class PackageMappingTransformation extends EmptyEObjectMappingTransformation {

	private static val Logger logger = Logger.getLogger(PackageMappingTransformation.simpleName)

	private var boolean correspondenceRepositoryAlreadyExists;
	private var Repository repository

	override getClassOfMappedEObject() {
		return Package
	}

	/**
	 * override setCorrespondenceInstance:
	 * Check whether there already exists a repository in the correspondences.
	 * If yes set correspondenceRepositoryAlreadyExists to true otherwise to false.
	 * If a repository exists we do not have to create a new one in addEObject
	 */
	override void setBlackboard(Blackboard blackboard) {
		super.setBlackboard(blackboard)
		checkCorrespondenceRepository()
	}

	def boolean checkCorrespondenceRepository() {
		val repositorys = blackboard.correspondenceInstance.getAllEObjectsOfTypeInCorrespondences(Repository)
		if (null == repositorys || 0 == repositorys.size) {
			correspondenceRepositoryAlreadyExists = false
		} else {
			if (1 != repositorys.size) {
				logger.warn(
					"more than one repositorys exists in correspondence model. Should not happen. " + repositorys)
			}
			repository = repositorys.iterator.next
			correspondenceRepositoryAlreadyExists = true
		}
		return correspondenceRepositoryAlreadyExists
	}

	/**
	 * When the first package has been added we automatically create a repository for the package. 
	 * Otherwise the following possibilities exists:
	 * i) it should be a package corresponding to a basic component --> create PCM basic component
	 * ii) it should be a a package corresponding to a composite component --> create PCM composite component
	 * iii) it should be a package corresponding to a system --> create PCM system  
	 * iv) none of the above/decide later (in other transformations) whether the package should become a one of the above  
	 * 	   --> do nothing
	 * 
	 * Case iv) occurs when no package and no repository exist yet--> c
	 * 			an be determined automatically (see correspondenceRepositoryAlreadyExists)
	 * To figure out which case should be realized ask the user directly
	 * 
	 */
	override createEObject(EObject eObject) {
		val Package jaMoPPPackage = eObject as Package

		//if the package already has already a correspondence 
		//(which can happen when the package was created by the PCM2JaMoPP transformation) nothing has do be done 
		if (!blackboard.correspondenceInstance.getCorrespondences(jaMoPPPackage.toList).nullOrEmpty) {
			return null
		}
		var packageName = jaMoPPPackage.name
		if (packageName.contains(".") && packageName.lastIndexOf(".") < packageName.length) {
			packageName = packageName.substring(packageName.lastIndexOf(".") + 1, packageName.length)
		}
		if (!correspondenceRepositoryAlreadyExists && !checkCorrespondenceRepository()) {

			//first package created --> it is the corresponding package to the repository
			repository = RepositoryFactory.eINSTANCE.createRepository
			repository.setEntityName(packageName)
			correspondenceRepositoryAlreadyExists = true
			return repository.toList
		}
		//if the contracts or datatypes packages has been created we simply create the correspondence to the repository
		if(packageName == "contracts" ||packageName == "datatypes"){
			logger.debug("created " + packageName + " package - create correspondence to the repository" )
			return repository.toList
		}
		val String userMsg = "A package has been created. Please decide whether and which corresponding architectural element should be created"
		val String[] selections = #["Create basic component", "Create composite component", "Create system",
			"Do nothing/Decide later"]
		switch (super.modalTextUserinteracting(userMsg, selections)) {
			case 0: {

				// case i)
				var BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent
				basicComponent.setEntityName(packageName)
				repository.components__Repository.add(basicComponent)
				return basicComponent.toList
			}
			case 1: {

				//case ii)
				var CompositeComponent compositeComponent = RepositoryFactory.eINSTANCE.createCompositeComponent
				compositeComponent.entityName = packageName
				repository.components__Repository.add(compositeComponent)
				return compositeComponent.toList
			}
			case 2: {

				//case iii)
				var System pcmSystem = SystemFactory.eINSTANCE.createSystem
				pcmSystem.entityName = packageName
				return pcmSystem.toList
			}
			case 3: {

				//case iv)
				logger.debug("No PCM object should be created for package: " + packageName)
				return null
			}
			default: {
				return null
			}
		}
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		val transformationResult = new TransformationResult
		JaMoPP2PCMUtils.
			createNewCorrespondingEObjects(newRootEObject, newCorrespondingEObjects,
				blackboard, transformationResult)
		return transformationResult
	}

	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		createRootEObject(newValue, newCorrespondingEObjects)
	}

	
	override removeEObject(EObject eObject) {
		return null
	}

	/**
	 * When a package is removed all classes in the packages are removed as well.
	 * Hence, we remove all corresponding objects (which theoretically could be the whole repository if the main
	 * package is removed.
	 */
	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
			//TODO
	}

	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
	}

	/**
	 * Changes the name of the component only if there is no corresponding class for the component (yet).
	 * If there already is a class corresponding to the component the name is only changed when the class name has 
	 * been changed @see ClassMappingTransformation
	 * 
	 */
	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		var Object newVarValue = newValue
		if (newValue instanceof String) {
			var String newStringValue = newValue as String
			if (newStringValue.contains(".") && newStringValue.length > newStringValue.lastIndexOf(".") + 1) {
				newStringValue = newStringValue.substring(newStringValue.lastIndexOf(".") + 1, newStringValue.length)
				newVarValue = newStringValue.toString()
			}
		}
		val transformationResult = new TransformationResult
		JaMoPP2PCMUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newVarValue,
			featureCorrespondenceMap, blackboard, transformationResult)
		return transformationResult
	}

	override setCorrespondenceForFeatures() {
		JaMoPP2PCMUtils.addName2EntityNameCorrespondence(featureCorrespondenceMap);
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
