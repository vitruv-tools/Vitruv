package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.CollectionDataType
import de.uka.ipd.sdq.pcm.repository.DataType
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.JaMoPP2PCMUtils
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.TransformationUtils
import java.lang.reflect.Modifier
import java.util.ArrayList
import java.util.Collection
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import java.util.Set
import java.util.Stack
import java.util.Vector
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.generics.GenericsFactory
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.impl.IntImpl
import org.emftext.language.java.types.PrimitiveType

class CollectionDataTypeMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return CollectionDataType
	}

	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	/** 
	 * Called when a new CollectionDataType is created
	 * Possible options for user:
	 * 	i) create Collection class in data types package
	 *  ii) just remember that it is a collection data type and map it internally to selected class
	 * In both cases: ask user whether it should be mapped to a which Collection class
	 * Currently implemented:
	 * 	CollectionDataType will always be mapped to its own class and the user is asked which kind of collection should be used
	 */
	override createEObject(EObject eObject) {
		val CollectionDataType cdt = eObject as CollectionDataType
		var String jaMoPPInnerDataTypeName = "?"
		if (null != cdt.innerType_CollectionDataType) {
			var jaMoPPInnerDataType = DataTypeCorrespondenceHelper.
				claimUniqueCorrespondingJaMoPPDataTypeReference(cdt.innerType_CollectionDataType, correspondenceInstance)
			if(jaMoPPInnerDataType instanceof PrimitiveType){
				//get class object for inner type, e.g, for int get the class Integer
				jaMoPPInnerDataType = PCM2JaMoPPUtils.getWrapperTypeReferenceForPrimitiveType(jaMoPPInnerDataType)
			}
			if (null != jaMoPPInnerDataType && null != JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(jaMoPPInnerDataType)) {
				jaMoPPInnerDataTypeName = JaMoPP2PCMUtils.getTargetClassifierFromTypeReference(jaMoPPInnerDataType).name
			}
		}

		//i) ask whether to create a new class
		//		val String selectClasOrNoClass = "Would you like to create a own class for the CollectionDataType named '" +
		//			cdt.entityName + "' in the data type package?"
		//		val int createOwnClassInt = userInteracting.selectFromMessage(UserInteractionType.MODAL,
		//			"Collection Data Type created. " + selectClasOrNoClass, #{"Yes", "No"})
		//		var boolean createOwnClass = false;
		//		if (0 == createOwnClassInt) {
		//			createOwnClass = true
		//		}
		val createOwnClass = true

		//ii) ask data type
		//reflections does not work for java.util?
		//val reflection = new Reflections()
		//var Set<Class<? extends Collection>> collectionDataTypes = reflection.getSubTypesOf(Collection)
		var Set<Class<? extends Collection>> collectionDataTypes = new HashSet
		collectionDataTypes.add(ArrayList)
		collectionDataTypes.add(LinkedList)
		collectionDataTypes.add(Vector)
		collectionDataTypes.add(Stack)
		collectionDataTypes.add(HashSet)
		if (createOwnClass) {
			collectionDataTypes = removeAbstractClasses(collectionDataTypes)
		}
		val List<String> collectionDataTypeNames = new ArrayList<String>(collectionDataTypes.size)
		for (collectionDataType : collectionDataTypes) {
			collectionDataTypeNames.add(collectionDataType.name)
		}
		val String selectTypeMsg = "Please select type (or interface) that should be used for the type"
		val int selectedType = userInteracting.selectFromMessage(UserInteractionType.MODAL, selectTypeMsg,
			collectionDataTypeNames)
		val Class<? extends Collection> selectedClass = collectionDataTypes.get(selectedType)
		if (createOwnClass) {
			var datatypePackage = PCM2JaMoPPUtils.getDatatypePackage(correspondenceInstance,
				cdt.repository__DataType, cdt.entityName, userInteracting)
			val String content = '''package «datatypePackage.namespacesAsString + datatypePackage.name»;

import «selectedClass.package.name».«selectedClass.simpleName»;

public class «cdt.entityName» extends «selectedClass.simpleName»<«jaMoPPInnerDataTypeName»>{

}
'''
			val cu = PCM2JaMoPPUtils.createCompilationUnit(cdt.entityName, content)
			val classifier = cu.classifiers.get(0)
			val superTypeRef = classifier.superTypeReferences.get(0)
			return #[cu, classifier, superTypeRef]
		} else {

			//TODO
			throw new UnsupportedOperationException(
				"Not creating a class for collection data type is currently not supported")
		}
	}

	def Set<Class<? extends Collection>> removeAbstractClasses(Set<Class<? extends Collection>> collectionDataTypes) {
		val Set<Class<? extends Collection>> nonAbstractCollections = new HashSet<Class<? extends Collection>>
		for (currentClass : collectionDataTypes) {
			if (!Modifier.isAbstract(currentClass.getModifiers())) {
				nonAbstractCollections.add(currentClass)
			}
		}
		return nonAbstractCollections
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val affectedEObjects = PCM2JaMoPPUtils.checkKeyAndCorrespondingObjects(affectedEObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceInstance)
		if (affectedEObjects.nullOrEmpty) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val tcr = new TransformationChangeResult
		val cus = affectedEObjects.filter(typeof(CompilationUnit))
		if (!cus.nullOrEmpty) {
			val CompilationUnit cu = cus.get(0)
			PCM2JaMoPPUtils.handleJavaRootNameChange(cu, affectedAttribute, newValue, tcr, correspondenceInstance,
				false)
		}
		return tcr
	}

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val innerType = DataTypeCorrespondenceHelper.
			claimUniqueCorrespondingJaMoPPDataType(newValue as DataType, correspondenceInstance)
		if (null == innerType || !(innerType instanceof ConcreteClassifier)) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val innerClassifier = innerType as ConcreteClassifier
		val concreteClass = correspondenceInstance.
			claimUniqueCorrespondingEObjectByType(affectedEObject, org.emftext.language.java.classifiers.Class)
		if (!(concreteClass.extends instanceof NamespaceClassifierReference)) {
			return TransformationUtils.createEmptyTransformationChangeResult
		}
		val extendsReference = concreteClass.extends as NamespaceClassifierReference
		val QualifiedTypeArgument qtr = GenericsFactory.eINSTANCE.createQualifiedTypeArgument
		qtr.typeReference = PCM2JaMoPPUtils.createNamespaceClassifierReference(innerClassifier)
		PCM2JaMoPPUtils.addImportToCompilationUnitOfClassifier(concreteClass, innerClassifier)
		extendsReference.classifierReferences.get(0).typeArguments.clear
		extendsReference.classifierReferences.get(0).typeArguments.add(qtr)
		return TransformationUtils.createTransformationChangeResultForEObjectsToSave(concreteClass.toArray)
	}

}
