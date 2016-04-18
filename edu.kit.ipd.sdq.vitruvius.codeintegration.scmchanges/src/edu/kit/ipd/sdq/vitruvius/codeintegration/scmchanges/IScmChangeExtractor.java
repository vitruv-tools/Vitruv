package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges;

import java.util.List;

public interface IScmChangeExtractor<VersionId> {
	
	public List<ScmChangeResult> extract(VersionId newVersion, VersionId oldVersion);

}
