package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal;

import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;

public interface InternalCorrespondenceModel extends CorrespondenceModel {
    public Resource getResource();

    public boolean changedAfterLastSave();

    public void resetChangedAfterLastSave();
}
