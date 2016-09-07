package tools.vitruvius.extensions.dslsruntime.mapping.interfaces;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;

import tools.vitruvius.extensions.dslsruntime.mapping.MappedCorrespondenceModel;
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.Candidate;
import tools.vitruvius.framework.change.echange.EChange;

public interface CandidateGenerator {
	public Set<Candidate> createCandidates(EChange change, List<MappingRealization> requiredMappings,
			List<EClass> elementTypes, MappingRealization mapping, MappedCorrespondenceModel mci);
}
