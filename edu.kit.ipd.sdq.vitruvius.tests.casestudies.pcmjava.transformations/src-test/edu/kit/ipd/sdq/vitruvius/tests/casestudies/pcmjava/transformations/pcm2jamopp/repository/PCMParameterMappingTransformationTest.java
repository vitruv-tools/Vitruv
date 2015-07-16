package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.repository;

import org.junit.Test;

import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp.PCM2JaMoPPTransformationTest;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;

public class PCMParameterMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddParameter() throws Throwable {
        final Parameter param = this.createAndSyncRepoOpSigAndParameter();

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testChangeParameterName() throws Throwable {
        final Parameter param = this.createAndSyncRepoOpSigAndParameter();

        param.setParameterName(PCM2JaMoPPTestUtils.PARAMETER_NAME + PCM2JaMoPPTestUtils.RENAME);
        super.triggerSynchronization(VURI.getInstance(param.eResource()));

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testChangeParameterType() throws Throwable {
        final Parameter param = this.createAndSyncRepoOpSigAndParameter();

        final PrimitiveDataType pdt = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        pdt.setType(PrimitiveTypeEnum.STRING);
        param.setDataType__Parameter(pdt);
        super.triggerSynchronization(VURI.getInstance(param.eResource()));

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testAddParameterWithCompositeDataType() throws Throwable {
        final Parameter param = super.createAndSyncRepoOpSigAndParameterWithDataTypeName(
                PCM2JaMoPPTestUtils.COMPOSITE_DATA_TYPE_NAME, PCM2JaMoPPTestUtils.PARAMETER_NAME);

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testAddMultipleParameters() throws Throwable {
        final Parameter param = super.createAndSyncRepoOpSigAndParameterWithDataTypeName(
                PCM2JaMoPPTestUtils.COMPOSITE_DATA_TYPE_NAME + "_1", "compositeParam1");
        final OperationSignature opSig = param.getOperationSignature__Parameter();
        final CompositeDataType cdt = super.createAndSyncCompositeDataType(opSig.getInterface__OperationSignature()
                .getRepository__Interface(), PCM2JaMoPPTestUtils.COMPOSITE_DATA_TYPE_NAME + "_2");

        final Parameter param2 = super.addAndSyncParameterWithPrimitiveTypeToSignature(opSig);
        final Parameter param3 = super.addAndSyncParameterToSignature(opSig, cdt, "compositeParam2");

        this.assertParameterCorrespondences(param);
        this.assertParameterCorrespondences(param2);
        this.assertParameterCorrespondences(param3);
    }

    /**
     * assert that the parameter type has a correspondence to the type of the parameter in the
     * JaMoPP model
     */
    private void assertParameterCorrespondences(final Parameter param) throws Throwable {
        this.assertDataTypeCorrespondence(param.getDataType__Parameter());
    }

}
