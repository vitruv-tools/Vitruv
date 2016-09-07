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

package tools.vitruv.domains.emf.monitorededitor.test.mocking;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import org.eclipse.core.resources.IProject;

public class EclipseProjectDummyFactory {
    public static final IProject DUMMY = createDummyIProjectImpl();

    public static IProject createDummyIProjectImpl() {
        IProject result = createNiceMock(IProject.class);
        reset(result);
        replay(result);
        return result;
    }
}
