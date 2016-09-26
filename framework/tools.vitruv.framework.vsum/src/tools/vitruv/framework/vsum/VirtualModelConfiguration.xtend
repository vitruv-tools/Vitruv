package tools.vitruv.framework.vsum

import tools.vitruv.framework.metamodel.Metamodel
import java.util.List
import org.apache.log4j.Logger
import tools.vitruv.framework.change.processing.Change2CommandTransforming
import java.util.ArrayList

class VirtualModelConfiguration {
	private static val logger = Logger.getLogger(VirtualModelConfiguration);
	private val List<Metamodel> metamodels;
	private val List<Change2CommandTransforming> transformers;
	
	public new() {
		this.metamodels = new ArrayList();
		this.transformers = new ArrayList();
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
	
	private def boolean checkForTransformerConflict(Change2CommandTransforming transformer) {
		for (existingTransformer : transformers) {
			if (existingTransformer.transformableMetamodels.equals(transformer.transformableMetamodels)) {
				logger.error("Model configuration already contains change2command transformer " + existingTransformer + " for the metamodel pair: " + existingTransformer.transformableMetamodels);
				return false;
			}
		}
		return true;
	}
	
	public def void addChange2CommandTransforming(Change2CommandTransforming change2CommandTransforming) {
		if (!checkForTransformerConflict(change2CommandTransforming)) {
			throw new IllegalArgumentException("Given transformer is defined for metamodel pair which another transformer already defines");
		}
		transformers += change2CommandTransforming;	
	}
	
	public def Iterable<Metamodel> getMetamodels() {
		return metamodels;
	}
	
	public def Iterable<Change2CommandTransforming> getChange2CommandTransformings() {
		return transformers;
	}
}

