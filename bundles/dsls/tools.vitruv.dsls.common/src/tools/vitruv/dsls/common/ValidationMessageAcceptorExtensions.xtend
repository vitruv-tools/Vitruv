package tools.vitruv.dsls.common

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.validation.ValidationMessageAcceptor
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EObject

/**
 * Copies some of the protected helpers from AbstractDeclarativeValidator to make it easier to split validators into multiple
 * classes. 
 */
@Utility
class ValidationMessageAcceptorExtensions {
	def static info(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature) {
		acceptor.info(message, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null)
	}

	def static info(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature,
		int index) {
		acceptor.info(message, source, feature, index, null)
	}

	def static info(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature,
		int index, String code, String... issueData) {
		acceptor.acceptInfo(message, source, feature, index, code, issueData)
	}

	def static warning(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature) {
		acceptor.warning(message, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX)
	}

	def static warning(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature,
		int index) {
		acceptor.warning(message, source, feature, index, null)
	}

	def static warning(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature,
		int index, String code, String... issueData) {
		acceptor.acceptWarning(message, source, feature, index, code, issueData)
	}

	def static warning(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature,
		String code, String... issueData) {
		acceptor.acceptWarning(message, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, code, issueData)
	}

	def static error(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature) {
		acceptor.error(message, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX)
	}

	def static error(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature,
		int index) {
		acceptor.error(message, source, feature, index, null)
	}

	def static error(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature,
		int index, String code, String... issueData) {
		acceptor.acceptWarning(message, source, feature, index, code, issueData)
	}

	def static error(ValidationMessageAcceptor acceptor, String message, EObject source, EStructuralFeature feature,
		String code, String... issueData) {
		acceptor.acceptError(message, source, feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, code, issueData)
	}
}
