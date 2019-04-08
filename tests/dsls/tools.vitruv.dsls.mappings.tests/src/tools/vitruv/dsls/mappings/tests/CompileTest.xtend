package tools.vitruv.dsls.mappings.tests

import org.junit.runner.RunWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.InjectWith
import com.google.inject.Inject
import org.eclipse.xtext.testing.util.ParseHelper
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile
import org.eclipse.xtext.generator.IGenerator2
import org.junit.Test
import com.google.inject.Provider
import org.eclipse.xtext.generator.InMemoryFileSystemAccess
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.emf.common.util.URI
import tools.vitruv.dsls.mappings.MappingsLanguageStandaloneSetup

@RunWith(XtextRunner)
@InjectWith(MappingsLanguageInjectorProvider)
class CompileTest {
	
	
   	@Inject Provider<InMemoryFileSystemAccess> fsaProvider
	@Inject Provider<IGenerator2> generatorProvider
	@Inject Provider<XtextResourceSet> resourceSetProvider
   
    @Test
    def void test(){
    	//new MappingsLanguageStandaloneSetup().createInjectorAndDoEMFRegistration()
    	var generator = generatorProvider.get()
		var resourceSet = resourceSetProvider.get()
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE)
		
		val fsa = fsaProvider.get() => [
			currentSource = "src"
			outputPath = "src-gen"
		]
		
    	val resource = resourceSet.getResource(
   		URI.createURI("platform:/plugin/tools.vitruv.dsls.mappings.tests/src/tools/vitruv/dsls/mappings/tests/test.mappings"), true);
    	generator.doGenerate(resource, fsa, null)
    }
}