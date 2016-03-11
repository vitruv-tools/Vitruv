package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges;

public interface IScmActionExtractor<VersionId> {
	
	public Iterable<ExtractionResult> extract(VersionId newVersion, VersionId oldVersion);

}
