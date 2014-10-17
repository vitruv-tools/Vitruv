package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import de.uka.ipd.sdq.pcm.repository.OperationInterface
import de.uka.ipd.sdq.pcm.repository.Repository
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory
import de.uka.ipd.sdq.pcm.system.System
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package

/**
 * Maps a JaMoPP interface to a PCM interface 
 * Triggered when a CUD operation on JaMoPP interface is detected.
 */
class InterfaceMappingTransformation extends EmptyEObjectMappingTransformation {
	
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
	override createEObject(EObject eObject) {
		val Interface jaMoPPInterface = eObject as Interface
		val Package jaMoPPPackage = PCM2JaMoPPUtils::getContainingPackageFromCorrespondenceInstance(jaMoPPInterface, correspondenceInstance)
		try{
			// get correspoding Object for Package--> it should be either a system, a component or the repository itself  
			val Set<EObject> pcmArtefacts = correspondenceInstance.getAllCorrespondingEObjects(jaMoPPPackage)
			if(null == pcmArtefacts || 0 == pcmArtefacts.size){
				// no corresponding artefact for Package
				// if this is the case we currently we assume that the interface is not an architectural interface
				// TODO: ask user whether it should be an architectural interface anyway 
				return null
			}
			var Repository repo = null;
			if(0 < pcmArtefacts.filter[pcmArt|pcmArt instanceof Repository].size){
				// if one of the corresponding objects is the repository itself: the interface is considered as architectural interface 
				// and added to the repository 
				repo = pcmArtefacts.filter[pcmArt|pcmArt instanceof Repository].iterator.next as Repository
			}else if( 0 < pcmArtefacts.filter[pcmArt|pcmArt instanceof RepositoryComponent].size || 0 < pcmArtefacts.filter[pcmArt|pcmArt instanceof System].size){
				// TODO: if the interface is in a Component ask the developer whether it is architectural relevant
				logger.info("pcmArtefact is instanceof RepositoryComponent. Assuming that the interface is not architectural relevant")
				return null;	
			}else {
				logger.warn("pcmArtefact is not the repository or a component or a system. Should not happen. PCMArtefact: " + pcmArtefacts)
				return null;
			}
			var OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface
			opInterface.setEntityName(jaMoPPInterface.name)
			opInterface.setRepository__Interface(repo)
			repo.interfaces__Repository.add(opInterface)
			val parentCorrespondences = correspondenceInstance.getAllCorrespondences(repo)
			var Correspondence parentCorrespondence = null
			if(null != parentCorrespondences){
				parentCorrespondence = parentCorrespondences.iterator.next
			}
			return opInterface.toArray
		}catch(Exception e ){
			logger.info(e)
		}
		return null;		
	}
	
	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		if(newCorrespondingEObjects.nullOrEmpty){
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val Interface jaMoPPIf = newValue as Interface
		val parentCorrespondences = correspondenceInstance.getAllCorrespondences(affectedEObject)
		var Correspondence parentCorrespondence = null
		if(null != parentCorrespondences){
			parentCorrespondence = parentCorrespondences.iterator.next
		}
		for(newCorrespondingEObject : newCorrespondingEObjects){
			correspondenceInstance.createAndAddEObjectCorrespondence(newCorrespondingEObject, jaMoPPIf, parentCorrespondence) 
		}
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(newCorrespondingEObjects)
	}
	
	/**
	 * Called when a Java-interface was removed. Also removes the corresponding PCM Interface (if there is one)
	 * Does not ask the developer whether the PCM interface should be removed also.
	 */
	override removeEObject(EObject eObject) {
		val Interface jaMoPPInterface = eObject as Interface
		val CompilationUnit jaMoPPCompilationUnit = jaMoPPInterface.containingCompilationUnit
		try{
			var EObject correspondingOpInterface = correspondenceInstance.claimUniqueCorrespondingEObjectByType(jaMoPPInterface, OperationInterface)
			var EObject correspondingOpInterface2CompilationUnit = correspondenceInstance.claimUniqueCorrespondingEObjectByType(jaMoPPCompilationUnit, OperationInterface)
			if(null == correspondingOpInterface && null == correspondingOpInterface2CompilationUnit){
				return null
			}
			if(correspondingOpInterface != correspondingOpInterface2CompilationUnit){
				logger.warn("corresponding interface " + correspondingOpInterface 
					+ "is not the same interface as the interface corresponding to the compilation unit " + correspondingOpInterface2CompilationUnit )
				return null
			}
			EcoreUtil.remove(correspondingOpInterface)
			correspondenceInstance.removeAllCorrespondences(jaMoPPInterface)
		}catch(RuntimeException rte){
			logger.info(rte)
		} 
		return null
	}
	
	/**
	 * we do not really need the method deleteNonRootEObjectInList in InterfaceMappingTransformation because the deletion of the 
	 * object has already be done in removeEObject.
	 * We just return an empty TransformationChangeResult 
	 */
	override deleteNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		return TransformationUtils.createEmptyTransformationChangeResult
	}
	
	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
		val Interface jaMoPPInterface = eObject as Interface
		var EObject ret = null
		try{
			val EStructuralFeature attribute = featureCorrespondenceMap.claimValueForKey(affectedAttribute)
			var opInterfaces = correspondenceInstance.claimCorrespondingEObjectsByType(jaMoPPInterface, OperationInterface)
			for(opInterface : opInterfaces){
				opInterface.eSet(attribute, newValue)
				ret = opInterface
			}
		}catch(RuntimeException rt){
			logger.trace(rt)
		}
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(ret.toArray)
	}
	
	override setCorrespondenceForFeatures() {
		var interfaceNameAttribute = ClassifiersFactory.eINSTANCE.createInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("name")].iterator.next
		var opInterfaceNameAttribute = RepositoryFactory.eINSTANCE.createOperationInterface.eClass.getEAllAttributes.filter[attribute|
			attribute.name.equalsIgnoreCase("entityName")].iterator.next
		featureCorrespondenceMap.put(interfaceNameAttribute, opInterfaceNameAttribute)
	}

	override createNonRootEObjectSingle(EObject affectedEObject, EReference affectedReference, EObject newValue, EObject[] newCorrespondingEObjects) {
		logger.warn("method createNonRootEObjectSingle should not be called for " + InterfaceMappingTransformation.simpleName + " transformation")
		return null
	}

}