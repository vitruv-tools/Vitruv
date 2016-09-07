package tools.vitruvius.domains.java.util.gitchangereplay.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.Bundle;

import com.github.gumtreediff.gen.jdt.JdtTreeGenerator;
import com.github.gumtreediff.tree.ITree;

import tools.vitruvius.domains.java.util.gitchangereplay.converters.GumTree2JdtAstConverter;
import tools.vitruvius.domains.java.util.gitchangereplay.converters.GumTree2JdtAstConverterImpl;

public class GumTree2JdtAstConverterTest {
	
	private static final Logger logger = Logger.getLogger(GumTree2JdtAstConverterTest.class.getName());
	
	private static final String TEST_BUNDLE_NAME = "tools.vitruvius.domains.java.util.gitchangereplay.tests";
	private static final String TEST_FILE_NAME = "testrepo/Test.java";
	
	@Test
	public void testSimpleJavaFileConversion() throws IOException, URISyntaxException {
		Bundle bundle = Platform.getBundle(TEST_BUNDLE_NAME);
		URL testFileURL = bundle.getEntry(TEST_FILE_NAME);
		URL fileURL = FileLocator.resolve(testFileURL);
		File file = new File(fileURL.toURI());
		ITree tree = new JdtTreeGenerator().generateFromFile(file).getRoot();
		
		GumTree2JdtAstConverter converter = new GumTree2JdtAstConverterImpl();
		CompilationUnit cu = converter.convertTree(tree);
		Assert.assertNotNull(cu);
		logger.info("\n\n" + converter.getLastConvertedAsText());
	}
	

}
