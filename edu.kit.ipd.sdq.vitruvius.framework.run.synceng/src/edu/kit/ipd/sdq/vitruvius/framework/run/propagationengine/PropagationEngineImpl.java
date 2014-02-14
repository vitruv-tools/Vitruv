package edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecutingProviding;

public class PropagationEngineImpl implements ChangePropagating {

    private static final Logger logger = Logger.getLogger(PropagationEngineImpl.class.getSimpleName());

    private final TransformationExecutingProviding transformationExecutingProviding;

    public PropagationEngineImpl(final TransformationExecutingProviding syncTransformationProviding) {
        this.transformationExecutingProviding = syncTransformationProviding;
    }

    @Override
    public void propagateChange(final Change change, final ModelInstance sourceModel,
            final CorrespondenceInstance correspondenceInstance) {
        TransformationExecuting transformationExecuting = this.transformationExecutingProviding.getTransformationExecuting(
                correspondenceInstance.getMapping().getMetamodelA().getURI(), correspondenceInstance.getMapping()
                        .getMetamodelB().getURI());
        transformationExecuting.executeTransformation(change, sourceModel, correspondenceInstance);
    }
}
