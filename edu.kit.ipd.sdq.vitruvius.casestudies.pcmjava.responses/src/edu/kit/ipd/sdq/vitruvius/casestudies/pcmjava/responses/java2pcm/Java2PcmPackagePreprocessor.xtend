package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import org.emftext.language.java.containers.Package
import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.Change2CommandTransformingPreprocessor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.GeneralChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.javaextension.change.feature.attribute.JavaReplaceSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange

class Java2PcmPackagePreprocessor implements Change2CommandTransformingPreprocessor {
	    /**
     * Special treatment for packages: we have to use the package-info file as input for the
     * transformation and make sure that the packages have resources attached
     *
     * @param change
     *            the change that may contain the newly created package
     */
    private def void handlePackageInEChange(GeneralChange change) {
    	if (change.EChanges.size == 1) {
    		val eChange = change.EChanges.get(0);
        	if (eChange instanceof InsertRootEObject<?>) {
	            attachPackageToResource(eChange.getNewValue(), change.getURI());
        	} else if (eChange instanceof JavaReplaceSingleValuedEAttribute<?,?>) {
	            prepareRenamePackageInfos(eChange, change.getURI());
        	} // TODO: package deletion
        }
    }

    private def void prepareRenamePackageInfos(JavaReplaceSingleValuedEAttribute<?,?> updateSingleValuedEAttribute,
            VURI vuri) {
        if (updateSingleValuedEAttribute.getOldAffectedEObject() instanceof Package
                && updateSingleValuedEAttribute.getAffectedEObject() instanceof Package) {
            val Package oldPackage = updateSingleValuedEAttribute.getOldAffectedEObject() as Package;
            val Package newPackage = updateSingleValuedEAttribute.getAffectedEObject() as Package;
            this.attachPackageToResource(oldPackage, vuri);
            var String newVURIKey = vuri.toString();
            val String oldPackagePath = oldPackage.getName().replace(".", "/");
            val String newPackagePath = newPackage.getName().replace(".", "/");
            newVURIKey = newVURIKey.replace(oldPackagePath, newPackagePath);
            val VURI newVURI = VURI.getInstance(newVURIKey);
            this.attachPackageToResource(newPackage, newVURI);
        }
    }

    private def void attachPackageToResource(EObject eObject, VURI vuri) {
        if (eObject instanceof Package) {
            val Package newPackage = eObject as Package;
            // attach the package to a resource in order to enable the calculation of
            // a TUID in the transformations
            val ResourceSet resourceSet = new ResourceSetImpl();
            val Resource resource = resourceSet.createResource(vuri.getEMFUri());
            resource.getContents().add(newPackage);
        }
    }
				
	override processChange(VitruviusChange change, UserInteracting userInteracting, Blackboard blackboard) {
		if (change instanceof GeneralChange) {
			handlePackageInEChange(change);
		}
		return #[];
	}
	
	override doesProcess(VitruviusChange change) {
		return change instanceof GeneralChange;
	}
				
}
