package tools.vitruv.applications.jmljava.vitruvius.changesynchronizer;

import tools.vitruv.applications.jmljava.correspondences.CSTCorrespondenceProvider;
import tools.vitruv.applications.jmljava.mappings.CSTMappingProvider;
import tools.vitruv.applications.jmljava.metamodels.JMLMetaModelProvider;
import tools.vitruv.applications.jmljava.metamodels.JaMoPPMetaModelProvider;
import tools.vitruv.applications.jmljava.models.JMLModelURIProvider;
import tools.vitruv.applications.jmljava.models.JavaModelURIProvider;
import tools.vitruv.applications.jmljava.synchronizers.CSTEMFModelTransformationExecutingProvider;
import tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.extensions.Change2CommandTransformingProvider;
import tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.extensions.CorrespondenceProvider;
import tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.extensions.MappingProvider;
import tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.extensions.MetaModelProvider;
import tools.vitruv.applications.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;

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
