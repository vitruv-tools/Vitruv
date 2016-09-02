package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.seffstatements.code2seff;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.somox.gast2seff.visitors.AbstractFunctionClassificationStrategy;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding;

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.seffstatements.code2seff.BasicComponentFinding;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.seffstatements.code2seff.Code2SEFFFactory;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;

public class POJOJava2PCMCode2SEFFFactory implements Code2SEFFFactory {

    @Override
    public BasicComponentFinding createBasicComponentFinding() {
        return new BasicComponentForPackageMappingFinder();
    }

    @Override
    public InterfaceOfExternalCallFinding createInterfaceOfExternalCallFinding(
            final CorrespondenceModel correspondenceModel, final BasicComponent basicComponent) {
        return new InterfaceOfExternalCallFinderForPackageMapping(correspondenceModel, basicComponent);
    }

    @Override
    public ResourceDemandingBehaviourForClassMethodFinding createResourceDemandingBehaviourForClassMethodFinding(
            final CorrespondenceModel correspondenceModel) {
        return new ResourceDemandingBehaviourForClassMethodFinderForPackageMapping(correspondenceModel);
    }

    @Override
    public AbstractFunctionClassificationStrategy createAbstractFunctionClassificationStrategy(
            final BasicComponentFinding basicComponentFinding,
            final CorrespondenceModel correspondenceModel, final BasicComponent basicComponent) {
        return new FunctionClassificationStrategyForPackageMapping(basicComponentFinding, correspondenceModel,
                basicComponent);
    }

}
