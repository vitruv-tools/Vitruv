package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.Package;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class BasicComponentMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);

        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        this.assertBasicComponentCorrespondences(basicComponent);
    }

    @Test
    public void testRenameBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        basicComponent.setEntityName(PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + PCM2JaMoPPTestUtils.RENAME);
        this.triggerSynchronization(VURI.getInstance(repo.eResource()));

        this.assertBasicComponentCorrespondences(basicComponent);
    }

    @Test
    public void testDeleteBasicComponent() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);

        EcoreUtil.delete(basicComponent);
        super.triggerSynchronization(repo);

        this.assertEmptyCorrespondence(basicComponent);
        this.assertCompilationUnitForBasicComponentDeleted(basicComponent);
    }

    @Test
    public void testAddTwoBasicComponentAndDeleteOne() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final BasicComponent basicComponent = this.addBasicComponentAndSync(repo);
        final BasicComponent basicComponent2 = this.addBasicComponentAndSync(repo, "SecondBasicComponent");

        EcoreUtil.delete(basicComponent);
        super.triggerSynchronization(repo);

        this.assertEmptyCorrespondence(basicComponent);
        this.assertBasicComponentCorrespondences(basicComponent2);
    }

    @SuppressWarnings("unchecked")
    private void assertBasicComponentCorrespondences(final BasicComponent basicComponent) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(basicComponent, 3,
                new java.lang.Class[] { CompilationUnit.class, Package.class, Class.class },
                new String[] { basicComponent.getEntityName() + "Impl", basicComponent.getEntityName(),
                        basicComponent.getEntityName() + "Impl" });

    }

}
