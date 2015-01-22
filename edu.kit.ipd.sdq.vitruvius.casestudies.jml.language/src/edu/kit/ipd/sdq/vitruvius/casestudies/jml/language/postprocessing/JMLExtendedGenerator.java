package edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.postprocessing;

import org.eclipse.xtext.XtextRuntimeModule;
import org.eclipse.xtext.XtextStandaloneSetup;
import org.eclipse.xtext.generator.Generator;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.postprocessing.JMLPostProcessor;

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
            @Override
            public Injector createInjector() {
                return Guice.createInjector(new XtextRuntimeModule() {
                    @Override
                    public Class<? extends IXtext2EcorePostProcessor> bindIXtext2EcorePostProcessor() {
                        return JMLPostProcessor.class;
                    }
                });
            }
        } .createInjectorAndDoEMFRegistration();
    }

}
