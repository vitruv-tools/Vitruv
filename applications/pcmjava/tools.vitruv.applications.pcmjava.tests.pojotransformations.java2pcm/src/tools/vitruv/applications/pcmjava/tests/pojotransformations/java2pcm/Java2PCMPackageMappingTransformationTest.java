package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import java.util.Collections;

import org.palladiosimulator.pcm.repository.CompositeDataType;

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.transformations.ClassMappingTransformation;
import tools.vitruv.applications.pcmjava.tests.util.JaMoPP2PCMTransformationTest;
import tools.vitruv.framework.change.processing.Change2CommandTransformingProviding;
import tools.vitruv.framework.change.processing.impl.AbstractChange2CommandTransformingProviding;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Change2CommandTransformingJavaToPcm;

public class Java2PCMPackageMappingTransformationTest extends JaMoPP2PCMTransformationTest {

	@Override
	protected Change2CommandTransformingProviding createChange2CommandTransformingProviding() {
		return AbstractChange2CommandTransformingProviding.createChange2CommandTransformingProviding(
				Collections.singletonList(new Change2CommandTransformingJavaToPcm()));
	}

	protected CompositeDataType addClassThatCorrespondsToCompositeDatatype() throws Throwable {
		this.testUserInteractor.addNextSelections(ClassMappingTransformation.SELECT_CREATE_COMPOSITE_DATA_TYPE);
		final CompositeDataType cdt = this.addClassInPackage(this.getDatatypesPackage(), CompositeDataType.class);
		return cdt;
	}
	
}
