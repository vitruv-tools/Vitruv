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
}
