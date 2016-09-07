package tools.vitruvius.domains.java.monitorededitor.jamopputil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.emftext.language.java.containers.CompilationUnit;

import tools.vitruvius.domains.java.util.jamoppparser.JaMoPPParser;

public final class AST2JaMoPP {

    private static JaMoPPParser jamopp = new JaMoPPParser();

    private AST2JaMoPP() {
    }

    public static CompilationUnit getCompilationUnitForSerializedCompilationUnit(
            final org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit) throws IOException {
        return jamopp.parseCompilationUnitFromDisk(getIPathFromCompilationUnitWithResource(astCompilationUnit));
    }

    public static CompilationUnit getCompilationUnitForInMemoryCompilationUnit(
            final org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit, final URI uri) throws IOException {
        final CompilationUnit cu = jamopp.parseCompilationUnitFromInputStream(uri, getInputStream(astCompilationUnit));
        final String cuName = getCompilationUnitName(astCompilationUnit);
        restoreCompilationUnitName(cuName, cu);
        return cu;
    }

    private static InputStream getInputStream(final org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit) {
        final String newSource = astCompilationUnit.toString();
        return new ByteArrayInputStream(newSource.getBytes());
    }

    private static String getCompilationUnitName(final org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit) {
        // if no JavaElement is reference, the first top-level Type is assumed to be the compilation
        // unit's name
        final IJavaElement element = astCompilationUnit.getJavaElement();
        if (element != null) {
            return element.getElementName();
        } else {
            for (final Object type : astCompilationUnit.types()) {
                if (!(type instanceof TypeDeclaration)) {
                    continue;
                }
                return ((TypeDeclaration) type).getName().toString();
            }
            return null;
        }
    }

    private static void restoreCompilationUnitName(final String compilationUnitName,
            final CompilationUnit compilationUnit) {
        if (compilationUnit == null) {
            return;
        }
        final String fullCuName = getFullCompilationUnitName(compilationUnitName, compilationUnit);
        compilationUnit.setName(fullCuName);
    }

    private static String getFullCompilationUnitName(final String compilationUnitName,
            final CompilationUnit compilationUnit) {
        final StringBuilder projectPrefix = new StringBuilder();
        for (final String ns : compilationUnit.getNamespaces()) {
            projectPrefix.append(ns);
            projectPrefix.append('.');
        }
        final String fullCuName = projectPrefix + compilationUnitName;
        return fullCuName;
    }

    public static CompilationUnit getCompilationUnitFromAbsolutePath(final IPath compilationUnitPath)
            throws IOException {
        return jamopp.parseCompilationUnitFromDisk(compilationUnitPath);
    }

    public static IPath getIPathFromCompilationUnitWithResource(final ASTNode astNode) {
        final IResource iResource = getIResource(astNode);
        if (iResource == null) {
            return null;
        }
        return iResource.getLocation();
    }

    public static IResource getIResource(final ASTNode astNode) {
        if (null == astNode) {
            return null;
        }
        final ASTNode astCompilationUnit = astNode.getRoot();
        if (!(astCompilationUnit instanceof org.eclipse.jdt.core.dom.CompilationUnit)) {
            throw new IllegalStateException("astNode.getRoot() is not a CompilationUnit");
        }
        final ICompilationUnit iCU = (ICompilationUnit) ((org.eclipse.jdt.core.dom.CompilationUnit) astCompilationUnit)
                .getJavaElement();
        if (iCU == null) {
            return null;
        }
        IResource resource = null;
        try {
            resource = iCU.getCorrespondingResource();
        } catch (final JavaModelException e) {
            e.printStackTrace();
        }
        return resource;
    }

}
