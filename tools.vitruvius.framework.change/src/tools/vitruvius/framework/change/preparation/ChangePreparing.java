package tools.vitruvius.framework.change.preparation;

import java.util.List;

import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.description.VitruviusChange;

public interface ChangePreparing {
    List<EChange> prepareChange(VitruviusChange unpreparedChange);
}
