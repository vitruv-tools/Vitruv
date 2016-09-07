package tools.vitruv.extensions.dslsruntime.mapping.interfaces

import tools.vitruv.framework.correspondence.Correspondence
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Data

@Data
public class Candidate implements ElementProvider {
	public List<Correspondence> requiredCorrespondences;
	public List<EObject> elements;
}