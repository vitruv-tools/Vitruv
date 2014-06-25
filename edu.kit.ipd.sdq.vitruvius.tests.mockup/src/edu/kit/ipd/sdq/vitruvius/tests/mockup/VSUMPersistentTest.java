package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class VSUMPersistentTest extends VSUMTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSaveAndLoadModels() throws Exception {
        // 1. create empty VSUM + store dummy model instances
        VSUMImpl vsum = testMetaRepositoryAndVSUMCreation();
        int nrOfVURIs = 2;
        Set<VURI> vuris = PersistentTestUtil.createDummyVURIs(nrOfVURIs);
        // PersistentTestUtil.createResources(vuris);
        for (VURI vuri : vuris) {
            vsum.saveModelInstanceOriginal(vuri);
        }

        // 2.create VSUM again (should read all model instances from disk)
        VSUMImpl newVSUM = testMetaRepositoryAndVSUMCreation();

        for (VURI vuri : vuris) {
            assertTrue("new VSUM does not contain model instances with URI " + vuri, newVSUM.existsModelInstance(vuri));
        }
        // 3. compare the modelInstances map from the two VSUMs - expectation: they are equal
        Map<VURI, ModelInstance> modelInstancesVSUM = getModelInstancesFieldFromVSUM(vsum);
        Map<VURI, ModelInstance> modelInstancesNewVSUM = getModelInstancesFieldFromVSUM(newVSUM);
        PersistentTestUtil.assertEqualsSets(modelInstancesVSUM.keySet(), modelInstancesNewVSUM.keySet());
    }

    @SuppressWarnings("unchecked")
    protected Map<VURI, ModelInstance> getModelInstancesFieldFromVSUM(final VSUMImpl vsum) throws NoSuchFieldException,
            IllegalAccessException {
        Field modelInstancesField = VSUMImpl.class.getDeclaredField("modelInstances");
        modelInstancesField.setAccessible(true);
        return (Map<VURI, ModelInstance>) modelInstancesField.get(vsum);
    }
}
