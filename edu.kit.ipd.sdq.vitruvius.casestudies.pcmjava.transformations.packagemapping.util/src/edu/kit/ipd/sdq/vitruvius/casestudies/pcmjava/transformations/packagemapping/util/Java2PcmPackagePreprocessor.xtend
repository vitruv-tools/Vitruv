package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.util

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import org.emftext.language.java.containers.Package
import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.root.InsertRootEObject
import edu.kit.ipd.sdq.vitruvius.domains.java.echange.feature.attribute.JavaReplaceSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.change.description.ConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.ChangeProcessorResult
import java.util.ArrayList
import org.eclipse.emf.common.command.Command
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.AbstractChangeProcessor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting

class Java2PcmPackagePreprocessor extends AbstractChangeProcessor {
    
	new(UserInteracting userInteracting) {
		super(userInteracting);
	}

	/**
     * Special treatment for packages: we have to use the package-info file as input for the
     * transformation and make sure that the packages have resources attached
     *
     * @param change
     *            the change that may contain the newly created package
     */	
	private def void handlePackageInEChange(ConcreteChange change) {
    	if (change.getEChanges.size == 1) {
    		val eChange = change.getEChanges.get(0);
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
				
	override transformChange(ConcreteChange change, CorrespondenceModel correspondenceModel) {
		val result = new ChangeProcessorResult(change, #[]);
		if (change instanceof GeneralChange) {
			handlePackageInEChange(change);	
		}
		
		return result;
	}
				
}
