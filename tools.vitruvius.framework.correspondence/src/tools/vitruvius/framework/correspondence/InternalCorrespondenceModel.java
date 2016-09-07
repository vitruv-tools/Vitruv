package tools.vitruvius.framework.correspondence;

import tools.vitruvius.framework.correspondence.CorrespondenceModel;

public interface InternalCorrespondenceModel extends CorrespondenceModel {
	public boolean changedAfterLastSave();
    public void resetChangedAfterLastSave();
}
