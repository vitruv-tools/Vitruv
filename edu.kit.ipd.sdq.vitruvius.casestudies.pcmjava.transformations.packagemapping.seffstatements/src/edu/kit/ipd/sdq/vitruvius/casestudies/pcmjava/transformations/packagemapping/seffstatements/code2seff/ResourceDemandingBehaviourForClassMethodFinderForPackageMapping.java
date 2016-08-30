package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements.code2seff;

import java.util.Set;

import org.apache.log4j.Logger;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil;

public class ResourceDemandingBehaviourForClassMethodFinderForPackageMapping
        implements ResourceDemandingBehaviourForClassMethodFinding {

    private static final Logger logger = Logger
            .getLogger(ResourceDemandingBehaviourForClassMethodFinderForPackageMapping.class.getSimpleName());

    private final CorrespondenceModel correspondenceModel;

    public ResourceDemandingBehaviourForClassMethodFinderForPackageMapping(
            final CorrespondenceModel correspondenceModel) {
        this.correspondenceModel = correspondenceModel;
    }

    @Override
    public ResourceDemandingSEFF getCorrespondingRDSEFForClassMethod(final ClassMethod classMethod) {
        return this.getFirstCorrespondingEObjectIfAny(classMethod, ResourceDemandingSEFF.class);
    }

    private <T> T getFirstCorrespondingEObjectIfAny(final ClassMethod classMethod, final Class<T> correspondingClass) {
        final Set<T> correspondingObjects = CorrespondenceModelUtil
                .getCorrespondingEObjectsByType(this.correspondenceModel, classMethod, correspondingClass);
        if (correspondingObjects == null || correspondingObjects.isEmpty()) {
            return null;
        }
        if (1 < correspondingObjects.size()) {
            logger.warn("Found " + correspondingObjects.size() + " corresponding Objects from Type "
                    + correspondingClass + " for ClassMethod " + classMethod + " Returning the first.");
        }
        return correspondingObjects.iterator().next();
    }

    @Override
    public ResourceDemandingInternalBehaviour getCorrespondingResourceDemandingInternalBehaviour(
            final ClassMethod classMethod) {
        return this.getFirstCorrespondingEObjectIfAny(classMethod, ResourceDemandingInternalBehaviour.class);
    }

}
