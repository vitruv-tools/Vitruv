package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavapojo.transformations;

import org.emftext.language.java.members.ClassMethod;
import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodBodyChangedChangeRefiner;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff.ClassMethodBodyChangedTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.PCMJaMoPPTransformationExecuterBase;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.CompilationUnitMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.FieldMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.InterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.MethodMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ModifierMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.PackageMappingTransformation;
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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;

public class PCMJaMoPPPOJOTransformationExecuter extends PCMJaMoPPTransformationExecuterBase {

    public PCMJaMoPPPOJOTransformationExecuter() {
    }

    @Override
    protected void initializeChangeSynchronizer() {
        super.initializeChangeSynchronizer();
        // PCM2JaMoPP
        // Repository
        this.changeSynchronizer.addMapping(new RepositoryMappingTransformation());
        this.changeSynchronizer.addMapping(new BasicComponentMappingTransformation());
        this.changeSynchronizer.addMapping(new CompositeComponentMappingTransformation());
        this.changeSynchronizer.addMapping(new OperationInterfaceMappingTransformation());
        this.changeSynchronizer.addMapping(new OperationSignatureMappingTransformation());
        this.changeSynchronizer.addMapping(new ParameterMappingTransformation());
        this.changeSynchronizer.addMapping(new CollectionDataTypeMappingTransformation());
        this.changeSynchronizer.addMapping(new CompositeDataTypeMappingTransformation());
        this.changeSynchronizer.addMapping(new InnerDeclarationMappingTransforamtion());
        this.changeSynchronizer.addMapping(new SEFFMappingTransformation());
        // System
        this.changeSynchronizer.addMapping(new SystemMappingTransformation());
        this.changeSynchronizer.addMapping(new AssemblyContextMappingTransformation());
        this.changeSynchronizer.addMapping(new AssemblyConnectorMappingTransformation());
        this.changeSynchronizer.addMapping(new ProvidedDelegationConnectorMappingTransformation());
        this.changeSynchronizer.addMapping(new RequiredDelegationConnectorMappingTransformation());
        // Repository and System
        this.changeSynchronizer.addMapping(new OperationProvidedRoleMappingTransformation());
        this.changeSynchronizer.addMapping(new OperationRequiredRoleMappingTransformation());

        // JaMoPP2PCM
        this.changeSynchronizer.addMapping(new PackageMappingTransformation());
        this.changeSynchronizer.addMapping(new CompilationUnitMappingTransformation());
        this.changeSynchronizer.addMapping(new ClassMappingTransformation());
        this.changeSynchronizer.addMapping(new InterfaceMappingTransformation());
        this.changeSynchronizer.addMapping(new MethodMappingTransformation());
        this.changeSynchronizer
                .addMapping(new edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm.ParameterMappingTransformation());
        this.changeSynchronizer.addMapping(new ModifierMappingTransformation());
        this.changeSynchronizer.addMapping(new FieldMappingTransformation());

    }

    @Override
    public EMFChangeResult executeTransformation(final CompositeChange compositeChange,
            final CorrespondenceInstance correspondenceInstance) {

        // TODO: kind of hack to execute the ClassMethodBodyChangedTransformation
        final JavaMethodBodyChangedChangeRefiner refiner = new JavaMethodBodyChangedChangeRefiner(null);
        if (refiner.match(compositeChange)) {
            return this.executeClassMethodBodyChangeRefiner(correspondenceInstance, compositeChange);
        }

        return super.executeTransformation(compositeChange, correspondenceInstance);
    }

    private EMFChangeResult executeClassMethodBodyChangeRefiner(final CorrespondenceInstance correspondenceInstance,
            final CompositeChange compositeChange) {
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
        return methodBodyChanged.execute(correspondenceInstance, this.userInteracting, null);
    }
}
