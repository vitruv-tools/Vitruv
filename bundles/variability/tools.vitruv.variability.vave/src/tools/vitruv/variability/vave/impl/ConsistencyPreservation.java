package tools.vitruv.variability.vave.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

import tools.vitruv.framework.change.echange.EChange;
import vavemodel.CrossTreeConstraint;
import vavemodel.DeltaModule;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.FeatureRevision;
import vavemodel.GroupType;
import vavemodel.Mapping;
import vavemodel.SystemRevision;
import vavemodel.TreeConstraint;

public class ConsistencyPreservation {

	public void liftingDependenciesBetweenFeatures(VirtualVaVeModeIImpl vave, SystemRevision sysrev) throws Exception {

		// After every internalizeChanges, retrieve dependencies between deltas of vave model
		// If dependencies have been detected, find mapped features of deltas
		// check if features already depend on problem space (via implication or exclusion)
		// otherwise, add implication of depending features

		ISolver solver = SolverFactory.newDefault();

		// start by translating the feature model at given system revision to sat
		FeatureModel fm = vave.externalizeDomain(sysrev);
		Set<TreeConstraint> treeconstr = fm.getTreeConstraints();
		Set<CrossTreeConstraint> crosstreeconstr = fm.getCrossTreeConstraints();
		Map<FeatureOption, Integer> featureOptionToIntMap = new HashMap<>();
		int i = 0;
		for (FeatureOption fo : fm.getFeatureOptions()) {
			featureOptionToIntMap.put(fo, i);
			i++;
		}
		for (FeatureOption fo : featureOptionToIntMap.keySet()) {
			if (fo instanceof FeatureRevision) {
				// add clause that says "feature revision implies feature"
				// also the other way round?
				// TODO
			} else if (fo instanceof Feature) {
				// do nothing?
				// TODO
			}
		}
		for (TreeConstraint tc : treeconstr) {
			// first, children imply parents
			for (Feature feature : tc.getFeature()) {
				solver.addClause(new VecInt(new int[] { -featureOptionToIntMap.get(feature), featureOptionToIntMap.get(tc.eContainer()) }));
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
					siblings.add(featureOptionToIntMap.get(feature));
				}
				solver.addClause(new VecInt(parentchild.stream().mapToInt(val -> val).toArray()));
				solver.addClause(new VecInt(siblings.stream().mapToInt(val -> val).toArray()));
			} else if (tc.getType() == GroupType.XOR) { //!D v !E // mandatory or alternative group
				ArrayList<Integer> parentchild = new ArrayList<>();
				ArrayList<Integer> siblings = new ArrayList<>();
				parentchild.add(-featureOptionToIntMap.get(tc.eContainer()));
				for (Feature feature : tc.getFeature()) {
					parentchild.add(featureOptionToIntMap.get(feature));
					siblings.add(-featureOptionToIntMap.get(feature));
				}
				solver.addClause(new VecInt(parentchild.stream().mapToInt(val -> val).toArray()));
				solver.addClause(new VecInt(siblings.stream().mapToInt(val -> val).toArray()));
				
			} else if (tc.getType() == GroupType.XORNONE) { // at most one, no parent-child relation required
				ArrayList<Integer> siblings = new ArrayList<>();
				for (Feature feature : tc.getFeature()) {
					siblings.add(-featureOptionToIntMap.get(feature));
				}
				solver.addClause(new VecInt(siblings.stream().mapToInt(val -> val).toArray()));
			}
		}
		for (CrossTreeConstraint ctc : crosstreeconstr) {
			// add clause for ctc
			// TODO: traverse expression and generate clauses
		}

		// compute clauses for mappings
		Map<Mapping, List<int[]>> mappingToClauses = new HashMap<>();
		for (Mapping mapping : vave.getSystem().getMapping()) {
			// TODO
		}

		// compute dependencies between mappings based on their fragments
		Map<Mapping, Mapping> requires = new HashMap<>();
		Map<Mapping, Mapping> excludes = new HashMap<>();
		for (Mapping mapping : vave.getSystem().getMapping()) {
			for (DeltaModule deltaModule : mapping.getDeltamodule()) {
				for (EChange change : deltaModule.getChange()) {
					// TODO: for every change, check its type, affected value, new value, old value, etc.
					// TODO: traverse every mapping, delta module, change, and check to which there are dependencies
					// example: this change in mapping M1 adds statement to method, other change in other mapping M2 adds method. then M1 requires M2.
				}
			}
		}

		// for every dependency, clone sat solver instance, add respective clauses, and check if it is satisfiable
		for (Entry<Mapping, Mapping> entry : requires.entrySet()) {
			// TODO
		}
		for (Entry<Mapping, Mapping> entry : excludes.entrySet()) {
			// TODO
		}

	}

}
