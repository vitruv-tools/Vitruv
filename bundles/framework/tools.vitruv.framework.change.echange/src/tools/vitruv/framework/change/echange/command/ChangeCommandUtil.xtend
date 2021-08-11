package tools.vitruv.framework.change.echange.command

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.edit.domain.EditingDomain
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import org.eclipse.emf.common.command.BasicCommandStack
import static com.google.common.base.Preconditions.checkState
import java.util.List
import org.eclipse.emf.ecore.EReference

@Utility
class ChangeCommandUtil {
	/**
	 * Returns the editing domain of the an object. If the object has no 
	 * editing domain, it returns a new {@code AdapterFactoryEditingDomain}.
	 * @param object the {@link EObject}
	 * @return the editing domain of the object
	 */
	static def EditingDomain getEditingDomain(EObject object) {
		val editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(object)
		return editingDomain ?: new AdapterFactoryEditingDomain(new ComposedAdapterFactory(), new BasicCommandStack())
	}
	
	/**
	 * Returns whether the affected object already contains the given value in the given reference.
	 * 
	 * @param affectedEObject the {@link EObject} whose feature to check
	 * @param reference the {@link EReference} of the <code>affectedEObject</code> to check
	 * @param value the {@link EObject} to check whether it is already contained in the <code>affectedEObject</code>'s reference
	 * @return whether the <code>affectedEObject</code> contains the given value in the given reference.
	 */
	static def boolean alreadyContainsObject(EObject affectedEObject, EReference reference, EObject value) {
		checkState(affectedEObject.eClass.EAllReferences.contains(reference),
			"Given object %s does not contain reference %s", affectedEObject, reference)
		val List<EObject> featureContents = if (reference.many) {
				affectedEObject.eGet(reference) as List<EObject>
			} else {
				#[affectedEObject.eGet(reference) as EObject]
			}
		if (featureContents.contains(value)) {
			return true
		}
		return false
	}
}