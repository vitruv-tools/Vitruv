package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import org.eclipse.emf.ecore.EStructuralFeature

class InterfaceMappingTransformation extends EObjectMappingTransformation {
	
	val private static final Logger logger = Logger.getLogger(InterfaceMappingTransformation.name)
	
	override getClassOfMappedEObject() {
		return Interface
	}
	
	/**
	 * Called when a Java-interface was added to the source code
	 * Determines whether the interface is architecture relevant or not by 
	 * a) checking whether it is in the package that corresponds to the repository package
	 * b) asking the developer (not yet implmented)
	 */
	override addEObject(EObject eObject) {
		val Interface jaMoPPInterface = eObject as Interface
		val List<String> namespace = jaMoPPInterface.containingPackageName
		val Package jaMoPPPackage = null
		try{
			val Repository repo = correspondenceInstance.claimCorrespondingEObjectByTypeIfUnique(jaMoPPPackage, Repository)
			
			var OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface
			opInterface.setEntityName(jaMoPPInterface.name)
			opInterface.setRepository__Interface(repo)
			
			var EObjectCorrespondence interface2opInterfaceCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
			interface2opInterfaceCorrespondence.setElementA(jaMoPPInterface)
			interface2opInterfaceCorrespondence.setElementB(opInterface)
			correspondenceInstance.addSameTypeCorrespondence(interface2opInterfaceCorrespondence)
			var EObjectCorrespondence compilationUnit2opInterfaceCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence
			compilationUnit2opInterfaceCorrespondence.setElementA(jaMoPPInterface.containingCompilationUnit)
			compilationUnit2opInterfaceCorrespondence.setElementB(opInterface)
			return opInterface 
		}catch(Exception e ){
			logger.info(e)
		}
		return null;		
	}
	
	/**
	 * Called when a Java-interface was removed. Also removes the corresponding PCM Interface (if there is one)
	 * Does not ask the developer whether the PCM interface should be removed also.
	 */
	override removeEObject(EObject eObject) {
		val Interface jaMoPPInterface = eObject as Interface
		val CompilationUnit jaMoPPCompilationUnit = jaMoPPInterface.containingCompilationUnit
		
		var EObject correspondingOpInterface = correspondenceInstance.getCorrespondeceForEObjectIfUnique(jaMoPPInterface)
		var EObject correspondingOpInterface2CompilationUnit = correspondenceInstance.getCorrespondeceForEObjectIfUnique(jaMoPPCompilationUnit)
		if(null == correspondingOpInterface && null == correspondingOpInterface2CompilationUnit){
			return null
		}
		if(correspondingOpInterface != correspondingOpInterface2CompilationUnit){
			logger.warn("corresponding interface " + correspondingOpInterface 
				+ "is not the same interface as the interface corresponding to the compilation unit " + correspondingOpInterface2CompilationUnit )
			return null
		}
		EcoreUtil.remove(correspondingOpInterface)
		correspondenceInstance.removeAllCorrespondingInstances(jaMoPPInterface)
		return correspondingOpInterface
	}
	
	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		val Interface jaMoPPInterface = eObject as Interface
		var OperationInterface opInterface = correspondenceInstance.claimCorrespondingEObjectByTypeIfUnique(jaMoPPInterface, OperationInterface)
		val EStructuralFeature attribute = featureCorrespondenceMap.claimValueForKey(affectedAttribute)
		opInterface.eSet(attribute, newValue)
		return opInterface
	}
	
	override updateEReference(EObject eObject, EReference affectedEReference, Object newValue) {
		logger.warn("updateEReference called in class " + this.class.simpleName + " with parameters: EObject" +
			eObject + " AffectedERefernece" + affectedEReference + " newValue=" + newValue + ". Should not happen...")
		return null 
	}
	
	override updateEContainmentReference(EObject eObject, EReference affectedEReference, Object newValue) {
		logger.warn("updateEContainmentReference called in class " + this.class.simpleName + " with parameters: EObject" +
			eObject + " affectedEReference" + affectedEReference + " newValue=" + newValue + ". Should not happen...")
		return null 
	}
	
	override setCorrespondenceForFeatures() {
		var interfaceNameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("name")].iterator.next
		var opInterfaceNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("entityName")].iterator.next
		featureCorrespondenceMap.put(interfaceNameAttribute, opInterfaceNameAttribute)
	}
	
}