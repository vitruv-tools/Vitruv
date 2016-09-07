package tools.vitruvius.applications.jmljava.synchronizers.java.compositerefiners;

import java.util.List;
import java.util.concurrent.Callable;

import com.google.common.collect.Lists;

import tools.vitruvius.applications.jmljava.synchronizers.CustomTransformation;
import tools.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener;
import tools.vitruvius.applications.jmljava.synchronizers.CSSynchronizer;
import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.util.command.TransformationResult;
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand;
import tools.vitruvius.framework.userinteraction.UserInteracting;
import tools.vitruvius.framework.util.command.EMFCommandBridge;

/**
 * Base class for composite change refiner results, which are custom transformations.
 *
 * @author Stephan Seifermann
 *
 */
public class CompositeChangeRefinerResultTransformation implements CompositeChangeRefinerResult {

    private final CustomTransformation transformation;

    /**
     * Constructor.
     *
     * @param transformation
     *            The transformation, which shall be executed.
     */
    public CompositeChangeRefinerResultTransformation(final CustomTransformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public List<VitruviusRecordingCommand> apply(final CSSynchronizer transformationExecuting, final CorrespondenceModel correspondenceModel,
            final UserInteracting ui, final SynchronisationAbortedListener abortListener) {
        final VitruviusRecordingCommand command = EMFCommandBridge
                .createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {
                    @Override
                    public TransformationResult call() {
                        return CompositeChangeRefinerResultTransformation.this.transformation.execute(correspondenceModel, ui,
                                abortListener);
                    }
                });
        return Lists.newArrayList(command);

    }

}
