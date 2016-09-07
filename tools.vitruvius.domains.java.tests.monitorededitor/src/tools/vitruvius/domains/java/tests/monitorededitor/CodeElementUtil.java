package tools.vitruvius.domains.java.tests.monitorededitor;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class CodeElementUtil {

    public static ICompilationUnit getCompilationUnit(String compilationUnitName) throws JavaModelException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = root.getProject("hadoop-hdfs");

        IJavaProject javaProject = JavaCore.create(project);
        for (IPackageFragment pkg : javaProject.getPackageFragments()) {
            for (ICompilationUnit unit : pkg.getCompilationUnits()) {
                if (unit.getElementName().equals(compilationUnitName)) {
                    return unit;
                }
            }
        }
        return null;
    }

    public static IType getType(String compilationUnitName, String typeName) throws JavaModelException {
        ICompilationUnit unit = getCompilationUnit(compilationUnitName);
        for (IType iType : unit.getAllTypes()) {
            if (iType.getElementName().equals(typeName)) {
                return iType;
            }
        }
        return null;
    }

    public static IMethod getMethod(String compilationUnitName, String typeName, String methodName)
            throws JavaModelException {
        IType iType = getType(compilationUnitName, typeName);
        if (iType.getElementName().equals(typeName)) {
            for (IJavaElement child : iType.getChildren()) {
                if ((child instanceof IMethod) && child.getElementName().equals(methodName))
                    return (IMethod) child;
            }
        }
        return null;
    }

}
