package tools.vitruv.framework.vsum

import java.util.List
import org.apache.log4j.Logger
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain

class VirtualModelConfiguration {
	static extension Logger = Logger::getLogger(VirtualModelConfiguration)
	val List<VitruvDomain> metamodels
	val List<ChangePropagationSpecification> changePropagationSpecifications

	new() {
		metamodels = newArrayList
		changePropagationSpecifications = newArrayList
	}

	private def boolean checkForMetamodelConflict(VitruvDomain newMetamodel) {
		for (existingMetamodel : metamodels) {
			for (nsURI : newMetamodel.nsUris) {
				if (existingMetamodel.nsUris.contains(nsURI)) {
					error(
						"Model configuration already contains metamodel " + existingMetamodel + " registering nsURI: " +
							nsURI
					)
					return false
				}
			}
			for (fileExtension : newMetamodel.fileExtensions) {
				if (existingMetamodel.fileExtensions.contains(fileExtension)) {
					error(
						"Model configuration already contains metamodel " + existingMetamodel +
							" registering file extension: " + fileExtension
					)
					return false
				}
			}
		}
		return true
	}

	def void addMetamodel(VitruvDomain metamodel) {
		if (!checkForMetamodelConflict(metamodel))
			throw new IllegalArgumentException(
				"Given metamodel defines nsURI or file extension that another metamodel already defines")
		metamodels += metamodel
	}

	def Iterable<VitruvDomain> getMetamodels() {
		metamodels
	}

	def Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		changePropagationSpecifications
	}

	def void addChangePropagationSpecification(
		ChangePropagationSpecification changePropagationSpecification
	) {
		if (!checkForTransformerConflict(changePropagationSpecification))
			throw new IllegalArgumentException(
				"Given propagation specification is defined for metamodel pair which another specification already defines")
		changePropagationSpecifications += changePropagationSpecification
	}

	private def boolean checkForTransformerConflict(
		ChangePropagationSpecification changePropagationSpecification
	) {
		for (existingPropagationSpecification : changePropagationSpecifications) {
			if (existingPropagationSpecification.sourceDomain.equals(changePropagationSpecification.sourceDomain) &&
				existingPropagationSpecification.targetDomain.equals(changePropagationSpecification.targetDomain)) {
				error(
					"Model configuration already contains propagation specification " +
						existingPropagationSpecification + " for the domain pair: " +
						existingPropagationSpecification.sourceDomain + " and " +
						existingPropagationSpecification.targetDomain
					)
						return false
					}
				}
				return true
			}

		}
		