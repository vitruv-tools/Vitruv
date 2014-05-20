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

class BasicComponentMappingTransformation extends edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return BasicComponent
	}

	override addEObject(EObject eObject) {
		val BasicComponent basicComponent = eObject as BasicComponent

		// get root (aka repository) package
		val Package rootPackage = correspondenceInstance.
			claimCorrespondingEObjectByTypeIfUnique(basicComponent.repository__RepositoryComponent, Package)

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

		val Correspondence parentCorrespondence = correspondenceInstance.getCorrespondeceForEObjectIfUnique(rootPackage)

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
		return jaMoPPCompilationUnit;
	}

	override removeEObject(EObject eObject) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override updateEAttribute(EObject eObject, EAttribute affectedAttribute, Object newValue) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
		
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
