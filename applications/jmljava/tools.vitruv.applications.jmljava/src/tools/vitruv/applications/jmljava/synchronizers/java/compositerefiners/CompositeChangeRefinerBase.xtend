package tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners

import tools.vitruv.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import tools.vitruv.applications.jmljava.synchronizers.CompositeChangeRefiner
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.util.datatypes.VURI
import java.util.ArrayList
import java.util.List
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.domains.java.echange.feature.JavaFeatureEChange
import tools.vitruv.domains.java.echange.feature.reference.JavaRemoveEReference
import tools.vitruv.domains.java.echange.feature.reference.JavaInsertEReference
import tools.vitruv.framework.change.description.TransactionalChange

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
		val modelChanges = change.changes.filter(TransactionalChange)
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
		return VitruviusChangeFactory.instance.createConcreteChange(change, VURI.getInstance(anyEObject.eResource))
	}
	
//	protected static def dispatch boolean typeCheck(JavaRemoveEReference<?,?> change, Class<?> oldAffectedType, Class<?> oldValueType) {
//		return oldAffectedType.isAssignableFrom(change.oldAffectedEObject.class) &&  oldValueType.isAssignableFrom(change.oldValue.class)
//	}
//	
//	protected static def dispatch boolean typeCheck(JavaInsertEReference<?,?> change, Class<?> newAffectedType, Class<?> newValueType) {
//		return newAffectedType.isAssignableFrom(change.affectedEObject.class) &&  newValueType.isAssignableFrom(change.newValue.class)
//	}
}