package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.ws.ServiceMode;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EclipseHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRMappingHelper;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRUserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * {@link AbstractMIRMappingRealization} is extended by the code generated from
 * the intermediate language.
 * <p>
 * A mapping is instantiated for two model instances.
 *
 * @author Dominik Werle
 *
 */
@Deprecated
public abstract class AbstractMIRMappingRealization implements MIRMappingRealization {
//	private final static Logger LOGGER = Logger.getLogger(AbstractMIRMappingRealization.class);
//
//	/**
//	 * Returns the {@link EClass} mapped by this mapping.
//	 * 
//	 * @return the {@link EClass} mapped by this mapping
//	 */
//	protected abstract EClass getMappedEClass();
//
//	/**
//	 * Check if the conditions of the mapping hold for the given {@link EObject}
//	 * .
//	 * 
//	 * @param eObject
//	 *            the object to check
//	 * @return true if the mapping holds for the given object.
//	 */
//	protected abstract boolean checkConditions(EObject eObject, Blackboard blackboard);
//
//	/**
//	 * Ensure that the postconditions ("where") still hold for the mapping.
//	 * <p>
//	 * The given {@link EChange} can be used to select the conditions that have
//	 * to be checked.
//	 * 
//	 * @param eObject
//	 *            the {@link EObject} that has been changed
//	 * @param target
//	 *            the {@link EObject} that <code>eObject</code> is corresponding
//	 *            to
//	 * @param eChange
//	 *            the change that was applied
//	 * @return
//	 */
//	protected abstract void restorePostConditions(EObject eObject, EObject target, EChange change,
//			Blackboard blackboard);
//
//	/**
//	 * Creates a corresponding object for <code>eObject</code> and a
//	 * correspondence in the mapped meta model and registers it
//	 * 
//	 * @return the created objects
//	 */
//	protected abstract Collection<Pair<EObject, VURI>> createCorresponding(EObject eObject, Blackboard blackboard);
//
//	/**
//	 * Deletes the corresponding object to <code>eObject</code>, which is
//	 * <code>target</code>, (and its children) and the correspondence itself.
//	 * This method does not delete <code>eObject</code> .
//	 * 
//	 * @param source
//	 * @param transformationResult
//	 * @param correspondenceInstance
//	 */
//	protected void deleteCorresponding(final EObject source, final EObject target, final Blackboard blackboard,
//			final TransformationResult transformationResult) {
//		final MappedCorrespondenceInstance correspondenceInstance = getMappedCorrespondenceInstanceFromBlackboard(
//				blackboard);
//		final Set<Correspondence> removedCorrespondences = correspondenceInstance
//				.removeCorrespondencesOfEObjectAndChildrenOnBothSides(target);
//		final TUID sourceTUID = correspondenceInstance.calculateTUIDFromEObject(source);
//
//		for (final Correspondence correspondence : removedCorrespondences) {
//			final Collection<TUID> eObjectsInCorrespondence = this.getTUIDsInCorrespondence(correspondence,
//					correspondenceInstance);
//			eObjectsInCorrespondence.stream().filter(it -> !(it.equals(sourceTUID))).forEach(it -> {
//				final EObject itEObject = correspondenceInstance.resolveEObjectFromTUID(it);
//				this.deleteEObjectAndResourceIfRoot(itEObject, transformationResult);
//			});
//		}
//	}
//
//	private void deleteEObjectAndResourceIfRoot(final EObject eObjectToDelete,
//			final TransformationResult transformationResult) {
//		LOGGER.trace("Deleting " + eObjectToDelete.toString());
//		if (EcoreHelper.isRoot(eObjectToDelete)) {
//			transformationResult.addVURIToDeleteIfNotNull(VURI.getInstance(eObjectToDelete.eResource()));
//		}
//		EcoreUtil.delete(eObjectToDelete);
//	}
//
//	private Collection<TUID> getTUIDsInCorrespondence(final Correspondence correspondence,
//			final CorrespondenceInstance correspondenceInstance) {
//		final Set<TUID> result = new HashSet<TUID>();
//		if (correspondence instanceof Correspondence) {
//			final Correspondence eObjectCorrespondence = (Correspondence) correspondence;
//			result.add(eObjectCorrespondence.getElementATUID());
//			result.add(eObjectCorrespondence.getElementBTUID());
//		} else {
//			LOGGER.info("Correspondence is not EObjectCorrespondence. Returning empty set.");
//		}
//
//		return result;
//	}
//
//	/**
//	 * Returns {@link EObject EObjects} that are possibly affected by this
//	 * change.
//	 * 
//	 * @param eChange
//	 * @return
//	 */
//	protected Collection<EObject> getCandidates(final EChange eChange) {
//		final EClass mappedEClass = this.getMappedEClass();
//		final Collection<EObject> affectedObjects = MIRMappingHelper.getAllAffectedObjects(eChange);
//		return toList(filter(affectedObjects, p -> p.eClass().equals(mappedEClass)));
//	}
//
//	// FIXME DW: protected? move to MappedCorrespondenceInstance?
//	protected static MappedCorrespondenceInstance getMappedCorrespondenceInstanceFromBlackboard(
//			final Blackboard blackboard) {
//		final CorrespondenceInstance correspondenceInstance = blackboard.getCorrespondenceInstance();
//		if (!(correspondenceInstance instanceof MappedCorrespondenceInstance)) {
//			throw new IllegalArgumentException("The given correspondence instance " + correspondenceInstance
//					+ " is not a " + MappedCorrespondenceInstance.class.getSimpleName());
//		} else {
//			return (MappedCorrespondenceInstance) correspondenceInstance;
//		}
//	}
//
//	@Override
//	public TransformationResult applyEChange(final EChange eChange, final Blackboard blackboard) {
//		final MappedCorrespondenceInstance correspondenceInstance = getMappedCorrespondenceInstanceFromBlackboard(
//				blackboard);
//		final TransformationResult transformationResult = new TransformationResult();
//
//		/*
//		 * TODO: change to create candidates (EObject, PotentialTransition(new,
//		 * still, remove), Mapping) in AbstractMIRChange2CommandTransforming.
//		 * Create -> cannot be mapped before
//		 * Delete -> cannot be mapped after
//		 */
//		final Collection<EObject> candidates = this.getCandidates(eChange);
//
//		for (final EObject candidate : candidates) {
//			LOGGER.trace("Checking candidate " + candidate.toString());
//
//			final boolean mappedBefore = correspondenceInstance.checkIfMappedBy(candidate, this);
//			final boolean mappedAfter = this.checkConditions(candidate, blackboard);
//			
//			LOGGER.trace("... mapped before: " + mappedBefore + ", after: " + mappedAfter);
//
//			EObject mappingTarget = null;
//
//			if (mappedBefore) {
//				LOGGER.trace(candidate.toString() + " was already mapped.");
//				mappingTarget = Objects.requireNonNull(correspondenceInstance.getMappingTarget(candidate, this));
//
//				if (!mappedAfter) {
//					LOGGER.trace("... and is not mapped anymore.");
//					this.deleteCorresponding(candidate, mappingTarget, blackboard, transformationResult);
//				}
//			}
//
//			if (!mappedBefore && mappedAfter) {
//				LOGGER.trace("Create new correspondence for " + candidate.toString() + ":");
//				final Collection<Pair<EObject, VURI>> newRootObjects = this.createCorresponding(candidate, blackboard);
//				for (final Pair<EObject, VURI> newRootObject : newRootObjects) {
//					LOGGER.trace(" -- " + newRootObject.getFirst().toString() + " -> "
//							+ newRootObject.getSecond().toString());
//				}
//				transformationResult.getRootEObjectsToSave().addAll(newRootObjects);
//				mappingTarget = Objects.requireNonNull(correspondenceInstance.getMappingTarget(candidate, this));
//			}
//
//			if (mappedAfter) {
//				LOGGER.trace("Still mapped.");
//				this.restorePostConditions(candidate, mappingTarget, eChange, blackboard);
//			}
//		}
//
//		return transformationResult;
//	}
//
//	/**
//	 * Asks the user and creates new resources for EObjects in
//	 * <code>affectedEObjects</code> that do not have a container.
//	 * 
//	 * @param blackboard
//	 *            TODO
//	 * @param transformationResult
//	 */
//	protected Collection<Pair<EObject, VURI>> handleNonContainedEObjects(final Collection<EObject> eObjects) {
//		Collection<Pair<EObject, VURI>> result = new ArrayList<>();
//		for (final EObject eObject : eObjects) {
//			if (eObject.eResource() == null) {
//				final VURI resourceVURI = VURI
//						.getInstance(userInteracting.askForNewResource(EcoreHelper.createSensibleString(eObject)));
//				result.add(new Pair<>(eObject, resourceVURI));
//			}
//		}
//		
//		return result;
//	}
//
//	// TODO: DW remove private field that can only be changed throughg reflection / dependency injection?
//	private transient MIRUserInteracting userInteracting = new EclipseDialogMIRUserInteracting();
}
