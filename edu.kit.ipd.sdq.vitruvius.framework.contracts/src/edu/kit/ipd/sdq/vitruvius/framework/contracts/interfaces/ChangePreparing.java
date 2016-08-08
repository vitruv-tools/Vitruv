package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

public interface ChangePreparing {
    List<EChange> prepareChange(Change unpreparedChange);
}
