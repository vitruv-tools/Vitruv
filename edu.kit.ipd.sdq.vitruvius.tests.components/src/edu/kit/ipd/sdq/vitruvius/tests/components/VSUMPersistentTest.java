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

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

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
        Map<Metamodel, Set<InternalCorrespondenceInstance>> oldMetamodel2CorrespondenceInstancesMap = TestUtil
                .getFieldFromClass(VSUMImpl.class, "metamodel2CorrespondenceInstancesMap", vsum);
        Map<Metamodel, Set<InternalCorrespondenceInstance>> newMetamodel2CorrespondenceInstancesMap = TestUtil
                .getFieldFromClass(VSUMImpl.class, "metamodel2CorrespondenceInstancesMap", newVSUM);
        assertEquals("Metamodel maps must have the same size", oldMetamodel2CorrespondenceInstancesMap.size(),
                newMetamodel2CorrespondenceInstancesMap.size());
        for (Metamodel oldMetamodel : oldMetamodel2CorrespondenceInstancesMap.keySet()) {
            boolean foundMetamodel = false;
            for (Metamodel newMetamodel : newMetamodel2CorrespondenceInstancesMap.keySet()) {
                if (metamodelEquals(oldMetamodel, newMetamodel)) {
                    // found metamodel
                    foundMetamodel = true;
                    // Check Set of CorrespondenceInstances
                    Set<InternalCorrespondenceInstance> oldCIs = oldMetamodel2CorrespondenceInstancesMap
                            .get(oldMetamodel);
                    Set<InternalCorrespondenceInstance> newCIs = newMetamodel2CorrespondenceInstancesMap
                            .get(newMetamodel);
                    assertEquals("Correspondence sets for metamodel " + oldMetamodel + " have to have the same size",
                            oldCIs.size(), newCIs.size());
                    for (InternalCorrespondenceInstance oldCi : oldCIs) {
                        boolean foundCorrespondenceInstance = false;
                        for (InternalCorrespondenceInstance newCi : newCIs) {
                            if (correspondenceInstanceEquals(oldCi, newCi)) {
                                foundCorrespondenceInstance = true;
                                break;
                            }
                        }
                        assertTrue(
                                "CorrespondenceInstance " + oldCi + " not found in the new CorrespondenceInstance set",
                                foundCorrespondenceInstance);
                    }
                    break;
                }
            }
            assertTrue("Metamodel " + oldMetamodel + " not found in the new metamodel map", foundMetamodel);
        }

        Map<Mapping, InternalCorrespondenceInstance> oldMapping2CorrespondenceInstanceMap = TestUtil
                .getFieldFromClass(VSUMImpl.class, "mapping2CorrespondenceInstanceMap", vsum);
        Map<Mapping, InternalCorrespondenceInstance> newMapping2CorrespondenceInstanceMap = TestUtil
                .getFieldFromClass(VSUMImpl.class, "mapping2CorrespondenceInstanceMap", newVSUM);
        assertEquals("Mapping maps must have the same size", oldMetamodel2CorrespondenceInstancesMap.size(),
                newMetamodel2CorrespondenceInstancesMap.size());
        for (Mapping oldMapping : oldMapping2CorrespondenceInstanceMap.keySet()) {
            boolean mappingFound = false;
            for (Mapping newMapping : newMapping2CorrespondenceInstanceMap.keySet()) {
                if (mappingEquals(oldMapping, newMapping)) {
                    mappingFound = true;
                    InternalCorrespondenceInstance oldCi = oldMapping2CorrespondenceInstanceMap.get(oldMapping);
                    InternalCorrespondenceInstance newCi = newMapping2CorrespondenceInstanceMap.get(newMapping);
                    assertTrue("Old correspondence instance " + oldCi + " does not equal new CorrespondenceInstance "
                            + newCi, correspondenceInstanceEquals(oldCi, newCi));
                }
            }
            assertTrue("Mapping " + oldMapping2CorrespondenceInstanceMap + " not found in the new mapping map",
                    mappingFound);
        }
    }

    @Test
    public void testLoadVSUMAndCorrespondenceInstance() {
        // create and fill VSUM
        VSUMImpl vsum = testMetaRepositoryAndVSUMCreation();
        fillVSUM(vsum);

        // create VSUM again
        VSUMImpl newVSUM = testMetaRepositoryAndVSUMCreation();

        // check correspondences
        fail("TODO: check whether correspondence instances and the maps within the correspondence instances are equal.");
    }

    private boolean correspondenceInstanceEquals(final InternalCorrespondenceInstance oldCi,
            final InternalCorrespondenceInstance newCi) {
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
