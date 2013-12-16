package edu.kit.ipd.sdq.vitruvius.framework.run.exandimporter;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelExAndImporting;

public abstract class AbstractExAndImporter implements ModelExAndImporting {
	@SuppressWarnings("unused")
	private final ChangeSynchronizing changeSynchronizing;
	@SuppressWarnings("unused")
	private final ModelCopyProviding modelCopyProviding;
	
	public AbstractExAndImporter(ChangeSynchronizing changeSynchronizing,
			ModelCopyProviding modelCopyProviding) {
		this.changeSynchronizing = changeSynchronizing;
		this.modelCopyProviding = modelCopyProviding;
	}

}
