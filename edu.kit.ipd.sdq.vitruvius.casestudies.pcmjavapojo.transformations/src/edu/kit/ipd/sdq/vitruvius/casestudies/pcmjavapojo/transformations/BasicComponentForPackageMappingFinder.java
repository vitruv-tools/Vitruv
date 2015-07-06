package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavapojo.transformations;

import java.util.Set;

import org.apache.log4j.Logger;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.members.Method;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.BasicComponentFinding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;

/**
 * Finds the component for a method if the the simple package mapping structure is used.
 *
 * @author langhamm
 *
 */
public class BasicComponentForPackageMappingFinder implements BasicComponentFinding {

    private static final Logger logger = Logger.getLogger(BasicComponentForPackageMappingFinder.class.getSimpleName());

    /**
     * To find the BasicComponent we try to find a corresponding component for the package of the
     * method. If the class of the method is in a nested package of a component we find the matching
     * component by finding the BasicComponent of the parent component.
     */
    @Override
    public BasicComponent findBasicComponentForMethod(final Method newMethod, final CorrespondenceInstance ci) {
        final CompilationUnit cu = newMethod.getContainingCompilationUnit();
        final Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
        jaMoPPPackage.getNamespaces().addAll(cu.getNamespaces());
        final BasicComponent correspondingBc = this.findCorrespondingBasicComponentForPackage(jaMoPPPackage, ci);
        if (null == correspondingBc) {
            logger.info("Could not find basic component for method " + newMethod + " in package " + jaMoPPPackage);
        }
        return correspondingBc;
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
        if (correspondingComponents.isEmpty()) {
            jaMoPPPackage.getNamespaces().remove(jaMoPPPackage.getNamespaces().size() - 1);
            return this.findCorrespondingBasicComponentForPackage(jaMoPPPackage, ci);
        }
        return correspondingComponents.iterator().next();
    }

}
