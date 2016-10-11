package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.TransformationExecutorChangeProcessor
import tools.vitruv.framework.userinteraction.UserInteracting
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
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.DefaultEObjectMappingTransformation
import tools.vitruv.framework.util.datatypes.MetamodelPair
import org.emftext.language.java.JavaPackage
import org.palladiosimulator.pcm.PcmPackage

class Pcm2JavaChangeProcessor extends TransformationExecutorChangeProcessor {
	private val MetamodelPair metamodelPair;
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
		this.metamodelPair = new MetamodelPair(PcmPackage.eNS_URI, JavaPackage.eNS_URI);

		// Repository
		addMapping(new RepositoryMappingTransformation())
		addMapping(new BasicComponentMappingTransformation())
		addMapping(new CompositeComponentMappingTransformation())
		addMapping(new OperationInterfaceMappingTransformation())
		addMapping(new OperationSignatureMappingTransformation())
		addMapping(new ParameterMappingTransformation())
		addMapping(new CollectionDataTypeMappingTransformation())
		addMapping(new CompositeDataTypeMappingTransformation())
		addMapping(new InnerDeclarationMappingTransforamtion())
		addMapping(new SEFFMappingTransformation())
		addMapping(new ResourceDemandingInternalBehaviorMappingTransformation())
		// System
		addMapping(new SystemMappingTransformation())
		addMapping(new AssemblyContextMappingTransformation())
		addMapping(new AssemblyConnectorMappingTransformation())
		addMapping(new ProvidedDelegationConnectorMappingTransformation())
		addMapping(new RequiredDelegationConnectorMappingTransformation())
		// Repository and System
		addMapping(new OperationProvidedRoleMappingTransformation())
		addMapping(new OperationRequiredRoleMappingTransformation())
		// Mapping for EObjects in order to avoid runtime exceptions
        addMapping(new DefaultEObjectMappingTransformation());
	}
	
	override getMetamodelPair() {
		return metamodelPair;
	}
	
}