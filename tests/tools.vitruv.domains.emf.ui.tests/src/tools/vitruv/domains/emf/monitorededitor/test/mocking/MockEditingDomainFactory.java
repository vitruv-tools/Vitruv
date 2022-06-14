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
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.net.URL;

import org.easymock.IAnswer;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;

import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Models;

public class MockEditingDomainFactory {
    public TransactionalEditingDomain createEditingDomain(URL modelURL) {
        TransactionalEditingDomain result = createNiceMock(InternalTransactionalEditingDomain.class);
        ResourceSet editorRS = createResourceSet(modelURL);
        CommandStack commandStack = createCommandStack();
        EObject root = editorRS.getResources().get(0).getContents().get(0);

        reset(result);
        expect(result.getResourceSet()).andReturn(editorRS).anyTimes();
        expect(result.getRoot(isA(EObject.class))).andReturn(root).anyTimes();
        expect(result.getCommandStack()).andReturn(commandStack).anyTimes();

        replay(result);
        return result;
    }

    protected ResourceSet createResourceSet(URL modelURL) {
        Resource modelResource = Models.loadModel(modelURL);
        return modelResource.getResourceSet();
    }

    protected CommandStack createCommandStack() {
        CommandStack result = createNiceMock(CommandStack.class);
        reset(result);

        result.execute(isA(Command.class));
        expectLastCall().andAnswer(new IAnswer<Object>() {

            @Override
            public Object answer() throws Throwable {
                Object[] args = getCurrentArguments();
                assert args.length == 1;
                assert args[0] instanceof Command;

                Command cmd = (Command) args[0];
                if (cmd instanceof RecordingCommand) {
                    RecordingCommand rc = (RecordingCommand) cmd;
                    rc.execute();
                }

                return cmd;
            }
        }).anyTimes();
        replay(result);
        return result;
    }
}
