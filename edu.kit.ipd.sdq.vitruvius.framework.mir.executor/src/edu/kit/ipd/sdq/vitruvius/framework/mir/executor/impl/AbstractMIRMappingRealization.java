package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Functions;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.SameTypeCorrespondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRMappingHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * {@link AbstractMIRMappingRealization} is extended by the code generated from the
 * intermediate language.
 * <p>
 * A mapping is instantiated for two model instances. 
 * @author Dominik Werle
 *
 */
public abstract class AbstractMIRMappingRealization implements MIRMappingRealization {
	private final static Logger LOGGER = Logger.getLogger(AbstractMIRMappingRealization.class);
	
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
	 * @param eObject the {@link EObject} that has been changed 
	 * @param eChange the change that was applied
	 * @param transformationExecuting 
	 * @param correspondenceInstance 
	 * @return 
	 */
	protected abstract TransformationChangeResult restorePostConditions(EObject eObject, EChange eChange, MappedCorrespondenceInstance correspondenceInstance);
	
	/**
	 * Creates a corresponding object for <code>eObject</code> and a correspondence in the mapped meta model
	 * and registers it 
	 * @param eObject
	 * @param correspondenceInstance
	 * @param transformationExecuting
	 */
	protected abstract TransformationChangeResult createCorresponding(EObject eObject, MappedCorrespondenceInstance correspondenceInstance);
	
	/**
	 * Deletes the corresponding object (and its children) and the correspondence.
	 * @param eObject
	 * @param correspondenceInstance
	 * @param transformationExecuting
	 */
	protected TransformationChangeResult deleteCorresponding(EObject eObject, MappedCorrespondenceInstance correspondenceInstance) {
		TransformationChangeResult result = new TransformationChangeResult();
		
		SameTypeCorrespondence correspondence = correspondenceInstance.getMappedCorrespondence(eObject, this);
		Pair<TUID, EObject> target = correspondenceInstance.getCorrespondenceTarget(eObject, correspondence);
		
		result.addCorrespondenceToDelete(correspondenceInstance.getCorrespondenceInstance(),
				target.getFirst());
		
		EcoreUtil.remove(target.getSecond());
		
		return result;
	}
	
	/**
	 * Returns {@link EObject EObjects} that are possibly affected by this change.
	 * @param eChange
	 * @return
	 */
	protected Collection<EObject> getCandidates(EChange eChange) {
		final EClass mappedEClass = getMappedEClass();
		Collection<EObject> affectedObjects = MIRMappingHelper.getAllAffectedObjects(eChange);
		return toList(filter(affectedObjects, new Functions.Function1<EObject, Boolean>() {
			@Override public Boolean apply(EObject p) {
				return p.eClass().equals(mappedEClass);
			}
		}));
	}
	
	@Override
	public TransformationChangeResult applyEChange(
			EChange eChange,
			MappedCorrespondenceInstance correspondenceInstance) {
		Collection<EObject> candidates = getCandidates(eChange);
		
		TransformationChangeResult result = new TransformationChangeResult();
		
		for (EObject candidate : candidates) {
			LOGGER.trace("Checking candidate " + candidate.toString());
			
			boolean mappedBefore = correspondenceInstance.checkIfMappedBy(candidate, this);
			boolean mappedAfter = checkConditions(candidate, correspondenceInstance);
			
			if (!mappedBefore && mappedAfter) {
				result.addChangeResult(createCorresponding(candidate, correspondenceInstance));
			}
			
			if (mappedBefore && !mappedAfter) {
				result.addChangeResult(deleteCorresponding(candidate, correspondenceInstance));
			}
			
			if (mappedAfter) {
				result.addChangeResult(restorePostConditions(candidate, eChange, correspondenceInstance));
			}
		}
		
		return result;
	}
}
