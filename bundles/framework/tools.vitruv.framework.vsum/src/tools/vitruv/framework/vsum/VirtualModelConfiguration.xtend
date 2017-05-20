package tools.vitruv.framework.vsum

import java.util.List
import org.apache.log4j.Logger
import java.util.ArrayList
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain

class VirtualModelConfiguration {
	private static val logger = Logger::getLogger(VirtualModelConfiguration)
	private val List<VitruvDomain> metamodels
	private val List<ChangePropagationSpecification> changePropagationSpecifications

	public new() {
		this.metamodels = new ArrayList
		this.changePropagationSpecifications = new ArrayList
	}

	private def boolean checkForMetamodelConflict(VitruvDomain newMetamodel) {
		for (existingMetamodel : metamodels) {
			for (nsURI : newMetamodel.nsUris) {
				if (existingMetamodel.nsUris.contains(nsURI)) {
					logger.
						error('''Model configuration already contains metamodel «existingMetamodel» registering nsURI: «nsURI»''')
					return false
				}
			}
			for (fileExtension : newMetamodel.fileExtensions) {
				if (existingMetamodel.fileExtensions.contains(fileExtension)) {
					logger.
						error('''Model configuration already contains metamodel «existingMetamodel» registering file extension: «fileExtension»''')
					return false
				}
			}
		}
		return true
	}

	public def void addMetamodel(VitruvDomain metamodel) {
		if (!checkForMetamodelConflict(metamodel)) {
			throw new IllegalArgumentException(
				"Given metamodel defines nsURI or file extension that another metamodel already defines")
		}
		metamodels += metamodel;
	}

	private def boolean checkForTransformerConflict(ChangePropagationSpecification changePropagationSpecification) {
		for (existingPropagationSpecification : changePropagationSpecifications) {
			if (existingPropagationSpecification.sourceDomain.equals(changePropagationSpecification.sourceDomain) &&
				existingPropagationSpecification.targetDomain.equals(changePropagationSpecification.targetDomain)) {
				logger.error(
					'''Model configuration already contains propagation specification 
					«existingPropagationSpecification»  for the domain pair: 
					«existingPropagationSpecification.sourceDomain» and «existingPropagationSpecification.targetDomain»''')
				return false
			}
		}
		return true
	}

	public def void addChangePropagationSpecification(ChangePropagationSpecification changePropagationSpecification) {
		if (!checkForTransformerConflict(changePropagationSpecification)) {
			throw new IllegalArgumentException(
				"Given propagation specification is defined for metamodel pair which another specification already defines")
		}
		changePropagationSpecifications += changePropagationSpecification;
	}

	public def Iterable<VitruvDomain> getMetamodels() {
		metamodels
	}

	public def Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		changePropagationSpecifications
	}
}
