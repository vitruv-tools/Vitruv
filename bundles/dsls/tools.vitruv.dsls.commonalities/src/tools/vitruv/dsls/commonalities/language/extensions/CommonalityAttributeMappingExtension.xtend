package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.OperatorAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.SimpleAttributeMapping
import tools.vitruv.dsls.commonalities.language.elements.Classifier
import tools.vitruv.dsls.commonalities.language.elements.ClassifierProvider

import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.language.extensions.AttributeMappingOperatorExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.OperandExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.OperatorAttributeMappingExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationClassExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationExtension.*

@Utility
package class CommonalityAttributeMappingExtension {

	static def isSimpleMapping(CommonalityAttributeMapping mapping) {
		return (mapping instanceof SimpleAttributeMapping)
	}

	static def isOperatorMapping(CommonalityAttributeMapping mapping) {
		return (mapping instanceof OperatorAttributeMapping)
	}

	static def dispatch ParticipationAttribute getParticipationAttribute(SimpleAttributeMapping mapping) {
		return mapping.attribute
	}

	// Can be null
	static def dispatch ParticipationAttribute getParticipationAttribute(OperatorAttributeMapping mapping) {
		return mapping.participationAttributeOperand?.participationAttribute
	}

	static def dispatch getInvolvedParticipationClasses(SimpleAttributeMapping mapping) {
		return Collections.singleton(mapping.attribute.participationClass)
	}

	// Note: This also includes the participation class for the (optional) participation attribute operand.
	static def dispatch getInvolvedParticipationClasses(OperatorAttributeMapping mapping) {
		// Assert: Not empty since there is always either an ParticipationAttributeOperand or at least one
		// ParticipationClassOperand (ensured via validation).
		// toSet: Filters duplicates in case the same participation class is involved in multiple operands.
		return mapping.operands.map[it.participationClass].filterNull.toSet
	}

	static def dispatch Participation getParticipation(SimpleAttributeMapping mapping) {
		return mapping.attribute.participationClass.participation
	}

	static def dispatch Participation getParticipation(OperatorAttributeMapping mapping) {
		val participationAttribute = mapping.participationAttribute // can be null
		if (participationAttribute !== null) {
			return participationAttribute.participationClass.participation
		} else {
			// Assert: There is at least one participation class operand (ensured via validation).
			assertTrue(!mapping.participationClassOperands.empty)
			return mapping.participationClassOperands.head.participation
		}
	}

	static def getDeclaringAttribute(CommonalityAttributeMapping mapping) {
		return mapping.getDirectContainer(CommonalityAttribute)
	}

	/**
	 * Returns <code>true<code> if the commonality side of the mapping is
	 * multi-valued.
	 */
	static def dispatch boolean isMultiValuedRead(SimpleAttributeMapping mapping) {
		return mapping.attribute.isMultiValued
	}

	static def dispatch boolean isMultiValuedRead(OperatorAttributeMapping mapping) {
		return mapping.operator.commonalityAttributeTypeDescription.isMultiValued
	}

	/**
	 * Returns <code>true<code> if the participation side of the mapping is
	 * multi-valued.
	 */
	static def dispatch boolean isMultiValuedWrite(SimpleAttributeMapping mapping) {
		return mapping.attribute.isMultiValued
	}

	static def dispatch boolean isMultiValuedWrite(OperatorAttributeMapping mapping) {
		return mapping.operator.participationAttributeTypeDescription.isMultiValued
	}

	static def dispatch Classifier getCommonalityAttributeType(SimpleAttributeMapping mapping) {
		return mapping.attribute.type
	}

	static def dispatch Classifier getCommonalityAttributeType(OperatorAttributeMapping mapping) {
		val domain = mapping.participation.domain
		val attributeTypeDescription = mapping.operator.commonalityAttributeTypeDescription
		return ClassifierProvider.INSTANCE.findClassifier(domain, attributeTypeDescription.qualifiedTypeName)
	}

	static def dispatch Classifier getParticipationAttributeType(SimpleAttributeMapping mapping) {
		return mapping.attribute.type
	}

	static def dispatch Classifier getParticipationAttributeType(OperatorAttributeMapping mapping) {
		val domain = mapping.participation.domain
		val attributeTypeDescription = mapping.operator.participationAttributeTypeDescription
		return ClassifierProvider.INSTANCE.findClassifier(domain, attributeTypeDescription.qualifiedTypeName)
	}

	// Gets the output type when applied in read direction, or null if the
	// mapping is not applicable in read direction:
	static def Classifier getProvidedType(CommonalityAttributeMapping mapping) {
		if (mapping.isRead) {
			return mapping.commonalityAttributeType
		} else {
			return null
		}
	}

	// Gets the required input type when applied in write direction, or null if
	// the mapping is not applicable in write direction
	static def Classifier getRequiredType(CommonalityAttributeMapping mapping) {
		if (mapping.isWrite) {
			return mapping.commonalityAttributeType
		} else {
			return null
		}
	}
}
