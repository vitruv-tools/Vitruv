package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CSSynchronizer;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.extensions.EMFModelTransformationExecutingProvider;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting;

/**
 * Provider for EMF transformation executings. It is used to create and provide
 * the {@link CSSynchronizer}.
 */
public class CSTEMFModelTransformationExecutingProvider implements
		EMFModelTransformationExecutingProvider {

	@Override
	public List<EMFModelTransformationExecuting> getEMFModelTransformationExecutings() {
		List<EMFModelTransformationExecuting> result = new ArrayList<EMFModelTransformationExecuting>();

		Injector injector = Guice.createInjector(new CSSynchronizerModule());
		result.add(injector.getInstance(CSSynchronizer.class));

		return result;
	}

}