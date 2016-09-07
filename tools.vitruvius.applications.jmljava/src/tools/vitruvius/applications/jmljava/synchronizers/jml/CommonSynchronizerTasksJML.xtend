package tools.vitruvius.applications.jmljava.synchronizers.jml

import tools.vitruvius.applications.jmljava.helper.Utilities
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopy
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyCorrespondences
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyJavaJmlHelperBase
import tools.vitruvius.applications.jmljava.synchronizers.helpers.CorrespondenceHelper
import tools.vitruvius.domains.jml.language.jML.JMLFactory
import tools.vitruvius.domains.jml.language.jML.JMLMultilineSpec
import tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import tools.vitruvius.framework.correspondence.CorrespondenceModel
import java.util.ArrayList
import java.util.HashSet
import java.util.LinkedList
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.expressions.AssignmentExpression
import org.emftext.language.java.expressions.Expression
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.references.MethodCall
import org.emftext.language.java.statements.ForLoop
import org.emftext.language.java.statements.LocalVariableStatement
import org.emftext.language.java.statements.Statement

class CommonSynchronizerTasksJML {
	
	public static def isPureMethod(ClassMethod method, CorrespondenceModel ci) {
		return !method.statements.exists[CommonSynchronizerTasksJML.isStatementAssignmentToFieldOrCallToNonPureMethod(it, ci)]
	}
	
	public static def isPureMethod(MemberDeclWithModifier jmlMethod) {
		return jmlMethod.jmlModifiers.exists[modifier == JMLSpecMemberModifier.PURE]
	}
	
	public static def isStatementAssignmentToFieldOrCallToNonPureMethod(Statement stmt, CorrespondenceModel ci) {
		val expressions = Utilities.getChildrenOfType(stmt, Expression)
		return expressions.exists[isExpressionAssignmentToFieldOrCallToNonPureMethod(ci)]
	}
	
	private static def dispatch isExpressionAssignmentToFieldOrCallToNonPureMethod(MethodCall expr, CorrespondenceModel ci) {
		val correspondingJmlMethod = CorrespondenceHelper.getSingleCorrespondingEObjectOfType(ci, expr.target, MemberDeclWithModifier)
		if (correspondingJmlMethod == null) {
			if (ShadowCopyJavaJmlHelperBase.isAReplacementMethod(expr.target)) {
				return false
			}
			return true // if there is no corresponding JML method it can not be marked pure
		}
		
		val isPureMethod = correspondingJmlMethod.jmlModifiers.exists[modifier == JMLSpecMemberModifier.PURE]
		return !isPureMethod
	}
	
	private static def dispatch isExpressionAssignmentToFieldOrCallToNonPureMethod(AssignmentExpression expr, CorrespondenceModel ci) {
		if (!(expr.child instanceof IdentifierReference)) {
			return false
		}
		
		val identifierReference = expr.child as IdentifierReference
		return identifierReference.target instanceof Field
	}
	
	private static def dispatch isExpressionAssignmentToFieldOrCallToNonPureMethod(Expression expr, CorrespondenceModel ci) {
		return false
	}
	
	public static def adjustPureModifiersForMethod(boolean oldMethodIsPure, boolean newMethodIsPure, ClassMethod newJavaMethod, ShadowCopy newShadowCopy, CorrespondenceModel ci) {
		val changedEObjects = new ArrayList<EObject>()

		if (oldMethodIsPure && !newMethodIsPure) {
			val pureMethodsToChange = newJavaMethod.getPureMethodsToChangeTransitively(newShadowCopy.shadowCopyCorrespondences)
				
			// remove pure transitively
			pureMethodsToChange.forEach[removePureModifier]
			changedEObjects.addAll(pureMethodsToChange)
		}

		if (!oldMethodIsPure && newMethodIsPure) {
			changedEObjects.addAll(newJavaMethod.transitivelyAddPureIfPossible(newShadowCopy.shadowCopyCorrespondences, ci))
		}
		
		return changedEObjects
	}
	
	public static class OperationNotApplicableException extends Exception {
		val Object cause;
		new(Object cause) {
			this.cause = cause;
		}
		def getCausingObject() {
			return cause;
		}
	}
	
