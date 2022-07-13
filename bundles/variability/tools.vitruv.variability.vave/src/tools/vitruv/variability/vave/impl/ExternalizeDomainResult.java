package tools.vitruv.variability.vave.impl;

import tools.vitruv.variability.vave.OperationResult;
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;

public class ExternalizeDomainResult extends OperationResult<FeatureModel> {

	private FeatureModel featureModel;

	public ExternalizeDomainResult(FeatureModel featureModel) {
		if (featureModel == null)
			throw new RuntimeException("Resulting feature model must not be null.");
		this.featureModel = featureModel;
	}

	@Override
	public FeatureModel getResult() {
		return this.featureModel;
	}

	@Override
	public void setResult(FeatureModel featureModel) {
		this.featureModel = featureModel;
	}
}
