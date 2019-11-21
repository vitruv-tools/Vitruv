package tools.vitruv.dsls.mappings.generator

import java.util.List
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.mirbase.mirBase.DomainReference

class MappingParameterExtractor {

	var DomainReference targetDomain
	var Mapping mapping
	var boolean leftSide

	new(DomainReference targetDomain) {
		this.targetDomain = targetDomain
	}

	/**
	 * Extracts the parameters, conditions and further information from a mapping specification 
	 */
	def extract(Mapping mapping) {
		this.mapping = mapping
		// check the parameters for their model references
		val leftIsTargetSide = mapping.leftParameters.targetDomainSide
		val rightIsTargetSide = mapping.rightParameters.targetDomainSide
		if (leftIsTargetSide && rightIsTargetSide) {
			// invalid mapping definition
			throw new IllegalStateException(
				'The Mapping parameters fit both domain sides, therefore no direction can be determined!')
		} else if (leftIsTargetSide == false && rightIsTargetSide == false) {
			throw new IllegalStateException(
				'The Mapping parameters do not fit any domain side, therefore no direction can be determined!')
		}
		// everything correct and determined which side of the mapping is the target side
		leftSide = leftIsTargetSide
	}

	/**
	 * checks if there are no parameters, for which the parameter doesnt fit to the target side.
	 * therefore its true only if all parameters are from the target domain
	 */
	def isTargetDomainSide(List<MappingParameter> parameters) {
		!parameters.exists[!it.parameterFromTargetDomain]
	}

	/** 
	 * This only works when the imports of the domain metamodels have the same name as the domain reference.
	 * TODO: In the future it should be checked if the metaclass from the parameter is from the target domain,
	 * instead of checking the import name
	 * */
	def isParameterFromTargetDomain(MappingParameter parameter) {
		val parameterDomainName = parameter.value.metamodel.name.toLowerCase
		val targetDomainName = targetDomain.domain.toLowerCase
		parameterDomainName == targetDomainName
	}

	def getFromParameters() {
		if(leftSide) mapping.leftParameters else mapping.rightParameters
	}

	def getToParameters() {
		if(leftSide) mapping.rightParameters else mapping.leftParameters
	}

	def getFromConditions() {
		if(leftSide) mapping.leftConditions else mapping.rightConditions
	}

	def getToConditions() {
		if(leftSide) mapping.rightConditions else mapping.leftConditions
	}

	def getBidirectionalizableConditions() {
		mapping.bidirectionalizableConditions
	}

	def getBidirectionalizableRoutines() {
		mapping.bidirectionalizableRoutines
	}

	def getObserveChanges() {
		mapping.observeChanges
	}

}
