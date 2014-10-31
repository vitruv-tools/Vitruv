package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.test;

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

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.MonitoredEditor;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.test.HadoopCodeElements.HadoopCoompilationUnitSize;

public class EditorManipulator extends WriterAppender {

    private static final int RUNS = 3;
    private static final long INITIAL_SLEEP = 1000 * 15;
    private boolean barrier = false;

    public EditorManipulator() throws JavaModelException, InterruptedException {
        System.out.println("EditorManipulator started");

        Logger monitorLog = Logger.getLogger(MonitoredEditor.class);
        monitorLog.addAppender(this);
        HadoopCoompilationUnitSize size = HadoopCoompilationUnitSize.LOC67175;
        // ICompilationUnit cu = HadoopCodeElements.getCompilationUnit(size);
        // IType type = HadoopCodeElements.getType(size);
        IMethod method = HadoopCodeElements.getMethod(size);
        // long sleep = HadoopCodeElements.getSleepTime(size);
        // EditorManipulator.renameMethod(method);
        // EditorManipulator.changeReturnTypeVoidAndString(method);
        Thread.sleep(this.INITIAL_SLEEP);
        this.renameMethod(method, RUNS);
        // this.togglePrivatePublic(method, RUNS);
        // this.changePackageDeclaration(cu, RUNS);
        // this.addRemoveField(type, RUNS);
    }

    public void addRemoveField(IType type, int runs) throws JavaModelException, InterruptedException {
        ICompilationUnit cu = type.getCompilationUnit();
        int offset = ((ISourceReference) type.getChildren()[0]).getSourceRange().getOffset();
        String field = "String lorem = \"Lorem ipsum dolor sit amet\";\n";
        for (int i = 0; i < runs; i++) {
            InsertEdit addField = new InsertEdit(offset, field);
            DeleteEdit removeField = new DeleteEdit(offset, field.length());
            while (this.barrier) {
                Thread.sleep(500);
            }
            this.barrier = true;
            if (i % 2 == 0)
                cu.applyTextEdit(addField, null);
            else
                cu.applyTextEdit(removeField, null);
        }
    }

    public void changePackageDeclaration(ICompilationUnit cu, int runs) throws JavaModelException, InterruptedException {
        IPackageDeclaration pkg = cu.getPackageDeclarations()[0];
        String pkgName = pkg.getElementName();
        ISourceRange sourceRange = pkg.getSourceRange();
        String oldSource = pkg.getSource();
        for (int i = 0; i < runs; i++) {
            while (this.barrier) {
                Thread.sleep(500);
            }
            this.barrier = true;
            String newPkgName = pkgName.substring(0, pkgName.length() - 1) + (i % 2);
            String newSource = oldSource.replace(pkgName, newPkgName);
            ReplaceEdit edit = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), newSource);
            cu.applyTextEdit(edit, null);
        }
    }

    public void renameMethod(IMethod method, int runs) throws JavaModelException, InterruptedException {
        String methodName = method.getElementName();
        ICompilationUnit cu = method.getCompilationUnit();
        ISourceRange sourceRange = method.getSourceRange();
        String oldSource = method.getSource();
        for (int i = 0; i < runs; i++) {
            while (this.barrier) {
                Thread.sleep(500);
            }
            this.barrier = true;
            String newMethodName = methodName.substring(0, methodName.length() - 1) + (i % 2);
            String newSource = oldSource.replace(methodName, newMethodName);
            ReplaceEdit edit = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), newSource);
            cu.applyTextEdit(edit, null);
        }
    }

    public void togglePrivatePublic(IMethod method, int runs) throws JavaModelException, InterruptedException {
        boolean originalPrivate = false;
        ICompilationUnit cu = method.getCompilationUnit();
        ISourceRange sourceRange = method.getSourceRange();
        String oldSource = method.getSource();
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

    public void changeReturnTypeVoidAndString(IMethod method) throws JavaModelException, InterruptedException {
        ICompilationUnit cu = method.getCompilationUnit();
        ISourceRange sourceRange = method.getSourceRange();
        String oldSource = method.getSource();
        String newSource = oldSource.replace("void", "String");
        TextEdit toString = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), newSource);
        TextEdit toVoid = new ReplaceEdit(sourceRange.getOffset(), sourceRange.getLength(), oldSource);
        for (int i = 0; i < RUNS; i++) {
            if (i % 2 == 0)
                cu.applyTextEdit(toString, null);
            else
                cu.applyTextEdit(toVoid, null);
            Thread.sleep(0);
        }
    }

    private static final String MSG_PATTERN = "TimeMeasurement:";

    @Override
    public synchronized void doAppend(LoggingEvent event) {
        String msg = event.getRenderedMessage();
        if (msg.startsWith(MSG_PATTERN)) {
            this.barrier = false;
        }
    }
}
