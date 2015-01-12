package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.CompositeDataType
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.PCMJaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.members.Member
import org.emftext.language.java.modifiers.ModifiersFactory

/**
 * Maps a composite DataType to a class in the data types package.
 * 
 */
class CompositeDataTypeMappingTransformation extends EmptyEObjectMappingTransformation {

	private static final Logger logger = Logger.getLogger(CompositeDataTypeMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return CompositeDataType
	}

	/**
	 * Set Correspondence for name 
	 */
	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	/**
	 * called when a composite data type has been created.
	 * Creates a corresponding class in in the "datatypes" package
	 */
	override EObject[] createEObject(EObject eObject) {
		val CompositeDataType cdt = eObject as CompositeDataType

		val compUnit = ContainersFactory.eINSTANCE.createCompilationUnit

		var Class classifier = ClassifiersFactory.eINSTANCE.createClass
		compUnit.classifiers.add(classifier)
		classifier.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createPublic)
		var datatypePackage = PCM2JaMoPPUtils.findCorrespondingPackageByName("datatypes", correspondenceInstance,
			cdt.repository__DataType)
		if (null == datatypePackage) {
			val String message = "Datatype " + cdt.entityName +
				" created. Please specify to which package the datatype should be added"
			datatypePackage = PCM2JaMoPPUtils.askUserForPackage(correspondenceInstance, cdt.repository__DataType,
				userInteracting, message)
		}
		compUnit.name = cdt.entityName + "." + PCMJaMoPPNamespace.JaMoPP.JAVA_FILE_EXTENSION
		if (null != datatypePackage) {
			compUnit.namespaces.addAll(datatypePackage.namespaces)
			compUnit.namespaces.add(datatypePackage.name)
		}
		classifier.name = cdt.entityName

		return #[compUnit, classifier]
	}

	/**
	 * Called when a CompositeDataType has been renamed. Following things are done:
	 * 1) remove old compilation unit and move it to the new package with the new name + Impl
	 * 2) rename the classifier in the compilaiton unit accordingly
	 * 
	 */
	override updateSingleValuedEAttribute(EObject eObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val affectedEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(eObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance)
		if (affectedEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val tcr = new TransformationChangeResult
		val cus = affectedEObjects.filter(typeof(CompilationUnit))
		if (!cus.nullOrEmpty) {
			val CompilationUnit cu = cus.get(0)
			PCM2JaMoPPUtils.handleJavaRootNameChange(cu, affectedAttribute, newValue, tcr, correspondenceInstance, false)
		}
		return tcr
	}

	/**
	 * called when a InnerDeclaration has been added to the CompositeDataType
	 */
	override createNonRootEObjectInList(EObject affectedEObject, EReference affectedReference, EObject newValue,
		int index, EObject[] newCorrespondingEObjects) {
		if (!affectedReference.name.equals(PCMJaMoPPNamespace.PCM.INNER_DECLARATION_COMPOSITE_DATA_TYPE)) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val compositeDataType = affectedEObject as CompositeDataType
		val jaMoPPDataType = correspondenceInstance.claimUniqueCorrespondingEObjectByType(compositeDataType, Class)
		val tcr = TransformationUtils.createEmptyTransformationChangeResult
		val correspondences = correspondenceInstance.getAllCorrespondences(compositeDataType)
		var Correspondence correspondence = null
		if (!correspondences.nullOrEmpty) {
			correspondence = correspondences.get(0)
		}
		var rootObjectsAffected = false
		val rootObjects = newCorrespondingEObjects.filter(typeof(JavaRoot))
		if(!rootObjects.nullOrEmpty){
			tcr.newRootObjectsToSave.addAll(rootObjects)
			rootObjectsAffected = true	
		}
		for (newCorrespondingEObject : newCorrespondingEObjects) {
			tcr.addNewCorrespondence(correspondenceInstance, newValue, newCorrespondingEObject, correspondence)
			if(!rootObjectsAffected){
				tcr.existingObjectsToSave.add(newCorrespondingEObject)
			}
			if(newCorrespondingEObject instanceof Member){
				jaMoPPDataType.members.add(newCorrespondingEObject)
			}
		}
		return tcr
	}

}
