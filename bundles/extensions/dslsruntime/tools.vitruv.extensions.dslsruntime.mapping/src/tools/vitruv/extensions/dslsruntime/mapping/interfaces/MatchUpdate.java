package tools.vitruv.extensions.dslsruntime.mapping.interfaces;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import tools.vitruv.extensions.dslsruntime.mapping.MappedCorrespondenceModel;
import tools.vitruv.extensions.dslsruntime.mapping.interfaces.Candidate;
import tools.vitruv.framework.correspondence.Correspondence;
import edu.kit.ipd.sdq.commons.util.java.Pair;
import edu.kit.ipd.sdq.commons.util.java.Triple;

/**
 * Represents all changed mapping states for a set of candidates.
 * 
 * @author dwerle
 *
 * @param <W>
 *            The type of the wrapper for the relevant side.
 * @param <CW>
 *            The type of the correspondence wrapper
 */
public class MatchUpdate<W extends ElementProvider, CW> {
	private Set<Pair<Candidate, W>> newCandidates;
	private Set<Pair<Candidate, CW>> currentCorrespondences;
	private Set<Pair<Candidate, CW>> voidedCorrespondences;
	// candidates that have been rebased from an existing correspondence (second
	// element)
	private Set<Triple<Candidate, CW, W>> rebasedCandidates;

	public MatchUpdate() {
		intialize();
	}

	public MatchUpdate(MappingRealization mapping, MappedCorrespondenceModel mci, Set<Candidate> candidates,
			Function<Candidate, W> matchWrapper, Predicate<W> checkFunction,
			Function<Correspondence, CW> correspondenceWrapper) {

		populate(mapping, mci, candidates, matchWrapper, checkFunction, correspondenceWrapper);
	}

	private void intialize() {
		newCandidates = new HashSet<>();
		currentCorrespondences = new HashSet<>();
		voidedCorrespondences = new HashSet<>();
		rebasedCandidates = new HashSet<>();
	}

	public void populate(MappingRealization mapping, MappedCorrespondenceModel mci, Set<Candidate> candidates,
			Function<Candidate, W> matchWrapper, Predicate<W> checkFunction,
			Function<Correspondence, CW> correspondenceWrapper) {

		intialize();

		candidates.stream().forEach(candidate -> {
			W wrapper = matchWrapper.apply(candidate);

			Correspondence correspondence = mci.getMappedCorrespondence(candidate.getElements(),
					candidate.getRequiredCorrespondences(), mapping);
			boolean wasMapped = (correspondence != null);
			boolean isMapped = checkFunction.test(wrapper);

			if (!wasMapped && isMapped) {
				// if a candidate with the same elements, but different required
				// mappings was previously removed,
				// this is a rebase and saved as such
				final Optional<Pair<Candidate, CW>> previouslyVoided = voidedCorrespondences.stream()
						.filter(it -> it.getFirst().getElements().equals(candidate.elements)).findFirst();
				if (previouslyVoided.isPresent()) {
					rebasedCandidates.add(new Triple<>(candidate, previouslyVoided.get().getSecond(), wrapper));
				} else {
					newCandidates.add(new Pair<>(candidate, wrapper));
				}
			} else if (wasMapped && !isMapped) {
				// see above. TODO: combine
				final Optional<Pair<Candidate, W>> previouslyCreated = newCandidates.stream()
						.filter(it -> it.getFirst().getElements().equals(candidate.elements)).findFirst();
				if (previouslyCreated.isPresent()) {
					rebasedCandidates.add(new Triple<>(candidate, correspondenceWrapper.apply(correspondence),
							previouslyCreated.get().getSecond()));
				} else {
					voidedCorrespondences.add(new Pair<>(candidate, correspondenceWrapper.apply(correspondence)));
				}
			} else if (wasMapped && isMapped) {
				currentCorrespondences.add(new Pair<>(candidate, correspondenceWrapper.apply(correspondence)));
			}
		});

	}

	public Set<W> getNewCandidates() {
		return newCandidates.stream().map(it -> it.getSecond()).collect(Collectors.toSet());
	}

	public Set<CW> getCurrentCorrespondences() {
		return currentCorrespondences.stream().map(it -> it.getSecond()).collect(Collectors.toSet());
	}

	public Set<CW> getVoidedCorrespondences() {
		return voidedCorrespondences.stream().map(it -> it.getSecond()).collect(Collectors.toSet());
	}
}
