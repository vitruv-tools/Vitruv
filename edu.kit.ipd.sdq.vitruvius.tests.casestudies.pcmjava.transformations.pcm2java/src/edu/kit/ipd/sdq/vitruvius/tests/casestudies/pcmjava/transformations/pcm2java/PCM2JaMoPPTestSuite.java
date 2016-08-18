package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.BasicComponentMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.CollectionDataTypeMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.CompositeComponentMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.CompositeDataTypeMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.InnerDeclarationMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.OperationInterfaceMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.OperationProvidedRoleMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.OperationRequiredRoleMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.OperationSignatureMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.PCMParameterMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.RepositoryMappingTransformaitonTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.ResourceDemandingInternalBehaviorMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository.SEFFMappingTransformationTest;

@RunWith(Suite.class)

@SuiteClasses({ BasicComponentMappingTransformationTest.class, CollectionDataTypeMappingTransformationTest.class,
		CompositeComponentMappingTransformationTest.class, CompositeDataTypeMappingTransformationTest.class,
		InnerDeclarationMappingTransformationTest.class, OperationInterfaceMappingTransformationTest.class,
		OperationProvidedRoleMappingTransformationTest.class, OperationRequiredRoleMappingTransformationTest.class,
		OperationSignatureMappingTransformationTest.class, PCMParameterMappingTransformationTest.class,
		RepositoryMappingTransformaitonTest.class, ResourceDemandingInternalBehaviorMappingTransformationTest.class,
		SEFFMappingTransformationTest.class })
public class PCM2JaMoPPTestSuite {

}
