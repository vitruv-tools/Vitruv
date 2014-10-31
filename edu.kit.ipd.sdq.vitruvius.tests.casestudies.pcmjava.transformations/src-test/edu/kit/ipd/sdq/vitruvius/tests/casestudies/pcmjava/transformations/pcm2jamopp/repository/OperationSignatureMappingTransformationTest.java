package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository;

import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.OperationSignature;
import de.uka.ipd.sdq.pcm.repository.PrimitiveDataType;
import de.uka.ipd.sdq.pcm.repository.PrimitiveTypeEnum;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java.repository.DataTypeCorrespondenceHelper;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;

public class OperationSignatureMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddOperationSignature() throws Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

        this.assertOperationSignatureCorrespondence(opSig);
    }

    @Test
    public void testRenameOperationSignature() throws Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

        opSig.setEntityName(PCM2JaMoPPUtils.OPERATION_SIGNATURE_1_NAME + PCM2JaMoPPUtils.RENAME);
        super.triggerSynchronization(VURI.getInstance(opSig.eResource()));

        this.assertOperationSignatureCorrespondence(opSig);
    }

    @Test
    public void testChangeOperationSignatureReturnType() throws Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

        final PrimitiveDataType pdt = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        pdt.setType(PrimitiveTypeEnum.STRING);
        opSig.setReturnType__OperationSignature(pdt);
        super.triggerSynchronization(VURI.getInstance(opSig.eResource()));

        this.assertOperationSignatureCorrespondence(opSig);
    }

    private InterfaceMethod assertOperationSignatureCorrespondence(final OperationSignature opSig) throws Throwable {
        final InterfaceMethod intMethod = (InterfaceMethod) this.assertSingleCorrespondence(opSig,
                InterfaceMethod.class, opSig.getEntityName());
        final TypeReference tr = DataTypeCorrespondenceHelper.claimUniqueCorrespondingJaMoPPDataType(
                opSig.getReturnType__OperationSignature(), this.getCorrespondenceInstance());
        this.assertEqualsTypeReference(intMethod.getTypeReference(), tr);

        return intMethod;
    }

}
