package edu.kit.ipd.sdq.vitruvius.framework.monitorededitor;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;

public abstract class AbstractMonitoredEditor {
	protected final ChangeSynchronizing changeSynchronizing;
	protected final ModelCopyProviding modelCopyProviding;
	
	public AbstractMonitoredEditor(ChangeSynchronizing changeSynchronizing,
			ModelCopyProviding modelCopyProviding) {
		this.changeSynchronizing = changeSynchronizing;
		this.modelCopyProviding = modelCopyProviding;
	}	

}
