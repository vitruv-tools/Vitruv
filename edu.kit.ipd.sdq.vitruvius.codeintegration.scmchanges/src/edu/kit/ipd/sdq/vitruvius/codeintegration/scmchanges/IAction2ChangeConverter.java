package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges;

import com.github.gumtreediff.actions.model.Action;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

public interface IAction2ChangeConverter {
	
	public Iterable<EChange> convertToChanges(Iterable<Action> actions);

}
