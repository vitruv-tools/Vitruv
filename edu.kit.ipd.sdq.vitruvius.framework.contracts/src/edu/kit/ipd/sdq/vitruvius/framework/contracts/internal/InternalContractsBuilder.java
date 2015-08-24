package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceInstanceDecorating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge;

public class InternalContractsBuilder {
    public static InternalCorrespondenceInstance createCorrespondenceInstance(final Mapping mapping,
            final ModelProviding modelProviding, final VURI correspondencesVURI,
            final Resource correspondencesResource) {
        List<CorrespondenceInstanceDecorating> extensions = EclipseBridge.getRegisteredExtensionsInDescPriority(
                CorrespondenceInstanceDecorating.ID, VitruviusConstants.getExtensionPropertyName(),
                VitruviusConstants.getExtensionPriorityPropertyName(), CorrespondenceInstanceDecorating.class);
        CorrespondenceInstanceDecorator last = new CorrespondenceInstanceImpl(mapping, modelProviding,
                correspondencesVURI, correspondencesResource);
        CorrespondenceInstanceDecorator next;
        Iterator<CorrespondenceInstanceDecorating> extensionsIterator = extensions.iterator();
        while (extensionsIterator.hasNext()) {
            next = extensionsIterator.next().decorate(last);
            last = next;
        }
        return last;
    };
}
