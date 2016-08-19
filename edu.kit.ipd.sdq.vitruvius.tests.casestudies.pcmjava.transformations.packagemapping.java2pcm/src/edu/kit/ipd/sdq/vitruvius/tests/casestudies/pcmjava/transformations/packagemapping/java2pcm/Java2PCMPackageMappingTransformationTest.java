package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.java2pcm;

import org.palladiosimulator.pcm.repository.CompositeDataType;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.JaMoPP2PCMTransformationTest;

public class Java2PCMPackageMappingTransformationTest extends JaMoPP2PCMTransformationTest {

    protected CompositeDataType addClassThatCorrespondsToCompositeDatatype() throws Throwable {
        this.testUserInteractor.addNextSelections(ClassMappingTransformation.SELECT_CREATE_COMPOSITE_DATA_TYPE);
        final CompositeDataType cdt = this.addClassInPackage(this.getDatatypesPackage(), CompositeDataType.class);
        return cdt;
    }
    
}
