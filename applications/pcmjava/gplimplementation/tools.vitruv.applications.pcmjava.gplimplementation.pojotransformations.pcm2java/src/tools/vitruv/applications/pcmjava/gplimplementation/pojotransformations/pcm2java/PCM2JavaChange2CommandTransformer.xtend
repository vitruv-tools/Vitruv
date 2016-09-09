package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java

import tools.vitruv.domains.java.util.JaMoPPNamespace
import tools.vitruv.domains.pcm.util.PCMNamespace
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.OperationProvidedRoleMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.OperationRequiredRoleMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.BasicComponentMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.CollectionDataTypeMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.CompositeComponentMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.CompositeDataTypeMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.InnerDeclarationMappingTransforamtion
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.OperationInterfaceMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.OperationSignatureMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.ParameterMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.RepositoryMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.ResourceDemandingInternalBehaviorMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.repository.SEFFMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.system.AssemblyConnectorMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.system.AssemblyContextMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.system.ProvidedDelegationConnectorMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.system.RequiredDelegationConnectorMappingTransformation
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations.system.SystemMappingTransformation
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.processing.impl.AbstractChange2CommandTransforming
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.DefaultEObjectMappingTransformation
import tools.vitruv.framework.change.processing.ChangeProcessor
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.TransformationExecutorChangeProcessor

class PCM2JavaChange2CommandTransformer extends AbstractChange2CommandTransforming {
	
	new() {
		super(VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE),
				VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE))
	}

	private def ChangeProcessor createTransformationExecutorChangeProcessor() {
		val changeProcessor = new TransformationExecutorChangeProcessor(userInteracting);
		// Repository
		changeProcessor.addMapping(new RepositoryMappingTransformation())
		changeProcessor.addMapping(new BasicComponentMappingTransformation())
		changeProcessor.addMapping(new CompositeComponentMappingTransformation())
		changeProcessor.addMapping(new OperationInterfaceMappingTransformation())
		changeProcessor.addMapping(new OperationSignatureMappingTransformation())
		changeProcessor.addMapping(new ParameterMappingTransformation())
		changeProcessor.addMapping(new CollectionDataTypeMappingTransformation())
		changeProcessor.addMapping(new CompositeDataTypeMappingTransformation())
		changeProcessor.addMapping(new InnerDeclarationMappingTransforamtion())
		changeProcessor.addMapping(new SEFFMappingTransformation())
		changeProcessor.addMapping(new ResourceDemandingInternalBehaviorMappingTransformation())
		// System
		changeProcessor.addMapping(new SystemMappingTransformation())
		changeProcessor.addMapping(new AssemblyContextMappingTransformation())
		changeProcessor.addMapping(new AssemblyConnectorMappingTransformation())
		changeProcessor.addMapping(new ProvidedDelegationConnectorMappingTransformation())
		changeProcessor.addMapping(new RequiredDelegationConnectorMappingTransformation())
		// Repository and System
		changeProcessor.addMapping(new OperationProvidedRoleMappingTransformation())
		changeProcessor.addMapping(new OperationRequiredRoleMappingTransformation())
		// Mapping for EObjects in order to avoid runtime exceptions
        changeProcessor.addMapping(new DefaultEObjectMappingTransformation());
        
        return changeProcessor;
	}
	
	override protected setup() {
		addChangeMainprocessor(createTransformationExecutorChangeProcessor());
	}
	
}
