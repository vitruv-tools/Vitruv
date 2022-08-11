package tools.vitruv.variability.vave.consistency;

import java.util.ArrayList;
import java.util.Collection;

import tools.vitruv.variability.vave.VirtualVaVeModel;
import vavemodel.Configuration;
import vavemodel.Expression;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.FeatureRevision;
import vavemodel.Option;
import vavemodel.Variable;

/**
 * Performs consistency preservation from solution space (changed product) to solution space (affected products by that change) by computing which existing configurations are affected by the changes and should be checked by the developer.
 */
public class AffectedConfigurations implements ConsistencyRule {

	public class Result implements ConsistencyResult {
		private Collection<Configuration> affectedConfigurations;

		public Result(Collection<Configuration> affectedConfigurations) {
			this.affectedConfigurations = affectedConfigurations;
		}
	}

	@Override
	public ConsistencyResult internalizeChangesPre(VirtualVaVeModel vave, Expression<FeatureOption> expression) {
		Collection<Configuration> affectedConfigurations = new ArrayList<>();
		// check for every existing configuration of the unified system if it is affected by the performed changes (that affect the feature options of the expression)
		for (Configuration configuration : vave.getSystem().getConfiguration()) {
			// A configuration is affected by the expression if all (negative) feature revisions in the expression are (de)selected in the configuration and for all features in the expression any revision is selected in the configuration.
			ExpressionEvaluator ee = new ExpressionEvaluator(configuration) {
				@Override
				public <T extends Option> Boolean caseVariable(Variable<T> variable) {
					// for feature revisions: check if the configuration contains this feature revision or a newer one
					// for feature: check if the configuration contains this feature or any revision of it
					return this.configuration.getOption().contains(variable.getOption()) || variable.getOption() instanceof Feature && this.configuration.getOption().stream().filter(o -> o instanceof FeatureRevision && ((FeatureRevision) o).eContainer().equals(variable.getOption())).findAny().isPresent()
							|| variable.getOption() instanceof FeatureRevision && this.configuration.getOption().stream().filter(o -> o instanceof FeatureRevision && ((FeatureRevision) o).eContainer().equals(((FeatureRevision) variable.getOption()).eContainer()) && ((FeatureRevision) o).getRevisionID() >= ((FeatureRevision) variable.getOption()).getRevisionID()).findAny().isPresent();
				}
			};
			if (expression == null || ee.eval(expression)) {
				affectedConfigurations.add(configuration);
			}
		}
		return new Result(affectedConfigurations);
	}
}
