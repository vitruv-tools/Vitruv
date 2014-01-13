package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProjectInput;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProjectOutput;

public interface ProjectGenerating {
	ProjectOutput generateProject(ProjectInput projectInput);
}
