package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Constructor
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.FieldDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMultilineSpec
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSinglelineSpec
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifierRegular
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MethodDeclaration
import java.util.Collection
import org.apache.commons.lang.RandomStringUtils
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

class Utilities {
	
	public static def <T extends EObject> T getModelInstanceElement(T obj, CorrespondenceModel ci) {
		val rootObject = EcoreUtil.getRootContainer(obj)
//		val modelInstanceRoot = ci.resolveEObjectFromTUID(TUID.getInstance(rootObject.TUID));
		return Utilities.getChildEqualToEObject(rootObject, obj);
	}
	
	public static def <T extends EObject> T getModelInstanceElementByTUID(T obj, CorrespondenceModel ci) {
		val rootObject = EcoreUtil.getRootContainer(obj)
//		val modelInstanceRoot = ci.resolveEObjectFromTUID(TUID.getInstance(rootObject.TUID));
//		return resolve(modelInstanceRoot, getTUID(obj)) as T
		return rootObject as T
	}
	
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
	
    public static def isVisibilityModifier(Modifier mod) {
    	return mod instanceof Public || mod instanceof Private || mod instanceof Protected;
	}
	
	public static def getRandomString() {
		return RandomStringUtils.random(10, true, true)
	}
	
	public static def corresponds(Method javaMethod, MemberDeclWithModifier jmlMethod) {
		var MethodDeclaration md = null
		if (jmlMethod.memberdecl instanceof MemberDeclaration) {
			md = (jmlMethod.memberdecl as MemberDeclaration).method
		}
		else if (jmlMethod.memberdecl instanceof GenericMethodOrConstructorDecl) {
			md = (jmlMethod.memberdecl as GenericMethodOrConstructorDecl).method			
		}

		if (md == null) {
			throw new IllegalArgumentException("The given parameter is no method.");
		}
		
		//TODO this matching does not consider type parameters correctly
		val javaStringRepresentation = StringOperationsJaMoPP.getStringRepresentation(javaMethod)
		val jmlStringRepresentation = StringOperationsJML.getStringRepresentation(md)
		return javaStringRepresentation.equals(jmlStringRepresentation)
	}
	
	public static def corresponds(Field javaField, MemberDeclWithModifier jmlField) {
		val FieldDeclaration fd = (jmlField.memberdecl as MemberDeclaration).field
		if (fd == null) {
			throw new IllegalArgumentException("The given parameter is no field.");
		}
		
		//TODO this matching is very simple assuming that only candidates of the same type are tested
		return fd.variabledeclarator.exists[identifier.equals(javaField.name)];
	}
	
	public static def corresponds(org.emftext.language.java.members.Constructor javaConstructor, MemberDeclWithModifierRegular memberDecl) {
		val jmlConstructor = (memberDecl.memberdecl as Constructor)
		
		if (javaConstructor.parameters.size != jmlConstructor.parameters.size) {
			return false
		}
		
		val javaStringRepresentation = StringOperationsJaMoPP.getStringRepresentation(javaConstructor)
		val jmlStringRepresentation = StringOperationsJML.getStringRepresentation(jmlConstructor)
		return javaStringRepresentation.equals(jmlStringRepresentation)
	}
	
	public static def corresponds(MemberDeclWithModifier jmlMemberDecl1, MemberDeclWithModifier jmlMemberDecl2) {
		if (!(jmlMemberDecl1.memberdecl instanceof MemberDeclaration)) {
			return false
		}
		val memberDecl1 = (jmlMemberDecl1.memberdecl as MemberDeclaration)
		
		if (!(jmlMemberDecl2.memberdecl instanceof MemberDeclaration)) {
			return false
		}  
		val memberDecl2 = (jmlMemberDecl2.memberdecl as MemberDeclaration)
		
		return corresponds(memberDecl1.field, memberDecl2.field) || corresponds(memberDecl1.method, memberDecl2.method)
	}
	
	public static def corresponds(FieldDeclaration fd1, FieldDeclaration fd2) {
		if (fd1 == null || fd2 == null) {
			return false
		}
		return fd1.variabledeclarator.get(0).identifier.equals(fd2.variabledeclarator.get(0).identifier)
	}
	
	public static def corresponds(MethodDeclaration md1, MethodDeclaration md2) {
		if (md1 == null || md2 == null) {
			return false
		}
		val str1 = StringOperationsJML.getStringRepresentation(md1)
		val str2 = StringOperationsJML.getStringRepresentation(md2)
		return str1.equals(str2)
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
