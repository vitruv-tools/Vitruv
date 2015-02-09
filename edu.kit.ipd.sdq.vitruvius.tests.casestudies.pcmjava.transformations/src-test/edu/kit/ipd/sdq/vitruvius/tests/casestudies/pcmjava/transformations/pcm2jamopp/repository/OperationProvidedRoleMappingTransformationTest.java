package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationProvidedRole;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class OperationProvidedRoleMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddOperationProvidedRole() throws Throwable {
        final OperationProvidedRole operationProvidedRole = this
                .createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();

        this.assertOperationProvidedRole(operationProvidedRole);
    }

    @Test
    public void testChangeInterfaceOfOperationProvidedRole() throws Throwable {
        final OperationProvidedRole operationProvidedRole = this
                .createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();
        final Repository repo = operationProvidedRole.getProvidedInterface__OperationProvidedRole()
                .getRepository__Interface();
        final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME + PCM2JaMoPPTestUtils.RENAME);

        operationProvidedRole.setProvidedInterface__OperationProvidedRole(newInterface);
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        this.assertOperationProvidedRole(operationProvidedRole);
    }

    @Test
    public void testChangeComponentOfOperationProvidedRole() throws Throwable {
        final OperationProvidedRole operationProvidedRole = this
                .createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();
        final Repository repo = operationProvidedRole.getProvidedInterface__OperationProvidedRole()
                .getRepository__Interface();
        final BasicComponent newBasicComponent = this.addBasicComponentAndSync(repo, "NewProvidingComponent");

        operationProvidedRole.setProvidingEntity_ProvidedRole(newBasicComponent);
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        this.assertOperationProvidedRole(operationProvidedRole);
    }

    @Test
    public void testTwoProvidedRolesInOneComponent() throws Throwable {
        final OperationProvidedRole operationProvidedRole = this
                .createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();
        final Repository repo = operationProvidedRole.getProvidedInterface__OperationProvidedRole()
                .getRepository__Interface();
        final BasicComponent basicComponent = (BasicComponent) operationProvidedRole.getProvidingEntity_ProvidedRole();
        final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME + PCM2JaMoPPTestUtils.RENAME);

        final OperationProvidedRole newOperationProvidedRole = RepositoryFactory.eINSTANCE
                .createOperationProvidedRole();
        newOperationProvidedRole.setEntityName("NewOperationProvidedRole");
        newOperationProvidedRole.setProvidedInterface__OperationProvidedRole(newInterface);
        newOperationProvidedRole.setProvidingEntity_ProvidedRole(basicComponent);
        basicComponent.getProvidedRoles_InterfaceProvidingEntity().add(newOperationProvidedRole);
        final VURI vuri = VURI.getInstance(repo.eResource());
        super.triggerSynchronization(vuri);

        this.assertOperationProvidedRole(operationProvidedRole);
        this.assertOperationProvidedRole(newOperationProvidedRole);
    }

    @Test
    public void testTwoProvidedRolesInOneComponentAndRemoveOne() throws Throwable {
        final OperationProvidedRole operationProvidedRole = this
                .createAndSyncRepoOpIntfOpSigBasicCompAndOperationProvRole();
        final Repository repo = operationProvidedRole.getProvidedInterface__OperationProvidedRole()
                .getRepository__Interface();
        final BasicComponent basicComponent = (BasicComponent) operationProvidedRole.getProvidingEntity_ProvidedRole();
        final OperationInterface newInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME + PCM2JaMoPPTestUtils.RENAME);
        final OperationProvidedRole newOperationProvidedRole = RepositoryFactory.eINSTANCE
                .createOperationProvidedRole();
        newOperationProvidedRole.setEntityName("NewOperationProvidedRole");
        newOperationProvidedRole.setProvidedInterface__OperationProvidedRole(newInterface);
        newOperationProvidedRole.setProvidingEntity_ProvidedRole(basicComponent);
        basicComponent.getProvidedRoles_InterfaceProvidingEntity().add(newOperationProvidedRole);
        final VURI vuri = VURI.getInstance(repo.eResource());
        super.triggerSynchronization(vuri);

        basicComponent.getProvidedRoles_InterfaceProvidingEntity().remove(newOperationProvidedRole);
        super.triggerSynchronization(vuri);

        this.assertOperationProvidedRole(operationProvidedRole);
        final CompilationUnit jaMoPPCu = this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(
                basicComponent, CompilationUnit.class);
        assertEquals("Unexpected size of imports", 1, jaMoPPCu.getImports().size());
        final Class jaMoPPClass = (Class) jaMoPPCu.getClassifiers().get(0);
        assertEquals("Unexpected size of implemented interfaces", 1, jaMoPPClass.getImplements().size());
    }

    @Test
    public void testOperationProvidedRoleToSystem() throws Throwable {
        final Repository repo = super.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = super.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);
        final de.uka.ipd.sdq.pcm.system.System system = super.createAndSyncSystem(PCM2JaMoPPTestUtils.SYSTEM_NAME);

        final OperationProvidedRole operationProvidedRole = super.createAndSyncOperationProvidedRole(opInterface,
                system);

        this.assertOperationProvidedRole(operationProvidedRole);
    }

    /**
     * a operation provided is represented by the main class implementing the interface and an
     * import
     *
     * @param operationProvidedRole
     * @throws Throwable
     */
    private void assertOperationProvidedRole(final OperationProvidedRole operationProvidedRole) throws Throwable {
        final Set<EObject> correspondingEObjects = this.getCorrespondenceInstance().getAllCorrespondingEObjects(
                operationProvidedRole);
        int namespaceClassifierReferenceFound = 0;
        int importFound = 0;
        for (final EObject eObject : correspondingEObjects) {
            if (eObject instanceof NamespaceClassifierReference) {
                namespaceClassifierReferenceFound++;
            } else if (eObject instanceof ClassifierImport) {
                importFound++;
            } else {
                fail("operation provided role corresponds to unexpected object: " + eObject);
            }
        }
        assertEquals("unexpected size of corresponding imports", 1, importFound);
        assertEquals("unexpected size of corresponding namespace classifier references", 1,
                namespaceClassifierReferenceFound);
    }

}
