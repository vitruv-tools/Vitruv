package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.types.Int;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.CompositeDataType;
import de.uka.ipd.sdq.pcm.repository.PrimitiveDataType;
import de.uka.ipd.sdq.pcm.repository.PrimitiveTypeEnum;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.DataTypeCorrespondenceHelper;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class DataTypeCorrespondenceHelperTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testCorrespondenceForCompositeDataType() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);

        // Create and sync CompositeDataType
        final CompositeDataType cdt = this.createAndSyncCompositeDataType(repo);

        final TypeReference type = DataTypeCorrespondenceHelper.claimUniqueCorrespondingJaMoPPDataTypeReference(cdt,
                super.getCorrespondenceInstance());
        final NamespaceClassifierReference ncr = (NamespaceClassifierReference) type;
        final Classifier classifier = (Classifier) ncr.getTarget();
        assertEquals("Name of composite data type does not equals name of classifier", cdt.getEntityName() + "Impl",
                classifier.getName());
    }

    @Test
    public void testCorrespondenceForPrimitiveDataType() throws Throwable {
        // final Repository repo = this.createAndSyncRepository(this.resourceSet,
        // PCM2JaMoPPUtils.REPOSITORY_NAME);
        final PrimitiveDataType pdtInt = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        pdtInt.setType(PrimitiveTypeEnum.INT);
        final TypeReference type = DataTypeCorrespondenceHelper.claimUniqueCorrespondingJaMoPPDataTypeReference(pdtInt,
                super.getCorrespondenceInstance());
        if (!(type instanceof Int)) {
            fail("found type is not instance of Int");
        }
    }

    @Test
    public void testCorrespondenceForCollectionDataType() throws Throwable {
        fail("not yet implmented since transformation for CollectionDataType is not implemented yet.");
    }

}
