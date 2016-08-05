package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProcessableChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.RecordedChange;

public interface ChangePreparing {
    ProcessableChange prepareAllChanges(RecordedChange unpreparedChange);
}
