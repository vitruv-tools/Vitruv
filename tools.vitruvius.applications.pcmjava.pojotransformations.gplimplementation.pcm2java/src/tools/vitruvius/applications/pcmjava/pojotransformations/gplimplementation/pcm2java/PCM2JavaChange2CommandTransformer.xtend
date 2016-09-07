package tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java

import tools.vitruvius.domains.java.util.JaMoPPNamespace
import tools.vitruvius.domains.pcm.util.PCMNamespace
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.OperationProvidedRoleMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.OperationRequiredRoleMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.BasicComponentMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.CollectionDataTypeMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.CompositeComponentMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.CompositeDataTypeMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.InnerDeclarationMappingTransforamtion
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.OperationInterfaceMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.OperationSignatureMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.ParameterMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.RepositoryMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.ResourceDemandingInternalBehaviorMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.repository.SEFFMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.system.AssemblyConnectorMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.system.AssemblyContextMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.system.ProvidedDelegationConnectorMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.system.RequiredDelegationConnectorMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.system.SystemMappingTransformation
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransforming
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.DefaultEObjectMappingTransformation
import tools.vitruvius.framework.change.processing.ChangeProcessor
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.TransformationExecutorChangeProcessor

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
		addChangeProcessor(createTransformationExecutorChangeProcessor());
	}
	
}
