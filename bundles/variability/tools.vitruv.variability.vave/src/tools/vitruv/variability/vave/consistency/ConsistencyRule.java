package tools.vitruv.variability.vave.consistency;

import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;
import tools.vitruv.variability.vave.model.vave.Configuration;
import tools.vitruv.variability.vave.model.vave.FeatureOption;
import tools.vitruv.variability.vave.model.vave.SystemRevision;

/**
 * Generic interface for consistency rules that contains methods that are triggered before and after each view-based vave operation.
 */
public interface ConsistencyRule {

	public default ConsistencyResult externalizeProductPre(Configuration configuration) {
		return null;
	}

	public default ConsistencyResult externalizeProductPost() {
		return null;
	}

	public default ConsistencyResult internalizeChangesPre(VirtualVaVeModel vave, Expression<FeatureOption> expression) {
		return null;
	}

	public default ConsistencyResult internalizeChangesPost(VirtualVaVeModel vave, SystemRevision newSystemRevision) {
		return null;
	}

	public default ConsistencyResult externalizeDomainPre() {
		return null;
	}

	public default ConsistencyResult externalizeDomainPost() {
		return null;
	}

	public default ConsistencyResult internalizeDomainPre() {
		return null;
	}

	public default ConsistencyResult internalizeDomainPost(SystemRevision newSystemRevision, FeatureModel oldFeatureModel, FeatureModel newFeatureModel) {
		return null;
	}
}
