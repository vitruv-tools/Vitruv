package edu.kit.ipd.sdq.vitruvius.tests.components;

import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public abstract class AbstractVSUMTest extends MetaRepositoryTest {
    protected VSUMImpl testMetaRepositoryAndVSUMCreation(final String mm1URIString, final String fileExt1,
            final String mm2URIString, final String fileExt2) {
        MetaRepositoryImpl metaRepository = testMetaRepository();
        testAddMapping(metaRepository, mm1URIString, fileExt1, mm2URIString, fileExt2);
        return testVSUMCreation(metaRepository);
    }

    protected VSUMImpl testVSUMCreation(final MetaRepositoryImpl metaRepository) {
        VSUMImpl vsum = TestUtil.createVSUM(metaRepository);
        return vsum;
    }
}
