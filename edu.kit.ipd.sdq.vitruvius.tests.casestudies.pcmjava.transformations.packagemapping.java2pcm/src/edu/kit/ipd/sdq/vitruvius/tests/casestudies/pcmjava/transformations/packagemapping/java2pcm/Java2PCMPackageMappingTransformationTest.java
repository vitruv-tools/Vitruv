package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.java2pcm;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Supplier;

import org.apache.log4j.Logger;
import org.palladiosimulator.pcm.repository.CompositeDataType;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.java2pcm.transformations.ClassMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.JaMoPP2PCMTransformationTest;

public class Java2PCMPackageMappingTransformationTest extends JaMoPP2PCMTransformationTest {
	private static Logger logger = Logger.getLogger(Java2PCMPackageMappingTransformationTest.class);
	
	public Java2PCMPackageMappingTransformationTest(Supplier<Change2CommandTransformingProviding> change2CommandTransformingProvidingSupplier) {
		super(change2CommandTransformingProvidingSupplier);
	}
	
	public Java2PCMPackageMappingTransformationTest() {
		super(() -> createChange2CommandTransformingProvidingFromVMArgument());
	}
	
    protected CompositeDataType addClassThatCorrespondsToCompositeDatatype() throws Throwable {
        this.testUserInteractor.addNextSelections(ClassMappingTransformation.SELECT_CREATE_COMPOSITE_DATA_TYPE);
        final CompositeDataType cdt = this.addClassInPackage(this.getDatatypesPackage(), CompositeDataType.class);
        return cdt;
    }
    
    /**
	 * Create a Change2CommandTransformingProviding, if possible from the command line argument "transfomerClass",
	 * otherwise the default one.
     * @return 
	 */
    protected static Change2CommandTransformingProviding createChange2CommandTransformingProvidingFromVMArgument() {
    	Change2CommandTransformingProviding result;
		try {
			String transformerClass = System.getProperty("transformerClass");
			Class<?> clazz = Class.forName(transformerClass);
			Constructor<?> ctor = clazz.getConstructor();
			Change2CommandTransforming transformer = (Change2CommandTransforming) ctor.newInstance();
			logger.debug("Transformer class used for test: " + transformerClass);
			result = AbstractChange2CommandTransformingProviding.createChange2CommandTransformingProviding(
					Collections.singletonList(transformer));
		} catch (Exception e) {
			logger.debug("Transformer class used for test: DEFAULT");
			result = new Change2CommandTransformingProvidingImpl();
		}
		return result;
    }
    
}
