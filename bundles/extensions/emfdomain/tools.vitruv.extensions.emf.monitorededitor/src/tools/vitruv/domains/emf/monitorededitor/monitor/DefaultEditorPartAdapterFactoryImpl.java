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

package tools.vitruv.domains.emf.monitorededitor.monitor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.ui.IEditorPart;

import tools.vitruv.domains.emf.monitorededitor.EditorNotMonitorableException;
import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory;

/**
 * A simple {@link IEditorPartAdapterFactory} implementation providing adapters for arbitrary EMF
 * and GMF editors.
 * 
 * The factory can be configured to refuse creating adapters except for editors whose ResourceSet
 * contains a resource having an URI with a specific suffix. For example, a
 * {@link DefaultEditorPartAdapterFactoryImpl} configured to make adapters for editors having
 * "ecore"-URI-suffixed resources are suitable for restricting the set of monitored editors to those
 * editing Ecore models.
 */
public class DefaultEditorPartAdapterFactoryImpl implements IEditorPartAdapterFactory {

    /**
     * The monitored resource URI suffixes. If empty, the restriction to suffixes is inactive.
     */
    private final Collection<String> allowedSuffixes;

    /**
     * Constructor for {@link DefaultEditorPartAdapterFactoryImpl} instances creating adapters only
     * for editors whose EMF model resource's URI has the given suffix.
     * 
     * @param resourceSuffix
     *            The URI suffix restriction.
     */
    public DefaultEditorPartAdapterFactoryImpl(String resourceSuffix) {
        this(Collections.singleton(resourceSuffix));
    }

    /**
     * Constructor for {@link DefaultEditorPartAdapterFactoryImpl} instances creating adapters only
     * for editors whose EMF model resource's URI has one of the given suffixes.
     * 
     * @param resourceSuffixes
     *            The URI suffix restriction.
     */
    public DefaultEditorPartAdapterFactoryImpl(Collection<String> resourceSuffixes) {
        this.allowedSuffixes = resourceSuffixes;
    }

    /**
     * Constructor for {@link DefaultEditorPartAdapterFactoryImpl} instances creating adapters for
     * all EMF/GMF editors.
     */
    public DefaultEditorPartAdapterFactoryImpl() {
        this(new HashSet<String>());
    }

    /**
     * @param editorPart
     *            An Eclipse editor part.
     * @return <code>true</code> iff <code>editorPart</code> can be adapted.
     */
    protected boolean isEditorAdaptable(IEditorPart editorPart) {
        return editorPart instanceof IEditingDomainProvider || editorPart instanceof DiagramDocumentEditor;
    }

    /**
     * @param adapter
     *            An adapter for an Eclipse editor part.
     * @return <code>true</code> iff <code>adapter</code> yields a monitorable resource.
     */
    protected boolean isProvidingAResource(IEditorPartAdapter adapter) {
        try {
            adapter.getEditedModelResource();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public IEditorPartAdapter createAdapter(IEditorPart editorPart) {
        if (!isEditorAdaptable(editorPart)) {
            throw new EditorNotMonitorableException();
        }

        IEditorPartAdapter result = new DefaultIEditorPartAdapter(editorPart);

        // If the EMF model resource does not match the URI suffix restriction,
        // an exception needs to be thrown.
        if (isProvidingAResource(result)) {
            return result;
        } else {
            throw new EditorNotMonitorableException();
        }
    }

    /**
     * The default IEditorPartAdapter, adapting EMF and GMF editors.
     */
    private class DefaultIEditorPartAdapter implements IEditorPartAdapter {
        private final IEditorPart editorPart;

        public DefaultIEditorPartAdapter(IEditorPart editorPart) {
            this.editorPart = editorPart;
        }

        @Override
        public EditingDomain getEditingDomain() {
            if (editorPart instanceof IEditingDomainProvider) {
                // EMF tree editor case
                return ((IEditingDomainProvider) editorPart).getEditingDomain();
            } else if (editorPart instanceof DiagramDocumentEditor) {
                // GMF editor case
                DiagramDocumentEditor dde = (DiagramDocumentEditor) editorPart;
                return dde.getEditingDomain();
            } else {
                throw new IllegalArgumentException("Don't know how to handle class "
                        + editorPart.getClass().getCanonicalName());
            }
        }

        private boolean endsWithAnySuffix(String str, Collection<String> suffixes) {
            for (String suffix : suffixes) {
                if (str.endsWith(suffix)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Resource getEditedModelResource() {
            Resource result = null;

            for (Resource r : getEditingDomain().getResourceSet().getResources()) {

                String rPath = r.getURI().fileExtension();
                if (!allowedSuffixes.isEmpty() && endsWithAnySuffix(rPath, allowedSuffixes)) {
                    if (r.getURI().isPlatform() || r.getURI().isFile()) {
                        result = r;
                    }
                } else if (allowedSuffixes.isEmpty() && !rPath.endsWith("diagram")) {
                    // TODO: This is crummy, There needs to be a better way
                    // of determining wheter r is a diagram resource...
                    result = r;
                }
            }

            if (result == null) {
                throw new NoSuchElementException();
            } else {
                return result;
            }
        }

        @Override
        public IEditorPart getEditorPart() {
            return editorPart;
        }

        @Override
        public void executeCommand(final Runnable r) {
            if (editorPart instanceof DiagramDocumentEditor) {
                // GMF editors use transactional editing domains. Thus,
                // the command needs to be executed within a transaction.
                RecordingCommand rc = new RecordingCommand((TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        r.run();
                    }
                };
                getEditingDomain().getCommandStack().execute(rc);
                getEditingDomain().getCommandStack().undo();
            } else {
                // EMF tree editors use regular editing domains, thus the
                // Runnable can be executed directly.
                r.run();
            }
        }
    }
}
