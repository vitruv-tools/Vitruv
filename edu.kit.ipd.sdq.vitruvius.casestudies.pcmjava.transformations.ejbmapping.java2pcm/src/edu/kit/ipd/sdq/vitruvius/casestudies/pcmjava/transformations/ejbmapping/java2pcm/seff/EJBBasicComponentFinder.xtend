package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.seff

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.BasicComponentFinding
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import java.util.List
import org.apache.log4j.Logger
import org.emftext.language.java.containers.ContainersFactory
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.emftext.language.java.members.Method
import org.palladiosimulator.pcm.repository.BasicComponent

import static extension edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import org.eclipse.emf.ecore.resource.ResourceSet
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

/**
 * Finds the corresponding basic component for a given method.
 * Is specific for EJB code that partly uses the package mapping rules for PCM 2 EJB code. 
 * Hence, a BasicComponent can be found either by the direct correspondence of the methods classifier
 * to a BasicComponent or recursively by its packages.
 * The latter check is copied from BasicComponentForPackageMappingFinder
 */
class EJBBasicComponentFinder implements BasicComponentFinding {
	
	private static val Logger logger = Logger.getLogger(EJBBasicComponentFinder.name)
	
	private val ResourceSet dummyResourceSet;
	
	new(){
		dummyResourceSet = new ResourceSetImpl
	}
	
	override BasicComponent findBasicComponentForMethod(Method newMethod, CorrespondenceModel ci) {
		val classifier = newMethod.containingConcreteClassifier
		val basicComponents = ci.getCorrespondingEObjectsByType(classifier, BasicComponent)
		if(!basicComponents.nullOrEmpty){
			return basicComponents.get(0)
		}
		logger.info("No direct correspondence between a basic component found for the classifier " + classifier)
		val cu = classifier.containingCompilationUnit
        val jaMoPPPackage = createPackage(cu, cu.getNamespaces());
        val BasicComponent correspondingBc = this.findCorrespondingBasicComponentForPackage(jaMoPPPackage, ci);
        if (null == correspondingBc) {
            logger.info("Could not find basic component for method " + newMethod + " in package " + jaMoPPPackage);
        }
        return correspondingBc;
    }

    private def Package createPackage(JavaRoot cu,  List<String> namespace) {
        val jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
        jaMoPPPackage.getNamespaces().addAll(namespace);
        // attach dummy resource in order to enable TUID calculation
        val vuri = VURI.getInstance(cu.eResource());
        var String packageURIString = vuri.toString();
        val lastSegment = vuri.getLastSegment();
        if (packageURIString.endsWith(lastSegment)) {
            val int newLength = packageURIString.length() - lastSegment.length();
            packageURIString = packageURIString.substring(0, newLength);
            packageURIString = packageURIString + "package-info.java";
        }
        val VURI packageVuri = VURI.getInstance(packageURIString);
        val dummyResource = this.dummyResourceSet.createResource(packageVuri.getEMFUri());
        dummyResource.getContents().add(jaMoPPPackage);
        return jaMoPPPackage;
    }

    /**
     * Recursively finds the corresponding basic component for the package. walks up the package
     * hierarchy and retunrs the first matching basic component.
     *
     * @param jaMoPPPackage
     * @param ci
     * @return
     */
    private def BasicComponent findCorrespondingBasicComponentForPackage(Package jaMoPPPackage,
            CorrespondenceModel ci) {
        if (0 == jaMoPPPackage.getNamespaces().size()) {
            return null;
        }
        val correspondingComponents = CorrespondenceModelUtil
                .getCorrespondingEObjectsByType(ci, jaMoPPPackage, BasicComponent);
        if (null == correspondingComponents || correspondingComponents.isEmpty()) {

            jaMoPPPackage.getNamespaces().remove(jaMoPPPackage.getNamespaces().size() - 1);
            return this.findCorrespondingBasicComponentForPackage(jaMoPPPackage, ci);
        }
        return correspondingComponents.iterator().next();
    }
}
