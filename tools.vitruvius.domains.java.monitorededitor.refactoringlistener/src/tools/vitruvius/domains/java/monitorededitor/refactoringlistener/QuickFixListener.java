package tools.vitruvius.domains.java.monitorededitor.refactoringlistener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.quickassist.IQuickAssistAssistant;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;

public final class QuickFixListener implements ICompletionListener, IPartListener2 {

    private static QuickFixListener instance = null;
    private Method getSourceViewer;
    private final IWorkbench workbench;

    private QuickFixListener() {
        try {
            this.getSourceViewer = AbstractTextEditor.class.getDeclaredMethod("getSourceViewer");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        this.getSourceViewer.setAccessible(true);
        this.workbench = PlatformUI.getWorkbench();
        this.workbench.getDisplay().syncExec(new Runnable() {

            @Override
            public void run() {
//                IWorkbenchWindow window = QuickFixListener.this.workbench.getActiveWorkbenchWindow();
//                IPartService partService = window.getPartService();
                // partService.addPartListener(QuickFixListener.this);
            }
        });
    }

    public static synchronized QuickFixListener getInstance() {
        if (instance == null) {
            instance = new QuickFixListener();
        }
        return instance;
    }

    @Override
    public void selectionChanged(ICompletionProposal proposal, boolean smartToggle) {
//        this.lastProposal = proposal;
    }

    @Override
    public void assistSessionStarted(ContentAssistEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void assistSessionEnded(ContentAssistEvent event) {
        List<ChangeClassifyingEvent> changes = null;
//        if (this.lastProposal instanceof SelfEncapsulateFieldProposal) {
//            // handle getter/setter creation
//        }
        RefactoringChangeListener.getInstance().addChangeClassifyingEvents(changes);
    }

//    private List<ChangeClassifyingEvent> handleAddImportCorrectionProposal() {
//        List<ChangeClassifyingEvent> addImportEvents = new ArrayList<ChangeClassifyingEvent>();
//        String src = null;
//        try {
//            src = ((ASTRewriteCorrectionProposal) this.lastProposal).getPreviewContent();
//        } catch (CoreException e) {
//            e.printStackTrace();
//        }
//        CompilationUnit unit = JavaModel2AST.parseCompilationUnit(src.toCharArray());
//        for (String addedImport : ((ASTRewriteCorrectionProposal) this.lastProposal).getImportRewrite()
//                .getAddedImports()) {
//            ImportDeclaration importDeclaration = JavaModel2AST.getImportDeclaration(addedImport, unit);
//            // addImportEvents.add(new AddImportEvent(importDeclaration));
//        }
//        return addImportEvents;
//    }

    private void addQuickAssistantListenerToEditor(IWorkbenchPartReference part) {
        IWorkbenchPage page = part.getPage();
        AbstractTextEditor textEditor = (AbstractTextEditor) page.getActiveEditor();
        Object result;
        try {
            result = QuickFixListener.this.getSourceViewer.invoke(textEditor);
            SourceViewer sourceViewer = (SourceViewer) result;
            IQuickAssistAssistant quickAssist = sourceViewer.getQuickAssistAssistant();
            quickAssist.addCompletionListener(QuickFixListener.this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void partActivated(IWorkbenchPartReference partRef) {
        // TODO Auto-generated method stub
        addQuickAssistantListenerToEditor(partRef);
    }

    @Override
    public void partBroughtToTop(IWorkbenchPartReference partRef) {
        // TODO Auto-generated method stub
    }

    @Override
    public void partClosed(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partDeactivated(IWorkbenchPartReference partRef) {
        // TODO Auto-generated method stub

    }

    @Override
    public void partOpened(IWorkbenchPartReference partRef) {
    }

    @Override
    public void partHidden(IWorkbenchPartReference partRef) {
        // TODO Auto-generated method stub
    }

    @Override
    public void partVisible(IWorkbenchPartReference partRef) {
        // TODO Auto-generated method stub

    }

    @Override
    public void partInputChanged(IWorkbenchPartReference partRef) {
        // TODO Auto-generated method stub

    }
}
