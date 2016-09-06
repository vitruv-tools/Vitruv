package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.jml

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.FieldDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VariableDeclarator
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.Utilities
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteractionType
import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.MembersPackage

class JMLVariableDeclarationTransformations extends JML2JavaTransformationsBase {
	
	static val LOGGER = Logger.getLogger(JMLMethodDeclarationTransformations)
	
	@Inject
	new (ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(shadowCopyFactory, synchronisationAbortedListener)
	}
	
	override protected getLogger() {
		return LOGGER
	}
	
	override getClassOfMappedEObject() {
		return VariableDeclarator
	}
	
	override setCorrespondenceForFeatures() {
		featureCorrespondenceMap.put(JMLPackage.eINSTANCE.identifierHaving_Identifier, MembersPackage.eINSTANCE.field.getEStructuralFeature("name"))
	}
	
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue, Object newValue) {
		val changedObjects = new ArrayList<EObject>()
		
		LOGGER.trace("UpdateSingleValuedEAttribute\t" + affectedEObject + "." + affectedAttribute.name + " (" + oldValue + " -> " + newValue + ")")
		
		if (affectedAttribute == JMLPackage.eINSTANCE.identifierHaving_Identifier && affectedEObject.eContainer instanceof FieldDeclaration) {
			LOGGER.trace("Updating JML specifications")
			
			val affectedModelInstanceObject = (affectedEObject as VariableDeclarator).modelInstanceElement
			val jmlSpecOnlyElement = affectedModelInstanceObject.getParentOfType(JMLSpecificationOnlyElement)
			if (jmlSpecOnlyElement == null) {
				LOGGER.info("Aborted transformation because of violated restriction on model elements.")
				userInteracting.showMessage(UserInteractionType.MODAL, "You must NOT edit non model elements.");
				syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
				return new TransformationResult
			}
			
			val dummy = jmlSpecOnlyElement.element.clone;
			(dummy.memberdecl as MemberDeclaration).field.variabledeclarator.findFirst[identifier.equals(oldValue as String)].identifier = newValue as String
			
			val coid = jmlSpecOnlyElement.getParentOfType(ClassOrInterfaceDeclaration);
			val javaClassifier = coid.getSingleCorrespondingEObjectOfType(ConcreteClassifier)
			if (javaClassifier.getMembersByName(newValue as String).filter(Field).exists[Utilities.corresponds(it, dummy)]) {
				LOGGER.info("Aborted transformation because of name clash with Java.")
				userInteracting.showMessage(UserInteractionType.MODAL, "There already is a field in Java, which has the same name.");
				syncAbortedListener.synchronisationAborted(super.getSynchAbortChange());
				return new TransformationResult
			}
			
			val jmlFields = coid.getChildrenOfType(MemberDeclWithModifier).filter[memberdecl instanceof MemberDeclaration].filter[(memberdecl as MemberDeclaration).field != null]
			if (jmlFields.exists[Utilities.corresponds(it, dummy)]) {
				LOGGER.info("Aborted transformation because of name clash with JML.")
				userInteracting.showMessage(UserInteractionType.MODAL, "There already is a field in JML, which has the same name.");
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