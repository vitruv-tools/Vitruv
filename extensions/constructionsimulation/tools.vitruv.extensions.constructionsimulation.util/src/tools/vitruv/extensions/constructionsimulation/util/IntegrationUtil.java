package tools.vitruv.extensions.constructionsimulation.util;

import tools.vitruv.framework.change.processing.Change2CommandTransformingProviding;
import tools.vitruv.framework.change.processing.impl.Change2CommandTransformingProvidingImpl;
import tools.vitruv.framework.metarepository.MetaRepositoryImpl;
import tools.vitruv.framework.modelsynchronization.ChangeSynchronizerImpl;
import tools.vitruv.framework.modelsynchronization.ChangeSynchronizing;
import tools.vitruv.framework.vsum.VSUMImpl;

public class IntegrationUtil {

    private IntegrationUtil() {
    }

    public static ChangeSynchronizing createVitruviusCore(final VSUMImpl vsum,
            final MetaRepositoryImpl metaRepository) {
        final Change2CommandTransformingProviding change2CommandTransformingProviding = new Change2CommandTransformingProvidingImpl();

        final ChangeSynchronizerImpl changeSynchronizerImpl = new ChangeSynchronizerImpl(vsum,
                change2CommandTransformingProviding, vsum);

        return changeSynchronizerImpl;
    }

    public static VSUMImpl createVSUM(final MetaRepositoryImpl metaRepository) {
        final VSUMImpl vsum = new VSUMImpl(metaRepository, metaRepository);
        return vsum;
    }

    public static ChangeSynchronizing createVitruviusCore(final MetaRepositoryImpl metaRepository) {
        final VSUMImpl vsum = createVSUM(metaRepository);
        return createVitruviusCore(vsum, metaRepository);
    }

}
