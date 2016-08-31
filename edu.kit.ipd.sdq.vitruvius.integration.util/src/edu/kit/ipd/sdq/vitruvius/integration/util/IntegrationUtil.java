package edu.kit.ipd.sdq.vitruvius.integration.util;

import edu.kit.ipd.sdq.vitruvius.framework.commandexecutor.CommandExecutingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.change.preparation.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.change.preparation.ChangePreparingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.commandexecutor.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class IntegrationUtil {

    private IntegrationUtil() {
    }

    public static ChangeSynchronizing createVitruviusCore(final VSUMImpl vsum,
            final MetaRepositoryImpl metaRepository) {
        final Change2CommandTransformingProviding change2CommandTransformingProviding = new Change2CommandTransformingProvidingImpl();

        final ChangePreparing changePreparing = new ChangePreparingImpl();
        final CommandExecuting commandExecuting = new CommandExecutingImpl();

        final ChangeSynchronizerImpl changeSynchronizerImpl = new ChangeSynchronizerImpl(vsum,
                change2CommandTransformingProviding, vsum, null, changePreparing,
                commandExecuting);

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
