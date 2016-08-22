package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.seff

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.BasicComponentFinding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceModelUtil
import java.util.Set
import org.apache.log4j.Logger
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Method
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationSignature
import org.somox.gast2seff.visitors.AbstractFunctionClassificationStrategy
import org.somox.gast2seff.visitors.MethodCallFinder
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.java2pcm.JaMoPP2PCMUtils
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

class EJB2PCMFunctionClassificationStrategy extends AbstractFunctionClassificationStrategy {
	
	private static val Logger logger = Logger.getLogger(EJB2PCMFunctionClassificationStrategy.name)
	
	private final BasicComponentFinding basicComponentFinding
	private final CorrespondenceModel correspondenceModel
	private final BasicComponent basicComponent

	new(BasicComponentFinding basicComponentFinding, CorrespondenceModel correspondenceModel,
		BasicComponent basicComponent) {
		super(new MethodCallFinder())
		this.basicComponentFinding = basicComponentFinding
		this.correspondenceModel = correspondenceModel
		this.basicComponent = basicComponent
	}

    //copied from  FunctionClassificationStrategyForPackageMapping
	override protected boolean isExternalCall(Method method) {
		if (!JaMoPP2PCMUtils.normalizeURI(method)) {
            logger.info("Could not normalize URI for method " + method
                    + ". Method call is not considered as as external call");
            return false;
        }
        if (!JaMoPP2PCMUtils.normalizeURI(method)) {
            logger.info("Could not normalize URI for method " + method
                    + ". Method call is not considered as as external call");
            return false;
        }
        val Set<OperationSignature> correspondingSignatures = CorrespondenceModelUtil
                .getCorrespondingEObjectsByType(this.correspondenceModel, method, OperationSignature);
        if (null != correspondingSignatures && !correspondingSignatures.isEmpty()) {
            return true;
        }
        if (method instanceof ClassMethod) {
            val BasicComponent basicComponent = this.basicComponentFinding.findBasicComponentForMethod(method,
                    this.correspondenceModel);
            if (null == basicComponent || basicComponent.getId().equals(this.basicComponent.getId())) {
                return false;
            }
            return true;
        }
        return false;
	}

	//copied from  FunctionClassificationStrategyForPackageMapping
	override protected boolean isLibraryCall(Method method) {
		 val basicComponentOfMethod = this.basicComponentFinding.findBasicComponentForMethod(method,
                this.correspondenceModel);
        if (null == basicComponentOfMethod) {
            return true;
        }
        if (basicComponentOfMethod.getId().equals(this.basicComponent.getId())) {
            return false;
        }
        logger.warn("The destination of a call to the method " + method
                + " is another component than the source component. This should not happen in isLibraryCall.");
        return true;
	}
}
