package edu.kit.ipd.sdq.vitruvius.tests.components;

import edu.kit.ipd.sdq.vitruvius.extensions.tests.util.TestUtil;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public abstract class AbstractVSUMTest extends MetaRepositoryTest {

    protected VSUMImpl testMetaRepositoryAndVSUMCreation(final String mm1URIString, final String fileExt1,
            final String mm2URIString, final String fileExt2) {
        MetaRepositoryImpl metaRepository = testMetaRepository();
        testAddMapping(metaRepository, mm1URIString, fileExt1, mm2URIString, fileExt2);
        return testVSUMCreation(metaRepository);
    }

    protected VSUMImpl testVSUMCreation(final MetaRepositoryImpl metaRepository) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        VSUMImpl vsum = TestUtil.createVSUM(metaRepository, classLoader);
        return vsum;
    }
}
