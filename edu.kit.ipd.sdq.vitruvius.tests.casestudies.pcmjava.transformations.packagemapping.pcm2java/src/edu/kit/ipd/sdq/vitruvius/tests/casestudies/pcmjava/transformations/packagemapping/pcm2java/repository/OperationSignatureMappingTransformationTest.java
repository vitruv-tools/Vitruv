package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java.repository;

import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.DataTypeCorrespondenceHelper;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.pcm2java.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.PCM2JaMoPPTestUtils;

public class OperationSignatureMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddOperationSignature() throws Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

        this.assertOperationSignatureCorrespondence(opSig);
    }

    @Test
    public void testRenameOperationSignature() throws Throwable {
        final OperationSignature opSig = this.createAndSyncRepoInterfaceAndOperationSignature();

        opSig.setEntityName(PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME + PCM2JaMoPPTestUtils.RENAME);
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

    @Test
    public void testCreateOperationSignatureWithReturnType() throws Throwable {
        // create
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);

        // prepare OperationSignature with return type at creation
        final OperationSignature opSig = RepositoryFactory.eINSTANCE.createOperationSignature();
        opSig.setEntityName(PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME);
        final PrimitiveDataType pdt = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        pdt.setType(PrimitiveTypeEnum.STRING);
        opSig.setReturnType__OperationSignature(pdt);
        opSig.setInterface__OperationSignature(opInterface);
        // trigger synchronization execution
        super.triggerSynchronization(opInterface);

        // assert the signature
        this.assertOperationSignatureCorrespondence(opSig);
    }

    private InterfaceMethod assertOperationSignatureCorrespondence(final OperationSignature opSig) throws Throwable {
        final InterfaceMethod intMethod = (InterfaceMethod) this.assertSingleCorrespondence(opSig,
                InterfaceMethod.class, opSig.getEntityName());
        final TypeReference tr = DataTypeCorrespondenceHelper.claimUniqueCorrespondingJaMoPPDataTypeReference(
                opSig.getReturnType__OperationSignature(), this.getCorrespondenceInstance());
        this.assertEqualsTypeReference(intMethod.getTypeReference(), tr);

        return intMethod;
    }

}
