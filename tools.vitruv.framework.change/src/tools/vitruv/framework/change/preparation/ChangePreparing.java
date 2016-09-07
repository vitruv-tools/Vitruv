package tools.vitruv.framework.change.preparation;

import java.util.List;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.description.VitruviusChange;

public interface ChangePreparing {
    List<EChange> prepareChange(VitruviusChange unpreparedChange);
}
