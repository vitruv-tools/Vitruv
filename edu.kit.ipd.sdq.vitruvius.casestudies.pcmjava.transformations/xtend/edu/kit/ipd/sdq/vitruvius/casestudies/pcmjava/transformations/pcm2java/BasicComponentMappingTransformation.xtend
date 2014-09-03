package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.BasicComponent
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMMappingTransformationBase
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import org.emftext.language.java.modifiers.ModifiersFactory

class BasicComponentMappingTransformation extends JaMoPPPCMMappingTransformationBase {
	
	val private static Logger logger = Logger.getLogger(BasicComponentMappingTransformation.simpleName)
	
	override getClassOfMappedEObject() {
		return BasicComponent
	}

	override createEObject(EObject eObject) {
		val BasicComponent basicComponent = eObject as BasicComponent

		// get root (aka repository) package
		val Package rootPackage = correspondenceInstance. 
			claimUniqueCorrespondingEObjectByType(basicComponent.repository__RepositoryComponent, Package)

		// create JaMoPP Package
		val Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage
		jaMoPPPackage.name = basicComponent.entityName
		jaMoPPPackage.namespaces.addAll(rootPackage.namespaces)
		jaMoPPPackage.namespaces.add(jaMoPPPackage.name)

		//create JaMoPP compilation unit and JaMoPP class 
		val Class jaMoPPClass = ClassifiersFactory.eINSTANCE.createClass
		jaMoPPClass.name = basicComponent.entityName + "Impl"
		jaMoPPClass.addModifier(ModifiersFactory.eINSTANCE.createPublic)
		val CompilationUnit jaMoPPCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit
		jaMoPPCompilationUnit.name = jaMoPPClass.name + ".java"

		// add classifier to compilation unit
		jaMoPPCompilationUnit.classifiers.add(jaMoPPClass)

		// add compilation unit to package		
		jaMoPPPackage.compilationUnits.add(jaMoPPCompilationUnit)
		jaMoPPCompilationUnit.namespaces.addAll(jaMoPPPackage.namespaces)

		return #[jaMoPPPackage, jaMoPPCompilationUnit, jaMoPPClass];
	}
	
	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		val BasicComponent basicComponent = newValue as BasicComponent

		// get root (aka repository) package
		val Package rootPackage = correspondenceInstance. 
			claimUniqueCorrespondingEObjectByType(basicComponent.repository__RepositoryComponent, Package)
		val Correspondence parentCorrespondence = correspondenceInstance.claimUniqueOrNullCorrespondenceForEObject(rootPackage)

		for(jaMoPPElement : newCorrespondingEObjects){
			correspondenceInstance.createAndAddEObjectCorrespondence(basicComponent, jaMoPPElement, parentCorrespondence)
		}
		return TransformationUtils.createTransformationChangeResultForNewRootEObjects(newCorrespondingEObjects)
	}
	

	override removeEObject(EObject eObject) {
		val correspondences = correspondenceInstance.getAllCorrespondences(eObject);
		val correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
		var eObjectsToDelete = new ArrayList<EObject>()
		if(!correspondences.nullOrEmpty ){
			eObjectsToDelete.addAll(correspondences)
		}
		if(!correspondingEObjects.nullOrEmpty){
			eObjectsToDelete.addAll(correspondingEObjects)
		}
		return eObjectsToDelete
	}
	
	override deleteNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		if(oldCorrespondingEObjectsToDelete.nullOrEmpty){
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		oldCorrespondingEObjectsToDelete.forEach[eObject|EcoreUtil.delete(eObject)]
		return TransformationUtils.createEmptyTransformationChangeResult
	}

	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
		if(!featureCorrespondenceMap.containsKey(affectedAttribute)){
			logger.info("no feature correspondence found for affected Attribute: " + affectedAttribute)
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		var correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
		if(null == correspondingEObjects){
			logger.info("No corresponding objects found for " + eObject)
		}
		var TransformationChangeResult transformationChangeResult = new TransformationChangeResult
		/**
		 * update TUIDs after rename the elements
		 */ 
		for(EObject correspondingObject : correspondingEObjects){
			// compilationUnit was renamed: Delete old one and save new one
			val oldObject = EcoreUtil.copy(correspondingObject)
			if(correspondingObject instanceof CompilationUnit){
				transformationChangeResult.existingObjectsToDelete.add(oldObject)
			}
			correspondingObject.eClass.eSet(featureCorrespondenceMap.get(affectedAttribute), newValue)
			correspondenceInstance.update(oldObject, correspondingObject)
			if(correspondingObject instanceof CompilationUnit){
				transformationChangeResult.newRootObjectsToSave.add(correspondingObject)
			}
//			else{
//				transformationChangeResult.existingObjectsToSave.add(correspondingObject)
//			}
		}
		return transformationChangeResult
	}

	override setCorrespondenceForFeatures() {
		var basicComponentNameAttribute = RepositoryFactory.eINSTANCE.createBasicComponent.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPPCMNamespace::PCM_ATTRIBUTE_ENTITY_NAME)].iterator.next
		var classNameAttribute = ClassifiersFactory.eINSTANCE.createClass.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPPCMNamespace::JAMOPP_ATTRIBUTE_NAME)].iterator.next
		var packageNameAttribute = ContainersFactory.eINSTANCE.createPackage.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase(JaMoPPPCMNamespace::JAMOPP_ATTRIBUTE_NAME)].iterator.next
		featureCorrespondenceMap.put(basicComponentNameAttribute, classNameAttribute)
		featureCorrespondenceMap.put(basicComponentNameAttribute, packageNameAttribute)
	}
	
	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue, EObject[] newCorrespondingEObjects) {
		logger.warn("method should not be called for BasicComponentMappingTransformation transformation")
		return null
	}
}
