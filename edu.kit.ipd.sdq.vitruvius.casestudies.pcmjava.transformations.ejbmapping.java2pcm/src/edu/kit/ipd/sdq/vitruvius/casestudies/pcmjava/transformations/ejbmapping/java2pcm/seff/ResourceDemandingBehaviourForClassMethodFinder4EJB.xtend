package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.seff

import org.emftext.language.java.members.ClassMethod
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding
import java.util.Set
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceModelUtil
import org.apache.log4j.Logger
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

// copied from ResourceDemandingBehaviourForClassMethodFinderForPackageMapping
class ResourceDemandingBehaviourForClassMethodFinder4EJB implements ResourceDemandingBehaviourForClassMethodFinding {
	
	val private static Logger logger = Logger.getLogger(ResourceDemandingBehaviourForClassMethodFinder4EJB.name)

	private	final CorrespondenceModel correspondenceModel

	new(CorrespondenceModel correspondenceModel) {
		this.correspondenceModel = correspondenceModel
	}

	override ResourceDemandingSEFF getCorrespondingRDSEFForClassMethod(ClassMethod classMethod) {
		return this.getFirstCorrespondingEObjectIfAny(classMethod, ResourceDemandingSEFF);
	}

	override ResourceDemandingInternalBehaviour getCorrespondingResourceDemandingInternalBehaviour(
		ClassMethod classMethod) {
		return this.getFirstCorrespondingEObjectIfAny(classMethod, ResourceDemandingInternalBehaviour);
	}
	
	def private <T> T getFirstCorrespondingEObjectIfAny(ClassMethod classMethod, Class<T> correspondingClass) {
        val Set<T> correspondingObjects = CorrespondenceModelUtil
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
}
