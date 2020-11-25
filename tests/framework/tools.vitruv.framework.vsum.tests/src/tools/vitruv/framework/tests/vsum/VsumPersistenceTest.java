package tools.vitruv.framework.tests.vsum;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import pcm_mockup.Pcm_mockupFactory;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.InternalVirtualModel;

public class VsumPersistenceTest extends VsumTest {
    @Test
    public void testSaveAndLoadModels() throws Exception {
        // 1. create empty VSUM + store dummy model instances
        InternalVirtualModel vsum = createDefaultVirtualModel();
        int nrOfVURIs = 2;
        Set<VURI> vuris = PersistentTestUtil.createDummyVURIs(getCurrentProjectFolder(), nrOfVURIs);
        // PersistentTestUtil.createResources(vuris);
        for (VURI vuri : vuris) {
            vsum.persistRootElement(vuri, Pcm_mockupFactory.eINSTANCE.createRepository());
        }

        // 2.create VSUM again (should read all model instances from disk)
        // InternalVirtualModel newVSUM = createMetaRepositoryAndVSUM();

        // TODO We do not need that method in general. Determine if the model exists somehow else
        // for (VURI vuri : vuris) {
        // assertTrue("new VSUM does not contain model instances with URI " + vuri,
        // newVSUM.existsModel(vuri));
        // }
        // 3. compare the modelInstances map from the two VSUMs - expectation: they are equal
        // Map<VURI, ModelInstance> modelInstancesVSUM = getModelInstancesFieldFromVSUM(vsum);
        // Map<VURI, ModelInstance> modelInstancesNewVSUM = getModelInstancesFieldFromVSUM(newVSUM);
        Set<VURI> vsumModelVuris = new HashSet<VURI>();
        Set<VURI> newVsumModelVuris = new HashSet<VURI>();
        // TODO We do not need the getAllModelInstances method in general. We have to determine this
        // somehow else
        // for (ModelInstance model : vsum.getAllModelInstances()) {
        // vsumModelVuris.add(model.getURI());
        // }
        // for (ModelInstance model : newVSUM.getAllModelInstances()) {
        // newVsumModelVuris.add(model.getURI());
        // }
        PersistentTestUtil.assertEqualsSets(vsumModelVuris, newVsumModelVuris);
    }

    // @Test
    // public void testLoadVSUMAndFillCorrespondenceMaps() throws Throwable {
    // // create and fill VSUM
    // InternalVirtualModel vsum = createMetaRepositoryAndVSUM();
    // fillVSUM(vsum);
    //
    // // create VSUM again
    // InternalVirtualModel newVSUM = createMetaRepositoryAndVSUM();
    //
    // // assert Maps
    // Map<MetamodelPair, InternalCorrespondenceModel> oldMapping2CorrespondenceModelMap =
    // JavaBridge
    // .getFieldFromClass(InternalVirtualModel.class, "mapping2CorrespondenceModelMap", vsum);
    // Map<MetamodelPair, InternalCorrespondenceModel> newMapping2CorrespondenceModelMap =
    // JavaBridge
    // .getFieldFromClass(VSUMImpl.class, "mapping2CorrespondenceModelMap", newVSUM);
    //
    // assertEquals("Mapping maps must have the same size",
    // oldMapping2CorrespondenceModelMap.size(),
    // newMapping2CorrespondenceModelMap.size());
    // for (MetamodelPair oldMapping : oldMapping2CorrespondenceModelMap.keySet()) {
    // boolean mappingFound = false;
    // for (MetamodelPair newMapping : newMapping2CorrespondenceModelMap.keySet()) {
    // if (mappingEquals(oldMapping, newMapping)) {
    // mappingFound = true;
    // CorrespondenceModel oldCi = oldMapping2CorrespondenceModelMap.get(oldMapping);
    // CorrespondenceModel newCi = newMapping2CorrespondenceModelMap.get(newMapping);
    // assertTrue(
    // "Old correspondence instance " + oldCi + " does not equal new CorrespondenceModel " + newCi,
    // correspondenceModelEquals(oldCi, newCi));
    // }
    // }
    // assertTrue("Mapping " + oldMapping2CorrespondenceModelMap + " not found in the new mapping
    // map",
    // mappingFound);
    // }
    // }

    // private boolean correspondenceModelEquals(final CorrespondenceModel oldCi, final
    // CorrespondenceModel newCi) {
    // VURI oldVURI = VURI.getInstance(oldCi.getResource().getURI());
    // VURI newVURI = VURI.getInstance(newCi.getResource().getURI());
    // return oldVURI == newVURI && mappingEquals(oldCi.getMapping(), newCi.getMapping());
    //
    // }

    // private boolean metamodelEquals(final Metamodel mmA, final Metamodel mmB) {
    // return Arrays.equals(mmA.getFileExtensions().toArray(), mmB.getFileExtensions().toArray())
    // && mmA.getURI().equals(mmB.getURI()) && mmA.getNsURIs().equals(mmB.getNsURIs());
    // }

    // private boolean mappingEquals(final MetamodelPair mappingA, final MetamodelPair mappingB) {
    // return metamodelEquals(mappingA.getMetamodelA(), mappingB.getMetamodelA())
    // && metamodelEquals(mappingA.getMetamodelB(), mappingB.getMetamodelB());
    // }

    // @SuppressWarnings("unchecked")
    // protected Map<VURI, ModelInstance> getModelInstancesFieldFromVSUM(final InternalVirtualModel
    // vsum)
    // throws NoSuchFieldException, IllegalAccessException {
    // Field modelInstancesField = VSUMImpl.class.getDeclaredField("modelInstances");
    // modelInstancesField.setAccessible(true);
    // return (Map<VURI, ModelInstance>) modelInstancesField.get(vsum);
    // }
}
