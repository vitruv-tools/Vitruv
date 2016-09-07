package tools.vitruvius.applications.jmljava.metamodels;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.xtext.resource.SaveOptions;

import tools.vitruvius.applications.jmljava.metamodels.JMLTUIDCalculatorAndResolver;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.framework.metamodel.Metamodel;
import tools.vitruvius.framework.util.datatypes.VURI;

/**
 * Provider for the JML meta model.
 */
public class JMLMetaModelProvider extends MetaModelProviderBase {

	public static final String[] NS_URIS = { JMLPackage.eINSTANCE.getNsURI() };
	public static final String[] EXTENSIONS = { "jml" };
	public static final VURI URI = VURI
			.getInstance(JMLPackage.eINSTANCE.getNsURI());

	@Override
	public void registerFactory() {
		// new JMLStandaloneSetupGenerated().createInjectorAndDoEMFRegistration();
		
	}

	@Override
	protected Metamodel constructMetaModel() {
		Set<String> nsURIs = new HashSet<String>(Arrays.asList(NS_URIS));
		Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		SaveOptions.newBuilder().format().noValidation().getOptions().addTo(saveOptions);
		return new Metamodel(nsURIs, URI, new JMLTUIDCalculatorAndResolver(),
				Collections.emptyMap(), saveOptions, EXTENSIONS);
	}

}
