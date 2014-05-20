package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import de.uka.ipd.sdq.pcm.system.System
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMUtils
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.Package
import org.emftext.language.java.containers.CompilationUnit

/**
 * Maps a JaMoPP interface to a PCM interface 
 * Triggered when a CUD operation on JaMoPP interface is detected.
 */
class InterfaceMappingTransformation extends EObjectMappingTransformation {
	
	val private static final Logger logger = Logger.getLogger(InterfaceMappingTransformation.name)
	
	override getClassOfMappedEObject() {
		return Interface
	}
	
	/**
	 * Called when a Java-interface was added to the source code
	 * Determines whether the interface is architecture relevant or not by 
	 * a) checking whether it is in the package that corresponds to the repository package
	 * b) asking the developer (not yet implemented)
	 */
	override addEObject(EObject eObject) {
		val Interface jaMoPPInterface = eObject as Interface
		val Package jaMoPPPackage = JaMoPPPCMUtils::getContainingPackage(jaMoPPInterface, correspondenceInstance)
		try{
			// get correspoding Object for Package--> it should be either a system, a component or the repository itself  
			val pcmArtefact = correspondenceInstance.getCorrespondeceForEObjectIfUnique(jaMoPPPackage)
			if(null == pcmArtefact){
				// no corresponding artefact for Package
				// if this is the case we currently we assume that the interface is not an architectural interface
				// TODO: ask user whether it should be an architectural interface anyway 
				return null
			}
			var Repository repo = null;
			if(pcmArtefact instanceof Repository){
				// if the corresponding object the repository itself: the interface is considered as architectural interface 
				// and added to the repository 
				repo = pcmArtefact as Repository
			}else if(pcmArtefact instanceof RepositoryComponent || pcmArtefact instanceof System){
				// TODO: if the interface is in a Component ask the developer whether it is architectural relevant
				logger.info("pcmArtefact is instanceof RepositoryComponent. Assuming that the interface is not architectural relevant")
				return null;	
			}else {
				logger.warn("pcmArtefact is not the repository or a component or a system. Should not happen. PCMArtefact: " + pcmArtefact)
				return null;
			}
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