package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository;

import org.junit.Test;

import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class CollectionDataTypeMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddCollectionDataTypeWithoutInnerType() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);

        final CollectionDataType collectionDataType = this.addCollectionDatatypeAndSync(repo,
                PCM2JaMoPPTestUtils.COLLECTION_DATA_TYPE_NAME, null);

        this.assertDataTypeCorrespondence(collectionDataType);
    }

    @Test
    public void testAddCollectionDataTypeWithPrimitiveTypeStringAsInnerType() throws Throwable {
        final PrimitiveTypeEnum pte = PrimitiveTypeEnum.STRING;
        this.testAddCollectionDataTypeWithPrimitiveInnerType(pte);
    }

    @Test
    public void testAddCollectionDataTypeWithPrimitiveTypeIntAsInnerType() throws Throwable {
        this.testAddCollectionDataTypeWithPrimitiveInnerType(PrimitiveTypeEnum.INT);
    }

    @Test
    public void testAddCollectionDataTypeWithComplexInnerType() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final CompositeDataType compositeDataType = this.createAndSyncCompositeDataType(repo);

        final CollectionDataType collectionDataType = this.addCollectionDatatypeAndSync(repo,
                PCM2JaMoPPTestUtils.COLLECTION_DATA_TYPE_NAME, compositeDataType);

        this.assertDataTypeCorrespondence(collectionDataType);
    }

    protected void testAddCollectionDataTypeWithPrimitiveInnerType(final PrimitiveTypeEnum pte) throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final PrimitiveDataType primitiveType = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        primitiveType.setType(pte);

        final CollectionDataType collectionDataType = this.addCollectionDatatypeAndSync(repo,
                PCM2JaMoPPTestUtils.COLLECTION_DATA_TYPE_NAME, primitiveType);

        this.assertDataTypeCorrespondence(collectionDataType);
    }

    private CollectionDataType addCollectionDatatypeAndSync(final Repository repo, final String name,
            final DataType innerType) {
        final CollectionDataType cdt = RepositoryFactory.eINSTANCE.createCollectionDataType();
        cdt.setEntityName(name);
        cdt.setRepository__DataType(repo);
        if (null != innerType) {
            cdt.setInnerType_CollectionDataType(innerType);
        }
        super.triggerSynchronization(repo);
        return cdt;
    }

}
