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

import org.eclipse.core.runtime.IProgressMonitor;

public class EclipseProgressMonitorDummyFactory {
    public static final IProgressMonitor DUMMY = createProgressMonitorDummy();

    public static IProgressMonitor createProgressMonitorDummy() {
        IProgressMonitor result = createNiceMock(IProgressMonitor.class);
        reset(result);
        replay(result);
        return result;
    }
}
