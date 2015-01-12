package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import de.uka.ipd.sdq.pcm.repository.BasicComponent
import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import de.uka.ipd.sdq.pcm.system.System
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.containers.Package

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
	override void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
		super.setCorrespondenceInstance(correspondenceInstance)
		checkCorrespondenceRepository()
	}

	def boolean checkCorrespondenceRepository() {
		val repositorys = correspondenceInstance.getAllEObjectsInCorrespondencesWithType(Repository)
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
	 * when a package is added there following possibilities exists:
	 * i) it is a package corresponding to a basic component --> create PCM basic component
	 * ii) it is a package corresponding to a composite component --> create PCM composite component
	 * iii) it is a package corresponding to a system --> create PCM system  
	 * iv) it is the root package and the package where all interfaces and datatypes should be stored --> create PCM repository
	 * v) none of the above --> do nothing
	 * 
	 * Case iv) occurs when no package and no repository exist yet--> c
	 * 			an be determined automatically (see correspondenceRepositoryAlreadyExists)
	 * Whether it is case i), ii) or iii) can not be decided automatically --> ask user
	 * 
	 */
	override createEObject(EObject eObject) {
		val Package jaMoPPPackage = eObject as Package

		//if the package already has already a correspondence 
		//(which can happen when the package was created by the PCM2JaMoPP transformation we do nothing here 
		if (!correspondenceInstance.getAllCorrespondences(jaMoPPPackage).nullOrEmpty) {
			return null
		}
		var packageName = jaMoPPPackage.name
		if (packageName.contains(".") && packageName.lastIndexOf(".") < packageName.length) {
			packageName = packageName.substring(packageName.lastIndexOf(".") + 1, packageName.length)
		}
		if (!correspondenceRepositoryAlreadyExists && !checkCorrespondenceRepository()) {

			// iv) first package created --> it is the corresponding package to the repository
			repository = RepositoryFactory.eINSTANCE.createRepository
			repository.setEntityName(packageName)
			correspondenceRepositoryAlreadyExists = true
			return repository.toArray
		}

		// case i)
		var BasicComponent basicComponent = RepositoryFactory.eINSTANCE.createBasicComponent
		basicComponent.setEntityName(packageName)
		basicComponent.setRepository__RepositoryComponent(repository)
		repository.components__Repository.add(basicComponent)
		return basicComponent.toArray
	}

	override createRootEObject(EObject newRootEObject, EObject[] newCorrespondingEObjects) {
		if (newCorrespondingEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val tcr = new TransformationChangeResult
		var Correspondence parrentCorrespondence = null
		for (pcmElement : newCorrespondingEObjects) {
			parrentCorrespondence = null
			if (pcmElement instanceof Repository || pcmElement instanceof System) {
				tcr.newRootObjectsToSave.add(pcmElement)
			} else if (pcmElement instanceof RepositoryComponent) {
				val component = pcmElement as RepositoryComponent
				val parrentCorrespondences = correspondenceInstance.getAllCorrespondences(
					component.repository__RepositoryComponent)
				if (!parrentCorrespondences.nullOrEmpty) {
					parrentCorrespondence = parrentCorrespondences.get(0)
				}
				tcr.existingObjectsToSave.add(component)

			} else {
				tcr.existingObjectsToSave.add(pcmElement)
			}
			tcr.addNewCorrespondence(correspondenceInstance, pcmElement, newRootEObject, parrentCorrespondence)
		}
		return tcr
	}

	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingEObjects) {
		return createRootEObject(newValue, newCorrespondingEObjects)
	}

	/**
	 * When a package is removed all classes in the packages are removed as well.
	 * Hence, we remove all corresponding objects (which theoretically could be the whole repository if the main
	 * package is removed.
	 */
	override removeEObject(EObject eObject) {
		val Package jaMoPPPackage = eObject as Package
		try {
			val correspondingObjects = correspondenceInstance.claimCorrespondingEObjects(jaMoPPPackage)
			for (correspondingObject : correspondingObjects) {
				EcoreUtil.remove(correspondingObject)
				correspondenceInstance.removeAllCorrespondences(correspondingObject)
			}
		} catch (RuntimeException rte) {
			logger.info(rte)
		}
		return null
	}

	override deleteRootEObject(EObject oldRootEObject, EObject[] oldCorrespondingEObjectsToDelete) {
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	override deleteNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue,
		int index, EObject[] oldCorrespondingEObjectsToDelete) {
		return TransformationUtils.createEmptyTransformationChangeResult
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
		if(newValue instanceof String){
			var String newStringValue = newValue as String
			if(newStringValue.contains(".") && newStringValue.length > newStringValue.lastIndexOf(".")+1){
				newStringValue = newStringValue.substring(newStringValue.lastIndexOf(".")+1, newStringValue.length)
				newVarValue = newStringValue.toString()
			}
		}
		JaMoPP2PCMUtils.updateNameAsSingleValuedEAttribute(eObject, affectedAttribute, oldValue, newVarValue, featureCorrespondenceMap,
			correspondenceInstance)
	}

	override setCorrespondenceForFeatures() {
		JaMoPP2PCMUtils.addName2EntityNameCorrespondence(featureCorrespondenceMap);
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue,
		EObject[] newCorrespondingEObjects) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
