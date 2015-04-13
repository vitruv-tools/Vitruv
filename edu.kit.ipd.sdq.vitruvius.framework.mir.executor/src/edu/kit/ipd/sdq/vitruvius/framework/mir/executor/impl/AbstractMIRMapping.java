package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collection;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMapping;

/**
 * {@link AbstractMIRMapping} is extended by the code generated from the
 * intermediate language.
 * <p>
 * A mapping is instantiated for two model instances. 
 * @author Dominik Werle
 *
 */
public abstract class AbstractMIRMapping implements MIRMapping {
	/**
	 * Returns the {@link EClassifier} mapped by this mapping.
	 * @return the {@link EClassifier} mapped by this mapping
	 */
	protected abstract EClassifier getMappedEClassifier();
	
	/**
	 * Check if the conditions of the mapping hold for the given
	 * {@link EObject}.
	 * @param eObject the object to check
	 * @param correspondenceInstance
	 * @param transformationExecuting
	 * @return true if the mapping holds for the given object.
	 */
	protected abstract boolean checkConditions(EObject eObject, CorrespondenceInstance correspondenceInstance,
			AbstractMIRTransformationExecuting transformationExecuting);
	
	/**
	 * Ensure that the postconditions ("where") still hold for the
	 * mapping.
	 * <p>
	 * The given {@link EChange} can be used to select the conditions
	 * that have to be checked.
	 * @param eChange the change that was applied
	 * @param transformationExecuting 
	 * @param correspondenceInstance 
	 */
	protected abstract void restorePostConditions(EChange eChange, CorrespondenceInstance correspondenceInstance,
			AbstractMIRTransformationExecuting transformationExecuting);
	
	/**
	 * Creates a corresponding object for <code>eObject</code> and a correspondence in the mapped meta model
	 * and registers it 
	 * @param eObject
	 * @param correspondenceInstance
	 * @param transformationExecuting
	 */
	protected abstract void createCorresponding(EObject eObject, CorrespondenceInstance correspondenceInstance,
			AbstractMIRTransformationExecuting transformationExecuting);
	
	/**
	 * Deletes the corresponding object (and its children) and the correspondence.
	 * @param eObject
	 * @param correspondenceInstance
	 * @param transformationExecuting
	 */
	protected abstract void deleteCorresponding(EObject eObject, CorrespondenceInstance correspondenceInstance,
			AbstractMIRTransformationExecuting transformationExecuting);
	
	/**
	 * Returns {@link EObject EObjects} that are possibly affected by this change.
	 * @param eChange
	 * @return
	 */
	protected Collection<EObject> getCandidates(EChange eChange) {
		// TODO: implement
		throw new UnsupportedOperationException("getting candidates not supported");
	}
	
	/**
	 * Checks if this mapping maps <code>eObject</code>.
	 * @param eObject the {@link EObject} to check
	 * @param correspondenceInstance
	 * @param transformationExecuting
	 * @return <code>true</code> if this mapping maps <code>eObject</code>
	 */
	private boolean checkIfMappedBy(EObject eObject, CorrespondenceInstance correspondenceInstance,
			AbstractMIRTransformationExecuting transformationExecuting) {
		Collection<Correspondence> correspondences = correspondenceInstance.getAllCorrespondences(eObject);
		for (Correspondence correspondence : correspondences) {
			MIRMapping mappingForCorrespondence = transformationExecuting.getMappingForCorrespondence(correspondence);
			if (mappingForCorrespondence == this) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public EMFChangeResult applyEChange(
			EChange eChange,
			CorrespondenceInstance correspondenceInstance,
			AbstractMIRTransformationExecuting transformationExecuting) {
		Collection<EObject> candidates = getCandidates(eChange);
		
		EMFChangeResult result = new EMFChangeResult();
		
		for (EObject candidate : candidates) {
			boolean mappedBefore = checkIfMappedBy(candidate, correspondenceInstance, transformationExecuting);
			boolean mappedAfter = checkConditions(candidate, correspondenceInstance, transformationExecuting);
			
			if (!mappedBefore && mappedAfter) {
				createCorresponding(candidate, correspondenceInstance, transformationExecuting);
			}
			
			if (mappedBefore && !mappedAfter) {
				deleteCorresponding(candidate, correspondenceInstance, transformationExecuting);
			}
			
			if (mappedAfter) {
				restorePostConditions(eChange, correspondenceInstance, transformationExecuting);
			}
		}
		
		return result;
	}

}
