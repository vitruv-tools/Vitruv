package tools.vitruv.applications.jmljava.synchronizers.custom

import tools.vitruv.domains.jml.language.ConcreteSyntaxHelper
import tools.vitruv.domains.jml.language.jML.JMLMethodExpression
import tools.vitruv.domains.jml.language.jML.MemberDeclWithModifier
import tools.vitruv.applications.jmljava.changesynchronizer.JavaTransformation
import tools.vitruv.applications.jmljava.helper.Utilities
import tools.vitruv.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import tools.vitruv.applications.jmljava.synchronizers.helpers.CorrespondenceHelper
import tools.vitruv.applications.jmljava.synchronizers.jml.CommonSynchronizerTasksJML
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.TransformationResult
import tools.vitruv.framework.userinteraction.UserInteractionType
import tools.vitruv.framework.userinteraction.UserInteracting
import java.util.Collection
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.statements.Statement

class JavaMethodBodyChangedTransformation extends CustomTransformationsBase implements JavaTransformation {

	static val LOGGER = Logger.getLogger(JavaMethodBodyChangedTransformation)
	val ClassMethod oldMethod
	val ClassMethod newMethod

	new(ShadowCopyFactory shadowCopyFactory, ClassMethod oldMethod, ClassMethod newMethod) {
		super(shadowCopyFactory)
		this.oldMethod = oldMethod
		this.newMethod = newMethod
	}

	override TransformationResult executeInternal(CorrespondenceModel correspondenceModel, UserInteracting userInteracting) {
		LOGGER.info(
			"Starting custom transformation " + JavaMethodBodyChangedTransformation.simpleName + " for method " +
				oldMethod.name)

		val oldMethodIsPure = oldMethod.isMethodDeclaredPure(correspondenceModel)

		val newShadowCopy = shadowCopyFactory.create(correspondenceModel)
		val newJavaMember = newShadowCopy.shadowCopyCorrespondences.getShadow(newMethod as ClassMethod)
		newJavaMember.statements.clear()
		newMethod.statements.forEach[newJavaMember.statements.add(Utilities.<Statement>clone(it))]
		newShadowCopy.setupShadowCopyWithJMLSpecifications(false)
		val newMethodIsPure = newJavaMember.isMethodPure(correspondenceModel)

		LOGGER.trace("Original method considered pure: " + oldMethodIsPure)
		LOGGER.trace("Changed method is pure: " + newMethodIsPure)

		var Collection<EObject> changedEObjects;
		try {
			changedEObjects = CommonSynchronizerTasksJML.adjustPureModifiersForMethod(
				oldMethodIsPure,
				newMethodIsPure,
				newJavaMember,
				newShadowCopy,
				correspondenceModel
			)
		} catch (CommonSynchronizerTasksJML.OperationNotApplicableException e) {
			LOGGER.trace("Transformation aborted since change is not applicable.")
			userInteracting.showMessage(UserInteractionType.MODAL,
				"The method body can not be changed this way since it would destroy the query property of this method.\n" +
				"Since the method is (transitively) used in the specification\n\"" +
				ConcreteSyntaxHelper.convertToConcreteSyntax(e.causingObject as JMLMethodExpression) +
				"\"\nthis is not allowed.")
			if(null != abortListener){
				this.abortListener.synchronisationAborted(this)
			}
		}

		LOGGER.trace("Transformation changed " + changedEObjects.size + " objects.")
		return new TransformationResult
	}

	private static def isMethodDeclaredPure(ClassMethod method, CorrespondenceModel ci) {
		val jmlMethodDeclaration = CorrespondenceHelper.
			getSingleCorrespondingEObjectOfType(ci, method, MemberDeclWithModifier)
		if (jmlMethodDeclaration == null) {
			return false
		}

		return CommonSynchronizerTasksJML.isPureMethod(jmlMethodDeclaration)
	}

	private static def isMethodPure(ClassMethod method, CorrespondenceModel ci) {
		return CommonSynchronizerTasksJML.isPureMethod(method, ci)
	}

}
