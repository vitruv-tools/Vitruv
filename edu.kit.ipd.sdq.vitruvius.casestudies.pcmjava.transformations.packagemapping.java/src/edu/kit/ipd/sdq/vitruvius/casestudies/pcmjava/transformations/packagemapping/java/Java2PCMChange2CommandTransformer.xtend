package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java

import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ClassMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ClassMethodMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.CompilationUnitMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.FieldMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.InterfaceMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.MethodMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ModifierMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.PackageMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ParameterMappingTransformation
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.TypeReferenceMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.AbstractChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.DefaultEObjectMappingTransformation
import edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor.ChangeProcessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.util.Java2PcmPackagePreprocessor
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.seffstatements.Java2PcmPackageMappingMethodBodyChangePreprocessor

class Java2PCMChange2CommandTransformer extends AbstractChange2CommandTransforming {
	
	new() {
		super(
			new TransformationMetamodelPair(VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE),
				VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE)))
	}

	private def ChangeProcessor createTransformationExecutorChangeProcessor() {
		val changeProcessor = new TransformationExecutorChangeProcessor(userInteracting);
		changeProcessor.addMapping(new PackageMappingTransformation())
		changeProcessor.addMapping(new CompilationUnitMappingTransformation())
		changeProcessor.addMapping(new ClassMappingTransformation())
		changeProcessor.addMapping(new InterfaceMappingTransformation())
		changeProcessor.addMapping(new MethodMappingTransformation())
		changeProcessor.addMapping(new ParameterMappingTransformation())
		changeProcessor.addMapping(new ModifierMappingTransformation())
		changeProcessor.addMapping(new FieldMappingTransformation())
		changeProcessor.addMapping(new ClassMethodMappingTransformation())
		changeProcessor.addMapping(new TypeReferenceMappingTransformation())
		// Mapping for EObjects in order to avoid runtime exceptions
        changeProcessor.addMapping(new DefaultEObjectMappingTransformation());
        
        return changeProcessor;
	}
	
	override protected setup() {
		addChangeProcessor(new Java2PcmPackagePreprocessor(userInteracting));
		addChangeProcessor(new Java2PcmPackageMappingMethodBodyChangePreprocessor(userInteracting));
		addChangeProcessor(createTransformationExecutorChangeProcessor());
	}
	
}
