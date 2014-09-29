package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.FileChange.FileChangeKind;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.PCMJaMoPPTransformationTestBase;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;

public class PCM2JaMoPPTransformationTest extends PCMJaMoPPTransformationTestBase {

    private static Logger logger = Logger.getLogger(PCM2JaMoPPTransformationTest.class.getSimpleName());

    /**
     * Test change synchronizing
     *
     * @throws Throwable
     */
    @Test
    public void testAddRepository() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);

        final Package jaMoPPPackage = this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(repo,
                Package.class);

        assertEquals("name of JaMoPP Package does not match changed repository name", jaMoPPPackage.getName(),
                repo.getEntityName());
    }

    @Test
    public void testRepositoryNameChange() throws Throwable {
        // setup
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);

        // Test
        repo.setEntityName(PCM2JaMoPPUtils.REPOSITORY_NAME + PCM2JaMoPPUtils.RENAME);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        // check
        if (null == this.getCorrespondenceInstance()) {
            fail("correspondence instance is still null - no transformation was executed.");
            return;
        }
        final Package jaMoPPPackage = this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(repo,
                Package.class);
        assertEquals("name of JaMoPP Package does not match changed repository name", jaMoPPPackage.getName(),
                repo.getEntityName());
    }

    @Test
    public void testAddInterface() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);

        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPUtils.INTERFACE_NAME);

        this.assertOperationInterfaceCorrespondences(opInterface);
    }

    @Test
    public void testRenameInterface() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo, PCM2JaMoPPUtils.INTERFACE_NAME);

        opInterface = this.renameInterfaceAndSync(opInterface);

        this.assertOperationInterfaceCorrespondences(opInterface);
    }

    @Test
    public void testAddBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);

        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        this.assertBasicComponentCorrespondences(basicComponent);
    }

    @Test
    public void testRenameBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        basicComponent.setEntityName(PCM2JaMoPPUtils.BASIC_COMPONENT_NAME + PCM2JaMoPPUtils.RENAME);

        this.assertBasicComponentCorrespondences(basicComponent);
    }

    @SuppressWarnings("unchecked")
    private void assertBasicComponentCorrespondences(final BasicComponent basicComponent) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(
                basicComponent,
                3,
                new java.lang.Class[] { CompilationUnit.class, Package.class, Class.class },
                new String[] { basicComponent.getEntityName() + "Impl", basicComponent.getEntityName(),
                        basicComponent.getEntityName() + "Impl" });

    }

    private <T> void assertCorrespondnecesAndCompareNames(
            final de.uka.ipd.sdq.pcm.core.entity.NamedElement pcmNamedElement, final int expectedSize,
            final java.lang.Class<? extends EObject>[] expectedClasses, final String[] expectedNames) throws Throwable {
        final Set<EObject> correspondences = this.getCorrespondenceInstance().claimCorrespondingEObjects(
                pcmNamedElement);
        assertTrue("correspondences.size should be " + expectedSize, correspondences.size() == expectedSize);
        for (int i = 0; i < expectedClasses.length; i++) {
            final java.lang.Class<? extends EObject> expectedClass = expectedClasses[i];
            final EObject correspondingEObject = this.getCorrespondenceInstance()
                    .claimUniqueCorrespondingEObjectByType(pcmNamedElement, expectedClass);
            if (false == correspondingEObject.getClass().isInstance(expectedClass)) {
                fail("Corresponding EObject " + correspondingEObject + " is not an instance of " + expectedClass);
            }
            final NamedElement jaMoPPElement = (NamedElement) correspondingEObject;
            assertEquals(jaMoPPElement.getClass().getSimpleName() + " " + jaMoPPElement.getName() + " does not match "
                    + pcmNamedElement.getClass().getSimpleName() + " name " + pcmNamedElement.getEntityName(),
                    pcmNamedElement.getEntityName(), jaMoPPElement.getName());
        }
    }

    @SuppressWarnings("unchecked")
    private void assertOperationInterfaceCorrespondences(final OperationInterface opInterface) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(opInterface, 2, new java.lang.Class[] { CompilationUnit.class,
                Interface.class }, new String[] { opInterface.getEntityName(), opInterface.getEntityName() });
    }

    private OperationInterface renameInterfaceAndSync(final OperationInterface opInterface) throws Throwable {
        final String newValue = opInterface.getEntityName() + PCM2JaMoPPUtils.RENAME;
        opInterface.setEntityName(newValue);
        EcoreResourceBridge.saveResource(opInterface.eResource());
        super.triggerSynchronization(VURI.getInstance(opInterface.eResource()));
        return opInterface;
    }

    private BasicComponent addBasicComponentAndSync(final Repository repo) throws Throwable {
        final BasicComponent basicComponent = PCM2JaMoPPUtils.createBasicComponent(repo);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return basicComponent;
    }

    private OperationInterface addInterfaceToReposiotryAndSync(final Repository repo, final String interfaceName)
            throws Throwable {
        final OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setRepository__Interface(repo);
        opInterface.setEntityName(interfaceName);
        EcoreResourceBridge.saveResource(repo.eResource());
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));
        return opInterface;
    }

    private Repository createAndSyncRepository(final ResourceSet resourceSet, final String repositoryName)
            throws IOException {
        final Repository repo = PCM2JaMoPPUtils.createRepository(resourceSet, repositoryName);
        this.changeRecorder.beginRecording(Collections.singletonList(repo));
        super.synchronizeFileChange(FileChangeKind.CREATE, VURI.getInstance(repo.eResource()));
        return repo;
    }
}
