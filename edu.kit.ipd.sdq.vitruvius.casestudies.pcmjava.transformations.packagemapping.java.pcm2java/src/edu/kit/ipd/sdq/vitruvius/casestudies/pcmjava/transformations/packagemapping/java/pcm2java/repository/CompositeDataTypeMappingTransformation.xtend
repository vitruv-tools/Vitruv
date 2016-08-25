package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.members.Member
import org.emftext.language.java.modifiers.ModifiersFactory
import org.palladiosimulator.pcm.repository.CompositeDataType
import org.emftext.language.java.containers.JavaRoot

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceModelUtil.*
import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.PCMJaMoPPUtils

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

		var datatypePackage = PCM2JaMoPPUtils.getDatatypePackage(correspondenceModel,
			cdt.repository__DataType, cdt.entityName, userInteracting)
		compUnit.name = cdt.entityName + "." + JaMoPPNamespace.JAVA_FILE_EXTENSION
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
		val transformationResult = new TransformationResult
		val affectedEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(eObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceModel)
		if (affectedEObjects.nullOrEmpty) {
			return transformationResult
		}
		val cus = affectedEObjects.filter(typeof(CompilationUnit))
		if (!cus.nullOrEmpty) {
			val CompilationUnit cu = cus.get(0)
			PCM2JaMoPPUtils.handleJavaRootNameChange(cu, affectedAttribute, newValue, correspondenceModel, false,
				transformationResult, eObject)
		}
		transformationResult
	}

	/**
	 * called when a InnerDeclaration has been added to the CompositeDataType
	 */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		val transformationResult = new TransformationResult
		if (!affectedReference.name.equals(PCMNamespace.INNER_DECLARATION_COMPOSITE_DATA_TYPE)) {
			return transformationResult
		}
		val compositeDataType = newAffectedEObject as CompositeDataType
		val jaMoPPDataType = correspondenceModel.
			getCorrespondingEObjectsByType(compositeDataType, Class).claimOne
		for (newCorrespondingEObject : newCorrespondingEObjects) {
			if (newCorrespondingEObject instanceof Member) {
				jaMoPPDataType.members.add(newCorrespondingEObject)
			}
			correspondenceModel.createAndAddCorrespondence(newValue, newCorrespondingEObject)
			if (newCorrespondingEObject instanceof JavaRoot) {
				PCMJaMoPPUtils.addRootChangeToTransformationResult(newCorrespondingEObject, correspondenceModel,
					PCM2JaMoPPUtils.getSourceModelVURI(newAffectedEObject), transformationResult)
			}
		}
		transformationResult
	}

}
