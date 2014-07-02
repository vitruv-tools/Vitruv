package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils;

import java.net.URL;

public class TestUtils {
    public static boolean isURIEqual(org.eclipse.emf.common.util.URI eclipseURI, URL javaURL) {
        String eclipseURIRep = "file:" + eclipseURI.toFileString();
        String javaURLRep = javaURL.toString();
        return eclipseURIRep.equals(javaURLRep);
    }
}
