package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Collection;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
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
	 * @param target the {@link EObject} that <code>eObject</code> is corresponding to
	 * @param eChange the change that was applied
	 * @return 
	 */
	protected abstract MIRMappingChangeResult restorePostConditions(EObject eObject, EObject target, EChange change);
	
	/**
	 * Creates a corresponding object for <code>eObject</code> and a correspondence in the mapped meta model
	 * and registers it 
	 * @param eObject
	 * @param correspondenceInstance
	 */
	protected abstract MIRMappingChangeResult createCorresponding(EObject eObject, MappedCorrespondenceInstance correspondenceInstance);
	
	/**
	 * Deletes the corresponding object (and its children) and the correspondence.
	 * @param eObject
	 * @param correspondenceInstance
	 */
	protected MIRMappingChangeResult deleteCorresponding(EObject eObject, EObject target, MappedCorrespondenceInstance correspondenceInstance) {
		MIRMappingChangeResult result = new MIRMappingChangeResult();
		result.addObjectToDelete(target);
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
	
	private EObject getNewCorresponding(EObject source, MIRMappingChangeResult changeResult) {
		for (Pair<EObject, EObject> candidate : changeResult.getCorrespondencesToAdd()) {
			if (candidate.getFirst() == source)
				return candidate.getSecond();
			else if (candidate.getSecond() == source)
				return candidate.getFirst();
		}
			
		return null;
	}
	
	@Override
	public MIRMappingChangeResult applyEChange(
			EChange eChange,
			MappedCorrespondenceInstance correspondenceInstance) {
		Collection<EObject> candidates = getCandidates(eChange);
		
		MIRMappingChangeResult result = new MIRMappingChangeResult();
		
		for (EObject candidate : candidates) {
			LOGGER.trace("Checking candidate " + candidate.toString());
			
			boolean mappedBefore = correspondenceInstance.checkIfMappedBy(candidate, this);
			boolean mappedAfter = checkConditions(candidate, correspondenceInstance);
			
			EObject mappingTarget = null;
			
			if (mappedBefore) {
				mappingTarget = Objects.requireNonNull(correspondenceInstance.getMappingTarget(candidate, this));
				
				if (!mappedAfter) {
					result.add(deleteCorresponding(candidate, mappingTarget, correspondenceInstance));
				}
			}
			
			if (!mappedBefore && mappedAfter) {
				result.add(createCorresponding(candidate, correspondenceInstance));
				mappingTarget = Objects.requireNonNull(getNewCorresponding(candidate, result));
			}
			
			if (mappedAfter) {
				result.add(restorePostConditions(candidate, mappingTarget, eChange));
			}
		}
		
		return result;
	}
}
