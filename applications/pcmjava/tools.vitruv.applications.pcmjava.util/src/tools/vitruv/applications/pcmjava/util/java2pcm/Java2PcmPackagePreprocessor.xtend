package tools.vitruv.applications.pcmjava.util.java2pcm

import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import org.emftext.language.java.containers.Package
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.domains.java.echange.feature.attribute.JavaReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.framework.change.processing.impl.AbstractChangePropagationSpecification
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.domains.pcm.PcmNamespace
import tools.vitruv.domains.java.JavaNamespace

class Java2PcmPackagePreprocessor extends AbstractChangePropagationSpecification {
   private val MetamodelPair metamodelPair;
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
		this.metamodelPair = new MetamodelPair(JavaNamespace.METAMODEL_NAMESPACE, PcmNamespace.METAMODEL_NAMESPACE);
	}
	
	override getMetamodelPair() {
		return metamodelPair;
	}
	
	override doesHandleChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		if (change instanceof ConcreteChange && change.getEChanges.size == 1) {
    		val eChange = change.getEChanges.get(0);
        	return eChange instanceof InsertRootEObject<?> || eChange instanceof JavaReplaceSingleValuedEAttribute<?,?>;
        }
        return false;
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
	
	/**
     * Special treatment for packages: we have to use the package-info file as input for the
     * transformation and make sure that the packages have resources attached
     *
     * @param change
     *            the change that may contain the newly created package
     */	
	override propagateChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		if (doesHandleChange(change, correspondenceModel)) {
    		val eChange = change.getEChanges.get(0);
        	if (eChange instanceof InsertRootEObject<?>) {
	            attachPackageToResource(eChange.getNewValue(), change.getURI());
        	} else if (eChange instanceof JavaReplaceSingleValuedEAttribute<?,?>) {
	            prepareRenamePackageInfos(eChange, change.getURI());
        	} // TODO: package deletion
        }
		
		return new ChangePropagationResult();
	}
				
}
