package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalContractsBuilder;

public class ContractsBuilder {
    public static CorrespondenceModel createCorrespondenceModel(final Mapping mapping,
            final ModelProviding modelProviding, final VURI correspondencesVURI,
            final Resource correspondencesResource) {
        return InternalContractsBuilder.createCorrespondenceModel(mapping, modelProviding, correspondencesVURI,
                correspondencesResource);
    };
}
