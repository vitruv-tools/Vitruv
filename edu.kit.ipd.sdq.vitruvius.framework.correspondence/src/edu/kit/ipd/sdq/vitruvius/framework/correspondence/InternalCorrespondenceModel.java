package edu.kit.ipd.sdq.vitruvius.framework.correspondence;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;

public interface InternalCorrespondenceModel extends CorrespondenceModel {
	public boolean changedAfterLastSave();
    public void resetChangedAfterLastSave();
}
