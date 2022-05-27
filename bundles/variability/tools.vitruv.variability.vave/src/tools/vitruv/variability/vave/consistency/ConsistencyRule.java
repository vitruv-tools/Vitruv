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

	public default void externalizeProductPre(Configuration configuration) {}
	
	public default void externalizeProductPost() {}
	
	public default void internalizeChangesPre(Expression<FeatureOption> expression) {}
	
	public default void internalizeChangesPost(VirtualVaVeModel vave, SystemRevision newSystemRevision) {}
	
	public default void externalizeDomainPre() {}
	
	public default void externalizeDomainPost() {}
	
	public default void internalizeDomainPre() {}
	
	public default void internalizeDomainPost(SystemRevision newSystemRevision, FeatureModel oldFeatureModel, FeatureModel newFeatureModel) {}
}
