package tools.vitruvius.domains.java.tests.monitorededitor;

import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.junit.Test;

import tools.vitruvius.domains.java.monitorededitor.MonitoredEditor;
import tools.vitruvius.domains.java.tests.monitorededitor.HadoopCodeElements.HadoopCoompilationUnitSize;

public class EditorManipulator extends WriterAppender {

    private static final int RUNS = 100;
    private static final long INITIAL_SLEEP = 1000 * 15;
    private boolean barrier = false;

    public EditorManipulator() {

    }

    @Test
    public void testExecuteEditorManipulator() throws JavaModelException, InterruptedException {
        System.out.println("EditorManipulator started");

        final Logger monitorLog = Logger.getLogger(MonitoredEditor.class);
        monitorLog.addAppender(this);
        final HadoopCoompilationUnitSize size = HadoopCoompilationUnitSize.LOC67175;
        // ICompilationUnit cu = HadoopCodeElements.getCompilationUnit(size);
        // IType type = HadoopCodeElements.getType(size);
        final IMethod method = HadoopCodeElements.getMethod(size);
        // long sleep = HadoopCodeElements.getSleepTime(size);
        // EditorManipulator.renameMethod(method);
        // EditorManipulator.changeReturnTypeVoidAndString(method);
        Thread.sleep(EditorManipulator.INITIAL_SLEEP);
        this.renameMethod(method, RUNS);
        // this.togglePrivatePublic(method, RUNS);
        // this.changePackageDeclaration(cu, RUNS);
        // this.addRemoveField(type, RUNS);
    }

    public void addRemoveField(final IType type, final int runs) throws JavaModelException, InterruptedException {
        final ICompilationUnit cu = type.getCompilationUnit();
        final int offset = ((ISourceReference) type.getChildren()[0]).getSourceRange().getOffset();
        final String field = "String lorem = \"Lorem ipsum dolor sit amet\";\n";
        for (int i = 0; i < runs; i++) {
            final InsertEdit addField = new InsertEdit(offset, field);
            final DeleteEdit removeField = new DeleteEdit(offset, field.length());
            while (this.barrier) {
                Thread.sleep(500);
            }
            this.barrier = true;
            if (i % 2 == 0) {
                cu.applyTextEdit(addField, null);
            } else {
                cu.applyTextEdit(removeField, null);
            }
        }
    }

    public void changePackageDeclaration(final ICompilationUnit cu, final int runs) throws JavaModelException,
            InterruptedException {
        final IPackageDeclaration pkg = cu.getPackageDeclarations()[0];
        final String pkgName = pkg.getElementName();
        final ISourceRange sourceRange = pkg.getSourceRange();
        final String oldSource = pkg.getSource();
        for (int i = 0; i < runs; i++) {
            while (this.barrier) {
                Thread.sleep(500);
            }
            this.barrier = true;
            final String newPkgName = pkgName.substring(0, pkgName.length() - 1) + (i % 2);
            final String newSource = oldSource.replace(pkgName, newPkgName);
            final ReplaceEdit edit = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), newSource);
            cu.applyTextEdit(edit, null);
        }
    }

    public void renameMethod(final IMethod method, final int runs) throws JavaModelException, InterruptedException {
        final String methodName = method.getElementName();
        final ICompilationUnit cu = method.getCompilationUnit();
        final ISourceRange sourceRange = method.getSourceRange();
        final String oldSource = method.getSource();
        for (int i = 0; i < runs; i++) {
            while (this.barrier) {
                Thread.sleep(500);
            }
            this.barrier = true;
            final String newMethodName = methodName.substring(0, methodName.length() - 1) + (i % 2);
            final String newSource = oldSource.replace(methodName, newMethodName);
            final ReplaceEdit edit = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), newSource);
            cu.applyTextEdit(edit, null);
        }
    }

    public void togglePrivatePublic(final IMethod method, final int runs) throws JavaModelException,
            InterruptedException {
        boolean originalPrivate = false;
        final ICompilationUnit cu = method.getCompilationUnit();
        final ISourceRange sourceRange = method.getSourceRange();
        final String oldSource = method.getSource();
        String newSource = "";

        for (int i = 0; i < runs; i++) {
            while (this.barrier) {
                Thread.sleep(500);
            }
            this.barrier = true;

            // create new ReplaceEdits because if reused Eclipse modifies them internally leading to
            // unforeseen faulty behavior
            TextEdit toPublic = null, toPrivate = null;
            if (oldSource.contains("private")) {
                originalPrivate = true;
                newSource = oldSource.replace("private", "public");
                toPublic = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), newSource);
                toPrivate = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength() - 1, oldSource);
            } else if (oldSource.contains("public")) {
                newSource = oldSource.replace("public", "private");
                toPublic = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength() + 1, oldSource);
                toPrivate = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), newSource);
            }

            if (originalPrivate) {
                if (i % 2 == 0) {
                    cu.applyTextEdit(toPublic, null);
                } else {
                    cu.applyTextEdit(toPrivate, null);
                }
            } else {
                if (i % 2 == 0) {
                    cu.applyTextEdit(toPrivate, null);
                } else {
                    cu.applyTextEdit(toPublic, null);
                }
            }
        }
    }

    public void changeReturnTypeVoidAndString(final IMethod method) throws JavaModelException, InterruptedException {
        final ICompilationUnit cu = method.getCompilationUnit();
        final ISourceRange sourceRange = method.getSourceRange();
        final String oldSource = method.getSource();
        final String newSource = oldSource.replace("void", "String");
        final TextEdit toString = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), newSource);
        final TextEdit toVoid = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), oldSource);
        for (int i = 0; i < RUNS; i++) {
            if (i % 2 == 0) {
                cu.applyTextEdit(toString, null);
            } else {
                cu.applyTextEdit(toVoid, null);
            }
            Thread.sleep(0);
        }
    }

    private static final String MSG_PATTERN = "TimeMeasurement:";

    @Override
    public synchronized void doAppend(final LoggingEvent event) {
        final String msg = event.getRenderedMessage();
        if (msg.startsWith(MSG_PATTERN)) {
            this.barrier = false;
        }
    }
}
