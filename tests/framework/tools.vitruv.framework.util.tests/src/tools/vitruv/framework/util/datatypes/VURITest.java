package tools.vitruv.framework.util.datatypes;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.SystemUtils;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.vitruv.framework.util.VitruviusConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VURITest {

	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateVURIs() {
		int nrOfVURIs = 10;
		Set<VURI> vuriSet = new HashSet<VURI>();
		Set<String> stringSet = new HashSet<String>();
		for (int i = 0; i < nrOfVURIs; ++i) {
			String testKey = "testKey_" + i;
			stringSet.add(testKey);
			vuriSet.add(VURI.getInstance(testKey));
		}
		Set<VURI> vuriCompareSet = new HashSet<VURI>();
		for (String str : stringSet) {
			vuriCompareSet.add(VURI.getInstance(str));
		}

		assertEquals(vuriSet, vuriCompareSet, "VURI compare set does not match original VURI set");
	}

	@Test
	public void testPlatformResourceKey() {
		String testKeyWithPlatformRes = VitruviusConstants.getPlatformResourcePrefix() + "testResource";
		VURI orgVURI = testWithKey(testKeyWithPlatformRes);
		ensureStartsWithPlatformResource(orgVURI);
		assertEquals(orgVURI.getEMFUri().toString(), testKeyWithPlatformRes,
				"Platform resource string does not equal the EMF URI string of the VURI");
	}

	@Test
	public void testStringToPlatformResourceTest() {
		String testKey = "testKey";
		VURI vuri = testWithKey(testKey);
		ensureStartsWithPlatformResource(vuri);
	}

	@Test
	public void testHTTPKey() {
		String testHttpKey = "http://www.test.org";
		VURI vuri = testWithKey(testHttpKey);
		ensureEquals(testHttpKey, vuri.getEMFUri().toString());
	}

	@Test
	public void testPlatformDependentAbsolutePathKey() {
		// creating file URIs is platform specific so we can not write a test for
		// another operating
		// system than the one which runs the Java VM
		String absolutePath = "/Test/bla";
		if (SystemUtils.IS_OS_WINDOWS) {
			absolutePath = "C:\\Test\\bla";
		}
		VURI vuri = testWithKey(absolutePath);
		ensureEquals(absolutePath.toLowerCase(), vuri.getEMFUri().toFileString().toLowerCase());
	}

	@Test
	public void testPlatformDependentAbsolutePathUri() {
		// creating file URIs is platform specific so we can not write a test for
		// another operating
		// system than the one which runs the Java VM
		String absolutePath = "/Test/bla";
		if (SystemUtils.IS_OS_WINDOWS) {
			absolutePath = "C:\\Test\\bla";
		}
		URI absolutePathUri = URI.createFileURI(absolutePath);
		VURI absolutePathVURI = VURI.getInstance(absolutePathUri);
		assertEquals(absolutePathUri, absolutePathVURI.getEMFUri());
	}

	private VURI testWithKey(final String testKeyWithPlatformRes) {
		VURI vuriOrg = VURI.getInstance(testKeyWithPlatformRes);
		VURI vuriCompare = VURI.getInstance(testKeyWithPlatformRes);
		VURI vuriEmfCompare = VURI.getInstance(vuriOrg.getEMFUri());

		assertEquals(vuriOrg, vuriCompare, "Original VURI is not the same VURI as vuriCompare");
		assertEquals(vuriOrg, vuriEmfCompare, "Original VURI is not the same VURI as vuriEmfCompare");
		return vuriOrg;
	}

	private void ensureStartsWithPlatformResource(final VURI orgVURI) {
		assertTrue(orgVURI.getEMFUri().toString().startsWith(VitruviusConstants.getPlatformResourcePrefix()),
				"VURI does not start with platform resource");
	}

	private void ensureEquals(final String orgString, final String compString) {
		assertEquals(orgString, compString, "EMF URI string does not equal original String");
	}
}
