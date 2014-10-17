package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository

import de.uka.ipd.sdq.pcm.repository.CollectionDataType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import java.lang.reflect.Modifier
import java.util.ArrayList
import java.util.Collection
import java.util.HashSet
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.reflections.Reflections

class CollectionDataTypeMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return CollectionDataType
	}

	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	/** 
	 * Called when a new CollectionDataType is created
	 * Options for user:
	 * 	i) create Collection class in data types package
	 *  ii) just remember that it is a collection data type and map it internally to selected class
	 * In both cases: ask user whether it should be mapped to a which Collection clas 
	 */
	override createEObject(EObject eObject) {
		val CollectionDataType cdt = eObject as CollectionDataType
		var String jaMoPPInnerDataTypeName = "?"
		if (null != cdt.innerType_CollectionDataType) {
			val jaMoPPInnerDataType = DataTypeCorrespondenceHelper.
				claimUniqueCorrespondingJaMoPPDataType(cdt.innerType_CollectionDataType, correspondenceInstance)
			
		}

		//i) ask whether to create a new class
		val String selectClasOrNoClass = "Would you like to create a own class for the CollectionDataType named '" +
			cdt.entityName + "' in the data type package?"
		val int createOwnClassInt = userInteracting.selectFromMessage(UserInteractionType.MODAL,
			"Collection Data Type created. " + selectClasOrNoClass, #{"Yes", "No"})
		var boolean createOwnClass = false;
		if(0 == createOwnClassInt) createOwnClass = true

		//ii) ask data type
		val reflection = new Reflections
		var Set<Class<? extends Collection>> collectionDataTypes = reflection.getSubTypesOf(Collection)
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
		var ret = new ArrayList<EObject>()
		if (createOwnClass) {
			val String content = '''import «selectedClass.package.name».«selectedClass.simpleName»
			   
			   public class «cdt.entityName» extends «selectedClass.simpleName»<«jaMoPPInnerDataTypeName»>{
			   	
			   }
			   
			'''
			val cu = PCM2JaMoPPUtils.createCompilationUnit(cdt.entityName, content)
			val classifier = cu.classifiers.get(0)
			return #[cu, classifier]
		} else {
			throw new UnsupportedOperationException("Not creating a class for collection data type is currently not supported")
			//TODO
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

	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {

		//TODO: implement behaviour for change of innerdataType
		return null
	}

}
