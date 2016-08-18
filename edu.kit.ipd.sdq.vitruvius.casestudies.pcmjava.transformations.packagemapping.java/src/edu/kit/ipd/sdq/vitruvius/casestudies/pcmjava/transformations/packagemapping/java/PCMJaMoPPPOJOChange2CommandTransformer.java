package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java;

import java.util.List;

import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ClassMethodMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.CompilationUnitMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.FieldMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.InterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.MethodMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ModifierMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.PackageMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.TypeReferenceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.OperationProvidedRoleMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.OperationRequiredRoleMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.BasicComponentMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.CollectionDataTypeMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.CompositeComponentMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.CompositeDataTypeMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.InnerDeclarationMappingTransforamtion;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.OperationInterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.OperationSignatureMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.ParameterMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.RepositoryMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.ResourceDemandingInternalBehaviorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository.SEFFMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.system.AssemblyConnectorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.system.AssemblyContextMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.system.ProvidedDelegationConnectorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.system.RequiredDelegationConnectorMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.system.SystemMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;

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
        this.transformationExecuter.addMapping(new ParameterMappingTransformation());
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
        if (changesForTransformation.size() == 1) {
        	return new Java2PcmPackageMappingMethodBodyChangePreprocessor().doesProcess(changesForTransformation.get(0));
        }
        return false;
    }

    @Override
    protected Command executeChangeRefiner(final List<VitruviusChange> changesForTransformation,
            final Blackboard blackboard) {
    	return new Java2PcmPackageMappingMethodBodyChangePreprocessor().processChange(changesForTransformation.get(0), null, blackboard).iterator().next();
    }

}
