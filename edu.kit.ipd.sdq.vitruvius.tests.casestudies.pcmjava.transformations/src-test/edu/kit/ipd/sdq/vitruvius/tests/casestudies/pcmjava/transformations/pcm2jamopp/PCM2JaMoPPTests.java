package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.BasicComponentMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.OperationInterfaceMappingTransformationTest;

@RunWith(Suite.class)
/*
 * @SuiteClasses({ BasicComponentMappingTransformationTest.class,
 * OperationInterfaceMappingTransformationTest.class,
 * CollectionDataTypeMappingTransformationTest.class,
 * CompositeDataTypeMappingTransformationTest.class,
 * InnerDeclarationMappingTransformationTest.class,
 * OperationInterfaceMappingTransformationTest.class,
 * OperationProvidedRoleMappingTransformationTest.class,
 * OperationRequiredRoleMappingTransformationTest.class,
 * OperationSignatureMappingTransformationTest.class, PCMParameterMappingTransformationTest.class,
 * RepositoryMappingTransformaitonTest.class,
 * ResourceDemandingInternalBehaviorMappingTransformationTest.class,
 * SEFFMappingTransformationTest.class })
 */
@SuiteClasses({ BasicComponentMappingTransformationTest.class, OperationInterfaceMappingTransformationTest.class })
public class PCM2JaMoPPTests {

}
