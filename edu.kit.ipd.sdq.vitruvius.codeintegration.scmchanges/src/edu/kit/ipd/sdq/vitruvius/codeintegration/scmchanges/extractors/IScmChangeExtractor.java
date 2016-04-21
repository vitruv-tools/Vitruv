package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ScmChangeResult;

public interface IScmChangeExtractor<VersionId> {
	
	public List<ScmChangeResult> extract(VersionId newVersion, VersionId oldVersion);

}
