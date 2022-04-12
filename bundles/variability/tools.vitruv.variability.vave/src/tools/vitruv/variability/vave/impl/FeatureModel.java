package tools.vitruv.variability.vave.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import tools.vitruv.variability.vave.util.ExpressionToSATConverter;
import vavemodel.Configuration;
import vavemodel.CrossTreeConstraint;
import vavemodel.Expression;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.FeatureRevision;
import vavemodel.GroupType;
import vavemodel.Option;
import vavemodel.SystemRevision;
import vavemodel.TreeConstraint;

public class FeatureModel {
	private Feature rootFeature;
	private final SystemRevision sysrev;
	private final Set<FeatureOption> featureOptions = new HashSet<FeatureOption>();
	private final Set<TreeConstraint> treeConstraints = new HashSet<TreeConstraint>();
	private final Set<CrossTreeConstraint> crossTreeConstraints = new HashSet<CrossTreeConstraint>();

	public FeatureModel(Feature rootfeature, SystemRevision sysrev, Set<FeatureOption> featureOptions, Set<TreeConstraint> treeConstraints, Set<CrossTreeConstraint> crossTreeConstraints) {
		this.rootFeature = rootfeature;
		this.sysrev = sysrev;
		this.featureOptions.addAll(featureOptions);
		this.treeConstraints.addAll(treeConstraints);
		this.crossTreeConstraints.addAll(crossTreeConstraints);
	}

	public Feature getRootFeature() {
		return rootFeature;
	}

	public void setRootFeature(Feature rootFeature) {
		this.rootFeature = rootFeature;
	}

	public SystemRevision getSysrev() {
		return sysrev;
	}

	public Set<FeatureOption> getFeatureOptions() {
		return featureOptions;
	}

	public Set<TreeConstraint> getTreeConstraints() {
		return treeConstraints;
	}

	public Set<CrossTreeConstraint> getCrossTreeConstraints() {
		return crossTreeConstraints;
	}

