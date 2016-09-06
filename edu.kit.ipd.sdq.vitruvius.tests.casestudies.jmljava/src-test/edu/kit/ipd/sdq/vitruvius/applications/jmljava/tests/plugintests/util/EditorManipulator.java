package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.RenameJavaElementDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.monitorededitor.java.JavaMonitoredEditor;
import edu.kit.ipd.sdq.vitruvius.domains.java.monitorededitor.MonitoredEditor;
import edu.kit.ipd.sdq.vitruvius.framework.monitorededitor.registries.MonitoredEditorsRegistry;

public class EditorManipulator {

	public interface SaveChangesAction extends Runnable {
	}
	
	private static SaveChangesAction dummyAction = new SaveChangesAction() {
		@Override
		public void run() {
			// do nothing
		}
	};
	
	private static class DefaultChangesAction implements SaveChangesAction {
		private final IWorkbenchPage page;
		private final IEditorPart editor;
		
		public DefaultChangesAction(IWorkbenchPage page, IEditorPart editor) {
			this.page = page;
			this.editor = editor;
		}

		@Override
		public void run() {
			editor.doSave(null);
			page.closeAllEditors(false);
		}
	}
	
	public SaveChangesAction addImport(ICompilationUnit cu, String importStatement) throws PartInitException, JavaModelException {
		IPackageDeclaration lastPackageDeclaration = cu.getPackageDeclarations()[cu.getPackageDeclarations().length - 1];
		int offset = lastPackageDeclaration.getSourceRange().getOffset() + lastPackageDeclaration.getSourceRange().getLength();
		InsertEdit ie = new InsertEdit(offset, importStatement);
		return applyTextEdit(cu, ie);
	}
	
	public SaveChangesAction addSecondaryClass(ICompilationUnit cu) throws PartInitException, JavaModelException {
		return addSecondaryClass(cu, "TestClass");
	}

	public SaveChangesAction addSecondaryClass(ICompilationUnit cu, String className) throws PartInitException, JavaModelException {
		IType lastType = cu.getAllTypes()[cu.getAllTypes().length - 1];
		int offset = lastType.getSourceRange().getOffset() + lastType.getSourceRange().getLength();
		InsertEdit ie = new InsertEdit(offset, "\nclass " + className + " {\nint dummyField;\n}\n");
		return applyTextEdit(cu, ie);
	}
	
	public SaveChangesAction addField(IType clazz) throws JavaModelException, PartInitException {
		return addMember(clazz, "String lorem = \"Lorem ipsum dolor sit amet\";\n");
	}

	public SaveChangesAction removeField(IField field) throws JavaModelException, PartInitException {
		return removeElement(field.getCompilationUnit(), field);
	}
	