	/**
	 * Determines all pure methods, which have to be changed if the given method looses its query status.
	 * If any of the methods for which the pure modifier shall be removed is referenced in a specification
	 * we have to block the change since we can not replace the method call in the specification. In that
	 * case an empty set is returned. 
	 * @returns The set of methods for which the pure modifier shall be removed.
	 * @throws OperationNotApplicableException In case of an existing reference to one of the methods,
	 *         which would become non pure.
	 */
	private static def getPureMethodsToChangeTransitively(Method javaChangedMethod, ShadowCopyCorrespondences shadowCorrespondences) {
		val pureMethodsToChange = new HashSet<MemberDeclWithModifier>()
		val processedObjects = new HashSet<EObject>()
		val queue = new LinkedList<Method>();
		queue.add(shadowCorrespondences.getShadow(javaChangedMethod));
		pureMethodsToChange.add(Utilities.getFirstChildOfType(shadowCorrespondences.get(queue.first), MemberDeclWithModifier));
		
		while (!queue.empty) {
			val javaObject = queue.pop
			val references = shadowCorrespondences.findReferencesToJavaObject(javaObject)
			for (reference : references) {
				val referencingObject = reference.EObject
				if (!processedObjects.contains(referencingObject)) {
					// check if the referencing object belongs to a jml specification dummy
					val statements = #[Utilities.getParentOfType(referencingObject, LocalVariableStatement), Utilities.getParentOfType(referencingObject, ForLoop)].filterNull
					if (statements.size != 0 && !statements.exists[processedObjects.contains(it)]) {
						val statementUsedInSpec = statements.findFirst[shadowCorrespondences.get(it as Statement) != null]
						if (statementUsedInSpec != null) {
							// mentioned in a spec, finished
							throw new OperationNotApplicableException(shadowCorrespondences.get(statementUsedInSpec));
							//return #[].toSet
						}
					}
					
					// check if the referencing object belongs to a pure method
					val containingMethod = Utilities.getParentOfType(referencingObject, ClassMethod)
					val jmlSpecElement = shadowCorrespondences.get(containingMethod)
					if (jmlSpecElement != null && !processedObjects.contains(jmlSpecElement)) {
						val jmlMethod = Utilities.getFirstChildOfType(jmlSpecElement, MemberDeclWithModifier)
						if (CommonSynchronizerTasksJML.isPureMethod(jmlMethod)) {
							queue.add(containingMethod)
							pureMethodsToChange.add(jmlMethod)							
						}
					}
					
					processedObjects.addAll(referencingObject, jmlSpecElement)
					processedObjects.addAll(statements)
				}
			}
		}
		
		return pureMethodsToChange
	}
	
	private static def removePureModifier(MemberDeclWithModifier jmlMethod) {
		val newModifiers = jmlMethod.jmlModifiers.filter[modifier != JMLSpecMemberModifier.PURE]
		jmlMethod.jmlModifiers.clear()
		jmlMethod.jmlModifiers.addAll(newModifiers)
	}
	
	private static def transitivelyAddPureIfPossible(ClassMethod newlyPureMethod, ShadowCopyCorrespondences shadowCorrespondences, CorrespondenceModel ci) {
		val changedJmlMethods = new ArrayList<JMLSpecifiedElement>()
		val processedElements = new HashSet<EObject>()
		val queue = new LinkedList<ClassMethod>()
		queue.add(newlyPureMethod)
		
		while (!queue.empty) {
			val candidate = queue.pop
			if (!processedElements.contains(candidate) && CommonSynchronizerTasksJML.isPureMethod(candidate, ci)) {
				val jmlMethod = shadowCorrespondences.get(candidate)
				val jmlMethodModelInstance = Utilities.getModelInstanceElement(jmlMethod, ci)
				if (!processedElements.contains(jmlMethodModelInstance)) {
					jmlMethodModelInstance.addPureModifier
					changedJmlMethods.add(jmlMethodModelInstance)
				}
				if (!processedElements.contains(jmlMethod) && jmlMethodModelInstance != jmlMethod) {
					jmlMethod.addPureModifier
				}
				val referencingMethods = new HashSet<ClassMethod>()
				shadowCorrespondences.findReferencesToJavaObject(candidate).forEach[referencingMethods.add(Utilities.getParentOfType(EObject, ClassMethod))]
				val filteredReferencingMethods = referencingMethods.filter[it != null].filter[!processedElements.contains(it)]
				
				queue.addAll(filteredReferencingMethods)
				processedElements.addAll(jmlMethod, jmlMethodModelInstance, candidate)
			}
		}
		
		return changedJmlMethods
	}
	
	private static def addPureModifier(JMLSpecifiedElement element) {
		val newPureModifier = JMLFactory.eINSTANCE.createJMLMemberModifier
		newPureModifier.modifier = JMLSpecMemberModifier.PURE
		if (element.element != null) {
			element.element.jmlModifiers.add(newPureModifier)
		} else if (element instanceof JMLMultilineSpec) {
			element.modelElement.element.element.jmlModifiers.add(newPureModifier)
		} else {
			throw new IllegalArgumentException("The given element has no member declaration inside...")
		}
	}
	
}