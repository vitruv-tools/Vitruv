package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java;

import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace;
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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public class PCM2JavaChange2CommandTransformer extends PCMJaMoPPChange2CommandTransformerBase {

    public PCM2JavaChange2CommandTransformer() {
        super(new TransformationMetamodelPair(VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE), 
        		VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE)));
    }

    @Override
    protected void setup() {
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

        // call super setup as last statement: it sets the default mapping and user interactor for all mappings
        super.setup();
    }

}
