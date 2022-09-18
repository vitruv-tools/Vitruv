package tools.vitruv.variability.vave.consistency;

import java.util.ArrayList;
import java.util.Collection;

import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.vave.Configuration;
import tools.vitruv.variability.vave.model.vave.FeatureOption;
import tools.vitruv.variability.vave.util.ExpressionUtil;

/**
 * Performs consistency preservation from solution space (changed product) to solution space (affected products by that change) by computing which existing configurations are affected by the changes and should be checked by the developer.
 */
public class AffectedConfigurations implements ConsistencyRule {

	public class Result implements ConsistencyResult {
		private Collection<Configuration> affectedConfigurations;

		public Result(Collection<Configuration> affectedConfigurations) {
			this.affectedConfigurations = affectedConfigurations;
		}
		
		public Collection<Configuration> getAffectedConfigurations() {
			return this.affectedConfigurations;
		}
	}

	/**
	 * Checks for every existing configuration of the unified system if it is affected by the performed changes (that affect the feature options of the expression). A configuration is affected by the expression if all (negative) feature revisions in the expression are (de)selected in the configuration and for all features in the expression any revision is selected in the configuration.
	 */
	@Override
	public ConsistencyResult internalizeChangesPre(VirtualVaVeModel vave, Expression<FeatureOption> expression) {
		Collection<Configuration> affectedConfigurations = new ArrayList<>();
		for (Configuration configuration : vave.getSystem().getConfigurations()) {

			if (expression == null || ExpressionUtil.eval(expression, configuration)) {
				affectedConfigurations.add(configuration);
			}
		}
		return new Result(affectedConfigurations);
	}
}
