package edu.kit.ipd.sdq.vitruvius.integration.util;

import edu.kit.ipd.sdq.vitruvius.commandexecutor.CommandExecutingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.Change2CommandTransformingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.changes.changepreparer.ChangePreparingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.changesynchronizer.ChangeSynchronizerImpl;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePreparing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CommandExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl;

public class IntegrationUtil {

    private IntegrationUtil() {
    }

    public static ChangeSynchronizing createVitruviusCore(final VSUMImpl vsum,
            final MetaRepositoryImpl metaRepository) {
        final Change2CommandTransformingProviding change2CommandTransformingProviding = new Change2CommandTransformingProvidingImpl();

        final ChangePreparing changePreparing = new ChangePreparingImpl(vsum);
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
