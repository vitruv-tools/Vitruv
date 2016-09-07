package tools.vitruv.applications.pcmjava.seffstatements.code2seff;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.somox.gast2seff.visitors.AbstractFunctionClassificationStrategy;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding;

import tools.vitruv.framework.correspondence.CorrespondenceModel;

public interface Code2SEFFFactory {

    BasicComponentFinding createBasicComponentFinding();

    InterfaceOfExternalCallFinding createInterfaceOfExternalCallFinding(
    		CorrespondenceModel correspondenceModel, BasicComponent basicComponent);

    ResourceDemandingBehaviourForClassMethodFinding createResourceDemandingBehaviourForClassMethodFinding(
    		CorrespondenceModel correspondenceModel);

    AbstractFunctionClassificationStrategy createAbstractFunctionClassificationStrategy(
            BasicComponentFinding basicComponentFinding, CorrespondenceModel correspondenceModel,
            BasicComponent basicComponent);
}
