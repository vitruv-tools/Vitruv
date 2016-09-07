package tools.vitruvius.applications.jmljava.synchronizers.jml

import com.google.inject.Inject
import tools.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration
import tools.vitruvius.domains.jml.language.jML.JMLModelElement
import tools.vitruvius.domains.jml.language.jML.JMLPackage
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration
import tools.vitruvius.applications.jmljava.helper.Utilities
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import tools.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import tools.vitruvius.framework.util.command.TransformationResult
import tools.vitruvius.framework.userinteraction.UserInteractionType
import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.members.MembersPackage
import org.emftext.language.java.members.Method

class JMLMethodDeclarationTransformations extends JML2JavaTransformationsBase {
	
	static val LOGGER = Logger.getLogger(JMLMethodDeclarationTransformations)
	
	@Inject
	new (ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(shadowCopyFactory, synchronisationAbortedListener)
	}
	
	override protected getLogger() {
		return LOGGER
	}
	
	override getClassOfMappedEObject() {
		return MethodDeclaration
	}
	
	override setCorrespondenceForFeatures() {
		featureCorrespondenceMap.put(JMLPackage.eINSTANCE.identifierHaving_Identifier, MembersPackage.eINSTANCE.method.getEStructuralFeature("name"))
	}
	
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
		val changedObjects = new ArrayList<EObject>()
		
		LOGGER.trace("UpdateSingleValuedEAttribute\t" + affectedEObject + "." + affectedAttribute.name + " (" + oldValue + " -> " + newValue + ")")
		
		//val javaFeature = featureCorrespondenceMap.claimValueForKey(affectedAttribute)
		if (affectedAttribute == JMLPackage.eINSTANCE.identifierHaving_Identifier) {
			LOGGER.trace("Updating JML specifications")
			
			val affectedModelInstanceObject = (affectedEObject as MethodDeclaration).modelInstanceElement
			val jmlModelElement = affectedModelInstanceObject.getParentOfType(JMLModelElement)
			if (jmlModelElement == null) {
				LOGGER.info("Aborted transformation because of violated restriction on model elements.")
				userInteracting.showMessage(UserInteractionType.MODAL, "You must NOT edit non model elements.");
				syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
				return new TransformationResult
			}
			
			val dummy = jmlModelElement.element.clone;
			(dummy.memberdecl as MemberDeclaration).method.identifier = newValue as String
			
			val coid = jmlModelElement.getParentOfType(ClassOrInterfaceDeclaration);
			val javaClassifier = coid.getSingleCorrespondingEObjectOfType(ConcreteClassifier)
			if (javaClassifier.getMembersByName(newValue as String).filter(Method).exists[Utilities.corresponds(it, dummy)]) {
				LOGGER.info("Aborted transformation because of name clash with Java.")
				userInteracting.showMessage(UserInteractionType.MODAL, "There already is a method in Java, which has the same name.");
				syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
				return new TransformationResult
			}
			
			val jmlMethods = coid.getChildrenOfType(MemberDeclWithModifier).filter[memberdecl instanceof MemberDeclaration].filter[(memberdecl as MemberDeclaration).method != null]
			if (jmlMethods.exists[Utilities.corresponds(it, dummy)]) {
				LOGGER.info("Aborted transformation because of name clash with JML.")
				userInteracting.showMessage(UserInteractionType.MODAL, "There already is a method in JML, which has the same name.");
				syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
				return new TransformationResult
			}
			
			changedObjects.addAll(affectedModelInstanceObject.renameModelElementInAllSpecifications(newValue as String))
			
			affectedModelInstanceObject.identifier = newValue as String
			changedObjects.add(affectedModelInstanceObject)
		}
		return new TransformationResult
	}	
	
}