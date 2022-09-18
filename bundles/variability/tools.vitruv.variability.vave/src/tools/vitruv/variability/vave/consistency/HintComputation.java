package tools.vitruv.variability.vave.consistency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;
import tools.vitruv.variability.vave.model.vave.Configuration;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureOption;
import tools.vitruv.variability.vave.model.vave.FeatureRevision;
import tools.vitruv.variability.vave.model.vave.SystemRevision;
import tools.vitruv.variability.vave.util.FeatureModelUtil;
import tools.vitruv.variability.vave.util.OptionUtil;

/**
 * Performs consistency preservation from problem space (feature model) to solution space (product) by computing hints for not yet implemented features or potential pair-wise feature interactions.
 */
public class HintComputation implements ConsistencyRule {

	public class Result implements ConsistencyResult {
		private Collection<Feature[]> newHints;

		public Result(Collection<Feature[]> newHints) {
			this.newHints = newHints;
		}

		public Collection<Feature[]> getNewHints() {
			return this.newHints;
		}
	}

	private Collection<Feature[]> hints = new ArrayList<>();

	/**
	 * Checks if any of the hinted feature interactions are contained in the configuration. If yes, the hint is added to the product.
	 */
	@Override
	public ConsistencyResult externalizeProductPre(Configuration config) {
		Collection<Feature[]> hints = new ArrayList<>();

		for (Feature[] featureInteraction : this.hints) {
			if (config.getOptions().containsAll(Arrays.asList(featureInteraction))) {
				hints.add(featureInteraction);
				System.out.println("RELEVANT HINT: " + Arrays.asList(featureInteraction));
			}
		}

		return new Result(hints);
	}

	/**
	 * 	Checks if the expression covers any hints. If yes, the respective hints are deleted.
	 */
	@Override
	public ConsistencyResult internalizeChangesPre(VirtualVaVeModel vave, Expression<FeatureOption> expression) {
		Collection<FeatureOption> options = OptionUtil.collect(expression);

		Collection<Feature[]> removedHints = new ArrayList<>();

		// NOTE: we can do this easily with the OptionCollector as long as we assume that expressions are always conjunctions of positive features (see NOTE at the top of this method)!
		Iterator<Feature[]> hintsIt = this.hints.iterator();
		while (hintsIt.hasNext()) {
			Feature[] featureInteraction = hintsIt.next();
			if (options.containsAll(Arrays.asList(featureInteraction))) {
				removedHints.add(featureInteraction);
				hintsIt.remove();
				System.out.println("FIXED HINT: " + Arrays.asList(featureInteraction));
			}
		}

		return new Result(removedHints);
	}

	/**
	 * Computes hints for new (i.e., not yet implemented) features or potential pair-wise feature interactions after every execution of the vave operation internalizeDomain.
	 */
	@Override
	public ConsistencyResult internalizeDomainPost(SystemRevision newSystemRevision, FeatureModel previousFeatureModel, FeatureModel currentFeatureModel) {
		Collection<FeatureOption> enabledFeatureOptions = newSystemRevision.getEnablesFeatureOptions();
		List<Feature> enabledFeatures = enabledFeatureOptions.stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).collect(Collectors.toList());

		// assign integer values to each feature. feature revisions get the same value as the respective feature.
		int currentInt = 0;
		Map<FeatureOption, Integer> optionToIntMap = new HashMap<>();
		for (FeatureOption featureOption : enabledFeatureOptions) {
			if (featureOption instanceof Feature) {
				optionToIntMap.put(featureOption, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(featureOption) + " TO FEATURE " + featureOption);
			}
		}
		for (FeatureOption fo : enabledFeatureOptions) {
			if (fo instanceof FeatureRevision) {
				Feature feature = (Feature) ((FeatureRevision) fo).eContainer();
				if (optionToIntMap.get(feature) == null) {
					optionToIntMap.put(feature, ++currentInt);
					System.out.println("ASSIGN VALUE " + optionToIntMap.get(feature) + " TO FEATURE " + fo);
				}
				optionToIntMap.put(fo, optionToIntMap.get(feature));
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE REVISION " + fo);
			}
		}

		// compute clauses for old feature model
		Collection<int[]> previousFeatureModelClauses = null;
		if (previousFeatureModel == null) {
			previousFeatureModelClauses = new ArrayList<>();
		} else {
			previousFeatureModelClauses = FeatureModelUtil.computeClauses(previousFeatureModel, optionToIntMap);
			// add clauses for negations of features that are not contained in feature model but enabled in new system revision
			Set<FeatureOption> featureOptionsNotInPreviousFeatureModel = new HashSet<>(optionToIntMap.keySet());
			featureOptionsNotInPreviousFeatureModel.removeAll(FeatureModelUtil.collectFeatureOptions(previousFeatureModel));
			for (FeatureOption featureOption : featureOptionsNotInPreviousFeatureModel)
				previousFeatureModelClauses.add(new int[] { -optionToIntMap.get(featureOption) });
		}

		// compute clauses for current feature model
		Collection<int[]> currentFeatureModelClauses = FeatureModelUtil.computeClauses(currentFeatureModel, optionToIntMap);

		// compute (new) hints
		Collection<Feature[]> newHints = new ArrayList<>();
		for (int i = 0; i < enabledFeatures.size(); i++) { // for (Feature f1 : enabledFs) {
			Feature f1 = enabledFeatures.get(i);
			for (int j = i; j < enabledFeatures.size(); j++) { // for (Feature f2 : enabledFs) {
				Feature f2 = enabledFeatures.get(j);
				ISolver previousFeatureModelSolver = SolverFactory.newDefault();
				ISolver currentFeatureModelSolver = SolverFactory.newDefault();
				boolean oldSat = false;
				boolean newSat = false;

				try {
					for (int[] clause : previousFeatureModelClauses)
						previousFeatureModelSolver.addClause(new VecInt(clause));
					previousFeatureModelSolver.addClause(new VecInt(new int[] { optionToIntMap.get(f1) }));
					previousFeatureModelSolver.addClause(new VecInt(new int[] { optionToIntMap.get(f2) }));
					oldSat = previousFeatureModelSolver.isSatisfiable();
				} catch (ContradictionException e) {
					oldSat = false;
				} catch (TimeoutException e) {
					oldSat = false;
				}

				try {
					for (int[] clause : currentFeatureModelClauses)
						currentFeatureModelSolver.addClause(new VecInt(clause));
					currentFeatureModelSolver.addClause(new VecInt(new int[] { optionToIntMap.get(f1) }));
					currentFeatureModelSolver.addClause(new VecInt(new int[] { optionToIntMap.get(f2) }));
					newSat = currentFeatureModelSolver.isSatisfiable();
				} catch (ContradictionException e) {
					newSat = false;
				} catch (TimeoutException e) {
					newSat = false;
				}

				if (!oldSat && newSat) {
					// feature interaction is new
					Feature[] hint = new Feature[] { f1, f2 };
					this.hints.add(hint);
					newHints.add(hint);
					System.out.println("ADDED HINT: " + f1 + " / " + f2);
				}
			}
		}

		// POSSIBLE IMPROVEMENT: suggest configuration(s) that cover(s) as many hints as possible, or with as few other features as possible.

		// POSSIBLE IMPROVEMENT: also consider feature interactions with negative features.

		return new Result(newHints);
	}

}
