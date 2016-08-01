package edu.kit.ipd.sdq.vitruvius.framework.util.changes

import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.change.impl.ChangeDescriptionImpl
import org.eclipse.xtend.lib.annotations.Delegate
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.change.impl.ChangeDescriptionImplUtil.*
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.change.ChangeDescriptionUtil.*
import org.eclipse.emf.ecore.resource.Resource

/**
 * A forward change description that encapsulates the state before a change,
 * the delta to obtain the state after a change,
 * and information about the containment and resource of attached and detached objects after the change happened.<br/>
 * 
 * EMF's {@link ChangeDescription}, however always yields containment information before the last application of the change,
 * even if this application was a call to {@link ChangeDescription#applyAndReverse applyAndReverse} or {@link ChangeDescription#copyAndReverse copyAndReverse}!
 */
class ForwardChangeDescription implements ChangeDescription {
	@Delegate
	public val ChangeDescriptionImpl changeDescription
	// FIXME MK REMOVE PUBLIC FOR TESTING

	/**
	 * maps objects to their container and containment before the recorded change is reversed
	 */
	val Map<EObject, Pair<EObject, EReference>> containmentsBeforeReversion
	/**
	 * maps objects to their resource before the recorded change is reversed
	 */
	val Map<EObject, Resource> resourcesBeforeReversion
	
	/**
	 * Creates a new forward change description using the given backward change description by copying and reversing it if the given map is not {@code null} and otherwise applying and reversing it.
	 */
	new(ChangeDescription backwardChangeDescription, Map<EObject, URI> eObjectToProxyURIMap) {
		val cdi = backwardChangeDescription?.asImpl()
		this.changeDescription = cdi
		this.containmentsBeforeReversion = cdi.getContainmentsBeforeReversion()
		this.resourcesBeforeReversion = cdi.getResourcesBeforeReversion()
		if (eObjectToProxyURIMap == null) {
			this.applyAndReverse()
		} else {
			this.copyAndReverse(eObjectToProxyURIMap)
		}
	}
	
	/**
	 * Creates a new forward change description using the given backward change description by applying and reversing it.
	 */
	new(ChangeDescription changeDescription) {
		this(changeDescription, null)
	}
	
	def EObject getNewContainer(EObject eObject) {
		return containmentsBeforeReversion.get(eObject)?.getKey()
	}
	
	def EReference getNewContainmentReference(EObject eObject) {
		return containmentsBeforeReversion.get(eObject)?.getValue()
	}
	
	def Resource getNewResource(EObject rootObject) {
		return resourcesBeforeReversion.get(rootObject)
	}
}