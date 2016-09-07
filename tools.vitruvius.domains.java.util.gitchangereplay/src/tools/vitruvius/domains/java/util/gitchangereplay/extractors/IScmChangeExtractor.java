package tools.vitruvius.domains.java.util.gitchangereplay.extractors;

import java.util.List;

import tools.vitruvius.domains.java.util.gitchangereplay.ScmChangeResult;

public interface IScmChangeExtractor<VersionId> {
	
	public List<ScmChangeResult> extract(VersionId newVersion, VersionId oldVersion);

}
