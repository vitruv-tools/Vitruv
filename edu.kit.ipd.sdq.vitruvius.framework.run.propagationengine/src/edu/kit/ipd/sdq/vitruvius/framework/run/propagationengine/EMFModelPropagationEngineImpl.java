package edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecutingProviding;

public class EMFModelPropagationEngineImpl implements ChangePropagating {

    // private static final Logger logger =
    // Logger.getLogger(EMFModelPropagationEngineImpl.class.getSimpleName());

    private final TransformationExecutingProviding transformationExecutingProviding;

    public EMFModelPropagationEngineImpl(final TransformationExecutingProviding syncTransformationProviding) {
        this.transformationExecutingProviding = syncTransformationProviding;
    }

    @Override
    public ChangeResult propagateChange(final Change change, final CorrespondenceInstance correspondenceInstance) {
        EMFModelChange emfModelChange = (EMFModelChange) change;
        return getTransformationExecuting(correspondenceInstance).executeTransformation(emfModelChange,
                correspondenceInstance);
    }

    @Override
    public ChangeResult propagateChange(final CompositeChange compositeChange,
            final CorrespondenceInstance correspondenceInstance) {
        return getTransformationExecuting(correspondenceInstance).executeTransformation(compositeChange,
                correspondenceInstance);
    }

    private EMFModelTransformationExecuting getTransformationExecuting(
            final CorrespondenceInstance correspondenceInstance) {
        EMFModelTransformationExecuting transformationExecuting = this.transformationExecutingProviding
                .getTransformationExecuting(correspondenceInstance.getMapping().getMetamodelA().getURI(),
                        correspondenceInstance.getMapping().getMetamodelB().getURI());
        return transformationExecuting;
    }
}
