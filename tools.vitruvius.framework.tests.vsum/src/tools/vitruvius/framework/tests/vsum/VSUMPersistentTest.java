package tools.vitruvius.framework.tests.vsum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.correspondence.InternalCorrespondenceModel;
import tools.vitruvius.framework.metamodel.Mapping;
import tools.vitruvius.framework.metamodel.Metamodel;
import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.util.bridges.JavaBridge;
import tools.vitruvius.framework.util.datatypes.VURI;
import tools.vitruvius.framework.vsum.VSUMImpl;
import pcm_mockup.Pcm_mockupFactory;

public class VSUMPersistentTest extends VSUMTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSaveAndLoadModels() throws Exception {
        // 1. create empty VSUM + store dummy model instances
        VSUMImpl vsum = createMetaRepositoryAndVSUM();
        int nrOfVURIs = 2;
        Set<VURI> vuris = PersistentTestUtil.createDummyVURIs(getCurrentProjectFolderName(), nrOfVURIs);
        // PersistentTestUtil.createResources(vuris);
        for (VURI vuri : vuris) {
            vsum.saveModelInstanceOriginalWithEObjectAsOnlyContent(vuri, Pcm_mockupFactory.eINSTANCE.createRepository(),
                    null);
        }

        // 2.create VSUM again (should read all model instances from disk)
        VSUMImpl newVSUM = createMetaRepositoryAndVSUM();

        for (VURI vuri : vuris) {
            assertTrue("new VSUM does not contain model instances with URI " + vuri, newVSUM.existsModelInstance(vuri));
        }
        // 3. compare the modelInstances map from the two VSUMs - expectation: they are equal
        Map<VURI, ModelInstance> modelInstancesVSUM = getModelInstancesFieldFromVSUM(vsum);
        Map<VURI, ModelInstance> modelInstancesNewVSUM = getModelInstancesFieldFromVSUM(newVSUM);
        PersistentTestUtil.assertEqualsSets(modelInstancesVSUM.keySet(), modelInstancesNewVSUM.keySet());
    }

    @Test
    public void testLoadVSUMAndFillCorrespondenceMaps() throws Throwable {
        // create and fill VSUM
        VSUMImpl vsum = createMetaRepositoryAndVSUM();
        fillVSUM(vsum);

        // create VSUM again
        VSUMImpl newVSUM = createMetaRepositoryAndVSUM();

        // assert Maps
        Map<Mapping, InternalCorrespondenceModel> oldMapping2CorrespondenceModelMap = JavaBridge
                .getFieldFromClass(VSUMImpl.class, "mapping2CorrespondenceModelMap", vsum);
        Map<Mapping, InternalCorrespondenceModel> newMapping2CorrespondenceModelMap = JavaBridge
                .getFieldFromClass(VSUMImpl.class, "mapping2CorrespondenceModelMap", newVSUM);

        assertEquals("Mapping maps must have the same size", oldMapping2CorrespondenceModelMap.size(),
                newMapping2CorrespondenceModelMap.size());
        for (Mapping oldMapping : oldMapping2CorrespondenceModelMap.keySet()) {
            boolean mappingFound = false;
            for (Mapping newMapping : newMapping2CorrespondenceModelMap.keySet()) {
                if (mappingEquals(oldMapping, newMapping)) {
                    mappingFound = true;
                    CorrespondenceModel oldCi = oldMapping2CorrespondenceModelMap.get(oldMapping);
                    CorrespondenceModel newCi = newMapping2CorrespondenceModelMap.get(newMapping);
                    assertTrue(
                            "Old correspondence instance " + oldCi + " does not equal new CorrespondenceModel " + newCi,
                            correspondenceModelEquals(oldCi, newCi));
                }
            }
            assertTrue("Mapping " + oldMapping2CorrespondenceModelMap + " not found in the new mapping map",
                    mappingFound);
        }
    }

    private boolean correspondenceModelEquals(final CorrespondenceModel oldCi, final CorrespondenceModel newCi) {
        VURI oldVURI = VURI.getInstance(oldCi.getResource().getURI());
        VURI newVURI = VURI.getInstance(newCi.getResource().getURI());
        return oldVURI == newVURI && mappingEquals(oldCi.getMapping(), newCi.getMapping());

    }

    private boolean metamodelEquals(final Metamodel mmA, final Metamodel mmB) {
        return Arrays.equals(mmA.getFileExtensions(), mmB.getFileExtensions()) && mmA.getURI().equals(mmB.getURI())
                && mmA.getNsURIs().equals(mmB.getNsURIs());
    }

    private boolean mappingEquals(final Mapping mappingA, final Mapping mappingB) {
        return metamodelEquals(mappingA.getMetamodelA(), mappingB.getMetamodelA())
                && metamodelEquals(mappingA.getMetamodelB(), mappingB.getMetamodelB());
    }

    @SuppressWarnings("unchecked")
    protected Map<VURI, ModelInstance> getModelInstancesFieldFromVSUM(final VSUMImpl vsum)
            throws NoSuchFieldException, IllegalAccessException {
        Field modelInstancesField = VSUMImpl.class.getDeclaredField("modelInstances");
        modelInstancesField.setAccessible(true);
        return (Map<VURI, ModelInstance>) modelInstancesField.get(vsum);
    }
}
