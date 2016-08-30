package edu.kit.ipd.sdq.vitruvius.framework.change.preparation;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;

public interface ChangePreparing {
    List<EChange> prepareChange(VitruviusChange unpreparedChange);
}
