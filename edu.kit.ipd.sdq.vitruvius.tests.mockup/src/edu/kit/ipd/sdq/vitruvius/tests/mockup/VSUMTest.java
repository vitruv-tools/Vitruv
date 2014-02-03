package edu.kit.ipd.sdq.vitruvius.tests.mockup;

import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceMM;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceMMProviding;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class VSUMTest extends MetaRepositoryTest {
    @Override
    @Test
    public void testAll() {
        test("MockupProject/metamodels/pcm_mockup.ecore", "MockupProject/metamodels/uml_mockup.ecore");

    }

    public void test(final String mm1URIString, final String mm2URIString) {
        MetaRepositoryImpl metaRepository = testMetaRepository();
        testAddMapping(metaRepository, mm1URIString, mm2URIString);
        CorrespondenceMMProviding correspondenceMMproviding = new CorrespondenceMMProviding() {

            @Override
            public CorrespondenceMM getCorrespondenceMM(final VURI uriMM1, final VURI uriMM2) {
                // TODO Auto-generated method stub
                return null;
            }
        };
        VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository, metaRepository, correspondenceMMproviding);
    }
}
