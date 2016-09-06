package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java.compositerefiners

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.CompositeChangeRefiner
import edu.kit.ipd.sdq.vitruvius.framework.change.description.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.RemoveEReference
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChangeFactory
import edu.kit.ipd.sdq.vitruvius.domains.java.echange.feature.JavaFeatureEChange
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.FeatureEChange
import edu.kit.ipd.sdq.vitruvius.domains.java.echange.feature.reference.JavaRemoveEReference
import edu.kit.ipd.sdq.vitruvius.domains.java.echange.feature.reference.JavaInsertEReference

abstract class CompositeChangeRefinerBase implements CompositeChangeRefiner {
	
	protected val ShadowCopyFactory shadowCopyFactory;
	
	new(ShadowCopyFactory shadowCopyFactory) {
		this.shadowCopyFactory = shadowCopyFactory
	}
	
	protected static class AddDeleteContainer {
		private val List<RemoveEReference<?,?>> deleteChanges
		private val List<InsertEReference<?,?>> addChanges
		
		new(List<RemoveEReference<?,?>> deleteChanges, List<InsertEReference<?,?>> addChanges) {
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
		val modelChanges = change.changes.filter(GeneralChange)
		if (modelChanges.size != change.changes.size && addAndDeleteAreAllowedOnly) {
			throw new IllegalArgumentException("Only emd model changes changes are allowed.")
		}
		
		val deleteChanges = new ArrayList<RemoveEReference<?,?>>()
		modelChanges.map[EChanges].flatten.filter[it instanceof RemoveEReference<?,?>].forEach[deleteChanges.add(it as RemoveEReference<?,?>)]
		val addChanges = new ArrayList<InsertEReference<?,?>>()
		modelChanges.map[EChanges].flatten.filter[it instanceof InsertEReference<?,?>].forEach[addChanges.add(it as InsertEReference<?,?>)]
		
		if (deleteChanges.size + addChanges.size != modelChanges.size && addAndDeleteAreAllowedOnly) {
			throw new IllegalArgumentException("Only add and delete changes are allowed.")
		}
		
		return new CompositeChangeRefinerBase.AddDeleteContainer(deleteChanges, addChanges)
	}
	
	protected static def getEMFChange(JavaFeatureEChange<?,?> change) {
		var anyEObject = change.oldAffectedEObject
		if (anyEObject == null) {
			anyEObject = change.affectedEObject
		}
		return VitruviusChangeFactory.instance.createGeneralChange(change, VURI.getInstance(anyEObject.eResource))
	}
	
	protected static def dispatch typeCheck (JavaRemoveEReference<?,?> change, Class<?> oldAffectedType, Class<?> oldValueType) {
		return oldAffectedType.isAssignableFrom(change.oldAffectedEObject.class) &&  oldValueType.isAssignableFrom(change.oldValue.class)
	}
	
	protected static def dispatch typeCheck (JavaInsertEReference<?,?> change, Class<?> newAffectedType, Class<?> newValueType) {
		return newAffectedType.isAssignableFrom(change.affectedEObject.class) &&  newValueType.isAssignableFrom(change.newValue.class)
	}
}