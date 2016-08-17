package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavapojo.transformations;

import java.util.List;
import java.util.concurrent.Callable;

import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
import org.somox.gast2seff.visitors.ResourceDemandingBehaviourForClassMethodFinding;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.ClassMethodBodyChangedTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.Java2PcmMethodBodyChangePreprocessor;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPChange2CommandTransformerBase;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ClassMethodMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.CompilationUnitMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.FieldMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.InterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.MethodMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ModifierMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.PackageMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.TypeReferenceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.OperationProvidedRoleMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.OperationRequiredRoleMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.BasicComponentMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.CollectionDataTypeMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.CompositeComponentMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.CompositeDataTypeMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.InnerDeclarationMappingTransforamtion;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.OperationInterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.OperationSignatureMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.ParameterMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.RepositoryMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.ResourceDemandingInternalBehaviorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.repository.SEFFMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.system.AssemblyConnectorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.system.AssemblyContextMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.system.ProvidedDelegationConnectorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.system.RequiredDelegationConnectorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java.pcm2java.system.SystemMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.ConcreteChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.GeneralChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.TransactionalChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.javaextension.change.feature.JavaFeatureEChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusTransformationRecordingCommand;

public class PCMJaMoPPPOJOChange2CommandTransformer extends PCMJaMoPPChange2CommandTransformerBase {

    public PCMJaMoPPPOJOChange2CommandTransformer() {
    }

    @Override
    protected void initializeTransformationExecuter() {
        // PCM2JaMoPP
        // Repository
        this.transformationExecuter.addMapping(new RepositoryMappingTransformation());
        this.transformationExecuter.addMapping(new BasicComponentMappingTransformation());
        this.transformationExecuter.addMapping(new CompositeComponentMappingTransformation());
        this.transformationExecuter.addMapping(new OperationInterfaceMappingTransformation());
        this.transformationExecuter.addMapping(new OperationSignatureMappingTransformation());
        this.transformationExecuter.addMapping(new ParameterMappingTransformation());
        this.transformationExecuter.addMapping(new CollectionDataTypeMappingTransformation());
        this.transformationExecuter.addMapping(new CompositeDataTypeMappingTransformation());
        this.transformationExecuter.addMapping(new InnerDeclarationMappingTransforamtion());
        this.transformationExecuter.addMapping(new SEFFMappingTransformation());
        this.transformationExecuter.addMapping(new ResourceDemandingInternalBehaviorMappingTransformation());
        // System
        this.transformationExecuter.addMapping(new SystemMappingTransformation());
        this.transformationExecuter.addMapping(new AssemblyContextMappingTransformation());
        this.transformationExecuter.addMapping(new AssemblyConnectorMappingTransformation());
        this.transformationExecuter.addMapping(new ProvidedDelegationConnectorMappingTransformation());
        this.transformationExecuter.addMapping(new RequiredDelegationConnectorMappingTransformation());
        // Repository and System
        this.transformationExecuter.addMapping(new OperationProvidedRoleMappingTransformation());
        this.transformationExecuter.addMapping(new OperationRequiredRoleMappingTransformation());

        // JaMoPP2PCM
        this.transformationExecuter.addMapping(new PackageMappingTransformation());
        this.transformationExecuter.addMapping(new CompilationUnitMappingTransformation());
        this.transformationExecuter.addMapping(new ClassMappingTransformation());
        this.transformationExecuter.addMapping(new InterfaceMappingTransformation());
        this.transformationExecuter.addMapping(new MethodMappingTransformation());
        this.transformationExecuter.addMapping(
                new edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ParameterMappingTransformation());
        this.transformationExecuter.addMapping(new ModifierMappingTransformation());
        this.transformationExecuter.addMapping(new FieldMappingTransformation());
        this.transformationExecuter.addMapping(new ClassMethodMappingTransformation());
        this.transformationExecuter.addMapping(new TypeReferenceMappingTransformation());

        // execute initializetransformationExecuter as last statement: it sets the user interactor
        // for
        // all mappings
        super.initializeTransformationExecuter();
    }

    @Override
    protected boolean hasChangeRefinerForChanges(final List<VitruviusChange> changesForTransformation) {
        if (1 == changesForTransformation.size() && changesForTransformation.get(0) instanceof TransactionalChange) {
            final TransactionalChange compositeChange = (TransactionalChange) changesForTransformation.get(0);
            final Java2PcmMethodBodyChangePreprocessor refiner = new Java2PcmMethodBodyChangePreprocessor(new POJOJava2PCMCode2SEFFFactory());
            return refiner.match(compositeChange);
        }
        return false;
    }

    @Override
    protected VitruviusTransformationRecordingCommand executeChangeRefiner(final List<VitruviusChange> changesForTransformation,
            final Blackboard blackboard) {
        final TransactionalChange compositeChange = (TransactionalChange) changesForTransformation.get(0);
        final VitruviusTransformationRecordingCommand vitruviusCommand = EMFCommandBridge
                .createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {

                    @Override
                    public TransformationResult call() {
                        return PCMJaMoPPPOJOChange2CommandTransformer.this
                                .executeClassMethodBodyChangeRefiner(blackboard, compositeChange);

                    }
                });
        return vitruviusCommand;
    }

    private TransformationResult executeClassMethodBodyChangeRefiner(final Blackboard blackboard,
            final TransactionalChange compositeChange) {
        final CorrespondenceInstance correspondenceInstance = blackboard.getCorrespondenceInstance();
        final GeneralChange emfChange = (GeneralChange) compositeChange.getChanges().get(0);
        final JavaFeatureEChange<?,?> eFeatureChange = (JavaFeatureEChange<?,?>) emfChange.getEChanges().get(0);
        final ClassMethod oldMethod = (ClassMethod) eFeatureChange.getOldAffectedEObject();
        final ClassMethod newMethod = (ClassMethod) eFeatureChange.getAffectedEObject();
        final BasicComponentForPackageMappingFinder basicComponentFinder = new BasicComponentForPackageMappingFinder();
        final BasicComponent myBasicComponent = basicComponentFinder.findBasicComponentForMethod(newMethod,
                correspondenceInstance);
        final FunctionClassificationStrategyForPackageMapping classification = new FunctionClassificationStrategyForPackageMapping(
                basicComponentFinder, correspondenceInstance, myBasicComponent);
        final InterfaceOfExternalCallFinding interfaceOfExternalCallFinder = new InterfaceOfExternalCallFinderForPackageMapping(
                correspondenceInstance, myBasicComponent);
        final ResourceDemandingBehaviourForClassMethodFinding resourceDemandingBehaviourForClassMethodFinding = new ResourceDemandingBehaviourForClassMethodFinderForPackageMapping(
                correspondenceInstance);
        final ClassMethodBodyChangedTransformation methodBodyChanged = new ClassMethodBodyChangedTransformation(
                oldMethod, newMethod, basicComponentFinder, classification, interfaceOfExternalCallFinder,
                resourceDemandingBehaviourForClassMethodFinding);
        return methodBodyChanged.execute(blackboard, this.userInteracting);
    }
}
