package tools.vitruv.dsls.mappings.postprocessor;

import org.eclipse.xtext.XtextRuntimeModule;
import org.eclipse.xtext.XtextStandaloneSetup;
//import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;
import org.eclipse.xtext.xtext.generator.XtextGenerator;

import com.google.inject.Guice;
import com.google.inject.Injector;

// TODO MK: refactor commonalities with ReactionsLanguageExtendedGenerator
@SuppressWarnings("restriction")
public class MappingsLanguageExtendedGenerator extends XtextGenerator {
	public MappingsLanguageExtendedGenerator() {
		new XtextStandaloneSetup() {
			public Injector createInjector() {
				return Guice.createInjector(new XtextRuntimeModule() {
					public void configureIXtext2EcorePostProcessor(com.google.inject.Binder binder) {
//						binder.bind(IXtext2EcorePostProcessor.class).to(ReactionsLanguageXtext2EcorePostProcessor.class);
					};
				});				
			};			
		}.createInjectorAndDoEMFRegistration();
	}
}
