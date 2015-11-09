package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.DefaultEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.types.TypesFactory
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour

class ResourceDemandingInternalBehaviorMappingTransformation extends DefaultEObjectMappingTransformation {

	val private static Logger logger = Logger.getLogger(
		ResourceDemandingInternalBehaviorMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return ResourceDemandingInternalBehaviour
	}

	/**
	 * called when an internal behavior has been created
	 * For an internal behaviour we create a package-private method within the components main class. 
	 */
	override createEObject(EObject eObject) {
		val resourceDemandingInternalBehaviour = eObject as ResourceDemandingInternalBehaviour 
		val basicComponent = resourceDemandingInternalBehaviour.basicComponent_ResourceDemandingInternalBehaviour
		val classesForMethod = this.blackboard.correspondenceInstance.
			getCorrespondingEObjectsByType(basicComponent, Class)
		if (!classesForMethod.nullOrEmpty) {
			val classForMethod = classesForMethod.get(0)
			//val methodName = getMethodNameFromUser(classForMethod.name)
			val methodName = resourceDemandingInternalBehaviour.entityName
			val classMethod = PCM2JaMoPPUtils.createClassMethod(methodName, TypesFactory.eINSTANCE.createVoid, null,
				null, false)
			classForMethod.members.add(classMethod)
			blackboard.correspondenceInstance.createAndAddEObjectCorrespondence(resourceDemandingInternalBehaviour, classMethod)
		}
		logger.warn(
			"could not create a corresponding ClassMethod for the current ResourceDemandingInternalBehaviour" +
				" because a corresponding ClassMethod already exists" + eObject)
		return null
	}

// since ResourceDemandingInternalBehaviorMapping extends Entity the method (and also userinteraction) currently not needed
//	def getMethodNameFromUser(String className) {
//		val msg = '''A new ResourceDemandingInternalBehaviour has been created. 
//			 A new method will be created in the class «className»
//			 Please specify the name of the new corresponding method in the class.'''
//		val String methodName = userInteracting.getTextInput(msg)
//		return methodName
//	}

	override removeEObject(EObject eObject) {
		TransformationUtils.removeCorrespondenceAndAllObjects(eObject, blackboard)
		return null
	}

}