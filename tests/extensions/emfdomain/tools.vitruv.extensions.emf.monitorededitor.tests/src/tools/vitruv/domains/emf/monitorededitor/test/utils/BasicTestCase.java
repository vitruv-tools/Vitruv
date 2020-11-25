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

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.testutils.TestLogging;

@ExtendWith(TestLogging.class)
public class BasicTestCase {
    public static final String PLUGIN_NAME = "tools.vitruv.extensions.emf.monitorededitor.tests";
    protected Logger LOGGER = Logger.getLogger(BasicTestCase.class);

    @BeforeAll
    static void setupLog4J() {
        org.apache.log4j.BasicConfigurator.configure();
    }

    public static URI getURI(URL modelURL) {
        return URI.createPlatformPluginURI(PLUGIN_NAME + modelURL.getFile(), true);
    }
}
