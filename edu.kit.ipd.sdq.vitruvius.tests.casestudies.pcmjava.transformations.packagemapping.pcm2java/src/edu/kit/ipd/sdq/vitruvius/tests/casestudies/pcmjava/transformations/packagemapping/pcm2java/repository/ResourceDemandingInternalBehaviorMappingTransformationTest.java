package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java.repository;

import static org.junit.Assert.assertEquals;

import org.emftext.language.java.members.ClassMethod;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import org.palladiosimulator.pcm.seff.SeffFactory;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceModelUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.PCM2JaMoPPTestUtils;

public class ResourceDemandingInternalBehaviorMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    private static final String RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR_CLASS_METHOD = "resourceDemandingInternalBehaviourClassMethod";

    @Test
    public void testCreateResourceDemandingInternalBehavior() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent bc1 = this.addBasicComponentAndSync(repo);

        this.testUserInteractor.addNextSelections(RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR_CLASS_METHOD);
        final ResourceDemandingInternalBehaviour resourceDemandingInternalBehaviour = this
                .createAndSyncResourceDemandingInternalBehavior(bc1,
                        RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR_CLASS_METHOD);

        this.assertResourceDemandingInternalBehaviourCorrespondenceToMethod(resourceDemandingInternalBehaviour,
                RESOURCE_DEMANDING_INTERNAL_BEHAVIOUR_CLASS_METHOD);
    }

    private void assertResourceDemandingInternalBehaviourCorrespondenceToMethod(
            final ResourceDemandingInternalBehaviour resourceDemandingInternalBehaviour,
            final String expectedMethodName) throws Throwable {
        final ClassMethod classMethod = CollectionBridge
                .claimOne(CorrespondenceModelUtil.getCorrespondingEObjectsByType(this.getCorrespondenceModel(),
                        resourceDemandingInternalBehaviour, ClassMethod.class));
        assertEquals("The class method should have the same name as the user selected", classMethod.getName(),
                expectedMethodName);
    }

    private ResourceDemandingInternalBehaviour createAndSyncResourceDemandingInternalBehavior(
            final BasicComponent basicComponent, final String resourceDemandingInternalBehaviourName) {
        final ResourceDemandingInternalBehaviour resourceDemandingInternalBehaviour = SeffFactory.eINSTANCE
                .createResourceDemandingInternalBehaviour();
        resourceDemandingInternalBehaviour.setEntityName(resourceDemandingInternalBehaviourName);
        basicComponent.getResourceDemandingInternalBehaviours__BasicComponent().add(resourceDemandingInternalBehaviour);
        this.triggerSynchronization(basicComponent);
        return resourceDemandingInternalBehaviour;
    }
}