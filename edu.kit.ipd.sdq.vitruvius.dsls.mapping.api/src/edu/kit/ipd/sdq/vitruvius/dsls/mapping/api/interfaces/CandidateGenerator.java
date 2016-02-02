package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappedCorrespondenceInstance;

public interface CandidateGenerator {
	public Set<Candidate> createCandidates(EChange change, List<MappingRealization> requiredMappings,
			List<EClass> elementTypes, MappingRealization mapping, MappedCorrespondenceInstance mci);
}
