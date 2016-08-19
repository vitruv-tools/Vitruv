package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.somox.gast2seff.visitors.AbstractFunctionClassificationStrategy;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel;

public interface Code2SEFFFactory {

    BasicComponentFinding createBasicComponentFinding();

    InterfaceOfExternalCallFinding createInterfaceOfExternalCallFinding(
    		CorrespondenceModel correspondenceInstance, BasicComponent basicComponent);

    ResourceDemandingBehaviourForClassMethodFinding createResourceDemandingBehaviourForClassMethodFinding(
    		CorrespondenceModel correspondenceInstance);

    AbstractFunctionClassificationStrategy createAbstractFunctionClassificationStrategy(
            BasicComponentFinding basicComponentFinding, CorrespondenceModel correspondenceInstance,
            BasicComponent basicComponent);
}
