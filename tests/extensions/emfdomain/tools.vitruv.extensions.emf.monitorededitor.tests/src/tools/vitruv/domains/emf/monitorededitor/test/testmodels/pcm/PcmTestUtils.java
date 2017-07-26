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

package tools.vitruv.domains.emf.monitorededitor.test.testmodels.pcm;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;

public final class PcmTestUtils {
    public static final String BOOL_TYPE_URI = "pathmap://PCM_MODELS/PrimitiveTypes.repository#//@dataTypes__Repository.2";

    public static DataType getBoolType(Resource pcmResource) {
        ResourceSet rs = pcmResource.getResourceSet();
        EObject boolTypeObj = rs.getEObject(
                URI.createURI("pathmap://PCM_MODELS/PrimitiveTypes.repository#//@dataTypes__Repository.2"), true);
        PrimitiveDataType boolType = (PrimitiveDataType) boolTypeObj;

        assert boolType.getType() == PrimitiveTypeEnum.BOOL;

        return boolType;
    }

    private PcmTestUtils() {

    }
}
