package tools.vitruv.framework.tests.vsum;

import tools.vitruv.framework.metarepository.MetaRepositoryImpl;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.vsum.VSUMImpl;

public abstract class AbstractVSUMTest extends MetaRepositoryTest {

    protected VSUMImpl createMetaRepositoryAndVSUM(final String mm1URIString, final String fileExt1,
            final String mm2URIString, final String fileExt2) {
        MetaRepositoryImpl metaRepository = testMetaRepository();
        addMapping(metaRepository, mm1URIString, fileExt1, mm2URIString, fileExt2);
        return createVSUM(metaRepository);
    }

    protected VSUMImpl createVSUM(final MetaRepositoryImpl metaRepository) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        VSUMImpl vsum = TestUtil.createVSUM(metaRepository, classLoader);
        return vsum;
    }
}
