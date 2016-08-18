package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.seff

import org.emftext.language.java.members.Method
import org.palladiosimulator.pcm.repository.BasicComponent
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import org.palladiosimulator.pcm.repository.RequiredRole
import org.palladiosimulator.pcm.repository.OperationRequiredRole
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
import java.util.Set
import org.apache.log4j.Logger

class InterfaceOfExternalCallFinder4EJB implements InterfaceOfExternalCallFinding {
	
	private static val Logger logger = Logger.getLogger(InterfaceOfExternalCallFinder4EJB.name)
	
	private final CorrespondenceInstance<Correspondence> correspondenceInstance
	private final BasicComponent basicComponent

	new(CorrespondenceInstance<Correspondence> correspondenceInstance, BasicComponent basicComponent) {
		this.correspondenceInstance = correspondenceInstance
		this.basicComponent = basicComponent
	}

	override InterfacePortOperationTuple getCalledInterfacePort(Method calledMethod) {
		val interfacePortOperationTuple = new org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding.InterfacePortOperationTuple
		val OperationSignature opSig = calledMethod.queryInterfaceOperation
		if(null != opSig){
		interfacePortOperationTuple.signature = opSig;
            val accessedOpIf = opSig.getInterface__OperationSignature()
            for (RequiredRole requiredRole : this.basicComponent.getRequiredRoles_InterfaceRequiringEntity()) {
                if (requiredRole instanceof OperationRequiredRole) {
                    val OperationRequiredRole orr = requiredRole as OperationRequiredRole;
                    if (orr.getRequiredInterface__OperationRequiredRole().getId().equals(accessedOpIf.getId())) {
                        interfacePortOperationTuple.role = orr;
                        return interfacePortOperationTuple
                    }
                }
            }
        }
		return null
	}
	
	    /**
     * Returns the OperationSignature for the invoked method. If the invoked method directly
     * corresponds to an OperationSignature we are finished already. If the invoked method
     * corresponds to a SEFF (aka. it is a class method that implements an interface method, that
     * corresponds to an operation signature) we can use the operation signature from the SEFF.
     * copied from InterfaceOfExternalCallFinderForPackageMapping
     * 
     * @param invokedMethod
     * @return
     */
    private def OperationSignature queryInterfaceOperation( Method invokedMethod) {
        val Set<OperationSignature> correspondingOpSigs = CorrespondenceInstanceUtil
                .getCorrespondingEObjectsByType(this.correspondenceInstance, invokedMethod, OperationSignature);
        if (null != correspondingOpSigs && 0 < correspondingOpSigs.size()) {
            return correspondingOpSigs.iterator().next();
        }
        val Set<ResourceDemandingSEFF> correspondingRDSEFFs = CorrespondenceInstanceUtil
                .getCorrespondingEObjectsByType(this.correspondenceInstance, invokedMethod,
                        ResourceDemandingSEFF);
        if (null != correspondingRDSEFFs && 0 < correspondingRDSEFFs.size()) {
            for (ResourceDemandingSEFF seff : correspondingRDSEFFs) {
                if (seff.getDescribedService__SEFF() instanceof OperationSignature) {
                    return seff.getDescribedService__SEFF() as OperationSignature;
                }
            }
        }
        logger.warn("Could not find operation signature for method " + invokedMethod);
        return null;
    }
}
