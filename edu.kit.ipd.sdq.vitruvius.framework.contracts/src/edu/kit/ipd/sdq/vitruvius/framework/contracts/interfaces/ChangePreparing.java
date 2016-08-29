package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange;

public interface ChangePreparing {
    List<EChange> prepareChange(VitruviusChange unpreparedChange);
}
