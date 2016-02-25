package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges;

import com.github.gumtreediff.actions.model.Action;

public interface IScmActionExtractor<VersionId> {
	
	public Iterable<Action> getActions(VersionId newVersion, VersionId oldVersion);

}
