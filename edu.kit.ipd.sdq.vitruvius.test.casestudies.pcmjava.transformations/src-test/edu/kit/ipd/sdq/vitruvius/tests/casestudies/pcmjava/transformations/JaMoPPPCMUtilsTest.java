package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.emftext.language.java.containers.CompilationUnit;
import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMUtils;
import edu.kit.ipd.sdq.vitruvius.tests.jamopppcm.util.JaMoPPPCMTestUtil;

/**
 * Test for the JaMoPPPCMUtil class - not a utility class. Tests the textual creation of a
 * CompilationUnit using JaMoPP.
 *
 * @author Langhamm
 *
 */
public class JaMoPPPCMUtilsTest {

    @Before
    public void setUp() throws Exception {
        JaMoPPPCMTestUtil.registerMetamodels();
    }

    @Test
    public void testCreateCompilationUnit() throws IOException {
        final String className = "TestCollectionDataType";
        final Class<? extends Collection> selectedClass = ArrayList.class;
        final String content = "package " + "datatypes;" + "\n\n" + "import " + selectedClass.getPackage().getName()
                + "." + selectedClass.getSimpleName() + ";\n\n" + "public class " + className + " extends "
                + selectedClass.getSimpleName() + "<" + "String" + ">" + " {\n" + "\n\n" + "}";
        final CompilationUnit cu = JaMoPPPCMUtils.createCompilationUnit(className, content);

        assertEquals("CompilationUnit name is wrong", cu.getName(), className + ".java");
        assertTrue("No classifier in compliation unit", cu.getClassifiers().size() == 1);
        assertEquals("ClassifierName name is wrong", cu.getClassifiers().get(0).getName(), className);
    }
}
