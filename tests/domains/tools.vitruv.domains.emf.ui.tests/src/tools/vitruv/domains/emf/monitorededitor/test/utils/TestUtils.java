/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package tools.vitruv.domains.emf.monitorededitor.test.utils;

import java.net.URL;

public class TestUtils {
    public static boolean isURIEqual(org.eclipse.emf.common.util.URI eclipseURI, URL javaURL) {
        String eclipseURIRep = eclipseURI.toString();
        String javaURLRep = javaURL.toString();
        return eclipseURIRep.equals(javaURLRep);
    }
}
