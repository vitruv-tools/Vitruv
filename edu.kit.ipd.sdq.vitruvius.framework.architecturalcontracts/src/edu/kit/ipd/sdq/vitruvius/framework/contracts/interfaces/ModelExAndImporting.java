package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelFile;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URI;

public interface ModelExAndImporting {
	ModelFile exportModel(URI sourceURI);
	void importModel(ModelFile modelFile);
	void importDifferences(ModelFile oldModelFile, ModelFile newModelFile);
}
