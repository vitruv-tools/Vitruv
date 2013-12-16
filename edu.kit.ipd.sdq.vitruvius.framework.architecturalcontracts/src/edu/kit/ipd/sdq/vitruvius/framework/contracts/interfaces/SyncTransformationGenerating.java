package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Collection;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.SyncTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URI;

public interface SyncTransformationGenerating {
	Collection<SyncTransformation> generateSyncTransformations(URI mmURI1, URI mmURI2);
}
