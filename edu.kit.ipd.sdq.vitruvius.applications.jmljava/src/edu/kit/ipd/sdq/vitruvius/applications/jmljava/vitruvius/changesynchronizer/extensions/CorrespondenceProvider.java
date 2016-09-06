package edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions;

import java.util.List;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.metamodel.ModelProviding;

/**
 * A provider for correspondences, which calculates and adds all correspondences
 * for a given set of models. Although the correspondences are bidirectional,
 * the order of the supported meta-models is crucial.
 */
public interface CorrespondenceProvider {

	/**
	 * Calculates and adds all correspondences for the given models to the given
	 * correspondence instance.
	 * 
	 * @param correspondenceInstance
	 *            The correspondence instance to be updated.
	 * @param relevantURIs
	 *            The URIs of all models, which shall be processed.
	 * @param modelProviding
	 *            An implementation of a model providing, which is used to get
	 *            the models by the given URI.
	 * @param mmManaging
	 *            An implementation of a meta-model provinding, which is used to
	 *            check that the given correspondence instance matches the
	 *            supported meta-models. The order of the meta-models is
	 *            crucial.
	 */
	void setAllCorrespondences(CorrespondenceModel correspondenceInstance,
			List<VURI> relevantURIs, ModelProviding modelProviding,
			MetamodelManaging mmManaging);

	/**
	 * @return The first supported meta-model.
	 */
	VURI getFirstMetaModelUri();

	/**
	 * @return The second supported meta-model.
	 */
	VURI getSecondMetaModelUri();

}
