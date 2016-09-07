package tools.vitruvius.domains.jml.language.postprocessing;

import org.eclipse.xtext.XtextRuntimeModule;
import org.eclipse.xtext.XtextStandaloneSetup;
import org.eclipse.xtext.generator.Generator;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

import com.google.inject.Guice;
import com.google.inject.Injector;

import tools.vitruvius.domains.jml.language.postprocessing.JMLPostProcessor;

/**
 * Generator for JML, which incorporates the custom post processor
 * 
 * @see http://christiandietrich.wordpress.com/2011/07/22/customizing-xtext-metamodel-inference-using-xtend2/
 */
public class JMLExtendedGenerator extends Generator {

    /**
     * Constructs and initializes the generator.
     */
    public JMLExtendedGenerator() {
    	new XtextStandaloneSetup() {
			public Injector createInjector() {
				return Guice.createInjector(new XtextRuntimeModule() {
					public void configureIXtext2EcorePostProcessor(com.google.inject.Binder binder) {
						binder.bind(IXtext2EcorePostProcessor.class).to(JMLPostProcessor.class);
					};
				});				
			};			
		}.createInjectorAndDoEMFRegistration();
    }

}
