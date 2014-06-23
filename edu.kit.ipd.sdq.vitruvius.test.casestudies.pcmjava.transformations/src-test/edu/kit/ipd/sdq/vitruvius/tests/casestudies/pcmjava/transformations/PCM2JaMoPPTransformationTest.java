package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;

public class PCM2JaMoPPTransformationTest extends JaMoPPPCMTransformationTest {

    private static Logger logger = Logger.getLogger(PCM2JaMoPPTransformationTest.class.getSimpleName());

    @BeforeClass
    public static void setUp() {
        JaMoPPPCMTransformationTest.setUp();
    }

    @Override
    @Before
    public void createNewCorrespondenceInstance() {
        super.createNewCorrespondenceInstance();
    }

    /**
     * Test change synchronizing
     */
    @Test
    public void testAddRepository() {
        PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        this.moveCreatedFilesToPath("addRepository");
    }

    @Test
    public void testRepositoryNameChange() {
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        repo.setEntityName("TestNameChange");

        final UpdateEAttribute<Object> repoNameChange = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        repoNameChange.setAffectedEObject(repo);
        final EAttribute affectedFeature = (EAttribute) repo.eClass().getEStructuralFeature("entityName");
        repoNameChange.setAffectedFeature(affectedFeature);
        repoNameChange.setNewValue(repo.getEntityName());
        System.out.println(repoNameChange.getNewValue());
        this.changeSynchronizer.synchronizeChange(repoNameChange);
        this.moveCreatedFilesToPath("testRepositoryNameChange");
    }

    @Test
    public void testAddInterface() {
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        this.addInterfaceToReposiotry(repo);
        this.moveCreatedFilesToPath("testAddInterface");
    }

    @Test
    public void testRenameInterface() {
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        final OperationInterface opInterface = this.addInterfaceToReposiotry(repo);
        this.renameInterfaceAndSync(opInterface);
        this.moveCreatedFilesToPath("testRenameInterface");
    }

    @Test
    public void testAddBasicComponent() {
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        this.addBasicComponent(repo);
    }

    private void renameInterfaceAndSync(final OperationInterface opInterface) {

        final String newValue = "TestRenameInterface";
        final UpdateEAttribute<Object> renameInterface = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        renameInterface.setAffectedEObject(opInterface);
        renameInterface.setAffectedFeature((EAttribute) opInterface.eClass().getEStructuralFeature("entityName"));
        renameInterface.setNewValue(newValue);
        final EObject[] eObject = PCM2JaMoPPTransformationTest.changeSynchronizer.synchronizeChange(renameInterface);
        opInterface.setEntityName(newValue);
        this.saveEObjectIfCompilationUnit(eObject);
    }

    private BasicComponent addBasicComponent(final Repository repo) {
        final BasicComponent basicComponent = PCM2JaMoPPUtils.createBasicComponent(repo);
        final CreateNonRootEObject<EObject> createBasicComponentChange = ChangeFactory.eINSTANCE
                .createCreateNonRootEObject();
        createBasicComponentChange.setChangedEObject(basicComponent);
        createBasicComponentChange.setAffectedEObject(repo);
        createBasicComponentChange.setAffectedFeature((EReference) repo.eClass().getEStructuralFeature(
                "components__Repository"));
        createBasicComponentChange.setNewValue(basicComponent);
        final EObject eObject[] = PCM2JaMoPPTransformationTest.changeSynchronizer
                .synchronizeChange(createBasicComponentChange);
        this.saveEObjectIfCompilationUnit(eObject);
        return basicComponent;
    }

    private OperationInterface addInterfaceToReposiotry(final Repository repo) {
        final OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setRepository__Interface(repo);
        final CreateNonRootEObject<EObject> createInterfaceChange = ChangeFactory.eINSTANCE
                .createCreateNonRootEObject();
        createInterfaceChange.setChangedEObject(opInterface);
        createInterfaceChange.setAffectedEObject(repo);
        createInterfaceChange.setAffectedFeature((EReference) repo.eClass().getEStructuralFeature(
                "interfaces__Repository"));
        createInterfaceChange.setNewValue(opInterface);
        final EObject eObject[] = PCM2JaMoPPTransformationTest.changeSynchronizer
                .synchronizeChange(createInterfaceChange);
        this.saveEObjectIfCompilationUnit(eObject);
        return opInterface;
    }

    private void saveEObjectIfCompilationUnit(final EObject[] eObjects) {
        for (final EObject eObj : eObjects) {
            this.saveEObjectIfCompilationUnit(eObj);
        }
    }

    private void saveEObjectIfCompilationUnit(final EObject eObject) {
        if (!(eObject instanceof CompilationUnit)) {
            return;
        }
        final CompilationUnit compilationUnit = (CompilationUnit) eObject;
        String compilationUnitName = compilationUnit.getNamespacesAsString();
        compilationUnitName = compilationUnitName.replace(".", "/").replace("$", "/") + compilationUnit.getName();
        final VURI resourceURI = VURI.getInstance("MockupProject/src/" + compilationUnitName);
        final Resource resource = PCM2JaMoPPTransformationTest.resourceSet.createResource(resourceURI.getEMFUri());
        resource.getContents().add(compilationUnit);
        try {
            resource.save(null);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Moves the created model and src folder files to a specific folder/path.
     * 
     * @param destinationPathAsString
     *            destinationPath in test workspace
     * @throws URISyntaxException
     */
    private void moveCreatedFilesToPath(final String destinationPathAsString) {
        // IResource iResource = Wor
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        final IProject project = root.getProject("MockupProject");
        final IResource member = project.findMember("src");
        if (null == member) {
            logger.warn("member not found moveCreatedFilesToPath will not work");
            return;
        }
        final IPath destinationPath = new Path(destinationPathAsString);
        try {
            member.move(destinationPath, true, new NullProgressMonitor());
        } catch (final CoreException e) {
            logger.warn("Could not move src folder do destination folder " + destinationPathAsString + ": "
                    + e.getMessage());
        }
    }
}
