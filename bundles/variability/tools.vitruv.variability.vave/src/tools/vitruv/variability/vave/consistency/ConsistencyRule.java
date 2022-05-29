package tools.vitruv.variability.vave.consistency;

import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.FeatureModel;
import vavemodel.Configuration;
import vavemodel.Expression;
import vavemodel.FeatureOption;
import vavemodel.SystemRevision;

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
	
	public default ConsistencyResult internalizeChangesPre(Expression<FeatureOption> expression) {
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
