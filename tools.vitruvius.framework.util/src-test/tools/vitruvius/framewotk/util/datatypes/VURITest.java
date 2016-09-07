package tools.vitruvius.framewotk.util.datatypes;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.SystemUtils;
import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import tools.vitruvius.framework.util.VitruviusConstants;
import tools.vitruvius.framework.util.datatypes.VURI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VURITest {

    @Before
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

        assertEquals("VURI compare set does not match original VURI set", vuriSet, vuriCompareSet);
    }

    @Test
    public void testPlatformResourceKey() {
        String testKeyWithPlatformRes = VitruviusConstants.getPlatformResourcePrefix() + "testResource";
        VURI orgVURI = testWithKey(testKeyWithPlatformRes);
        ensureStartsWithPlatformResource(orgVURI);
        assertEquals("Platform resource string does not equal the EMF URI string of the VURI", orgVURI.getEMFUri()
                .toString(), testKeyWithPlatformRes);
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
        // creating file URIs is platform specific so we can not write a test for another operating
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
        // creating file URIs is platform specific so we can not write a test for another operating
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

        assertEquals("Original VURI is not the same VURI as vuriCompare", vuriOrg, vuriCompare);
        assertEquals("Original VURI is not the same VURI as vuriEmfCompare", vuriOrg, vuriEmfCompare);
        return vuriOrg;
    }

    private void ensureStartsWithPlatformResource(final VURI orgVURI) {
        assertTrue("VURI does not start with platform resource",
                orgVURI.getEMFUri().toString().startsWith(VitruviusConstants.getPlatformResourcePrefix()));
    }

    private void ensureEquals(final String orgString, final String compString) {
        assertEquals("EMF URI string does not equal original String", orgString, compString);
    }
}
