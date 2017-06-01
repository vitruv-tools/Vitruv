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
		val nrOfVURIs = 10
		val Set<VURI> vuriSet = new HashSet<VURI>
		val Set<String> stringSet = new HashSet<String>
		for (i : 0 ..< nrOfVURIs) {
			val String testKey = '''testKey_«i»'''
			stringSet.add(testKey)
			vuriSet.add(VURI::getInstance(testKey))
		}
		val Set<VURI> vuriCompareSet = new HashSet<VURI>
		stringSet.forEach[vuriCompareSet.add(VURI::getInstance(it))]
		assertEquals("VURI compare set does not match original VURI set", vuriSet, vuriCompareSet)
	}

	@Test
	def void testPlatformResourceKey() {
		val testKeyWithPlatformRes = '''«VitruviusConstants::platformResourcePrefix»testResource'''
		val orgVURI = testKeyWithPlatformRes.testWithKey
		orgVURI.ensureStartsWithPlatformResource
		assertEquals("Platform resource string does not equal the EMF URI string of the VURI", orgVURI.EMFUri.toString,
			testKeyWithPlatformRes)
	}

	@Test
	def void testStringToPlatformResourceTest() {
		val testKey = "testKey"
		val vuri = testKey.testWithKey
		vuri.ensureStartsWithPlatformResource
	}

	@Test
	def void testHTTPKey() {
		val testHttpKey = "http://www.test.org"
		val vuri = testHttpKey.testWithKey
		testHttpKey.ensureEquals(vuri.EMFUri.toString)
	}

	@Test
	def void testPlatformDependentAbsolutePathKey() {
		// creating file URIs is platform specific so we can not write a test for another operating
		// system than the one which runs the Java VM
		val absolutePath = if (SystemUtils.IS_OS_WINDOWS) "C:\\Test\\bla" else "/Test/bla"
		val vuri = absolutePath.testWithKey
		ensureEquals(absolutePath.toLowerCase, vuri.EMFUri.toFileString.toLowerCase)
	}

	@Test
	def void testPlatformDependentAbsolutePathUri() {
		// creating file URIs is platform specific so we can not write a test for another operating
		// system than the one which runs the Java VM
		val absolutePath = if (SystemUtils.IS_OS_WINDOWS) "C:\\Test\\bla" else "/Test/bla"
		val absolutePathUri = URI::createFileURI(absolutePath)
		val absolutePathVURI = VURI::getInstance(absolutePathUri)
		assertEquals(absolutePathUri, absolutePathVURI.EMFUri)
	}

	def private VURI testWithKey(String testKeyWithPlatformRes) {
		val vuriOrg = VURI::getInstance(testKeyWithPlatformRes)
		val vuriCompare = VURI::getInstance(testKeyWithPlatformRes)
		val vuriEmfCompare = VURI::getInstance(vuriOrg.getEMFUri())
		assertEquals("Original VURI is not the same VURI as vuriCompare", vuriOrg, vuriCompare)
		assertEquals("Original VURI is not the same VURI as vuriEmfCompare", vuriOrg, vuriEmfCompare)
		vuriOrg
	}

	def private void ensureStartsWithPlatformResource(VURI orgVURI) {
		assertTrue("VURI does not start with platform resource",
			orgVURI.EMFUri.toString.startsWith(VitruviusConstants::platformResourcePrefix))
	}

	def private void ensureEquals(String orgString, String compString) {
		assertEquals("EMF URI string does not equal original String", orgString, compString)
	}
}
