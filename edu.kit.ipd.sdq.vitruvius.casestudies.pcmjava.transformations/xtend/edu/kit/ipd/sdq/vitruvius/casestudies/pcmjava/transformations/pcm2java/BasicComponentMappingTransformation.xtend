package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java

import de.uka.ipd.sdq.pcm.repository.BasicComponent
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package
import org.emftext.language.java.modifiers.ModifiersFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.TransformationUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult

class BasicComponentMappingTransformation extends edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation {
	
	val private static Logger logger = Logger.getLogger(BasicComponentMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return BasicComponent
	}

	override addEObject(EObject eObject) {
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

		val Correspondence parentCorrespondence = correspondenceInstance.claimUniqueOrNullCorrespondenceForEObject(rootPackage)

		//create correspondence for package and class (both are corresponding to the basic component)
		val EObjectCorrespondence basicComponent2Package = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
		basicComponent2Package.setElementA(basicComponent)
		basicComponent2Package.setElementB(jaMoPPPackage)
		basicComponent2Package.setParent(parentCorrespondence)
		val EObjectCorrespondence basicComponent2Class = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
		basicComponent2Class.setElementA(basicComponent)
		basicComponent2Class.setElementB(jaMoPPClass)
		basicComponent2Class.setParent(parentCorrespondence)
		val EObjectCorrespondence basicComponent2CompilationUnit = CorrespondenceFactory.eINSTANCE.
			createEObjectCorrespondence
		basicComponent2CompilationUnit.setElementA(basicComponent)
		basicComponent2CompilationUnit.setElementB(jaMoPPCompilationUnit)
		basicComponent2CompilationUnit.setParent(parentCorrespondence)
		correspondenceInstance.addSameTypeCorrespondence(basicComponent2Package)
		correspondenceInstance.addSameTypeCorrespondence(basicComponent2CompilationUnit)
		correspondenceInstance.addSameTypeCorrespondence(basicComponent2Class)
		return TransformationUtils.createTransformationChangeResultForNewRootEObjects(jaMoPPCompilationUnit.toArray)
	}

	override removeEObject(EObject eObject) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		if(!featureCorrespondenceMap.containsKey(affectedAttribute)){
			logger.info("no feature correspondence found for affected Attribute: " + affectedAttribute)
			return null
		}
		var correspondingEObjects = correspondenceInstance.getAllCorrespondingEObjects(eObject)
		if(null == correspondingEObjects){
			logger.info("No corresponding objects found for " + eObject)
		}
		var TransformationChangeResult transformationChangeResult = new TransformationChangeResult 
		for(EObject correspondingObject : correspondingEObjects){
			// compilationUnit was renamed: Delete old one and save new one
			if(correspondingObject instanceof CompilationUnit){
				transformationChangeResult.existingObjectsToDelete.add(correspondingObject)
			}
			correspondingObject.eClass.eSet(featureCorrespondenceMap.get(affectedAttribute), newValue)
			if(correspondingObject instanceof CompilationUnit){
				transformationChangeResult.newRootObjectsToSave.add(correspondingObject)
			}else{
				transformationChangeResult.existingObjectsToSave.add(correspondingObject)
			}
		}
		
		return transformationChangeResult
	}

	override updateEReference(EObject eObject, EReference affectedEReference, Object newValue) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override updateEContainmentReference(EObject eObject, EReference afffectedEReference, Object newValue) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
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

}
