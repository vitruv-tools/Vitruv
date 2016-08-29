package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappedCorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange;

public interface CandidateGenerator {
	public Set<Candidate> createCandidates(EChange change, List<MappingRealization> requiredMappings,
			List<EClass> elementTypes, MappingRealization mapping, MappedCorrespondenceModel mci);
}
