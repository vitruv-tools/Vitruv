package tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm

import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.TransformationExecutorChangeProcessor
import tools.vitruvius.framework.userinteraction.UserInteracting
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.PackageMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.CompilationUnitMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.ClassMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.InterfaceMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.MethodMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.ParameterMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.ModifierMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.FieldMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.ClassMethodMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.TypeReferenceMappingTransformation
import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.DefaultEObjectMappingTransformation

class Java2PCMChangeProcessor extends TransformationExecutorChangeProcessor {
	new(UserInteracting userInteracting) {
		super(userInteracting);
		
		addMapping(new PackageMappingTransformation())
		addMapping(new CompilationUnitMappingTransformation())
		addMapping(new ClassMappingTransformation())
		addMapping(new InterfaceMappingTransformation())
		addMapping(new MethodMappingTransformation())
		addMapping(new ParameterMappingTransformation())
		addMapping(new ModifierMappingTransformation())
		addMapping(new FieldMappingTransformation())
		addMapping(new ClassMethodMappingTransformation())
		addMapping(new TypeReferenceMappingTransformation())
		// Mapping for EObjects in order to avoid runtime exceptions
		addMapping(new DefaultEObjectMappingTransformation());
	}
	
}