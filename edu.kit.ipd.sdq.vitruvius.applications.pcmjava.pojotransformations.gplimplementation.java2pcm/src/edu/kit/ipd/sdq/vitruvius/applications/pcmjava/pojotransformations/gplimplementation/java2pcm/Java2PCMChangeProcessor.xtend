package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.TransformationExecutorChangeProcessor
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.PackageMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.CompilationUnitMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.ClassMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.InterfaceMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.MethodMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.ParameterMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.ModifierMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.FieldMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.ClassMethodMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.java2pcm.transformations.TypeReferenceMappingTransformation
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.DefaultEObjectMappingTransformation

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