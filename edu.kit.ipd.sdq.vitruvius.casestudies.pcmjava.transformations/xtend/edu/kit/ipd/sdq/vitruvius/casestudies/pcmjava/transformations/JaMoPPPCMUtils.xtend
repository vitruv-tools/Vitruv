package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import java.util.Set
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.Package

class JaMoPPPCMUtils {
	private new(){}
	
	def static Package getContainingPackage(Classifier classifier, CorrespondenceInstance correspondenceInstance){
		val namespace = classifier.containingCompilationUnit.namespacesAsString
		var Set<Package> packagesWithCorrespondences = correspondenceInstance.getAllEObjectCorrespondencesWithType(Package)
		val Package packageWithNamespace = packagesWithCorrespondences.filter[pack|pack.namespaces.equals(namespace)].iterator.next
		if(null != packageWithNamespace){
			return packageWithNamespace
		}
		val Package newPackage = ContainersFactory.eINSTANCE.createPackage
		newPackage.setName(namespace)
		return newPackage;
	}
	
	def static EAttribute getAttributeByNameFromEObject(String attributeName, EObject eObject) {
		return eObject.eClass.EAllAttributes.filter[attribute|attribute.name.equalsIgnoreCase(attributeName)].iterator.next
	}
	
}