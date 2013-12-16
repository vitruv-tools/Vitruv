package edu.kit.ipd.sdq.vitruvius.framework.run.syncmanager;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;

public class SyncManagerImpl implements ChangeSynchronizing {
	private final ModelProviding modelProviding;
	private final ChangePropagating changePropagating;
	
	public SyncManagerImpl(ModelProviding modelProviding, ChangePropagating changePropagating) {
		this.modelProviding = modelProviding;
		this.changePropagating = changePropagating;
	}

	@Override
	public void synchronizeChange(Change change, ModelInstance sourceModel) {
		// TODO Auto-generated method stub
		
	}

}
