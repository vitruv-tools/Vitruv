package tools.vitruv.framework.vsum

import tools.vitruv.framework.metamodel.Metamodel
import java.util.List
import org.apache.log4j.Logger
import java.util.ArrayList
import tools.vitruv.framework.change.processing.ChangePropagationSpecification

class VirtualModelConfiguration {
	private static val logger = Logger.getLogger(VirtualModelConfiguration);
	private val List<Metamodel> metamodels;
	private val List<ChangePropagationSpecification> changePropagationSpecifications;
	
	public new() {
		this.metamodels = new ArrayList();
		this.changePropagationSpecifications = new ArrayList();
	}
	
	private def boolean checkForMetamodelConflict(Metamodel newMetamodel) {
		for (existingMetamodel : metamodels) {
			for (nsURI : newMetamodel.nsURIs) {
				if (existingMetamodel.nsURIs.contains(nsURI)) {
					logger.error("Model configuration already contains metamodel " + existingMetamodel + " registering nsURI: " + nsURI);
					return false;
				}
			}
			for (fileExtension : newMetamodel.fileExtensions) {
				if (existingMetamodel.fileExtensions.contains(fileExtension)) {
					logger.error("Model configuration already contains metamodel " + existingMetamodel + " registering file extension: " + fileExtension);
					return false;
				}
			}
		}
		return true;
	}
	
	public def void addMetamodel(Metamodel metamodel) {
		if (!checkForMetamodelConflict(metamodel)) {
			throw new IllegalArgumentException("Given metamodel defines nsURI or file extension that another metamodel already defines");
		}
		metamodels += metamodel;	
	}
	
	private def boolean checkForTransformerConflict(ChangePropagationSpecification changePropagationSpecification) {
		for (existingPropagationSpecification : changePropagationSpecifications) {
			if (existingPropagationSpecification.metamodelPair.equals(changePropagationSpecification.metamodelPair)) {
				logger.error("Model configuration already contains propagation specification " + existingPropagationSpecification + " for the metamodel pair: " + existingPropagationSpecification.metamodelPair);
				return false;
			}
		}
		return true;
	}
	
	public def void addChangePropagationSpecification(ChangePropagationSpecification changePropagationSpecification) {
		if (!checkForTransformerConflict(changePropagationSpecification)) {
			throw new IllegalArgumentException("Given propagation specification is defined for metamodel pair which another specification already defines");
		}
		changePropagationSpecifications += changePropagationSpecification;	
	}
	
	public def Iterable<Metamodel> getMetamodels() {
		return metamodels;
	}
	
	public def Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return changePropagationSpecifications;
	}
}

