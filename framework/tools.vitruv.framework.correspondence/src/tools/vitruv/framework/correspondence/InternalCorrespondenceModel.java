package tools.vitruv.framework.correspondence;

import tools.vitruv.framework.correspondence.CorrespondenceModel;

public interface InternalCorrespondenceModel extends CorrespondenceModel {
	public boolean changedAfterLastSave();
    public void resetChangedAfterLastSave();
}
