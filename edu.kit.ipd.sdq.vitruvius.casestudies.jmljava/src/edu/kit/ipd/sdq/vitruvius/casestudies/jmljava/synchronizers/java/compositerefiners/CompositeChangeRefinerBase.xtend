package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CompositeChangeRefiner
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.CompositeChange

abstract class CompositeChangeRefinerBase implements CompositeChangeRefiner {
	
	protected val ShadowCopyFactory shadowCopyFactory;
	
	new(ShadowCopyFactory shadowCopyFactory) {
		this.shadowCopyFactory = shadowCopyFactory
	}
	
	protected static class AddDeleteContainer {
		private val List<DeleteNonRootEObjectInList<EObject>> deleteChanges
		private val List<CreateNonRootEObjectInList<EObject>> addChanges
		
		new(List<DeleteNonRootEObjectInList<EObject>> deleteChanges, List<CreateNonRootEObjectInList<EObject>> addChanges) {
			this.deleteChanges = deleteChanges
			this.addChanges = addChanges
		}
		
		def getDeleteChanges() {
			return deleteChanges
		}
		
		def getAddChanges() {
			return addChanges
		}
	}
	
	protected static def getAddAndDeleteChanges (CompositeChange change) {
		return getAddAndDeleteChanges(change, true)
	}
	
	protected static def getAddAndDeleteChanges (CompositeChange change, boolean addAndDeleteAreAllowedOnly) {
		val modelChanges = change.changes.filter(EMFModelChange)
		if (modelChanges.size != change.changes.size && addAndDeleteAreAllowedOnly) {
			throw new IllegalArgumentException("Only emd model changes changes are allowed.")
		}
		
		val deleteChanges = new ArrayList<DeleteNonRootEObjectInList<EObject>>()
		modelChanges.filter[getEChange instanceof DeleteNonRootEObjectInList<?>].forEach[deleteChanges.add(getEChange as DeleteNonRootEObjectInList<EObject>)]
		val addChanges = new ArrayList<CreateNonRootEObjectInList<EObject>>()
		modelChanges.filter[getEChange instanceof CreateNonRootEObjectInList<?>].forEach[addChanges.add(getEChange as CreateNonRootEObjectInList<EObject>)]
		
		if (deleteChanges.size + addChanges.size != modelChanges.size && addAndDeleteAreAllowedOnly) {
			throw new IllegalArgumentException("Only add and delete changes are allowed.")
		}
		
		return new CompositeChangeRefinerBase.AddDeleteContainer(deleteChanges, addChanges)
	}
	
	protected static def getEMFChange(EFeatureChange<?> change) {
		val anyEObject = change.oldAffectedEObject
		if (anyEObject == null) {
			change.newAffectedEObject
		}
		return new EMFModelChange(change, VURI.getInstance(anyEObject.eResource))
	}
	
	protected static def dispatch typeCheck (DeleteNonRootEObjectInList<EObject> change, Class<?> oldAffectedType, Class<?> oldValueType) {
		return oldAffectedType.isAssignableFrom(change.oldAffectedEObject.class) &&  oldValueType.isAssignableFrom(change.oldValue.class)
	}
	
	protected static def dispatch typeCheck (CreateNonRootEObjectInList<EObject> change, Class<?> newAffectedType, Class<?> newValueType) {
		return newAffectedType.isAssignableFrom(change.newAffectedEObject.class) &&  newValueType.isAssignableFrom(change.newValue.class)
	}
}