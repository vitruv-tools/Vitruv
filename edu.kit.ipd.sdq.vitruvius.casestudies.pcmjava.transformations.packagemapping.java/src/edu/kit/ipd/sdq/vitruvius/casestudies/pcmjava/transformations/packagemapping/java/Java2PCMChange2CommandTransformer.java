package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java;

import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ClassMethodMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.CompilationUnitMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.FieldMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.InterfaceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.MethodMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ModifierMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.PackageMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ParameterMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.TypeReferenceMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public class Java2PCMChange2CommandTransformer extends PCMJaMoPPChange2CommandTransformerBase {

    public Java2PCMChange2CommandTransformer() {
        super(new TransformationMetamodelPair(VURI.getInstance(JaMoPPNamespace.JAMOPP_METAMODEL_NAMESPACE),
        		VURI.getInstance(PCMNamespace.PCM_METAMODEL_NAMESPACE)));
    }

    @Override
    protected void setup() {
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

        // call super setup as last statement: it sets the default mapping and user interactor for all mappings
        super.setup();
    }


}
