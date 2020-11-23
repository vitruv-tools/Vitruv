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

package tools.vitruv.domains.emf.monitorededitor.mocking;

import java.net.URISyntaxException;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.emf.monitorededitor.test.mocking.MockEditingDomainFactory;
import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Files;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureExecuted;

public class MockEditingDomainFactoryTests {
    private MockEditingDomainFactory factory;

    @BeforeEach
    public void setUp() {
        this.factory = new MockEditingDomainFactory();
    }

    @Test
    public void createResourceSet() throws URISyntaxException {
        EditingDomain ed = factory.createEditingDomain(Files.EXAMPLEMODEL_ECORE);
        ResourceSet rs = ed.getResourceSet();
        assert rs != null;
        assert rs.getResources().size() == 1;
        Resource r = rs.getResources().get(0);
        assert r.getContents().get(0) instanceof EPackage;
    }

    @Test
    public void createEditingDomain() {
        EditingDomain ed = factory.createEditingDomain(Files.EXAMPLEMODEL_ECORE);
        assert ed != null;
        assert ed.getRoot(EcoreFactory.eINSTANCE.createEClass()) != null;
        assert ed.getResourceSet() != null;

        ResourceSet rs = ed.getResourceSet();
        assert rs.getResources().size() == 1;
        assert rs.getResources().get(0).getContents().get(0) == ed.getRoot(EcoreFactory.eINSTANCE.createEClass());
    }

    @Test
    public void createCommandStack() {
        TransactionalEditingDomain ed = factory.createEditingDomain(Files.EXAMPLEMODEL_ECORE);
        CommandStack cs = ed.getCommandStack();
        assert cs != null;

        final EnsureExecuted ensureExecuted = new EnsureExecuted();

        cs.execute(new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                ensureExecuted.markExecuted();
            }
        });

        assert !ensureExecuted.isIndicatingFail() : "Did not execute the listener.";
    }
}
