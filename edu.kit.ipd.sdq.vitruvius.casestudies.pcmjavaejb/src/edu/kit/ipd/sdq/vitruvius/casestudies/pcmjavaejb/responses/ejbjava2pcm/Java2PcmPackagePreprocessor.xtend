package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.Change2CommandTransformingPreprocessor
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.emftext.language.java.containers.Package
 
class Java2PcmPackagePreprocessor implements Change2CommandTransformingPreprocessor { 
	    /**
     * Special treatment for packages: we have to use the package-info file as input for the
     * transformation and make sure that the packages have resources attached
     *
     * @param change
     *            the change that may contain the newly created package
     */
    private def void handlePackageInEChange(EMFModelChange change) {
        if (change.getEChange() instanceof CreateRootEObject<?>) {
            val CreateRootEObject<?> createRoot = change.getEChange() as CreateRootEObject<?>;
            attachPackageToResource(createRoot.getNewValue(), change.getURI());
        } else if (change.getEChange() instanceof UpdateSingleValuedEAttribute<?>) {
            val UpdateSingleValuedEAttribute<?> updateSingleValuedEAttribute = change
                    .getEChange() as UpdateSingleValuedEAttribute<?>;
            prepareRenamePackageInfos(updateSingleValuedEAttribute, change.getURI());
        } // TODO: package deletion
    }

    private def void prepareRenamePackageInfos(UpdateSingleValuedEAttribute<?> updateSingleValuedEAttribute,
            VURI vuri) {
        if (updateSingleValuedEAttribute.getOldAffectedEObject() instanceof Package
                && updateSingleValuedEAttribute.getNewAffectedEObject() instanceof Package) {
            val Package oldPackage = updateSingleValuedEAttribute.getOldAffectedEObject() as Package;
            val Package newPackage = updateSingleValuedEAttribute.getNewAffectedEObject() as Package;
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
				
	override processChange(Change change, UserInteracting userInteracting, Blackboard blackboard) {
		if (change instanceof EMFModelChange) {
			handlePackageInEChange(change);
		}
		return #[];
	}
	
	override doesProcess(Change change) {
		return change instanceof EMFModelChange;
	}
				
}
