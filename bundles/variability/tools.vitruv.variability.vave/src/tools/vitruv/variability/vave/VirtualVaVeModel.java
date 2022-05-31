package tools.vitruv.variability.vave;

import java.io.IOException;
import java.nio.file.Path;

import tools.vitruv.variability.vave.impl.ExternalizeDomainResult;
import tools.vitruv.variability.vave.impl.ExternalizeProductResult;
import tools.vitruv.variability.vave.impl.FeatureModel;
import tools.vitruv.variability.vave.impl.InternalizeChangesResult;
import tools.vitruv.variability.vave.impl.InternalizeDomainResult;
import vavemodel.Configuration;
import vavemodel.Expression;
import vavemodel.FeatureOption;
import vavemodel.System;
import vavemodel.SystemRevision;

public interface VirtualVaVeModel {

	/**
	 * Returns the System element (i.e., the unified system) contained in the VaVe model instance.
	 * 
	 * @return The System element in the VaVe model instance.
	 */
	public System getSystem();

	/**
	 * Externalizes a product view based on a provided configuration.
	 * 
	 * @param storageFolder A path that is used as storage location for the Virtual Product Model (VSUM) at which all model instances and correspondences are stored.
	 * @param configuration The configuration of the product.
	 * @return The Virtual Product Model (VSUM) which consists of one or multiple dependent domains representing the configuration.
	 * @throws IOException In case of problems when writing the externalized product to the disk.
	 */
	public ExternalizeProductResult externalizeProduct(Path storageFolder, Configuration configuration) throws IOException;

	/**
	 * Internalizes the changes performed on a product view into the unified system based on a manually provided expression. A new system revision is added and becomes the successor to the current system revision, new feature revisions are created for the features appearing in the expression and set as successors to the current revisions of those features and enabled by the new system revision,
	 * and new deltas and mappings are added based on the expression.
	 * 
	 * @param virtualProductModel The virtual product model that has been externalized before
	 * @param expression          The expression (currently a conjunction of variables (features) to which the recorded deltas should be mapped and which is provided manually by the user)
	 * @throws IOException In case of problems when writing the updated VaVe model to the disk.
	 */
	public InternalizeChangesResult internalizeChanges(VirtualProductModel virtualModel, Expression<FeatureOption> expression) throws IOException;

	/**
	 * Externalizes a view on the domain of the unified system based on a system revision.
	 * 
	 * @param system The unified system
	 * @param sysrev The system revision as point in time for which the domain view should be externalized
	 * @return The domain of the unified system in the form of a feature model
	 */
	public ExternalizeDomainResult externalizeDomain(SystemRevision sysrev);

	/**
	 * Internalizes the changes performed on a domain view (i.e., feature model) into the unified system.
	 * 
	 * @param fm The modified feature model view.
	 * @throws IOException In case of problems when writing the updated VaVe model to the disk.
	 */
	public InternalizeDomainResult internalizeDomain(FeatureModel fm) throws IOException;

}
