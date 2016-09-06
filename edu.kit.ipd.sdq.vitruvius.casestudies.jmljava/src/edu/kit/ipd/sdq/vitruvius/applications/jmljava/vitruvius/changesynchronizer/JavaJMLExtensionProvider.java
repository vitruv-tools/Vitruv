package edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.correspondences.CSTCorrespondenceProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.mappings.CSTMappingProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels.JMLMetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels.JaMoPPMetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.models.JMLModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.models.JavaModelURIProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.CSTEMFModelTransformationExecutingProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.Change2CommandTransformingProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.CorrespondenceProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.MappingProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.MetaModelProvider;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;

public class JavaJMLExtensionProvider {

	public static MetaModelProvider[] getMetaModelProvider() {
		return getArray(new JaMoPPMetaModelProvider(), new JMLMetaModelProvider());
	}
	
	public static MappingProvider[] getMappingProviders() {
		return getArray(new CSTMappingProvider());
	}
	
	public static CorrespondenceProvider[] getCorrespondenceProviders() {
		return getArray(new CSTCorrespondenceProvider());
	}
	
	public static ModelURIProvider[] getModelURIProviders() {
		return getArray(new JavaModelURIProvider(), new JMLModelURIProvider());
	}
	
	public static Change2CommandTransformingProvider[] getEMFModelTransformationExecutingProviders() {
		return getArray(new CSTEMFModelTransformationExecutingProvider());
	}
	
	private static <T extends Object> T[] getArray(T... args) {
		return args;
	}
	
}
