package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.repository;

import java.util.concurrent.Callable;

import org.junit.Test;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2java.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.PCM2JaMoPPTestUtils;

public class CompositeDataTypeMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddCompositeDataType() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);

        final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);

        this.assertDataTypeCorrespondence(cdt);
    }

    @Test
    public void testRenameCompositeDataType() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);

        cdt.setEntityName(PCM2JaMoPPTestUtils.COMPOSITE_DATA_TYPE_NAME + PCM2JaMoPPTestUtils.RENAME);
        super.triggerSynchronization(VURI.getInstance(cdt.eResource()));

        this.assertDataTypeCorrespondence(cdt);
    }

    @Test
    public void testAddCompositeDataTypeWithInnerTypes() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final CompositeDataType cdt = this.createCompositeDataType(repo, PCM2JaMoPPTestUtils.COMPOSITE_DATA_TYPE_NAME);
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        final InnerDeclaration innerDec = this.addInnerDeclaration(cdt, repo);
        super.triggerSynchronization(VURI.getInstance(repo.eResource()));

        EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                try {
                    CompositeDataTypeMappingTransformationTest.this.assertDataTypeCorrespondence(cdt);
                    CompositeDataTypeMappingTransformationTest.this.assertInnerDeclaration(innerDec);
                } catch (final Throwable e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        }, this.vsum);
    }

    @Test
    public void testAddPrimitiveDataTypeToCompositeDataType() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        this.assertInnerDeclaration(innerDec);
    }

    @Test
    public void testAddCompositeDataTypeToCompositeDataType() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);
        final CompositeDataType cdt2 = this.createAndSyncCompositeDataType(repo, "InnerCompositeDataTypeTest");

        final InnerDeclaration innerDec = RepositoryFactory.eINSTANCE.createInnerDeclaration();
        innerDec.setDatatype_InnerDeclaration(cdt2);
        innerDec.setCompositeDataType_InnerDeclaration(cdt);
        cdt.getInnerDeclaration_CompositeDataType().add(innerDec);
        super.triggerSynchronization(VURI.getInstance(cdt.eResource()));

        this.assertDataTypeCorrespondence(cdt);
    }

}
