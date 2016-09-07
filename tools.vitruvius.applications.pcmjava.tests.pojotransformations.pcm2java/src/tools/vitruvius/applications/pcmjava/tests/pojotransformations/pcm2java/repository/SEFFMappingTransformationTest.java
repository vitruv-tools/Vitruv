package tools.vitruvius.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;

import tools.vitruvius.applications.pcmjava.tests.pojotransformations.pcm2java.PCM2JaMoPPTransformationTest;
import tools.vitruvius.applications.pcmjava.tests.util.PCM2JaMoPPTestUtils;
import tools.vitruvius.framework.correspondence.CorrespondenceModelUtil;

public class SEFFMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testCreateSEFF() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent bc1 = this.addBasicComponentAndSync(repo);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);
        final OperationSignature opSignature = this.createAndSyncOperationSignature(repo, opInterface);
        this.createAndSyncOperationProvidedRole(opInterface, bc1);

        final ResourceDemandingSEFF rdSEFF = this.createAndSyncSeff(bc1, opSignature);

        this.assertSEFFCorrespondenceToMethod(rdSEFF, opSignature.getEntityName());
    }

    @Test
    public void testRenameSEFF() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent bc1 = this.addBasicComponentAndSync(repo);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);
        final OperationSignature opSignature = this.createAndSyncOperationSignature(repo, opInterface);
        this.createAndSyncOperationProvidedRole(opInterface, bc1);
        final ResourceDemandingSEFF rdSEFF = this.createAndSyncSeff(bc1, opSignature);

        opSignature.setEntityName(PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME + PCM2JaMoPPTestUtils.RENAME);
        super.triggerSynchronization(opInterface);

        this.assertSEFFCorrespondenceToMethod(rdSEFF, opSignature.getEntityName());
    }

    private void assertSEFFCorrespondenceToMethod(final ResourceDemandingSEFF rdSEFF, final String expectedName)
            throws Throwable {
        final Set<EObject> correspondingEObjects = CorrespondenceModelUtil
                .getCorrespondingEObjects(this.getCorrespondenceModel(), rdSEFF);
        assertEquals("Expected exactly one corresponding EObject for rdSEFF " + rdSEFF, 1,
                correspondingEObjects.size());
        final EObject correspondingEObject = correspondingEObjects.iterator().next();
        assertTrue("The corresponding EObject for a SEFF has to be a ClassMethod",
                correspondingEObject instanceof ClassMethod);
        final ClassMethod correspondingClassMethod = (ClassMethod) correspondingEObject;
        assertEquals("The corresponding class method has the wrong name", expectedName,
                correspondingClassMethod.getName());
    }
}
