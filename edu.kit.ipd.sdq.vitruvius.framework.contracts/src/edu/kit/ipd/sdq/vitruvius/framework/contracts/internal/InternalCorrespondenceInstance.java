package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.HalfEObjectCorrespondence;

public interface InternalCorrespondenceInstance extends CorrespondenceInstance {

    public Resource getResource();

    public boolean changedAfterLastSave();

    public void resetChangedAfterLastSave();

    public VURI getURI();

    public HalfEObjectCorrespondence getOrCreateHalfEObjectCorrespondence(List<EObject> eObjects);

}
