package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MappedCorrespondenceInstance;

/**
 * {@link AbstractMIRMappingRealization} is extended by the code generated from the
 * intermediate language.
 * <p>
 * A mapping is instantiated for two model instances. 
 * @author Dominik Werle
 *
 */
public abstract class AbstractMIRMappingRealization implements MIRMappingRealization {
	/**
	 * Returns the {@link EClass} mapped by this mapping.
	 * @return the {@link EClass} mapped by this mapping
	 */
	protected abstract EClass getMappedEClass();
	
	/**
	 * Check if the conditions of the mapping hold for the given
	 * {@link EObject}.
	 * @param eObject the object to check
	 * @param correspondenceInstance
	 * @return true if the mapping holds for the given object.
	 */
	protected abstract boolean checkConditions(EObject eObject, MappedCorrespondenceInstance correspondenceInstance);
	
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
	protected abstract void restorePostConditions(EChange eChange, MappedCorrespondenceInstance correspondenceInstance);
	
	/**
	 * Creates a corresponding object for <code>eObject</code> and a correspondence in the mapped meta model
	 * and registers it 
	 * @param eObject
	 * @param correspondenceInstance
	 * @param transformationExecuting
	 */
	protected abstract void createCorresponding(EObject eObject, MappedCorrespondenceInstance correspondenceInstance);
	
	/**
	 * Deletes the corresponding object (and its children) and the correspondence.
	 * @param eObject
	 * @param correspondenceInstance
	 * @param transformationExecuting
	 */
	protected abstract void deleteCorresponding(EObject eObject, MappedCorrespondenceInstance correspondenceInstance);
	
	/**
	 * Returns {@link EObject EObjects} that are possibly affected by this change.
	 * @param eChange
	 * @return
	 */
	protected Collection<EObject> getCandidates(EChange eChange) {
		// TODO: implement
		// VSUM -> eChange.getVURI to get model elements
		throw new UnsupportedOperationException("getting candidates not supported");
	}
	
	@Override
	public EMFChangeResult applyEChange(
			EChange eChange,
			MappedCorrespondenceInstance correspondenceInstance) {
		Collection<EObject> candidates = getCandidates(eChange);
		
		EMFChangeResult result = new EMFChangeResult();
		
		for (EObject candidate : candidates) {
			boolean mappedBefore = correspondenceInstance.checkIfMappedBy(candidate, this);
			boolean mappedAfter = checkConditions(candidate, correspondenceInstance);
			
			if (!mappedBefore && mappedAfter) {
				createCorresponding(candidate, correspondenceInstance);
			}
			
			if (mappedBefore && !mappedAfter) {
				deleteCorresponding(candidate, correspondenceInstance);
			}
			
			if (mappedAfter) {
				restorePostConditions(eChange, correspondenceInstance);
			}
		}
		
		return result;
	}

}
