package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.pcm2jamopp;

import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.Parameter;
import de.uka.ipd.sdq.pcm.repository.PrimitiveDataType;
import de.uka.ipd.sdq.pcm.repository.PrimitiveTypeEnum;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPUtils;

public class ParameterMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddParameter() throws Throwable {
        final Parameter param = this.createAndSyncRepoOpSigAndParameter();

        this.assertParameterCorrespondences(param);
    }

    @Test
    public void testChangeParameterName() throws Throwable {
        final Parameter param = this.createAndSyncRepoOpSigAndParameter();

        param.setParameterName(PCM2JaMoPPUtils.PARAMETER_NAME + PCM2JaMoPPUtils.RENAME);
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
    	final Parameter param = super.createAndSyncRepoOpSigAndParameterWithDataTypeName(PCM2JaMoPPUtils.COMPOSITE_DATA_TYPE_NAME);
    	
        this.assertParameterCorrespondences(param);
    }
    
    /**
     * assert that the parameter type has a correspondence to the type of the parameter in the
     * JaMoPP model
     */
    private void assertParameterCorrespondences(final Parameter param) throws Throwable {
        this.assertDataTypeCorrespondence(param.getDataType__Parameter());
    }

}
