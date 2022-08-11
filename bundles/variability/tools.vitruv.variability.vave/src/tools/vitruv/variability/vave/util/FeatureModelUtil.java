package tools.vitruv.variability.vave.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;
import tools.vitruv.variability.vave.model.featuremodel.ViewCrossTreeConstraint;
import tools.vitruv.variability.vave.model.featuremodel.ViewFeature;
import tools.vitruv.variability.vave.model.featuremodel.ViewTreeConstraint;
import tools.vitruv.variability.vave.model.vave.Configuration;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureOption;
import tools.vitruv.variability.vave.model.vave.FeatureRevision;
import tools.vitruv.variability.vave.model.vave.GroupType;
import tools.vitruv.variability.vave.model.vave.Option;
import tools.vitruv.variability.vave.model.vave.SystemRevision;

public final class FeatureModelUtil {

	private FeatureModelUtil() {
	}

	public static Collection<int[]> computeClauses(FeatureModel featureModel, Map<FeatureOption, Integer> featureOptionToIntMap) {
		Collection<int[]> fmClauses = new ArrayList<>();
		for (Option fo : featureOptionToIntMap.keySet()) {
			if (fo instanceof FeatureRevision) {
				// add clause that says "feature revision implies feature"
				// also the other way round?
				// this is not needed!
			} else if (fo instanceof Feature) {
				// do nothing?
				// do nothing!
			}
		}
		// root feature
		if (featureModel.getRootFeatures().isEmpty())
			throw new IllegalStateException("No root feature in feature model.");
		else {
			for (ViewFeature viewFeature : featureModel.getRootFeatures()) {
				if (featureOptionToIntMap.containsKey(viewFeature.getOriginalFeature()))
					fmClauses.add(new int[] { featureOptionToIntMap.get(viewFeature.getOriginalFeature()) });
				else
					System.out.println("WARNING: no literal for root feature!");
			}
		}
		for (ViewTreeConstraint tc : collectTreeConstraints(featureModel)) {
			// first, children imply parents
			for (ViewFeature feature : tc.getChildFeatures()) {
				fmClauses.add(new int[] { -featureOptionToIntMap.get(feature.getOriginalFeature()), featureOptionToIntMap.get(tc.getOriginalTreeConstraint().getParentFeature()) });
			}
			// second, depending on tc type, add relation between siblings and/or parent
			if (tc.getOriginalTreeConstraint().getType() == GroupType.OPTIONAL) { // ORNONE
				// do nothing
			} else if (tc.getOriginalTreeConstraint().getType() == GroupType.MANDATORY) { // all
				// parents imply children
				for (ViewFeature feature : tc.getChildFeatures()) {
					ArrayList<Integer> literals = new ArrayList<>();
					literals.add(-featureOptionToIntMap.get(tc.getOriginalTreeConstraint().getParentFeature()));
					literals.add(featureOptionToIntMap.get(feature.getOriginalFeature()));
					fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
				}
			} else if (tc.getOriginalTreeConstraint().getType() == GroupType.OR) { // at least one
				// parent implies at least one child
				ArrayList<Integer> literals = new ArrayList<>();
				literals.add(-featureOptionToIntMap.get(tc.getOriginalTreeConstraint().getParentFeature()));
				for (ViewFeature feature : tc.getChildFeatures())
					literals.add(featureOptionToIntMap.get(feature.getOriginalFeature()));
				fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
			} else if (tc.getOriginalTreeConstraint().getType() == GroupType.ALTERNATIVE) { // XOR. !D v !E // exactly one. mandatory or alternative group
				// siblings exclude each other
				for (int i = 0; i < tc.getChildFeatures().size(); i++) {
					for (int j = i + 1; j < tc.getChildFeatures().size(); j++) {
						ArrayList<Integer> literals = new ArrayList<>();
						literals.add(-featureOptionToIntMap.get(tc.getChildFeatures().get(i).getOriginalFeature()));
						literals.add(-featureOptionToIntMap.get(tc.getChildFeatures().get(j).getOriginalFeature()));
						fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
					}
				}
				// parent implies at least one child
				ArrayList<Integer> literals = new ArrayList<>();
				literals.add(-featureOptionToIntMap.get(tc.getOriginalTreeConstraint().getParentFeature()));
				for (ViewFeature feature : tc.getChildFeatures()) {
					literals.add(featureOptionToIntMap.get(feature.getOriginalFeature()));
				}
				fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
			}
//			else if (tc.getOriginalTreeConstraint().getType() == GroupType.XORNONE) { // at most one, no parent-child relation required
//				for (int i = 0; i < tc.getChildFeatures().size(); i++) {
//					for (int j = i + 1; j < tc.getChildFeatures().size(); j++) {
//						ArrayList<Integer> literals = new ArrayList<>();
//						literals.add(-featureOptionToIntMap.get(tc.getChildFeatures().get(i)));
//						literals.add(-featureOptionToIntMap.get(tc.getChildFeatures().get(j)));
//						fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
//					}
//				}
//			}
		}
		for (ViewCrossTreeConstraint ctc : featureModel.getCrossTreeConstraints()) {
			// add clause for ctc
			ExpressionToSATConverter e2sc = new ExpressionToSATConverter();
			e2sc.setIntsForOptions(featureOptionToIntMap.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
			Collection<int[]> ctcCnf = e2sc.convertExpr2Sat(ctc.getExpression());
			fmClauses.addAll(ctcCnf);
		}
		return fmClauses;
	}

	private static Collection<ViewTreeConstraint> collectTreeConstraints(FeatureModel featureModel) {
		Collection<ViewTreeConstraint> viewTreeConstraints = new ArrayList<>();
		for (ViewFeature viewFeature : featureModel.getRootFeatures())
			collectTreeConstraintsRec(viewFeature, viewTreeConstraints);
		return viewTreeConstraints;
	}

	private static void collectTreeConstraintsRec(ViewFeature viewFeature, Collection<ViewTreeConstraint> viewTreeConstraints) {
		for (ViewTreeConstraint viewTreeConstraint : viewFeature.getChildTreeConstraints()) {
			viewTreeConstraints.add(viewTreeConstraint);
			for (ViewFeature childViewFeature : viewTreeConstraint.getChildFeatures()) {
				collectTreeConstraintsRec(childViewFeature, viewTreeConstraints);
			}
		}
	}

	private static Set<FeatureOption> collectFeatureOptions(FeatureModel featureModel) {
		Set<FeatureOption> featureOptions = new HashSet<>();
		for (ViewFeature viewFeature : featureModel.getRootFeatures())
			collectFeaturesRec(viewFeature, featureOptions);
		for (ViewCrossTreeConstraint viewCrossTreeConstraint : featureModel.getCrossTreeConstraints())
			featureOptions.addAll(OptionUtil.collect(viewCrossTreeConstraint.getExpression()));
		return featureOptions;
	}

	private static void collectFeaturesRec(ViewFeature viewFeature, Set<FeatureOption> featureOptions) {
		featureOptions.add(viewFeature.getOriginalFeature());
		featureOptions.addAll(viewFeature.getOriginalRevisions());
		for (ViewTreeConstraint viewTreeConstraint : viewFeature.getChildTreeConstraints()) {
			for (ViewFeature childViewFeature : viewTreeConstraint.getChildFeatures()) {
				collectFeaturesRec(childViewFeature, featureOptions);
			}
		}
	}

	/**
	 * Checks whether a configuration is complete according to a feature model.
	 */
	public static boolean isComplete(FeatureModel featureModel, Configuration configuration) {
		// at least one system revision
		List<SystemRevision> sysrevs = configuration.getOptions().stream().filter(o -> o instanceof SystemRevision).map(sr -> (SystemRevision) sr).collect(Collectors.toList());
		if (sysrevs.isEmpty())
			return false;

		// since our implementation does not yet require partial configurations, any feature that is not explicitly selected is assumed deselected
		// this means that there are no negative (i.e., deselected) features in configurations, and we do not need to make sure that no feature revisions are selected for deselected features
		// finally, we do not require features to be selected. instead, we assume every feature to be selected of which at least one feature revision is selected in the configuration

		// in summary: there is currently not much to do here.

		return true;
	}

	/**
	 * Checks whether a configuration is valid according to a feature model.
	 */
	public static boolean isValid(FeatureModel featureModel, Configuration configuration) {
		Collection<FeatureOption> featureOptions = collectFeatureOptions(featureModel);

		int currentInt = 0;
		Map<FeatureOption, Integer> optionToIntMap = new HashMap<>();
		for (FeatureOption fo : featureOptions) {
			if (fo instanceof Feature) {
				optionToIntMap.put(fo, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE " + fo);
			} else if (fo instanceof FeatureRevision) {
				optionToIntMap.put(fo, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE REVISION " + fo);
			}
		}

		Collection<int[]> clauses = computeClauses(featureModel, optionToIntMap);

		ISolver solver = SolverFactory.newDefault();
		try {
			for (int[] clause : clauses)
				solver.addClause(new VecInt(clause));

			// for every option in configuration
			for (Option option : configuration.getOptions()) {
				// make sure it exists in the feature model
				if (!optionToIntMap.containsKey(option))
					throw new RuntimeException("There is an option in configuration that is not in feature model.");

				if (option instanceof Feature || option instanceof FeatureRevision) {
					int literal = optionToIntMap.get(option);
					solver.addClause(new VecInt(new int[] { literal }));
					if (option instanceof FeatureRevision) {
						FeatureRevision featureRevision = (FeatureRevision) option;
						Feature feature = (Feature) featureRevision.eContainer();
						if (optionToIntMap.containsKey(feature)) {
							int literal2 = optionToIntMap.get(feature);
							solver.addClause(new VecInt(new int[] { literal2 }));
						} else {
							return false;
						}
					}
				}
			}

			boolean sat = solver.isSatisfiable();
			if (!sat)
				return false;
		} catch (ContradictionException e) {
			return false;
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

	/**
	 * Checks whether an expression is valid according to a feature model.
	 */
	public static <T extends Option> boolean isValid(FeatureModel featureModel, Expression<T> expression) {
		Collection<FeatureOption> featureOptions = collectFeatureOptions(featureModel);

		int currentInt = 0;
		Map<FeatureOption, Integer> optionToIntMap = new HashMap<>();
		for (FeatureOption featureOption : featureOptions) {
			if (featureOption instanceof Feature) {
				optionToIntMap.put(featureOption, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(featureOption) + " TO FEATURE " + featureOption);
			} else if (featureOption instanceof FeatureRevision) {
				optionToIntMap.put(featureOption, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(featureOption) + " TO FEATURE REVISION " + featureOption);
			}
		}

		Collection<int[]> clauses = computeClauses(featureModel, optionToIntMap);

		ISolver solver = SolverFactory.newDefault();
		try {
			for (int[] clause : clauses)
				solver.addClause(new VecInt(clause));

			// for every option in expression make sure it exists in the feature model
			Collection<T> expressionOptions = OptionUtil.collect(expression);
			for (Option option : expressionOptions)
				if (!optionToIntMap.containsKey(option))
					throw new RuntimeException("There is an option in expression that is not in feature model.");

			ExpressionToSATConverter e2satconv = new ExpressionToSATConverter();
			e2satconv.setIntsForOptions(new HashMap<>(optionToIntMap));
			Collection<int[]> expressionClauses = e2satconv.convertExpr2Sat(expression);
			for (int[] clause : expressionClauses)
				solver.addClause(new VecInt(clause));

			boolean sat = solver.isSatisfiable();
			if (!sat)
				return false;
		} catch (ContradictionException e) {
			return false;
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

}
