package tools.vitruv.variability.vave.consistency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;
import vavemodel.Configuration;
import vavemodel.Expression;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.FeatureRevision;
import vavemodel.Option;
import vavemodel.SystemRevision;

/**
 * Performs consistency preservation from problem space (feature model) to solution space (product) by computing hints for not yet implemented features or potential pair-wise feature interactions.
 */
public class HintComputation implements ConsistencyRule {

	public class Result implements ConsistencyResult {
		private Collection<Feature[]> hints;

		public Result(Collection<Feature[]> hints) {
			this.hints = hints;
		}
	}

	private Collection<Feature[]> hints = new ArrayList<>();

	@Override
	public ConsistencyResult externalizeProductPre(Configuration config) {
		Collection<Feature[]> hints = new ArrayList<>();

		// check if any of the hinted feature interactions are contained in the configuration. if yes, then add a hint to the product.
		for (Feature[] featureInteraction : this.hints) {
			if (config.getOption().containsAll(Arrays.asList(featureInteraction))) {
				hints.add(featureInteraction);
				System.out.println("RELEVANT HINT: " + Arrays.asList(featureInteraction));
			}
		}

		return new Result(hints);
	}

	@Override
	public ConsistencyResult internalizeChangesPre(VirtualVaVeModel vave, Expression<FeatureOption> expr) {
		Collection<Option> options = new OptionsCollector().doSwitch(expr);

		Collection<Feature[]> removedHints = new ArrayList<>();

		// check if the expression covers any hints. if yes, then delete the respective hints.
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

	@Override
	public ConsistencyResult internalizeDomainPost(SystemRevision newSystemRevision, FeatureModel fm_prev, FeatureModel fm_cur) {
		// inconsistency type 2 cause

		// obtain feature model at previous system revision
		// FeatureModel fm_prev = fmAtOldSysrev;
		// obtain new feature model
		// FeatureModel fm_cur = fm;

		Collection<FeatureOption> enabledFOs = newSystemRevision.getEnablesoptions();
		List<Feature> enabledFs = enabledFOs.stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).collect(Collectors.toList());

		int currentInt = 0;
		Map<Option, Integer> optionToIntMap = new HashMap<>();
		for (FeatureOption fo : enabledFOs) {
			if (fo instanceof Feature) {
				optionToIntMap.put(fo, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE " + fo);
			}
		}
		for (FeatureOption fo : enabledFOs) {
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

		Set<Option> notInOldFM = new HashSet<>();
		Collection<int[]> oldClauses = null;
		if (fm_prev == null) {
			oldClauses = new ArrayList<>();
			notInOldFM.addAll(enabledFOs);
		} else {
			Map<FeatureOption, Integer> oldfmOptionToIntMap = new HashMap<>();
			for (Entry<Option, Integer> entry : optionToIntMap.entrySet()) {
				if (entry.getKey() instanceof Feature) {
					Optional<Feature> fmFeature = fm_prev.getFeatureOptions().stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).filter(f -> f.getName().equals(((Feature) entry.getKey()).getName())).findAny();
					if (fmFeature.isPresent())
						oldfmOptionToIntMap.put(fmFeature.get(), entry.getValue());
					else
						notInOldFM.add(entry.getKey());
				} else if (entry.getKey() instanceof FeatureRevision) {
					Optional<FeatureRevision> fmFeatureRevision = fm_prev.getFeatureOptions().stream().filter(fo -> fo instanceof FeatureRevision).map(fr -> (FeatureRevision) fr).filter(fr -> ((Feature) fr.eContainer()).getName().equals(((Feature) ((FeatureRevision) entry.getKey()).eContainer()).getName()) && fr.getRevisionID() == ((FeatureRevision) entry.getKey()).getRevisionID()).findAny();
					if (fmFeatureRevision.isPresent())
						oldfmOptionToIntMap.put(fmFeatureRevision.get(), entry.getValue());
					else
						notInOldFM.add(entry.getKey());
				}
			}
			oldClauses = fm_prev.computeClauses(oldfmOptionToIntMap);
		}

		Map<FeatureOption, Integer> newfmOptionToIntMap = new HashMap<>();
		for (Entry<Option, Integer> entry : optionToIntMap.entrySet()) {
			if (entry.getKey() instanceof Feature) {
				Feature fmFeature = fm_cur.getFeatureOptions().stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).filter(f -> f.getName().equals(((Feature) entry.getKey()).getName())).findAny().get();
				newfmOptionToIntMap.put(fmFeature, entry.getValue());
			} else if (entry.getKey() instanceof FeatureRevision) {
				FeatureRevision fmFeatureRevision = fm_cur.getFeatureOptions().stream().filter(fo -> fo instanceof FeatureRevision).map(fr -> (FeatureRevision) fr).filter(fr -> ((Feature) fr.eContainer()).getName().equals(((Feature) ((FeatureRevision) entry.getKey()).eContainer()).getName()) && fr.getRevisionID() == ((FeatureRevision) entry.getKey()).getRevisionID()).findAny().get();
				newfmOptionToIntMap.put(fmFeatureRevision, entry.getValue());
			}
		}
		Collection<int[]> newClauses = fm_cur.computeClauses(newfmOptionToIntMap);

		Collection<Feature[]> newHints = new ArrayList<>();

//		for (Feature f1 : enabledFs) {
//			for (Feature f2 : enabledFs) {
		for (int i = 0; i < enabledFs.size(); i++) {
			Feature f1 = enabledFs.get(i);
			for (int j = i; j < enabledFs.size(); j++) {
				Feature f2 = enabledFs.get(j);
				ISolver oldFMSolver = SolverFactory.newDefault();
				ISolver newFMSolver = SolverFactory.newDefault();
				boolean oldSat = false;
				boolean newSat = false;

				try {
					if (notInOldFM.contains(f1) || notInOldFM.contains(f2)) {
						oldSat = false;
					} else {
						for (int[] clause : oldClauses)
							oldFMSolver.addClause(new VecInt(clause));
						oldFMSolver.addClause(new VecInt(new int[] { optionToIntMap.get(f1) }));
						oldFMSolver.addClause(new VecInt(new int[] { optionToIntMap.get(f2) }));
						oldSat = oldFMSolver.isSatisfiable();
					}
				} catch (ContradictionException e) {
					oldSat = false;
				} catch (TimeoutException e) {
					oldSat = false;
				}

				try {
					for (int[] clause : newClauses)
						newFMSolver.addClause(new VecInt(clause));
					newFMSolver.addClause(new VecInt(new int[] { optionToIntMap.get(f1) }));
					newFMSolver.addClause(new VecInt(new int[] { optionToIntMap.get(f2) }));
					newSat = newFMSolver.isSatisfiable();
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
					// System.out.println("ADDED HINT: " + f1 + " / " + f2);
				}
			}
		}

		// TODO: suggest configuration(s) that cover(s) as many hints as possible? or with as few other features as possible?

		// TODO: also consider feature interactions with negative features?

		return new Result(newHints);
	}

}
