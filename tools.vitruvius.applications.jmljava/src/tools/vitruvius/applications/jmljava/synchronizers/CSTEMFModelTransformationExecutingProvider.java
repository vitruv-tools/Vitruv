package tools.vitruvius.applications.jmljava.synchronizers;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;

import tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.extensions.Change2CommandTransformingProvider;
import tools.vitruvius.applications.jmljava.synchronizers.CSSynchronizer;
import tools.vitruvius.framework.change.processing.Change2CommandTransforming ;

/**
 * Provider for EMF transformation executings. It is used to create and provide
 * the {@link CSSynchronizer}.
 */
public class CSTEMFModelTransformationExecutingProvider implements
		Change2CommandTransformingProvider {

	@Override
	public List<Change2CommandTransforming> getEMFModelTransformationExecutings() {
		List<Change2CommandTransforming> result = new ArrayList<Change2CommandTransforming>();

		Injector injector = Guice.createInjector(new CSSynchronizerModule());
		result.add(injector.getInstance(CSSynchronizer.class));

		return result;
	}

}