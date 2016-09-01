package edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.mapping.interfaces;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.mapping.MappedCorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.mapping.interfaces.Candidate;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;

public interface CandidateGenerator {
	public Set<Candidate> createCandidates(EChange change, List<MappingRealization> requiredMappings,
			List<EClass> elementTypes, MappingRealization mapping, MappedCorrespondenceModel mci);
}
