package tools.vitruv.framework.modelsynchronization.blackboard;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.util.command.VitruviusRecordingCommand;
import tools.vitruv.framework.vsum.ModelRepository;

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
@Deprecated // Only Mapping languages still needs this, will be removed if Mappings are updated
public interface Blackboard {
    CorrespondenceModel getCorrespondenceModel();

    ModelRepository getModelProviding();

    void pushCommands(List<VitruviusRecordingCommand> commands);

    void pushSourceModelResource(Resource resource);

    void pushChangedResource(Resource resource);

    Set<Resource> getAndArchiveSourceModelResources();

    Set<Resource> getAndArchiveChangedResources();

    List<VitruviusRecordingCommand> getAndArchiveCommandsForExecution();

}
