package edu.kit.ipd.sdq.vitruvius.casestudies.pcmuml.executortest;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.synctransprovider.TransformationExecutingProvidingImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * Provider for transformation executings based on @TransformationExecutingProvidingImpl
 * TransformationExecutings can be registered via a method in addition to the
 * usual Eclipse extension point mechanism of Eclipse.
 */
public class TransformationExecutingProvidingImplCustom extends
		TransformationExecutingProvidingImpl {

	/**
	 * Adds a transformation executing to the provider.
	 * 
	 * @param transformationExecuting
	 *            The transformation executing to be added.
	 */
	public void addEMFModelTransformationExecuting(
			EMFModelTransformationExecuting transformationExecuting) {
		for (Pair<VURI, VURI> transformableMetamodel : transformationExecuting
				.getTransformableMetamodels()) {
			this.transformationExecuterMap.put(transformableMetamodel,
					transformationExecuting);
		}
	}
}
