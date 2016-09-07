package tools.vitruvius.domains.jml.language.formatting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.formatting.INodeModelFormatter;
import org.eclipse.xtext.formatting.INodeModelFormatter.IFormattedRegion;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.Test;

import com.google.inject.Injector;

import tools.vitruvius.domains.jml.language.JMLInjectorProvider;



public class JMLFormatterTest {

	private static final Injector INJECTOR = createInjector();
	private static final INodeModelFormatter formatter = INJECTOR.getInstance(INodeModelFormatter.class);
	
	private static Injector createInjector() {
		JMLInjectorProvider provider = new JMLInjectorProvider();
		provider.setupRegistry();
		return provider.getInjector();
	}
	
    private static String format(String modelFileName) throws IOException {
        XtextResource resource = (XtextResource)getResource(modelFileName);
        IParseResult parseResult = resource.getParseResult();
        if (parseResult == null)
            return null;
        IFormattedRegion r = formatter.format(parseResult.getRootNode(), 0, parseResult.getRootNode().getText().length());
        return r.getFormattedText();
    }
	
    private static Resource getResource(String modelFileName) throws IOException {
    	// http://blog1.vorburger.ch/2009/08/xtext-standalone-setup-parsing-dsl-from.html
    	File tmpFile = File.createTempFile("idontcare", ".jml");
    	tmpFile.deleteOnExit();
    	
    	String resourceString = "resources/" + modelFileName;
    	InputStream resourceStream = JMLFormatterTest.class.getResourceAsStream(resourceString);
    	
    	Resource resource = new ResourceSetImpl().createResource(URI.createFileURI(tmpFile.getAbsolutePath()));
    	resource.load(resourceStream, Collections.EMPTY_MAP);            
    	return resource;
    }
    
    private static String getResourceContent(String modelFileName) throws IOException {
    	String resourceString = "resources/" + modelFileName;
    	InputStream resourceStream = JMLFormatterTest.class.getResourceAsStream(resourceString);
    	return IOUtils.toString(resourceStream);
    }
    
    @Test
	public void test() throws Exception {
		String actual = format("BasicJMLModel.jml");
		String expected = getResourceContent("BasicJMLModel.jml");
		//assertEquals(expected, actual);
	}
}
