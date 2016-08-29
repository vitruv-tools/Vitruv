package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;

public interface ChangePreparing {
    List<EChange> prepareChange(VitruviusChange unpreparedChange);
}
