package tools.vitruvius.domains.jml.monitorededitor.changeinjection;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import tools.vitruvius.domains.jml.language.jML.CompilationUnit;
import tools.vitruvius.applications.jmljava.changesynchronizer.ChangeSynchronizerRegistry;
import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.userinteraction.impl.UserInteractorDialog;

/**
 * Base class for all JML injection handlers.
 */
public abstract class JMLInjectionHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // https://gist.github.com/shosoai/5991752
        boolean success = false;
        IWorkbenchWindow activeWindow = HandlerUtil.getActiveWorkbenchWindow(event);
        if (activeWindow == null) {
            return null;
        }
        IWorkbenchPage page = activeWindow.getActivePage();
        if (page == null) {
            return null;
        }
        IEditorPart editor = page.getActiveEditor();
        if (editor instanceof XtextEditor) {
            XtextEditor xEditor = (XtextEditor) editor;
            page.saveEditor(xEditor, false);
            IXtextDocument doc = xEditor.getDocument();
            success = doc.modify(new IUnitOfWork<Boolean, XtextResource>() {
                @Override
                public Boolean exec(XtextResource state) throws Exception {
                    for (EObject obj : state.getContents()) {
                        if (obj instanceof CompilationUnit) {
                            return performChangeOn((CompilationUnit) obj);
                        }
                    }
                    return false;
                }
            });
        }

        if (!success) {
            UserInteractorDialog.showMessage(activeWindow.getShell(), true, "The operation could not be applied.");
        }
        return null;
    }

    /**
     * Performs the changes on the given model and submits the resulting changes. The parameters
     * must NOT be passed to other modules since they might be destroyed anytime. You are encouraged
     * to create a copy of it and use it.
     * 
     * @param cu
     *            The root object of the model.
     * @return If the change has been applied and the changes have been submitted.
     */
    protected abstract boolean performChangeOn(CompilationUnit cu);

    /**
     * Submits the given change asynchronously.
     * 
     * @param change
     *            The change to be submitted.
     */
    protected void submitChange(final VitruviusChange change) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().synchronizeChange(change);
            }
        }).start();
    }

    /**
     * Submits the given changes asynchronously.
     * 
     * @param changes
     *            The changes to be submitted.
     */
    protected void submitChanges(final List<VitruviusChange> changes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChangeSynchronizerRegistry.getInstance().getChangeSynchronizer().synchronizeChanges(changes);
            }
        }).start();
    }

}
