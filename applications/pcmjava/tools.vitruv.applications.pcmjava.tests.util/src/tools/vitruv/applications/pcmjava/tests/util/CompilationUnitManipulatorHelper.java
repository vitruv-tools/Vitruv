package tools.vitruv.applications.pcmjava.tests.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.TextEdit;

import tools.vitruv.domains.java.JavaNamespace;

/**
 * Helper class that allows the manipulation of compilation units that causes a notification of the
 * Java monitor.
 *
 * @author langhamm
 *
 */
public class CompilationUnitManipulatorHelper {

    private CompilationUnitManipulatorHelper() {

    }

    public static void editCompilationUnit(final ICompilationUnit cu, SynchronizationAwaitCallback synchronizationCallback, 
    		final TextEdit... edits) throws JavaModelException {
    	cu.becomeWorkingCopy(new NullProgressMonitor());
        for (final TextEdit edit : edits) {
            cu.applyTextEdit(edit, null);
        }
        cu.reconcile(ICompilationUnit.NO_AST, false, null, null);
        // Wait for synchronization before commiting the working copy, because
        // JaMoPP overwrites the compilation unit during reconcile
        synchronizationCallback.waitForSynchronization(1);
        cu.commitWorkingCopy(true, new NullProgressMonitor());
        cu.discardWorkingCopy();
    }

    public static ICompilationUnit findICompilationUnitWithClassName(String entityName,
            final IProject currentTestProject) throws JavaModelException {
        entityName = CompilationUnitManipulatorHelper.ensureJavaFileExtension(entityName);
        final IJavaProject javaProject = JavaCore.create(currentTestProject);
        for (final IPackageFragmentRoot packageFragmentRoot : javaProject.getPackageFragmentRoots()) {
            final IJavaElement[] children = packageFragmentRoot.getChildren();
            for (final IJavaElement iJavaElement : children) {
                if (iJavaElement instanceof IPackageFragment) {
                    final IPackageFragment fragment = (IPackageFragment) iJavaElement;
                    final IJavaElement[] javaElements = fragment.getChildren();
                    for (int k = 0; k < javaElements.length; k++) {
                        final IJavaElement javaElement = javaElements[k];
                        if (javaElement.getElementType() == IJavaElement.COMPILATION_UNIT) {
                            final ICompilationUnit compilationUnit = (ICompilationUnit) javaElement;
                            if (compilationUnit.getElementName().equals(entityName)) {
                                return compilationUnit;
                            }
                        }
                    }
                }
            }
        }
        throw new RuntimeException("Could not find a compilation unit with name " + entityName);
    }
    
    public static ICompilationUnit addMethodToCompilationUnit(final String compilationUnitName,
            final String methodString, final IProject currentTestProject, SynchronizationAwaitCallback synchronizerCallback) 
            		throws JavaModelException {
        final ICompilationUnit cu = findICompilationUnitWithClassName(compilationUnitName, currentTestProject);
        final IType firstType = cu.getAllTypes()[0];
        final int offset = CompilationUnitManipulatorHelper.getOffsetForClassifierManipulation(firstType);
        final InsertEdit insertEdit = new InsertEdit(offset, methodString);
        editCompilationUnit(cu, synchronizerCallback, insertEdit);
        return cu;
    }

    public static String ensureJavaFileExtension(String entityName) {
        if (!entityName.endsWith("." + JavaNamespace.FILE_EXTENSION)) {
            entityName = entityName + "." + JavaNamespace.FILE_EXTENSION;
        }
        return entityName;
    }

    public static int getOffsetForClassifierManipulation(final IType firstType) throws JavaModelException {
        final int posOfFirstBracket = firstType.getCompilationUnit().getSource().indexOf("{");
        return posOfFirstBracket + 1;
    }

    public static int getOffsetForAddingAnntationToClass(final IType firstType) throws JavaModelException {
        final int posOfFirstBracket = firstType.getCompilationUnit().getSource().indexOf("public");
        return posOfFirstBracket - 1;
    }

    public static int getOffsetToInsertInMethodInCompilationUnit(final ICompilationUnit compUnit,
            final String methodName) throws JavaModelException {
        final IType firstType = compUnit.getAllTypes()[0];
        final String source = firstType.getCompilationUnit().getSource();
        final int methodOffset = source.indexOf(methodName);
        final String sourceMethod = source.substring(methodOffset, source.length());
        final int inMethodOffset = sourceMethod.indexOf("{");
        final int offset = methodOffset + inMethodOffset + 1;
        return offset;
    }

    public static int getOffsetForAddingAnntationToField(final IType type, final String fieldName) throws JavaModelException  {
        for (final IField iField : type.getFields()) {
            if (iField.getElementName().equals(fieldName)) {
                return iField.getSourceRange().getOffset();
            }
        }
        throw new RuntimeException("Could not find field " + fieldName + " in class " + type.getElementName());
    }

}
