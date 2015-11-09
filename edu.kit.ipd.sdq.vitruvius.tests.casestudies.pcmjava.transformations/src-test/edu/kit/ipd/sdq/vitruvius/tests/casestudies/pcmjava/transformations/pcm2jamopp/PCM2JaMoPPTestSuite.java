package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.BasicComponentMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.CollectionDataTypeMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.CompositeDataTypeMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.InnerDeclarationMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.OperationInterfaceMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.OperationProvidedRoleMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.OperationRequiredRoleMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.OperationSignatureMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.PCMParameterMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.RepositoryMappingTransformaitonTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.ResourceDemandingInternalBehaviorMappingTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository.SEFFMappingTransformationTest;

@RunWith(Suite.class)

@SuiteClasses({ BasicComponentMappingTransformationTest.class, OperationInterfaceMappingTransformationTest.class,
        CollectionDataTypeMappingTransformationTest.class, CompositeDataTypeMappingTransformationTest.class,
        InnerDeclarationMappingTransformationTest.class, OperationInterfaceMappingTransformationTest.class,
        OperationProvidedRoleMappingTransformationTest.class, OperationRequiredRoleMappingTransformationTest.class,
        OperationSignatureMappingTransformationTest.class, PCMParameterMappingTransformationTest.class,
        RepositoryMappingTransformaitonTest.class, ResourceDemandingInternalBehaviorMappingTransformationTest.class,
        SEFFMappingTransformationTest.class })
public class PCM2JaMoPPTestSuite {

}
