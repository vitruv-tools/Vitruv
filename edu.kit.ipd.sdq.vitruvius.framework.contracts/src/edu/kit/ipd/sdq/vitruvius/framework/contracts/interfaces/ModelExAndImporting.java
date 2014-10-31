package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelFile;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface ModelExAndImporting {
	ModelFile exportModel(VURI sourceURI);
	void importModel(ModelFile modelFile);
	void importDifferences(ModelFile oldModelFile, ModelFile newModelFile);
}
