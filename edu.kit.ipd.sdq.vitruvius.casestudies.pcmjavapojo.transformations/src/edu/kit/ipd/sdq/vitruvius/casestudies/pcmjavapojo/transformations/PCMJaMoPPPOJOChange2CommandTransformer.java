package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavapojo.transformations;

import java.util.List;

import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodBodyChangedChangeRefiner;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.ClassMethodBodyChangedTransformation;
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
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.OperationProvidedRoleMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.OperationRequiredRoleMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.BasicComponentMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.CollectionDataTypeMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.CompositeComponentMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.CompositeDataTypeMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.InnerDeclarationMappingTransforamtion;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.OperationInterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.OperationSignatureMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.ParameterMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.RepositoryMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.SEFFMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system.AssemblyConnectorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system.AssemblyContextMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system.ProvidedDelegationConnectorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system.RequiredDelegationConnectorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.system.SystemMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationRunnable;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;

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
    protected boolean hasChangeRefinerForChanges(final List<Change> changesForTransformation) {
        if (1 == changesForTransformation.size() && changesForTransformation.get(0) instanceof CompositeChange) {
            final CompositeChange compositeChange = (CompositeChange) changesForTransformation.get(0);
            final JavaMethodBodyChangedChangeRefiner refiner = new JavaMethodBodyChangedChangeRefiner(null);
            return refiner.match(compositeChange);
        }
        return false;
    }

    @Override
    protected VitruviusRecordingCommand executeChangeRefiner(final List<Change> changesForTransformation,
            final Blackboard blackboard) {
        final CompositeChange compositeChange = (CompositeChange) changesForTransformation.get(0);
        final VitruviusRecordingCommand vitruviusCommand = EMFCommandBridge
                .createVitruviusRecordingCommand(new TransformationRunnable() {

                    @Override
                    public TransformationResult runTransformation() {
                        return PCMJaMoPPPOJOChange2CommandTransformer.this
                                .executeClassMethodBodyChangeRefiner(blackboard, compositeChange);

                    }
                });
        return vitruviusCommand;
    }

    private TransformationResult executeClassMethodBodyChangeRefiner(final Blackboard blackboard,
            final CompositeChange compositeChange) {
        final CorrespondenceInstance correspondenceInstance = blackboard.getCorrespondenceInstance();
        final EMFModelChange emfChange = (EMFModelChange) compositeChange.getChanges().get(0);
        final EFeatureChange<?> eFeatureChange = (EFeatureChange<?>) emfChange.getEChange();
        final ClassMethod oldMethod = (ClassMethod) eFeatureChange.getOldAffectedEObject();
        final ClassMethod newMethod = (ClassMethod) eFeatureChange.getNewAffectedEObject();
        final BasicComponentForPackageMappingFinder basicComponentFinder = new BasicComponentForPackageMappingFinder();
        final BasicComponent myBasicComponent = basicComponentFinder.findBasicComponentForMethod(newMethod,
                correspondenceInstance);
        final FunctionClassificationStrategyForPackageMapping classification = new FunctionClassificationStrategyForPackageMapping(
                basicComponentFinder, correspondenceInstance, myBasicComponent);
        final InterfaceOfExternalCallFinding interfaceOfExternalCallFinder = new InterfaceOfExternalCallFinderForPackageMapping(
                correspondenceInstance, myBasicComponent);
        final ClassMethodBodyChangedTransformation methodBodyChanged = new ClassMethodBodyChangedTransformation(
                oldMethod, newMethod, basicComponentFinder, classification, interfaceOfExternalCallFinder);
        return methodBodyChanged.execute(blackboard, this.userInteracting, null);
    }
}
