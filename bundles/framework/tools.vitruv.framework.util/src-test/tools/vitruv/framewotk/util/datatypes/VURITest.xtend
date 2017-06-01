package tools.vitruv.framewotk.util.datatypes

import java.util.HashSet
import java.util.Set
import org.apache.commons.lang.SystemUtils
import org.eclipse.emf.common.util.URI
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.util.VitruviusConstants
import tools.vitruv.framework.util.datatypes.VURI
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class VURITest {
	@Before
	def void setUp() throws Exception {
	}

	@Test
	def void testCreateVURIs() {
		var int nrOfVURIs = 10
		var Set<VURI> vuriSet = new HashSet<VURI>()
		var Set<String> stringSet = new HashSet<String>()
		for (var int i = 0; i < nrOfVURIs; {
			i = i + 1
		}) {
			var String testKey = '''testKey_«i»'''
			stringSet.add(testKey)
			vuriSet.add(VURI.getInstance(testKey))
		}
		var Set<VURI> vuriCompareSet = new HashSet<VURI>()
		for (String str : stringSet) {
			vuriCompareSet.add(VURI.getInstance(str))
		}
		assertEquals("VURI compare set does not match original VURI set", vuriSet, vuriCompareSet)
	}

	@Test
	def void testPlatformResourceKey() {
		var String testKeyWithPlatformRes = '''«VitruviusConstants.getPlatformResourcePrefix()»testResource'''
		var VURI orgVURI = testWithKey(testKeyWithPlatformRes)
		ensureStartsWithPlatformResource(orgVURI)
		assertEquals("Platform resource string does not equal the EMF URI string of the VURI",
			orgVURI.getEMFUri().toString(), testKeyWithPlatformRes)
	}

	@Test
	def void testStringToPlatformResourceTest() {
		var String testKey = "testKey"
		var VURI vuri = testWithKey(testKey)
		ensureStartsWithPlatformResource(vuri)
	}

	@Test
	def void testHTTPKey() {
		var String testHttpKey = "http://www.test.org"
		var VURI vuri = testWithKey(testHttpKey)
		ensureEquals(testHttpKey, vuri.getEMFUri().toString())
	}

	@Test
	def void testPlatformDependentAbsolutePathKey() {
		// creating file URIs is platform specific so we can not write a test for another operating
		// system than the one which runs the Java VM
		var String absolutePath = "/Test/bla"
		if (SystemUtils.IS_OS_WINDOWS) {
			absolutePath = "C:\\Test\\bla"
		}
		var VURI vuri = testWithKey(absolutePath)
		ensureEquals(absolutePath.toLowerCase(), vuri.getEMFUri().toFileString().toLowerCase())
	}

	@Test
	def void testPlatformDependentAbsolutePathUri() {
		// creating file URIs is platform specific so we can not write a test for another operating
		// system than the one which runs the Java VM
		var String absolutePath = "/Test/bla"
		if (SystemUtils.IS_OS_WINDOWS) {
			absolutePath = "C:\\Test\\bla"
		}
		var URI absolutePathUri = URI.createFileURI(absolutePath)
		var VURI absolutePathVURI = VURI.getInstance(absolutePathUri)
		assertEquals(absolutePathUri, absolutePathVURI.getEMFUri())
	}

	def private VURI testWithKey(String testKeyWithPlatformRes) {
		var VURI vuriOrg = VURI.getInstance(testKeyWithPlatformRes)
		var VURI vuriCompare = VURI.getInstance(testKeyWithPlatformRes)
		var VURI vuriEmfCompare = VURI.getInstance(vuriOrg.getEMFUri())
		assertEquals("Original VURI is not the same VURI as vuriCompare", vuriOrg, vuriCompare)
		assertEquals("Original VURI is not the same VURI as vuriEmfCompare", vuriOrg, vuriEmfCompare)
		return vuriOrg
	}

	def private void ensureStartsWithPlatformResource(VURI orgVURI) {
		assertTrue("VURI does not start with platform resource",
			orgVURI.getEMFUri().toString().startsWith(VitruviusConstants.getPlatformResourcePrefix()))
	}

	def private void ensureEquals(String orgString, String compString) {
		assertEquals("EMF URI string does not equal original String", orgString, compString)
	}
}
