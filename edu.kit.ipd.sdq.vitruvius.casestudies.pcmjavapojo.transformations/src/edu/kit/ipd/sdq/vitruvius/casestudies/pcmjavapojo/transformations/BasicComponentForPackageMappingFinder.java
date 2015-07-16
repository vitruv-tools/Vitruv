package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavapojo.transformations;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Method;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.BasicComponentFinding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

/**
 * Finds the component for a method if the the simple package mapping structure is used.
 *
 * @author langhamm
 *
 */
public class BasicComponentForPackageMappingFinder implements BasicComponentFinding {

    private static final Logger logger = Logger.getLogger(BasicComponentForPackageMappingFinder.class.getSimpleName());

    private final ResourceSet dummyResourceSet;

    public BasicComponentForPackageMappingFinder() {
        this.dummyResourceSet = new ResourceSetImpl();
    }

    /**
     * To find the BasicComponent we try to find a corresponding component for the package of the
     * method. If the class of the method is in a nested package of a component we find the matching
     * component by finding the BasicComponent of the parent component.
     */
    @Override
    public BasicComponent findBasicComponentForMethod(final Method newMethod, final CorrespondenceInstance ci) {
        final CompilationUnit cu = newMethod.getContainingCompilationUnit();
        final Package jaMoPPPackage = this.createPackage(cu, cu.getNamespaces());
        final BasicComponent correspondingBc = this.findCorrespondingBasicComponentForPackage(jaMoPPPackage, ci);
        if (null == correspondingBc) {
            logger.info("Could not find basic component for method " + newMethod + " in package " + jaMoPPPackage);
        }
        return correspondingBc;
    }

    private Package createPackage(final JavaRoot cu, final List<String> namespace) {
        final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
        jaMoPPPackage.getNamespaces().addAll(namespace);
        // attach dummy resource in order to enable TUID calculation
        final VURI vuri = VURI.getInstance(cu.eResource());
        String packageURIString = vuri.toString();
        final String lastSegment = vuri.getLastSegment();
        if (packageURIString.endsWith(lastSegment)) {
            final int newLength = packageURIString.length() - lastSegment.length();
            packageURIString = packageURIString.substring(0, newLength);
            packageURIString = packageURIString + "package-info.java";
        }
        final VURI packageVuri = VURI.getInstance(packageURIString);
        final Resource dummyResource = this.dummyResourceSet.createResource(packageVuri.getEMFUri());
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
    private BasicComponent findCorrespondingBasicComponentForPackage(final Package jaMoPPPackage,
            final CorrespondenceInstance ci) {
        if (0 == jaMoPPPackage.getNamespaces().size()) {
            return null;
        }
        final Set<BasicComponent> correspondingComponents = ci.getCorrespondingEObjectsByType(jaMoPPPackage,
                BasicComponent.class);
        if (null == correspondingComponents || correspondingComponents.isEmpty()) {

            jaMoPPPackage.getNamespaces().remove(jaMoPPPackage.getNamespaces().size() - 1);
            return this.findCorrespondingBasicComponentForPackage(jaMoPPPackage, ci);
        }
        return correspondingComponents.iterator().next();
    }

}
