package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.Bundle;

import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors.GumTreeChangeExtractor;

public class GumTreeChangeExtractorTest {
	
	private static final String TEST_BUNDLE_NAME = "edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.tests";
	private static final String TEST_REAL_SAMPLE1 = "samples/real_version1.java";
	private static final String TEST_REAL_SAMPLE2 = "samples/real_version2.java";
	private static final String TEST_CONCAT_SAMPLE1 = "samples/concat_version1.java";
	private static final String TEST_CONCAT_SAMPLE2 = "samples/concat_version2.java";
	private static final String TEST_WRAP_SAMPLE1 = "samples/wrap_assignment_version1.java";
	private static final String TEST_WRAP_SAMPLE2 = "samples/wrap_assignment_version2.java";
	private static final String TEST_PARAMS_SAMPLE1 = "samples/params_version1.java";
	private static final String TEST_PARAMS_SAMPLE2 = "samples/params_version2.java";
	private static final String TEST_MISSING_SAMPLE1 = "samples/MISSING_version1.java";
	private static final String TEST_MISSING_SAMPLE2 = "samples/MISSING_version2.java";
	
	@Test
	public void testRealExampleContent() throws IOException, URISyntaxException {
		String content1 = readFile(TEST_REAL_SAMPLE1);
		String content2 = readFile(TEST_REAL_SAMPLE2);
		URI uri = URI.createFileURI(TEST_REAL_SAMPLE2);
		
		GumTreeChangeExtractor extractor = new GumTreeChangeExtractor(content1, content2, uri, false);
		List<String> results = extractor.extract();
		
		// Compare the last state of the working tree, to the direct representation of the dst tree
		Assert.assertEquals(results.get(results.size() - 1), results.get(results.size() - 2));
	}
	
	@Test
	public void testConcatUpdate() throws IOException, URISyntaxException {
		String content1 = readFile(TEST_CONCAT_SAMPLE1);
		String content2 = readFile(TEST_CONCAT_SAMPLE2);
		URI uri = URI.createFileURI(TEST_CONCAT_SAMPLE2);
		
		GumTreeChangeExtractor extractor = new GumTreeChangeExtractor(content1, content2, uri, false);
		List<String> results = extractor.extract();
		
		// Compare the last state of the working tree, to the direct representation of the dst tree
		Assert.assertEquals(results.get(results.size() - 1), results.get(results.size() - 2));
	}
	
	@Test
	public void testWrapUpdate() throws IOException, URISyntaxException {
		String content1 = readFile(TEST_WRAP_SAMPLE1);
		String content2 = readFile(TEST_WRAP_SAMPLE2);
		URI uri = URI.createFileURI(TEST_CONCAT_SAMPLE2);
		
		GumTreeChangeExtractor extractor = new GumTreeChangeExtractor(content1, content2, uri, false);
		List<String> results = extractor.extract();
		
		// Compare the last state of the working tree, to the direct representation of the dst tree
		Assert.assertEquals(results.get(results.size() - 1), results.get(results.size() - 2));
	}
	
	@Test
	public void testMISSING() throws IOException, URISyntaxException {
		String content1 = readFile(TEST_MISSING_SAMPLE1);
		String content2 = readFile(TEST_MISSING_SAMPLE2);
		URI uri = URI.createFileURI(TEST_CONCAT_SAMPLE2);
		
		GumTreeChangeExtractor extractor = new GumTreeChangeExtractor(content1, content2, uri, true);
		List<String> results = extractor.extract();
		
		for (String content : results) {
			Assert.assertFalse(content.contains("MISSING"));
		}
	}
	
	@Test
	public void testParamsUpdate() throws IOException, URISyntaxException {
		String content1 = readFile(TEST_PARAMS_SAMPLE1);
		String content2 = readFile(TEST_PARAMS_SAMPLE2);
		URI uri = URI.createFileURI(TEST_CONCAT_SAMPLE2);
		
		GumTreeChangeExtractor extractor = new GumTreeChangeExtractor(content1, content2, uri, false);
		List<String> results = extractor.extract();
		
		// Compare the last state of the working tree, to the direct representation of the dst tree
		Assert.assertEquals(results.get(results.size() - 1), results.get(results.size() - 2));
	}

	private String readFile(String relativePath) throws IOException, URISyntaxException {
		Bundle bundle = Platform.getBundle(TEST_BUNDLE_NAME);
		URL testFileURL = bundle.getEntry(relativePath);
		URL fileURL = FileLocator.resolve(testFileURL);
		String content = readFile(fileURL.toURI(), StandardCharsets.UTF_8);
		return content;
	}

	static String readFile(java.net.URI fileUri, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(fileUri));
		return new String(encoded, encoding);
	}

}
