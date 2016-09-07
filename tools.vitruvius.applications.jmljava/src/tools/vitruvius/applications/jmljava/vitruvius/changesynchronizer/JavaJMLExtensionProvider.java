package tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer;

import tools.vitruvius.applications.jmljava.correspondences.CSTCorrespondenceProvider;
import tools.vitruvius.applications.jmljava.mappings.CSTMappingProvider;
import tools.vitruvius.applications.jmljava.metamodels.JMLMetaModelProvider;
import tools.vitruvius.applications.jmljava.metamodels.JaMoPPMetaModelProvider;
import tools.vitruvius.applications.jmljava.models.JMLModelURIProvider;
import tools.vitruvius.applications.jmljava.models.JavaModelURIProvider;
import tools.vitruvius.applications.jmljava.synchronizers.CSTEMFModelTransformationExecutingProvider;
import tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.Change2CommandTransformingProvider;
import tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.CorrespondenceProvider;
import tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.MappingProvider;
import tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.MetaModelProvider;
import tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.ModelURIProvider;

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