	public Collection<int[]> computeClauses(Map<FeatureOption, Integer> featureOptionToIntMap) {
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
		if (this.rootFeature != null) {
			if (featureOptionToIntMap.containsKey(this.rootFeature))
				fmClauses.add(new int[] { featureOptionToIntMap.get(this.rootFeature) });
			else
				System.out.println("WARNING: no literal for root feature!");
		}
		for (TreeConstraint tc : this.getTreeConstraints()) {
			// first, children imply parents
			for (Feature feature : tc.getFeature()) {
				fmClauses.add(new int[] { -featureOptionToIntMap.get(feature), featureOptionToIntMap.get(tc.eContainer()) });
			}
			// second, depending on tc type, add relation between siblings and/or parent
			if (tc.getType() == GroupType.ORNONE) {
				// do nothing (only parent implies child)
			} else if (tc.getType() == GroupType.OR) {
				ArrayList<Integer> parentchild = new ArrayList<>();
				ArrayList<Integer> siblings = new ArrayList<>();
				parentchild.add(-featureOptionToIntMap.get(tc.eContainer()));
				for (Feature feature : tc.getFeature()) {
					parentchild.add(featureOptionToIntMap.get(feature));
//					siblings.add(featureOptionToIntMap.get(feature));
				}
				fmClauses.add(parentchild.stream().mapToInt(val -> val).toArray());
//				fmClauses.add(siblings.stream().mapToInt(val -> val).toArray());
			} else if (tc.getType() == GroupType.XOR) { // !D v !E // mandatory or alternative group
				for (int i = 0; i < tc.getFeature().size(); i++) {
					for (int j = i + 1; j < tc.getFeature().size(); j++) {
						ArrayList<Integer> literals = new ArrayList<>();
						literals.add(-featureOptionToIntMap.get(tc.getFeature().get(i)));
						literals.add(-featureOptionToIntMap.get(tc.getFeature().get(j)));
						fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
					}
				}
				ArrayList<Integer> literals = new ArrayList<>();
				literals.add(-featureOptionToIntMap.get(tc.eContainer()));
				for (Feature feature : tc.getFeature()) {
					literals.add(featureOptionToIntMap.get(feature));
				}
				fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
			} else if (tc.getType() == GroupType.XORNONE) { // at most one, no parent-child relation required
				for (int i = 0; i < tc.getFeature().size(); i++) {
					for (int j = i + 1; j < tc.getFeature().size(); j++) {
						ArrayList<Integer> literals = new ArrayList<>();
						literals.add(-featureOptionToIntMap.get(tc.getFeature().get(i)));
						literals.add(-featureOptionToIntMap.get(tc.getFeature().get(j)));
						fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
					}
				}
			}
		}
		for (CrossTreeConstraint ctc : this.getCrossTreeConstraints()) {
			// add clause for ctc
			ExpressionToSATConverter e2sc = new ExpressionToSATConverter();
			e2sc.setIntsForOptions(featureOptionToIntMap.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
			Collection<int[]> ctcCnf = e2sc.convertExpr2Sat(ctc.getExpression());
			fmClauses.addAll(ctcCnf);
		}
		return fmClauses;
	}

	public boolean isComplete(Configuration configuration) {
		// at least one system revision
		List<SystemRevision> sysrevs = configuration.getOption().stream().filter(o -> o instanceof SystemRevision).map(sr -> (SystemRevision) sr).collect(Collectors.toList());
		if (sysrevs.isEmpty())
			return false;

		// since our implementation does not yet require partial configurations, any feature that is not explicitly selected is assumed deselected
		// this means that there are no negative (i.e., deselected) features in configurations, and we do not need to make sure that no feature revisions are selected for deselected features
		// finally, we do not require features to be selected. instead, we assume every feature to be selected of which at least one feature revision is selected in the configuration

		// in summary: there is currently not much to do here.

		return true;
	}

	public boolean isValid(Configuration configuration) {
		int currentInt = 0;
		Map<FeatureOption, Integer> optionToIntMap = new HashMap<>();
		for (FeatureOption fo : this.featureOptions) {
			if (fo instanceof Feature) {
				optionToIntMap.put(fo, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE " + fo);
			} else if (fo instanceof FeatureRevision) {
				optionToIntMap.put(fo, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE REVISION " + fo);
			}
		}

		Collection<int[]> clauses = this.computeClauses(optionToIntMap);

		ISolver solver = SolverFactory.newDefault();
		try {
			for (int[] clause : clauses)
				solver.addClause(new VecInt(clause));
			for (Option option : configuration.getOption()) {
				// find corresponding option from feature model
				if (option instanceof Feature) {
					final Feature feature = (Feature) option;
					Optional<Feature> featureOptional = this.featureOptions.stream().filter(o -> o instanceof Feature).map(f -> (Feature) f).filter(f -> f.getName().equals(feature.getName())).findAny();
					if (featureOptional.isPresent()) {
						option = featureOptional.get();
					} else {
						return false;
					}
				} else if (option instanceof FeatureRevision) {
					final FeatureRevision featureRevision = (FeatureRevision) option;
					Optional<FeatureRevision> featureRevisionOptional = this.featureOptions.stream().filter(o -> o instanceof FeatureRevision).map(fr -> (FeatureRevision) fr).filter(fr -> fr.getRevisionID() == featureRevision.getRevisionID() && ((Feature) featureRevision.eContainer()).getName().equals(((Feature) fr.eContainer()).getName())).findAny();
					if (featureRevisionOptional.isPresent()) {
						option = featureRevisionOptional.get();
					} else {
						return false;
					}
				}
				if (option instanceof Feature || option instanceof FeatureRevision) {
					if (optionToIntMap.containsKey(option)) {
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
					} else {
						return false;
					}
				}
			}
			boolean sat = solver.isSatisfiable();
			if (!sat) {
				return false;
			}
		} catch (ContradictionException e) {
			return false;
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

	public boolean isValid(Expression<? extends Option> expression) {
		int currentInt = 0;
		Map<FeatureOption, Integer> optionToIntMap = new HashMap<>();
		for (FeatureOption fo : this.featureOptions) {
			if (fo instanceof Feature) {
				optionToIntMap.put(fo, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE " + fo);
			} else if (fo instanceof FeatureRevision) {
				optionToIntMap.put(fo, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE REVISION " + fo);
			}
		}

		Collection<int[]> clauses = this.computeClauses(optionToIntMap);

		ISolver solver = SolverFactory.newDefault();
		try {
			for (int[] clause : clauses)
				solver.addClause(new VecInt(clause));

			ExpressionToSATConverter e2satconv = new ExpressionToSATConverter();
			e2satconv.setIntsForOptions(new HashMap<>(optionToIntMap));
			Collection<int[]> expressionClauses = e2satconv.convertExpr2Sat(expression);
			for (int[] clause : expressionClauses) {
				solver.addClause(new VecInt(clause));
			}

			boolean sat = solver.isSatisfiable();
			if (!sat) {
				return false;
			}
		} catch (ContradictionException e) {
			return false;
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}

}
