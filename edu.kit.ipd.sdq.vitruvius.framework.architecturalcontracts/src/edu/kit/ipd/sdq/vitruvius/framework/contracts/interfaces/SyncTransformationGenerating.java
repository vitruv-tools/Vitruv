package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import java.util.Collection;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.SyncTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface SyncTransformationGenerating {
	Collection<SyncTransformation> generateSyncTransformations(VURI mmURI1, VURI mmURI2);
}
