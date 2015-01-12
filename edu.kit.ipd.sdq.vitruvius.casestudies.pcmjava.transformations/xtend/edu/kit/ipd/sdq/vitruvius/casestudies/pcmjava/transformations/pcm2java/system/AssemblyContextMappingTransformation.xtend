package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system

import de.uka.ipd.sdq.pcm.core.composition.AssemblyContext
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPUtils
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.types.TypeReference

class AssemblyContextMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return AssemblyContext
	}

	override setCorrespondenceForFeatures() {
		PCM2JaMoPPUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	/**
	 * called when a assembly context has been added
	 * creates a private field with type of enclosed component and name of the assembly context.
	 * 
	 */
	override createEObject(EObject eObject) {
		val AssemblyContext assemblyContext = eObject as AssemblyContext
		val component = assemblyContext.encapsulatedComponent__AssemblyContext
		if(null == component){
			// we are not able to create a field if the component in the assembly context is null
			// the following methods has to be aware of that
			return null
		}
			
		val jaMoPPClass = correspondenceInstance.
			claimUniqueCorrespondingEObjectByType(component, Class)
		
		val TypeReference typeRef = PCM2JaMoPPUtils.createNamespaceClassifierReference(jaMoPPClass) 
		val String name = assemblyContext.entityName
		
		val List<EObject> newEObjects = new ArrayList<EObject>()
		
		val field = PCM2JaMoPPUtils.createPrivateField(typeRef, name)
		jaMoPPClass.members.add(field)
		
		newEObjects.add(field)
		
		val constructors = jaMoPPClass.members.filter(typeof(Constructor))
		
		if(constructors.nullOrEmpty){
			PCM2JaMoPPUtils.addConstructorToClass(jaMoPPClass)
		}
		
		// add creation of EObject to each constructor
		for (ctor : jaMoPPClass.members.filter(typeof(Constructor))) {
			PCM2JaMoPPUtils.createNewOfFieldInConstructor(ctor, field, null)
		}
		
		newEObjects
	}

}
