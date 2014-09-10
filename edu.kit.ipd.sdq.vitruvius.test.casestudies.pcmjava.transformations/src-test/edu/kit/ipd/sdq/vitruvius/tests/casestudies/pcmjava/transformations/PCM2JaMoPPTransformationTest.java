package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
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
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

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
        TestUtil.moveSrcFilesFromMockupProjectToPath("addRepository");
    }

    @Test
    public void testRepositoryNameChange() {
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        repo.setEntityName("TestNameChange");

        final UpdateSingleValuedEAttribute<Object> repoNameChange = AttributeFactory.eINSTANCE
                .createUpdateSingleValuedEAttribute();
        repoNameChange.setOldAffectedEObject(repo);
        final EAttribute affectedFeature = (EAttribute) repo.eClass().getEStructuralFeature("entityName");
        repoNameChange.setAffectedFeature(affectedFeature);
        repoNameChange.setNewValue(repo.getEntityName());
        System.out.println(repoNameChange.getNewValue());
        changeSynchronizer.synchronizeChange(repoNameChange);
        TestUtil.moveSrcFilesFromMockupProjectToPath("testRepositoryNameChange");
    }

    @Test
    public void testAddInterface() {
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        this.addInterfaceToReposiotry(repo);
        TestUtil.moveSrcFilesFromMockupProjectToPath("testAddInterface");
    }

    @Test
    public void testRenameInterface() {
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        final OperationInterface opInterface = this.addInterfaceToReposiotry(repo);
        this.renameInterfaceAndSync(opInterface);
        TestUtil.moveSrcFilesFromMockupProjectToPath("testRenameInterface");
    }

    @Test
    public void testAddBasicComponent() {
        final Repository repo = PCM2JaMoPPUtils.createAndSyncRepository(resourceSet, changeSynchronizer);
        this.addBasicComponent(repo);
    }

    private void renameInterfaceAndSync(final OperationInterface opInterface) {

        final String newValue = "TestRenameInterface";
        final UpdateSingleValuedEAttribute<Object> renameInterface = AttributeFactory.eINSTANCE
                .createUpdateSingleValuedEAttribute();
        renameInterface.setOldAffectedEObject(opInterface);
        renameInterface.setAffectedFeature((EAttribute) opInterface.eClass().getEStructuralFeature("entityName"));
        renameInterface.setNewValue(newValue);
        final TransformationChangeResult transformationChangeResult = PCM2JaMoPPTransformationTest.changeSynchronizer
                .synchronizeChange(renameInterface);
        opInterface.setEntityName(newValue);
        this.saveEObjectIfCompilationUnit(transformationChangeResult.getNewRootObjectsToSave());
    }

    private BasicComponent addBasicComponent(final Repository repo) {
        final BasicComponent basicComponent = PCM2JaMoPPUtils.createBasicComponent(repo);
        final CreateNonRootEObjectInList<EObject> createBasicComponentChange = ContainmentFactory.eINSTANCE
                .createCreateNonRootEObjectInList();
        createBasicComponentChange.setNewValue(basicComponent);
        createBasicComponentChange.setOldAffectedEObject(repo);
        createBasicComponentChange.setAffectedFeature((EReference) repo.eClass().getEStructuralFeature(
                "components__Repository"));
        createBasicComponentChange.setNewValue(basicComponent);
        final TransformationChangeResult transformationChangeResult = PCM2JaMoPPTransformationTest.changeSynchronizer
                .synchronizeChange(createBasicComponentChange);
        this.saveEObjectIfCompilationUnit(transformationChangeResult.getNewRootObjectsToSave());
        return basicComponent;
    }

    private OperationInterface addInterfaceToReposiotry(final Repository repo) {
        final OperationInterface opInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
        opInterface.setRepository__Interface(repo);
        final CreateNonRootEObjectInList<EObject> createInterfaceChange = ContainmentFactory.eINSTANCE
                .createCreateNonRootEObjectInList();
        createInterfaceChange.setNewValue(opInterface);
        createInterfaceChange.setOldAffectedEObject(repo);
        createInterfaceChange.setAffectedFeature((EReference) repo.eClass().getEStructuralFeature(
                "interfaces__Repository"));
        createInterfaceChange.setNewValue(opInterface);
        final TransformationChangeResult transformationChangeResult = PCM2JaMoPPTransformationTest.changeSynchronizer
                .synchronizeChange(createInterfaceChange);
        this.saveEObjectIfCompilationUnit(transformationChangeResult.getNewRootObjectsToSave());
        return opInterface;
    }

    private void saveEObjectIfCompilationUnit(final Set<EObject> eObjects) {
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
}
