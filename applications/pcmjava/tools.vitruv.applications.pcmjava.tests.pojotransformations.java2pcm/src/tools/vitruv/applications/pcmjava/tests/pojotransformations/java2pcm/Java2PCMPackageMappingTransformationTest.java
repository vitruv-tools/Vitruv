package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import java.util.Collections;
import java.util.List;

import org.palladiosimulator.pcm.repository.CompositeDataType;

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.transformations.ClassMappingTransformation;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.JavaToPcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.tests.util.JaMoPP2PCMTransformationTest;
import tools.vitruv.framework.change.processing.ChangeProcessor;

public class Java2PCMPackageMappingTransformationTest extends JaMoPP2PCMTransformationTest {

	@Override
	protected List<ChangeProcessor> createChangePropagationSpecifications() {
		return Collections.singletonList(new JavaToPcmChangePropagationSpecification());
	}

	protected CompositeDataType addClassThatCorrespondsToCompositeDatatype() throws Throwable {
		this.testUserInteractor.addNextSelections(ClassMappingTransformation.SELECT_CREATE_COMPOSITE_DATA_TYPE);
		final CompositeDataType cdt = this.addClassInPackage(this.getDatatypesPackage(), CompositeDataType.class);
		return cdt;
	}
	
}
