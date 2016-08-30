package edu.kit.ipd.sdq.vitruvius.tests.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
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
            vsum.saveExistingModelInstanceOriginal(vuri);
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

    @Test
    public void testLoadVSUMAndFillCorrespondenceMaps() throws Throwable {
        // create and fill VSUM
        VSUMImpl vsum = testMetaRepositoryAndVSUMCreation();
        fillVSUM(vsum);

        // create VSUM again
        VSUMImpl newVSUM = testMetaRepositoryAndVSUMCreation();

        // assert Maps
        Map<Metamodel, Set<CorrespondenceModel>> oldMetamodel2CorrespondenceModelsMap = JavaBridge
                .getFieldFromClass(VSUMImpl.class, "metamodel2CorrespondenceModelsMap", vsum);
        Map<Metamodel, Set<CorrespondenceModel>> newMetamodel2CorrespondenceModelsMap = JavaBridge
                .getFieldFromClass(VSUMImpl.class, "metamodel2CorrespondenceModelsMap", newVSUM);
        assertEquals("Metamodel maps must have the same size", oldMetamodel2CorrespondenceModelsMap.size(),
                newMetamodel2CorrespondenceModelsMap.size());
        for (Metamodel oldMetamodel : oldMetamodel2CorrespondenceModelsMap.keySet()) {
            boolean foundMetamodel = false;
            for (Metamodel newMetamodel : newMetamodel2CorrespondenceModelsMap.keySet()) {
                if (metamodelEquals(oldMetamodel, newMetamodel)) {
                    // found metamodel
                    foundMetamodel = true;
                    // Check Set of CorrespondenceModels
                    Set<CorrespondenceModel> oldCIs = oldMetamodel2CorrespondenceModelsMap.get(oldMetamodel);
                    Set<CorrespondenceModel> newCIs = newMetamodel2CorrespondenceModelsMap.get(newMetamodel);
                    assertEquals("Correspondence sets for metamodel " + oldMetamodel + " have to have the same size",
                            oldCIs.size(), newCIs.size());
                    for (CorrespondenceModel oldCi : oldCIs) {
                        boolean foundCorrespondenceModel = false;
                        for (CorrespondenceModel newCi : newCIs) {
                            if (correspondenceModelEquals(oldCi, newCi)) {
                                foundCorrespondenceModel = true;
                                break;
                            }
                        }
                        assertTrue("CorrespondenceModel " + oldCi + " not found in the new CorrespondenceModel set",
                                foundCorrespondenceModel);
                    }
                    break;
                }
            }
            assertTrue("Metamodel " + oldMetamodel + " not found in the new metamodel map", foundMetamodel);
        }

        Map<Mapping, CorrespondenceModel> oldMapping2CorrespondenceModelMap = JavaBridge
                .getFieldFromClass(VSUMImpl.class, "mapping2CorrespondenceModelMap", vsum);
        Map<Mapping, CorrespondenceModel> newMapping2CorrespondenceModelMap = JavaBridge
                .getFieldFromClass(VSUMImpl.class, "mapping2CorrespondenceModelMap", newVSUM);
        assertEquals("Mapping maps must have the same size", oldMetamodel2CorrespondenceModelsMap.size(),
                newMetamodel2CorrespondenceModelsMap.size());
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

    @Test
    public void testLoadVSUMAndCorrespondenceModel() {
        // create and fill VSUM
        VSUMImpl vsum = testMetaRepositoryAndVSUMCreation();
        fillVSUM(vsum);

        // create VSUM again
        VSUMImpl newVSUM = testMetaRepositoryAndVSUMCreation();

        // check correspondences
        fail("TODO: check whether correspondence instances and the maps within the correspondence instances are equal.");
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
