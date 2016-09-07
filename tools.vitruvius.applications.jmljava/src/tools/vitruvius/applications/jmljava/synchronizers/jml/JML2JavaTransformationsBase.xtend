package tools.vitruvius.applications.jmljava.synchronizers.jml

import com.google.inject.Inject
import tools.vitruvius.domains.jml.language.jML.FieldDeclaration
import tools.vitruvius.domains.jml.language.jML.JMLModelElement
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration
import tools.vitruvius.domains.jml.language.jML.VariableDeclarator
import tools.vitruvius.applications.jmljava.helper.Utilities
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import tools.vitruvius.applications.jmljava.synchronizers.java.AbortableEObjectMappingTransformationBase
import java.util.ArrayList
import org.eclipse.emf.ecore.EObject
import tools.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener

abstract class JML2JavaTransformationsBase extends AbortableEObjectMappingTransformationBase {
	
	protected val ShadowCopyFactory shadowCopyFactory
	
	@Inject
	protected new(ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(synchronisationAbortedListener)
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
		val shadowCopy = shadowCopyFactory.create(correspondenceModel)
		shadowCopy.setupShadowCopyWithJMLSpecifications(true)
		val obj = shadowCopy.shadowCopyCorrespondences.getMember(tmpSpecifiedElement)
		obj.name = newName
		val refactoredObjects = shadowCopy.updateOriginalJMLSpecifications()
		changedObjects.addAll(refactoredObjects)
		
		return changedObjects
	}
	
}