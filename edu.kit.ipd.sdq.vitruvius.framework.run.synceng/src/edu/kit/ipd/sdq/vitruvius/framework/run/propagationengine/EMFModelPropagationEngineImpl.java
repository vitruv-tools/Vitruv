package edu.kit.ipd.sdq.vitruvius.framework.run.propagationengine;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangePropagating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.TransformationExecutingProviding;

public class EMFModelPropagationEngineImpl implements ChangePropagating {

    private static final Logger logger = Logger.getLogger(EMFModelPropagationEngineImpl.class.getSimpleName());

    private final TransformationExecutingProviding transformationExecutingProviding;

    public EMFModelPropagationEngineImpl(final TransformationExecutingProviding syncTransformationProviding) {
        this.transformationExecutingProviding = syncTransformationProviding;
    }

    @Override
    public ChangeResult propagateChange(final Change change, final CorrespondenceInstance correspondenceInstance) {
        EMFModelTransformationExecuting transformationExecuting = this.transformationExecutingProviding
                .getTransformationExecuting(correspondenceInstance.getMapping().getMetamodelA().getURI(),
                        correspondenceInstance.getMapping().getMetamodelB().getURI());
        EMFModelChange emfModelChange = (EMFModelChange) change;
        return transformationExecuting.executeTransformation(emfModelChange, correspondenceInstance);
    }
}
