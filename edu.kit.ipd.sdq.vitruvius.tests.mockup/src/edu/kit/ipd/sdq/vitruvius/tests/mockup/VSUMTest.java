package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceMM;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceMMProviding;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class VSUMTest extends MetaRepositoryTest {
    @Override
    @Test
    public void testAll() {
        testMetaRepositoryVSUMAndModelInstancesCreation("/MockupProject/model/My.pcm_mockup");
    }

    public VSUMImpl testMetaRepositoryVSUMAndModelInstancesCreation(final String pcmURIString) {
        return testMetaRepositoryVSUMAndModelInstancesCreation("/MockupProject/metamodel/pcm_mockup.ecore",
                "pcm_mockup", "/MockupProject/metamodel/uml_mockup.ecore", "uml_mockup", pcmURIString,
                "/MockupProject/model/My.uml_mockup");
    }

    private VSUMImpl testMetaRepositoryVSUMAndModelInstancesCreation(final String mm1URIString, final String fileExt1,
            final String mm2URIString, final String fileExt2, final String model1URIString, final String model2URIString) {
        MetaRepositoryImpl metaRepository = testMetaRepository();
        testAddMapping(metaRepository, mm1URIString, fileExt1, mm2URIString, fileExt2);
        CorrespondenceMMProviding correspondenceMMproviding = new CorrespondenceMMProviding() {
            @Override
            public CorrespondenceMM getCorrespondenceMM(final VURI uriMM1, final VURI uriMM2) {
                // nothing to be done as long as the correspondence mm stays generic
                return null;
            }
        };
        VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository, metaRepository, correspondenceMMproviding);

        VURI model1URI = VURI.getInstance(model1URIString);
        VURI model2URI = VURI.getInstance(model2URIString);
        ModelInstance model1 = vsum.getModelInstanceOriginal(model1URI);
        ModelInstance model2 = vsum.getModelInstanceOriginal(model2URI);
        EList<EObject> contents1 = model1.getResource().getContents();
        assertNotNull(contents1);
        EObject root1 = contents1.get(0);
        assertNotNull(root1);
        EList<EObject> contents2 = model2.getResource().getContents();
        assertNotNull(contents2);
        EObject root2 = contents2.get(0);
        assertNotNull(root2);

        return vsum;
    }
}
