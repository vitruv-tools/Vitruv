package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import java.util.Set
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.containers.Package
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance

class JaMoPPPCMUtils {
	private new(){}
	
	def static Package getContainingPackageFromCorrespondenceInstance(Classifier classifier, CorrespondenceInstance correspondenceInstance){
		var namespace = classifier.containingCompilationUnit.namespacesAsString
		if(namespace.endsWith("$")){
			namespace = namespace.substring(0, namespace.length - 1)
		}
		if(!namespace.endsWith(".")){
			namespace = namespace + "."
		}		
		val finalNamespace = namespace
		var Set<Package> packagesWithCorrespondences = correspondenceInstance.getAllEObjectsInCorrespondencesWithType(Package)
		val packagesWithNamespace = packagesWithCorrespondences.filter[pack|finalNamespace.equals(pack.namespacesAsString)]
		if(null != packagesWithNamespace && 0 < packagesWithNamespace.size && null != packagesWithNamespace.iterator.next){
			return packagesWithNamespace.iterator.next
		}
		return null;
	}
}