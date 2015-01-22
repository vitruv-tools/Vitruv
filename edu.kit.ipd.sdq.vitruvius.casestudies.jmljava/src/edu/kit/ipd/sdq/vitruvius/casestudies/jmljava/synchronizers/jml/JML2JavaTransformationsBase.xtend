package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.jml

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.EObjectMappingTransformationBase
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.FieldDeclaration
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLModelElement
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclWithModifier
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MethodDeclaration
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.VariableDeclarator
import java.util.ArrayList
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.Utilities
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLSpecifiedElement

abstract class JML2JavaTransformationsBase extends EObjectMappingTransformationBase {
	
	protected val ShadowCopyFactory shadowCopyFactory
	
	@Inject
	protected new(ShadowCopyFactory shadowCopyFactory) {
		this.shadowCopyFactory = shadowCopyFactory
	}
	
	protected def dispatch renameModelElementInAllSpecifications(VariableDeclarator vd, String newName) {
		if (!(vd.eContainer instanceof FieldDeclaration) || getParentOfType(vd, JMLModelElement) == null) {
			// error
		}
		
		vd.renameInAllJMLSpecifications(newName)
	}
	
	protected def dispatch renameModelElementInAllSpecifications(MethodDeclaration md, String newName) {
		if (md.getParentOfType(JMLModelElement) == null) {
			// error
		}
		
		md.renameInAllJMLSpecifications(newName)
	}
	
	private def renameInAllJMLSpecifications(EObject originalRenamedObject, String newName) {
		val changedObjects = new ArrayList<EObject>()
		
		val memberDecl = originalRenamedObject.getParentOfType(MemberDeclWithModifier)
		val tmpSpecifiedElement = Utilities.getParentOfType(memberDecl.modelInstanceElement, JMLSpecifiedElement)
		
		
		//TODO this only works if the old state of the model is serialized to a file
		val shadowCopy = shadowCopyFactory.create(correspondenceInstance)
		shadowCopy.setupShadowCopyWithJMLSpecifications(true)
		val obj = shadowCopy.shadowCopyCorrespondences.getMember(tmpSpecifiedElement)
		obj.name = newName
		val refactoredObjects = shadowCopy.updateOriginalJMLSpecifications()
		changedObjects.addAll(refactoredObjects)
		
		return changedObjects
	}
	
}