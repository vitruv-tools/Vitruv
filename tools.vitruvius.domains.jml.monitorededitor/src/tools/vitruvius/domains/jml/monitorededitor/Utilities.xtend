package tools.vitruvius.domains.jml.monitorededitor

import tools.vitruvius.domains.jml.language.jML.FieldDeclaration
import tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec
import tools.vitruvius.domains.jml.language.jML.JMLSinglelineSpec
import java.util.Collection
import org.apache.commons.lang.RandomStringUtils
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * FIXME Extract of jmljava.helper.Utilities: Combine both
 */
class Utilities {
	
	public static def <T extends EObject> getChildEqualToEObject(EObject rootObject, T objectToSearch) {
		return ModelSearchAlgorithms.breadthFirstSearch(rootObject, [EObject obj | EcoreUtil.equals(obj, objectToSearch)]) as T
	}
	
	public static def <T extends EObject> getFirstChildOfType(EObject rootObject, Class<T> type) {
		return ModelSearchAlgorithms.breadthFirstSearch(rootObject, [EObject obj | type.isAssignableFrom(obj.class)]) as T
	}
	
	public static def <T extends EObject> clone(T obj) {
        val rootObjectClone = EcoreUtil.copy(EcoreUtil.getRootContainer(obj));

        val rs = new ResourceSetImpl();
        val r = rs.createResource(obj.eResource().getURI(), obj.eResource().getURI().fileExtension());
        r.getContents().add(rootObjectClone);

        return getChildEqualToEObject(rootObjectClone, obj);
	}
	
	public static def <T extends EObject> getParentOfType(EObject eobject, Class<T> type) {
		ModelSearchAlgorithms.inverseTraversal(eobject, [EObject obj | type.isAssignableFrom(obj.class)]) as T
	}
	
	public static def <T extends EObject> getChildrenOfType(EObject eobject, Class<T> type) {
		return getChildrenOfType(eobject, type, false)
	}
	
	public static def <T extends EObject> getChildrenOfType(EObject eobject, Class<T> type, boolean keepOrder) {
		return ModelSearchAlgorithms.breadthFirstSearch(eobject, [EObject obj | type.isAssignableFrom(obj.class)], !keepOrder) as Collection<T>
	}
	
	public static def getRandomString() {
		return RandomStringUtils.random(10, true, true)
	}
	
	public static def corresponds(FieldDeclaration fd1, FieldDeclaration fd2) {
		if (fd1 == null || fd2 == null) {
			return false
		}
		return fd1.variabledeclarator.get(0).identifier.equals(fd2.variabledeclarator.get(0).identifier)
	}
	
	public static def dispatch getMemberDecl(JMLSinglelineSpec spec) {
		if (spec.element != null) {
			return spec.element
		}
		
		return null
	}
	
	public static def dispatch getMemberDecl(JMLMultilineSpec spec) {
		if (spec.element != null) {
			return spec.element
		}
		
		if (spec.modelElement != null) {
			return spec.modelElement.element.element
		}
		
		return null
	}
	
	public static def deleteEObjectInParent(EObject obj) {
		val container = obj.eContainer
		val containmentReference = obj.eContainmentFeature
		
		// containment reference is list
		if (containmentReference.upperBound > 1 || containmentReference.upperBound == -1) {
			val containingList = container.eGet(containmentReference) as EList<EObject>
			return containingList.remove(obj)
		}
		
		// containment reference is single reference
		container.eUnset(containmentReference)
		return true
	}
}
