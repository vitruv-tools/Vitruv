package tools.vitruv.dsls.mappings.generator

import java.util.List
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import org.eclipse.emf.ecore.EPackage
import java.util.Set

class MappingParameterExtractor {

	var Set<EPackage> targetMetamodelPackages
	var Mapping mapping
	var boolean leftSide

	new(Set<EPackage> targetMetamodelPackages) {
		this.targetMetamodelPackages = targetMetamodelPackages
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
	private def isTargetDomainSide(List<MappingParameter> parameters) {
		!parameters.exists[!it.parameterFromTargetDomain]
	}

	private def isParameterFromTargetDomain(MappingParameter parameter) {
		val parameterPackage = parameter.value.metamodel.package
		return targetMetamodelPackages.exists[parameterPackage.isPackageOrSubpackage(it)]
	}
	
	private static def boolean isPackageOrSubpackage(EPackage consideredPackage, EPackage packageToSearchIn) {
		if (consideredPackage == packageToSearchIn) {
			return true;
		} else {
			return packageToSearchIn.ESubpackages.exists[isPackageOrSubpackage(consideredPackage, it)];
		}
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
