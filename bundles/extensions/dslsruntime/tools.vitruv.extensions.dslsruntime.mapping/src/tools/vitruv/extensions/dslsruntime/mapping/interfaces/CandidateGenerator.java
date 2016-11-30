package tools.vitruv.extensions.dslsruntime.mapping.interfaces;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;

import tools.vitruv.extensions.dslsruntime.mapping.MappedCorrespondenceModel;
import tools.vitruv.extensions.dslsruntime.mapping.interfaces.Candidate;
import tools.vitruv.framework.change.echange.EChange;

public interface CandidateGenerator {
	public Set<Candidate> createCandidates(EChange change, List<MappingRealization> requiredMappings,
			List<EClass> elementTypes, MappingRealization mapping, MappedCorrespondenceModel mci);
}
