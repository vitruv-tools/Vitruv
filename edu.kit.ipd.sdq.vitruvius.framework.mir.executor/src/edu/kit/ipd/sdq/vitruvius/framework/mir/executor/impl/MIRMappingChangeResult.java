package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AddDeleteChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationChangeResult;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * Change model used by {@link AbstractMIRTransformationExecuting}. Can be transformed into a
 * {@link EMFChangeResult} by ensuring containments for all changed {@link EObject EObjects} and
 * then creating {@link EMFChangeResult#getNewRootObjectsToSave()} and
 * {@link EMFChangeResult#getExistingObjectsToSave()}.
 * 
 * @see AddDeleteChangeResult
 * @see TransformationChangeResult
 * @see EMFChangeResult
 * @author Dominik Werle
 */
public class MIRMappingChangeResult {
	private final Set<EObject> objectsToSave;
	private final Set<EObject> objectsToDelete;
	private final Set<Pair<EObject, EObject>> correspondencesToAdd;
	
	public MIRMappingChangeResult() {
		objectsToSave = new HashSet<EObject>();
		objectsToDelete = new HashSet<EObject>();
		correspondencesToAdd = new HashSet<Pair<EObject,EObject>>();
	}
	
	public void addObjectToSave(EObject objectToSave) {
		objectsToSave.add(objectToSave);
	}
	
	public void addObjectToDelete(EObject objectToDelete) {
		objectsToSave.add(objectToDelete);
	}
	
	public void addCorrespondence(EObject objectA, EObject objectB) {
		correspondencesToAdd.add(new Pair<EObject, EObject>(objectA, objectB));
	}
	
	public Set<EObject> getObjectsToSave() {
		return Collections.unmodifiableSet(objectsToSave);
	}

	public Set<EObject> getObjectsToDelete() {
		return Collections.unmodifiableSet(objectsToDelete);
	}

	public Set<Pair<EObject, EObject>> getCorrespondencesToAdd() {
		return Collections.unmodifiableSet(correspondencesToAdd);
	}
	
	public void add(MIRMappingChangeResult changeResult) {
		objectsToSave.addAll(changeResult.getObjectsToSave());
		objectsToDelete.addAll(changeResult.getObjectsToDelete());
		correspondencesToAdd.addAll(changeResult.getCorrespondencesToAdd());
	}
}
