package tools.vitruvius.framework.modelsynchronization.blackboard;

import java.util.List;

import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.correspondence.CorrespondenceProviding;
import tools.vitruvius.framework.metamodel.ModelProviding;
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand;
import tools.vitruvius.framework.util.datatypes.Pair;

/*
 * @startuml doc-files/Blackboard.png
 *
 * [*] --> Ready
 *
 * state Ready {
 *
 * [*] --> Waiting4Changes
 *
 * Waiting4Changes --> Waiting4Transformation : pushChanges
 *
 * Waiting4Transformation --> Waiting4Changes : popChangesForPreparation
 *
 * Waiting4Transformation --> Waiting4Commands : getAndArchiveChangesForTransformation
 *
 * Waiting4Commands --> Waiting4Execution : pushCommands
 *
 * Waiting4Execution --> Waiting4Check : getAndArchiveCommandsForExecution
 *
 * Waiting4Check --> Waiting4Undo : pushCheckResult
 *
 * Waiting4Undo --> Waiting4Undo : getCheckResult
 *
 * Waiting4Undo --> Waiting4Redo : getArchivedChangesAndCommandsForUndo
 *
 * Waiting4Redo --> Waiting4Execution : unarchiveChangesAndCommandsForRedo
 *
 * }
 *
 * Ready --> Ready : getCorrespondenceModel, getModelProviding
 *
 * @enduml
 */
/**
 * Blackboard managing all data that is necessary to propagate a list of changes that occurred in
 * model instances of a metamodel A to model instances of a metamodel B. A new individual blackboard
 * is used to propagate other changes to model instances of B or the same changes to model instances
 * of another metamodel C. All blackboards are kept in an archive for later undoing and redoing.
 * <p>
 * The method invocation protocol for each blackboard is depicted in the following state chart:
 * <p>
 * <img src="doc-files/Blackboard.png">
 *
 * @author kramerm
 *
 */
public interface Blackboard {
    CorrespondenceModel getCorrespondenceModel();

    ModelProviding getModelProviding();

    CorrespondenceProviding getCorrespondenceProviding();

    void pushChanges(List<VitruviusChange> changes);

    List<VitruviusChange> popChangesForPreparation();

    List<VitruviusChange> getAndArchiveChangesForTransformation();

    void pushCommands(List<VitruviusRecordingCommand> commands);

    List<VitruviusRecordingCommand> getAndArchiveCommandsForExecution();

    Pair<List<VitruviusChange>, List<VitruviusRecordingCommand>> getArchivedChangesAndCommandsForUndo();

    void unarchiveChangesAndCommandsForRedo();

}
