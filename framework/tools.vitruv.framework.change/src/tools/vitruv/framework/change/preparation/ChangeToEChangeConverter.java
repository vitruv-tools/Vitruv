package tools.vitruv.framework.change.preparation;

import java.util.List;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.description.EMFModelChange;
import tools.vitruv.framework.change.description.FileChange;

public interface ChangeToEChangeConverter {
    List<EChange> calculateEChangesForChange(EMFModelChange unpreparedChange);
    List<EChange> calculateEChangesForChange(FileChange unpreparedChange);
}
