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

package tools.vitruv.domains.emf.monitorededitor.test.testmodels;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import tools.vitruv.domains.emf.monitorededitor.test.utils.BasicTestCase;

public class Models {
    private static Logger LOGGER = Logger.getLogger(Models.class);

    public static final String ROOT_OBJECT_URI = "/";

    public static Resource loadModel(URL modelURL) {

        ResourceSet resSet = new ResourceSetImpl();
        resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
                new XMIResourceFactoryImpl());

        EcoreResourceFactoryImpl ecoreResFact = new EcoreResourceFactoryImpl();
        URI fileName = BasicTestCase.getURI(modelURL);
        LOGGER.info("Trying to load " + fileName);
        Resource ecoreRes = ecoreResFact.createResource(fileName);
        try {
            ecoreRes.load(null);
        } catch (IOException e) {
            fail("Could not load " + Files.EXAMPLEMODEL_ECORE.getFile() + ". Reason: " + e);
        }

        resSet.getResources().add(ecoreRes);

        return ecoreRes;
    }

    public static void unloadModel(Resource res) {
        LOGGER.info("Unloading model " + res.getURI().toFileString());
        res.unload();
    }
}
