package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Data

@Data
public class Candidate implements ElementProvider {
	public List<Correspondence> requiredCorrespondences;
	public List<EObject> elements;
}