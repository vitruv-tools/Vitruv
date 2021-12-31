package tools.vitruv.variability.vave.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import tools.vitruv.variability.vave.util.ExpressionToSATConverter;
import vavemodel.CrossTreeConstraint;
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

}
