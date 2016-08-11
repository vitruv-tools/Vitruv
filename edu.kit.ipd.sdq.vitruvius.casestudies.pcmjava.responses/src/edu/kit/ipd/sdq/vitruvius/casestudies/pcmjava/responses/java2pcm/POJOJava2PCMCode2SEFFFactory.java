package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.somox.gast2seff.visitors.AbstractFunctionClassificationStrategy;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm.transformations.BasicComponentForPackageMappingFinder;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm.transformations.FunctionClassificationStrategyForPackageMapping;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm.transformations.InterfaceOfExternalCallFinderForPackageMapping;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.java2pcm.transformations.ResourceDemandingBehaviourForClassMethodFinderForPackageMapping;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.BasicComponentFinding;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Code2SEFFFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;

public class POJOJava2PCMCode2SEFFFactory implements Code2SEFFFactory {

    @Override
    public BasicComponentFinding createBasicComponentFinding() {
        return new BasicComponentForPackageMappingFinder();
    }

    @Override
    public InterfaceOfExternalCallFinding createInterfaceOfExternalCallFinding(
            final CorrespondenceInstance<Correspondence> correspondenceInstance, final BasicComponent basicComponent) {
        return new InterfaceOfExternalCallFinderForPackageMapping(correspondenceInstance, basicComponent);
    }

    @Override
    public ResourceDemandingBehaviourForClassMethodFinding createResourceDemandingBehaviourForClassMethodFinding(
            final CorrespondenceInstance<Correspondence> correspondenceInstance) {
        return new ResourceDemandingBehaviourForClassMethodFinderForPackageMapping(correspondenceInstance);
    }

    @Override
    public AbstractFunctionClassificationStrategy createAbstractFunctionClassificationStrategy(
            final BasicComponentFinding basicComponentFinding,
            final CorrespondenceInstance<Correspondence> correspondenceInstance, final BasicComponent basicComponent) {
        return new FunctionClassificationStrategyForPackageMapping(basicComponentFinding, correspondenceInstance,
                basicComponent);
    }

}
