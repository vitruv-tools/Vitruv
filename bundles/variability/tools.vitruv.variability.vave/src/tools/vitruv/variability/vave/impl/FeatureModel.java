package tools.vitruv.variability.vave.impl;

import java.util.HashSet;
import java.util.Set;

import vavemodel.CrossTreeConstraint;
import vavemodel.Feature;
import vavemodel.FeatureOption;
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

}