	public void renameField(IField field) throws CoreException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stopASTListening();
		performRenameRefactoring(field, IJavaRefactorings.RENAME_FIELD);
	}
	
	public void renameField(IField field, String newName) throws CoreException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stopASTListening();
		performRenameRefactoring(field, newName, IJavaRefactorings.RENAME_FIELD);
	}
	
	public SaveChangesAction addMethod(IType clazz) throws JavaModelException, PartInitException {
		return addMember(clazz, "boolean SOMEMETHOD_FOR_TEST_123(int i){\n\treturn i == 0;\n};\n");
	}
	
	public SaveChangesAction removeMethod(IMethod method) throws JavaModelException, PartInitException {
		return removeElement(method.getCompilationUnit(), method);
	}
	
	public void renameMethod(IMethod method) throws CoreException {
		performRenameRefactoring(method, IJavaRefactorings.RENAME_METHOD);
	}
	
	public SaveChangesAction addStatementToMethod(IMethod method, String newStatement) throws PartInitException, JavaModelException {
		int offset = method.getSourceRange().getOffset() + method.getSource().indexOf('{') + 1;
		InsertEdit addMember = new InsertEdit(offset, newStatement);
		return applyTextEdit(method.getCompilationUnit(), addMember);
	}
	
	public SaveChangesAction removeLineFromMethod(IMethod method, int methodBodyLineToRemove) throws JavaModelException, PartInitException {
		String[] methodBodyLines = method.getSource().split("\n");
		int startIndex = 0;
		for (; startIndex < methodBodyLines.length; ++startIndex) {
			if (methodBodyLines[startIndex].contains("{")) {
				++startIndex;
				break;
			}
		}
		String lineToRemove = methodBodyLines[startIndex + methodBodyLineToRemove];
		int offset = method.getSource().indexOf(lineToRemove); // this only works if there are no identical statements
		int length = lineToRemove.length();
		DeleteEdit removeElement = new DeleteEdit(method.getSourceRange().getOffset() + offset, length);
		return applyTextEdit(method.getCompilationUnit(), removeElement);
	}
	
	public SaveChangesAction addParameter(IMethod method) throws JavaModelException, PartInitException {
		int offset = method.getSourceRange().getOffset() + method.getSource().indexOf('(') + 1;
		InsertEdit addParameter = new InsertEdit(offset, "int SOME_NEW_PARAM");
		return applyTextEdit(method.getCompilationUnit(), addParameter);
	}
	
	public SaveChangesAction removeParameter(ILocalVariable param) throws JavaModelException, PartInitException {
		IMethod parentMethod = (IMethod)param.getParent();
		String methodSource = parentMethod.getSource();
		
		if (parentMethod.getParameters().length == 1) {
			return removeElement(param.getDeclaringMember().getCompilationUnit(), param);
		}
		
		int paramIndex = methodSource.indexOf(param.getSource());
		int paramEndIndex = paramIndex + param.getSourceRange().getLength();
		int postfixCommaIndex = methodSource.indexOf(",", paramEndIndex);
		if (!methodSource.substring(paramEndIndex, postfixCommaIndex).contains(")")) {
			// found the comma to remove!
			DeleteEdit paramDelete = new DeleteEdit(param.getSourceRange().getOffset(), postfixCommaIndex - paramIndex + 1);
			return applyTextEdit(parentMethod.getCompilationUnit(), paramDelete);
		}
		
		// TODO handle prefix comma
		return null;
	}
	
	public void renameParameter(ILocalVariable param) throws CoreException {
		performRenameRefactoring(param, IJavaRefactorings.RENAME_LOCAL_VARIABLE);
	}
	
	public SaveChangesAction addMember(IType clazz, String concreteSyntax) throws JavaModelException, PartInitException {
		ICompilationUnit cu = clazz.getCompilationUnit();
		int offset = ((ISourceReference) clazz.getChildren()[0])
				.getSourceRange().getOffset();
		InsertEdit addMember = new InsertEdit(offset, concreteSyntax);
		return applyTextEdit(cu, addMember);
	}
	
	
	
	private static SaveChangesAction removeElement(ICompilationUnit cu, ISourceReference srcRef) throws JavaModelException, PartInitException {
		DeleteEdit removeElement = new DeleteEdit(srcRef.getSourceRange()
				.getOffset(), srcRef.getSourceRange().getLength());
		return applyTextEdit(cu, removeElement);
	}
	
	private static void performRenameRefactoring(IJavaElement elementToRename,
			String refactoringName) throws CoreException {
		performRenameRefactoring(elementToRename, elementToRename.getElementName() + "RENAMED", refactoringName);
	}

	private static void performRenameRefactoring(IJavaElement elementToRename, String newName,
			String refactoringName) throws CoreException {
		RefactoringContribution contribution = RefactoringCore
				.getRefactoringContribution(refactoringName);
		final RenameJavaElementDescriptor descriptor = (RenameJavaElementDescriptor) contribution
				.createDescriptor();
		descriptor.setProject(elementToRename.getResource().getProject()
				.getName());
		descriptor.setNewName(newName);
		descriptor.setJavaElement(elementToRename);
		descriptor.setUpdateReferences(true);

		RefactoringStatus status = new RefactoringStatus();
		Refactoring refactoring = descriptor.createRefactoring(status);
		IProgressMonitor monitor = new NullProgressMonitor();
		refactoring.checkInitialConditions(monitor);
		refactoring.checkFinalConditions(monitor);
		Change change = refactoring.createChange(monitor);
		change.perform(monitor);
	}
	
	private static SaveChangesAction applyTextEdit(ICompilationUnit cu, TextEdit te) throws PartInitException, JavaModelException {
		// open an editor so the monitored editor can detect the change
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = IDE.openEditor(page, (IFile)cu.getResource());
		
		// apply the edit operation
		ITextEditor textEditor = (ITextEditor)editor;
		IDocument doc = textEditor.getDocumentProvider().getDocument(editor.getEditorInput());
		try {
			if (te instanceof InsertEdit) {
				doc.replace(te.getOffset(), 0, ((InsertEdit) te).getText());
			} else if (te instanceof DeleteEdit) {
				doc.replace(te.getOffset(), te.getLength(), "");
			} else {
				throw new IllegalArgumentException("Not supported text edit operation.");
			}
			Thread.sleep(1000);
		} catch (BadLocationException e) {
			throw new IllegalArgumentException();
		} catch (InterruptedException e) {
			return dummyAction;
		}
		
		return new DefaultChangesAction(page, editor);
	}
	
	private static void stopASTListening() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object editor = Iterables.find(MonitoredEditorsRegistry.getInstance().getRegisteredElements(), Predicates.instanceOf(JavaMonitoredEditor.class));
		Method m = MonitoredEditor.class.getDeclaredMethod("stopASTListening");
		m.setAccessible(true);
		m.invoke(editor);
	}

}
