package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;

/**
 * 
 * @author dwerle
 *
 * @param <W>
 *            The type of the wrapper for the relevant side.
 * @param <CW>
 *            The type of the correspondence wrapper
 */
public class MatchUpdate<W extends ElementProvider, CW> {
	private Set<W> newCandidates;
	private Set<CW> currentCorrespondences;
	private Set<CW> voidedCorrespondences;

	public MatchUpdate() {
		intialize();
	}

	public MatchUpdate(MappingRealization mapping, MappedCorrespondenceInstance mci, Set<Candidate> candidates,
			Function<Candidate, W> matchWrapper, Predicate<W> checkFunction,
			Function<Correspondence, CW> correspondenceWrapper) {
		
		populate(mapping, mci, candidates, matchWrapper, checkFunction, correspondenceWrapper);
	}

	private void intialize() {
		newCandidates = new HashSet<>();
		currentCorrespondences = new HashSet<>();
		voidedCorrespondences = new HashSet<>();
	}

	public void populate(MappingRealization mapping, MappedCorrespondenceInstance mci, Set<Candidate> candidates,
			Function<Candidate, W> matchWrapper, Predicate<W> checkFunction,
			Function<Correspondence, CW> correspondenceWrapper) {

		intialize();

		candidates.stream().forEach(candidate -> {
			W wrapper = matchWrapper.apply(candidate);
			
			Correspondence correspondence = mci.getMappedCorrespondence(candidate.getElements(), candidate.getRequiredCorrespondences(), mapping);
			boolean wasMapped = (correspondence != null);
			boolean isMapped = checkFunction.test(wrapper);

			if (!wasMapped && isMapped)
				newCandidates.add(wrapper);
			else if (wasMapped && !isMapped)
				voidedCorrespondences.add(correspondenceWrapper.apply(correspondence));
			else if (wasMapped && isMapped)
				currentCorrespondences.add(correspondenceWrapper.apply(correspondence));
		});

	}

	public Set<W> getNewCandidates() {
		return Collections.unmodifiableSet(newCandidates);
	}

	public Set<CW> getCurrentCorrespondences() {
		return Collections.unmodifiableSet(currentCorrespondences);
	}

	public Set<CW> getVoidedCorrespondences() {
		return Collections.unmodifiableSet(voidedCorrespondences);
	}
}
